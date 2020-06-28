package com.avasthi.varahamihir.guardian.filters;

import com.avasthi.varahamihir.common.constants.VarahamihirConstants;
import com.avasthi.varahamihir.common.exceptions.InvalidTenantDiscriminatorException;
import com.avasthi.varahamihir.guardian.utils.VarahamihirRequestContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Component
@Order(VarahamihirConstants.TENANT_HEADER_PRECEDENCE)
public class TenantHeaderFilter implements Filter {
  public static final String TENANT_HEADER = "X-tenant";
  private static final String defaultClient = "supersecretclient";
  private static final String defaultSecret = "supersecretclient123";

  @Value("${tutorial.default.tenant.discriminator:default}")
  private String defaultDiscriminator;

  @Override
  public void doFilter(ServletRequest request,
                       ServletResponse response,
                       FilterChain chain) throws IOException, ServletException {

    HttpServletRequest httpServletRequest = (HttpServletRequest)request;
    HttpServletResponse httpServletResponse = (HttpServletResponse) response;
    String requestURI = httpServletRequest.getRequestURI();
    String[] dirs = requestURI.split(File.separator);
    String tenantDiscriminatorInPath = dirs[1];
    WebApplicationContext webApplicationContext
            = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
    String tenantDiscriminator = httpServletRequest.getHeader(TENANT_HEADER);
    if (requestURI.equals(VarahamihirConstants.HEALTH_ENDPOINT) || tenantDiscriminatorInPath.equals("actuator")) {

      VarahamihirRequestContext.currentTenantDiscriminator.set(defaultDiscriminator);
    }
    else if (tenantDiscriminatorInPath.equals(tenantDiscriminator)) {
      VarahamihirRequestContext.currentTenantDiscriminator.set(tenantDiscriminator);
      chain.doFilter(request, response);
    }
    else {
      httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
      httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
      Map<String, Object> errorDetail = new HashMap<>();
      errorDetail.put("message", String.format("The header X-tenant should be same as the tenant discriminator in URL. Currently are %s and %s ", tenantDiscriminator, tenantDiscriminatorInPath));
      ObjectMapper mapper = new ObjectMapper();
      mapper.writeValue(httpServletResponse.getWriter(), errorDetail);
      throw new InvalidTenantDiscriminatorException(String.format("The header X-tenant should be same as the tenant discriminator in URL. Currently are %s and %s ", tenantDiscriminator, tenantDiscriminatorInPath));
    }
  }
}