package com.avasthi.varahamihir.identityserver.services;

import com.avasthi.varahamihir.identityserver.entities.VarahamihirClientDetails;
import com.avasthi.varahamihir.identityserver.repositories.ClientRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
public class VarahamihirClientDetailsService implements ClientDetailsService {
  @Autowired
  private ClientRepository clientRepository;

  @Override
  public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {

    log.error("Searching for client " +clientId);
    Optional<VarahamihirClientDetails> optionalClientDetails = clientRepository.findById(clientId);
    if (optionalClientDetails.isPresent()) {
      return optionalClientDetails.get();
    }
    throw new ClientRegistrationException(String.format("%s client doesn't exist!", clientId));
  }
}
