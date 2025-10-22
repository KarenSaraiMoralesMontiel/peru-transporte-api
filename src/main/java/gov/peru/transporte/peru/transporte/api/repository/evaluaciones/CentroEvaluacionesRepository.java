package gov.peru.transporte.peru.transporte.api.repository.evaluaciones;

import gov.peru.transporte.peru.transporte.api.entity.evaluacion.ReadingCentroEvaluacionEntity;
import gov.peru.transporte.peru.transporte.api.entity.evaluacion.WritingCentroEvaluacionEntity;
import gov.peru.transporte.peru.transporte.api.entity.medicos.ReadingCentroMedicoEntity;
import gov.peru.transporte.peru.transporte.api.entity.medicos.WritingCentroMedicoEntity;
import gov.peru.transporte.peru.transporte.api.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CentroEvaluacionesRepository extends BaseRepository<WritingCentroEvaluacionEntity, ReadingCentroEvaluacionEntity, Integer> {

    @Override
    default String getTableType() {
        return "evaluaciones";
    }


    default Optional<ReadingCentroEvaluacionEntity> findByIdCustom(Integer id) {
        return findByIdCustom(getTableType(), id);
    }


    default List<ReadingCentroEvaluacionEntity> findBySearchParams(String nombre, String departamento, String provincia, String distrito, String[] order_params, String order) {
        return findBySearchParams(getTableType(), nombre, departamento, provincia, distrito, order_params, order);
    }

    default Page<ReadingCentroEvaluacionEntity> findBySearchParamsPaging(String nombre, String departamento, String provincia, String distrito, Pageable pageable, String[] order_params, String order) {
        return findBySearchParamsPaging(getTableType(), nombre, departamento, provincia, distrito, pageable, order_params, order);
    }


    default Integer insertCentro(String no_ruc,
                                 String nombre_centro,
                                 String direccion_centro,
                                 String email,
                                 String phone,
                                 String id_distrito) {
        return insertCentro( no_ruc, nombre_centro, direccion_centro, email, phone, id_distrito, getTableType());
    }
}
