package com.tracebucket.idem.test.fixture;

import com.tracebucket.idem.rest.resource.ApprovalResource;
import com.tracebucket.idem.test.builder.ApprovalResourceBuilder;

import java.util.Date;

/**
 * @author ssm
 * @since 13-03-15
 */
public class ApprovalResourceFixture {
    public static ApprovalResource standardApproval() {
        ApprovalResource approval = ApprovalResourceBuilder.anApprovalBuilder()
                .withUser(null)
                .withClient(null)
                .withApprovalStatus(org.springframework.security.oauth2.provider.approval.Approval.ApprovalStatus.APPROVED)
                .withExpiresAt(new Date())
                .withLastUpdatedAt(new Date())
                .withScope("Scope")
                .build();
        return approval;
    }
}