package gov.peru.transporte.peru.transporte.api.entity.medicos;

import gov.peru.transporte.peru.transporte.api.entity.WritingBaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "peru_centros_evaluacion")
public class WritingCentroMedicoEntity extends WritingBaseEntity {
    public WritingCentroMedicoEntity() {
        super();
    }
}
