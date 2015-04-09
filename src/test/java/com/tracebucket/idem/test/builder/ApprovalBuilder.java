package com.tracebucket.idem.test.builder;

import com.tracebucket.idem.domain.Approval;
import com.tracebucket.idem.domain.Client;
import com.tracebucket.idem.domain.User;

import java.util.Date;

/**
 * Created by sadath on 13-Mar-15.
 */
public class ApprovalBuilder {
    private Client client;
    private User user;
    private String scope;
    private org.springframework.security.oauth2.provider.approval.Approval.ApprovalStatus approvalStatus;
    private Date expiresAt;
    private Date lastUpdatedAt;

    private ApprovalBuilder(){

    }

    public static ApprovalBuilder anApprovalBuilder(){
        return new ApprovalBuilder();
    }

    public ApprovalBuilder withClient(Client client){
        this.client = client;
        return this;
    }

    public ApprovalBuilder withUser(User user){
        this.user = user;
        return this;
    }

    public ApprovalBuilder withScope(String scope){
        this.scope = scope;
        return this;
    }

    public ApprovalBuilder withApprovalStatus(org.springframework.security.oauth2.provider.approval.Approval.ApprovalStatus approvalStatus){
        this.approvalStatus = approvalStatus;
        return this;
    }

    public ApprovalBuilder withExpiresAt(Date expiresAt){
        this.expiresAt = expiresAt;
        return this;
    }

    public ApprovalBuilder withLastUpdatedAt(Date lastUpdatedAt){
        this.lastUpdatedAt = lastUpdatedAt;
        return this;
    }

    public Approval build(){
        Approval approval = new Approval();
        approval.setApprovalStatus(this.approvalStatus);
        approval.setClient(this.client);
        approval.setExpiresAt(this.expiresAt);
        approval.setLastUpdatedAt(this.lastUpdatedAt);
        approval.setScope(this.scope);
        approval.setUser(this.user);
        return approval;
    }
}
