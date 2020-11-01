package com.nikhil.main.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nikhil.main.entity.TimeTableEntity;
import com.nikhil.main.util.ConnectDatabase;

@Component
public class TimeTableDaoImp implements TimeTableDao {
	public static final int noOfClassesOnADay=8;
	
	@Autowired
	ConnectDatabase database;
		
	@Override
	public ArrayList<String> getClassInTheDb() {
		Connection con=database.getConnection();
		ArrayList<String> classes=new ArrayList<String>();
		String sqlGetClasses="SELECT * FROM CLASS";
		Statement stmt;
		try {
			stmt = con.createStatement();
			ResultSet rs=stmt.executeQuery(sqlGetClasses);
			while(rs.next()) {
				classes.add(rs.getString("CLASS_NAME"));
				System.out.println(rs.getString("CLASS_NAME"));
				//con.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return classes;
	}

	@Override
	public String[][][] getTheTimeTableOfAClass(String className) {
		String sqlToGetClass="SELECT * FROM TIMETABLEVIEW1 WHERE CLASS_NAME=?";
		String[][][] timeTableArrayForm=new String[6][noOfClassesOnADay][3];
		for(int i=0;i<6;i++) {
			for(int j=0;j<noOfClassesOnADay;j++) {
				for(int k=0;k<3;k++) {
					timeTableArrayForm[i][j][k]="";
				}
			}
		}
		try {
			Connection con=database.getConnection();
			PreparedStatement ps=con.prepareStatement(sqlToGetClass);
			ps.setString(1, className);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				int dayIndex=rs.getInt("DAY_ID");
				int periodNumber=rs.getInt("PERIOD_NUMBER")-1;
				String courseName=rs.getString("COURSE_NAME");
				String teacherName=rs.getString("TEACHER_NAME");
				String roomNumber=rs.getString("ROOM_NUMBER");
				timeTableArrayForm[dayIndex][periodNumber][0]=courseName;
				timeTableArrayForm[dayIndex][periodNumber][1]=teacherName;
				timeTableArrayForm[dayIndex][periodNumber][2]=roomNumber;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return timeTableArrayForm;
	}

	@Override
	public String getClashesOnAday(String className, ArrayList<TimeTableEntity> timetableOnDay, int day_index) {
		String clash="";
		Connection con=database.getConnection();
		String sqlToCheckTeacherClash="SELECT * FROM TIMETABLEVIEW1 WHERE (TEACHER_NAME LIKE ? OR TEACHER_NAME LIKE ? OR TEACHER_NAME LIKE ?) AND DAY_ID=? AND PERIOD_NUMBER=? AND CLASS_NAME!=?;";
		/* This sql has  a total of 4 paramaters
		1-> The Teacher Name To Check
		2->day index like on MONDAY =0; like convention
		3->The Period Number which ranges from 1-8
		4-> The Present TimeTable Class name which should not equal while checking
		*/
		String sqlToCheckRoomClash="SELECT * FROM TIMETABLEVIEW1 WHERE (ROOM_NUMBER LIKE ? OR ROOM_NUMBER LIKE ? OR ROOM_NUMBER LIKE ?) AND DAY_ID=? AND PERIOD_NUMBER=? AND CLASS_NAME!=?;";
		/* This sql has  a total of 4 paramaters
		1-> The Room Number To Check
		2->day index like on MONDAY =0; like convention
		3->The Period Number which ranges from 1-8
		4-> The Present TimeTable Class name which should not equal while checking
		*/
		String teacherClash;
		String roomClash;
		for(int i=1;i<=noOfClassesOnADay;i++) {
			try {
				PreparedStatement ps1=con.prepareStatement(sqlToCheckTeacherClash);
				PreparedStatement ps2=con.prepareStatement(sqlToCheckRoomClash);
				TimeTableEntity e=timetableOnDay.get(i-1);
				teacherClash="";
				roomClash="";
				if(!e.getTecaherName().equals("")){
					String[] teachers=e.getTecaherName().strip().split("/");
					System.out.println(teachers);
					for(String teachername:teachers)
					 {
						System.out.println("Teacher:"+teachername);
						//String teachername=e.getTecaherName();
						ps1.setString(1, teachername);
						ps1.setString(2, "%/"+teachername+"%");
						ps1.setString(3, "%"+teachername+"/%");
						ps1.setInt(4, day_index);
						ps1.setInt(5, i);
						ps1.setString(6, className);
						ResultSet rs=ps1.executeQuery();
						while(rs.next()) {
							teacherClash=rs.getString("CLASS_NAME");
							// There is a clash for the teacher
						}
						if(!teacherClash.equals("")) {
							clash+="<br>There is a clash for teacher "+teachername+" for class "+ teacherClash+" on Period "+i+"\n";
						}
					} 
				}
				if(!e.getRoomNumber().equals("")) {
					String[] rooms=e.getRoomNumber().strip().split("/");
					System.out.println(rooms);
					for(String roomNumber:rooms) {
					//String roomNumber=e.getRoomNumber();
						System.out.println("Room:"+roomNumber);
						ps2.setString(1, roomNumber);
						ps2.setString(2, "%/"+roomNumber+"%");
						ps2.setString(3, "%"+roomNumber+"/%");
						ps2.setInt(4, day_index);
						ps2.setInt(5, i);
						ps2.setString(6, className);
						ResultSet rs=ps2.executeQuery();
						while(rs.next()) {
							roomClash=rs.getString("CLASS_NAME");
							// There is a clash for the teacher
						}
						if(!roomClash.equals("")) {
							clash+="<br>There is a clash for room "+roomNumber+" for class "+ roomClash+" on Period "+i+"\n";
						}
					}
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "Exception occured Sorry Please Try Again or Contact Administrator";
			}
			
		}
		System.out.println("in DAO"+clash);
		return clash;
	}

	@Override
	public String getClashesOnFullTimeTable(String ClassName, HashMap<Integer, ArrayList<TimeTableEntity>> timetable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkClassIsPresent(String classname) {
		Connection con=database.getConnection();
		String sqlGetClass="SELECT * FROM CLASS WHERE CLASS_NAME=?";
		try {
			PreparedStatement ps=con.prepareStatement(sqlGetClass);
			ps.setString(1, classname);
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public String[][][] getTheTeachersClass(String teachername) {
	
		String sqlToGetClass="SELECT * FROM TIMETABLEVIEW1 WHERE TEACHER_NAME LIKE ? OR TEACHER_NAME LIKE ? OR TEACHER_NAME LIKE ?";
		String[][][] timeTableArrayForm=new String[6][noOfClassesOnADay][3];
		for(int i=0;i<6;i++) {
			for(int j=0;j<noOfClassesOnADay;j++) {
				for(int k=0;k<3;k++) {
					timeTableArrayForm[i][j][k]="";
				}
			}
		}
		try {
			Connection con=database.getConnection();
			PreparedStatement ps=con.prepareStatement(sqlToGetClass);
			ps.setString(1, teachername);
			ps.setString(2, "%/"+teachername+"%");
			ps.setString(3, "%"+teachername+"/%");
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				int dayIndex=rs.getInt("DAY_ID");
				int periodNumber=rs.getInt("PERIOD_NUMBER")-1;
				String courseName=rs.getString("COURSE_NAME");
				String className=rs.getString("CLASS_NAME");
				String roomNumber=rs.getString("ROOM_NUMBER");
				timeTableArrayForm[dayIndex][periodNumber][0]=courseName;
				timeTableArrayForm[dayIndex][periodNumber][1]=className;
				timeTableArrayForm[dayIndex][periodNumber][2]=roomNumber;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return timeTableArrayForm;
	}

	@Override
	public String[][][] getTheRoomClass(String roomnumber) {
		String sqlToGetClass="SELECT * FROM TIMETABLEVIEW1 WHERE ROOM_NUMBER LIKE ? OR ROOM_NUMBER LIKE ? OR ROOM_NUMBER LIKE ?";
		String[][][] timeTableArrayForm=new String[6][noOfClassesOnADay][3];
		for(int i=0;i<6;i++) {
			for(int j=0;j<noOfClassesOnADay;j++) {
				for(int k=0;k<3;k++) {
					timeTableArrayForm[i][j][k]="";
				}
			}
		}
		try {
			Connection con=database.getConnection();
			PreparedStatement ps=con.prepareStatement(sqlToGetClass);
			ps.setString(1, roomnumber);
			ps.setString(2, "%/"+roomnumber+"%");
			ps.setString(3, "%"+roomnumber+"/%");
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				int dayIndex=rs.getInt("DAY_ID");
				int periodNumber=rs.getInt("PERIOD_NUMBER")-1;
				String courseName=rs.getString("COURSE_NAME");
				String teacherName=rs.getString("TEACHER_NAME");
				String className=rs.getString("CLASS_NAME");
				timeTableArrayForm[dayIndex][periodNumber][0]=courseName;
				timeTableArrayForm[dayIndex][periodNumber][1]=teacherName;
				timeTableArrayForm[dayIndex][periodNumber][2]=className;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return timeTableArrayForm;
	}

	@Override
	public void addTimeTable(String ClassName, Map<Integer, ArrayList<TimeTableEntity>> map)
			throws Exception {
		Connection con=database.getConnection();
		String sqlCallToGetPeriodId="call get_the_period_id(?,?,?,?);";
		String sqlToInsertClass="INSERT INTO CLASS(CLASS_NAME) VALUES(?)";
		String sqlToInsertTimeTable="INSERT INTO TIMETABLE(CLASS_ID,DAY_ID,PERIOD_NUMBER,PERIOD_ID) VALUES(?,?,?,?)";
		/// Insert the class into time table
		PreparedStatement ps1=con.prepareStatement(sqlToInsertClass);
		ps1.setString(1, ClassName);
		ps1.execute();
		/// Get the class id for the inserted class'
		String callStatement="{call get_the_class_id(?,?)}";
		CallableStatement call=con.prepareCall(callStatement);
		call.setString(1, ClassName);
		call.registerOutParameter(2,Types.INTEGER);
		call.execute();
		int classId=call.getInt(2);
		
		//creating callable statement to get the period_id
		
		// Inserting the values into the database
		for(int i=0;i<6;i++) {
			ArrayList<TimeTableEntity> classesOnDay=map.get(i);
			for(int j=1;j<=noOfClassesOnADay;j++) {
				PreparedStatement ps=con.prepareStatement(sqlToInsertTimeTable);
				CallableStatement callToGetPeriod=con.prepareCall(sqlCallToGetPeriodId);
				TimeTableEntity entity=classesOnDay.get(j-1);
				// Calling the stored procedure to get the period_id
				callToGetPeriod.setString(1, entity.getCourseName());
				callToGetPeriod.setString(2, entity.getTecaherName());
				callToGetPeriod.setString(3, entity.getRoomNumber());
				callToGetPeriod.registerOutParameter(4, Types.INTEGER);
				callToGetPeriod.execute();
				int periodId=callToGetPeriod.getInt(4);
				ps.setInt(1, classId);
				ps.setInt(2, i);
				ps.setInt(3, j);
				ps.setInt(4, periodId);
				ps.execute();
			}
		}
		
		
	}
	@Override
	public void edidTheTimeTable(String ClassName, Map<Integer, ArrayList<TimeTableEntity>> map)
			throws Exception {
		Connection con=database.getConnection();
		String sqlCallToGetPeriodId="call get_the_period_id(?,?,?,?);";
		String sqlToUpdateTimeTable="UPDATE TIMETABLE SET PERIOD_ID=? WHERE CLASS_ID=? AND DAY_ID=? AND PERIOD_NUMBER=?";
		/// Insert the class into time table
		/// Get the class id for the inserted class'
		String callStatement="{call get_the_class_id(?,?)}";
		CallableStatement call=con.prepareCall(callStatement);
		call.setString(1, ClassName);
		call.registerOutParameter(2,Types.INTEGER);
		call.execute();
		int classId=call.getInt(2);
		if(classId<=0 ) {
			throw new Exception("Class Not Found "+ClassName);
		}
		
		//creating callable statement to get the period_id
		
		// Updating the values into the database
		for(int i=0;i<6;i++) {
			ArrayList<TimeTableEntity> classesOnDay=map.get(i);
			for(int j=1;j<=noOfClassesOnADay;j++) {
				PreparedStatement ps=con.prepareStatement(sqlToUpdateTimeTable);
				CallableStatement callToGetPeriod=con.prepareCall(sqlCallToGetPeriodId);
				TimeTableEntity entity=classesOnDay.get(j-1);
				// Calling the stored procedure to get the period_id
				callToGetPeriod.setString(1, entity.getCourseName());
				callToGetPeriod.setString(2, entity.getTecaherName());
				callToGetPeriod.setString(3, entity.getRoomNumber());
				callToGetPeriod.registerOutParameter(4, Types.INTEGER);
				callToGetPeriod.execute();
				int periodId=callToGetPeriod.getInt(4);
				ps.setInt(1, periodId);
				ps.setInt(2, classId);
				ps.setInt(3,i);
				ps.setInt(4, j);
				ps.execute();
				//System.out.println("successfully editrd:"+i+":"+j+":"+entity.getCourseName()+":"+entity.getTecaherName()+":"+entity.getRoomNumber()+":"+periodId);
			}
		}
	}

	@Override
	public void deleteTimeTable(String classname) throws SQLException {
		String sqlToDeleteClass="DELETE FROM CLASS WHERE CLASS_ID=?";
		String sqlToDeleteTimeTableOfClass="DELETE FROM TIMETABLE WHERE CLASS_ID=?";
		Connection con=database.getConnection();
		String callStatement="{call get_the_class_id(?,?)}";
		CallableStatement call=con.prepareCall(callStatement);
		call.setString(1, classname);
		call.registerOutParameter(2,Types.INTEGER);
		call.execute();
		int classId=call.getInt(2);
		PreparedStatement ps=con.prepareStatement(sqlToDeleteTimeTableOfClass);
		ps.setInt(1, classId);
		ps.execute();
		PreparedStatement ps1=con.prepareStatement(sqlToDeleteClass);
		ps1.setInt(1, classId);
		ps1.execute();
		
	}

}
