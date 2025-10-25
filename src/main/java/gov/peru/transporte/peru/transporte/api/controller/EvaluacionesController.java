package gov.peru.transporte.peru.transporte.api.controller;

import gov.peru.transporte.peru.transporte.api.dto.CentroDTO;
import gov.peru.transporte.peru.transporte.api.entity.evaluacion.ReadingCentroEvaluacionEntity;
import gov.peru.transporte.peru.transporte.api.entity.evaluacion.WritingCentroEvaluacionEntity;
import gov.peru.transporte.peru.transporte.api.services.escuelas.TablaEscuelaException;
import gov.peru.transporte.peru.transporte.api.services.evaluaciones.EvaluacionesServiceImpl;
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

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/evaluaciones")
public class EvaluacionesController {

    private final EvaluacionesServiceImpl driverSchoolService;
    private static final Logger logger = LoggerFactory.getLogger(EvaluacionesController.class);
    private final PagedResourcesAssembler<ReadingCentroEvaluacionEntity> pagedResourcesAssembler;
    public EvaluacionesController(EvaluacionesServiceImpl driverSchoolService, PagedResourcesAssembler<ReadingCentroEvaluacionEntity> pagedResourcesAssembler) {

        this.driverSchoolService = driverSchoolService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping("/{id}")
    public EntityModel<ReadingCentroEvaluacionEntity> findById(@PathVariable("id") Integer id) {
        ReadingCentroEvaluacionEntity evaluacion = driverSchoolService.findByIdCustom(id).orElseThrow(() ->
                new TablaEscuelaException("Not found"));

        EntityModel<ReadingCentroEvaluacionEntity> evaluacionEntityModel = EntityModel.of(
                evaluacion,
                linkTo(methodOn(EvaluacionesController.class).findById(id)).withSelfRel(),
                evaluacion.isEstado_autorizacion() ?
                        linkTo(methodOn(EvaluacionesController.class).updateEstado(id, false)).withRel("deshabilitar") :
                        linkTo(methodOn(EvaluacionesController.class).updateEstado(id, true)).withRel("habilitar")
        );

        return evaluacionEntityModel;
    }

    @GetMapping("")
    public CollectionModel<EntityModel<ReadingCentroEvaluacionEntity>> getSortedSchools(
            @RequestParam(required = false) String nombre_centro,
            @RequestParam(required = false) String provincia,
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String district,
            @RequestParam(value = "order_param", defaultValue = "id_establecimiento") String[] order_param,
            @RequestParam(value = "order", defaultValue = "ASC") String order) {

        List<ReadingCentroEvaluacionEntity> driverSchools = driverSchoolService.findBySearchParams(
                nombre_centro, department, provincia, district, order_param, order);

        // Apply multi-field sorting
        if (order_param.length > 0) {
            Comparator<ReadingCentroEvaluacionEntity> comparator = Util.applyComparator(order_param, order);
            driverSchools.sort(comparator);
        }

        List<EntityModel<ReadingCentroEvaluacionEntity>> evaluacions = driverSchools.stream()
                .map(evaluacion -> {
                    EntityModel<ReadingCentroEvaluacionEntity> evaluacionsModel = EntityModel.of(
                            evaluacion,
                            linkTo(methodOn(EvaluacionesController.class).findById(evaluacion.getId_establecimiento())).withRel("retribuirlo"),
                            linkTo(methodOn(EvaluacionesController.class).getSortedSchools( null, department,provincia, district, order_param, order)).withSelfRel(),
                            evaluacion.isEstado_autorizacion() ?
                                    linkTo(methodOn(EvaluacionesController.class).updateEstado(evaluacion.getId_establecimiento(), false)).withRel("deshabilitar") :
                                    linkTo(methodOn(EvaluacionesController.class).updateEstado(evaluacion.getId_establecimiento(), true)).withRel("habilitar")
                    );

                    return evaluacionsModel;
                }).toList();

        return CollectionModel.of(evaluacions,
                linkTo(methodOn(EvaluacionesController.class).getSortedSchools(nombre_centro, department,provincia,  district, order_param, order)).withSelfRel());
    }


    @GetMapping("/paging")
    public PagedModel<EntityModel<ReadingCentroEvaluacionEntity>> getSortedSchoolsPaging(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(required = false) String nombre_centro,
            @RequestParam(required =  false) String provincia,
            @RequestParam(required = false) String departamento,
            @RequestParam(required = false) String distrito,
            @RequestParam(value = "order_param", defaultValue = "id_establecimiento") String[] order_param,
            @RequestParam(value="order", defaultValue = "ASC") String order) {

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(order.toUpperCase()), order_param);

        Page<ReadingCentroEvaluacionEntity> driverSchools = driverSchoolService.findBySearchParamsPaging(pageable,nombre_centro, departamento, provincia, distrito, order_param, order);

        return pagedResourcesAssembler.toModel(driverSchools, evaluacion -> {
            EntityModel<ReadingCentroEvaluacionEntity> evaluacionEntityModel = EntityModel.of(evaluacion,
                    linkTo(methodOn(EvaluacionesController.class).findById(evaluacion.getId_establecimiento())).withSelfRel(),
                    linkTo(methodOn(EvaluacionesController.class).findById(evaluacion.getId_establecimiento())).withRel("retribuirlo"),
                    linkTo(methodOn(EvaluacionesController.class).getSortedSchools("", "","","",new String[] {"id_establecimiento"}, "ASC")).withRel("evaluacions"),
                    evaluacion.isEstado_autorizacion() ?
                            linkTo(methodOn(EvaluacionesController.class).updateEstado(evaluacion.getId_establecimiento(), false)).withRel("deshabilitar") :
                            linkTo(methodOn(EvaluacionesController.class).updateEstado(evaluacion.getId_establecimiento(), true)).withRel("habilitar")
            );
            return evaluacionEntityModel;
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
    public ResponseEntity<?> updatePerfil(@PathVariable Integer id, @RequestBody WritingCentroEvaluacionEntity evaluacion) {
        Map<String, String> res = validarId(id);
        try {
            if (!res.isEmpty()) {
                return ResponseEntity.badRequest().body(res);
            }
            evaluacion.setId_establecimiento(id);
            WritingCentroEvaluacionEntity resevaluacion = driverSchoolService.update(evaluacion);
            if (resevaluacion == null ) {
                return ResponseEntity.badRequest().body(res);
            } else {
                return ResponseEntity.ok(resevaluacion);
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
