/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eduit.repository;

import com.eduit.entities.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marcelo
 */
public class ProductoRepository {
    
    private Connection connection;

    public ProductoRepository(Connection connection) {
        this.connection = connection;
    }
    
    public Integer save(Producto p) {
        int inserted = 0;
        try {

            PreparedStatement preparedStatement = connection.prepareStatement("insert into productos (presentacion,cantidad,precio,descripcion) values (?,?,?,?)");
            preparedStatement.setString(1, p.getPresentacion());
            preparedStatement.setInt(2, p.getCantidad());
            preparedStatement.setFloat(3, p.getPrecio());
            preparedStatement.setString(4, p.getDescripcion());
            inserted = preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException("error consultando la base de datos", ex);
        }
        return inserted;
    }
    
    
     public Integer update (Producto p) {
        int inserted = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update productos set presentacion = ? ,cantidad = ? ,precio = ? ,descripcion = ? where id = ?");
            preparedStatement.setString(1, p.getPresentacion());
            preparedStatement.setInt(2, p.getCantidad());
            preparedStatement.setFloat(3, p.getPrecio());
            preparedStatement.setString(4, p.getDescripcion());
            preparedStatement.setLong(5, p.getId());
            inserted = preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("error consultando la base de datos", ex);
        }
        return inserted;
    }
     
    public Integer delete (Long id) {
        int inserted = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from productos where id = ?");
            preparedStatement.setLong(1,id);
            inserted = preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            throw new RuntimeException("error consultando la base de datos", ex);
        }
        return inserted;
    }
    
    
    
    public Producto getById (long id) {
        Producto producto = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from productos where id = ?");
            preparedStatement.setLong(1,id);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                String presentacion = rs.getString("presentacion");
                Integer cantidad = rs.getInt("cantidad");
                Float precio = rs.getFloat("precio");
                String nota = rs.getString("descripcion");
                producto = new Producto(id,presentacion, cantidad, precio, nota);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("error consultando la base de datos", ex);
        }
        return producto;
    }

    public List<Producto> getAll() {
        List<Producto> productos = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from productos");
            while(rs.next()){
                Long id = rs.getLong("id");
                String presentacion = rs.getString("presentacion");
                Integer cantidad = rs.getInt("cantidad");
                Float precio = rs.getFloat("precio");
                String nota = rs.getString("descripcion");
                Producto p = new Producto(id, presentacion, cantidad, precio, nota);
                productos.add(p);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("error consultando la base de datos", ex);
        }
        return productos;
    }
    
    
     public List<Producto> searchByDescripcion(String descripcion) {
        List<Producto> productos = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from productos p where p.descripcion like ?");
            preparedStatement.setString(1,"%"+descripcion+"%");
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Long id = rs.getLong("id");
                String presentacion = rs.getString("presentacion");
                Integer cantidad = rs.getInt("cantidad");
                Float precio = rs.getFloat("precio");
                String nota = rs.getString("descripcion");
                Producto p = new Producto(id, presentacion, cantidad, precio, nota);
                productos.add(p);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("error consultando la base de datos", ex);
        }
        return productos;
    }
    
    

}
