package com.avasthi.varahamihir.identityserver.endpoints;

import com.avasthi.varahamihir.common.annotations.AdminTenantAdminAllOrCurrentUserListPermission;
import com.avasthi.varahamihir.common.pojos.NutritionalUnits;
import com.avasthi.varahamihir.identityserver.annotations.AuthorizeForCurrentUser;
import com.avasthi.varahamihir.identityserver.entities.Unit;
import com.avasthi.varahamihir.identityserver.entities.User;
import com.avasthi.varahamihir.identityserver.pojo.UnitType;
import com.avasthi.varahamihir.identityserver.services.UnitService;
import com.avasthi.varahamihir.identityserver.utils.Paths;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(Paths.V1.Unit.fullPath)
@RequiredArgsConstructor
public class UnitEndpoint {

    private final UnitService unitService;

    @RequestMapping(value = Paths.V1.Unit.unitType,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UnitType> getUnitTypes() {
        return unitService.findAllUnitTypes();
    }
    @RequestMapping(value = Paths.V1.Unit.unitTypePath,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Unit> getUnits(@PathVariable(value = Paths.V1.Unit.unitType) UnitType unitType) {
        return unitService.findAll(unitType);
    }
}
