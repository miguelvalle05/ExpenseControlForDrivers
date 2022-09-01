package com.example.traz.ui.usariosdos;

import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.traz.R;
import com.example.traz.Usuario;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;


import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import fr.ganfra.materialspinner.MaterialSpinner;

public class UsuariosdosFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener, View.OnClickListener{


    TextInputEditText id_user;

    TextInputEditText nombre;
    TextInputEditText paterno;
    TextInputEditText materno;

    TextInputEditText puesto;
    TextInputEditText user;
    TextInputEditText pwd;

    MaterialButton edit;
    MaterialButton delete;
    ProgressDialog progreso;
    MaterialSpinner estatus;
    MaterialSpinner tipo;
    String ti;
    String est;



    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;

    public UsuariosdosFragment(){

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.usuariosdos_fragment, container, false);

        getObjects(root);


        getParentFragmentManager().setFragmentResultListener("key", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull @NotNull String requestKey, @NonNull @NotNull Bundle bundle) {

                String id_usuario= bundle.getString("id_usuario");
                id_user.setText(id_usuario);
                String URL ="http://10.5.2.121/login/fetch.php?id_usuario="+ id_usuario;

                cargarWebservices(URL);
            }
        });


        requestQueue= Volley.newRequestQueue(getContext());
        delete.setOnClickListener(this);
        edit.setOnClickListener(this);


        //Spinner de estatus

        final ArrayAdapter<CharSequence> adapter2 =
                ArrayAdapter.createFromResource(
                        getContext(),
                        R.array.opciones,
                        android.R.layout.simple_spinner_item);
        estatus.setAdapter(adapter2);
        estatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String opcion = String.valueOf(estatus.getSelectedItemId());
                int op = Integer.parseInt(opcion);
                System.out.println(opcion);
                if (op == 2){
                    est = "1";
                }else if (op == 3){
                    est= "0";
                }
            }
            //Fin spinner estatus

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Spinner de tipo usuario

        final ArrayAdapter<CharSequence> adapter3 =
                ArrayAdapter.createFromResource(
                        getContext(),
                        R.array.opcionesusuario,
                        android.R.layout.simple_spinner_item);
        tipo.setAdapter(adapter3);
        tipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String opcion = String.valueOf(tipo.getSelectedItemId());
                int op = Integer.parseInt(opcion);
                System.out.println(opcion);
                if (op == 2){
                    ti = "1";
                }else if (op == 3){
                    ti= "2";
                }
                else if(op==4){
                    ti="3";
                }
            }
            //Fin spinner tipo usuario

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });








        return root;

    }
    private void getObjects(View view){

        id_user=view.findViewById(R.id.altad_inp_id);
        tipo= view.findViewById(R.id.spudTipo);
        nombre= view.findViewById(R.id.altad_inp_nombre);
        paterno=view.findViewById(R.id.altad_inp_paterno);
        materno=view.findViewById(R.id.altad_inp_materno);
        puesto=view.findViewById(R.id.altad_inp_puesto);
        user=view.findViewById(R.id.altad_inp_user);
        pwd=view.findViewById(R.id.altad_inp_pwd);
        estatus=view.findViewById(R.id.spudEstatus);
        edit= view.findViewById(R.id.edit_btn_usuario);
        delete= view.findViewById(R.id.delete_btn_usuario);




    }



    public void cargarWebservices(String URL){


        progreso= new ProgressDialog(getContext());
        progreso.setMessage("Consultando.......");
        progreso.show();




        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,URL,null,this,this);
        requestQueue.add(jsonObjectRequest);






    }

    @Override
    public void onErrorResponse(VolleyError error) {

        progreso.hide();
        Toast.makeText(getContext(),"No se pudo consultar"+error.toString(),Toast.LENGTH_SHORT).show();
        Log.i("ERROR",error.toString());

    }

    @Override
    public void onResponse(JSONObject response) {
        progreso.hide();
       // Toast.makeText(getContext(),"Mensaje: "+response,Toast.LENGTH_SHORT).show(); //Mnesaje para validar que nos este regresando datos el WEBSERVICE

        Usuario miUsuario= new Usuario();
        JSONArray json= response.optJSONArray("usuario");
        JSONObject jsonObject= null;
        try{
            jsonObject=json.getJSONObject(0);

            miUsuario.setId_tipo_usuario(jsonObject.optString("id_tipo_usuario"));
            miUsuario.setNombre(jsonObject.optString("nombre"));
            miUsuario.setApellido_mat(jsonObject.optString("apellido_mat"));
            miUsuario.setApellido_pat(jsonObject.optString("apellido_pat"));
            miUsuario.setPuesto(jsonObject.optString("puesto"));
            miUsuario.setUser(jsonObject.optString("user"));
            miUsuario.setPwd(jsonObject.optString("pwd"));
            miUsuario.setEstatus(jsonObject.optString("estatus"));

        }catch(JSONException e){
            e.printStackTrace();

        }
        String opcion1 = String.valueOf(miUsuario.getId_tipo_usuario());
        Integer op1;
        if (opcion1.equals("1")){
            op1=2;
           tipo.setSelection(op1);

        }else if(opcion1.equals("2")){

            op1=3;
            tipo.setSelection(op1);
        }
        else{
            op1=4;
            tipo.setSelection(op1);
        }




        nombre.setText(miUsuario.getNombre());
        materno.setText(miUsuario.getApellido_mat());
        paterno.setText(miUsuario.getApellido_pat());

        puesto.setText(miUsuario.getPuesto());
        user.setText(miUsuario.getUser());
        pwd.setText(miUsuario.getPwd());
        String opcion = String.valueOf(miUsuario.getEstatus());
        Integer op;
        if (opcion.equals("1")){
            op=2;
            estatus.setSelection(op);

        }else {
            op=3;
            estatus.setSelection(op);
        }










    }


    @Override
    public void onClick(View v) {
        int id= v.getId();
        if(id== R.id.edit_btn_usuario) {

            String idUser = id_user.getText().toString();
            String tip= ti;
            String name= nombre.getText().toString();
            String pat=paterno.getText().toString();
            String mat= materno.getText().toString();
            String pues=puesto.getText().toString();
            String usuario=user.getText().toString();
            String pas=pwd.getText().toString();
            String esta=est;


            updateUser(idUser,tip,name,pat,mat,pues,usuario,pas,esta);



        }

        if(id== R.id.delete_btn_usuario) {


            String idUser = id_user.getText().toString();
            removeUser(idUser);




        }

    }

    public void removeUser(String idUser){
        String URL ="http://10.5.2.123/login/delete.php";
        StringRequest  stringRequest= new StringRequest(
                Request.Method.POST,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {









                        Toast.makeText(getActivity(),"Usuario Eliminado",Toast.LENGTH_SHORT).show();

                        getActivity().onBackPressed();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }



        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_usuario",idUser);


                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }


    private void updateUser(String idUser,final String tip, final String name, final String mat, final String pat, final String pues, final String usuario, final String pas, final String esta){
        String URL ="http://10.5.2.123/login/edit.php";
           StringRequest stringRequest = new StringRequest(
                   Request.Method.POST,
                   URL,
                   new Response.Listener<String>() {
                       @Override
                       public void onResponse(String response) {
                           Toast.makeText(getActivity(),"Update Successfully",Toast.LENGTH_SHORT).show();

                           getActivity().onBackPressed();

                       }
                   },
                   new Response.ErrorListener() {
                       @Override
                       public void onErrorResponse(VolleyError error) {

                       }
                   }


           ) {
               @Override
               protected Map<String, String> getParams() throws AuthFailureError {
                   Map<String, String> params = new HashMap<>();
                   params.put("id_usuario",idUser);
                   params.put("id_tipo_usuario",tip);
                   params.put("nombre", name);
                   params.put("apellido_mat", pat);
                   params.put("apellido_pat", mat);
                   params.put("puesto", pues);
                   params.put("user", usuario);
                   params.put("pwd", pas);
                   params.put("estatus",esta);
                   return params;
               }
           };
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);



    }
}