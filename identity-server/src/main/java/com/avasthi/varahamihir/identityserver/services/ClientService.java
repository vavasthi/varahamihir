package com.avasthi.varahamihir.identityserver.services;

import com.avasthi.varahamihir.identityserver.entities.VarahamihirClientDetails;
import com.avasthi.varahamihir.common.exceptions.EntityAlreadyExistsException;
import com.avasthi.varahamihir.common.exceptions.NotFoundException;
import com.avasthi.varahamihir.identityserver.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {
  @Autowired
  private ClientRepository clientRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public Iterable<VarahamihirClientDetails> findAll() {
    return clientRepository.findAll();
  }

  public Optional<VarahamihirClientDetails> findById(String clientId) {
    return clientRepository.findById(clientId);
  }

  public Optional<VarahamihirClientDetails> create(VarahamihirClientDetails clientDetails) {

    Optional<VarahamihirClientDetails> optionalOauthClientDetails
            = clientRepository.findById(clientDetails.getClientId());
    if (optionalOauthClientDetails.isPresent()) {
      throw new EntityAlreadyExistsException(String.format("%s is not a valid client id.", clientDetails.getClientId()));
    }
    clientDetails.setClientSecret(passwordEncoder.encode(clientDetails.getClientSecret()));
    clientDetails = clientRepository.save(clientDetails);
    return Optional.of(clientDetails);
  }

  public Optional<VarahamihirClientDetails> update(String clientId, VarahamihirClientDetails clientDetails) {

    Optional<VarahamihirClientDetails> optionalOauthClientDetails
            = clientRepository.findById(clientId);
    if (!optionalOauthClientDetails.isPresent()) {
      throw new NotFoundException(String.format("%s is not a valid client id.", clientId));
    }
    VarahamihirClientDetails storedClientDetails = optionalOauthClientDetails.get();
    storedClientDetails.setClientSecret(passwordEncoder.encode(clientDetails.getClientSecret()));
    return Optional.of(clientRepository.save(storedClientDetails));
  }

  public long count() {
    return clientRepository.count();
  }
}
