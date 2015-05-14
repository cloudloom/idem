package com.tracebucket.idem.config;

import com.tracebucket.idem.domain.Authority;
import com.tracebucket.idem.repository.jpa.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sadath on 30-Apr-15.
 */
@Configuration
public class InitialConfiguration implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Authority idemAdministrator = authorityRepository.findByRole("IDEM_ADMINISTRATOR");
        Authority tenantAdministrator = authorityRepository.findByRole("TENANT_ADMINISTRATOR");
        List<Authority> authorities = new ArrayList<Authority>();
        if(idemAdministrator == null) {
            authorities.add(new Authority("IDEM_ADMINISTRATOR"));
        }
        if(tenantAdministrator == null) {
            authorities.add(new Authority("TENANT_ADMINISTRATOR"));
        }
        if(authorities.size() > 0) {
            authorityRepository.save(authorities);
        }
        /**
         *  [1] Authority: Idem Administrator
         *  [2] Authority: Tenant Administrator
         *  [3] User: admin/admin --> Idem Administrator
         *  [4] User: tenant/tenant --> Tenant Administrator
         *  [5] Client: idem-admin/idem-admin-secret
         *  [6] Scope: idem-write
         *  [7] Scope: idem-read
         */
    }
}