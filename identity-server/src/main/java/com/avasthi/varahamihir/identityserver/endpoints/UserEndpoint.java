package com.avasthi.varahamihir.identityserver.endpoints;

import com.avasthi.varahamihir.common.annotations.AdminTenantAdminAllOrCurrentUserListPermission;
import com.avasthi.varahamihir.common.annotations.AdminTenantAdminOrCurrentUserBodyPermission;
import com.avasthi.varahamihir.identityserver.entities.Role;
import com.avasthi.varahamihir.identityserver.entities.User;
import com.avasthi.varahamihir.identityserver.services.UserService;
import com.avasthi.varahamihir.identityserver.utils.Paths;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(Paths.Users.Base)
@RequiredArgsConstructor
public class UserEndpoint {

    private final UserService userService;

    @AdminTenantAdminAllOrCurrentUserListPermission
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getUsers() {
        return userService.findAll();
    }
    @AdminTenantAdminOrCurrentUserBodyPermission
    @RequestMapping(value = Paths.Users.V1.GetOne,  method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<User> getUser(@PathParam("username") String username) {
        return userService.findOne(username);
    }
    @RequestMapping(value = Paths.Users.V1.Base,  method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<User> createUser(@RequestBody User user) {
        return userService.save(user);
    }
}
