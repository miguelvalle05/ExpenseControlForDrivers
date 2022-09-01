package com.example.traz;

public class Viaje {


    private String id_viaje;
    private String punto_salida;

    public String getId_viaje() {
        return id_viaje;
    }

    public void setId_viaje(String id_viaje) {
        this.id_viaje = id_viaje;
    }

    public String getPunto_salida() {
        return punto_salida;
    }

    public void setPunto_salida(String punto_salida) {
        this.punto_salida = punto_salida;
    }

    public String getPunto_llegada() {
        return punto_llegada;
    }

    public void setPunto_llegada(String punto_llegada) {
        this.punto_llegada = punto_llegada;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    private String punto_llegada;
    private String estatus;

    public Viaje(){

    }

    public Viaje(String id_viaje, String punto_salida, String punto_llegada, String estatus){

        this.id_viaje=id_viaje;
        this.punto_llegada=punto_llegada;
        this.punto_salida=punto_salida;
        this.estatus=estatus;

    }

    @Override
    public String toString() {
        return id_viaje+" "+punto_salida+" a "+punto_llegada;
    }
}
