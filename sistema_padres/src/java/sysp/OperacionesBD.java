/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sysp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author crist
 */
@ManagedBean  
@ApplicationScoped
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
    

    static ArrayList getListaHijos(int cedula_padre) {
        ArrayList listaEstudiantes = new ArrayList();
        try {
            stmObj = getConnection().createStatement();
            resultSetObj= stmObj.executeQuery("select h.* from hijo h, padre p where h.cedula_padre = p.cedula and p.cedula = "+cedula_padre);
            while(resultSetObj.next()){
                EstudianteBean estObj = new EstudianteBean();
                estObj.setId(resultSetObj.getInt("id"));
                estObj.setNombre(resultSetObj.getString("nombre"));
                estObj.setSeccion(resultSetObj.getInt("seccion"));
                estObj.setCedula_padre(resultSetObj.getInt("cedula_padre"));
                listaEstudiantes.add(estObj);
                
            
            }
            
            System.out.println("Cantidad de hijos consultados"+listaEstudiantes.size());
            connObj.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(OperacionesBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return listaEstudiantes;
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
            
           // System.out.println("Cantidad de estudiantes consultados"+listaEstudiantes.size());
            connObj.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(OperacionesBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return listaEstudiantes;
    }

    static String guardarHijo(EstudianteBean nuevoEstudiante) {
       
        try {
            pstmt = getConnection().prepareStatement("insert into hijo (nombre, seccion, cedula_padre) value(?,?,?)");
            pstmt.setString(1, nuevoEstudiante.getNombre());
            pstmt.setInt(2, nuevoEstudiante.getSeccion());
            pstmt.setInt(3, nuevoEstudiante.getCedula_padre());
            pstmt.executeUpdate();
            connObj.close();
        } catch (SQLException ex) {
            Logger.getLogger(OperacionesBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "menuAcudiente.xhtml?faces-redirect=true";
    }
    
    
    static void BorrarHijo(EstudianteBean nuevoEstudiante){
    
        try {
            pstmt = getConnection().prepareStatement("delete from hijo where id=? and cedula_padre=?");
            pstmt.setInt(1, nuevoEstudiante.getId());
            pstmt.setInt(2, nuevoEstudiante.getCedula_padre());
            pstmt.executeUpdate();
            System.out.println("Se borra hijo exitosamente");
            connObj.close();
        } catch (SQLException ex) {
            Logger.getLogger(OperacionesBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    
    static void AgregarHijo(EstudianteBean nuevoEstudiante, int cedula_padre){
    
        try {
            pstmt = getConnection().prepareStatement("insert into hijo (nombre, seccion, cedula_padre) value(?,?,?)");
            pstmt.setString(1, nuevoEstudiante.getNombre());
            pstmt.setInt(2, nuevoEstudiante.getSeccion());
            pstmt.setInt(3, cedula_padre);
            pstmt.executeUpdate();
            System.out.println("Se agrega hijo exitosamente");
            connObj.close();
        } catch (SQLException ex) {
            Logger.getLogger(OperacionesBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    static void ActualizarCarril(int cedula, int carril, int flag){
    
        if(flag == 1){
                try {
                pstmt = getConnection().prepareStatement("update padre set carril =? where cedula =?");
                pstmt.setInt(1, carril);
                pstmt.setInt(2, cedula);
                pstmt.executeUpdate();
                System.out.println("Se actualiza carril exitosamente a "+carril);
                connObj.close();
            } catch (SQLException ex) {
                Logger.getLogger(OperacionesBD.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
                try {
                pstmt = getConnection().prepareStatement("update padre set carril = null where cedula =?");
                pstmt.setInt(1, cedula);
                pstmt.executeUpdate();
                System.out.println("Se actualiza carril exitosamente a "+0);
                connObj.close();
            } catch (SQLException ex) {
                Logger.getLogger(OperacionesBD.class.getName()).log(Level.SEVERE, null, ex);
            }

        }    

    }
    
    
    static String guardarAcudiente(AcudienteBean nuevoAcudiente) {
       
        try {
            pstmt = getConnection().prepareStatement("insert into padre (cedula, nombre, celular, correo, contrasena) value(?,?,?,?,?)");
            pstmt.setInt(1, nuevoAcudiente.getCedula());
            pstmt.setString(2, nuevoAcudiente.getNombre());
            pstmt.setInt(3, nuevoAcudiente.getCelular());
            pstmt.setString(4, nuevoAcudiente.getCorreo());
            pstmt.setString(5, nuevoAcudiente.getContrasena());
            pstmt.executeUpdate();
            connObj.close();
        } catch (SQLException ex) {
            Logger.getLogger(OperacionesBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "index.xhtml?faces-redirect=true";
    }
    
        
   static AcudienteBean validaLoginAcudiente(int cedula, String contrasena){
   
   
       AcudienteBean objAcudiente = new AcudienteBean();
        try {
            stmObj = getConnection().createStatement();
            resultSetObj= stmObj.executeQuery("select p.* from padre p where p.cedula = "+cedula+" and p.contrasena = '"+contrasena+"'");
            while(resultSetObj.next()){
                objAcudiente.setCedula(resultSetObj.getInt("cedula"));
                objAcudiente.setNombre(resultSetObj.getString("nombre"));
                objAcudiente.setCelular(resultSetObj.getInt("celular"));
                objAcudiente.setCorreo(resultSetObj.getString("correo"));
                objAcudiente.setContrasena(resultSetObj.getString("contrasena"));
                objAcudiente.setCarril(resultSetObj.getInt("carril"));
            }
  
            System.out.println("Se realiza login exitosamente");
            connObj.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(OperacionesBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return objAcudiente;
   
   }     
        
    
}