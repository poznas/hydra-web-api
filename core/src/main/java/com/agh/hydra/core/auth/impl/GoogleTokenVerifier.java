package com.agh.hydra.core.auth.impl;

import com.agh.hydra.api.register.model.User;
import com.agh.hydra.common.model.UserId;
import com.agh.hydra.common.model.Username;
import com.agh.hydra.core.auth.service.TokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.agh.hydra.common.exception.BusinessException.INVALID_TOKEN;
import static com.agh.hydra.common.model.AuthenticationProvider.GOOGLE;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

@Component
public class GoogleTokenVerifier implements TokenVerifier {

    private static final String GOOGLE_CLIENT_ID =
            "712363122706-s6elmc4ghqdbq9s4uarm8dsoi980olfm.apps.googleusercontent.com";
    private static final List<String> issuers = asList("https://accounts.google.com", "accounts.google.com");

    @Override
    public User verifyTokenId(String tokenId) {
        return buildUser(verifyGoogleTokenId(tokenId));
    }

    private Payload verifyGoogleTokenId(String tokenId) {

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier
                .Builder(new NetHttpTransport(), new JacksonFactory())
                .setAudience(singletonList(GOOGLE_CLIENT_ID))
                .setIssuers(issuers)
                .build();

        try {
            return verifier.verify(tokenId).getPayload();
        } catch (Exception e) {
            throw INVALID_TOKEN.getException();
        }
    }

    private User buildUser(Payload payload){
        return User.builder()
                .id(UserId.of(payload.getSubject()))
                .username(Username.of((String) payload.get("name")))
                .imageUrl((String) payload.get("picture"))
                .email(payload.getEmail())
                .provider(GOOGLE)
                .build();
    }
}
