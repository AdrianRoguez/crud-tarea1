package es.adrianroguez.model;

import java.util.Set;

public interface OperacionesInterface {
    public boolean create(Empleado empleado);

    public Empleado read(String identificador);

    public Empleado read(Empleado empleado);

    public boolean update(Empleado empleado);

    public boolean delete(Empleado empleado);

    public Set<Empleado> empleadosPorPuesto(String puesto);

    public Set<Empleado> empleadosPorEdad(String fechaInicio, String fechaFin);
}
