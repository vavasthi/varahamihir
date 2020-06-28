package com.avasthi.varahamihir.identityserver.endpoints;

import com.avasthi.varahamihir.common.constants.VarahamihirConstants;
import com.avasthi.varahamihir.common.pojos.UserPojo;
import com.avasthi.varahamihir.identityserver.entities.User;
import com.avasthi.varahamihir.identityserver.entities.VarahamihirClientDetails;
import com.avasthi.varahamihir.identityserver.mappers.UserPojoMapper;
import com.avasthi.varahamihir.identityserver.services.ClientService;
import com.avasthi.varahamihir.identityserver.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Log4j2
@RestController
@RequestMapping(VarahamihirConstants.V1_REGISTRATION_ENDPOINT)
public class RegistrationEndpoint {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserPojoMapper userToPojoMapper;
    @Autowired
    private ClientService clientService;


    @RequestMapping(value = "/user", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<UserPojo> createUser(@PathVariable(value="tenant") String tenant,
                                     @RequestBody UserPojo userPojo) {
        User user = userToPojoMapper.convert(userPojo);
        user.setPassword(passwordEncoder.encode(userPojo.getPassword()));
        Mono<User> monoStoredUser = userService.save(user);
        return Mono.just(userToPojoMapper.convert(monoStoredUser.block()));
    }
    @RequestMapping(value = "/client", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'TENANT_ADMIN')")
    public Mono<VarahamihirClientDetails> getClient(@RequestBody VarahamihirClientDetails clientDetails) {
        return clientService.create(clientDetails);
    }
}
