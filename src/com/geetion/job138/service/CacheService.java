package com.geetion.job138.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import com.geetion.job138.model.CityInfo;
import com.geetion.job138.model.Station;

public class CacheService {
	public static boolean isLogined;
	public static String city;
	public static List<Station> quickSearchKeyWords = new ArrayList<Station>();

	public static Map<Integer, String> nationKeyMap = new LinkedHashMap<Integer, String>();
	public static Map<String, Integer> nationNameMap = new LinkedHashMap<String, Integer>();
	public static Map<Integer, String> sexKeyMap = new LinkedHashMap<Integer, String>();
	public static Map<String, Integer> sexNameMap = new LinkedHashMap<String, Integer>();
	public static Map<Integer, String> educKeyMap = new LinkedHashMap<Integer, String>();
	public static Map<String, Integer> educNameMap = new LinkedHashMap<String, Integer>();
	public static Map<Integer, String> marriageKeyMap = new LinkedHashMap<Integer, String>();
	public static Map<String, Integer> marriageNameMap = new LinkedHashMap<String, Integer>();
	public static Map<Integer, String> cardKeyMap = new LinkedHashMap<Integer, String>();
	public static Map<String, Integer> cardNameMap = new LinkedHashMap<String, Integer>();
	public static Map<Integer, String> jobTypeKeyMap = new LinkedHashMap<Integer, String>();
	public static Map<String, Integer> jobTypeNameMap = new LinkedHashMap<String, Integer>();
	public static Map<Integer, String> workDateKeyMap = new LinkedHashMap<Integer, String>();
	public static Map<String, Integer> workDateNameMap = new LinkedHashMap<String, Integer>();
	public static Map<Integer, String> workersKeyMap = new LinkedHashMap<Integer, String>();
	public static Map<Integer, String> ecoclassKeyMap = new LinkedHashMap<Integer, String>();
	public static Map<Integer, String> langKeyMap = new LinkedHashMap<Integer, String>();
	// public static Map<String, Integer> langNameMap = new
	// LinkedHashMap<String, Integer>();
	public static Map<Integer, String> langMasterKeyMap = new LinkedHashMap<Integer, String>();
	public static Map<String, Integer> langMasterNameMap = new LinkedHashMap<String, Integer>();
	// public static Map<String, Integer> payNameMap = new
	// LinkedHashMap<String,Integer>();
	public static Map<Integer, String> payKeyMap = new LinkedHashMap<Integer, String>();

	public static List<CityInfo> provinces = new ArrayList<CityInfo>();
	@SuppressLint("UseSparseArrays")
	public static Map<Integer, List<CityInfo>> cities = new HashMap<Integer, List<CityInfo>>();
	@SuppressLint("UseSparseArrays")
	public static Map<Integer, CityInfo> provincesMap = new HashMap<Integer, CityInfo>();

	public static void initMap() {
		// 性别Map
		sexKeyMap.put(1, "男");
		sexKeyMap.put(2, "女");
		sexNameMap.put("男", 1);
		sexNameMap.put("女", 2);

		// 最高学历Map
		/**
		 * 1>初中 2>高中 3>职高技校 4>中专 5>大专 6>大学本科 7>硕士 8>博士
		 */
		educKeyMap.put(1, "初中");
		educKeyMap.put(2, "高中");
		educKeyMap.put(3, "职高技校");
		educKeyMap.put(4, "中专");
		educKeyMap.put(5, "大专");
		educKeyMap.put(6, "大学本科");
		educKeyMap.put(7, "硕士");
		educKeyMap.put(8, "博士");

		educNameMap.put("初中", 1);
		educNameMap.put("高中", 2);
		educNameMap.put("职高技校", 3);
		educNameMap.put("中专", 4);
		educNameMap.put("大专", 5);
		educNameMap.put("大学本科", 6);
		educNameMap.put("硕士", 7);
		educNameMap.put("博士", 8);

		/**
		 * 1>未婚 2>已婚 3>离异 5>保密
		 */
		marriageKeyMap.put(1, "未婚");
		marriageKeyMap.put(2, "已婚");
		marriageKeyMap.put(3, "离异");
		marriageKeyMap.put(5, "保密");
		marriageNameMap.put("未婚", 1);
		marriageNameMap.put("已婚", 2);
		marriageNameMap.put("离异", 3);
		marriageNameMap.put("保密", 5);

		/**
		 * 0>身份证 1>驾证 2>军官证 3>护照 4>其它
		 */
		cardKeyMap.put(0, "身份证");
		cardKeyMap.put(1, "驾证");
		cardKeyMap.put(2, "军官证");
		cardKeyMap.put(3, "护照");
		cardKeyMap.put(4, "其它");
		cardNameMap.put("身份证", 0);
		cardNameMap.put("驾证", 1);
		cardNameMap.put("军官证", 2);
		cardNameMap.put("护照", 3);
		cardNameMap.put("其它", 4);

		/**
		 * 求职类型 1>全职 2>兼职 3>全职、兼职均可
		 */
		jobTypeKeyMap.put(1, "全职");
		jobTypeKeyMap.put(2, "兼职");
		jobTypeKeyMap.put(3, "全职、兼职均可");
		jobTypeNameMap.put("全职", 1);
		jobTypeNameMap.put("兼职", 2);
		jobTypeNameMap.put("全职、兼职均可", 3);

		/**
		 * 面议 1500元以下 1500～1999元 2000～2999元 3000～4499元 4500～5999元 6000～7999元
		 * 8000～9999元 10000～14999元 15000～19999元 20000元以上
		 */
		payKeyMap.put(1, "面议");
		payKeyMap.put(2, "1500～1999元");
		payKeyMap.put(3, "2000～2999元");
		payKeyMap.put(4, "3000～4499元");
		payKeyMap.put(5, "4500～5999元");
		payKeyMap.put(6, "6000～7999元");
		payKeyMap.put(7, "8000～9999元");
		payKeyMap.put(8, "10000～14999元");
		payKeyMap.put(9, "15000～19999元");
		payKeyMap.put(10, "20000元以上");

		/**
		 * 0>随时 7>1周以内 14>2周以内 30>1个月内 60>1～3个月 90>3个月以后
		 */
		workDateKeyMap.put(0, "随时");
		workDateKeyMap.put(7, "1周以内");
		workDateKeyMap.put(14, "2周以内");
		workDateKeyMap.put(30, "1个月内");
		workDateKeyMap.put(60, "1～3个月");
		workDateKeyMap.put(90, "3个月以后");
		workDateNameMap.put("随时", 0);
		workDateNameMap.put("1周以内", 7);
		workDateNameMap.put("2周以内", 14);
		workDateNameMap.put("1个月内", 30);
		workDateNameMap.put("1～3个月", 60);
		workDateNameMap.put("3个月以后", 90);

		/**
		 * 1>外语:英语 2>外语:日语 3>国语（普通话） 4>北方语（北京话） 5>吴语（上海话） 6>湘语（长沙话） 7>赣语（南昌话）
		 * 8>客家话（梅县话） 9>闽方言（潮汕话） 10>粤语（广州话） 12>其他方言 13>外语:韩语
		 */
		langKeyMap.put(1, "外语:英语");
		langKeyMap.put(2, "外语:日语");
		langKeyMap.put(3, "国语（普通话）");
		langKeyMap.put(4, "北方语（北京话）");
		langKeyMap.put(5, "吴语（上海话）");
		langKeyMap.put(6, "湘语（长沙话）");
		langKeyMap.put(7, "赣语（南昌话）");
		langKeyMap.put(8, "客家话（梅县话）");
		langKeyMap.put(9, "闽方言（潮汕话");
		langKeyMap.put(10, "粤语（广州话）");
		langKeyMap.put(12, "其他方言");
		langKeyMap.put(13, "外语:韩语");

		/**
		 * 0>请选择 1>一般 2>良好 3>熟练 4>精通
		 */
		langMasterKeyMap.put(1, "一般");
		langMasterKeyMap.put(2, "良好");
		langMasterKeyMap.put(3, "熟练");
		langMasterKeyMap.put(4, "精通");

		langMasterNameMap.put("一般", 1);
		langMasterNameMap.put("良好", 2);
		langMasterNameMap.put("熟练", 3);
		langMasterNameMap.put("精通", 4);

		/**
		 * 10人以下 10-49人 50-199人 200-499人 500-999人 1000人以上
		 */
		workersKeyMap.put(1, "10人以下");
		workersKeyMap.put(2, "10-49人");
		workersKeyMap.put(3, "50-199人");
		workersKeyMap.put(4, "200-499人");
		workersKeyMap.put(5, "500-999人");
		workersKeyMap.put(6, "1000人以上");

		/**
		 * 10>国有企业 11>集体企业 12>外商独资 13>中外合资 14>民营企业 15>股份制企业 16>行政机关 17>社会团体
		 * 18>事业单位 19>其他
		 */
		ecoclassKeyMap.put(10, "国有企业");
		ecoclassKeyMap.put(11, "集体企业");
		ecoclassKeyMap.put(12, "外商独资");
		ecoclassKeyMap.put(13, "中外合资");
		ecoclassKeyMap.put(14, "民营企业");
		ecoclassKeyMap.put(15, "股份制企业");
		ecoclassKeyMap.put(16, "行政机关");
		ecoclassKeyMap.put(17, "社会团体");
		ecoclassKeyMap.put(18, "事业单位");
		ecoclassKeyMap.put(19, "其他");
	}

	public static boolean isLogined() {
		return isLogined;
	}

	public static void saveQuickSearchKeyWords(List<Station> list) {
		quickSearchKeyWords = list;
	}

	public static void saveCity(List<CityInfo> list) {
		if (!list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				CityInfo cityInfo = list.get(i);
				int fid = cityInfo.getFid();
				if (fid == 0) {
					provinces.add(cityInfo);
				} else {
					if (!cities.containsKey(fid)) {
						List<CityInfo> city = new ArrayList<CityInfo>();
						city.add(cityInfo);
						cities.put(fid, city);
					} else {
						cities.get(fid).add(cityInfo);
					}
				}
			}
			saveProvinces(provinces);
		}
	}

	private static void saveProvinces(List<CityInfo> list) {
		for (int i = 0; i < list.size(); i++) {
			provincesMap.put(list.get(i).getId(), list.get(i));
		}
	}
}
