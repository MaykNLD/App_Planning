package com.example.planing1.adapter;

import com.google.firebase.Timestamp;

public class Nota {

    String fecha;

    String hora;
    String titulo;
    String descripcion; //Asunto - ocio
    Timestamp timestamp;
    String direccion;
    String motivo;
    String notaReunion;
    //Citas medicas
    String centro;
    String especialista;
    //hogar
    String tarea;
    String infTarea;
    //Finanzas
    String recibo;
    String infRecibo;

    public Nota(){

    }

    public String getCentro() {
        return centro;
    }

    public void setCentro(String centro) {
        this.centro = centro;
    }

    public String getEspecialista() {
        return especialista;
    }

    public void setEspecialista(String especialista) {
        this.especialista = especialista;
    }

    public String getTarea() {
        return tarea;
    }

    public void setTarea(String tarea) {
        this.tarea = tarea;
    }

    public String getInfTarea() {
        return infTarea;
    }

    public void setInfTarea(String infTarea) {
        this.infTarea = infTarea;
    }

    public String getRecibo() {
        return recibo;
    }

    public void setRecibo(String recibo) {
        this.recibo = recibo;
    }

    public String getInfRecibo() {
        return infRecibo;
    }

    public void setInfRecibo(String infRecibo) {
        this.infRecibo = infRecibo;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getNotaReunion() {
        return notaReunion;
    }

    public void setNotaReunion(String notaReunion) {
        this.notaReunion = notaReunion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public com.google.firebase.Timestamp getTimestamp() {
        return timestamp;
    }

    public String getFecha() { return fecha; }

    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getHora() { return hora; }

    public void setHora(String hora) { this.hora = hora; }

    public void setTimestamp(com.google.firebase.Timestamp timestamp) {
        this.timestamp = timestamp;
    }

}
