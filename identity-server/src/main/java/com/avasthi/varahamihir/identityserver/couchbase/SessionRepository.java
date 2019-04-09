/*
 * Copyright (c) 2018 Sanjnan Knowledge Technology Private Limited
 *
 * All Rights Reserved
 * This file contains software code that is proprietary and confidential.
 *  Unauthorized copying of this file, via any medium is strictly prohibited.
 *
 *  Author: vavasthi
 */

package com.avasthi.varahamihir.identityserver.couchbase;

import com.avasthi.varahamihir.common.pojos.Session;
import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Created by vinay on 1/6/16.
 */
@N1qlPrimaryIndexed
@ViewIndexed(designDoc = "session")
public interface SessionRepository extends CrudRepository<Session, String> {

  Optional<Session> findByAuthToken(String authToken);
  Optional<Session> findByRefreshToken(String refreshToken);

}
