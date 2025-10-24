package gov.peru.transporte.peru.transporte.api.repository;

import gov.peru.transporte.peru.transporte.api.entity.UbigeoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UbigeoRepository extends JpaRepository<UbigeoEntity, Integer> {

    @Query("SELECT u FROM ubigeo_peru_view u WHERE u.nombre_distrito LIKE UPPER(CONCAT('%', :nombre_distrito, '%'))")
    List<UbigeoEntity> findAllCustom(String nombre_distrito);

    @Query(value = "SELECT * FROM get_ubigeo_levels(:p_level, :parent_id)", nativeQuery = true)
    List<UbigeoLevelProjection> getUbigeoLevels(
            @Param("p_level") String p_level,
            @Param("parent_id") String parentId);
}



