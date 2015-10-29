package com.tracebucket.idem.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by sadath on 29-Oct-2015.
 */
public class CustomProviderManager extends ProviderManager {

    public CustomProviderManager() {

    }

    public CustomProviderManager(List<AuthenticationProvider> providers) {
        super(providers);
    }

    public CustomProviderManager(List<AuthenticationProvider> providers, AuthenticationManager parent) {
        super(providers, parent);
    }




}