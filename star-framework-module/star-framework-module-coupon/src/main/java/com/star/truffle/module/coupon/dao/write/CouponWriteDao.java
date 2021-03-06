/**create by framework at 2019年03月25日 14:18:36**/
package com.star.truffle.module.coupon.dao.write;

import java.util.List;

import com.star.truffle.module.coupon.domain.Coupon;
import com.star.truffle.module.coupon.dto.req.CouponRequestDto;
import com.star.truffle.module.coupon.dto.res.CouponResponseDto;

public interface CouponWriteDao {

  public int saveCoupon(Coupon coupon);

  public int batchSaveCoupon(List<Coupon> coupons);

  public int updateCoupon(CouponRequestDto couponDto);

  public int deleteCoupon(Long couponId);

  public CouponResponseDto getCoupon(Long couponId);

}