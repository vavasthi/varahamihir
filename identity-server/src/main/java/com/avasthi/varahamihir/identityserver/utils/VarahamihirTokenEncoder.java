package com.avasthi.varahamihir.identityserver.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class VarahamihirTokenEncoder implements PasswordEncoder {

  @Value("${varahamihir.password.encoder.secret:VarahaSecret}")
  private String secret;

  @Value("${varahamihir.password.encoder.iteration:33}")
  private Integer iteration;

  @Value("${varahamihir.password.encoder.keylength:256}")
  private Integer keylength;

  @Override
  public String encode(CharSequence charSequence) {
    try {
      byte[] result = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512")
              .generateSecret(new PBEKeySpec(charSequence.toString().toCharArray(), secret.getBytes(), iteration, keylength))
              .getEncoded();
      return Base64.getEncoder().encodeToString(result);
    } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
      throw new RuntimeException(ex);
    }
  }

  @Override
  public boolean matches(CharSequence charSequence, String s) {
    return encode(charSequence).equals(s);
  }
}
