/**create by framework at 2019年04月30日 10:49:00**/
package com.star.truffle.module.coupon.domain;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class UserCoupon {

  // id
  private Long id;
  // 用户Id
  private Long userId;
  // 优惠券Id
  private Long couponId;
  // 领取日期
  private Date createTime;
  // 优惠券状态
  private Integer state;
}