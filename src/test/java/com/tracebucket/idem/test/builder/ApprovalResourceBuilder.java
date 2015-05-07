package com.tracebucket.idem.test.builder;

import com.tracebucket.idem.rest.resource.ApprovalResource;
import com.tracebucket.idem.rest.resource.ClientResource;
import com.tracebucket.idem.rest.resource.UserResource;

import java.util.Date;

/**
 * @author ssm
 * @since 13-03-15
 */
public class ApprovalResourceBuilder {
    private ClientResource client;
    private UserResource user;
    private String scope;
    private org.springframework.security.oauth2.provider.approval.Approval.ApprovalStatus approvalStatus;
    private Date expiresAt;
    private Date lastUpdatedAt;

    private ApprovalResourceBuilder(){

    }

    public static ApprovalResourceBuilder anApprovalBuilder(){
        return new ApprovalResourceBuilder();
    }

    public ApprovalResourceBuilder withClient(ClientResource client){
        this.client = client;
        return this;
    }

    public ApprovalResourceBuilder withUser(UserResource user){
        this.user = user;
        return this;
    }

    public ApprovalResourceBuilder withScope(String scope){
        this.scope = scope;
        return this;
    }

    public ApprovalResourceBuilder withApprovalStatus(org.springframework.security.oauth2.provider.approval.Approval.ApprovalStatus approvalStatus){
        this.approvalStatus = approvalStatus;
        return this;
    }

    public ApprovalResourceBuilder withExpiresAt(Date expiresAt){
        this.expiresAt = expiresAt;
        return this;
    }

    public ApprovalResourceBuilder withLastUpdatedAt(Date lastUpdatedAt){
        this.lastUpdatedAt = lastUpdatedAt;
        return this;
    }

    public ApprovalResource build(){
        ApprovalResource approval = new ApprovalResource();
        approval.setApprovalStatus(this.approvalStatus);
        approval.setClient(this.client);
        approval.setExpiresAt(this.expiresAt);
        approval.setLastUpdatedAt(this.lastUpdatedAt);
        approval.setScope(this.scope);
        approval.setUser(this.user);
        return approval;
    }
}
