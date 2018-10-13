package com.silverhyuk.springbootjpa.account;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
//@SpringBootTest
public class AccountRepositoryTest {

    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    AccountRepository accountRepository;

    @Test
    public void di() throws Exception{
        try(Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            System.out.println(metaData.getURL());
            System.out.println(metaData.getDriverName());
            System.out.println(metaData.getUserName());

            Account account = new Account();
            account.setUsername("eunhyuk");
            account.setPassword("passwd");

            Account newAccount = accountRepository.save(account);
            assertThat(newAccount).isNotNull();

            Account existingAccount = accountRepository.findByUsername(newAccount.getUsername());
            assertThat(existingAccount).isNotNull();

            Account nonExistingAccount = accountRepository.findByUsername("silverhyuk");
            assertThat(nonExistingAccount).isNull();
        }
    }

}