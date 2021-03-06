package com.geetion.job138.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;

import com.geetion.job138.model.CityInfo;
import com.geetion.job138.model.JobType;

public class StationTypeSave {
	public static List<JobType> parentJobTypes = new ArrayList<JobType>();

	@SuppressLint("UseSparseArrays")
	public static Map<Integer, List<JobType>> childJobtypes = new HashMap<Integer, List<JobType>>();
	public static Map<Integer, JobType> parentMap = new HashMap<Integer, JobType>();
	
	public static boolean isGetType() {
		if(!parentJobTypes.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void saveJobType(List<JobType> list) {
		if(!list.isEmpty()) {
			for(int i = 0; i < list.size(); i++) {
				JobType jobType = list.get(i);
				int fid = jobType.getFid();
				if(fid == 0) {
					parentJobTypes.add(jobType);
				} else { 
					if(!childJobtypes.containsKey(fid)) {
						List<JobType> childs = new ArrayList<JobType>();
						childs.add(jobType);
						childJobtypes.put(fid, childs);
					} else {
						childJobtypes.get(fid).add(jobType);
					}
				}
			}
			saveParentMap(parentJobTypes);
		}
	}
	
	private static void saveParentMap(List<JobType> list) {
		for (int i = 0; i < list.size(); i++) {
			JobType jobType = list.get(i);
			parentMap.put(jobType.getId(), jobType);
		}
	}
}
