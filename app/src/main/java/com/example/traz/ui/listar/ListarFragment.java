package com.example.traz.ui.listar;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.traz.R;
import com.example.traz.Reporte;
import com.example.traz.ui.adapter.ReporteAdapter;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;


public class ListarFragment extends Fragment implements Response.Listener<JSONObject>,  Response.ErrorListener, SearchView.OnQueryTextListener {

    SearchView svRe;
    RecyclerView recyclerReporte;
    ArrayList<Reporte> listaReporte;
    ProgressDialog progress;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    ReporteAdapter adapter;

    public ListarFragment(){

    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_listar, container, false);

        listaReporte=new ArrayList<>();
        svRe= vista.findViewById(R.id.svReporte);

        recyclerReporte= vista.findViewById(R.id.idRecycler);
        recyclerReporte.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerReporte.setHasFixedSize(true);

        svRe.setOnQueryTextListener(this);


        request= Volley.newRequestQueue(getContext());

        cargarWebServices();
            





        return vista;
    }

    private void cargarWebServices() {

        progress = new ProgressDialog(getContext());
        progress.setMessage("Consultando........");
        progress.show();

        String url="http://10.5.2.124/login/listar.php";
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);

    }



    @Override
    public void onErrorResponse(VolleyError error) {


        Toast.makeText(getContext(),"No se pudo consultar"+error.toString(),Toast.LENGTH_SHORT).show();
        Log.i("ERROR",error.toString());
        Log.d("ERROR: ", error.toString());
        progress.hide();

    }

    @Override
    public void onResponse(JSONObject response) {
       // Toast.makeText(getContext(),"Mensaje: "+response,Toast.LENGTH_SHORT).show();
        Reporte reporte= null;

        JSONArray json=response.optJSONArray("reporte");

        try{
            for (int i=0;i<json.length();i++){

                reporte=new Reporte();
                JSONObject jsonObject=null;
                jsonObject=json.getJSONObject(i);

                reporte.setId_reporte_gasto(jsonObject.optString("id_reporte_gasto"));
                reporte.setId_chofer(jsonObject.optString("chofer"));
                reporte.setId_viaje(jsonObject.optString("viaje"));
                reporte.setId_usuario(jsonObject.optString("usuario"));
                reporte.setId_tipo_gasto(jsonObject.optString("gasto"));
                reporte.setImporte(jsonObject.optString("importe"));
                reporte.setFecha(jsonObject.optString("fecha"));
                reporte.setEstatus(jsonObject.optString("estatus"));
                listaReporte.add(reporte);





            }

            progress.hide();
            adapter=new ReporteAdapter(listaReporte);
            recyclerReporte.setAdapter(adapter);

        }
        catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(getContext(), "Nose a podido establecer conexion con el servidor"+
                    " "+response, Toast.LENGTH_LONG).show();
            progress.hide();

        }

    }

    @Override
    public boolean onQueryTextSubmit(String s) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        adapter.filtrado(s);

        return false;
    }
}
