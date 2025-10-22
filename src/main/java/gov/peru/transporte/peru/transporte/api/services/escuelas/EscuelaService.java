package gov.peru.transporte.peru.transporte.api.services.escuelas;

import gov.peru.transporte.peru.transporte.api.entity.WritingBaseEntity;
import gov.peru.transporte.peru.transporte.api.entity.escuelas.ReadingEscuelaEntity;
import gov.peru.transporte.peru.transporte.api.entity.escuelas.WritingEscuelaEntity;
import gov.peru.transporte.peru.transporte.api.repository.escuelas.TablaEscuelaRepository;
import gov.peru.transporte.peru.transporte.api.services.BaseService;
import org.springframework.stereotype.Service;

public interface EscuelaService extends BaseService<WritingEscuelaEntity, ReadingEscuelaEntity, Integer> {

}
