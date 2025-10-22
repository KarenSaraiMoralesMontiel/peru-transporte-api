package gov.peru.transporte.peru.transporte.api.services.evaluaciones;

import gov.peru.transporte.peru.transporte.api.entity.escuelas.ReadingEscuelaEntity;
import gov.peru.transporte.peru.transporte.api.entity.escuelas.WritingEscuelaEntity;
import gov.peru.transporte.peru.transporte.api.entity.evaluacion.ReadingCentroEvaluacionEntity;
import gov.peru.transporte.peru.transporte.api.entity.evaluacion.WritingCentroEvaluacionEntity;
import gov.peru.transporte.peru.transporte.api.repository.escuelas.TablaEscuelaRepository;
import gov.peru.transporte.peru.transporte.api.repository.evaluaciones.CentroEvaluacionesRepository;
import gov.peru.transporte.peru.transporte.api.services.BaseServiceImpl;
import gov.peru.transporte.peru.transporte.api.services.escuelas.EscuelaService;
import org.springframework.stereotype.Service;

@Service
public class EvaluacionesServiceImpl   extends BaseServiceImpl<WritingCentroEvaluacionEntity, ReadingCentroEvaluacionEntity, CentroEvaluacionesRepository, Integer>
        implements EvaluacionesService {

    public EvaluacionesServiceImpl(CentroEvaluacionesRepository repository) {
        super(repository);
    }

    // You can add escuela-specific methods here if needed
}