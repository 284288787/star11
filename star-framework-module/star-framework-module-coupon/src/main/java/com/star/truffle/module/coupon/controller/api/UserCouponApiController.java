/**create by framework at 2019年04月30日 10:49:00**/
package com.star.truffle.module.coupon.controller.api;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.web.ApiResult;
import com.star.truffle.core.web.ApiCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;
import lombok.extern.slf4j.Slf4j;
import com.star.truffle.module.coupon.domain.UserCoupon;
import com.star.truffle.module.coupon.service.UserCouponService;
import com.star.truffle.module.coupon.dto.req.UserCouponRequestDto;
import com.star.truffle.module.coupon.dto.res.UserCouponResponseDto;

@Slf4j
@RestController
@RequestMapping("/api/userCoupon")
@Api(tags = "UserCouponApiController")
public class UserCouponApiController {

  @Autowired
  private UserCouponService userCouponService;

  @RequestMapping(value = "/getUserCoupon", method = RequestMethod.POST)
  @ApiOperation(value = "根据主键获取用户已领取的优惠券", notes = "根据主键获取用户已领取的优惠券", httpMethod = "POST", response = UserCouponResponseDto.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "id", value = "id", dataType = "Long", required = true, paramType = "query")
  })
  public ApiResult<UserCouponResponseDto> getUserCoupon(Long id) {
    try {
      UserCouponResponseDto userCouponResponseDto = userCouponService.getUserCoupon(id);
      return ApiResult.success(userCouponResponseDto);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/queryUserCoupon", method = RequestMethod.POST)
  @ApiOperation(value = "根据条件获取用户已领取的优惠券列表", notes = "根据条件获取用户已领取的优惠券列表", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "id", value = "id", dataType = "Long", required = false, paramType = "query"),
    @ApiImplicitParam(name = "userId", value = "用户Id", dataType = "Long", required = false, paramType = "query"),
    @ApiImplicitParam(name = "couponId", value = "优惠券Id", dataType = "Long", required = false, paramType = "query"),
    @ApiImplicitParam(name = "state", value = "优惠券状态", dataType = "int", required = false, paramType = "query"),
    @ApiImplicitParam(name = "page.pageNum", value = "页码，页码如果没有则查询满足条件的所有记录", dataType = "int", required = false, paramType = "query"),
    @ApiImplicitParam(name = "page.pageSize", value = "一页最大记录数，当页码有值时，该值没有指定则默认为10", dataType = "int", required = false, paramType = "query"),
    @ApiImplicitParam(name = "page.orderBy", value = "排序字段，必须和数据库的字段一致", dataType = "String", required = false, paramType = "query"),
    @ApiImplicitParam(name = "page.orderType", value = "升序/降序", dataType = "String", required = false, paramType = "query", allowableValues = "asc,desc")
  })
  public ApiResult<List<UserCouponResponseDto>> queryUserCoupon(@ApiIgnore UserCouponRequestDto userCouponRequestDto) {
    try {
      List<UserCouponResponseDto> list = userCouponService.queryUserCoupon(userCouponRequestDto);
      return ApiResult.success(list);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/queryUserCouponCount", method = RequestMethod.POST)
  @ApiOperation(value = "根据条件获取用户已领取的优惠券数量", notes = "根据条件获取用户已领取的优惠券数量", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "id", value = "id", dataType = "Long", required = false, paramType = "query"),
    @ApiImplicitParam(name = "userId", value = "用户Id", dataType = "Long", required = false, paramType = "query"),
    @ApiImplicitParam(name = "couponId", value = "优惠券Id", dataType = "Long", required = false, paramType = "query"),
    @ApiImplicitParam(name = "state", value = "优惠券状态", dataType = "int", required = false, paramType = "query"),
  })
  public ApiResult<Long> queryUserCouponCount(@ApiIgnore UserCouponRequestDto userCouponRequestDto) {
    try {
      Long count = userCouponService.queryUserCouponCount(userCouponRequestDto);
      return ApiResult.success(count);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/saveUserCoupon", method = RequestMethod.POST)
  @ApiOperation(value = "新增用户已领取的优惠券", notes = "新增用户已领取的优惠券", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
  })
  public ApiResult<Long> saveUserCoupon(@ApiIgnore UserCoupon userCoupon) {
    try {
      Long id = userCouponService.saveUserCoupon(userCoupon);
      return ApiResult.success(id);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/deleteUserCoupon", method = RequestMethod.POST)
  @ApiOperation(value = "根据主键删除用户已领取的优惠券", notes = "根据主键删除用户已领取的优惠券", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "id", value = "id", dataType = "Long", required = true, paramType = "query")
  })
  public ApiResult<Void> deleteUserCoupon(Long id) {
    try {
      userCouponService.deleteUserCoupon(id);
      return ApiResult.success();
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

}