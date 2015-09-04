package com.tracebucket.idem.web;

import com.tracebucket.idem.service.impl.JpaTokenStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * @author ffl
 * @since 11-03-2015
 */
@RestController
@SessionAttributes("authorizationRequest")
public class UserController {

    @Autowired
    private JpaTokenStore jpaTokenStore;

    @RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
    }

    @RequestMapping(value = "/oauth/token/revoke", method = RequestMethod.POST)
    public @ResponseBody void create(@RequestParam("token") String value) throws InvalidClientException {
        jpaTokenStore.removeAccessToken(value);
    }


}
