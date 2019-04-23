/**create by liuhua at 2019年1月24日 下午4:22:06**/
package com.star.truffle.module.weixin.controller.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/weixin/card")
@Api(tags = "微信卡券")
public class CardApiController {

//  @RequestMapping(value = "/list", method = RequestMethod.POST)
//  @ApiOperation(value = "获取可以领取的卡券列表", notes = "获取可以领取的卡券列表", httpMethod = "POST", response = WeixinConfig.class)
//  public ApiResult<List<WeixinConfig>> getCardList() throws Exception {
//    try {
//      List<WeixinConfig> cards = ticketService.getCardList();
//      return ApiResult.success(cards);
//    } catch (StarServiceException e) {
//      return ApiResult.fail(e);
//    } catch (Exception e) {
//      log.error(e.getMessage(), e);
//      return ApiResult.fail();
//    }
//  }
}
