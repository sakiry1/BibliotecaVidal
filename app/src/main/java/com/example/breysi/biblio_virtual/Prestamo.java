package com.example.breysi.biblio_virtual;

import android.os.Parcel;
import android.os.Parcelable;


public class Prestamo implements Parcelable {
    private String fecha_devolución;
    private String fecha_prestamo;

    private String id_libro;
    private String id_usuario;
    public Libro libro=null;

    public Prestamo() {
    }
    private Prestamo(Parcel in) {
        this.fecha_devolución =/*new Date*/(in.readString());
        this.fecha_prestamo = /*new Date*/(in.readString());

        this.id_libro = (in.readString());
        this.id_usuario=(in.readString());

    }

    public String getFecha_prestamo() {
        return fecha_prestamo;
    }

    public void setfecha_prestamo(String fechaPrestamo) {
        this.fecha_prestamo = fechaPrestamo;
    }

    public String getFecha_devolución() {
        return fecha_devolución;
    }

    public void setfecha_devolución(String fecha_devolución) {
        this.fecha_devolución = fecha_devolución;
    }

    public Libro getlibro() {
        return libro;
    }

    public void setlibro(Libro libro) {
        this.libro = libro;
    }

    public String getId_libro() {
        return id_libro;
    }

    public void setid_libro(String id_libro) {
        this.id_libro = id_libro;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setid_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }



    public static final Creator<Prestamo> CREATOR = new Creator<Prestamo>() {
        @Override
        public Prestamo createFromParcel(Parcel in) {
            return new Prestamo(in);
        }

        @Override
        public Prestamo[] newArray(int size) {
            return new Prestamo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fecha_devolución);
        dest.writeString(fecha_prestamo);

        dest.writeString(id_libro);
        dest.writeString(id_usuario);
    }


}