package com.example.traz.ui.Chofer;

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
import com.example.traz.ui.choferdos.ChoferdosFragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.ganfra.materialspinner.MaterialSpinner;

public class ChoferFragment extends Fragment implements View.OnClickListener {


    TextInputEditText id_chofer;
    TextInputEditText nombre;
    TextInputEditText paterno;
    TextInputEditText materno;

    MaterialButton registrar;
    MaterialButton buscar;
    MaterialSpinner estatus;

     String est;

    private static final String URL="http://10.5.2.121/login/savechofer.php";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_chofer, container, false);



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





        return root;
    }
    private void getObjects(View view){


        id_chofer= view.findViewById(R.id.altac_inp_id);
        nombre= view.findViewById(R.id.altac_inp_nombre);
        paterno=view.findViewById(R.id.altac_inp_paterno);
        materno=view.findViewById(R.id.altac_inp_materno);
        estatus=view.findViewById(R.id.spcEstatus);
        registrar=view.findViewById(R.id.altac_btn_register);
        buscar= view.findViewById(R.id.fetch_btn_chofer);

    }


    @Override
    public void onClick(View v) {

        int id= v.getId();
        if(id== R.id.altac_btn_register){




            String name= nombre.getText().toString();
            String pat=paterno.getText().toString();
            String mat= materno.getText().toString();

          String esta=est;







          createChofer(name,pat,mat,esta);



        }
        if (id==R.id.fetch_btn_chofer){



            ChoferdosFragment fragment= new ChoferdosFragment();

            Bundle bundle = new Bundle();
            bundle.putString("id_chofer", id_chofer.getText().toString().trim());
            getParentFragmentManager().setFragmentResult("key",bundle);




            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.fragmentc, fragment, "choferfragament");

            ft.addToBackStack(null);  //opcional, si quieres agregarlo a la pila
            ft.commit();




        }



    }

    private void createChofer(final String name, final String pat, final String mat, final String esta) {

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
