package com.avasthi.varahamihir.identityserver.services;

import com.avasthi.varahamihir.common.pojos.NutritionalUnits;
import com.avasthi.varahamihir.identityserver.entities.Unit;
import com.avasthi.varahamihir.identityserver.pojo.UnitType;
import com.avasthi.varahamihir.identityserver.repositories.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UnitService {

    public Map<String, Unit> findAll() {

        return Arrays.stream(NutritionalUnits.values()).collect(Collectors.toMap(n -> n.name(), n -> Unit.builder()
                .acronym(n.getAcronym())
                .value(n.getValue())
                .conversionToPrimary(n.getConversionToPrimary())
                .unitSystem(n.getUnitSystems())
                .quantityType(n.getQuantityType())
                .primary(n.getPrimary() == null ? n.name() : n.getPrimary().name())
                .build()));
        /*List<Unit> returnValue = new ArrayList<>();
                Arrays.stream(NutritionalUnits.values()).map(v -> Unit.builder()
                                .acronym(v.getAcronym())
                                .value(v.getValue())
                                .conversionToPrimary(v.getConversionToPrimary())
                                .unitSystem(v.getUnitSystems())
                                .quantityType(v.getQuantityType())
                                .primary(v.getPrimary() == null ? null : v.getPrimary().name())
                                .build())
                        .forEach(u -> returnValue.add(u));

        return returnValue;*/
    }

}
