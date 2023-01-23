/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.modelo;

import java.io.Serializable;
import java.util.ArrayList;


public class Album implements Comparable<Album>,Serializable{
    private String nombre;
    private String descripcion;
    private ArrayList<Fotografia> fotografias;

    public Album(String nombre, String descripcion, ArrayList<Fotografia> fotografias) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fotografias = fotografias = new ArrayList<>();
    } 
    public Album(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fotografias = fotografias = new ArrayList<>();
        
    }
     public Album() {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fotografias = fotografias = new ArrayList<>();
        
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ArrayList<Fotografia> getFotografias() {
        return fotografias;
    }

    public void setFotografias(ArrayList<Fotografia> fotografias) {
        this.fotografias = fotografias;
    }
    public int compareTo(Album a){
        return a.descripcion.compareTo(this.descripcion);
    }


    public void moverFotografia(Fotografia f, Album a) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void organizarFotografias() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


    public void slideShowFotografias() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


    public void mostrarFotografias() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void anadirFotografias(Fotografia f) {
        this.fotografias.add(f);
    }
    public void eliminarImagen(Fotografia f){
        this.fotografias.remove(f);
    }
    
        
}
