package com.tracebucket.idem.autoconfig.conditional;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Created by sadath on 30-Mar-2016.
 */
public class NonExistingLoginConfigurationBeans implements Condition {
    private static final String LOGIN_CONFIGURATION_BEAN_NAME = "loginConfiguration";
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return !context.getBeanFactory().containsBean(LOGIN_CONFIGURATION_BEAN_NAME);
    }
}
