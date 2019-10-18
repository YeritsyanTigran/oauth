package com.TigranCorporations.oauth2.controller;

import com.TigranCorporations.oauth2.core.utility.OauthParams;
import com.TigranCorporations.oauth2.core.utility.OauthHelper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named("homeController")
@ViewScoped
@Data
public class HomeController {
    @Autowired
    private OauthHelper oauthHelper;

    @Autowired
    private OauthParams oauthParams;
}
