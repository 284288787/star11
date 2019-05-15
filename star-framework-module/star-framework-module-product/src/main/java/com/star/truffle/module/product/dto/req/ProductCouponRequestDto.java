/**create by liuhua at 2019年5月15日 下午3:15:39**/
package com.star.truffle.module.product.dto.req;

import com.star.truffle.core.jdbc.Page;

import lombok.Data;

@Data
public class ProductCouponRequestDto {
  
  private Page pager;
  private Long userId;
  private Long productId;
}
