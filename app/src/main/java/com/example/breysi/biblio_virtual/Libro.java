package com.example.breysi.biblio_virtual;

import android.os.Parcel;
import android.os.Parcelable;


class Libro implements Parcelable {
    private String autor;
    private String añoEdicion;
    private String editorial;
    private String genero;
    private String id;
    private String idioma;
    private String numeroPagina;
    private String portada;
    private String titulo;
    private String ISBN;

    public Libro() {
    }

    public Libro(Parcel in) {

        this.autor = in.readString();
        this.añoEdicion = in.readString();
        this.editorial = in.readString();
        this.genero = in.readString();
        this.id = in.readString();
        this.idioma = in.readString();
        this.numeroPagina = in.readString();
        this.portada = in.readString();
        this.titulo = in.readString();
        this.ISBN = in.readString();
    }

    public String getNumeroPagina() {
        return numeroPagina;
    }

    public void setNumeroPagina(String numeroPagina) {
        this.numeroPagina = numeroPagina;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getAñoEdicion() {
        return añoEdicion;
    }

    public void setAñoEdicion(String añoEdicion) {
        this.añoEdicion = añoEdicion;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getNpaginas() {
        return numeroPagina;
    }

    public void setNpaginas(String numeroPagina) {
        this.numeroPagina = numeroPagina;
    }

    public String getPortada() {
        return portada;
    }

    public void setPortada(String portada) {
        this.portada = portada;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static final Parcelable.Creator<Libro> CREATOR = new Parcelable.Creator<Libro>() {
        @Override
        public Libro createFromParcel(Parcel in) {
            return new Libro(in);
        }

        @Override
        public Libro[] newArray(int size) {
            return new Libro[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(autor);
        dest.writeString(añoEdicion);
        dest.writeString(editorial);
        dest.writeString(genero);
        dest.writeString(id);
        dest.writeString(idioma);
        dest.writeString(numeroPagina);
        dest.writeString(portada);
        dest.writeString(titulo);
    }
}