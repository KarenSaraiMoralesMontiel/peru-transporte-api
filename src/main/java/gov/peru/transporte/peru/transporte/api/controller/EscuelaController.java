package gov.peru.transporte.peru.transporte.api.controller;

import gov.peru.transporte.peru.transporte.api.dto.CentroDTO;
import gov.peru.transporte.peru.transporte.api.entity.escuelas.ReadingEscuelaEntity;
import gov.peru.transporte.peru.transporte.api.entity.escuelas.WritingEscuelaEntity;
import gov.peru.transporte.peru.transporte.api.services.escuelas.EscuelaServiceImpl;
import gov.peru.transporte.peru.transporte.api.services.escuelas.TablaEscuelaException;
import gov.peru.transporte.peru.transporte.api.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/escuelas")
public class EscuelaController {

    private final EscuelaServiceImpl driverSchoolService;
    private static final Logger logger = LoggerFactory.getLogger(EscuelaController.class);
    private PagedResourcesAssembler<ReadingEscuelaEntity> pagedResourcesAssembler;
    public EscuelaController(EscuelaServiceImpl driverSchoolService, PagedResourcesAssembler<ReadingEscuelaEntity> pagedResourcesAssembler) {

        this.driverSchoolService = driverSchoolService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping("/{id}")
    public EntityModel<ReadingEscuelaEntity> findById(@PathVariable("id") Integer id) {
        ReadingEscuelaEntity escuela = driverSchoolService.findByIdCustom(id).orElseThrow(() ->
                new TablaEscuelaException("Not found"));

        EntityModel<ReadingEscuelaEntity> escuelaEntityModel = EntityModel.of(
                escuela,
                linkTo(methodOn(EscuelaController.class).findById(id)).withSelfRel(),
                escuela.isEstado_autorizacion() ?
                        linkTo(methodOn(EscuelaController.class).updateEstado(id, false)).withRel("deshabilitar") :
                        linkTo(methodOn(EscuelaController.class).updateEstado(id, true)).withRel("habilitar")
        );

        return escuelaEntityModel;
    }

@GetMapping("")
public CollectionModel<EntityModel<ReadingEscuelaEntity>> getSortedSchools(
        @RequestParam(required = false) String nombre_centro,
        @RequestParam(required = false) String provincia,
        @RequestParam(required = false) String department,
        @RequestParam(required = false) String district,
        @RequestParam(value = "order_param", defaultValue = "id_establecimiento") String[] order_param,
        @RequestParam(value = "order", defaultValue = "ASC") String order) {

    List<ReadingEscuelaEntity> driverSchools = driverSchoolService.findBySearchParams(
            nombre_centro, department, provincia, district, order_param, order);

    // Apply multi-field sorting
    if (order_param.length > 0) {
        Comparator<ReadingEscuelaEntity> comparator = Util.applyComparator(order_param, order);
        driverSchools.sort(comparator);
    }

    List<EntityModel<ReadingEscuelaEntity>> escuelas = driverSchools.stream()
            .map(escuela -> {
                EntityModel<ReadingEscuelaEntity> escuelasModel = EntityModel.of(
                        escuela,
                        linkTo(methodOn(EscuelaController.class).findById(escuela.getId_establecimiento())).withRel("retribuirlo"),
                        linkTo(methodOn(EscuelaController.class).getSortedSchools( null, department,provincia, district, order_param, order)).withSelfRel(),
                        escuela.isEstado_autorizacion() ?
                                linkTo(methodOn(EscuelaController.class).updateEstado(escuela.getId_establecimiento(), false)).withRel("deshabilitar") :
                                linkTo(methodOn(EscuelaController.class).updateEstado(escuela.getId_establecimiento(), true)).withRel("habilitar")
                );

                return escuelasModel;
            }).toList();

    return CollectionModel.of(escuelas,
            linkTo(methodOn(EscuelaController.class).getSortedSchools(nombre_centro, department,provincia,  district, order_param, order)).withSelfRel());
}


    @GetMapping("/paging")
    public PagedModel<EntityModel<ReadingEscuelaEntity>>  getSortedSchoolsPaging(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(required = false) String nombre_centro,
            @RequestParam(required =  false) String provincia,
            @RequestParam(required = false) String departamento,
            @RequestParam(required = false) String distrito,
            @RequestParam(value = "order_param", defaultValue = "id_establecimiento") String[] order_param,
            @RequestParam(value="order", defaultValue = "ASC") String order) {

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(order.toUpperCase()), order_param);

        Page<ReadingEscuelaEntity> driverSchools = driverSchoolService.findBySearchParamsPaging(pageable,nombre_centro, departamento, provincia, distrito, order_param, order);

        return pagedResourcesAssembler.toModel(driverSchools, escuela -> {
            EntityModel<ReadingEscuelaEntity> escuelaEntityModel = EntityModel.of(escuela,
                    linkTo(methodOn(EscuelaController.class).findById(escuela.getId_establecimiento())).withSelfRel(),
                    linkTo(methodOn(EscuelaController.class).findById(escuela.getId_establecimiento())).withRel("retribuirlo"),
                    linkTo(methodOn(EscuelaController.class).getSortedSchools("", "","","",new String[] {"id_establecimiento"}, "ASC")).withRel("escuelas"),
                    escuela.isEstado_autorizacion() ?
                            linkTo(methodOn(EscuelaController.class).updateEstado(escuela.getId_establecimiento(), false)).withRel("deshabilitar") :
                            linkTo(methodOn(EscuelaController.class).updateEstado(escuela.getId_establecimiento(), true)).withRel("habilitar")
            );
            return escuelaEntityModel;
        });
    }

    @PostMapping
    public CentroDTO saveSchool(
            @RequestBody CentroDTO driverSchoolEntity
    ) {
        return driverSchoolService.saveCentro(driverSchoolEntity);
    }


    @PatchMapping("/update-autorizacion/{id}")
    public ResponseEntity<?> updateEstado(@PathVariable Integer id,
                                          @RequestParam(value = "estado_autorizacion", required = true) boolean estado_autorizacion) {
        Map<String, String> res = validarId(id);
        try {
            if (!res.isEmpty()) {
                return ResponseEntity.badRequest().body(res);
            }
            driverSchoolService.updateEstado(id, estado_autorizacion);
            return ResponseEntity.ok().build();
        } catch (TablaEscuelaException e) {
            logger.error("Error de servicio:" + e.getMessage());
            res.put("error", e.getMessage());
            return ResponseEntity.internalServerError().body(res);
        } catch (Exception e) {
            logger.error("Error interno del sistema:" + e.getMessage());
            res.put("error", e.getMessage());
            return ResponseEntity.internalServerError().body(res);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePerfil(@PathVariable Integer id, @RequestBody WritingEscuelaEntity escuela) {
        Map<String, String> res = validarId(id);
        try {
            if (!res.isEmpty()) {
                return ResponseEntity.badRequest().body(res);
            }
            escuela.setId_establecimiento(id);
            WritingEscuelaEntity resEscuela = driverSchoolService.update(escuela);
            if (resEscuela == null ) {
                return ResponseEntity.badRequest().body(res);
            } else {
                return ResponseEntity.ok(resEscuela);
            }

        } catch (TablaEscuelaException e) {
            logger.error("Error de servicio:" + e.getMessage());
            res.put("error", e.getMessage());
            return ResponseEntity.internalServerError().body(res);
        } catch (Exception e) {
            logger.error("Error interno del sistema:" + e.getMessage());
            res.put("error", "Error interno del sistema");
            return ResponseEntity.internalServerError().body(res);
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Map<String, String> res = validarId(id);
        try {
            if (!res.isEmpty()) {
                return ResponseEntity.badRequest().body(res);
            }
            driverSchoolService.delete(id);
            return ResponseEntity.ok().build();
        } catch (TablaEscuelaException e) {
            logger.error("Error de servicio:" + e.getMessage());
            res.put("error", e.getMessage());
            return ResponseEntity.internalServerError().body(res);
        } catch (Exception e) {
            logger.error("Error interno del sistema:" + e.getMessage());
            res.put("error", "Error interno del sistema");
            return ResponseEntity.internalServerError().body(res);
        }
    }

    private Map<String, String> validarId(Integer id) {
        Map<String, String> res = new HashMap<>();
        if (id == null || id < 0 || id > 1_000) {
            res.put("error", String
                    .format("El id=%d ingesado no es válido; este debe ser estar comprenido entre 1 y 1,000", id));
        }
        return res;
    }
}
