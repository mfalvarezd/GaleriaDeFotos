/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Fotografia implements Serializable {
    private Album album;
    private String descripcion;
    private String lugar;
    private String fecha;
    private String path;
    private String nombre;
    private String comentarios;
    private ArrayList<Persona> personaEnFoto;

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
    
    
            

    public Fotografia(Album album, String descripcion, String lugar, String fecha, String path, String nombre) {
        this.album = album;
        this.descripcion = descripcion;
        this.lugar = lugar;
        this.fecha = fecha;
        this.path = path;
        this.nombre = nombre;
        this.personaEnFoto = new ArrayList<>();
    }

    public Fotografia(Album album, String path, String nombre) {
        this.album = album;
        this.path = path;
        this.nombre = nombre;
        this.personaEnFoto = new ArrayList<>();
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPersonaEnFoto(ArrayList<Persona> personaEnFoto) {
        this.personaEnFoto = personaEnFoto;
    }

    public Album getAlbum() {
        return album;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getLugar() {
        return lugar;
    }

    public String getFecha() {
        return fecha;
    }

    public String getPath() {
        return path;
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<Persona> getPersonaEnFoto() {
        return personaEnFoto;
    }
    
    public void anadirPersonas(Persona p){
        this.personaEnFoto.add(p);
    }    

    @Override
    public String toString() {
        return "Fotografia{" + "album=" + album + ", path=" + path + ", nombre=" + nombre + '}';
    }
    
        
    
}
