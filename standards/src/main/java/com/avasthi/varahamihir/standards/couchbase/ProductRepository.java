package com.avasthi.varahamihir.standards.couchbase;

import com.avasthi.varahamihir.common.pojos.Product;
import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

@N1qlPrimaryIndexed
@ViewIndexed(designDoc = "product")
public interface ProductRepository extends CrudRepository<Product, String> {

  Optional<Product> findByEan(String ean);
  Optional<Product> findByUpc(String upc);
}
