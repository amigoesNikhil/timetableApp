package com.nikhil.main.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.nikhil.main.dao.TimeTableDaoImp;
import com.nikhil.main.dao.UserDaoImpl;
//import com.nikhil.main.entity.TimeTableEntity;
import com.nikhil.main.entity.TimeTableObject;
import com.nikhil.main.entity.UserObject;


@Controller
public class MaterController {
	
	@Autowired
	UserDaoImpl userAuthentificationObject;
	
	@Autowired
	TimeTableDaoImp tableOperations;
	
	String[] days= {"MONDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY","SATURDAY"};
	
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public ModelAndView getTheHomePage(HttpSession session) {
		ModelAndView mv=new ModelAndView("home");
		System.out.println("in Home");
		ArrayList<String> classes=tableOperations.getClassInTheDb();
		for(String cl:classes) {
			System.out.println("got the classes as "+cl);
		}
		mv.addObject("classNames", classes);
		return mv;
	}
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public ModelAndView sendLoginForm(HttpSession session) {
		ModelAndView mv=new ModelAndView("login");
		return mv;
	}
	
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public ModelAndView logoutMethod() {
		ModelAndView mv=new ModelAndView("logout");
		return mv;
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String getLoginForm(HttpSession session,@RequestParam("uname")String username,@RequestParam("password")String password) {
		int value=userAuthentificationObject.Authenticate(username, password);
		System.out.println(username+":"+password);
		if(value>=1) {
			int isAdmin=userAuthentificationObject.checkAdmin(value);
			System.out.println(username+":"+isAdmin);
			if(isAdmin==1) {
				session.setAttribute("isAdmin", "YES");
			}
			session.setAttribute("uname", username);
			session.setAttribute("success", username+" Successfully logged in");
			return "redirect:/";
		}
		session.setAttribute("error", " Username or password wrong");
		return "redirect:/login";
	}
	
	@RequestMapping("/viewClass")
	public ModelAndView getTheClasesFoAClass(@RequestParam("classname")String classname,HttpSession session) {
		ModelAndView mv=null;
		System.out.println("In Master controller classname="+classname);
		System.out.println(tableOperations.checkClassIsPresent(classname));
		if(tableOperations.checkClassIsPresent(classname)) {
			mv=new ModelAndView("viewtable");
			String[][][] timetableArray=tableOperations.getTheTimeTableOfAClass(classname);
			TimeTableObject tableobject=new TimeTableObject();
			tableobject.convertToTimeTableType(timetableArray);
			mv.addObject("TimeTableEntries",tableobject);
			mv.addObject("classname", classname);
		} else {
			session.setAttribute("error", classname+" Not Found Sorry For That");
			mv=new ModelAndView("redirect:/");
		}
		return mv;
	}
	
	@RequestMapping("/teacherview")
	public ModelAndView showTecaherForm() {
		ModelAndView mv=new ModelAndView("teacherview");
		return mv;
	}
	
	@RequestMapping("/roomview")
	public ModelAndView showroomForm() {
		ModelAndView mv=new ModelAndView("roomview");
		return mv;
	}
	
	
	@RequestMapping("/teacher")
	public ModelAndView getTheTeacherClasses(@RequestParam("teachercode")String teachername) {
		ModelAndView mv=new ModelAndView("teachertable");
		String[][][] timetableArray=tableOperations.getTheTeachersClass(teachername);
		TimeTableObject tableobject=new TimeTableObject();
		tableobject.convertToTimeTableType(timetableArray);
		mv.addObject("TimeTableEntries",tableobject);
		mv.addObject("teachername", teachername);
		return mv;
	}
	@RequestMapping("/room")
	public ModelAndView getTheRoomClasses(@RequestParam("roomnumber")String roomnumber) {
		ModelAndView mv=new ModelAndView("roomtable");
		String[][][] timetableArray=tableOperations.getTheRoomClass(roomnumber);
		TimeTableObject tableobject=new TimeTableObject();
		tableobject.convertToTimeTableType(timetableArray);
		mv.addObject("TimeTableEntries",tableobject);
		mv.addObject("roomnumber", roomnumber);
		return mv;
	}
	
	@RequestMapping(value="/addTable",method=RequestMethod.GET)
	public ModelAndView getTheFormToAddTable(HttpSession session) {
		if(session.getAttribute("uname")==null) {
			ModelAndView mv=new ModelAndView("redirect:/");
			session.setAttribute("error", "please login to add time table");
			return mv;
		}
		ModelAndView mv=new ModelAndView("addTableForm");
		TimeTableObject tableobject=new TimeTableObject();
		mv.addObject("TimeTableEntries",tableobject);
		return mv;
	}
	
	@RequestMapping(value="/addTable",method=RequestMethod.POST)
	public ModelAndView validateOrAddClass(HttpServletRequest request,HttpSession session) {
		if(session.getAttribute("uname")==null) {
			ModelAndView mv=new ModelAndView("redirect:/");
			session.setAttribute("error", "please login to add time table");
			return mv;
		}
		ModelAndView mv=null;
		String[][][] timetableArray=new String[6][8][3];
		for(int i=0;i<6;i++) {
			for(int j=0;j<8;j++) {
				timetableArray[i][j][0]=request.getParameter("timetable["+i+"]["+j+"][0]");
				timetableArray[i][j][1]=request.getParameter("timetable["+i+"]["+j+"][1]");
				timetableArray[i][j][2]=request.getParameter("timetable["+i+"]["+j+"][2]");
			}
		}
		TimeTableObject tableobject=new TimeTableObject();
		tableobject.convertToTimeTableType(timetableArray);
		String command=request.getParameter("command");
		String classname=request.getParameter("classname");
		System.out.println(command+":"+classname);
		if(tableOperations.checkClassIsPresent(classname)) {
			mv=new  ModelAndView("addTableForm");
			session.setAttribute("error", "Time Table For The class "+classname+" Is Already present");
			mv.addObject("TimeTableEntries",tableobject);
			mv.addObject("classname", classname);
			return mv;
		} else {
			if(command.equals("ADD TABLE")) {
				try {
					tableOperations.addTimeTable(classname, tableobject.getTimetable());
					session.setAttribute("success", "successfully added the Time Table For The Class");
				} catch (Exception e) {
					session.setAttribute("error", "Sorry could not add the class");
					e.printStackTrace();
				}
				mv=new  ModelAndView("redirect:/");
			}else {
				int dayIndex=-1;
				switch(command) {
				case "CHECK SATURDAY ":
					dayIndex=5;
					break;
				case "CHECK FRIDAY ":
					dayIndex=4;
					break;
				case "CHECK THURSDAY ":
					dayIndex=3;
					break;
				case "CHECK WEDNESDAY ":
					dayIndex=2;
					break;
				case "CHECK TUESDAY ":
					dayIndex=1;
					break;
				case "CHECK MONDAY ":
					dayIndex=0;
					break;
				}
				mv=new ModelAndView("addTableForm");
				System.out.println(dayIndex);
				String clash=tableOperations.getClashesOnAday(classname,tableobject.getTimetable().get(dayIndex), dayIndex);
				System.out.println(clash);
				if(clash.equals("")) {
					clash="No Claseshes found";
				}
				System.out.println("clashes on "+days[dayIndex]+" "+ clash);
				mv.addObject("clashes", "clashes on "+days[dayIndex]+"  "+clash);
				mv.addObject("TimeTableEntries",tableobject);
				mv.addObject("classname", classname);
				return mv;
			}
		}
		return mv;
	}
	
	@RequestMapping(value="/editTable",method=RequestMethod.POST)
	public ModelAndView validateOrEditClass(HttpServletRequest request,HttpSession session) {
		if(session.getAttribute("uname")==null) {
			ModelAndView mv=new ModelAndView("redirect:/");
			session.setAttribute("error", "please login to edit time table");
			return mv;
		}
		ModelAndView mv=null;
		String[][][] timetableArray=new String[6][8][3];
		for(int i=0;i<6;i++) {
			for(int j=0;j<8;j++) {
				timetableArray[i][j][0]=request.getParameter("timetable["+i+"]["+j+"][0]");
				timetableArray[i][j][1]=request.getParameter("timetable["+i+"]["+j+"][1]");
				timetableArray[i][j][2]=request.getParameter("timetable["+i+"]["+j+"][2]");
			}
		}
		TimeTableObject tableobject=new TimeTableObject();
		tableobject.convertToTimeTableType(timetableArray);
		String command=request.getParameter("command");
		String classname=request.getParameter("className");
		System.out.println(command+":"+classname);
		if(!tableOperations.checkClassIsPresent(classname)) {
			mv=new  ModelAndView("editTableForm");
			session.setAttribute("error", "Time Table For The class "+classname+" is not present to edit");
			mv.addObject("TimeTableEntries",tableobject);
			mv.addObject("classname", classname);
			return mv;
		} else {
			if(command.equals("EDIT TABLE")) {
				try {
					tableOperations.edidTheTimeTable(classname,tableobject.getTimetable() );
					session.setAttribute("success", "successfully edited the Time Table For The Class");
				} catch (Exception e) {
					session.setAttribute("error", "Sorry could not edit the class");
					e.printStackTrace();
				}
				mv=new  ModelAndView("redirect:/");
			}else {
				int dayIndex=-1;
				switch(command) {
				case "CHECK SATURDAY ":
					dayIndex=5;
					break;
				case "CHECK FRIDAY ":
					dayIndex=4;
					break;
				case "CHECK THURSDAY ":
					dayIndex=3;
					break;
				case "CHECK WEDNESDAY ":
					dayIndex=2;
					break;
				case "CHECK TUESDAY ":
					dayIndex=1;
					break;
				case "CHECK MONDAY ":
					dayIndex=0;
					break;
				}
				mv=new ModelAndView("editTableForm");
				System.out.println(dayIndex);
				String clash=tableOperations.getClashesOnAday(classname,tableobject.getTimetable().get(dayIndex), dayIndex);
				System.out.println(clash);
				if(clash.equals("")) {
					clash="No Claseshes found";
				}
				System.out.println("clashes on "+days[dayIndex]+" "+ clash);
				mv.addObject("clashes", "clashes on "+days[dayIndex]+"  "+clash);
				mv.addObject("TimeTableEntries",tableobject);
				mv.addObject("classname", classname);
				return mv;
			}
		}
		return mv;
	}
	
	
	@RequestMapping(value="/editClass",method=RequestMethod.GET)
	public ModelAndView getEditForm(HttpSession session,@RequestParam("classname")String classname) {
		ModelAndView mv=null;
		if(session.getAttribute("uname")==null) {
			ModelAndView mv1=new ModelAndView("redirect:/");
			session.setAttribute("error", "please login to edit time table");
			return mv1;
		}
		if(tableOperations.checkClassIsPresent(classname)) {
			mv=new ModelAndView("editTableForm");
			String[][][] timetableArray=tableOperations.getTheTimeTableOfAClass(classname);
			TimeTableObject tableobject=new TimeTableObject();
			tableobject.convertToTimeTableType(timetableArray);
			mv.addObject("TimeTableEntries",tableobject);
			mv.addObject("classname", classname);
			
		} else {
			mv=new ModelAndView("redirect:/");
			session.setAttribute("error", "Class name "+classname+" not present so please add to edit");
		}
		
		return mv;
	}
	
	@RequestMapping("/admin")
	public ModelAndView getAdminPage(HttpSession session) {
		ModelAndView mv;
		String adminname=(String) session.getAttribute("uname");
		try {
			UserObject users=userAuthentificationObject.getTheUsersOtherThanRequest(adminname);
			mv=new ModelAndView("admin");
			mv.addObject("users", users);
		} catch (Exception e) {
			session.setAttribute("error", "exception occured contact admin message="+e.getMessage());
			mv=new ModelAndView("redirect:/");
			e.printStackTrace();
		}
		return mv;
	}
	
	@RequestMapping(value="/deleteClass",method=RequestMethod.GET)
	public ModelAndView getDeletePage(@RequestParam("classname")String classname,HttpSession session) {
		if(session.getAttribute("uname")==null) {
			ModelAndView mv=new ModelAndView("redirect:/");
			session.setAttribute("error", "please login to add time table");
			return mv;
		}
		ModelAndView mv=new ModelAndView("redirect:/");
		if(tableOperations.checkClassIsPresent(classname)) {
			try {
				tableOperations.deleteTimeTable(classname);
				session.setAttribute("success", "Successfully delete the timetable for the class "+classname);
			} catch (SQLException e) {
				session.setAttribute("error", "Sorry for inconvience exception occured class admin");
				e.printStackTrace();
			}
		} else {
			session.setAttribute("error", "Time Table For The class "+classname+" is not present to delete");
		}
		return mv;
	}
		
}
