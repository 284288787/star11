/**create by framework at 2019年04月30日 10:49:00**/
package com.star.truffle.module.coupon.dao.write;

import java.util.List;
import com.star.truffle.module.coupon.domain.UserCoupon;
import com.star.truffle.module.coupon.dto.req.UserCouponRequestDto;
import com.star.truffle.module.coupon.dto.res.UserCouponResponseDto;

public interface UserCouponWriteDao {

  public int saveUserCoupon(UserCoupon userCoupon);

  public int batchSaveUserCoupon(List<UserCoupon> userCoupons);

  public int updateUserCoupon(UserCouponRequestDto userCouponDto);

  public int deleteUserCoupon(Long id);

  public UserCouponResponseDto getUserCoupon(Long id);

}