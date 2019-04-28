/**create by framework at 2019年04月28日 10:49:18**/
package com.star.truffle.module.coupon.config;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE })
@Documented
@Import({CouponModuleConfig.class})
public @interface EnableModuleCoupon {

}
