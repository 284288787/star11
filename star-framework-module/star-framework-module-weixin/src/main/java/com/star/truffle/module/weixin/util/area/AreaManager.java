package com.star.truffle.module.weixin.util.area;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AreaManager {
	private static final String PATH = "/areas.json";
	private static Map<Long, Area> allArea;
	
	private static ObjectMapper mapper = new ObjectMapper();
	
	static {
		try {
			InputStream stream = AreaManager.class.getResourceAsStream(PATH);
			allArea = mapper.readValue(stream, new TypeReference<Map<Long, Area>>() {});
			System.out.println("areas Num " + allArea.size());
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void buildData() throws IOException {
		int min = 1;
		int max = 60000;
		Map<Long, Area> areas = Stream.generate(() -> {
			return (long) Math.round(Math.random()*(max-min)+min);
		}).limit(100).distinct().map(id -> {
			Area area = new Area(id, "areaName" + id, "shortName" + id, id);
			return area;
		}).collect(Collectors.toMap(Area::getId, area -> area));
		System.out.println("areas Num " + areas.size());
		FileWriter writer = new FileWriter("D:\\work\\git-star\\star11\\star-framework-module\\star-framework-module-weixin\\src\\main\\resources\\areas.json");
		writer.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(areas));
		writer.flush();
		writer.close();
	}
	
	public static void main(String[] args) {
		try {
			buildData();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
