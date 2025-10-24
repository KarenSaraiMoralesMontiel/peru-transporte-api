package gov.peru.transporte.peru.transporte.api.repository;

import gov.peru.transporte.peru.transporte.api.entity.ReadingBaseEntity;
import gov.peru.transporte.peru.transporte.api.entity.WritingBaseEntity;
import gov.peru.transporte.peru.transporte.api.entity.escuelas.ReadingEscuelaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
@NoRepositoryBean
public interface BaseRepository<T extends WritingBaseEntity, Re extends ReadingBaseEntity, Integer>
        extends JpaRepository<T, Integer> {

    String getTableType();

    // Reading operations return Re (ReadingBaseEntity)
    default Optional<Re> findByIdCustom(Integer id) {
        return findByIdCustom(getTableType(), id);
    }

    default List<Re> findBySearchParams(String nombre, String departamento, String provincia,
                                        String distrito, String[] order_params, String order) {
        return findBySearchParams(getTableType(), nombre, departamento, provincia, distrito, order_params, order);
    }

    default Page<Re> findBySearchParamsPaging(String nombre, String departamento, String provincia,
                                              String distrito, Pageable pageable, String[] order_params, String order) {
        return findBySearchParamsPaging(getTableType(), nombre, departamento, provincia, distrito, pageable, order_params, order);
    }

    // Writing operations
    default Integer insertCentro(String no_ruc, String nombre_centro, String direccion_centro,
                                 String email, String phone, String id_distrito) {
        return insertCentro(no_ruc, nombre_centro, direccion_centro, email, phone, id_distrito, getTableType());
    }

    // ADD THE MISSING UPDATE METHOD
    @Query(value = "CALL update_centro(:id, :no_ruc, :nombre_centro, :direccion_centro, :email, :phone, :id_distrito, :table_type)",
            nativeQuery = true)
    Integer updateCentro(
            @Param("id") Integer id,
            @Param("no_ruc") String noRuc,
            @Param("nombre_centro") String nombreCentro,
            @Param("direccion_centro") String direccionCentro,
            @Param("email") String email,
            @Param("phone") String phone,
            @Param("id_distrito") String idDistrito,
            @Param("table_type") String tableType
    );

    // Default method for update
    default Integer updateCentro(Integer id, String noRuc, String nombreCentro,
                                 String direccionCentro, String email, String phone,
                                 String idDistrito) {
        return updateCentro(id, noRuc, nombreCentro, direccionCentro, email, phone, idDistrito, getTableType());
    }

    // Your existing query methods
    @Query(value = "SELECT * FROM get_data_by_table_type(:type) AS data WHERE data.id_establecimiento = :id", nativeQuery = true)
    Optional<Re> findByIdCustom(@Param("type") String type, @Param("id") Integer id);


    @Query(value = """
    SELECT * FROM get_data_by_table_type(:type) AS data
    WHERE (:nombre_centro IS NULL OR :nombre_centro = '' OR data.nombre_centro ILIKE '%' || :nombre_centro || '%')
      AND (:departamento IS NULL OR :departamento = '' OR data.departamento ILIKE '%' || :departamento || '%')
      AND (:provincia IS NULL OR :provincia = '' OR data.provincia ILIKE '%' || :provincia || '%')
      AND (:distrito IS NULL OR :distrito = '' OR data.distrito ILIKE '%' || :distrito || '%')
    """, countQuery = """
    SELECT COUNT(*) FROM get_data_by_table_type(:type) AS data
    WHERE (:nombre_centro IS NULL OR :nombre_centro = '' OR data.nombre_centro ILIKE '%' || :nombre_centro || '%')
      AND (:departamento IS NULL OR :departamento = '' OR data.departamento ILIKE '%' || :departamento || '%')
      AND (:provincia IS NULL OR :provincia = '' OR data.provincia ILIKE '%' || :provincia || '%')
      AND (:distrito IS NULL OR :distrito = '' OR data.distrito ILIKE '%' || :distrito || '%')
    """,

            nativeQuery = true)
    List<Re> findBySearchParams(
            @Param("type") String type,
            @Param("nombre_centro") String nombre_centro,
            @Param("departamento") String departamento,
            @Param("provincia") String provincia,
            @Param("distrito") String distrito,
            String[] order_params,
            String order
    );

    @Query(value = """
    SELECT * FROM get_data_by_table_type(:type) AS data
    WHERE (:nombre_centro IS NULL OR :nombre_centro = '' OR data.nombre_centro ILIKE '%' || :nombre_centro || '%')
      AND (:departamento IS NULL OR :departamento = '' OR data.departamento ILIKE '%' || :departamento || '%')
      AND (:provincia IS NULL OR :provincia = '' OR data.provincia ILIKE '%' || :provincia || '%')
      AND (:distrito IS NULL OR :distrito = '' OR data.distrito ILIKE '%' || :distrito || '%')
    """,
            countQuery = """
    SELECT COUNT(*) FROM get_data_by_table_type(:type) AS data
    WHERE (:nombre_centro IS NULL OR :nombre_centro = '' OR data.nombre_centro ILIKE '%' || :nombre_centro || '%')
      AND (:departamento IS NULL OR :departamento = '' OR data.departamento ILIKE '%' || :departamento || '%')
      AND (:provincia IS NULL OR :provincia = '' OR data.provincia ILIKE '%' || :provincia || '%')
      AND (:distrito IS NULL OR :distrito = '' OR data.distrito ILIKE '%' || :distrito || '%')
    """,
            nativeQuery = true)
    Page<Re> findBySearchParamsPaging(
            @Param("type") String type,
            @Param("nombre_centro") String nombre_centro,
            @Param("departamento") String departamento,
            @Param("provincia") String provincia,
            @Param("distrito") String distrito,
            Pageable pageable,
            String[] order_params,
            String order
    );


    @Query(value = "CALL insert_centro(:no_ruc, :nombre_centro, :direccion_centro, :email, :phone, :id_distrito, :table_type)",
            nativeQuery = true)
    Integer insertCentro(
            @Param("no_ruc") String noRuc,
            @Param("nombre_centro") String nombreCentro,
            @Param("direccion_centro") String direccionCentro,
            @Param("email") String email,
            @Param("phone") String phone,
            @Param("id_distrito") String idDistrito,
            @Param("table_type") String tableType
    );
}