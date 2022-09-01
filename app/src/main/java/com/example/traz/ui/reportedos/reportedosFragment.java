package com.example.traz.ui.reportedos;



import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
import com.example.traz.Reporte;
import com.example.traz.Usuario;
import com.example.traz.Viaje;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import fr.ganfra.materialspinner.MaterialSpinner;

public class reportedosFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener, View.OnClickListener{

    private AsyncHttpClient cliente;
    private int dia, mes,ano;
    TextInputEditText importe;

    TextInputEditText id_reporte_gast;

    MaterialButton edit;
    MaterialButton delete;
    EditText fecha;
    Button btnFecha;
    MaterialSpinner estatus;
    MaterialSpinner tipo;
    MaterialSpinner chofer;
    MaterialSpinner usuario;
    MaterialSpinner viaje;
    String ti;
    String est;

    ProgressDialog progreso;
    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;

    public reportedosFragment(){

    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.reportedos_fragment, container, false);

        getObjects(root);
        getParentFragmentManager().setFragmentResultListener("key", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull @NotNull String requestKey, @NonNull @NotNull Bundle bundle) {

                String id_reporte_gasto= bundle.getString("id_reporte_gasto");
                id_reporte_gast.setText(id_reporte_gasto);
                String URL ="http://10.5.2.121/login/fetchreporte.php?id_reporte_gasto="+ id_reporte_gasto;

                cargarWebservices(URL);
            }
        });


        requestQueue= Volley.newRequestQueue(getContext());
        delete.setOnClickListener(this);
        edit.setOnClickListener(this);


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
        ArrayList<Chofer> lista = new ArrayList<Chofer>();
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


    private void getObjects(View view){

        id_reporte_gast= view.findViewById(R.id.alta_inp_idrd);
        fecha= view.findViewById(R.id.fechaIngresoCreard);
        btnFecha= view.findViewById(R.id.btnDateCreard);
        tipo= view.findViewById(R.id.sprdTipog);
        estatus=view.findViewById(R.id.sprdEstatus);
        chofer=view.findViewById(R.id.sprdChofer);
        usuario= view.findViewById(R.id.sprdUsario);
        viaje=view.findViewById(R.id.sprdViaje);
        importe=view.findViewById(R.id.altad_inp_importe);
        edit=view.findViewById(R.id.edit_btn_reporte);
        delete=view.findViewById(R.id.delete_btn_reporte);






    }
    public void cargarWebservices(String URL){


        progreso= new ProgressDialog(getContext());
        progreso.setMessage("Consultando.......");
        progreso.show();




        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,URL,null,this,this);
        requestQueue.add(jsonObjectRequest);






    }

    @Override
    public void onClick(View v) {


        int id= v.getId();
        if(id== R.id.edit_btn_reporte) {

            String idRe= id_reporte_gast.getText().toString();
            String cho=chofer.getSelectedItem().toString().split(" ")[0];


            String via= viaje.getSelectedItem().toString().split(" ")[0];;

            String usu= usuario.getSelectedItem().toString().split(" ")[0];
            String fe=fecha.getText().toString();
            String gas=ti;
            String imp=importe.getText().toString();
            String esta=est;




            updateReporte(idRe, cho,via,usu,gas,imp,fe,esta);



        }

        if(id== R.id.delete_btn_reporte) {


            String idRe= id_reporte_gast.getText().toString();
            removeReporte(idRe);




        }









    }

    public void removeReporte(String idRe){
        String URL ="http://10.5.2.121/login/deletereporte.php";
        StringRequest stringRequest= new StringRequest(
                Request.Method.POST,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {









                        Toast.makeText(getActivity(),"Reporte Eliminado",Toast.LENGTH_SHORT).show();

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
                params.put("id_reporte_gasto",idRe);


                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

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
      //  Toast.makeText(getContext(),"Mensaje: "+response,Toast.LENGTH_SHORT).show(); //Mnesaje para validar que nos este regresando datos el WEBSERVICE

        Reporte miReporte= new Reporte();
        JSONArray json= response.optJSONArray("reporte");
        JSONObject jsonObject= null;
        try{
            jsonObject=json.getJSONObject(0);

            miReporte.setId_chofer(jsonObject.optString("id_chofer"));
            miReporte.setId_viaje(jsonObject.optString("id_viaje"));
            miReporte.setId_usuario(jsonObject.optString("id_usuario"));
            miReporte.setId_tipo_gasto(jsonObject.optString("id_tipo_gasto"));
            miReporte.setImporte(jsonObject.optString("importe"));
            miReporte.setFecha(jsonObject.optString("DATE_FORMAT(fecha,'%d/%m/%Y')"));
            miReporte.setEstatus(jsonObject.optString("estatus"));



        }catch(JSONException e){
            e.printStackTrace();

        }
        String opcion1 = String.valueOf(miReporte.getId_tipo_gasto());
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


        importe.setText(miReporte.getImporte());
        fecha.setText(miReporte.getFecha());






        String opcion = String.valueOf(miReporte.getEstatus());
        Integer op;
        if (opcion.equals("1")){
            op=2;
            estatus.setSelection(op);

        }else {
            op=3;
            estatus.setSelection(op);
        }

        String valorc = String.valueOf(miReporte.getId_chofer());


        chofer.setSelection(getIndex(chofer, valorc));

        String valorv = String.valueOf(miReporte.getId_viaje());


        viaje.setSelection(getIndex(viaje, valorv));

        String valoru = String.valueOf(miReporte.getId_usuario());


        usuario.setSelection(getIndex(usuario, valoru));












    }
    // buscar dato en especifico para spinner

    private int getIndex(MaterialSpinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().split(" ")[0].equalsIgnoreCase(myString)){
                return i+1;
            }
        }

        return 0;
    }

    private void updateReporte(String idRe,final String cho, final String via, final String usu, final String gas,final String imp, final String fe,final String esta) {
        String URL ="http://10.5.2.121/login/editreporte.php";
        StringRequest stringRequest=new StringRequest(
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
                params.put("id_reporte_gasto",idRe);

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