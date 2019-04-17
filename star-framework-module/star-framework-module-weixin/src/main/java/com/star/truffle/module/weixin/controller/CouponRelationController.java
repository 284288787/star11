/**create by framework at 2019年03月30日 10:29:30**/
package com.star.truffle.module.weixin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.star.truffle.common.constants.DeletedEnum;
import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.jdbc.Page;
import com.star.truffle.core.jdbc.Page.OrderType;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.core.web.ApiResult;
import com.star.truffle.module.weixin.dto.req.CouponRelationRequestDto;
import com.star.truffle.module.weixin.dto.res.CouponRelationResponseDto;
import com.star.truffle.module.weixin.service.CouponRelationService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/couponRelation")
public class CouponRelationController {

  @Autowired
  private CouponRelationService couponRelationService;

  @ResponseBody
  @RequestMapping(value = "/set", method = RequestMethod.POST)
  public ApiResult<Void> add(@RequestBody CouponRelationRequestDto couponRelationRequestDto) {
    try {
      couponRelationService.saveCouponRelation(couponRelationRequestDto);
      return ApiResult.success();
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/lists", method = RequestMethod.GET)
  public String list() {
    return "mgr/couponRelation/list";
  }
  
  @ResponseBody
  @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
  public Map<String, Object> list(CouponRelationRequestDto couponRelationRequestDto, Integer page, Integer rows, String sord, String sidx) {
    Page pager = new Page(page, rows, sidx, OrderType.desc.name().equals(sord) ? OrderType.desc : OrderType.asc);
    couponRelationRequestDto.setPager(pager);
    couponRelationRequestDto.setDeleted(DeletedEnum.notdelete.val());
    Long count = couponRelationService.queryCouponRelationCount(couponRelationRequestDto);
    Map<String, Object> map = new HashMap<>();
    map.put("page", pager.getPageNum());
    map.put("total", count % pager.getPageSize() == 0 ? count / pager.getPageSize() : count / pager.getPageSize() + 1);
    map.put("records", count);
    if(count > 0) {
      List<CouponRelationResponseDto> list = couponRelationService.queryCouponRelation(couponRelationRequestDto);
      map.put("rows", list);
    }
    return map;
  }
  
  @ResponseBody
  @RequestMapping(value = "/deleted", method = RequestMethod.POST)
  public ApiResult<Void> delete(String ids) {
    try {
      couponRelationService.deleteCouponRelation(ids);
      return ApiResult.success();
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }
}