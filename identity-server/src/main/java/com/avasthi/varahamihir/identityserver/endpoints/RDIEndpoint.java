package com.avasthi.varahamihir.identityserver.endpoints;

import com.avasthi.varahamihir.identityserver.entities.Unit;
import com.avasthi.varahamihir.identityserver.pojo.Nutrient;
import com.avasthi.varahamihir.identityserver.services.RDIService;
import com.avasthi.varahamihir.identityserver.services.UnitService;
import com.avasthi.varahamihir.identityserver.utils.Paths;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(Paths.V1.RDI.fullPath)
@RequiredArgsConstructor
public class RDIEndpoint {

    private final RDIService rdiService;

    @RequestMapping(method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Nutrient> getRDI() {
        return rdiService.findAll();
    }
}
