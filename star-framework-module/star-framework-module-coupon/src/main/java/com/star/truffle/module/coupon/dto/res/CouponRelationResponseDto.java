/**create by framework at 2019年03月30日 10:29:30**/
package com.star.truffle.module.coupon.dto.res;

import com.star.truffle.module.coupon.domain.CouponRelation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CouponRelationResponseDto extends CouponRelation {

  private String title;
}