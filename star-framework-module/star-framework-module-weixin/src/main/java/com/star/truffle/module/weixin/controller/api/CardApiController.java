/**create by liuhua at 2019年1月24日 下午4:22:06**/
package com.star.truffle.module.weixin.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.core.web.ApiResult;
import com.star.truffle.module.weixin.dto.card.res.CardDetail;
import com.star.truffle.module.weixin.service.CardService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/weixin/card")
@Api(tags = "微信卡券")
public class CardApiController {

  @Autowired
  private CardService cardService;
  
  @ResponseBody
  @RequestMapping(value = "/getWxCardInfo", method = RequestMethod.POST)
  public ApiResult<CardDetail> getWxCardInfo(String cardId) {
    try {
      CardDetail cardDetail = cardService.getWxCardInfo(cardId);
      return ApiResult.success(cardDetail);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }
}
