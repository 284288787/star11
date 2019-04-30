/**create by framework at 2019年04月30日 10:49:00**/
package com.star.truffle.module.coupon.constant;

public enum UserCouponEnum { 

  normal(1, "正常"), expired(2, "已过期"), used(3, "已使用");

  private int state;
  private String caption;

  private UserCouponEnum(int state, String caption) {
    this.state = state;
    this.caption = caption;
  }

  public int state() {
    return this.state;
  }

  public String caption() {
    return this.caption;
  }

}