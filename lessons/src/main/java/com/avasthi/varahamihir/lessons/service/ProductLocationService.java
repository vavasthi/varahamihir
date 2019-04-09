package com.avasthi.varahamihir.lessons.service;

import com.avasthi.varahamihir.lessons.couchbase.ProductRepository;
import com.avasthi.varahamihir.common.exception.EntityAlreadyExistsException;
import com.avasthi.varahamihir.common.exception.EntityNotFoundException;
import com.avasthi.varahamihir.common.pojos.Product;
import com.avasthi.varahamihir.common.utils.ObjectPatcher;
import com.avasthi.varahamihir.common.utils.SanjnanMessages;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductLocationService {
  
  @Autowired
  private ProductRepository productRepository;
  
  Logger logger = Logger.getLogger(ProductLocationService.class);

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

}
