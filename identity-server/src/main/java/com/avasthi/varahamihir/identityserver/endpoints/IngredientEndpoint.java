package com.avasthi.varahamihir.identityserver.endpoints;

import com.avasthi.varahamihir.identityserver.annotations.AuthorizeForRecipeEditorRole;
import com.avasthi.varahamihir.identityserver.entities.Ingredient;
import com.avasthi.varahamihir.identityserver.entities.IngredientWithNutrition;
import com.avasthi.varahamihir.identityserver.entities.Recipe;
import com.avasthi.varahamihir.identityserver.services.IngredientService;
import com.avasthi.varahamihir.identityserver.utils.Paths;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(Paths.V1.Ingredient.fullPath)
@RequiredArgsConstructor
public class IngredientEndpoint {

    private final IngredientService ingredientService;

    @RequestMapping(value = Paths.pageAndSizePath, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Ingredient> getIngredients(@PathVariable(value = Paths.pageVariable) int page,
                                       @PathVariable(Paths.sizeVariable) int size) {
        return ingredientService.findAll(page, size);
    }

    @RequestMapping(value = Paths.idPath,  method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<IngredientWithNutrition> getIngredient(@PathVariable(value = Paths.idPathVariable) @P(Paths.idPathVariable) UUID id) {
        return ingredientService.findOne(id);
    }
    @AuthorizeForRecipeEditorRole
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<Ingredient> createIngredient(@RequestBody Ingredient ingredient) {
        return ingredientService.save(ingredient);
    }
}
