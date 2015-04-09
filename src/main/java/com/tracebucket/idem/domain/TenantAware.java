package com.tracebucket.idem.domain;

import java.util.Map;

/**
 * Created by ffazil on 17/03/15.
 * Extension to add tenant information.
 */
public interface TenantAware {
    Map<String, Object> getTenantInformation();
}
