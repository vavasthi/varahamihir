package com.avasthi.varahamihir.identityserver.utils;

import com.avasthi.varahamihir.common.constants.VarahamihirConstants;
import com.avasthi.varahamihir.common.enums.Role;
import com.avasthi.varahamihir.common.enums.VarahamihirSubjectType;
import com.avasthi.varahamihir.common.pojos.TokenClaims;
import com.avasthi.varahamihir.common.pojos.VarahamihirGrantedAuthority;
import com.avasthi.varahamihir.common.pojos.VarahamihirOAuth2Principal;
import com.avasthi.varahamihir.identityserver.entities.Tenant;
import com.avasthi.varahamihir.identityserver.entities.User;
import com.avasthi.varahamihir.common.enums.VarahamihirTokenType;
import com.avasthi.varahamihir.identityserver.entities.VarahamihirClientDetails;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.DirectDecrypter;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jose.proc.JWEDecryptionKeySelector;
import com.nimbusds.jose.proc.JWEKeySelector;
import com.nimbusds.jose.proc.SimpleSecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.BadJWTException;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Log4j2
public class VarahamihirJWTUtil {

  @Value("${varahamihir.password.encoder.secret:MySecretIsBetterThanYourSecret}")
  private String secret;
  @Value("${varahamihir.claims.issuer:cto@varahamihir.com}")
  private String issuer;

  public JWTClaimsSet getAllClaimsFromToken(String token) throws ParseException, JOSEException, BadJOSEException {

    JWTClaimsSet claimsSet = jwtProcessor().process(token, null);
    return claimsSet;
  }

  public String getUsernameFromToken(String token) throws ParseException, JOSEException, BadJOSEException {
    return getAllClaimsFromToken(token).getSubject();
  }

  public Date getExpirationDateFromToken(String token) throws ParseException, JOSEException, BadJOSEException {
    return getAllClaimsFromToken(token).getExpirationTime();
  }

  private Boolean isTokenExpired(String token) throws ParseException, JOSEException, BadJOSEException {
    try {

      final Date expiration = getExpirationDateFromToken(token);
      return expiration.before(new Date());
    }
    catch(BadJWTException e) {
      return false;
    }
  }

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
    claims.put(VarahamihirConstants.TOKEN_ROLE_CLAIM, grantedAuthorities);
    claims.put(VarahamihirConstants.TOKEN_TYPE_CLAIM, tokenType);
    claims.put(VarahamihirConstants.TOKEN_SUBJECT_TYPE, subjectType);
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

  private ConfigurableJWTProcessor<SimpleSecurityContext> jwtProcessor() {
    ConfigurableJWTProcessor<SimpleSecurityContext> jwtProcessor = new DefaultJWTProcessor<SimpleSecurityContext>();
    JWKSource<SimpleSecurityContext> jweKeySource = new ImmutableSecret<SimpleSecurityContext>(getSecret());
    JWEKeySelector<SimpleSecurityContext> jweKeySelector =
            new JWEDecryptionKeySelector<SimpleSecurityContext>(JWEAlgorithm.DIR, EncryptionMethod.A128CBC_HS256, jweKeySource);
    jwtProcessor.setJWEKeySelector(jweKeySelector);
    return jwtProcessor;
  }


  public TokenClaims retrieveTokenClaims(String authToken) throws ParseException, JOSEException, BadJOSEException {
    JWTClaimsSet claims = getAllClaimsFromToken(authToken);
    List<String> rolesMap = (List<String>) claims.getClaims().get(VarahamihirConstants.TOKEN_ROLE_CLAIM);
    List<GrantedAuthority> authorities = new ArrayList<>();
    if (rolesMap != null) {

      for (String rolemap : rolesMap) {
        authorities.add(new VarahamihirGrantedAuthority(rolemap));
      }
    }
    VarahamihirSubjectType subjectType
            = VarahamihirSubjectType.valueOf((String)claims.getClaims().get(VarahamihirConstants.TOKEN_SUBJECT_TYPE));
    VarahamihirTokenType tokenType
            = VarahamihirTokenType.valueOf((String)claims.getClaims().get(VarahamihirConstants.TOKEN_TYPE_CLAIM));
    return TokenClaims.builder()
            .authorities(authorities)
            .subject(claims.getSubject())
            .issuer(claims.getIssuer())
            .audience(claims.getAudience())
            .authToken(authToken)
            .tokenType(tokenType)
            .subjectType(subjectType)
            .build();

  }
  private DirectEncrypter getDirectEncrypter() throws KeyLengthException {

    return new DirectEncrypter(getSecret());
  }
  private DirectDecrypter getDirectDecrypter() throws KeyLengthException {

    return new DirectDecrypter(getSecret());
  }
  private byte[] getSecret() {
    int keyBitLength = EncryptionMethod.A128CBC_HS256.cekBitLength();
    return secret.substring(0, keyBitLength/8).getBytes();
  }
  public static String getAuthorizationPayload(ServerWebExchange serverWebExchange) {
    return serverWebExchange.getRequest()
            .getHeaders()
            .getFirst(HttpHeaders.AUTHORIZATION);
  }
  public Authentication getAuthenticationToken(TokenClaims tokenClaims, String authToken) {
    VarahamihirOAuth2Principal principal
            = VarahamihirOAuth2Principal.builder()
            .username(tokenClaims.getSubject())
            .authToken(authToken)
            .tokenType(tokenClaims.getTokenType())
            .subjectType(tokenClaims.getSubjectType())
            .grantedAuthorities(tokenClaims.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.toSet()))
            .build();
    return new UsernamePasswordAuthenticationToken(principal, authToken, tokenClaims.getAuthorities());
  }
}
