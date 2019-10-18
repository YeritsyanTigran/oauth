package com.TigranCorporations.oauth2.api;

import com.TigranCorporations.oauth2.controller.model.Payload;
import com.TigranCorporations.oauth2.core.ex.TokenException;
import com.TigranCorporations.oauth2.core.utility.OauthHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/tokenId")
public class TokenIdController {
    @Autowired
    private OauthHelper oauthHelper;

    @PostMapping
    public Payload processToken(@RequestParam(name = "tokenId") String tokenId) throws IOException, TokenException {
        Payload payload =  oauthHelper.getPayload(tokenId);
        if(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) > payload.getExp()){
            throw new TokenException("Outdated token");
        }
        return payload;
    }
}
