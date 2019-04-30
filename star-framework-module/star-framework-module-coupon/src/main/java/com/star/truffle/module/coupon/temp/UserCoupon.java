/**create by liuhua at 2019年4月30日 上午10:09:01**/
package com.star.truffle.module.coupon.temp;

import java.util.Date;

import com.star.truffle.module.build.annotation.StarDomainName;
import com.star.truffle.module.build.annotation.StarField;
import com.star.truffle.module.build.annotation.StarFieldList;
import com.star.truffle.module.build.annotation.StarFieldQuery;
import com.star.truffle.module.build.constant.DsType;
import com.star.truffle.module.build.constant.InputType;

@StarDomainName(caption = "用户已领取的优惠券", createTable = true, tableName = "coupon_user", addPage = false, editPage = false, addButton = false, editButton = false, disabledButton = false, deleteButton = false)
public class UserCoupon {

  @StarField(caption = "id", primary = true, dsType = DsType.BIGINT, dsLength = 20)
  @StarFieldList(inputType = InputType.text)
  private Long id;
  
  @StarField(caption = "用户Id", dsType = DsType.BIGINT, dsLength = 20)
  @StarFieldList(inputType = InputType.text)
  @StarFieldQuery(inputType = InputType.text)
  private Long userId;
  
  @StarField(caption = "优惠券Id", dsType = DsType.BIGINT, dsLength = 20)
  @StarFieldList(inputType = InputType.text)
  @StarFieldQuery(inputType = InputType.text)
  private Long couponId;
  
  @StarField(caption = "领取日期", dsType = DsType.DATETIME, dsLength = 0, defaultAddFieldValue = "new Date()", defaultAddFieldValueType = "java.util.Date")
  @StarFieldList(inputType = InputType.date, inputValue = "Y-m-d")
  private Date createTime;
  
  @StarField(caption = "优惠券状态", dsType = DsType.TINYINT, dsLength = 1, enumName = "UserCouponEnum", enumOptTypes = "{\"state\":\"int\",\"caption\":\"String\"}", enumOptValues = "{\"normal\": {\"state\": 1, \"caption\": \"正常\"}, \"expired\": {\"state\": 2, \"caption\": \"已过期\"}, \"used\": {\"state\": 3, \"caption\": \"已使用\"}}")
  @StarFieldList(inputType = InputType.select, inputValue = "{value:'1:正常;2:已过期;3:已使用'}")
  @StarFieldQuery(inputType = InputType.select, inputValue = "{\"1\":\"正常\",\"2\":\"已过期\",\"3\":\"已使用\"}")
  private Integer state;
}
