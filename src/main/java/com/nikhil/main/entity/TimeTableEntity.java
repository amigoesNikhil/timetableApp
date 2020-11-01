package com.nikhil.main.entity;

public class TimeTableEntity { 
		String courseName;
		String tecaherName;
		String roomNumber;
		public String getCourseName() {
			return courseName;
		}
		public void setCourseName(String courseName) {
			this.courseName = courseName;
		}
		public String getTecaherName() {
			return tecaherName;
		}
		public void setTecaherName(String tecaherName) {
			this.tecaherName = tecaherName;
		}
		public String getRoomNumber() {
			return roomNumber;
		}
		public void setRoomNumber(String roomNumber) {
			this.roomNumber = roomNumber;
		}
		@Override
		public String toString() {
			return "TimeTableEntity [courseName=" + courseName + ", tecaherName=" + tecaherName + ", roomNumber="
					+ roomNumber + "]";
		}
		
		public String compareTo(TimeTableEntity o) {
			String clash="";
			return clash;
		}


}
