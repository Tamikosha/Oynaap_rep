package com.oynaap.service;

import com.oynaap.models.Account;
import com.oynaap.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShopService implements UserDetailsService{
    
    @Autowired
    private ShopRepository shopRepo;

    public void addNewUser(Account account){
        shopRepo.insertUser(account);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Account account = shopRepo.authenticateUser(username);


        if (account == null) {
            throw new UsernameNotFoundException("User " //
                    + username + " was not found in the database");
        }

        String role = account.getRole();

        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
        GrantedAuthority authority = new SimpleGrantedAuthority(role);

        grantList.add(authority);

        UserDetails userDetails = (UserDetails) new User(account.getUsername(), //
        account.getPassword(), grantList);
        System.out.println(">>>>>> userDetails: " +userDetails);

        return userDetails;
    }
    
}
