package com.avasthi.varahamihir.standards.couchbase;

import com.avasthi.varahamihir.common.pojos.TaxSurcharge;
import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

@N1qlPrimaryIndexed
@ViewIndexed(designDoc = "taxSurcharge")
public interface TaxSurchargeRepository extends CrudRepository<TaxSurcharge, String> {

  Optional<TaxSurcharge> findByName(String name);
}
