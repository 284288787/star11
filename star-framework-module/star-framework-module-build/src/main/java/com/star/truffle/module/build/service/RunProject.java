/**create by liuhua at 2018年9月3日 上午9:52:33**/
package com.star.truffle.module.build.service;

import com.star.truffle.module.build.dto.Project;

public class RunProject {
  
  public static void main(String[] args) {
    Project project = new Project("D:/work/git-star/star11/star-framework-module", "D:/work/git-star/star11/star-framework-module", "coupon", 12356, "0.0.5-MODULE");
    ProjectService projectService = new ProjectService();
    projectService.buildProject(project);
  }
}
