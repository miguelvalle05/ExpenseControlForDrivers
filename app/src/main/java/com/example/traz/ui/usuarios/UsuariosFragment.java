package com.example.traz.ui.usuarios;



import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.traz.R;
import com.example.traz.ui.usariosdos.UsuariosdosFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

import fr.ganfra.materialspinner.MaterialSpinner;


public class UsuariosFragment extends Fragment implements View.OnClickListener{

    TextInputEditText id_usuario;

    TextInputEditText nombre;
    TextInputEditText paterno;
    TextInputEditText materno;

    TextInputEditText puesto;
    TextInputEditText user;
    TextInputEditText pwd;
    MaterialSpinner estatus;
    MaterialSpinner tipo;
    String ti;
    String est;
    MaterialButton registrar;
    MaterialButton buscar;
    RequestQueue requestQueue;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;


    private static final String URL="http://10.5.2.121/login/save.php";







    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_usuarios, container, false);
        getObjects(root);

        registrar.setOnClickListener(this);
        buscar.setOnClickListener(this);

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
        id_usuario=view.findViewById(R.id.alta_inp_id);
        tipo= view.findViewById(R.id.spuTipo);
        nombre= view.findViewById(R.id.alta_inp_nombre);
        paterno=view.findViewById(R.id.alta_inp_paterno);
        materno=view.findViewById(R.id.alta_inp_materno);
        puesto=view.findViewById(R.id.alta_inp_puesto);
        user=view.findViewById(R.id.alta_inp_user);
        pwd=view.findViewById(R.id.alta_inp_pwd);
        estatus=view.findViewById(R.id.spuEstatus);
        registrar=view.findViewById(R.id.alta_btn_register);
        buscar= view.findViewById(R.id.fetch_btn_usuario);









    }


    @Override
    public void onClick(View v) {



        int id= v.getId();
        if(id== R.id.alta_btn_register){



            String tip= ti;
            String name= nombre.getText().toString();
            String pat=paterno.getText().toString();
            String mat= materno.getText().toString();
            String pues=puesto.getText().toString();
            String usuario=user.getText().toString();
            String pas=pwd.getText().toString();
            String esta=est;







            createUser(tip,name,pat,mat,pues,usuario,pas,esta);



        }
      if (id==R.id.fetch_btn_usuario){



          UsuariosdosFragment fragment= new UsuariosdosFragment();

          Bundle bundle = new Bundle();
          bundle.putString("id_usuario", id_usuario.getText().toString().trim());
          getParentFragmentManager().setFragmentResult("key",bundle);




          FragmentTransaction ft = getFragmentManager().beginTransaction();
          ft.replace(R.id.fragment, fragment, "choferfragament");

          ft.addToBackStack(null);  //opcional, si quieres agregarlo a la pila
          ft.commit();




          }










    }

    private void createUser(final String tip, final String name, final String mat, final String pat, final String pues, final String usuario, final String pas, final String esta) {

        StringRequest stringRequest=new StringRequest(
                Request.Method.POST,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getActivity(),"Usuario creado correctamente",Toast.LENGTH_SHORT).show();
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