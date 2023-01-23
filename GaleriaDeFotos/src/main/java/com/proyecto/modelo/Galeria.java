/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.modelo;

import java.io.Serializable;
import java.util.ArrayList;


public class Galeria extends Album implements Serializable{
    private ArrayList<Album> albumes;
    public Galeria(String nombre, String descripcion) {
        super(nombre,descripcion);
        this.albumes= new ArrayList<>();
    }
    public Galeria(){
        this.albumes = new ArrayList<>();
    }

    public ArrayList<Album> getAlbumes() {
        return albumes;
    }

    public void setAlbumes(ArrayList<Album> albumes) {
        this.albumes = albumes;
    }
    public void crearAlbum(String nombre, String descripcion){
        albumes.add(new Album(nombre,descripcion));
    }
    

    
    
    
    
    
    
}
