package gov.peru.transporte.peru.transporte.api.entity.medicos;

import gov.peru.transporte.peru.transporte.api.entity.ReadingBaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "peru_centros_evaluacion")
public class ReadingCentroMedicoEntity extends ReadingBaseEntity {
    public ReadingCentroMedicoEntity() {
        super();
    }


}

