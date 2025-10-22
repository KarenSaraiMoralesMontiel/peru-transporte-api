package gov.peru.transporte.peru.transporte.api.entity.escuelas;

import gov.peru.transporte.peru.transporte.api.entity.WritingBaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="peru_driver_escuelas")
public class WritingEscuelaEntity extends WritingBaseEntity {

   public  WritingEscuelaEntity() {
        super();
    }
}
