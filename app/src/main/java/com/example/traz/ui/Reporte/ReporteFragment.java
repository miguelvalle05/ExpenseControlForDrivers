package com.example.traz.ui.Reporte;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.traz.Chofer;
import com.example.traz.Usuario;
import com.example.traz.Viaje;
import com.example.traz.ui.reportedos.reportedosFragment;
import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.loopj.android.http.*;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.traz.R;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import fr.ganfra.materialspinner.MaterialSpinner;

public class ReporteFragment extends Fragment implements View.OnClickListener {
    private AsyncHttpClient cliente;
    private int dia, mes,ano;
    TextInputEditText importe;
    TextInputEditText id_reporte_gasto;

    MaterialButton registrar;
    MaterialButton buscar;
    EditText fecha;
    Button btnFecha;
    MaterialSpinner estatus;
    MaterialSpinner tipo;
    MaterialSpinner chofer;
    MaterialSpinner usuario;
    MaterialSpinner viaje;
    String ti;
    String est;
    private static final String URL="http://10.5.2.121/login/save_reporte.php";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_reporte, container, false);

        getObjects(root);

        registrar.setOnClickListener(this);
        buscar.setOnClickListener(this);
        // datepicker

        btnFecha.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                final Calendar c= Calendar.getInstance();
                dia=c.get(Calendar.DAY_OF_MONTH);
                mes=c.get(Calendar.MONTH);
                ano = c.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear , int dayOfMonth) {
                        //     sFecha=Integer.toString(2019-year);
                        fecha.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
                    }
                },ano,mes,dia);
                datePickerDialog.show();

            }
        });

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
                        R.array.opcionesgasto,
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

        cliente = new AsyncHttpClient();
        llenarSpinnerc();
        llenarSpinneru();
        llenarSpinnerv();

        return root;
    }
    private void getObjects(View view){

        id_reporte_gasto= view.findViewById(R.id.alta_inp_idr);
        fecha= view.findViewById(R.id.fechaIngresoCrear);
        btnFecha= view.findViewById(R.id.btnDateCrear);
        tipo= view.findViewById(R.id.sprTipog);
        estatus=view.findViewById(R.id.sprEstatus);
        chofer=view.findViewById(R.id.sprChofer);
        usuario= view.findViewById(R.id.sprUsario);
        viaje=view.findViewById(R.id.sprViaje);
        importe=view.findViewById(R.id.alta_inp_importe);
        registrar=view.findViewById(R.id.altar_btn_register);
        buscar=view.findViewById(R.id.fetch_btn_reporte);





    }
    private void llenarSpinnerc(){
        String URL = "http://10.5.2.121/login/obtenerChofer.php";
        cliente.post(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200){
                    cargarSpinner(new String(responseBody));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });


    }
    private void llenarSpinneru(){
        String URL = "http://10.5.2.121/login/obtenerUsuario.php";
        cliente.post(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200){
                    cargarSpinneru(new String(responseBody));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });


    }
    private void llenarSpinnerv(){
        String URL = "http://10.5.2.121/login/obtenerViaje.php";
        cliente.post(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200){
                    cargarSpinnerv(new String(responseBody));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });


    }
    private void cargarSpinner(String respuesta){
        ArrayList <Chofer> lista = new ArrayList<Chofer>();
        try {
            JSONArray jsonArreglo= new JSONArray(respuesta);
            for (int i=0;i<jsonArreglo.length();i++){

                Chofer c= new Chofer();
                c.setId_chofer(jsonArreglo.getJSONObject(i).getString("id_chofer"));
                c.setNombre(jsonArreglo.getJSONObject(i).getString("nombre"));
                c.setApellido_pat(jsonArreglo.getJSONObject(i).getString("apellido_pat"));
                c.setApellido_mat(jsonArreglo.getJSONObject(i).getString("apellido_mat"));
                lista.add(c);

            }
            ArrayAdapter <Chofer> a = new ArrayAdapter<Chofer>(getContext(), android.R.layout.simple_dropdown_item_1line,lista);
            chofer.setAdapter(a);



        }catch (Exception e){

        }


    }
    private void cargarSpinneru(String respuesta){
        ArrayList <Usuario> lista = new ArrayList<Usuario>();
        try {
            JSONArray jsonArreglo= new JSONArray(respuesta);
            for (int i=0;i<jsonArreglo.length();i++){

                Usuario u= new Usuario();
                u.setId_usuario(jsonArreglo.getJSONObject(i).getString("id_usuario"));
                u.setNombre(jsonArreglo.getJSONObject(i).getString("nombre"));
                u.setApellido_pat(jsonArreglo.getJSONObject(i).getString("apellido_pat"));
                u.setApellido_mat(jsonArreglo.getJSONObject(i).getString("apellido_mat"));
                lista.add(u);

            }
            ArrayAdapter <Usuario> a = new ArrayAdapter<Usuario>(getContext(), android.R.layout.simple_dropdown_item_1line,lista);
            usuario.setAdapter(a);



        }catch (Exception e){

        }


    }
    private void cargarSpinnerv(String respuesta){
        ArrayList <Viaje> lista = new ArrayList<Viaje>();
        try {
            JSONArray jsonArreglo= new JSONArray(respuesta);
            for (int i=0;i<jsonArreglo.length();i++){

                Viaje v= new Viaje();
                v.setId_viaje(jsonArreglo.getJSONObject(i).getString("id_viaje"));
                v.setPunto_salida(jsonArreglo.getJSONObject(i).getString("punto_salida"));
                v.setPunto_llegada(jsonArreglo.getJSONObject(i).getString("punto_llegada"));

                lista.add(v);

            }
            ArrayAdapter <Viaje> a = new ArrayAdapter<Viaje>(getContext(), android.R.layout.simple_dropdown_item_1line,lista);
            viaje.setAdapter(a);



        }catch (Exception e){

        }


    }

    @Override
    public void onClick(View v) {

        int id= v.getId();
        if(id== R.id.altar_btn_register){




            String cho=chofer.getSelectedItem().toString().split(" ")[0];


            String via= viaje.getSelectedItem().toString().split(" ")[0];;

            String usu= usuario.getSelectedItem().toString().split(" ")[0];
            String fe=fecha.getText().toString();
            String gas=ti;
            String imp=importe.getText().toString();
            String esta=est;







            createReporte(cho,via,usu,gas,imp,fe,esta);



        }
        if (id==R.id.fetch_btn_reporte){



            reportedosFragment  fragment= new reportedosFragment();

            Bundle bundle = new Bundle();
            bundle.putString("id_reporte_gasto", id_reporte_gasto.getText().toString().trim());
            getParentFragmentManager().setFragmentResult("key",bundle);




            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.fragmentr, fragment, "reportefragament");

            ft.addToBackStack(null);  //opcional, si quieres agregarlo a la pila
            ft.commit();




        }



    }

    private void createReporte(final String cho, final String via, final String usu, final String gas,final String imp, final String fe,final String esta) {

        StringRequest stringRequest=new StringRequest(
                Request.Method.POST,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getActivity(),"Reporte creado correctamente",Toast.LENGTH_SHORT).show();
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

                params.put("id_chofer", cho);
                params.put("id_viaje",via);
                params.put("id_usuario", usu);
                params.put("id_tipo_gasto", gas);
                params.put("importe",imp);
                params.put("fecha",fe);
                params.put("estatus",esta);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}