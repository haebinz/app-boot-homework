package it.korea.app_boot.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import it.korea.app_boot.common.listener.P6SpyEventListener;

@Configuration
public class P6SpyConfig {

    @Bean
    public P6SpyEventListener pSpyEventListener(){
        return new P6SpyEventListener();
    }

    @Bean
    public P6sypSqlFormater p6sypSqlFormater(){
        return new P6sypSqlFormater();
    }
}
