package com.star.truffle.module.weixin.util.area;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Area {

	private Long id;
	private String areaName;
	private String shortName;
	private Long parentId;
}
