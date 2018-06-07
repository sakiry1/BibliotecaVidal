package com.example.breysi.biblio_virtual;



public class Genero {
    private String id_genero;
    private String nombre;

    public Genero() {

    }

    public Genero(String id_genero, String nombre) {
        this.id_genero = id_genero;
        this.nombre = nombre;
    }

    public String getId_genero() {
        return id_genero;
    }

    public void setId_genero(String id_genero) {
        this.id_genero = id_genero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
