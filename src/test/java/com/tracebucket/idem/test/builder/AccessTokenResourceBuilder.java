package com.tracebucket.idem.test.builder;

import com.tracebucket.idem.rest.resource.AccessTokenResource;
import com.tracebucket.idem.rest.resource.ClientResource;
import com.tracebucket.idem.rest.resource.RefreshTokenResource;
import com.tracebucket.idem.rest.resource.UserResource;

import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * @author ssm
 * @since 25-Nov-14
 */
public class AccessTokenResourceBuilder {
    private String authenticationId;
    private String tokenId;
    private byte[] token;
    private byte[] authentication;
    private ClientResource client;
    private UserResource user;
    private String value;
    private Date expiration;
    private String tokenType;
    private RefreshTokenResource refreshToken;
    private Set<String> scope = Collections.emptySet();
    private Map<String, String> additionalInformation = Collections.emptyMap();

    private AccessTokenResourceBuilder(){

    }

    public static AccessTokenResourceBuilder anAccessTokenBuilder(){
        return new AccessTokenResourceBuilder();
    }

    public AccessTokenResourceBuilder withAuthenticationId(String authenticationId){
        this.authenticationId = authenticationId;
        return this;
    }

    public AccessTokenResourceBuilder withTokenId(String tokenId){
        this.tokenId = tokenId;
        return this;
    }

    public AccessTokenResourceBuilder withToken(byte[] token){
        this.token = token;
        return this;
    }

    public AccessTokenResourceBuilder withAuthentication(byte[] authentication){
        this.authentication = authentication;
        return this;
    }

    public AccessTokenResourceBuilder withClient(ClientResource client){
        this.client = client;
        return this;
    }

    public AccessTokenResourceBuilder withUser(UserResource user) {
        this.user = user;
        return this;
    }

    public AccessTokenResourceBuilder withValue(String value){
        this.value = value;
        return this;
    }

    public AccessTokenResourceBuilder withExpiration(Date expiration){
        this.expiration = expiration;
        return this;
    }

    public AccessTokenResourceBuilder withTokenType(String tokenType){
        this.tokenType = tokenType;
        return this;
    }

    public AccessTokenResourceBuilder withRefreshToken(RefreshTokenResource refreshToken){
        this.refreshToken = refreshToken;
        return this;
    }

    public AccessTokenResourceBuilder withScope(Set<String> scope){
        this.scope = scope;
        return this;
    }

    public AccessTokenResourceBuilder withAdditionalInformation(Map<String, String> additionalInformation){
        this.additionalInformation = additionalInformation;
        return this;
    }

    public AccessTokenResource build(){
        AccessTokenResource accessToken = new AccessTokenResource();
        accessToken.setValue(this.value);
        accessToken.setAuthentication(this.authentication);
        accessToken.setAuthenticationId(this.authenticationId);
        accessToken.setToken(this.token);
        accessToken.setTokenId(this.tokenId);
        accessToken.setAdditionalInformation(this.additionalInformation);
        accessToken.setExpiration(this.expiration);
        accessToken.setRefreshToken(this.refreshToken);
        accessToken.setScope(this.scope);
        accessToken.setTokenType(this.tokenType);
        return accessToken;
    }
}
