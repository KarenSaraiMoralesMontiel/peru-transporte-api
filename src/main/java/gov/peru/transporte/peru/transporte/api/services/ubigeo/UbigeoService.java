package gov.peru.transporte.peru.transporte.api.services.ubigeo;

import gov.peru.transporte.peru.transporte.api.entity.UbigeoEntity;
import gov.peru.transporte.peru.transporte.api.repository.UbigeoLevelProjection;

import java.util.List;

public interface UbigeoService {

    List<UbigeoEntity> findCustomAll(String nombre_distrito);


    List<UbigeoLevelProjection> getDepartments();

    List<UbigeoLevelProjection> getProvinces(String departmentId) ;

    List<UbigeoLevelProjection> getDistricts(String provinceId) ;
}
