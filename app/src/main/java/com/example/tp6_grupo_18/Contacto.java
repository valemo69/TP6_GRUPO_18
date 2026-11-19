package com.example.tp6_grupo_18;

public class Contacto {
    private int id;
    private String nombre;
    private String apellido;
    private String telefono;
    private String tipoTelefono;
    private String email;
    private String tipoEmail;
    private String fechaNacimiento;
    private String direccion;
    private String nivelEstudios;
    private String intereses;
    private boolean recibeInformacion;

    public Contacto() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getTipoTelefono() { return tipoTelefono; }
    public void setTipoTelefono(String tipoTelefono) { this.tipoTelefono = tipoTelefono; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTipoEmail() { return tipoEmail; }
    public void setTipoEmail(String tipoEmail) { this.tipoEmail = tipoEmail; }

    public String getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(String fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getNivelEstudios() { return nivelEstudios; }
    public void setNivelEstudios(String nivelEstudios) { this.nivelEstudios = nivelEstudios; }

    public String getIntereses() { return intereses; }
    public void setIntereses(String intereses) { this.intereses = intereses; }

    public boolean isRecibeInformacion() { return recibeInformacion; }
    public void setRecibeInformacion(boolean recibeInformacion) { this.recibeInformacion = recibeInformacion; }

    @Override
    public String toString() {
        return nombre + " " + apellido + " - " + email;
    }
}