/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.visualyou;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author sean
 */
public class BackingBeanUtils {

    private DataSource dataSource;
    private Connection connx;

    //Getting Postgresql Connection
    public Connection getPostgresCon() {
        String jndi_name = "postgresDS";
        Context initContext;
        try {
            initContext = new InitialContext();
            Context envCtx = (Context) initContext.lookup("java:comp/env");
            dataSource = (DataSource) envCtx.lookup(jndi_name);
            connx = dataSource.getConnection();
            
        } catch (NamingException | SQLException ex) {
            System.out.println(ex);
        }
        return connx;
    }

}
