package com.tracebucket.idem.service.impl;

import com.tracebucket.idem.domain.Client;
import com.tracebucket.idem.domain.Tenant;
import com.tracebucket.idem.domain.User;
import com.tracebucket.idem.repository.jpa.ClientRepository;
import com.tracebucket.idem.repository.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author FFL
 * @since 17/03/15
 * Enhance Access Token with Tenant information.
 */
@Service("tenantTokenEnhancer")
public class TenantTokenEnhancer implements TokenEnhancer{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        String userName = authentication.isClientOnly() ? null : authentication.getName();
        User user = userRepository.findByUsername(userName);

        String clientId = authentication.getOAuth2Request().getClientId();
        Client client = clientRepository.findByClientId(clientId);

        Tenant tenant = client.getTenant();

        Map<String, Object> tenantInformation = user.getTenantInformation();

        if(tenantInformation != null && tenantInformation.containsKey("TENANT_ID")) {
            Set<Tenant> tenants = (Set<Tenant>)tenantInformation.get("TENANT_ID");
            if(tenants != null && tenants.size() > 0) {
                for(Tenant tenant1 : tenants) {
                    if(tenant1.getName().equals(tenant.getName())) {
                        Map<String, Object> additionalInfo = new HashMap<String, Object>();
                        additionalInfo.put("TENANT_ID", tenant1.getName());
                        ((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(additionalInfo);
                        return accessToken;

                    }
                }
            }
        }
        return accessToken;
    }
}
