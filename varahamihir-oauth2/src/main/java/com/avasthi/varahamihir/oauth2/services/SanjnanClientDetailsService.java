package com.avasthi.varahamihir.oauth2.services;

import com.avasthi.varahamihir.common.pojos.SanjnanClientDetails;
import com.avasthi.varahamihir.oauth2.couchbase.SanjnanClientDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class SanjnanClientDetailsService implements ClientDetailsService {

  @Autowired
  private SanjnanClientDetailsRepository clientDetailsRepository;

  @Override
  public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {

    SanjnanClientDetails client = clientDetailsRepository.findByClientId(clientId);

    String resourceIds = client.getResourceIds().stream().collect(Collectors.joining(","));
    String scopes = client.getScope().stream().collect(Collectors.joining(","));
    String grantTypes = client.getAuthorizedGrantTypes().stream().collect(Collectors.joining(","));
    String authorities = client.getAuthorities().stream().collect(Collectors.joining(","));

    BaseClientDetails base = new BaseClientDetails(client.getClientId(), resourceIds, scopes, grantTypes, authorities);
    base.setClientSecret(client.getClientSecret());
    base.setAccessTokenValiditySeconds(client.getAccessTokenValiditySeconds());
    base.setRefreshTokenValiditySeconds(client.getRefreshTokenValiditySeconds());
    base.setAdditionalInformation(client.getAdditionalInformation());
    base.setAutoApproveScopes(client.getScope());
    return base;
  }

}