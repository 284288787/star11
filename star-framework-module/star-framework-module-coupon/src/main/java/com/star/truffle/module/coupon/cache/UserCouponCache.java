/**create by framework at 2019年04月30日 10:49:00**/
package com.star.truffle.module.coupon.cache;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.star.truffle.core.jackson.StarJson;
import com.star.truffle.module.coupon.dao.read.UserCouponReadDao;
import com.star.truffle.module.coupon.dao.write.UserCouponWriteDao;
import com.star.truffle.module.coupon.domain.UserCoupon;
import com.star.truffle.module.coupon.dto.req.UserCouponRequestDto;
import com.star.truffle.module.coupon.dto.res.UserCouponResponseDto;

@Service
public class UserCouponCache {

  @Autowired
  private StarJson starJson;
  @Autowired
  private UserCouponWriteDao userCouponWriteDao;
  @Autowired
  private UserCouponReadDao userCouponReadDao;

//  @CachePut(value = "module-coupon-userCoupon", key = "'userCoupon_id_'+#result.id", condition = "#result != null and #result.id != null")
  public UserCouponResponseDto saveUserCoupon(UserCoupon userCoupon){
    this.userCouponWriteDao.saveUserCoupon(userCoupon);
    UserCouponResponseDto userCouponResponseDto = this.userCouponWriteDao.getUserCoupon(userCoupon.getId());
    return userCouponResponseDto;
  }

//  @CachePut(value = "module-coupon-userCoupon", key = "'userCoupon_id_'+#result.id", condition = "#result != null and #result.id != null")
  public UserCouponResponseDto updateUserCoupon(UserCouponRequestDto userCouponRequestDto){
    this.userCouponWriteDao.updateUserCoupon(userCouponRequestDto);
    UserCouponResponseDto userCouponResponseDto = this.userCouponWriteDao.getUserCoupon(userCouponRequestDto.getId());
    return userCouponResponseDto;
  }

//  @CacheEvict(value = "module-coupon-userCoupon", key = "'userCoupon_id_'+#id", condition = "#id != null")
  public int deleteUserCoupon(Long id){
    return this.userCouponWriteDao.deleteUserCoupon(id);
  }

//  @Cacheable(value = "module-coupon-userCoupon", key = "'userCoupon_id_'+#id", condition = "#id != null")
  public UserCouponResponseDto getUserCoupon(Long id){
    UserCouponResponseDto userCouponResponseDto = this.userCouponReadDao.getUserCoupon(id);
    return userCouponResponseDto;
  }

  public List<UserCouponResponseDto> queryUserCoupon(UserCouponRequestDto userCouponRequestDto){
    Map<String, Object> conditions = starJson.bean2Map(userCouponRequestDto);
    return this.userCouponReadDao.queryUserCoupon(conditions);
  }

  public Long queryUserCouponCount(UserCouponRequestDto userCouponRequestDto){
    Map<String, Object> conditions = starJson.bean2Map(userCouponRequestDto);
    return this.userCouponReadDao.queryUserCouponCount(conditions);
  }

}