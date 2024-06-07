package com.example.parcialtercercorte.clases;

import java.io.Serializable;

public class Character implements Serializable {
    private String imagen;
    private String nombre;
    private String detalle;
    private String id;

    public Character(String imagen, String nombre, String detalle, String id) {
        this.imagen = imagen;
        this.nombre = nombre;
        this.detalle = detalle;
        this.id = id;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
