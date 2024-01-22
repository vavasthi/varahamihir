package com.avasthi.varahamihir.identityserver.endpoints;

import com.avasthi.varahamihir.common.annotations.AdminTenantAdminAllOrCurrentUserListPermission;
import com.avasthi.varahamihir.identityserver.annotations.AuthorizeForCurrentUser;
import com.avasthi.varahamihir.identityserver.entities.User;
import com.avasthi.varahamihir.identityserver.services.UserService;
import com.avasthi.varahamihir.identityserver.utils.Paths;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(Paths.V1.Base)
@RequiredArgsConstructor
public class UserEndpoint {

    private final UserService userService;

    @AdminTenantAdminAllOrCurrentUserListPermission
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getUsers() {
        return userService.findAll();
    }


    @AuthorizeForCurrentUser
    @RequestMapping(value = Paths.V1.Users.GetOne,  method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<User> getUser(@PathVariable(value = "username") @P("username") String username) {
        return userService.findOne(username);
    }
    @RequestMapping(value = Paths.V1.Users.Base,  method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<User> createUser(@RequestBody User user) {
        return userService.save(user);
    }
}
