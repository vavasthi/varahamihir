package com.avasthi.varahamihir.oauth2.couchbase;
import com.avasthi.varahamihir.common.pojos.VarahamihirClientDetails;
import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;


@N1qlPrimaryIndexed
@ViewIndexed(designDoc = "varahamihirClientDetails", viewName = "all")
public interface VarahamihirClientDetailsRepository extends CouchbaseRepository<VarahamihirClientDetails, String> {

  VarahamihirClientDetails findByClientId(String clientId);
}