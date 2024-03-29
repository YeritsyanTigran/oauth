package com.TigranCorporations.oauth2.api;

import com.TigranCorporations.oauth2.core.ex.TokenException;
import com.TigranCorporations.oauth2.core.utility.OauthHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/code")
public class CodeController {
    @Autowired
    private OauthHelper oauthHelper;

    @PostMapping
    public String processCode(@RequestParam(name = "code") String code) throws IOException, TokenException {
        return oauthHelper.getCredentials(code).getAccountId();
    }
}
