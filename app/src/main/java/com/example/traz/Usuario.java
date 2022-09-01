package com.example.traz;

public class Usuario {

    private String id_usuario;

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getId_tipo_usuario() {
        return id_tipo_usuario;
    }

    public void setId_tipo_usuario(String id_tipo_usuario) {
        this.id_tipo_usuario = id_tipo_usuario;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    private String id_tipo_usuario;
    private  String estatus;



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

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }



    private String  nombre;
    private String apellido_mat;
    private String apellido_pat;
    private  String puesto;
    private String user;
    private  String pwd;

    public  Usuario(){

    }
    public Usuario(String id_usuario, String id_tipo_usuario, String nombre, String apellido_pat, String apellido_mat, String puesto ,String user, String pwd, String estatus){
                        this.id_usuario=id_usuario;
                        this.id_tipo_usuario=id_tipo_usuario;
                        this.nombre=nombre;
                        this.apellido_pat=apellido_pat;
                        this.apellido_mat=apellido_mat;
                        this.puesto=puesto;
                        this.user=user;
                        this.pwd=pwd;
                        this.estatus=estatus;
    }

    @Override
    public String toString() {
        return id_usuario+" "+nombre+" "+apellido_pat+" "+apellido_mat;
    }
}
