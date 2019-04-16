/**create by framework at 2019年03月30日 10:29:30**/
package com.star.truffle.module.weixin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.core.web.ApiResult;
import com.star.truffle.module.weixin.dto.req.CouponRelationRequestDto;
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

}