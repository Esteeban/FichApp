package com.example.fichapp.model;

public class Usuario {
    String nombre_paciente, apellido_paciente, correo, direccion, numero_ficha, estado, fecha, telefono;
    public Usuario(){}

    public Usuario(String nombre_paciente, String apellido_paciente, String correo, String direccion, String numero_ficha, String estado, String fecha, String telefono) {
        this.nombre_paciente = nombre_paciente;
        this.apellido_paciente = apellido_paciente;
        this.correo = correo;
        this.direccion = direccion;
        this.numero_ficha = numero_ficha;
        this.estado = estado;
        this.fecha = fecha;
        this.telefono = telefono;
    }

    public String getNombre_paciente() {
        return nombre_paciente;
    }

    public void setNombre_paciente(String nombre_paciente) {
        this.nombre_paciente = nombre_paciente;
    }

    public String getApellido_paciente() {
        return apellido_paciente;
    }

    public void setApellido_paciente(String apellido_paciente) {
        this.apellido_paciente = apellido_paciente;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNumero_ficha() {
        return numero_ficha;
    }

    public void setNumero_ficha(String numero_ficha) {
        this.numero_ficha = numero_ficha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }


}


