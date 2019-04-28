/**create by framework at 2019年03月25日 14:18:36**/
package com.star.truffle.module.coupon.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.star.truffle.common.choosedata.ChooseDataIntf;
import com.star.truffle.common.choosedata.GridColumn;
import com.star.truffle.common.choosedata.GridPagerResponse;
import com.star.truffle.common.constants.DeletedEnum;
import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.jackson.StarJson;
import com.star.truffle.core.jdbc.Page;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.module.coupon.cache.CouponCache;
import com.star.truffle.module.coupon.domain.Coupon;
import com.star.truffle.module.coupon.dto.req.CouponRequestDto;
import com.star.truffle.module.coupon.dto.res.CouponResponseDto;

@Service
public class CouponService implements ChooseDataIntf {

  @Autowired
  private StarJson starJson;
  @Autowired
  private CouponCache couponCache;

  public void saveCoupon(Coupon coupon) {
    if (null == coupon || StringUtils.isBlank(coupon.getCardId()) || StringUtils.isBlank(coupon.getTitle()) || 
        StringUtils.isBlank(coupon.getDescription()) || StringUtils.isBlank(coupon.getCardType())) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    CouponRequestDto couponRequestDto = new CouponRequestDto();
    couponRequestDto.setCardId(coupon.getCardId());
    List<CouponResponseDto> list = this.couponCache.queryCoupon(couponRequestDto);
    if(null != list && ! list.isEmpty()) {
      long deletedNum = list.stream().filter(item -> item.getDeleted() == DeletedEnum.delete.val()).count();
      if(deletedNum > 0) {
        list.stream().filter(item -> item.getDeleted() == DeletedEnum.delete.val()).forEach(item -> {
          CouponRequestDto updateDto = new CouponRequestDto();
          BeanUtils.copyProperties(item, updateDto);
          updateDto.setDeleted(0);
          updateDto.setView(coupon.getView());
          updateDto.setViewHome(coupon.getViewHome());
          updateDto.setViewDialog(coupon.getViewDialog());
          this.couponCache.updateCoupon(updateDto);
        });
      }else {
        throw new StarServiceException(ApiCode.PARAM_ERROR, "优惠券已经存在");
      }
    }else {
      coupon.setCreateTime(new Date());
      coupon.setEnabled(1);
      coupon.setDeleted(0);
      this.couponCache.saveCoupon(coupon);
    }
  }

  public void updateCoupon(CouponRequestDto couponRequestDto) {
    if (null == couponRequestDto || null == couponRequestDto.getCouponId()) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    this.couponCache.updateCoupon(couponRequestDto);
  }

  public void deleteCoupon(Long couponId) {
    this.couponCache.deleteCoupon(couponId);
  }

  public void deleteCoupon(String idstr) {
    if (StringUtils.isBlank(idstr)) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    String[] couponIds = idstr.split(",");
    for (String str : couponIds) {
      Long couponId = Long.parseLong(str);
      CouponRequestDto dto = new CouponRequestDto();
      dto.setCouponId(couponId);
      dto.setDeleted(DeletedEnum.delete.val());
      this.couponCache.updateCoupon(dto);
    }
  }

  public CouponResponseDto getCoupon(Long couponId) {
    CouponResponseDto couponResponseDto = this.couponCache.getCoupon(couponId);
    return couponResponseDto;
  }

  public List<CouponResponseDto> queryCoupon(CouponRequestDto couponRequestDto) {
    return this.couponCache.queryCoupon(couponRequestDto);
  }

  public Long queryCouponCount(CouponRequestDto couponRequestDto) {
    return this.couponCache.queryCouponCount(couponRequestDto);
  }

  @Override
  public GridPagerResponse getDatas(Map<String, Object> condition, Page pager) {
    CouponRequestDto couponRequestDto = starJson.str2obj(starJson.obj2string(condition), CouponRequestDto.class);
    couponRequestDto.setDeleted(0);
    couponRequestDto.setPager(pager);
    Long count = couponCache.queryCouponCount(couponRequestDto);
    List<CouponResponseDto> list = new ArrayList<>();
    if (count > 0) {
      list = couponCache.queryCoupon(couponRequestDto);
    }
    long total = count % pager.getPageSize() == 0 ? count / pager.getPageSize() : count / pager.getPageSize() + 1;
    return new GridPagerResponse(pager.getPageNum(), total, count, list);
  }

  @Override
  public List<GridColumn> getGridColumns() {
    List<GridColumn> columns = new ArrayList<>();
    columns.add(GridColumn.builder().caption("couponId").javaName("couponId").dsName("coupon_id").hidden(true).build());
    columns.add(GridColumn.builder().caption("优惠券标题").javaName("title").dsName("title").query(true).type("text").placeholder("优惠券标题").build());
    return columns;
  }

  @Override
  public String getPrimaryKey() {
    return "couponId";
  }

}