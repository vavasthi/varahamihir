/*
 * Copyright (c) 2018 Sanjnan Knowledge Technology Private Limited
 *
 * All Rights Reserved
 * This file contains software code that is proprietary and confidential.
 *  Unauthorized copying of this file, via any medium is strictly prohibited.
 *
 *  Author: vavasthi
 */

package com.avasthi.varahamihir.common.config;

import com.avasthi.varahamihir.common.pojos.*;
import com.couchbase.client.java.Bucket;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;
import org.springframework.data.couchbase.repository.config.RepositoryOperationsMapping;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableCouchbaseRepositories(basePackages = {"com.avasthi.varahamihir"})
@EnableJpaRepositories
public class VarahamihirCouchbaseConfig extends AbstractCouchbaseConfiguration  {

  @Value("${com.avasthi.varahamihir.common.couchbase.bootstraphosts:localhost:8082}")
  private String bootStrapHosts;

  @Value("${com.avasthi.varahamihir.common.couchbase.bucketName:identity}")
  private String bucketName;

  @Value("${com.avasthi.varahamihir.common.couchbase.bucketPassword:identity123}")
  private String bucketPassword;

  @Value("${com.avasthi.varahamihir.common.couchbase.username:identity}")
  private String couchbaseUsername;

  @Value("${com.avasthi.varahamihir.common.couchbase.password:identity123}")
  private String couchBasePassword;

  @Override
  protected List<String> getBootstrapHosts() {
    return  Arrays.asList(bootStrapHosts.split(","));
  }

  @Override
  protected String getBucketName() {
    return bucketName;
  }

  @Override
  protected String getBucketPassword() {
    return bucketPassword;
  }

  @Bean
  public Bucket accountBucket() throws Exception {

    return couchbaseCluster().openBucket("accounts");
  }
  @Bean
  public Bucket sessionBucket() throws Exception {
    return couchbaseCluster().openBucket("sessions");
  }
  @Bean
  public Bucket productBucket() throws Exception {
    return couchbaseCluster().openBucket("products");
  }
  @Bean
  public CouchbaseTemplate accountTemplate() throws Exception {
    CouchbaseTemplate template
            = new CouchbaseTemplate(couchbaseCluster().authenticate(couchbaseUsername, couchBasePassword).clusterManager().info(),
            accountBucket(),
            mappingCouchbaseConverter(),
            translationService());
    template.setDefaultConsistency(getDefaultConsistency());
    return template;
  }
  @Bean
  public CouchbaseTemplate sessionTemplate() throws Exception {
    CouchbaseTemplate template = new CouchbaseTemplate(
            couchbaseCluster().authenticate(couchbaseUsername, couchBasePassword).clusterManager().info(),
            sessionBucket(),
            mappingCouchbaseConverter(), translationService());
    template.setDefaultConsistency(getDefaultConsistency());
    return template;
  }
  @Bean
  public CouchbaseTemplate productTemplate() throws Exception {
    CouchbaseTemplate template = new CouchbaseTemplate(
            couchbaseCluster().authenticate(couchbaseUsername, couchBasePassword).clusterManager().info(),
            productBucket(),
            mappingCouchbaseConverter(), translationService());
    template.setDefaultConsistency(getDefaultConsistency());
    return template;
  }
  @Override
  public void configureRepositoryOperationsMapping(
          RepositoryOperationsMapping baseMapping) {
    try {
      baseMapping.mapEntity(Account.class, accountTemplate());
      baseMapping.mapEntity(Session.class, sessionTemplate());
      baseMapping.mapEntity(CouchbaseAccessToken.class, sessionTemplate());
      baseMapping.mapEntity(CouchbaseRefreshToken.class, sessionTemplate());
      baseMapping.mapEntity(VarahamihirClientDetails.class, sessionTemplate());
      baseMapping.mapEntity(Product.class, productTemplate());
      baseMapping.mapEntity(ProductLocation.class, productTemplate());
      baseMapping.mapEntity(TaxCategory.class, productTemplate());
      baseMapping.mapEntity(TaxSurcharge.class, productTemplate());
      baseMapping.mapEntity(ProductUnit.class, productTemplate());
    } catch (Exception e) {
      //custom Exception handling
    }
  }
}
