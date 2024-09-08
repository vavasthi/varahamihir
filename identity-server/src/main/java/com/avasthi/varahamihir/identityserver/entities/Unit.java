package com.avasthi.varahamihir.identityserver.entities;

import com.avasthi.varahamihir.common.pojos.QUANTITY_TYPE;
import com.avasthi.varahamihir.common.pojos.UNIT_SYSTEM;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class Unit {
    private String value;
    private String acronym;
    private double conversionToPrimary;
    private UNIT_SYSTEM unitSystem;
    private QUANTITY_TYPE quantityType;
    private String primary;
}
