package com.zaam.testecliniconnect;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.zaam.testecliniconnect.Repository")
@EntityScan(basePackages = {"com.zaam.testecliniconnect.Entity"})
public class TesteCliniconnectConfig {
}
