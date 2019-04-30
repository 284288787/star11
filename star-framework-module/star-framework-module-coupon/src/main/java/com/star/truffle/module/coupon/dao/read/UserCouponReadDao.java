/**create by framework at 2019年04月30日 10:49:00**/
package com.star.truffle.module.coupon.dao.read;

import java.util.List;
import java.util.Map;
import com.star.truffle.module.coupon.dto.res.UserCouponResponseDto;

public interface UserCouponReadDao {

  public UserCouponResponseDto getUserCoupon(Long id);

  public List<UserCouponResponseDto> queryUserCoupon(Map<String, Object> conditions);

  public Long queryUserCouponCount(Map<String, Object> conditions);

}