package gov.peru.transporte.peru.transporte.api.controller;

import gov.peru.transporte.peru.transporte.api.entity.UbigeoEntity;
import gov.peru.transporte.peru.transporte.api.repository.UbigeoLevelProjection;
import gov.peru.transporte.peru.transporte.api.services.ubigeo.UbigeoServiceImpl;
import gov.peru.transporte.peru.transporte.api.services.escuelas.TablaEscuelaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/ubigeo")
public class UbigeoController {

    private final UbigeoServiceImpl ubigeoService;
    private static final Logger logger = LoggerFactory.getLogger(UbigeoController.class);


    public UbigeoController(UbigeoServiceImpl ubigeoService) {
        this.ubigeoService = ubigeoService;
    }

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(required = false) String distrito) {
        Map<String, String> res = new HashMap<>();
        try {
            List<UbigeoEntity> ubigeo = ubigeoService.findCustomAll(distrito);
            return ResponseEntity.ok(ubigeo);
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

    @GetMapping("/departamentos")
    public ResponseEntity<?> getDepartments() {
        Map<String, String> res = new HashMap<>();
        try {
            List<UbigeoLevelProjection> ubigeo = ubigeoService.getDepartments();
            return ResponseEntity.ok(ubigeo);
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
    @GetMapping("/provincias/{departmentId}")
    public ResponseEntity<?> getProvinces(@PathVariable String departmentId) {
        Map<String, String> res = new HashMap<>();
        try {
            List<UbigeoLevelProjection> ubigeo = ubigeoService.getProvinces(departmentId);
            return ResponseEntity.ok(ubigeo);
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

    @GetMapping("/distritos/{provinceId}")
    public ResponseEntity<?> getDistricts(@PathVariable String provinceId) {
        Map<String, String> res = new HashMap<>();
        try {
            // FIXED: Call getDistricts instead of getProvinces
            List<UbigeoLevelProjection> ubigeo = ubigeoService.getDistricts(provinceId);
            return ResponseEntity.ok(ubigeo);
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

}
