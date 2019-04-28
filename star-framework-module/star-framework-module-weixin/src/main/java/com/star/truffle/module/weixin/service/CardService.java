/**create by liuhua at 2019年4月28日 上午11:33:50**/
package com.star.truffle.module.weixin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.star.truffle.module.weixin.dao.QuanDao;
import com.star.truffle.module.weixin.dto.card.res.CardDetail;

@Service
public class CardService {

  @Autowired
  private QuanDao quanDao;

  public CardDetail getWxCardInfo(String cardId) {
    return quanDao.getCardDetail(cardId);
  }

}
