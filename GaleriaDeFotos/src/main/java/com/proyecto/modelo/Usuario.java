/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.modelo;


public class Usuario {
    private String usuario;
    private String password;
    private Galeria galeria;
    private Persona persona;

    public Usuario(String usuario, String password,Persona persona) {
        this.usuario = usuario;
        this.password = password;
        this.persona = persona;
        this.galeria = new Galeria();
    }

    public Persona getPersona() {
        return persona;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Galeria getGaleria() {
        return galeria;
    }

    public void setGaleria(Galeria galeria) {
        this.galeria = galeria;
    }
    
    
    
}
