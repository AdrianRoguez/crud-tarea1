package es.adrianroguez.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Empleado {
    private String identificador;
    private String nombre;
    private String puesto;
    private double salario;
    private String fechaNacimiento;

    /**
     * Constructor vacío
     */
    public Empleado() {

    }

    /**
     * Constructor con solo el identificador
     * 
     * @param identificador del empleado
     */
    public Empleado(String identificador) {
        this.identificador = identificador;
    }

    /**
     * Constructor por defecto
     * 
     * @param identificador   del empleado
     * @param nombre          del empleado
     * @param puesto          del empleado
     * @param salario         del empleado
     * @param fechaNacimiento del empleado
     */
    public Empleado(String identificador, String nombre, String puesto, double salario, String fechaNacimiento) {
        this.identificador = identificador;
        this.nombre = nombre;
        this.puesto = puesto;
        this.salario = salario;
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getIdentificador() {
        return this.identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPuesto() {
        return this.puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public double getSalario() {
        return this.salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public String getFechaNacimiento() {
        return this.fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * Método que calcula la edad del empleado en base a su fecha de nacimiento
     * 
     * @return edad
     */
    public int getEdad() {
        LocalDate edad = LocalDate.parse(fechaNacimiento, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return LocalDate.now().compareTo(edad);
    }

    @Override
    public String toString() {
        return getIdentificador() + ", " +
                getNombre() + ", " +
                getPuesto() + ", " +
                getSalario() + ", " +
                getFechaNacimiento() + ", ";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Empleado)) {
            return false;
        }
        Empleado empleado = (Empleado) o;
        return Objects.equals(identificador, empleado.identificador);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificador, nombre, puesto, salario, fechaNacimiento);
    }
}
