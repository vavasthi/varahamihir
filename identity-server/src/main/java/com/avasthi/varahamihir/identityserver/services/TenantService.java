package com.avasthi.varahamihir.identityserver.services;

import com.avasthi.varahamihir.identityserver.entities.Tenant;
import com.avasthi.varahamihir.identityserver.repositories.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Service
public class TenantService {
  @Autowired
  private TenantRepository tenantRepository;
  @PersistenceContext
  private EntityManager entityManager;

  public long count() {
    return tenantRepository.count();
  }
  public Iterable<Tenant> findAll() {
    return tenantRepository.findAll();
  }
  public Optional<Tenant> findTenantsByDiscriminator(String discriminator) {
    return tenantRepository.findTenantsByDiscriminator(discriminator);
  }
  public Optional<Tenant> retrieveTenantsByDiscriminator(String discriminator) {
    Optional<Tenant> optionalTenant = tenantRepository.findTenantsByDiscriminator(discriminator);
    return optionalTenant;
  }

  public Tenant save(Tenant tenant) {
    return tenantRepository.save(tenant);
  }

  public void delete(Tenant tenant) {
    tenantRepository.delete(tenant);
  }

}
