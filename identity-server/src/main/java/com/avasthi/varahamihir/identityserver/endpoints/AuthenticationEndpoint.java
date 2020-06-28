package com.avasthi.varahamihir.identityserver.endpoints;

import com.avasthi.varahamihir.identityserver.utils.VarahamihirTokenEncoder;
import com.avasthi.varahamihir.identityserver.services.UserService;
import com.avasthi.varahamihir.identityserver.utils.VarahamihirJWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

public class AuthenticationEndpoint {
  @Autowired
  private VarahamihirJWTUtil varahamihirJwtUtil;

  @Autowired
  @Qualifier(value = "tokenEncoder")
  private VarahamihirTokenEncoder passwordEncoder;

  @Autowired
  private UserService userService;

  @Value("${varahamihir.jjwt.expiration}")
  private String expirationTime;

 /* @RequestMapping(value = "/authorize",
          method = RequestMethod.POST,
          consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public Mono<ResponseEntity<?>> login(@RequestBody VarahamihirAuthRequest ar) {
    Tenant tenant = VarahamihirRequestContext.currentTenant.get();
    return userService.findByUsername(ar.getUsername()).map((userDetails) -> {
      if (passwordEncoder.encode(ar.getPassword()).equals(userDetails.getPassword())) {
        return ResponseEntity.ok(new VarahamihirAuthResponse(UUID.randomUUID(),
                jwtUtil.generateToken(userDetails, VarahamihirTokenType.ACCESS_TOKEN),
                "Bearer",
                jwtUtil.generateToken(userDetails, VarahamihirTokenType.REFRESH_TOKEN)));
      } else {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
      }
    }).defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
  }*/
}


