package com.avasthi.varahamihir.identityserver.services;

import com.avasthi.varahamihir.common.pojos.NutritionalUnits;
import com.avasthi.varahamihir.identityserver.entities.Unit;
import com.avasthi.varahamihir.identityserver.pojo.UnitType;
import com.avasthi.varahamihir.identityserver.repositories.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UnitService {

    public List<Unit> findAll() {
        List<Unit> returnValue = new ArrayList<>();
                Arrays.stream(NutritionalUnits.values()).map(v -> Unit.builder()
                                .acronym(v.getAcronym())
                                .value(v.getValue())
                                .conversionToPrimary(v.getConversionToPrimary())
                                .unitSystem(v.getUnitSystems())
                                .quantityType(v.getQuantityType())
                                .primary(v.getPrimary() == null ? null : v.getPrimary().name())
                                .build())
                        .forEach(u -> returnValue.add(u));

        return returnValue;
    }

    public List<UnitType> findAllUnitTypes() {
        return Arrays.stream(UnitType.values()).toList();
    }
}
