package com.tracebucket.idem.domain.enums.converter;

import org.springframework.security.oauth2.provider.approval.Approval;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by sadath on 23-Nov-2015.
 */
@Converter(autoApply = true)
public class ApprovalStatusConverter implements AttributeConverter<org.springframework.security.oauth2.provider.approval.Approval.ApprovalStatus, String> {
    @Override
    public String convertToDatabaseColumn(Approval.ApprovalStatus approvalStatus) {
        switch (approvalStatus) {
            case APPROVED:
                return "APPROVED";
            case DENIED:
                return "DENIED";
            default:
                throw new IllegalArgumentException("Unknown value: " + approvalStatus);
        }
    }

    @Override
    public Approval.ApprovalStatus convertToEntityAttribute(String s) {
        switch (s) {
            case "APPROVED":
                return Approval.ApprovalStatus.APPROVED;
            case "DENIED":
                return Approval.ApprovalStatus.DENIED;
            default:
                throw new IllegalArgumentException("Unknown value: " + s);
        }    }
}
