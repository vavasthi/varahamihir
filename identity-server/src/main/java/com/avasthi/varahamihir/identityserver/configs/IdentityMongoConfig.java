package com.avasthi.varahamihir.identityserver.configs;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.UuidRepresentation;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import static java.util.Collections.singletonList;

@Configuration
@EnableMongoRepositories(basePackages = {"com.avasthi.varahamihir.identityserver.repositories"}, mongoTemplateRef = "identityMongoTemplate")
@EnableConfigurationProperties
public class IdentityMongoConfig {
    @Bean(name = "identityMongoProperties")
    @ConfigurationProperties(prefix = "mongodb.identity")
    @Primary
    public MongoProperties primaryProperties() {
        return new MongoProperties();
    }

    @Bean(name = "identityMongoClient")
    public MongoClient mongoClient(@Qualifier("identityMongoProperties") MongoProperties mongoProperties) {

        MongoCredential credential = MongoCredential
                .createCredential(mongoProperties.getUsername(), mongoProperties.getAuthenticationDatabase(), mongoProperties.getPassword());

        return MongoClients.create(MongoClientSettings.builder()
                        .uuidRepresentation(UuidRepresentation.STANDARD)
                        .applyConnectionString(new ConnectionString(mongoProperties.getUri()))
//                .applyToClusterSettings(builder -> builder
//                        .hosts(singletonList(new ServerAddress(mongoProperties.getHost(), mongoProperties.getPort()))))
                .credential(credential)
                .build());
    }
    @Primary
    @Bean(name = "identityMongoDBFactory")
    public MongoDatabaseFactory mongoDatabaseFactory(
            @Qualifier("identityMongoClient") MongoClient mongoClient,
            @Qualifier("identityMongoProperties") MongoProperties mongoProperties) {
        return new SimpleMongoClientDatabaseFactory(mongoClient, mongoProperties.getDatabase());
    }
    @Primary
    @Bean(name = "identityMongoTemplate")
    public MongoOperations mongoTemplate(@Qualifier("identityMongoDBFactory") MongoDatabaseFactory databaseFactory,
                                         MappingMongoConverter converter) {
        return new MongoTemplate(databaseFactory, converter);
    }
}