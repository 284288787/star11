/**create by framework at 2018年09月21日 15:21:35**/
package com.star.truffle.module.order.dto.req;

import com.star.truffle.core.jdbc.Page;
import lombok.Getter;
import lombok.Setter;
import com.star.truffle.module.order.domain.ShoppingCart;

@Getter
@Setter
public class ShoppingCartRequestDto extends ShoppingCart {

  private Page pager;
  
  public boolean checkSaveData() {
    if (null != getCartId() || null == getProductId() || null == getMemberId() || null == getNum()) {
      return false;
    }
    return true;
  }
}