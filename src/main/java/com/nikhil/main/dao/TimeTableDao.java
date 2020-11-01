package com.nikhil.main.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.nikhil.main.entity.TimeTableEntity;

public interface TimeTableDao {
	
	public String[][][] getTheRoomClass(String roomnumber);
	public String[][][] getTheTeachersClass(String teachername);
	public boolean checkClassIsPresent(String classname);
	public ArrayList<String> getClassInTheDb();
	public String[][][] getTheTimeTableOfAClass(String className);
	public String getClashesOnAday(String className,ArrayList<TimeTableEntity> timetableOnDay,int day_index);
	public String getClashesOnFullTimeTable(String ClassName,HashMap<Integer,ArrayList<TimeTableEntity>> timetable);
	public void addTimeTable(String ClassName,Map<Integer,ArrayList<TimeTableEntity>> timetable)throws Exception;
	public void edidTheTimeTable(String ClassName,Map<Integer,ArrayList<TimeTableEntity>> timetable) throws Exception;
	public void deleteTimeTable(String classname)throws SQLException;
}
