package gov.peru.transporte.peru.transporte.api.services.medicos;

import gov.peru.transporte.peru.transporte.api.entity.evaluacion.ReadingCentroEvaluacionEntity;
import gov.peru.transporte.peru.transporte.api.entity.evaluacion.WritingCentroEvaluacionEntity;
import gov.peru.transporte.peru.transporte.api.entity.medicos.ReadingCentroMedicoEntity;
import gov.peru.transporte.peru.transporte.api.entity.medicos.WritingCentroMedicoEntity;
import gov.peru.transporte.peru.transporte.api.repository.evaluaciones.CentroEvaluacionesRepository;
import gov.peru.transporte.peru.transporte.api.repository.medicos.CentrosMedicosRepository;
import gov.peru.transporte.peru.transporte.api.services.BaseServiceImpl;
import gov.peru.transporte.peru.transporte.api.services.evaluaciones.EvaluacionesService;
import org.springframework.stereotype.Service;

@Service
public class MedicoServiceImpl  extends BaseServiceImpl<WritingCentroMedicoEntity, ReadingCentroMedicoEntity, CentrosMedicosRepository, Integer>
        implements MedicoService {

    public MedicoServiceImpl(CentrosMedicosRepository repository) {
        super(repository);
    } }