package com.avasthi.varahamihir.standards.service;

import com.avasthi.varahamihir.standards.couchbase.ProductRepository;
import com.avasthi.varahamihir.common.exception.EntityAlreadyExistsException;
import com.avasthi.varahamihir.common.exception.EntityNotFoundException;
import com.avasthi.varahamihir.common.pojos.Product;
import com.avasthi.varahamihir.common.utils.ObjectPatcher;
import com.avasthi.varahamihir.common.utils.SanjnanMessages;
import com.avasthi.varahamihir.standards.utils.ProductSearchTrie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class ProductService {
  
  @Autowired
  private ProductRepository productRepository;

  

  public Optional<Product> getProduct(UUID id) {

      Optional<Product> productOptional = productRepository.findById(id.toString());
      if (productOptional.isPresent()) {
        return productOptional;
      }
    throw new EntityNotFoundException(String.format(SanjnanMessages.PRODUCT_NOT_FOUND, id.toString()));
  }

  public Product createProduct(Product product) {
    if (product.getEan() != null) {
      
      if (productRepository.findByEan(product.getEan()).isPresent()) {
        throw new EntityAlreadyExistsException(String.format(SanjnanMessages.PRODUCT_ALREADY_EXISTS_FOR_EAN, product.getEan()));
      }
    }
    if (product.getUpc() != null) {

      if (productRepository.findByUpc(product.getUpc()).isPresent()) {
        throw new EntityAlreadyExistsException(String.format(SanjnanMessages.PRODUCT_ALREADY_EXISTS_FOR_UPC, product.getUpc()));
      }
    }
    if (product.getId() != null) {
      if (productRepository.findById(product.getId()).isPresent()) {
        throw new EntityAlreadyExistsException(String.format(SanjnanMessages.PRODUCT_ALREADY_EXISTS_FOR_ID, product.getId()));
      }
    }
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    product.setId(UUID.randomUUID().toString());
    product = productRepository.save(product);
    return product;
  }
  public Product updateProduct(UUID id, Product product) throws InvocationTargetException, IllegalAccessException {

    Optional<Product> productOptional = productRepository.findById(id.toString());
    if (productOptional.isPresent()) {
      Product storedProduct = productOptional.get();
      ObjectPatcher.diffAndPatch(Product.class, storedProduct, Product.class, product);
      storedProduct = productRepository.save(storedProduct);
      return storedProduct;
    }
    throw new EntityNotFoundException(String.format(SanjnanMessages.PRODUCT_NOT_FOUND, product.getId()));
  }
  public Product deleteProduct(UUID id) {
    Optional<Product> ProductOptional = productRepository.findById(id.toString());
    if (ProductOptional.isPresent()) {
      Product storedProduct = ProductOptional.get();
      productRepository.deleteById(id.toString());
      return storedProduct;
    }
    throw new EntityNotFoundException(String.format(SanjnanMessages.PRODUCT_NOT_FOUND, id.toString()));
  }

  public void searchRebuild() {

    ProductSearchTrie.INSTANCE.rebuild(productRepository);
  }
  public Set<Product> search(String substring) {

    return ProductSearchTrie.INSTANCE.search(substring);
  }
}
