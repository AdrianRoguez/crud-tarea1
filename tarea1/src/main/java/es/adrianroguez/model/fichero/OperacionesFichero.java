package es.adrianroguez.model.fichero;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import es.adrianroguez.model.Empleado;
import es.adrianroguez.model.OperacionesInterface;

public class OperacionesFichero implements OperacionesInterface {
    File fichero;
    String path = "/home/adrianssd/Escritorio/Programación/2ª Evaluación/(Unidad -4) Tarea 1 (Uso y métodos en el paquete java.time)/tarea1/src/main/resources/archivo.txt";

    public OperacionesFichero() {
        fichero = new File(path);
        if (!fichero.exists() || !fichero.isFile()) {
            throw new IllegalArgumentException("El recurso no es de tipo fichero " + path);
        }
    }

    @Override
    public boolean create(Empleado empleado) {
        if (empleado == null || empleado.getIdentificador() == null || empleado.getIdentificador().isEmpty()) {
            return false;
        }
        Set<Empleado> empleados = read(fichero);
        if (empleados.contains(empleado)) {
            return false;
        }
        return create(empleado.toString(), fichero);
    }

    private boolean create(String data, File file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(data);
            writer.newLine(); // Añadir una nueva línea después del registro
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private Set<Empleado> read(File file) {
        Set<Empleado> empleados = new HashSet<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] arrayLine = line.split(",");
                Empleado empleado = new Empleado(arrayLine[0], arrayLine[1], arrayLine[2], Double.valueOf(arrayLine[3]),
                        arrayLine[4]);
                empleados.add(empleado);
            }
        } catch (IOException e) {
            return new HashSet<>();
        }
        return empleados;
    }

    @Override
    public Empleado read(Empleado empleado) {
        if (empleado == null || empleado.getIdentificador() == null || empleado.getIdentificador().isEmpty()) {
            return empleado;
        }
        Set<Empleado> empleados = read(fichero);
        for (Empleado empleadoBuscado : empleados) {
            if (empleadoBuscado.equals(empleado)) {
                return empleadoBuscado;
            }
        }
        return empleado;
    }

    @Override
    public Empleado read(String identificador) {
        if (identificador == null || identificador.isEmpty()) {
            return null;
        }
        Empleado empleado = new Empleado(identificador);
        return read(empleado);
    }

    @Override
    public boolean update(Empleado empleado) {
        if (empleado == null || empleado.getIdentificador() == null || empleado.getIdentificador().isEmpty()) {
            return false;
        }
        Set<Empleado> empleados = read(fichero);
        if (!empleados.contains(empleado)) {
            return false;
        }
        for (Empleado empleadoBuscado : empleados) {
            if (empleadoBuscado.equals(empleado)) {
                empleados.remove(empleadoBuscado);
                empleados.add(empleado);
                return updateFile(empleados, fichero);
            }
        }
        System.out.println(empleados);
        return true;
    }

    private boolean updateFile(Set<Empleado> empleados, File file) {
        String path = file.getAbsolutePath();
        try {
            file.delete();
            file.createNewFile();
        } catch (IOException e) {
            return false;
        }
        for (Empleado empleado : empleados) {
            create(empleado);
        }
        return true;
    }

    @Override
    public boolean delete(Empleado empleado) {
        if (empleado == null || empleado.getIdentificador() == null || empleado.getIdentificador().isEmpty()) {
            return false;
        }
        Set<Empleado> empleados = read(fichero);
        if (!empleados.contains(empleado)) {
            return false;
        }
        for (Empleado empleadoBuscado : empleados) {
            if (empleadoBuscado.equals(empleado)) {
                empleados.remove(empleadoBuscado);
                return updateFile(empleados, fichero);
            }
        }
        return false;
    }

    @Override
    public Set<Empleado> empleadosPorPuesto(String puesto) {
        if (puesto == null || puesto.isEmpty()) {
            return new HashSet<>();
        }
        Set<Empleado> empleados = read(fichero);
        Set<Empleado> empleadosOrdenados = new HashSet<>();
        for (Empleado empleado : empleados) {
            if (empleado.getPuesto().trim().equals(puesto.trim())) {
                empleadosOrdenados.add(empleado);
            }
        }
        return empleadosOrdenados;
    }

    @Override
    public Set<Empleado> empleadosPorEdad(String fechaInicio, String fechaFin) {
        if (fechaInicio == null || fechaInicio.isEmpty() || fechaFin == null || fechaFin.isEmpty()) {
            return new HashSet<>();
        }
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("d/MM/yyyy");
        LocalDate inicio = LocalDate.parse(fechaInicio, formato);
        LocalDate fin = LocalDate.parse(fechaFin, formato);
        Set<Empleado> empleados = read(fichero);
        Set<Empleado> empleadosOrdenados = new HashSet<>();
        for (Empleado empleado : empleados) {
            LocalDate cumpleanio = LocalDate.parse(empleado.getFechaNacimiento(), formato);
            if (cumpleanio.isAfter(inicio) && cumpleanio.isBefore(fin)) {
                empleadosOrdenados.add(empleado);
            }
        }
        return empleadosOrdenados;
    }
}
