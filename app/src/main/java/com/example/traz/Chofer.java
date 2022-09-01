package com.example.traz;

public class Chofer {

    private String id_chofer;
    private String nombre;

    public String getId_chofer() {
        return id_chofer;
    }

    public void setId_chofer(String id_chofer) {
        this.id_chofer = id_chofer;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido_mat() {
        return apellido_mat;
    }

    public void setApellido_mat(String apellido_mat) {
        this.apellido_mat = apellido_mat;
    }

    public String getApellido_pat() {
        return apellido_pat;
    }

    public void setApellido_pat(String apellido_pat) {
        this.apellido_pat = apellido_pat;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    private String apellido_mat;
    private String apellido_pat;
    private String estatus;
    public Chofer(){

    }

    public Chofer(String id_chofer, String nombre, String apellido_pat, String apellido_mat, String estatus){

        this.id_chofer=id_chofer;
        this.nombre= nombre;
        this.apellido_pat=apellido_pat;
        this.apellido_mat=apellido_mat;
        this.estatus=estatus;
    }

    @Override
    public String toString() {
        return id_chofer+" "+nombre+" "+apellido_pat+" "+apellido_mat ;
    }
}
