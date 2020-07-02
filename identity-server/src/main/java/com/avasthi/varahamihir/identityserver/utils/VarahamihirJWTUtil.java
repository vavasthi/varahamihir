package com.avasthi.varahamihir.identityserver.utils;

import com.avasthi.varahamihir.common.constants.VarahamihirConstants;
import com.avasthi.varahamihir.common.enums.Role;
import com.avasthi.varahamihir.common.enums.VarahamihirSubjectType;
import com.avasthi.varahamihir.common.enums.VarahamihirTokenType;
import com.avasthi.varahamihir.common.utils.VarahamihirJWTBaseUtil;
import com.avasthi.varahamihir.identityserver.entities.Tenant;
import com.avasthi.varahamihir.identityserver.entities.User;
import com.avasthi.varahamihir.identityserver.entities.VarahamihirClientDetails;
import com.nimbusds.jose.*;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.*;

@Component
@Log4j2
public class VarahamihirJWTUtil extends VarahamihirJWTBaseUtil {

  public String generateToken(Tenant tenant,
                              User user,
                              VarahamihirTokenType tokenType,
                              String audience) throws JOSEException {
    return generateToken(tenant,
            user.getUsername(),
            user.getGrantedAuthorities(),
            tokenType,
            VarahamihirSubjectType.USER,
            audience);
  }

  public String generateToken(Tenant tenant,
                              VarahamihirClientDetails clientDetails,
                              VarahamihirTokenType tokenType,
                              String audience) throws JOSEException {
    return generateToken(tenant,
            clientDetails.getClientId(),
            new HashSet<String>(Arrays.asList(Role.CLIENT.name())),
            tokenType,
            VarahamihirSubjectType.CLIENT,
            audience);
  }
  public String generateToken(Tenant tenant,
                              String username,
                              Set<String> grantedAuthorities,
                              VarahamihirTokenType tokenType,
                              VarahamihirSubjectType subjectType,
                              String audience) throws JOSEException {

    Map<String, Object> claims = new HashMap<>();
    if (tokenType == VarahamihirTokenType.REFRESH_TOKEN) {

      claims.put(VarahamihirConstants.TOKEN_ROLE_CLAIM, new HashSet<>(Arrays.asList(Role.REFRESH.name())));
    }
    else {

      claims.put(VarahamihirConstants.TOKEN_ROLE_CLAIM, grantedAuthorities);
    }
    claims.put(VarahamihirConstants.TOKEN_TYPE_CLAIM, tokenType);
    claims.put(VarahamihirConstants.TOKEN_SUBJECT_TYPE, subjectType);
    claims.put(VarahamihirConstants.TOKEN_TENANT_ID, tenant.getId());
    return doGenerateToken(tenant, claims, username, audience);
  }

  private String doGenerateToken(Tenant tenant, Map<String, Object> claims, String username, String audience)  {


    final Date createdDate = new Date();
    final Date expirationDate = new Date(createdDate.getTime() + tenant.getExpiry() * 1000);
    JWTClaimsSet.Builder claimsSetBuilder = new JWTClaimsSet.Builder();
    for (Map.Entry<String, Object> e : claims.entrySet()) {
      claimsSetBuilder.claim(e.getKey(), e.getValue());
    }
    claimsSetBuilder.subject(username)
            .issueTime(createdDate)
            .expirationTime(expirationDate)
            .issuer(issuer)
            .audience(audience);
    JWEHeader header = new JWEHeader(JWEAlgorithm.DIR, EncryptionMethod.A128CBC_HS256);
    Payload payload = new Payload(claimsSetBuilder.build().toJSONObject());
    JWEObject jweObject = new JWEObject(header, payload);
    try {

      jweObject.encrypt(getDirectEncrypter());
    }
    catch (JOSEException  e) {
      log.error("Encryption failed.", e);
    }
    return jweObject.serialize();
  }

  public Boolean validateToken(String token) throws ParseException, JOSEException, BadJOSEException {
    return !isTokenExpired(token);
  }

}
