/**create by framework at 2018年09月03日 09:54:38**/
package com.star.truffle.module.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.star.truffle.common.config.EnableModuleStatic;
import com.star.truffle.core.security.DisableSecurity;
import com.star.truffle.module.alibaba.config.EnableModuleAlibaba;
import com.star.truffle.module.member.config.EnableModuleMember;
import com.star.truffle.module.user.config.EnableModuleUser;

@EnableModuleMember
@EnableModuleUser
@EnableModuleAlibaba
@EnableModuleStatic
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@EnableDiscoveryClient
@DisableSecurity
public class ProductApplication {

  public static void main(String[] args) {
    SpringApplication.run(ProductApplication.class, args);
  }
}
