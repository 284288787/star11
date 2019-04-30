/**create by framework at 2019年04月30日 10:49:00**/
package com.star.truffle.module.coupon.dto.req;

import com.star.truffle.core.jdbc.Page;
import lombok.Getter;
import lombok.Setter;
import com.star.truffle.module.coupon.domain.UserCoupon;

@Getter
@Setter
public class UserCouponRequestDto extends UserCoupon {

  private Page pager;
}