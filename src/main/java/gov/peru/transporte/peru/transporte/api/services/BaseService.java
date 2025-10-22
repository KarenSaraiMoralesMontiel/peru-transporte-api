package gov.peru.transporte.peru.transporte.api.services;

import gov.peru.transporte.peru.transporte.api.dto.CentroDTO;
import gov.peru.transporte.peru.transporte.api.entity.ReadingBaseEntity;
import gov.peru.transporte.peru.transporte.api.entity.WritingBaseEntity;
import gov.peru.transporte.peru.transporte.api.entity.escuelas.ReadingEscuelaEntity;
import gov.peru.transporte.peru.transporte.api.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.lang.Integer;

import java.util.List;
import java.util.Optional;
public interface BaseService<W extends WritingBaseEntity, Re extends ReadingBaseEntity, ID> {
    List<Re> findBySearchParams(String nombre_centro, String departamento, String provincia,
                                String distrito, String[] order_params, String order);

    Page<Re> findBySearchParamsPaging(Pageable page, String nombre_centro, String departamento,
                                      String provincia, String distrito, String[] order_params, String order);

    Optional<Re> findByIdCustom(Integer id);

    CentroDTO saveCentro(CentroDTO entity);

    W update(W updateDTO);

    W updateEstado(Integer id, boolean estado_autorizacion);

    void delete(Integer id);
}
