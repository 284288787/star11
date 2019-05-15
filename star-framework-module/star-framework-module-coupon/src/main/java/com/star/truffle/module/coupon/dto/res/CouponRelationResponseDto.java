/**create by framework at 2019年03月30日 10:29:30**/
package com.star.truffle.module.coupon.dto.res;

import com.star.truffle.module.coupon.domain.CouponRelation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CouponRelationResponseDto extends CouponRelation {

  private String title;
  //微信卡券Id
  private String cardId;
  //卡券描述
  private String description;
  // 卡券类型 CardTypeEnum 
  private String cardType;
  //优惠券领取后的状态 0:未领取;1:正常;2:已过期;3:已使用
  private Integer state;
}