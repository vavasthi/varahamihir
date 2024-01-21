package com.avasthi.varahamihir.identityserver.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {


    @Value("${application.security.jwt.secret-key-file}")
    private String secretKeyFile;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;
    private String pemFileContent;

    private Key getSigningKey() {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.RS256;
        byte[] apiKeySecretBytes = Decoders.BASE64.decode(pemFileContent);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        System.out.println(signingKey);
        return signingKey;
    }
    @PostConstruct
    private void initialize() {

        try {

            InputStream is = new ClassPathResource("jwtRSA256-private.pem").getInputStream();
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
    public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }
    public <T> T extractClaim(Claims claims, Function<Claims, T> claimResolver) {
        return claimResolver.apply(claims);
    }
    public Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public String generateToken(Map<String, Object> additionalClaims,
                                UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(additionalClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
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
