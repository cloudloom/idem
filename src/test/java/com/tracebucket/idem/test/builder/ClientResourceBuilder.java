package com.tracebucket.idem.test.builder;

import com.tracebucket.idem.rest.resource.AuthorityResource;
import com.tracebucket.idem.rest.resource.ClientResource;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author ssm
 * @since 13-03-15
 */
public class ClientResourceBuilder {
    private String clientId;
    private String clientSecret;
    private Set<String> scope = Collections.emptySet();
    private Set<String> resourceIds = Collections.emptySet();
    private Set<String> authorizedGrantTypes = Collections.emptySet();
    private Set<String> registeredRedirectUris;
    private Set<String> autoApproveScopes;
    private Set<AuthorityResource> authorities = Collections.emptySet();
    private Integer accessTokenValiditySeconds;
    private Integer refreshTokenValiditySeconds;
    private Map<String, String> additionalInformation = new LinkedHashMap<>();

    private ClientResourceBuilder() {}

    public static ClientResourceBuilder aClientBuilder() {
        return new ClientResourceBuilder();
    }

    public ClientResourceBuilder withClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public ClientResourceBuilder withClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }

    public ClientResourceBuilder withScope(Set<String> scope) {
        this.scope = scope;
        return this;
    }

    public ClientResourceBuilder withResourceIds(Set<String> resourceIds) {
        this.resourceIds = resourceIds;
        return this;
    }

    public ClientResourceBuilder withAuthorizedGrantTypes(Set<String> authorizedGrantTypes) {
        this.authorizedGrantTypes = authorizedGrantTypes;
        return this;
    }

    public ClientResourceBuilder withRegisteredRedirectUris(Set<String> registeredRedirectUris) {
        this.registeredRedirectUris = registeredRedirectUris;
        return this;
    }

    public ClientResourceBuilder withAutoApproveScopes(Set<String> autoApproveScopes) {
        this.autoApproveScopes = autoApproveScopes;
        return this;
    }

    public ClientResourceBuilder withAuthorities(Set<AuthorityResource> authorities) {
        this.authorities = authorities;
        return this;
    }

    public ClientResourceBuilder withAccessTokenValiditySeconds(Integer accessTokenValiditySeconds) {
        this.accessTokenValiditySeconds = accessTokenValiditySeconds;
        return this;
    }

    public ClientResourceBuilder withRefreshTokenValiditySeconds(Integer refreshTokenValiditySeconds) {
        this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
        return this;
    }

    public ClientResourceBuilder withAdditionalInformation(Map<String, String> additionalInformation) {
        this.additionalInformation = additionalInformation;
        return this;
    }

    public ClientResource build() {
        ClientResource client = new ClientResource();
        client.setAccessTokenValiditySeconds(this.accessTokenValiditySeconds);
        client.setAdditionalInformation(this.additionalInformation);
        client.setAuthorities(this.authorities);
        client.setAuthorizedGrantTypes(this.authorizedGrantTypes);
        client.setAutoApproveScopes(this.autoApproveScopes);
        client.setClientId(this.clientId);
        client.setClientSecret(this.clientSecret);
        client.setRefreshTokenValiditySeconds(this.refreshTokenValiditySeconds);
        client.setRegisteredRedirectUris(this.registeredRedirectUris);
        client.setResourceIds(this.resourceIds);
        client.setScope(this.scope);
        return client;
    }
}
