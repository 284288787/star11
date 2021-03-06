/**create by framework at 2019年03月30日 10:29:30**/
package com.star.truffle.module.coupon.dao.write;

import java.util.List;

import com.star.truffle.module.coupon.domain.CouponRelation;
import com.star.truffle.module.coupon.dto.req.CouponRelationRequestDto;
import com.star.truffle.module.coupon.dto.res.CouponRelationResponseDto;

public interface CouponRelationWriteDao {

  public int saveCouponRelation(CouponRelation couponRelation);

  public int batchSaveCouponRelation(List<CouponRelation> couponRelations);

  public int updateCouponRelation(CouponRelationRequestDto couponRelationDto);

  public int deleteCouponRelation(Long id);

  public CouponRelationResponseDto getCouponRelation(Long id);

  public int deleteCouponRelationByParam(CouponRelationRequestDto param);

}