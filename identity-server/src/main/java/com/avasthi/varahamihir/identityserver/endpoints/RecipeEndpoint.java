package com.avasthi.varahamihir.identityserver.endpoints;

import com.avasthi.varahamihir.common.annotations.AdminTenantAdminAllOrCurrentUserListPermission;
import com.avasthi.varahamihir.identityserver.annotations.AuthorizeForCurrentUser;
import com.avasthi.varahamihir.identityserver.annotations.AuthorizeForRecipeEditorRole;
import com.avasthi.varahamihir.identityserver.entities.Recipe;
import com.avasthi.varahamihir.identityserver.entities.User;
import com.avasthi.varahamihir.identityserver.services.RecipeService;
import com.avasthi.varahamihir.identityserver.services.UserService;
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
@RequestMapping(Paths.V1.Recipe.fullPath)
@RequiredArgsConstructor
public class RecipeEndpoint {

    private final RecipeService recipeService;

    @RequestMapping(value = Paths.pageAndSizePath, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Recipe> getRecipes(@PathVariable(value = Paths.pageVariable) int page,
                                   @PathVariable(Paths.sizeVariable) int size) {
        return recipeService.findAll(page, size);
    }

    @RequestMapping(value = Paths.idPath,  method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<Recipe> getUser(@PathVariable(value = Paths.idPathVariable) @P(Paths.idPathVariable) UUID id) {
        return recipeService.findOne(id);
    }
    @AuthorizeForRecipeEditorRole
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<Recipe> createRecipe(@RequestBody Recipe recipe) {
        return recipeService.save(recipe);
    }
}
