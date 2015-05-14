package com.tracebucket.idem.init;

import com.tracebucket.idem.domain.Authority;
import com.tracebucket.idem.domain.User;
import com.tracebucket.idem.init.defaults.AuthoritiesDefault;
import com.tracebucket.idem.init.defaults.UsersDefault;
import com.tracebucket.idem.repository.jpa.AuthorityRepository;
import com.tracebucket.idem.repository.jpa.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by ffl on 14-05-2015.
 */
public class DefaultDataInitListener implements ApplicationListener<ContextRefreshedEvent> {
	private static final Logger log = LoggerFactory.getLogger(DefaultDataInitListener.class);
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AuthorityRepository authorityRepository;


	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		List<Authority> authorities = authorityRepository.findAll();
		List<User> users = userRepository.findAll();
		if(authorities.size() == 0){
			log.info("Autorities are empty ... Adding default authorities");
			authorityRepository.save(AuthoritiesDefault.idemAdministrator());
			authorityRepository.save(AuthoritiesDefault.tenantAdministrator());
		}
		if(users.size() == 0){
			log.info("Users are empty ... Adding default users");
			Authority idemAdministrator = authorityRepository.findByRole("IDEM_ADMINISTRATOR");
			User defaultIdemAdministrator = UsersDefault.defaultIdemAdministrator();
			defaultIdemAdministrator.getRawAuthorities().add(idemAdministrator);
			userRepository.save(defaultIdemAdministrator);
		}
	}
}
