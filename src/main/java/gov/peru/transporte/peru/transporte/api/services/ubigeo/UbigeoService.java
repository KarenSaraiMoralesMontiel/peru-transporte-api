package gov.peru.transporte.peru.transporte.api.services.ubigeo;

import gov.peru.transporte.peru.transporte.api.entity.UbigeoEntity;

import java.util.List;

public interface UbigeoService {

    List<UbigeoEntity> findCustomAll(String nombre_distrito);
}
