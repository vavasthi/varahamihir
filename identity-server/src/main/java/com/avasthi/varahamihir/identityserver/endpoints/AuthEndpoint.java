package com.avasthi.varahamihir.identityserver.endpoints;

import com.avasthi.varahamihir.identityserver.entities.User;
import com.avasthi.varahamihir.identityserver.pojo.AuthToken;
import com.avasthi.varahamihir.identityserver.pojo.Login;
import com.avasthi.varahamihir.identityserver.services.AuthenticateService;
import com.avasthi.varahamihir.identityserver.utils.Paths;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(Paths.V1.Base)
@RequiredArgsConstructor
public class AuthEndpoint {
    private final AuthenticateService authenticateService;
    @RequestMapping(value = Paths.V1.Auth.Base,  method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<AuthToken> login(@RequestBody Login login) {
        return authenticateService.auth(login);
    }
}
