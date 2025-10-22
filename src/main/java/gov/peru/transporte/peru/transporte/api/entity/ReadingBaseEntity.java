package gov.peru.transporte.peru.transporte.api.entity;

import jakarta.persistence.*;

@MappedSuperclass
public class ReadingBaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_establecimiento;
    private String no_ruc;
    private String departamento;
    private String provincia;
    private String distrito;
    private String nombre_centro;
    private String email;
    private String phone;
    private String direccion_centro;
    private String id_distrito;
    private boolean estado_autorizacion;

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public boolean isEstado_autorizacion() {
        return estado_autorizacion;
    }

    public ReadingBaseEntity() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ReadingBaseEntity(String no_ruc, String nombre_centro, String direccion_centro, boolean estado_autorizacion, String id_distrito) {
        this.no_ruc = no_ruc;
        this.nombre_centro = nombre_centro;
        this.direccion_centro = direccion_centro;
        this.estado_autorizacion = estado_autorizacion;
        this.id_distrito = id_distrito;
    }

    public ReadingBaseEntity(String no_ruc, String nombre_centro, String direccion_centro, String id_distrito) {
        this.no_ruc = no_ruc;
        this.nombre_centro = nombre_centro;
        this.direccion_centro = direccion_centro;
        this.id_distrito = id_distrito;
    }

    public Integer getId_establecimiento() {
        return id_establecimiento;
    }

    public void setId_establecimiento(Integer id_establecimiento) {
        this.id_establecimiento = id_establecimiento;
    }

    public String getNo_ruc() {
        return no_ruc;
    }

    public void setNo_ruc(String no_ruc) {
        this.no_ruc = no_ruc;
    }

    public String getNombre_centro() {
        return nombre_centro;
    }

    public void setNombre_centro(String nombre_centro) {
        this.nombre_centro = nombre_centro;
    }



    public boolean getEstado_autorizacion() {
        return estado_autorizacion;
    }

    public void setEstado_autorizacion(boolean estado_autorizacion) {
        this.estado_autorizacion = estado_autorizacion;
    }

    public String getId_distrito() {
        return id_distrito;
    }

    public void setId_distrito(String id_distrito) {
        this.id_distrito = id_distrito;
    }


    public String getDireccion_centro() {
        return direccion_centro;
    }

    public void setDireccion_centro(String direccion_centro) {
        this.direccion_centro = direccion_centro;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }
}
