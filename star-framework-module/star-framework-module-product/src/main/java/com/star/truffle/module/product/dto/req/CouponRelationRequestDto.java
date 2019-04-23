/**create by framework at 2019年03月30日 10:29:30**/
package com.star.truffle.module.product.dto.req;

import com.star.truffle.core.jdbc.Page;
import com.star.truffle.module.product.domain.CouponRelation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CouponRelationRequestDto extends CouponRelation {

  private Page pager;
  
  private String couponIds;
  
  private String title;
}