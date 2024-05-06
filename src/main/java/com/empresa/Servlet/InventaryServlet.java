/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.empresa.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author carlo
 */
public class InventaryServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet InventaryServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet InventaryServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = "jdbc:mysql://localhost:3306/inventario_db";
        String user = "root";
        String password = "";
    
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT * FROM inventario";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {
                response.setContentType("text/html;charset=UTF-8");
                try (PrintWriter out = response.getWriter()) {
                    out.println("<!DOCTYPE html>");
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>Productos en inventario</title>");
                    out.println("<meta charset=\"UTF-8\">");
                    out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
                    out.println("<link href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css\" rel=\"stylesheet\">");
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<div class=\"container mt-5\">");
                    out.println("<h1>Productos en inventario</h1>");
                    out.println("<table class=\"table table-striped\">"); // Añadir la clase table-striped para un estilo de filas alternadas
                    out.println("<thead class=\"thead-dark\">"); // Añadir la clase thead-dark para un encabezado oscuro
                    out.println("<tr>");
                    out.println("<th>ID</th>");
                    out.println("<th>Nombre</th>");
                    out.println("<th>Cantidad</th>");
                    out.println("<th>Precio</th>");
                    out.println("<th>Fecha Creación</th>");
                    out.println("<th>Fecha Modificación</th>");
                    out.println("</tr>");
                    out.println("</thead>");
                    out.println("<tbody>");
                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String nombre = rs.getString("nombre");
                        int cantidad = rs.getInt("cantidad");
                        double precio = rs.getDouble("precio");
                        Timestamp fechaCreacion = rs.getTimestamp("fecha_creacion");
                        Timestamp fechaModificacion = rs.getTimestamp("fecha_modificacion");
                        out.println("<tr>");
                        out.println("<td>" + id + "</td>");
                        out.println("<td>" + nombre + "</td>");
                        out.println("<td>" + cantidad + "</td>");
                        out.println("<td>" + precio + "</td>");
                        out.println("<td>" + fechaCreacion + "</td>");
                        out.println("<td>" + fechaModificacion + "</td>");
                        out.println("</tr>");
                    }
                    out.println("</tbody>");
                    out.println("</table>");
                    out.println("</div>");
                    out.println("</body>");
                    out.println("</html>");
                }
            }
        }
    } catch (ClassNotFoundException | SQLException ex) {
        Logger.getLogger(InventaryServlet.class.getName()).log(Level.SEVERE, null, ex);
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error de Base de Datos");
    }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
