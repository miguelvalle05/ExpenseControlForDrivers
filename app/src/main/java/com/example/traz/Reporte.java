package com.example.traz;

public class Reporte {
    private String id_reporte_gasto;

    public Reporte(String id_reporte_gasto, String id_chofer, String id_viaje, String id_usuario, String id_tipo_gasto, String importe, String fecha, String estatus) {
        this.id_reporte_gasto = id_reporte_gasto;
        this.id_chofer = id_chofer;
        this.id_viaje = id_viaje;
        this.id_usuario = id_usuario;
        this.id_tipo_gasto = id_tipo_gasto;
        this.importe = importe;
        this.fecha = fecha;
        this.estatus = estatus;
    }

    private String id_chofer;
    private String id_viaje;
    private String id_usuario;
    private String id_tipo_gasto;
    private String importe;
    private String fecha;
    private String estatus;

    public String getId_reporte_gasto() {
        return id_reporte_gasto;
    }

    public void setId_reporte_gasto(String id_reporte_gasto) {
        this.id_reporte_gasto = id_reporte_gasto;
    }

    public String getId_chofer() {
        return id_chofer;
    }

    public void setId_chofer(String id_chofer) {
        this.id_chofer = id_chofer;
    }

    public String getId_viaje() {
        return id_viaje;
    }

    public void setId_viaje(String id_viaje) {
        this.id_viaje = id_viaje;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getId_tipo_gasto() {
        return id_tipo_gasto;
    }

    public void setId_tipo_gasto(String id_tipo_gasto) {
        this.id_tipo_gasto = id_tipo_gasto;
    }

    public String getImporte() {
        return importe;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public Reporte(){

    }
}
