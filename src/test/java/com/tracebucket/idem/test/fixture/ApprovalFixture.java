package com.tracebucket.idem.test.fixture;

import com.tracebucket.idem.domain.Approval;
import com.tracebucket.idem.test.builder.ApprovalBuilder;

import java.util.Date;

/**
 * @author ssm
 * @since 13-03-15
 */
public class ApprovalFixture {
    public static Approval standardApproval() {
        Approval approval = ApprovalBuilder.anApprovalBuilder()
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