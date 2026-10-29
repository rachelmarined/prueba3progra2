/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package prueba3progra2;

/**
 *
 * @author XPS 13
 */

  import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
public class Prueba3progra2 extends JFrame {

    private ArrayList<Empleado> listaEmpleados;
    private DefaultTableModel modeloTabla;
    private JTable tablaEmpleados;

    private JTextField txtCodigo, txtNombre, txtSalarioBase, txtExtra1, txtExtra2;
    private JComboBox<String> cmbTipo;
    private JLabel lblExtra1, lblExtra2;

    private Empleado empleadoClonado=null;

    public Prueba3progra2() {
        listaEmpleados=new ArrayList<>();

        setTitle("Sistema de Gestión de Empleados");
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        
        JPanel panelForm=new JPanel(new GridLayout(6, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos del Empleado"));

        panelForm.add(new JLabel("Código:"));
        txtCodigo=new JTextField();
        panelForm.add(txtCodigo);

        panelForm.add(new JLabel("Nombre:"));
        txtNombre=new JTextField();
        panelForm.add(txtNombre);

        panelForm.add(new JLabel("Tipo de Empleado:"));
        cmbTipo=new JComboBox<>(new String[]{"Administrativo", "Por horas"});
        panelForm.add(cmbTipo);

        panelForm.add(new JLabel("Salario Base:"));
        txtSalarioBase=new JTextField();
        panelForm.add(txtSalarioBase);

        lblExtra1=new JLabel("Bonificación:");
        panelForm.add(lblExtra1);
        txtExtra1=new JTextField();
        panelForm.add(txtExtra1);

        lblExtra2=new JLabel("Descuento (%):");
        panelForm.add(lblExtra2);
        txtExtra2=new JTextField();
        panelForm.add(txtExtra2);

        add(panelForm, BorderLayout.NORTH);

        
        cmbTipo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tipo=(String)cmbTipo.getSelectedItem();
                if(tipo.equals("Administrativo")) {
                    lblExtra1.setText("Bonificación:");
                    lblExtra2.setText("Descuento (%):");
                }else{
                    lblExtra1.setText("Horas Trabajadas:");
                    lblExtra2.setText("Tarifa por Hora:");
                }
            }
        });

        
        JPanel panelBotones=new JPanel(new FlowLayout());

        JButton btnRegistrar=new JButton("Registrar");
        JButton btnCalcular=new JButton("Calcular Salario");
        JButton btnBuscar=new JButton("Buscar");
        JButton btnClonar=new JButton("Clonar empleado");
        JButton btnRegClon=new JButton("Registrar clon");
        JButton btnComparar=new JButton("Comparar objetos");
        JButton btnMax=new JButton("Salario máximo");
        JButton btnProm=new JButton("Promedio");
        JButton btnLimpiar=new JButton("Limpiar");

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnCalcular);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnClonar);
        panelBotones.add(btnRegClon);
        panelBotones.add(btnComparar);
        panelBotones.add(btnMax);
        panelBotones.add(btnProm);
        panelBotones.add(btnLimpiar);

    
        JPanel panelSur=new JPanel(new BorderLayout());
        panelSur.add(panelBotones, BorderLayout.NORTH);

  
        modeloTabla=new DefaultTableModel(new String[]{"Código", "Nombre", "Tipo", "Salario calculado", "Categoría"}, 0);
        tablaEmpleados=new JTable(modeloTabla);
        panelSur.add(new JScrollPane(tablaEmpleados), BorderLayout.CENTER);

        add(panelSur, BorderLayout.CENTER);

        btnRegistrar.addActionListener(e -> registrarEmpleado());
        btnCalcular.addActionListener(e -> calcularSalarioSeleccionado());
        btnBuscar.addActionListener(e -> buscarEmpleado());
        btnClonar.addActionListener(e -> clonarEmpleado());
        btnRegClon.addActionListener(e -> registrarClon());
        btnComparar.addActionListener(e -> compararObjetos());
        btnMax.addActionListener(e -> salarioMaximo());
        btnProm.addActionListener(e -> promedioSalarios());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
    }

    private void registrarEmpleado() {
        try {
            String cod=txtCodigo.getText().trim();
            String nom=txtNombre.getText().trim();
            if(cod.isEmpty()||nom.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Los campos obligatorios no pueden estar vacíos.");
                return;
            }

            for(Empleado emp:listaEmpleados) {
                if(emp.getCodigo().equalsIgnoreCase(cod)) {
                    JOptionPane.showMessageDialog(this, "El código ya existe.");
                    return;
                }
            }

            double salBase=Double.parseDouble(txtSalarioBase.getText());
            if(salBase<0) {
                JOptionPane.showMessageDialog(this, "El salario base no puede ser negativo.");
                return;
            }

            String tipo=(String)cmbTipo.getSelectedItem();
            Empleado nuevoEmp;

            double val1=Double.parseDouble(txtExtra1.getText());
            double val2=Double.parseDouble(txtExtra2.getText());

            if(tipo.equals("Administrativo")) {
                if(val2<0||val2>100) {
                    JOptionPane.showMessageDialog(this, "El descuento debe estar entre 0 y 100.");
                    return;
                }
                nuevoEmp=new EmpleadoAdministrativo(cod, nom, salBase, val1, val2);
            }else{
                int horas=(int)val1;
                if(horas<0||val2<0) {
                    JOptionPane.showMessageDialog(this, "Las horas y tarifa no pueden ser negativas.");
                    return;
                }
                nuevoEmp=new EmpleadoPorHoras(cod, nom, salBase, horas, val2);
            }

            listaEmpleados.add(nuevoEmp);
            actualizarTabla();
            limpiarFormulario();
            JOptionPane.showMessageDialog(this, "Empleado registrado con éxito.");

        }catch(NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese valores numéricos válidos.");
        }
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        for(Empleado emp:listaEmpleados) {
            String tipoStr=(emp instanceof EmpleadoAdministrativo) ? "Administrativo" : "Por horas";
            modeloTabla.addRow(new Object[]{
                emp.getCodigo(),
                emp.getNombre(),
                tipoStr,
                String.format("%.2f", emp.calcularSalario()),
                emp.obtenerCategoria()
            });
        }
    }

    private void calcularSalarioSeleccionado() {
        int fila=tablaEmpleados.getSelectedRow();
        if(fila>=0) {
            Empleado emp=listaEmpleados.get(fila);
            JOptionPane.showMessageDialog(this, "Salario calculado para "+emp.getNombre()+": "+emp.calcularSalario());
        }else{
            JOptionPane.showMessageDialog(this, "Seleccione un empleado de la tabla.");
        }
    }

    private void buscarEmpleado() {
        String cod=JOptionPane.showInputDialog(this, "Ingrese el código del empleado a buscar:");
        if(cod!=null&&!cod.trim().isEmpty()) {
            for(Empleado emp:listaEmpleados) {
                if(emp.getCodigo().equalsIgnoreCase(cod.trim())) {
                    JOptionPane.showMessageDialog(this, emp.mostrarInformacion());
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "Empleado no encontrado.");
        }
    }

    private void clonarEmpleado() {
        int fila=tablaEmpleados.getSelectedRow();
        if(fila>=0) {
            Empleado original=listaEmpleados.get(fila);
            empleadoClonado=original.clone();
            JOptionPane.showMessageDialog(this, "Empleado clonado exitosamente. Ahora cambie el código y haga clic en 'Registrar clon'.");
            txtCodigo.setText(original.getCodigo()+"-CLON");
            txtNombre.setText("Clon de "+original.getNombre());
            txtSalarioBase.setText(String.valueOf(original.getSalarioBase()));
            if(original instanceof EmpleadoAdministrativo) {
                cmbTipo.setSelectedItem("Administrativo");
                txtExtra1.setText(String.valueOf(((EmpleadoAdministrativo)original).getBonificacion()));
                txtExtra2.setText(String.valueOf(((EmpleadoAdministrativo)original).getDescuento()));
            }else{
                cmbTipo.setSelectedItem("Por horas");
                txtExtra1.setText(String.valueOf(((EmpleadoPorHoras)original).getHorasTrabajadas()));
                txtExtra2.setText(String.valueOf(((EmpleadoPorHoras)original).getTarifaPorHora()));
            }
        }else{
            JOptionPane.showMessageDialog(this, "Seleccione un empleado para clonar.");
        }
    }

    private void registrarClon() {
        if(empleadoClonado!=null) {
            String nuevoCod=txtCodigo.getText().trim();
            // Verificar unicidad
            for(Empleado emp:listaEmpleados) {
                if(emp.getCodigo().equalsIgnoreCase(nuevoCod)) {
                    JOptionPane.showMessageDialog(this, "El código del clon ya existe, por favor asigne uno único.");
                    return;
                }
            }
            empleadoClonado.setCodigo(nuevoCod);
            empleadoClonado.setNombre(txtNombre.getText().trim());
            listaEmpleados.add(empleadoClonado);
            actualizarTabla();
            limpiarFormulario();
            empleadoClonado=null;
            JOptionPane.showMessageDialog(this, "Clon registrado exitosamente.");
        }else{
            JOptionPane.showMessageDialog(this, "No hay ningún clon preparado.");
        }
    }

    private void compararObjetos() {
        int fila=tablaEmpleados.getSelectedRow();
        if(fila>=0) {
            Empleado seleccionado=listaEmpleados.get(fila);
            if(empleadoClonado!=null) {
                boolean mismaReferencia=(seleccionado==empleadoClonado);
                JOptionPane.showMessageDialog(this, "¿Es la misma referencia?: "+mismaReferencia+"\nNombre original: "+seleccionado.getNombre()+"\nNombre clon: "+empleadoClonado.getNombre());
            }else{
                JOptionPane.showMessageDialog(this, "Seleccione un empleado y asegúrese de haber generado un clon para comparar.");
            }
        }else{
            JOptionPane.showMessageDialog(this, "Seleccione un registro de la tabla.");
        }
    }

    private void salarioMaximo() {
        if(listaEmpleados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay registros.");
            return;
        }
        Empleado max=listaEmpleados.get(0);
        for(Empleado emp:listaEmpleados) {
            if(emp.calcularSalario()>max.calcularSalario()) {
                max=emp;
            }
        }
        JOptionPane.showMessageDialog(this, "Empleado con mayor salario: "+max.getNombre()+" con "+max.calcularSalario());
    }

    private void promedioSalarios() {
        if(listaEmpleados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay registros.");
            return;
        }
        double suma=0;
        for(Empleado emp:listaEmpleados) {
            suma+=emp.calcularSalario();
        }
        double prom=suma/listaEmpleados.size();
        JOptionPane.showMessageDialog(this, "Salario promedio: "+String.format("%.2f", prom));
    }

    private void limpiarFormulario() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtSalarioBase.setText("");
        txtExtra1.setText("");
        txtExtra2.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Prueba3progra2().setVisible(true);
        });
    }
}
