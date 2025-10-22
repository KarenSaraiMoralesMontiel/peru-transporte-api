package gov.peru.transporte.peru.transporte.api.services.escuelas;

import gov.peru.transporte.peru.transporte.api.entity.ReadingBaseEntity;
import gov.peru.transporte.peru.transporte.api.entity.WritingBaseEntity;
import gov.peru.transporte.peru.transporte.api.entity.escuelas.ReadingEscuelaEntity;
import gov.peru.transporte.peru.transporte.api.entity.escuelas.WritingEscuelaEntity;
import gov.peru.transporte.peru.transporte.api.repository.BaseRepository;
import gov.peru.transporte.peru.transporte.api.repository.escuelas.TablaEscuelaRepository;
import gov.peru.transporte.peru.transporte.api.services.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class EscuelaServiceImpl
        extends BaseServiceImpl<WritingEscuelaEntity, ReadingEscuelaEntity, TablaEscuelaRepository, Integer>
        implements EscuelaService {

    public EscuelaServiceImpl(TablaEscuelaRepository repository) {
        super(repository);
    }

    // You can add escuela-specific methods here if needed
}