package com.avasthi.varahamihir.identityserver.filters;

import com.avasthi.varahamihir.common.errors.ExceptionMessage;
import com.avasthi.varahamihir.common.exceptions.ExceptionResponse;
import com.avasthi.varahamihir.identityserver.services.JwtService;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
@Log4j2
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final UserDetailsService userDetailsService;
  public static final String AUTHORIZATION_HEADER = "Authorization";
  public static final String BEARER_TOKEN = "Bearer ";


  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request,
                                  @NonNull HttpServletResponse response,
                                  @NonNull FilterChain filterChain)
          throws ServletException, IOException {
    final String authHeader = request.getHeader(AUTHORIZATION_HEADER);
    if (authHeader == null || !authHeader.startsWith(BEARER_TOKEN)) {
      filterChain.doFilter(request, response);
      return;
    }
    try {

      final String jwt = authHeader.substring(BEARER_TOKEN.length());
      final Claims claims = jwtService.extractAllClaims(jwt);
      final String username = jwtService.extractUsername(claims);
      if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (jwtService.isTokenValid(claims, userDetails)) {
          UsernamePasswordAuthenticationToken authenticationToken
                  = new UsernamePasswordAuthenticationToken(userDetails, null, jwtService.extractRoles(claims));
          authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
      }
      filterChain.doFilter(request, response);
    }
    catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
      throw new RuntimeException(e);
    }
    catch (ExpiredJwtException eje) {
      returnExpiredTokenError(request, response, eje.getClaims(), eje.getHeader());
    }
  }
  private void returnExpiredTokenError(HttpServletRequest request,
                                       HttpServletResponse response,
                                       Claims claims,
                                       Header header) throws IOException {

    Date exp = claims.getExpiration();
    String user = claims.getSubject();
    Gson gson = new Gson();
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    response.getWriter().write(gson.toJson(ExceptionResponse.builder()
            .error(String.format(ExceptionMessage.EXPIRED_TOKEN.getReason(), exp, user))
            .message(String.format(ExceptionMessage.EXPIRED_TOKEN.getError(), exp, user))
            .timestamp(new Date())
            .path(request.getRequestURI())
            .requestId(request.getRequestId())
            .status(HttpStatus.UNAUTHORIZED.value())
            .build()));
  }
}
