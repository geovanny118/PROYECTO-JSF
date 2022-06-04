/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sysp;

import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author crist
 */
@ManagedBean
@ApplicationScoped

public class AcudienteBean {

    private int cedula;
    private String nombre;
    private int celular;
    private String correo;
    private String contrasena;
    private int carril;
    public ArrayList ListaHijos;
    public boolean divLlega, divListo;

    
    
    /**
     * Creates a new instance of AcudienteBean
     */

    public AcudienteBean(){
    
    }
            
            
    @PostConstruct
    public void init(){
        ListaHijos = OperacionesBD.getListaHijos(cedula);    
    }

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCelular() {
        return celular;
    }

    public void setCelular(int celular) {
        this.celular = celular;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public int getCarril() {
        return carril;
    }

    public void setCarril(int carril) {
        this.carril = carril;
    }


          
    public ArrayList listaHijos(){
        return ListaHijos;
    } 
     
    public boolean getDivLlega(){
        return divLlega;
    }
    
    public boolean getDivListo(){
        return divListo;
    }
    
    public String login(){
        
        AcudienteBean resultAcudiente = OperacionesBD.validaLoginAcudiente(cedula, contrasena);
        
        if(resultAcudiente.getNombre() != null){
            this.cedula = resultAcudiente.getCedula();
            this.nombre = resultAcudiente.getNombre();
            this.celular = resultAcudiente.getCelular();
            this.correo = resultAcudiente.getCorreo();
            this.contrasena = resultAcudiente.getContrasena();
            this.carril    = resultAcudiente.getCarril();
            this.ListaHijos = OperacionesBD.getListaHijos(resultAcudiente.getCedula());
            this.divLlega = true;
            this.divListo = false;
        }
        if(resultAcudiente.getNombre() != null){
            return "menuAcudiente.xhtml";
        }else{
            return "index.xhtml";    
        }
    
    }
    
    
    public String registraAcudiente(AcudienteBean nuevoAcudiente){
        
        return OperacionesBD.guardarAcudiente(nuevoAcudiente);
    
    }
    
    public void BorrarHijo(EstudianteBean nuevoHijo){
    
        OperacionesBD.BorrarHijo(nuevoHijo);
        
        //se actualiza lista
        this.ListaHijos = OperacionesBD.getListaHijos(this.cedula);
    }
    
    
    public void AgregarHijo(EstudianteBean nuevoHijo){
    
        OperacionesBD.AgregarHijo(nuevoHijo, this.cedula);
        
        //se actualiza lista
        this.ListaHijos = OperacionesBD.getListaHijos(this.cedula);
    
    }
    
    
    public void ActualizarCarril(int flag){
            
        OperacionesBD.ActualizarCarril(this.cedula, this.carril, flag);
        
        if(flag == 1){
            this.divListo = true;
            this.divLlega = false;
            System.out.println("Se deberia ocultar llegué y mostrar listo");
        }else{
            this.divLlega = true;
            this.divListo = false;
            System.out.println("Se deberia ocultar listo y mostrar llegué");
        }
    
    }
    
    
    
    
   
}
