package com.avasthi.varahamihir.identityserver.pojo;

public record AuthToken(String username, String authToken, String refreshToken) {
}
