package gov.peru.transporte.peru.transporte.api.services;

import gov.peru.transporte.peru.transporte.api.dto.CentroDTO;
import gov.peru.transporte.peru.transporte.api.entity.ReadingBaseEntity;
import gov.peru.transporte.peru.transporte.api.entity.WritingBaseEntity;
import gov.peru.transporte.peru.transporte.api.repository.BaseRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
public abstract class BaseServiceImpl<
        W extends WritingBaseEntity,
        Re extends ReadingBaseEntity,
        R extends BaseRepository<W, Re, Integer>,
        ID extends Integer>
        implements BaseService<W, Re, ID> {

    protected final R repository;

    public BaseServiceImpl(R repository) {
        this.repository = repository;
    }

    @Override
    public List<Re> findBySearchParams(String nombre_centro, String departamento, String provincia,
                                       String distrito, String[] order_params, String order) {
        return repository.findBySearchParams(nombre_centro, departamento, provincia, distrito, order_params, order);
    }

    @Override
    public Page<Re> findBySearchParamsPaging(Pageable page, String nombre_centro, String departamento,
                                             String provincia, String distrito, String[] order_params, String order) {
        return repository.findBySearchParamsPaging(nombre_centro, departamento, provincia, distrito, page, order_params, order);
    }

    @Override
    public Optional<Re> findByIdCustom(Integer id) {
        return repository.findByIdCustom(id);
    }

    @Override
    public CentroDTO saveCentro(CentroDTO centroDTO) {
        String no_ruc = centroDTO.getNo_ruc();
        String nombre_centro = centroDTO.getNombre_centro();
        String direccion_centro = centroDTO.getDireccion_centro();
        String email = centroDTO.getEmail();
        String phone = centroDTO.getPhone();
        String id_distrito = centroDTO.getId_distrito();

        repository.insertCentro(no_ruc, nombre_centro, direccion_centro, email, phone, id_distrito);
        return centroDTO;
    }

    @Override
    public W update(W updateEntity) {
        W oEntity = repository.findById(updateEntity.getId_establecimiento())
                .orElseThrow(() -> new RuntimeException("No existe objeto con el id: " + updateEntity.getId_establecimiento()));
        oEntity.setNo_ruc(updateEntity.getNo_ruc());
        oEntity.setNombre_centro(updateEntity.getNombre_centro());
        oEntity.setDireccion_centro(updateEntity.getDireccion_centro());
        oEntity.setEmail(updateEntity.getEmail());
        oEntity.setPhone(updateEntity.getPhone());
        oEntity.setEstado_autorizacion(updateEntity.isEstado_autorizacion());
        oEntity.setId_distrito(oEntity.getId_distrito());
        return repository.save(oEntity);
    }

    @Override
    @Transactional
    public W updateEstado(Integer id, boolean estado_autorizacion) {
        W existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe objeto con el id " + id));
        existingEntity.setEstado_autorizacion(estado_autorizacion);
        return repository.save(existingEntity);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        W existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe objeto con el id " + id));
        existingEntity.setEstado_autorizacion(false);
        repository.save(existingEntity);
    }
}

    /*
    @Override
    public List<Re> findBySearchParams(String nombre_centro, String departamento, String provincia, String distrito, String[] order_params, String order) {
        return repository.findBySearchParams( nombre_centro,departamento,provincia,distrito,order_params,order);
    }

    @Override
    public Page<Re> findBySearchParamsPaging(Pageable page, String nombre_centro, String departamento, String provincia, String distrito, String[] order_params, String order) {
        return repository.findBySearchParamsPaging( nombre_centro, departamento,provincia,distrito,page, order_params,order);
    }

    public Optional<Re> findByIdCustom(Integer id) {
        return null;
    }

    @Override
    public CentroDTO saveCentro(CentroDTO centroDTO) {
        String no_ruc = centroDTO.getNo_ruc();
        String nombre_centro = centroDTO.getNombre_centro();
        String direccion_centro = centroDTO.getDireccion_centro();
        String email = centroDTO.getEmail();
        String phone = centroDTO.getPhone();
        String id_distrito = centroDTO.getId_distrito();
        repository.insertCentro(no_ruc, nombre_centro, direccion_centro, email, phone, id_distrito);
        return centroDTO;
    }

    @Override
    public W update(W updateDTO) {
        // Indepotente

        Integer id = updateDTO.getId_establecimiento();
        String no_ruc = updateDTO.getNo_ruc();
        String nombre_centro = updateDTO.getNombre_centro();
        String direccion_centro = updateDTO.getDireccion_centro();
        String email = updateDTO.getEmail();
        String phone = updateDTO.getPhone();
        Boolean estado_autorizacion = updateDTO.isEstado_autorizacion();
        String id_distrito = updateDTO.getId_distrito();
        repository.update(updateDTO);
        return updateDTO;
    }

    @Override
    public W updateEstado(Integer id, boolean estado_autorizacion) {
        // Indepotente

        W oEntity = repository.findByIdUpdatable(id)
                .orElseThrow(() -> new RuntimeException("No existe escuela con el id " + id));
        oEntity.setEstado_autorizacion(estado_autorizacion);
        return repository.save(oEntity);
    }


    @Override
    public Optional<Re> findByIdCustom(Integer id) {
        return repository.findByIdCustom(id);

    }

    @Override
    public void delete(Integer id) {
        W oSchool= repository.findByIdCustom(id)
                .orElseThrow(() -> new RuntimeException("No existe objeto con el id "+id));
        oSchool.setEstado_autorizacion(false);

    } */

