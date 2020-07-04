package com.avasthi.varahamihir.identityserver.utils;

import com.avasthi.varahamihir.common.constants.VarahamihirConstants;
import com.avasthi.varahamihir.common.enums.Role;
import com.avasthi.varahamihir.common.enums.VarahamihirSubjectType;
import com.avasthi.varahamihir.common.enums.VarahamihirTokenType;
import com.avasthi.varahamihir.common.pojos.AbstractTokenRequest;
import com.avasthi.varahamihir.common.utils.VarahamihirJWTBaseUtil;
import com.avasthi.varahamihir.identityserver.entities.Tenant;
import com.avasthi.varahamihir.identityserver.entities.User;
import com.avasthi.varahamihir.identityserver.entities.VarahamihirClientDetails;
import com.nimbusds.jose.*;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Log4j2
public class VarahamihirJWTUtil extends VarahamihirJWTBaseUtil {

  public String generateToken(Tenant tenant,
                              User user,
                              VarahamihirTokenType tokenType,
                              AbstractTokenRequest.GrantType grantType,
                              String audience,
                              String scope) throws JOSEException {
    return generateToken(tenant,
            user.getUsername(),
            user.getGrantedAuthorities(),
            tokenType,
            VarahamihirSubjectType.USER,
            grantType,
            audience,
            scope);
  }

  public String generateToken(Tenant tenant,
                              VarahamihirClientDetails clientDetails,
                              VarahamihirTokenType tokenType,
                              AbstractTokenRequest.GrantType grantType,
                              String audience,
                              String scope) throws JOSEException {
    return generateToken(tenant,
            clientDetails.getClientId(),
            new HashSet<String>(Arrays.asList(Role.CLIENT.name())),
            tokenType,
            VarahamihirSubjectType.CLIENT,
            grantType,
            audience,
            scope);
  }
  public String generateToken(Tenant tenant,
                              String username,
                              Set<String> grantedAuthorities,
                              VarahamihirTokenType tokenType,
                              VarahamihirSubjectType subjectType,
                              AbstractTokenRequest.GrantType grantType,
                              String audience,
                              String scope) throws JOSEException {

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
    claims.put(VarahamihirConstants.GRANT_TYPE_CLAIM, grantType);
    claims.put(VarahamihirConstants.TOKEN_SCOPE, scope);
    return doGenerateToken(tenant, claims, username, audience, tokenType);
  }

  private String doGenerateToken(Tenant tenant,
                                 Map<String, Object> claims,
                                 String username,
                                 String audience,
                                 VarahamihirTokenType tokenType)  {


    final Date createdDate = new Date();
    final Date expirationDate = (tokenType == VarahamihirTokenType.ACCESS_TOKEN
            ? new Date(createdDate.getTime() + tenant.getExpiry() * 1000)
            : new Date(createdDate.getTime() + tenant.getRefreshExpiry() * 1000));

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

}
