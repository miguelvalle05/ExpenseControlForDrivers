package com.example.traz.ui.choferdos;

import androidx.fragment.app.FragmentResultListener;
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
import com.example.traz.Chofer;
import com.example.traz.R;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;



import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import fr.ganfra.materialspinner.MaterialSpinner;


public class ChoferdosFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener, View.OnClickListener{


    TextInputEditText id_cho;
    TextInputEditText nombre;
    TextInputEditText paterno;
    TextInputEditText materno;
    MaterialSpinner estatus;
    MaterialButton edit;
    MaterialButton delete;
    ProgressDialog progreso;


    String est;

    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;

 public ChoferdosFragment(){

 }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.choferdos_fragment, container, false);


        getObjects(root);

        getParentFragmentManager().setFragmentResultListener("key", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull @NotNull String requestKey, @NonNull @NotNull Bundle bundle) {

                String id_chofer= bundle.getString("id_chofer");
                id_cho.setText(id_chofer);
                String URL ="http://10.5.2.121/login/fetchchofer.php?id_chofer="+ id_chofer;

                cargarWebservices(URL);
            }
        });
        requestQueue= Volley.newRequestQueue(getContext());
        delete.setOnClickListener(this);
        edit.setOnClickListener(this);
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

        return root;
    }
    private void getObjects(View view){


        id_cho= view.findViewById(R.id.altacd_inp_id);
        nombre= view.findViewById(R.id.altacd_inp_nombre);
        paterno=view.findViewById(R.id.altacd_inp_paterno);
        materno=view.findViewById(R.id.altacd_inp_materno);
        estatus=view.findViewById(R.id.spcdEstatus);
        edit= view.findViewById(R.id.edit_btn_chofer);
        delete= view.findViewById(R.id.delete_btn_chofer);


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
        Integer p;
        Chofer miChofer= new Chofer();

        JSONArray json= response.optJSONArray("chofer");
        JSONObject jsonObject= null;
        try{
            jsonObject=json.getJSONObject(0);
            miChofer.setNombre(jsonObject.optString("nombre"));
            miChofer.setApellido_pat(jsonObject.optString("apellido_pat"));
            miChofer.setApellido_mat(jsonObject.optString("apellido_mat"));
            miChofer.setEstatus(jsonObject.optString("estatus"));




        }catch(JSONException e){
            e.printStackTrace();

        }

        nombre.setText(miChofer.getNombre());
        paterno.setText(miChofer.getApellido_pat());
        materno.setText(miChofer.getApellido_mat());

        String opcion = String.valueOf(miChofer.getEstatus());
        Integer op;
        if (opcion.equals("1")){
          op=2;
            estatus.setSelection(op);

        }else {
            op=3;
            estatus.setSelection(op);
        }

















    }
    public void onClick(View v) {

        int id= v.getId();
        if(id== R.id.edit_btn_chofer) {

            String idCho = id_cho.getText().toString();
            String name= nombre.getText().toString();
            String pat=paterno.getText().toString();
            String mat= materno.getText().toString();
            String esta=est;


            updateChofer(idCho,name,pat,mat,esta);



        }

        if(id== R.id.delete_btn_chofer) {


            String idCho = id_cho.getText().toString();
            removeChofer(idCho);




        }

    }
    public void removeChofer(String idCho){
        String URL ="http://10.5.2.121/login/deletechofer.php";
        StringRequest stringRequest= new StringRequest(
                Request.Method.POST,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {









                        Toast.makeText(getActivity(),"Chofer Eliminado",Toast.LENGTH_SHORT).show();

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
                params.put("id_chofer",idCho);


                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }



    private void updateChofer(String idCho,final String name, final String pat, final String mat, final String esta){
        String URL ="http://10.5.2.121/login/editchofer.php";
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
                params.put("id_chofer",idCho);

                params.put("nombre", name);
                params.put("apellido_pat", pat);
                params.put("apellido_mat", mat);

                params.put("estatus",esta);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);



    }

}