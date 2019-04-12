package com.star.truffle.module.weixin.dao;

import java.util.Date;

import com.star.truffle.module.weixin.dto.req.PayReqData;
import com.star.truffle.module.weixin.dto.res.PayResData;

public class Test {

	public static void main(String[] args) {
		WeiXinApiDao dao = new WeiXinApiDao();
		  PayReqData data = new PayReqData("asdfasdf", "123", "asdfasdfasdf", 10, new Date(), "wx43fd4135600dcee3"
				  , "98iujhrw3f6b87hhko09876421qaxdse", "1513883741", "http://47.105.38.227/api/weixin/pay/callback", "47.105.38.227", "JSAPI", "asdfasdf");
		  PayResData prd = dao.unifiedOrderGZH(data);
		  System.out.println(prd);
	}

}
