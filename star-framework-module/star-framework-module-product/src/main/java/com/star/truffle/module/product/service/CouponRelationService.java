/**create by framework at 2019年03月30日 10:29:30**/
package com.star.truffle.module.product.service;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.star.truffle.common.constants.DeletedEnum;
import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.module.product.cache.CouponRelationCache;
import com.star.truffle.module.product.domain.CouponRelation;
import com.star.truffle.module.product.dto.req.CouponRelationRequestDto;
import com.star.truffle.module.product.dto.res.CouponRelationResponseDto;

@Service
public class CouponRelationService {

  @Autowired
  private CouponRelationCache couponRelationCache;
  
  public void saveCouponRelation(CouponRelationRequestDto couponRelationRequestDto) {
    if(null == couponRelationRequestDto || null == couponRelationRequestDto.getBusinessType() || null == couponRelationRequestDto.getBusinessId()) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    CouponRelationRequestDto param = new CouponRelationRequestDto();
    param.setBusinessType(couponRelationRequestDto.getBusinessType());
    param.setBusinessId(couponRelationRequestDto.getBusinessId());
    this.couponRelationCache.deleteCouponRelation(param);
    if(StringUtils.isNotBlank(couponRelationRequestDto.getCouponIds())) {
      Arrays.stream(couponRelationRequestDto.getCouponIds().split(",")).forEach(couponId -> {
        CouponRelation couponRelation = new CouponRelation();
        BeanUtils.copyProperties(couponRelationRequestDto, couponRelation);
        couponRelation.setCouponId(Long.parseLong(couponId));
        saveCouponRelation(couponRelation);
      });
    }
  }

  public void saveCouponRelation(CouponRelation couponRelation) {
    if(null == couponRelation || null == couponRelation.getBusinessType() || null == couponRelation.getBusinessId() || null == couponRelation.getCouponId()) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    CouponRelationRequestDto dto = new CouponRelationRequestDto();
    dto.setBusinessId(couponRelation.getBusinessId());
    dto.setBusinessType(couponRelation.getBusinessType());
    dto.setCouponId(couponRelation.getCouponId());
    List<CouponRelationResponseDto> list = this.couponRelationCache.queryCouponRelation(dto);
    if(null != list && ! list.isEmpty()) {
      list.stream().filter(item -> item.getDeleted() == DeletedEnum.delete.val()).forEach(item -> {
        CouponRelationRequestDto updateDto = new CouponRelationRequestDto();
        updateDto.setId(item.getId());
        updateDto.setDeleted(0);
        couponRelationCache.updateCouponRelation(updateDto); 
      });
    }else {
      couponRelation.setDeleted(0);
      this.couponRelationCache.saveCouponRelation(couponRelation);
    }
  }

  public List<CouponRelationResponseDto> queryCouponRelation(CouponRelationRequestDto couponRelationRequestDto) {
    return this.couponRelationCache.queryCouponRelation(couponRelationRequestDto);
  }

  public Long queryCouponRelationCount(CouponRelationRequestDto couponRelationRequestDto) {
    return this.couponRelationCache.queryCouponRelationCount(couponRelationRequestDto);
  }

  public void deleteCouponRelation(String idstr) {
    if (StringUtils.isBlank(idstr)) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    String[] ids = idstr.split(",");
    for (String str : ids) {
      Long id = Long.parseLong(str);
      CouponRelationRequestDto dto = new CouponRelationRequestDto();
      dto.setId(id);
      dto.setDeleted(1);
      this.couponRelationCache.updateCouponRelation(dto);
    }
  }
}