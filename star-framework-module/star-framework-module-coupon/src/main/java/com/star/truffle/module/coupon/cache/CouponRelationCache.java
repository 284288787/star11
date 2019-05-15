/**create by framework at 2019年03月30日 10:29:30**/
package com.star.truffle.module.coupon.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.star.truffle.common.constants.DeletedEnum;
import com.star.truffle.core.async.ThreadPoolHelper;
import com.star.truffle.core.jackson.StarJson;
import com.star.truffle.module.coupon.constant.CouponBusinessTypeEnum;
import com.star.truffle.module.coupon.dao.read.CouponRelationReadDao;
import com.star.truffle.module.coupon.dao.read.UserCouponReadDao;
import com.star.truffle.module.coupon.dao.write.CouponRelationWriteDao;
import com.star.truffle.module.coupon.domain.CouponRelation;
import com.star.truffle.module.coupon.dto.req.CouponRelationRequestDto;
import com.star.truffle.module.coupon.dto.res.CouponRelationResponseDto;
import com.star.truffle.module.coupon.dto.res.UserCouponResponseDto;

@Service
public class CouponRelationCache {

  @Autowired
  private StarJson starJson;
  @Autowired
  private CouponRelationWriteDao couponRelationWriteDao;
  @Autowired
  private CouponRelationReadDao couponRelationReadDao;
  @Autowired
  private UserCouponReadDao userCouponReadDao;
  @Autowired
  private ThreadPoolHelper threadPoolHelper;

//  @CachePut(value = "module-weixin-couponRelation", key = "'couponRelation_id_'+#result.id", condition = "#result != null and #result.id != null")
  public CouponRelationResponseDto saveCouponRelation(CouponRelation couponRelation){
    this.couponRelationWriteDao.saveCouponRelation(couponRelation);
    CouponRelationResponseDto couponRelationResponseDto = this.couponRelationWriteDao.getCouponRelation(couponRelation.getId());
    return couponRelationResponseDto;
  }

//  @CachePut(value = "module-weixin-couponRelation", key = "'couponRelation_id_'+#result.id", condition = "#result != null and #result.id != null")
  public CouponRelationResponseDto updateCouponRelation(CouponRelationRequestDto couponRelationRequestDto){
    this.couponRelationWriteDao.updateCouponRelation(couponRelationRequestDto);
    CouponRelationResponseDto couponRelationResponseDto = this.couponRelationWriteDao.getCouponRelation(couponRelationRequestDto.getId());
    return couponRelationResponseDto;
  }

//  @CacheEvict(value = "module-weixin-couponRelation", key = "'couponRelation_id_'+#id", condition = "#id != null")
  public int deleteCouponRelation(Long id){
    return this.couponRelationWriteDao.deleteCouponRelation(id);
  }

//  @Cacheable(value = "module-weixin-couponRelation", key = "'couponRelation_id_'+#id", condition = "#id != null")
  public CouponRelationResponseDto getCouponRelation(Long id){
    CouponRelationResponseDto couponRelationResponseDto = this.couponRelationReadDao.getCouponRelation(id);
    return couponRelationResponseDto;
  }

  public List<CouponRelationResponseDto> queryCouponRelation(CouponRelationRequestDto couponRelationRequestDto){
    Map<String, Object> conditions = starJson.bean2Map(couponRelationRequestDto);
    return this.couponRelationReadDao.queryCouponRelation(conditions);
  }

  public Long queryCouponRelationCount(CouponRelationRequestDto couponRelationRequestDto){
    Map<String, Object> conditions = starJson.bean2Map(couponRelationRequestDto);
    return this.couponRelationReadDao.queryCouponRelationCount(conditions);
  }

//  @CacheEvict(value = "module-weixin-couponRelation", allEntries = true)
  public void deleteCouponRelation(CouponRelationRequestDto param) {
    couponRelationWriteDao.deleteCouponRelationByParam(param);
  }

  public List<CouponRelationResponseDto> getCouponsByCondition(String cateIds, Long productCateId, Long productId, Long userId) {
    List<List<CouponRelationResponseDto>> temp = threadPoolHelper.run(true, 10000, () -> {
      if(StringUtils.isBlank(cateIds)) {
        return new ArrayList<>();
      }
      Map<String, Object> conditions = new HashMap<>();
      conditions.put("deleted", DeletedEnum.notdelete.val());
      conditions.put("businessIds", cateIds);
      conditions.put("businessType", CouponBusinessTypeEnum.cate.type());
      List<CouponRelationResponseDto> releations = couponRelationReadDao.queryCouponRelation(conditions);
      return releations;
    }, () -> {
      if(null == productCateId) {
        return new ArrayList<>();
      }
      Map<String, Object> conditions = new HashMap<>();
      conditions.put("deleted", DeletedEnum.notdelete.val());
      conditions.put("businessId", productCateId);
      conditions.put("businessType", CouponBusinessTypeEnum.product_cate.type());
      List<CouponRelationResponseDto> releations = couponRelationReadDao.queryCouponRelation(conditions);
      return releations;
    }, () -> {
      if(null == productId) {
        return new ArrayList<>();
      }
      Map<String, Object> conditions = new HashMap<>();
      conditions.put("deleted", DeletedEnum.notdelete.val());
      conditions.put("businessId", productId);
      conditions.put("businessType", CouponBusinessTypeEnum.product.type());
      List<CouponRelationResponseDto> releations = couponRelationReadDao.queryCouponRelation(conditions);
      return releations;
    }, () -> {
      if(null == userId) {
        return new ArrayList<>();
      }
      Map<String, Object> conditions = new HashMap<>();
      conditions.put("deleted", DeletedEnum.notdelete.val());
      conditions.put("businessId", userId);
      conditions.put("businessType", CouponBusinessTypeEnum.member.type());
      List<CouponRelationResponseDto> releations = couponRelationReadDao.queryCouponRelation(conditions);
      return releations;
    });
    Map<Long, UserCouponResponseDto> userCouponMap = new HashMap<>();
    if(null != userId) {
      Map<String, Object> conditions = new HashMap<>();
      conditions.put("userId", userId);
      List<UserCouponResponseDto> userCoupons = userCouponReadDao.queryUserCoupon(conditions);
      if(null != userCoupons && ! userCoupons.isEmpty()) {
        userCouponMap = userCoupons.stream().collect(HashMap::new, (m, v) -> m.put(v.getCouponId(), v), HashMap::putAll);
      }
    }
    Map<Long, UserCouponResponseDto> tempMap = userCouponMap;
    List<CouponRelationResponseDto> result = temp.stream().flatMap(list -> list.stream()).map(cr -> {
      UserCouponResponseDto uc = tempMap.get(cr.getCouponId());
      cr.setState(0);
      if(null != uc) {
        cr.setState(uc.getState());
      }
      return cr;
    }).collect(Collectors.toList());
    return result;
  }

}