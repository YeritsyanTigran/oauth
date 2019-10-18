package com.TigranCorporations.oauth2.controller;

import com.TigranCorporations.oauth2.configuration.OauthParams;
import com.TigranCorporations.oauth2.core.utility.OauthHelper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.IOException;

@Named("homeController")
@ViewScoped
@Data
public class HomeController {
    @Autowired
    private OauthHelper oauthHelper;

    @Autowired
    private OauthParams oauthParams;
}
