package com.example.traz.ui.adapter;

import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traz.R;
import com.example.traz.Reporte;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReporteAdapter  extends RecyclerView.Adapter<ReporteAdapter.ReporteHolder> {

    List<Reporte> listaReporte;

    List<Reporte> listaOriginal;
    public ReporteAdapter(List<Reporte> listaReporte) {

        this.listaReporte = listaReporte;
        listaOriginal=  new ArrayList<>();
        listaOriginal.addAll(listaReporte);
    }




    @Override
    public ReporteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.reporte_list,parent,false);
        RecyclerView.LayoutParams layoutParams= new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        vista.setLayoutParams(layoutParams);


        return new ReporteHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ReporteHolder holder, int position) {
        holder.txtReporte.setText(listaReporte.get(position).getId_reporte_gasto());
        holder.txtChofer.setText(listaReporte.get(position).getId_chofer());
        holder.txtViaje.setText(listaReporte.get(position).getId_viaje());
        holder.txtUsuario.setText(listaReporte.get(position).getId_usuario());
        holder.txtGasto.setText(listaReporte.get(position).getId_tipo_gasto());
        holder.txtImporte.setText("$"+listaReporte.get(position).getImporte());
        holder.txtFecha.setText(listaReporte.get(position).getFecha());
        holder.txtEstatus.setText(listaReporte.get(position).getEstatus());

        if ( listaReporte.get(position).getEstatus().equals("0")){
            holder.txtEstatus.setText("Revisado");
            holder.itemView.setBackgroundColor(Color.parseColor("#1DE9B6"));

        }else{
            holder.itemView.setBackgroundColor(Color.parseColor("#FF8A80"));
            holder.txtEstatus.setText("No revisado");



        }


    }

    public void filtrado(String txtBuscar){
        int longitud = txtBuscar.length();

        if(longitud==0){
            listaReporte.clear();
            listaReporte.addAll(listaOriginal);

        }
        else{

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {


                List<Reporte> collecion = listaReporte.stream().
                        filter(i -> i.getId_chofer().toLowerCase().contains(txtBuscar.toLowerCase()))
                        .collect(Collectors.toList());
                listaReporte.clear();
                listaReporte.addAll(collecion);
            }
            else{

                for (Reporte r:listaOriginal) {
                    if (r.getId_chofer().toLowerCase().contains(txtBuscar.toLowerCase())){
                        listaReporte.add(r);
                    }
                    
                }

            }
            }

        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return listaReporte.size();
    }

    public class ReporteHolder extends RecyclerView.ViewHolder {
        TextView txtReporte, txtChofer, txtViaje, txtUsuario, txtGasto, txtImporte, txtFecha, txtEstatus;

        public ReporteHolder(View itemView) {
            super(itemView);

            txtReporte= (TextView) itemView.findViewById(R.id.txtReporte);
            txtChofer= (TextView) itemView.findViewById(R.id.txtChofer);
            txtViaje= (TextView) itemView.findViewById(R.id.txtViaje);
            txtUsuario= (TextView) itemView.findViewById(R.id.txtUsuario);
            txtGasto = (TextView) itemView.findViewById(R.id.txtGasto);
            txtImporte= (TextView) itemView.findViewById(R.id.txtImporte);
            txtFecha= (TextView) itemView.findViewById(R.id.txtFecha);
            txtEstatus= (TextView) itemView.findViewById(R.id.txtEstatus);
        }
    }
}
