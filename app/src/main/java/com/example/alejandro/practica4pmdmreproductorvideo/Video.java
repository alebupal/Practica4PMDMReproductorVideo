package com.example.alejandro.practica4pmdmreproductorvideo;

import android.graphics.Bitmap;



/**
 * Created by Alejandro on 21/01/2015.
 */
public class Video {
    private String nombre;
    private String peso;
    private String ruta;

    public Video() {
    }

    public Video(String peso, String ruta, String nombre) {
        this.peso = peso;
        this.ruta = ruta;
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }


    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }
}
