package com.avasthi.varahamihir.identityserver.endpoints;

import com.avasthi.varahamihir.identityserver.annotations.AuthorizeForEquationEditorRole;
import com.avasthi.varahamihir.identityserver.annotations.AuthorizeForRecipeEditorRole;
import com.avasthi.varahamihir.identityserver.entities.Equation;
import com.avasthi.varahamihir.identityserver.entities.Recipe;
import com.avasthi.varahamihir.identityserver.services.EquationService;
import com.avasthi.varahamihir.identityserver.services.RecipeService;
import com.avasthi.varahamihir.identityserver.utils.Paths;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(Paths.V1.Equation.fullPath)
@RequiredArgsConstructor
public class EquationEndpoint {

    private final EquationService equationService;

    @RequestMapping(value = Paths.pageAndSizePath, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Equation> getRecipes(@PathVariable(value = Paths.pageVariable) int page,
                                     @PathVariable(Paths.sizeVariable) int size) {
        return equationService.findAll(page, size);
    }

    @RequestMapping(value = Paths.idPath,  method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<Equation> getUser(@PathVariable(value = Paths.idPathVariable) @P(Paths.idPathVariable) UUID id) {
        return equationService.findOne(id);
    }
    @AuthorizeForEquationEditorRole
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<Equation> createEquation(@RequestBody Equation equation) {
        return equationService.save(equation);
    }
    @AuthorizeForEquationEditorRole
    @RequestMapping(value = "list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Equation> createEquations(@RequestBody List<Equation> equations) {
        return equationService.save(equations);
    }
}
