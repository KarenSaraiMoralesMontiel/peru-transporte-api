package gov.peru.transporte.peru.transporte.api.entity.evaluacion;

import gov.peru.transporte.peru.transporte.api.entity.WritingBaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "peru_centros_evaluacion")
public class WritingCentroEvaluacionEntity extends WritingBaseEntity {
    public WritingCentroEvaluacionEntity() {
        super();
    }
}
