package com.oynaap.repository;

import com.oynaap.models.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;


@Repository
public class ShopRepository implements SQLCOMMAND {
    
    @Autowired
    private JdbcTemplate template;

    public boolean insertUser(Account account){
        
        int count = template.update(SQL_INSERT_USER,
        account.getUsername(),
        account.getPassword(),
        account.getCustomer_name(),
        account.getCustomer_address(),
        account.getCustomer_number());

        return count == 1;
    }

    public Account authenticateUser(String username){
        Account account = new Account();
        SqlRowSet rs = template.queryForRowSet(SQL_AUTHETICATE_USER,username);
        rs.next();
        account = Account.convert(rs);
        return account;
    }




}
