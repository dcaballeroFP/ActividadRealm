package com.example.actividadrealm.Model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Persona extends RealmObject {
    private String nombre;
    private String apellidos;
    private String nombreCompleto;
    private int edad;
    private boolean sexo;

    @PrimaryKey
    private int identificador;

    @Override
    public String toString() {
        return "Persona{" +
                ",id: "+identificador+'\''+
                //----------------------------------------------------------------------------------------------
                //VERSION 1
                ", nombre completo"+nombreCompleto+'\''+
                //----------------------------------------------------------------------------------------------
                //VERSION 0
                //"nombre='" + nombre + '\'' +
                //", apellidos='" + apellidos + '\'' +
                //----------------------------------------------------------------------------------------------
                ", edad=" + edad +
                ", sexo=" + sexo +
                '}';
    }

    public Persona() {
        //----------------------------------------------------------------------------------------------
        //VERSION 0
        //nombre = "";
        //apellidos = "";
        //----------------------------------------------------------------------------------------------
        //VERSION 1
        nombreCompleto = "";
        //----------------------------------------------------------------------------------------------
        edad = 0;
        sexo=true;
    }
    //----------------------------------------------------------------------------------------------
    //VERSION 1
    public String getNombreCompleto(){return nombreCompleto;}
    public void setNombreCompleto(String nombreCompleto){this.nombreCompleto = nombreCompleto;}
    //----------------------------------------------------------------------------------------------
    //VERSION 0
    //public String getNombre() { return nombre; }
    //public void setNombre(String nombre) { this.nombre = nombre; }
    //public String getApellidos() {return apellidos;}
    //public void setApellidos(String apellidos) {this.apellidos = apellidos;}
    //----------------------------------------------------------------------------------------------
    public int getEdad() {  return edad; }
    public void setEdad(int edad) { this.edad = edad; }
    public boolean isSexo() {  return sexo; }
    public void setSexo(boolean sexo) { this.sexo = sexo; }
    public int getId() { return identificador; }
    public void setId(int id) { this.identificador = id; }
}