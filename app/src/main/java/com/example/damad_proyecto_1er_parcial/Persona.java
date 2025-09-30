package com.example.damad_proyecto_1er_parcial;

import android.os.Parcel;
import android.os.Parcelable;

public class Persona implements Parcelable {
    private String nombre;
    private int edad;
    private String correo;

    // Constructor
    public Persona(String nombre, int edad, String correo) {
        this.nombre = nombre;
        this.edad = edad;
        this.correo = correo;
    }

    // Getters
    public String getNombre() { return nombre; }
    public int getEdad() { return edad; }
    public String getCorreo() { return correo; }

    // Setters
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setEdad(int edad) { this.edad = edad; }
    public void setCorreo(String correo) { this.correo = correo; }

    // Parcelable
    protected Persona(Parcel in) {
        nombre = in.readString();
        edad = in.readInt();
        correo = in.readString();
    }

    public static final Creator<Persona> CREATOR = new Creator<Persona>() {
        @Override
        public Persona createFromParcel(Parcel in) {
            return new Persona(in);
        }

        @Override
        public Persona[] newArray(int size) {
            return new Persona[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeInt(edad);
        dest.writeString(correo);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}

