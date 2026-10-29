/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prueba3progra2;

/**
 *
 * @author XPS 13
 */
public class EmpleadoAdministrativo extends Empleado {
    private double bonificacion;
    private double descuento;

    public EmpleadoAdministrativo(String codigo, String nombre, double salarioBase, double bonificacion, double descuento) {
        super(codigo, nombre, salarioBase);
        this.bonificacion=bonificacion;
        this.descuento=descuento;
    }

    public double getBonificacion() {
        return bonificacion;
    }

    public void setBonificacion(double bonificacion) {
        this.bonificacion=bonificacion;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento=descuento;
    }

    @Override
    public double calcularSalario() {
        return (getSalarioBase()+bonificacion)*(1.0-(descuento/100.0));
    }
}
