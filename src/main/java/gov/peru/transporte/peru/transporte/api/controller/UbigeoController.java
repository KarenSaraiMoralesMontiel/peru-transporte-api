package gov.peru.transporte.peru.transporte.api.controller;

import gov.peru.transporte.peru.transporte.api.entity.UbigeoEntity;
import gov.peru.transporte.peru.transporte.api.services.ubigeo.UbigeoServiceImpl;
import gov.peru.transporte.peru.transporte.api.services.escuelas.TablaEscuelaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
