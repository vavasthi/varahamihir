/*
 * Copyright (c) 2018 Sanjnan Knowledge Technology Private Limited
 *
 * All Rights Reserved
 * This file contains software code that is proprietary and confidential.
 *  Unauthorized copying of this file, via any medium is strictly prohibited.
 *
 *  Author: vavasthi
 */

package com.avasthi.varahamihir.common.utils;

import org.apache.log4j.Logger;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

/**
 * Created by vinay on 2/8/16.
 */
public class VarahamihirPasswordEncryptionManager {

  public static VarahamihirPasswordEncryptionManager INSTANCE = new VarahamihirPasswordEncryptionManager();

  private Logger logger = Logger.getLogger(VarahamihirPasswordEncryptionManager.class);
  private final SCryptPasswordEncoder encoder = new SCryptPasswordEncoder();

  private VarahamihirPasswordEncryptionManager() {

  }
  public String encrypt(String password) {

    return encoder(password);
  }

  public boolean matches(String rawPassword, String encryptedPassword) {
    return encoder.matches(rawPassword, encryptedPassword);
  }

  private String encoder(String password) {
    return encoder.encode(password);
  }
}
