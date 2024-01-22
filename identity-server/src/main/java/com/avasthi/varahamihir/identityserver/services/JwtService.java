package com.avasthi.varahamihir.identityserver.services;

import com.avasthi.varahamihir.common.errors.ExceptionMessage;
import com.avasthi.varahamihir.common.exceptions.TokenSigningException;
import com.avasthi.varahamihir.identityserver.entities.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtService {


    @Value("${application.security.jwt.secret-key-file}")
    private String secretKeyFile;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;
    private String pemFileContent;

    private Key getSigningKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.RS256;
        byte[] apiKeySecretBytes = Decoders.BASE64.decode(pemFileContent);
        return Keys.hmacShaKeyFor(apiKeySecretBytes);
    }
    @PostConstruct
    private void initialize() {

        try {

            InputStream is = new ClassPathResource(secretKeyFile).getInputStream();
            byte[] bytes = is.readAllBytes();
            pemFileContent = new String(bytes)
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replace("\n", "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public String extractUsername(Claims claims) {
        return extractClaim(claims, Claims::getSubject);
    }
    public List<GrantedAuthority> extractRoles(Claims claims) {
        return ((List<String>)claims.get("roles")).stream().map(r -> new SimpleGrantedAuthority(r)).collect(Collectors.toList());
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimResolver) throws NoSuchAlgorithmException, InvalidKeySpecException {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }
    public <T> T extractClaim(Claims claims, Function<Claims, T> claimResolver) {
        return claimResolver.apply(claims);
    }
    public Claims extractAllClaims(String token) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public JwtBuilder generateBaseToken(Map<String, Object> additionalClaims,
                                        UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(additionalClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date());
    }
    public String generateAuthToken(UserDetails userDetails) {
        try {

            Map<String, Object> additionalClaims = new HashMap<>();
            additionalClaims.put("roles", userDetails.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.toList()));
            return generateBaseToken(additionalClaims, userDetails)
                    .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                    .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                    .compact();
        }
        catch( NoSuchAlgorithmException| InvalidKeySpecException e) {
            throw new TokenSigningException(ExceptionMessage.TOKEN_SIGNING_ERROR.getReason(), ExceptionMessage.TOKEN_SIGNING_ERROR.getError(), e);
        }
    }
    public String generateRefreshToken(UserDetails userDetails) {
        try {

            Map<String, Object> refreshAdditionalClaims = new HashMap<>();
            refreshAdditionalClaims.put("roles", List.of(Role.REFRESH));
            return generateBaseToken(refreshAdditionalClaims, userDetails)
                    .setExpiration(new Date(System.currentTimeMillis() + refreshExpiration))
                    .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                    .compact();
        }
        catch( NoSuchAlgorithmException| InvalidKeySpecException e) {
            throw new TokenSigningException(ExceptionMessage.TOKEN_SIGNING_ERROR.getReason(), ExceptionMessage.TOKEN_SIGNING_ERROR.getError(), e);
        }
    }
    public boolean isTokenValid(Claims claims, UserDetails userDetails) {
        final String tokenUsername = extractUsername(claims);
        return tokenUsername.equalsIgnoreCase(userDetails.getUsername()) && !isTokenExpired(claims);
    }
    private boolean isTokenExpired(Claims claims) {
        Date expiry = extractClaim(claims, Claims::getExpiration);
        return expiry.before(new Date());
    }
}
