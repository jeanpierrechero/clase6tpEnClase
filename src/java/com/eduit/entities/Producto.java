/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eduit.entities;

/**
 *
 * @author Marcelo
 */
public class Producto {

    private Long id;
    private String presentacion;
    private Integer cantidad;
    private Float precio;
    private String descripcion;

    public Producto(Long id, String presentacion, Integer cantidad, Float precio, String descripcion) {
        this.id = id;
        this.presentacion = presentacion;
        this.cantidad = cantidad;
        this.precio = precio;
        this.descripcion = descripcion;
    }

    public Producto(String presentacion, Integer cantidad, Float precio, String descripcion) {
        this.presentacion = presentacion;
        this.cantidad = cantidad;
        this.precio = precio;
        this.descripcion = descripcion;
    }

    public Long getId() {
        return id;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public Float getPrecio() {
        return precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

  

    @Override
    public String toString() {
        return id + "- " + presentacion + " - " + cantidad + " - " + precio + " - " + descripcion ;
    }
   
    
    
   
    
}
