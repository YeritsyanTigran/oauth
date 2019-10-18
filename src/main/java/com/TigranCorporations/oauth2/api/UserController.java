package com.TigranCorporations.oauth2.api;

import com.TigranCorporations.oauth2.core.domain.dto.UserDto;
import com.TigranCorporations.oauth2.core.ex.TokenException;
import com.TigranCorporations.oauth2.core.utility.OauthHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private OauthHelper oauthHelper;

    @GetMapping
    public String processToken(@RequestParam(name = "id") String id) throws IOException, TokenException {
        return oauthHelper.getUser(id).getName();
    }
}
