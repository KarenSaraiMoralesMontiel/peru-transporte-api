package gov.peru.transporte.peru.transporte.api.services.ubigeo;

import gov.peru.transporte.peru.transporte.api.entity.UbigeoEntity;
import gov.peru.transporte.peru.transporte.api.repository.UbigeoLevelProjection;
import gov.peru.transporte.peru.transporte.api.repository.UbigeoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UbigeoServiceImpl implements UbigeoService{
    @Autowired
    private final UbigeoRepository ubigeoRepository;

    public UbigeoServiceImpl(UbigeoRepository ubigeoRepository) {
        this.ubigeoRepository = ubigeoRepository;
    }

    @Override
    public List<UbigeoEntity> findCustomAll(String nombre_distrito) {
        return ubigeoRepository.findAllCustom(nombre_distrito);
    }
    public List<UbigeoLevelProjection> getDepartments() {
        return ubigeoRepository.getUbigeoLevels("departamentos", null);
    }

    public List<UbigeoLevelProjection> getProvinces(String departmentId) {
        return ubigeoRepository.getUbigeoLevels("provincias", departmentId);
    }

    public List<UbigeoLevelProjection> getDistricts(String provinceId) {
        return ubigeoRepository.getUbigeoLevels("distritos", provinceId);
    }
}
