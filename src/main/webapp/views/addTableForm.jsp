<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ page errorPage="error.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Time Table Application(add table)</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
  <style>
  
  td,th{
  	text-align:center;
  
  }
  table,th,td{
  	border: 2px solid black;
  
  }
  table{
  	border-collapse: collapse;
  	margin:0.2em;
  
  }
  
  </style>
</head>
<body>
	<%

		response.setHeader("Cache-Control", "no-cache , no-store, must-revalidate");   /// http/1.1
		response.setHeader("Pragna", "no-cache"); // http 1.0
		response.setHeader("Expirse","0");
	%>
<%
    if(session.getAttribute("uname")==null){
    	session.setAttribute("error", " please login to ass table ");
    	response.sendRedirect("/login");
    }


%>
<header>
		<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
			  <!-- Brand/logo -->
			  <a class="navbar-brand" href="/">Time Table Application</a>
			  <%
			  	if(session.getAttribute("isAdmin")!=null){
			  %>
			  |<a class="navbar-brand" href="/admin">ADMIN</a>
			  <%} %>
			  <!-- Links -->
			  <ul class="navbar-nav" style="width:100%; margin-left:70%">
			    <li class="nav-item">
			    <%
					System.out.println(session.getAttribute("uname"));
					if(session.getAttribute("uname")==null){ %>
						<a class="nav-link my-2 my-lg-0"  href="/login">LOG IN </a>
					<%} else { %>
						<a class="nav-link my-2 my-lg-0" href="/logout">LOG OUT</a>
				<%    } %>
			    </li>
			  </ul>
			</nav>
</header>
<div class="container-fluid">
		<div class="row">
				  <%
				  if(session.getAttribute("success")!=null){  %>
				  
				     <div class="alert alert-success alert-dismissible fade show " style="width:100%">
	    			 <button type="button" class="close" data-dismiss="alert">&times;</button>
				  <%
					out.println(session.getAttribute("success"));
					session.removeAttribute("success");
					 
				%></div>
				<%} %>
			<%
				  System.out.println(session.getAttribute("error"));
				  if(session.getAttribute("error")!=null){  %>
				  
				     <div class="alert alert-danger alert-dismissible fade show" style="width:100%">
	    			 <button type="button" class="close" data-dismiss="alert">&times;</button>
				  <%
					out.println(session.getAttribute("error"));
					session.removeAttribute("error");
					 
				%></div>
				<%} %>
		</div>
		</div>
<section id="mainform">
<div class="container-fluid">
	<c:set var="alphabet" value='<%=new String[]{"MONDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY","SATURDAY"} %>'/>
		<div style="overflow-x:auto;">
		<table style="border:1px;" class="table-dark table-striped" >
			<thead>
				<tr>
				<th>DAY</th>
					<th> ATTRIBUTRES </th>
					<th>  PERIOD 1</th> 
					<th>PERIOD 2</th>
					<th>PERIOD 3</th>
					<th> PERIOD 4</th>
					<th>PERIOD 5</th>
					<th>PERIOD 6</th>
					<th>PERIOD 7</th>
					<th>PERIOD 8</th>
					<th>Clash Check</th>
				</tr>
				<tr>
					<td></td>
					<td></td>
					<td>8:00 AM-9:00 AM</td>
					<td>9:00 AM-10:00 AM</td>
					<td>10:00 AM-11:00 AM</td>
					<td>11:30 AM-12:30 PM</td>
					<td>12:30 PM-1:30PM</td>
					<td>2:00 PM-3:00 PM</td>
					<td>3:00 PM-4:00 PM</td>
					<td>4:00 PM-5:00 PM</td>
					<td></td>
				
				</tr>
			</thead>
		<form:form method="post" action="/addTable" modelAttribute="TimeTableEntries" onsubmit="return validateForm()">
		<div class="form-group">
			<input type="text" class="form-control" id="classname" name="classname" value="${classname }" placeholder="Enter The Class Name"/>
		</div>
			<c:forEach items="${TimeTableEntries.timetable }" var="TimeTable" varStatus="status1">
				<!-- ${status1.index } -->
				<tr>
				<c:set var="index1" value="${status1.index }"></c:set>
				<!-- here in the below statement alphabet is referring to the Days Name Like Monday etc.. -->
				<td> <c:out value = "${alphabet[index1]}"/>  </td>
				<td>
				SUBJECT <br>
				TEACHER <br>
				Room <br>
				</td>
				<c:forEach items="${TimeTable.value }" var="Entrie" varStatus="status">
				<td>
					<input type="text" name="timetable[${status1.index }][${status.index }][0]" value="${Entrie.courseName }"/>
					<input type="text" name="timetable[${status1.index }][${status.index }][1]" value="${Entrie.tecaherName }"/>
					<input type="text" name="timetable[${status1.index }][${status.index }][2]" value="${Entrie.roomNumber }"/>
				</td>
				</c:forEach>
				<td><input type="submit" name="command" style="height: 64px;width: 158px;" data-toggle="tooltip" title="Check Clashes" value='CHECK <c:out value = "${alphabet[index1]}"/> '/></td>
				</tr>
			</c:forEach>
			<br>
			<input type="submit" data-toggle="tooltip" title="Add The Table" name="command" class="btn btn-outline-dark" style="width:100%;" value="ADD TABLE" height=100% onclick="alert('Please do not refresh the page');"/>
		</form:form>
		</table>
		</div>
		<div>
		 	${clashes } 
		</div>
</div>
</section>
	<script>
		function validateForm(){
			var a=document.getElementById("classname");
			if(a.value==""){
				alert("class name cannot be empty");
				return false;
			}
			return true;	
		}
	</script>
	<script>
		$(document).ready(function(){
	  		$('[data-toggle="tooltip"]').tooltip();   
		});
	</script>
</body>
</html>