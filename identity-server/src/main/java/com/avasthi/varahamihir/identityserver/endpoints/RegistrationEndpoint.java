package com.avasthi.varahamihir.identityserver.endpoints;

import com.avasthi.varahamihir.common.constants.VarahamihirConstants;
import com.avasthi.varahamihir.common.pojos.UserPojo;
import com.avasthi.varahamihir.identityserver.entities.User;
import com.avasthi.varahamihir.identityserver.entities.VarahamihirClientDetails;
import com.avasthi.varahamihir.identityserver.mappers.UserPojoMapper;
import com.avasthi.varahamihir.identityserver.services.ClientService;
import com.avasthi.varahamihir.identityserver.services.UserService;
import com.avasthi.varahamihir.identityserver.utils.VarahamihirRequestContext;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.Optional;

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
    public Optional<UserPojo> createUser(@PathParam(value="tenant") String tenant,
                                         @RequestBody UserPojo userPojo) {
        User user = userToPojoMapper.convert(userPojo);
        user.setTenant(VarahamihirRequestContext.currentTenant.get());
        user.setPassword(passwordEncoder.encode(userPojo.getPassword()));
        User storedUser = userService.save(user);
        storedUser.setPassword(null);
        return Optional.of(userToPojoMapper.convert(storedUser));
    }
    @RequestMapping(value = "/client", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'TENANT_ADMIN')")
    public Optional<VarahamihirClientDetails> getClient(@RequestBody VarahamihirClientDetails clientDetails) {
        return clientService.create(clientDetails);
    }
}
