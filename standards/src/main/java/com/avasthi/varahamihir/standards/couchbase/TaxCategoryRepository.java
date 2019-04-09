package com.avasthi.varahamihir.standards.couchbase;

import com.avasthi.varahamihir.common.pojos.TaxCategory;
import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

@N1qlPrimaryIndexed
@ViewIndexed(designDoc = "taxCategory")
public interface TaxCategoryRepository extends CrudRepository<TaxCategory, String> {

  Optional<TaxCategory> findByName(String name);
}
