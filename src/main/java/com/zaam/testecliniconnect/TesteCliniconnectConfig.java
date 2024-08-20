package com.zaam.testecliniconnect;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.zaam.testecliniconnect.Repository")
public class TesteCliniconnectConfig {
}
