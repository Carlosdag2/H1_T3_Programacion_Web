/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.empresa.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author carlo
 */
public class LoginServlet extends HttpServlet {

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
    PrintWriter out = response.getWriter();
    
    String url = "jdbc:mysql://localhost:3306/inventario_db";
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(url, "root", "");
        
        String correo = request.getParameter("correo");
        String contrasena = request.getParameter("contrasena");
        
        // Consulta para obtener la contraseña encriptada del usuario
        String consulta = "SELECT contrasena FROM usuarios WHERE correo = ?";
        ps = conn.prepareStatement(consulta);
        ps.setString(1, correo);
        rs = ps.executeQuery();
        
        if (rs.next()) {
            // Obtener la contraseña encriptada del usuario
            String contrasenaEncriptada = rs.getString("contrasena");
            
            // Verificar si la contraseña proporcionada coincide con la contraseña encriptada almacenada
            if (BCrypt.checkpw(contrasena, contrasenaEncriptada)) {
                // Si las contraseñas coinciden, redirigir al usuario a la página principal
                response.sendRedirect("InventaryServlet"); // Cambia "InventaryServlet" por la página que desees
            } else {
                // Si las contraseñas no coinciden, mostrar un mensaje de error
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Correo o contraseña incorrectos. Por favor, intenta de nuevo.');");
                out.println("window.location.href = 'index.html';"); // Cambia "index.html" por la página de inicio de sesión
                out.println("</script>");
            }
        } else {
            // Si no se encuentra un usuario con el correo proporcionado, mostrar un mensaje de error
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Usuario no encontrado. Por favor, verifica tu correo.');");
            out.println("window.location.href = 'index.html';"); // Cambia "index.html" por la página de inicio de sesión
            out.println("</script>");
        }
    } catch (ClassNotFoundException | SQLException ex) {
        Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
        // Cerrar recursos
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
        processRequest(request, response);
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
