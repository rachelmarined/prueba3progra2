/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prueba3progra2;

/**
 *
 * @author XPS 13
 */
public abstract class Empleado implements Cloneable {
    private String codigo;
    private String nombre;
    private double salarioBase;

    public Empleado(String codigo, String nombre, double salarioBase) {
        this.codigo=codigo;
        this.nombre=nombre;
        this.salarioBase=salarioBase;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo=codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre=nombre;
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(double salarioBase) {
        this.salarioBase=salarioBase;
    }

    public abstract double calcularSalario();

    public String obtenerCategoria() {
        double s=calcularSalario();
        if(s>2000.0) {
            return "Superior";
        }else{
            return "Estándar";
        }
    }

    public String mostrarInformacion() {
        return "Código:"+codigo+" | Nombre:"+nombre+" | Salario:"+calcularSalario();
    }

    @Override
    public Empleado clone() {
        try {
            return (Empleado) super.clone();
        }catch(CloneNotSupportedException e) {
            return null;
        }
    }
}