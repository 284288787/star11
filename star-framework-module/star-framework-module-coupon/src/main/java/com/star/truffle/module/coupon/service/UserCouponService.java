/**create by framework at 2019年04月30日 10:49:00**/
package com.star.truffle.module.coupon.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.module.coupon.cache.UserCouponCache;
import com.star.truffle.module.coupon.constant.UserCouponEnum;
import com.star.truffle.module.coupon.domain.UserCoupon;
import com.star.truffle.module.coupon.dto.req.UserCouponRequestDto;
import com.star.truffle.module.coupon.dto.res.UserCouponResponseDto;

@Service
public class UserCouponService {

  @Autowired
  private UserCouponCache userCouponCache;

  public Long saveUserCoupon(UserCoupon userCoupon) {
    UserCouponRequestDto userCouponRequestDto = new UserCouponRequestDto();
    userCouponRequestDto.setUserId(userCoupon.getUserId());
    userCouponRequestDto.setCouponId(userCoupon.getCouponId());
    long count = userCouponCache.queryUserCouponCount(userCouponRequestDto);
    if(count > 0) {
      throw new StarServiceException(ApiCode.PARAM_ERROR, "已领取过该优惠券");
    }
    userCoupon.setCreateTime(new Date());
    userCoupon.setState(UserCouponEnum.normal.state());
    this.userCouponCache.saveUserCoupon(userCoupon);
    return userCoupon.getId();
  }

  public void updateUserCoupon(UserCouponRequestDto userCouponRequestDto) {
    this.userCouponCache.updateUserCoupon(userCouponRequestDto);
  }

  public UserCouponResponseDto getUserCoupon(Long id) {
    UserCouponResponseDto userCouponResponseDto = this.userCouponCache.getUserCoupon(id);
    return userCouponResponseDto;
  }

  public List<UserCouponResponseDto> queryUserCoupon(UserCouponRequestDto userCouponRequestDto) {
    return this.userCouponCache.queryUserCoupon(userCouponRequestDto);
  }

  public Long queryUserCouponCount(UserCouponRequestDto userCouponRequestDto) {
    return this.userCouponCache.queryUserCouponCount(userCouponRequestDto);
  }

}