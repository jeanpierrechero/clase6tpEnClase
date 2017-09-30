/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eduit.servlet;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.beans.PropertyVetoException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.sql.DataSource;

/**
 *
 * @author Marcelo
 */
public abstract class BaseServlet extends HttpServlet {

    private static final String CONNECTION_POOL = "connection_pool";
    private DataSource datasource;

    @Override
    public final void init() throws ServletException {
        final ServletContext servletContext = getServletContext();
        datasource = (DataSource) servletContext.getAttribute(CONNECTION_POOL);
        if (datasource == null) {
            try {
                datasource = createDataSource();
                servletContext.setAttribute(CONNECTION_POOL, datasource);
            } catch (PropertyVetoException ex) {
                ex.printStackTrace();
                throw new RuntimeException("error base de datos", ex);
            }
        }
        initResource();
    }

    public abstract void initResource();

    private ComboPooledDataSource createDataSource() throws PropertyVetoException {
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setDriverClass("org.gjt.mm.mysql.Driver"); //loads the jdbc driver            
        cpds.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/abm");
        cpds.setUser("root");
        cpds.setPassword("");
        cpds.setInitialPoolSize(5);
        cpds.setMinPoolSize(5);
        cpds.setAcquireIncrement(5);
        cpds.setMaxPoolSize(20);
        return cpds;
    }

    public DataSource getDatasource() {
        return datasource;
    }

}
