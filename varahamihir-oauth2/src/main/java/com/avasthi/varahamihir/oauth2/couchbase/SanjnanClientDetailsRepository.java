package com.avasthi.varahamihir.oauth2.couchbase;
import com.avasthi.varahamihir.common.pojos.SanjnanClientDetails;
import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;


@N1qlPrimaryIndexed
@ViewIndexed(designDoc = "sanjnanClientDetails", viewName = "all")
public interface SanjnanClientDetailsRepository extends CouchbaseRepository<SanjnanClientDetails, String> {

  SanjnanClientDetails findByClientId(String clientId);
}