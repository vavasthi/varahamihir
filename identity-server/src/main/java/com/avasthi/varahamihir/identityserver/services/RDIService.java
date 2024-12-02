package com.avasthi.varahamihir.identityserver.services;

import com.avasthi.varahamihir.common.pojos.NutritionalUnits;
import com.avasthi.varahamihir.common.pojos.RDI;
import com.avasthi.varahamihir.identityserver.entities.Unit;
import com.avasthi.varahamihir.identityserver.pojo.Nutrient;
import com.avasthi.varahamihir.identityserver.pojo.UnitType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RDIService {

    public Map<String, Nutrient> findAll() {
        return Arrays.stream(RDI.values())
                .collect(Collectors.toMap(e -> e.name(),
                        e -> new Nutrient(e.name(), e.getNutrientName(), e.getQuantity(), e.getUnit())));
    }
}
