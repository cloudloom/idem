package com.tracebucket.idem.autoconfig;

import com.tracebucket.idem.domain.Authority;
import com.tracebucket.idem.domain.Client;
import com.tracebucket.idem.domain.User;
import com.tracebucket.idem.init.defaults.AuthoritiesDefault;
import com.tracebucket.idem.init.defaults.ClientDefault;
import com.tracebucket.idem.init.defaults.UsersDefault;
import com.tracebucket.idem.repository.jpa.AuthorityRepository;
import com.tracebucket.idem.repository.jpa.ClientRepository;
import com.tracebucket.idem.repository.jpa.UserRepository;
import com.tracebucket.idem.service.impl.UserDetailsManagerImpl;
import com.tracebucket.tron.autoconfig.NonExistingAssemblerBeans;
import com.tracebucket.tron.autoconfig.NonExistingInitialConfigurationBeans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sadath on 30-Apr-15.
 */
@Configuration
@Conditional(value = NonExistingInitialConfigurationBeans.class)
public class InitialConfiguration implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Authority idemAdministrator = authorityRepository.findByRole("IDEM_ADMINISTRATOR");
        Authority tenantAdministrator = authorityRepository.findByRole("TENANT_ADMINISTRATOR");
        List<Authority> authorities = new ArrayList<Authority>();
        if(idemAdministrator == null) {
            authorities.add(AuthoritiesDefault.idemAdministrator());
        }
        if(tenantAdministrator == null) {
            authorities.add(AuthoritiesDefault.tenantAdministrator());
        }
        if(authorities.size() > 0) {
            authorityRepository.save(authorities);
        }
        User userAdmin = (User)userRepository.findByUsername("admin");
        User userTenant = (User)userRepository.findByUsername("tenant");
        List<User> users = new ArrayList<User>();
        if(userAdmin == null) {
            Set<Authority> authoritySet = new HashSet<Authority>();
            authoritySet.add(authorityRepository.findByRole("IDEM_ADMINISTRATOR"));
            users.add(UsersDefault.defaultIdemAdministrator(authoritySet));
        }
        if(userTenant == null) {
            Set<Authority> authoritySet = new HashSet<Authority>();
            authoritySet.add(authorityRepository.findByRole("TENANT_ADMINISTRATOR"));
            users.add(UsersDefault.defaultTenantAdministrator(authoritySet));
        }
        if(users.size() > 0) {
            userRepository.save(users);
        }
        Client client = clientRepository.findByClientId("idem-admin");
        if(client == null) {
            clientRepository.save(ClientDefault.defaultClient(authorities));
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