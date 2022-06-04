/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sys;

import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author crist
 */
@ManagedBean
@RequestScoped
public class EstudianteBean {

    private int id;
    private String nombre;
    private int seccion;
    private int cedula_padre;
    public ArrayList ListaEstudiantesC1, ListaEstudiantesC2, ListaEstudiantesC3;
    
    /**
     * Creates a new instance of EstudianteBean
     */
    public EstudianteBean() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getSeccion() {
        return seccion;
    }

    public void setSeccion(int seccion) {
        this.seccion = seccion;
    }

    public int getCedula_padre() {
        return cedula_padre;
    }

    public void setCedula_padre(int cedula_padre) {
        this.cedula_padre = cedula_padre;
    }

    public ArrayList getListaEstudiantesC1() {
        return ListaEstudiantesC1;
    }

    public void setListaEstudiantesC1(ArrayList ListaEstudiantesC1) {
        this.ListaEstudiantesC1 = ListaEstudiantesC1;
    }

    public ArrayList getListaEstudiantesC2() {
        return ListaEstudiantesC2;
    }

    public void setListaEstudiantesC2(ArrayList ListaEstudiantesC2) {
        this.ListaEstudiantesC2 = ListaEstudiantesC2;
    }

    public ArrayList getListaEstudiantesC3() {
        return ListaEstudiantesC3;
    }

    public void setListaEstudiantesC3(ArrayList ListaEstudiantesC3) {
        this.ListaEstudiantesC3 = ListaEstudiantesC3;
    }

        
    @PostConstruct
    public void init(){
        
        System.out.println("Se llama al init postconstruct de estudianteBean");
        ListaEstudiantesC1 = OperacionesBD.getListaEstudiantes(1);
        ListaEstudiantesC2 = OperacionesBD.getListaEstudiantes(2);
        ListaEstudiantesC3 = OperacionesBD.getListaEstudiantes(3);
    }
    
    
    public void listaTodo(){
    
        ListaEstudiantesC1 = OperacionesBD.getListaEstudiantes(1);
        ListaEstudiantesC2 = OperacionesBD.getListaEstudiantes(2);
        ListaEstudiantesC3 = OperacionesBD.getListaEstudiantes(3);
        System.out.println("llamado a listaTodo");
    
    }
    
    public ArrayList listaEstudiantesC1(){
        this.ListaEstudiantesC1 = OperacionesBD.getListaEstudiantes(1);
        return this.ListaEstudiantesC1;
    } 
    
    public ArrayList listaEstudiantesC2(){
        this.ListaEstudiantesC2 = OperacionesBD.getListaEstudiantes(2);
        return this.ListaEstudiantesC2;
    } 
        
    public ArrayList listaEstudiantesC3(){
        this.ListaEstudiantesC3 = OperacionesBD.getListaEstudiantes(3);
        return this.ListaEstudiantesC3;
    }     
    
    
    
    
    
    
    public String obtenerColorSeccion(){
    
        String auxColor = "";
        
        if(this.seccion == 1){
            auxColor = "#F7FE2E"; // amarillo
        }else if(this.seccion == 2){
            auxColor =  "#DF0101"; // rojo
        }else if(this.seccion == 3){
            auxColor = "#298A08"; // verde
        }
    
        return auxColor;
    }
    
}
