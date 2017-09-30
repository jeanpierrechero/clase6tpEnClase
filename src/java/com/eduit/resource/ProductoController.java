/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eduit.resource;

import com.eduit.entities.Producto;
import com.eduit.repository.ProductoRepository;
import com.eduit.resource.response.Response;
import com.eduit.servlet.BaseServlet;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "pc", urlPatterns = "/producto", loadOnStartup = 1)
public class ProductoController extends BaseServlet {

    private ProductoRepository productoRepository;
    private Gson gson;
    private static final String APPLICATION_JSON = "application/json";
    private static final String CONTENT_TYPE = "Content-Type";

    @Override
    public void initResource() {
        try {
            productoRepository = new ProductoRepository(getDatasource().getConnection());
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("error base de datos", ex);
        }
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (validateHeaders(req, resp)) {
            List<Producto> productos = productoRepository.getAll();
            String json = gson.toJson(productos);
            resp.setHeader(CONTENT_TYPE, APPLICATION_JSON);
            resp.setStatus(resp.SC_OK);
            PrintWriter out = resp.getWriter();
            out.print(json);
            out.close();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (validateHeaders(req, resp)) {
            String json = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            try {
                Producto producto = gson.fromJson(json, Producto.class);
                productoRepository.save(producto);
                resp.setStatus(resp.SC_OK);
                Response response = new Response("saved");
                String jsonResponse = gson.toJson(response);
                PrintWriter out = resp.getWriter();
                out.print(jsonResponse);
                out.close();
            } catch (JsonSyntaxException ex) {
                resp.setStatus(resp.SC_BAD_REQUEST);
            }
        }
    }

    public boolean validateHeaders(HttpServletRequest req, HttpServletResponse resp) {
        String contentType = req.getHeader(CONTENT_TYPE);
        if (contentType == null || !contentType.equals(APPLICATION_JSON)) {
            resp.setStatus(resp.SC_BAD_REQUEST);
            return false;
        }
        return true;
    }

}
