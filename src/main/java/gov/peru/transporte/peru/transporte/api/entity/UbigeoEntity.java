package gov.peru.transporte.peru.transporte.api.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Immutable;

@Table
@Immutable
@Entity(name="ubigeo_peru_view")
public class UbigeoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_distrito;
    private String nombre_departamento;
    private String nombre_provincia;
    private String nombre_distrito;

    public UbigeoEntity(int id_distrito, String nombre_departamento, String nombre_provincia, String nombre_distrito) {
        this.id_distrito = id_distrito;
        this.nombre_departamento = nombre_departamento;
        this.nombre_provincia = nombre_provincia;
        this.nombre_distrito = nombre_distrito;
    }

    public UbigeoEntity() {
    }

    public int getId_distrito() {
        return id_distrito;
    }

    public void setId_distrito(int id_distrito) {
        this.id_distrito = id_distrito;
    }

    public String getNombre_departamento() {
        return nombre_departamento;
    }

    public void setNombre_departamento(String nombre_departamento) {
        this.nombre_departamento = nombre_departamento;
    }

    public String getNombre_provincia() {
        return nombre_provincia;
    }

    public void setNombre_provincia(String nombre_provincia) {
        this.nombre_provincia = nombre_provincia;
    }

    public String getNombre_distrito() {
        return nombre_distrito;
    }

    public void setNombre_distrito(String nombre_distrito) {
        this.nombre_distrito = nombre_distrito;
    }
}
