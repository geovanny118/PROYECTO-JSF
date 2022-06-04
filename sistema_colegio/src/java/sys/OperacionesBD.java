/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sys;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author crist
 */
class OperacionesBD {
    
    
    public static Statement stmObj;
    public static Connection connObj;
    public static ResultSet resultSetObj;
    public static PreparedStatement pstmt;
    
    
    public static Connection getConnection() throws SQLException{
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String db_url = "jdbc:mysql://localhost:3306/proyecto_final",db_username="root", db_password="";
            connObj = DriverManager.getConnection(db_url, db_username, db_password);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(OperacionesBD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex){
            Logger.getLogger(OperacionesBD.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } catch (InstantiationException ex) {
            Logger.getLogger(OperacionesBD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(OperacionesBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return connObj;
    }
    

    static ArrayList getListaEstudiantes(int carril) {
        ArrayList listaEstudiantes = new ArrayList();
        try {
            stmObj = getConnection().createStatement();
            resultSetObj= stmObj.executeQuery("select h.* from hijo h, padre p where h.cedula_padre = p.cedula and p.carril = "+carril);
            while(resultSetObj.next()){
                EstudianteBean estObj = new EstudianteBean();
                estObj.setId(resultSetObj.getInt("id"));
                estObj.setNombre(resultSetObj.getString("nombre"));
                estObj.setSeccion(resultSetObj.getInt("seccion"));
                estObj.setCedula_padre(resultSetObj.getInt("cedula_padre"));
                listaEstudiantes.add(estObj);
                
            
            }
            
            System.out.println("Cantidad de estudiantes consultados"+listaEstudiantes.size());
            connObj.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(OperacionesBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return listaEstudiantes;
    }

    static String guardarEstudiante(EstudianteBean nuevoEstudiante) {
      /* 
        try {
            pstmt = getConnection().prepareStatement("insert into student (cedula, nombre, correo, contrasena, sexo, direccion) value(?,?,?,?,?,?)");
            pstmt.setInt(1, nuevoEstudiante.getCedula());
            pstmt.setString(2, nuevoEstudiante.getNombre());
            pstmt.setString(3, nuevoEstudiante.getCorreo());
            pstmt.setString(4, nuevoEstudiante.getContrasena());
            pstmt.setString(5, nuevoEstudiante.getSexo());
            pstmt.setString(6, nuevoEstudiante.getDireccion());
            pstmt.executeUpdate();
            connObj.close();
        } catch (SQLException ex) {
            Logger.getLogger(OperacionesBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        */
        return "index.xhtml?faces-redirect=true";
    }
    
}