/**create by framework at 2019年04月30日 10:49:00**/
package com.star.truffle.module.coupon.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.star.truffle.core.jdbc.Page;
import com.star.truffle.core.jdbc.Page.OrderType;
import com.star.truffle.module.coupon.dto.req.UserCouponRequestDto;
import com.star.truffle.module.coupon.dto.res.UserCouponResponseDto;
import com.star.truffle.module.coupon.service.UserCouponService;

@Controller
@RequestMapping("/userCoupon")
public class UserCouponController {

  @Autowired
  private UserCouponService userCouponService;

  @RequestMapping(value = "/lists", method = RequestMethod.GET)
  public String list() {
    return "mgr/userCoupon/list";
  }

  @RequestMapping(value = "/editBefore/{id}", method = RequestMethod.GET)
  public String editBefore(@PathVariable Long id, Model model) {
    UserCouponResponseDto userCouponResponseDto = this.userCouponService.getUserCoupon(id);
    model.addAttribute("userCouponResponseDto", userCouponResponseDto);
    return "mgr/userCoupon/editUserCoupon";
  }

  @ResponseBody
  @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
  public Map<String, Object> list(UserCouponRequestDto userCouponRequestDto, Integer page, Integer rows, String sord, String sidx) {
    Page pager = new Page(page, rows, sidx, OrderType.desc.name().equals(sord) ? OrderType.desc : OrderType.asc);
    userCouponRequestDto.setPager(pager);
    List<UserCouponResponseDto> list = userCouponService.queryUserCoupon(userCouponRequestDto);
    Long count = userCouponService.queryUserCouponCount(userCouponRequestDto);

    Map<String, Object> map = new HashMap<>();
    map.put("page", pager.getPageNum());
    map.put("total", count % pager.getPageSize() == 0 ? count / pager.getPageSize() : count / pager.getPageSize() + 1);
    map.put("records", count);
    map.put("rows", list);
    return map;
  }

}