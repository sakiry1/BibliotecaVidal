package com.example.breysi.biblio_virtual;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;


public class Usuario implements Parcelable {

    private String apellido;
    private String clave;
    private String codigo;
    private String curso;
    private String dni;
    private String email;
    private String fechaNacimiento;
    private String nombre;
    private String poblacion;
    private String provincia;
    private String telefono;

    public Usuario() {
    }

    protected Usuario(Parcel in) {
        this.apellido = in.readString();
        this.clave = in.readString();
        this.codigo = in.readString();
        this.curso = in.readString();
        this.dni = in.readString();
        this.email = in.readString();
        this.fechaNacimiento = in.readString();
        //this.fechaNacimiento=(java.util.Date)in.readSerializable();/////
        //this.fechaNacimiento = new Date(in.readLong());///read Long and pass to Date constructor to getDate
        this.nombre = in.readString();
        this.poblacion = in.readString();
        this.provincia = in.readString();
        this.telefono = in.readString();
    }

    public String getApellido() {
        return apellido;
    }

    public void setapellido(String apellido) {
        this.apellido = apellido;
    }

    public String getClave() {
        return clave;
    }

    public void setclave(String clave) {
        this.clave = clave;
    }

    public String getodigo() {
        return codigo;
    }

    public void setcodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCurso() {
        return curso;
    }

    public void setcurso(String curso) {
        this.curso = curso;
    }

    public String getDni() {
        return dni;
    }

    public void setdni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setemail(String email) {
        this.email = email;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setfechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setnombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setpoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setprovincia(String provincia) {
        this.provincia = provincia;
    }

    public String getTelefono() {
        return telefono;
    }

    public void settelefono(String telefono) {
        this.telefono = telefono;
    }

    public static final Parcelable.Creator<Usuario> CREATOR = new Parcelable.Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(apellido);
        dest.writeString(clave);
        dest.writeString(codigo);
        dest.writeString(curso);
        dest.writeString(dni);
        dest.writeString(email);
        dest.writeString(fechaNacimiento);
        dest.writeString(nombre);
        dest.writeString(poblacion);
        dest.writeString(provincia);
        dest.writeString(telefono);
    }
}
