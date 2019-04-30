/**create by framework at 2019年04月30日 10:49:00**/
package com.star.truffle.module.coupon.service;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.module.coupon.cache.UserCouponCache;
import com.star.truffle.module.coupon.domain.UserCoupon;
import com.star.truffle.module.coupon.dto.req.UserCouponRequestDto;
import com.star.truffle.module.coupon.dto.res.UserCouponResponseDto;

@Service
public class UserCouponService {

  @Autowired
  private UserCouponCache userCouponCache;

  public Long saveUserCoupon(UserCoupon userCoupon) {
    this.userCouponCache.saveUserCoupon(userCoupon);
    return userCoupon.getId();
  }

  public void updateUserCoupon(UserCouponRequestDto userCouponRequestDto) {
    this.userCouponCache.updateUserCoupon(userCouponRequestDto);
  }

  public void deleteUserCoupon(Long id) {
    this.userCouponCache.deleteUserCoupon(id);
  }

  public void deleteUserCoupon(String idstr) {
    if (StringUtils.isBlank(idstr)) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    String[] ids = idstr.split(",");
    for (String str : ids) {
      Long id = Long.parseLong(str);
      this.userCouponCache.deleteUserCoupon(id);
    }
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