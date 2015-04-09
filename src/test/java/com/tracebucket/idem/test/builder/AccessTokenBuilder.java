package com.tracebucket.idem.test.builder;

import com.tracebucket.idem.domain.AccessToken;
import com.tracebucket.idem.domain.Client;
import com.tracebucket.idem.domain.RefreshToken;
import com.tracebucket.idem.domain.User;

import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * Created by sadath on 25-Nov-14.
 */
public class AccessTokenBuilder {
    private String authenticationId;
    private String tokenId;
    private byte[] token;
    private byte[] authentication;
    private Client client;
    private User user;
    private String value;
    private Date expiration;
    private String tokenType;
    private RefreshToken refreshToken;
    private Set<String> scope = Collections.emptySet();
    private Map<String, String> additionalInformation = Collections.emptyMap();

    private AccessTokenBuilder(){

    }

    public static AccessTokenBuilder anAccessTokenBuilder(){
        return new AccessTokenBuilder();
    }

    public AccessTokenBuilder withAuthenticationId(String authenticationId){
        this.authenticationId = authenticationId;
        return this;
    }

    public AccessTokenBuilder withTokenId(String tokenId){
        this.tokenId = tokenId;
        return this;
    }

    public AccessTokenBuilder withToken(byte[] token){
        this.token = token;
        return this;
    }

    public AccessTokenBuilder withAuthentication(byte[] authentication){
        this.authentication = authentication;
        return this;
    }

    public AccessTokenBuilder withClient(Client client){
        this.client = client;
        return this;
    }

    public AccessTokenBuilder withUser(User user) {
        this.user = user;
        return this;
    }

    public AccessTokenBuilder withValue(String value){
        this.value = value;
        return this;
    }

    public AccessTokenBuilder withExpiration(Date expiration){
        this.expiration = expiration;
        return this;
    }

    public AccessTokenBuilder withTokenType(String tokenType){
        this.tokenType = tokenType;
        return this;
    }

    public AccessTokenBuilder withRefreshToken(RefreshToken refreshToken){
        this.refreshToken = refreshToken;
        return this;
    }

    public AccessTokenBuilder withScope(Set<String> scope){
        this.scope = scope;
        return this;
    }

    public AccessTokenBuilder withAdditionalInformation(Map<String, String> additionalInformation){
        this.additionalInformation = additionalInformation;
        return this;
    }

    public AccessToken build(){
        AccessToken accessToken = new AccessToken(this.value);
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
