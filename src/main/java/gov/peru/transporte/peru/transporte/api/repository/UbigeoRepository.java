package gov.peru.transporte.peru.transporte.api.repository;

import gov.peru.transporte.peru.transporte.api.entity.UbigeoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UbigeoRepository extends JpaRepository<UbigeoEntity, Integer> {

    @Query("SELECT u FROM ubigeo_peru_view u WHERE u.nombre_distrito LIKE UPPER(CONCAT('%', :nombre_distrito, '%'))")
    List<UbigeoEntity> findAllCustom(String nombre_distrito);

}
