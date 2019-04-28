/**create by framework at 2019年04月28日 10:49:18**/
package com.star.truffle.module.coupon.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
    "com.star.truffle.module.coupon.dao",
    "com.star.truffle.module.coupon.cache",
    "com.star.truffle.module.coupon.service",
    "com.star.truffle.module.coupon.controller",
    "com.star.truffle.module.coupon.properties",
})
public class CouponModuleConfig {

}
