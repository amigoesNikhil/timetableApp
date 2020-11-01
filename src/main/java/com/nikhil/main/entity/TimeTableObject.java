package com.nikhil.main.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TimeTableObject {
	Map<Integer,ArrayList<TimeTableEntity>> timetable;
	int noOfPeriodsADay=8;
	String[] days= {"MONDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY","SATURDAY"};
	public TimeTableObject() {
		timetable=new HashMap<Integer,ArrayList<TimeTableEntity>>();
		for(int i=0;i<6;i++) {
			ArrayList<TimeTableEntity> classesOnADay=new ArrayList<TimeTableEntity>();
			for(int j=0;j<noOfPeriodsADay;j++) {
				TimeTableEntity e=new TimeTableEntity();
				classesOnADay.add(e);
			}
			timetable.put(i, classesOnADay);
		}
	}
	
	public void convertToTimeTableType(String[][][] classesString) {
		for(int i=0;i<6;i++) {
			ArrayList<TimeTableEntity> classesOnADay=timetable.get(i);
			for(int j=0;j<8;j++) {
				TimeTableEntity e=classesOnADay.get(j);
				e.setCourseName(classesString[i][j][0]);
				e.setTecaherName(classesString[i][j][1]);
				e.setRoomNumber(classesString[i][j][2]);
				System.out.println(days[i]+" class "+(j+1)+":"+e);
			}
		}
	}

	public Map<Integer, ArrayList<TimeTableEntity>> getTimetable() {
		return timetable;
	}

	public void setTimetable(HashMap<Integer, ArrayList<TimeTableEntity>> timetable) {
		this.timetable = timetable;
	}
	
	
	
}
