/**create by framework at 2018年09月21日 15:21:35**/
package com.star.truffle.module.order.dao.write;

import java.util.List;

import com.star.truffle.module.order.domain.OrderDetail;

public interface OrderDetailWriteDao {

  public int batchSaveOrderDetail(List<OrderDetail> orderDetails);

}