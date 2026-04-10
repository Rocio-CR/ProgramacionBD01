package org.example;

/**
 * @NombreClase Persona
 * @Descripción TODO: Describe el propósito de esta clase
 * @Autor Rocio
 * @Fecha 06/04/2026 9:49
 * @Versión 1.0
 */
public class Persona {
    int dni;
    String nombre;
    String apellidos;

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
