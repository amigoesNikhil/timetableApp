package com.nikhil.main.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.nikhil.main.dao.UserDaoImpl;
import com.nikhil.main.entity.UserEntity;

@Controller
public class AdminUserController {
	
	@Autowired
	UserDaoImpl userOperationClass;
	
	@RequestMapping(value="/addUser",method=RequestMethod.GET)
	public ModelAndView getTheAddUserForm(HttpSession session) {
		if(session.getAttribute("isAdmin")==null) {
			ModelAndView mv=new ModelAndView("redirect:/");
			session.setAttribute("error", "please login to add user");
			return mv;
		}
		ModelAndView mv=new ModelAndView("adduser");
		return mv;
	}
	
	@RequestMapping(value="/addUser",method=RequestMethod.POST)
	public ModelAndView addTheUser(HttpServletRequest request,HttpSession session) {
		if(session.getAttribute("isAdmin")==null) {
			ModelAndView mv=new ModelAndView("redirect:/");
			session.setAttribute("error", "please login to add user");
			return mv;
		}
		ModelAndView mv=null;
		String usernameToAdd=request.getParameter("uname");
		String password=request.getParameter("password");
		int isAdmin=Integer.parseInt(request.getParameter("isAdmin"));
		System.out.println(usernameToAdd+":"+password+":"+isAdmin);
		try {
			userOperationClass.addUser(usernameToAdd, password, isAdmin);
			session.setAttribute("success", "Successfully added user");
			mv=new ModelAndView("redirect:/admin");
		}
		catch(Exception e) {
			session.setAttribute("error", "Sorry could not add user because "+e.getMessage());
			mv=new ModelAndView("redirect:/addUser");
		}
		return mv;
	}
	
	@RequestMapping(value="/edituser",method=RequestMethod.GET)
	public ModelAndView editUserForm(HttpServletRequest request,HttpSession session) {
		if(session.getAttribute("isAdmin")==null) {
			ModelAndView mv=new ModelAndView("redirect:/");
			session.setAttribute("error", "please login to edit user");
			return mv;
		}
		ModelAndView mv=null;
		try {
			int id=Integer.parseInt(request.getParameter("userId"));
			UserEntity user=userOperationClass.getUserData(id);
			if(user.getUsername().equals(session.getAttribute("uname"))) {
				session.setAttribute("error", "You cannot chnage your own password by yourself");
				mv=new ModelAndView("redirect:/admin");
			} else {
				mv=new ModelAndView("modifyuser");
				mv.addObject("user", user);
				//System.out.println(user);
			}
		}catch(Exception e) {
			session.setAttribute("error", "In valid User ID");
			mv=new ModelAndView("redirect:/admin");
		}	
		return mv;
		
	}
	
	@RequestMapping(value="/edituser",method=RequestMethod.POST)
	public ModelAndView editUser(HttpServletRequest request,HttpSession session) {
		if(session.getAttribute("isAdmin")==null) {
			ModelAndView mv=new ModelAndView("redirect:/");
			session.setAttribute("error", "please login to edit user");
			return mv;
		}
		ModelAndView mv=new ModelAndView("redirect:/admin");
		int id=Integer.parseInt(request.getParameter("userId"));
		try {
			UserEntity user=userOperationClass.getUserData(id);
			String changePassword=request.getParameter("password");
			int isAdmin=Integer.parseInt(request.getParameter("isAdmin"));
			if(changePassword.equals(user.getPassword()) && isAdmin==user.getIsAdmin()) {
				session.setAttribute("success", "You Did Not Make Any Changes");
			} else {
				userOperationClass.changeUserDetails(id,changePassword,isAdmin);
				session.setAttribute("success", "Successfully made changes");
			}
			
		} catch (Exception e) {
			session.setAttribute("error", "In valid User ID");
			e.printStackTrace();
		}
		return mv;
	}
	
	
	@RequestMapping(value="/deleteuser")
	public ModelAndView deleteUser(@RequestParam("userId")int id,HttpSession session) {
		if(session.getAttribute("isAdmin")==null) {
			ModelAndView mv=new ModelAndView("redirect:/");
			session.setAttribute("error", "please login to delete user");
			return mv;
		}
		ModelAndView mv=new ModelAndView("redirect:/admin");
		try {
			UserEntity user=userOperationClass.getUserData(id);
			userOperationClass.deleteUser(id);
			session.setAttribute("success", "Successfully delete user "+ user.getUsername());
			
		} catch (Exception e) {
			session.setAttribute("error", "Invalid User ID");
			e.printStackTrace();
		}
		return mv;
	}
}
