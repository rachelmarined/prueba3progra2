/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prueba3progra2;

/**
 *
 * @author XPS 13
 */
public class EmpleadoPorHoras extends Empleado {
    private int horasTrabajadas;
    private double tarifaPorHora;

    public EmpleadoPorHoras(String codigo, String nombre, double salarioBase, int horasTrabajadas, double tarifaPorHora) {
        super(codigo, nombre, salarioBase);
        this.horasTrabajadas=horasTrabajadas;
        this.tarifaPorHora=tarifaPorHora;
    }

    public int getHorasTrabajadas() {
        return horasTrabajadas;
    }

    public void setHorasTrabajadas(int horasTrabajadas) {
        this.horasTrabajadas=horasTrabajadas;
    }

    public double getTarifaPorHora() {
        return tarifaPorHora;
    }

    public void setTarifaPorHora(double tarifaPorHora) {
        this.tarifaPorHora=tarifaPorHora;
    }

    @Override
    public double calcularSalario() {
        return getSalarioBase()+(horasTrabajadas*tarifaPorHora);
    }
}
