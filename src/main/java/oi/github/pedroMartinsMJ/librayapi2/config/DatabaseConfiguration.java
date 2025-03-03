package oi.github.pedroMartinsMJ.librayapi2.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfiguration {

    @Value("${spring.datasource.url}")
    String url;
    @Value("${spring.datasource.username}")
    String username;
    @Value("${spring.datasource.password}")
    String password;
    @Value("${spring.datasource.driver-class-name}")
    String driver;

    //@Bean
    public DataSource dataSource(){
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setDriverClassName(driver);

        return ds;
    }

    public DataSource HikariDataSource(){
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driver);

        config.setMaximumPoolSize(20); //maxima de conexoes liberadas
        config.setMinimumIdle(1); //tamanho inicial de pool
        config.setPoolName("library-db-pool"); //nome do processo Hikari no console
        config.setMaxLifetime(600000); // 10 minutos que uma conexão pode durar
        config.setConnectionTimeout(100000);//tempo maximo que podemos tentar fazer uma conexão
        config.setConnectionTestQuery("select 1");// testa conexão com banco

        return new HikariDataSource(config);//não tem como instancia o Hikari sem uma config
    }

}
