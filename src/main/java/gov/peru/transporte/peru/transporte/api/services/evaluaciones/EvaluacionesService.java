package gov.peru.transporte.peru.transporte.api.services.evaluaciones;

import gov.peru.transporte.peru.transporte.api.entity.escuelas.ReadingEscuelaEntity;
import gov.peru.transporte.peru.transporte.api.entity.escuelas.WritingEscuelaEntity;
import gov.peru.transporte.peru.transporte.api.entity.evaluacion.ReadingCentroEvaluacionEntity;
import gov.peru.transporte.peru.transporte.api.entity.evaluacion.WritingCentroEvaluacionEntity;
import gov.peru.transporte.peru.transporte.api.services.BaseService;

public interface EvaluacionesService extends BaseService<WritingCentroEvaluacionEntity, ReadingCentroEvaluacionEntity, Integer> {

}