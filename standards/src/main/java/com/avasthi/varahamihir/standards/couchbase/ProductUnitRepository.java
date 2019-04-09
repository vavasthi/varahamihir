package com.avasthi.varahamihir.standards.couchbase;

import com.avasthi.varahamihir.common.pojos.ProductUnit;
import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

@N1qlPrimaryIndexed
@ViewIndexed(designDoc = "productUnit")
public interface ProductUnitRepository extends CrudRepository<ProductUnit, String> {

  Optional<ProductUnit> findByAcronym(String acronym);
}
