/**create by liuhua at 2019年4月30日 上午10:27:45**/
package com.star.truffle.module.coupon.temp;

import java.io.IOException;

import com.star.truffle.module.build.dto.Project;
import com.star.truffle.module.build.service.BuildFiles;

public class Build {

  public static void main(String[] args) {
    Project project = new Project("D:/work/git-star/star11/star-framework-module", "D:/work/git-star/star11/star-framework-module", "coupon", 12356, "0.0.5-MODULE");
    String classpath = "com.star.truffle.module.coupon.temp.UserCoupon";
    try {
      BuildFiles.build(false, project, classpath);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
