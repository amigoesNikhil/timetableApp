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
<title>Time Table Application</title>
<meta charset="utf-8">
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
  caption { 
  	display: table-caption;
  	text-align: center;
  }
  .table-dark td, .table-dark th, .table-dark thead th {
    border-color: #454d55;
    min-width: 150px;
	}
  
  </style>
</head>
<body>
<header>
	<%

		response.setHeader("Cache-Control", "no-cache , no-store, must-revalidate");   /// http/1.1
		response.setHeader("Pragna", "no-cache"); // http 1.0
		response.setHeader("Expirse","0");
	%>
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
	<section>
<c:set var="alphabet" value='<%=new String[]{"MONDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY","SATURDAY"} %>'/>
<div style="overflow-x:auto;">
<%!
	
	String[] days={"MONDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY","SATURDAY"};%>
	<div class="btn btn-outline-dark" style="width:100%;margin:0.2em;"> Time Table For The Teacher Code= ${teachername } </div>
	<table style="border:1px" class="table-dark table-striped">
		<thead>
			<tr>
			<th>DAY</th>
				<th>ATTRIBUTRE </th>
				<th>PERIOD 1</th> 
				<th>PERIOD 2</th>
				<th>PERIOD 3</th>
				<th>PERIOD 4</th>
				<th>PERIOD 5</th>
				<th>PERIOD 6</th>
				<th>PERIOD 7</th>
				<th>PERIOD 8</th>
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
			</tr>
		</thead>
	<form:form  action="" modelAttribute="TimeTableEntries">
		<c:forEach items="${TimeTableEntries.timetable }" var="TimeTable" varStatus="status1">
			<!-- ${status1.index } -->
			<tr>
			<c:set var="index1" value="${status1.index }"></c:set>
			<td> <c:out value = "${alphabet[index1]}"/>  </td>
			<td>
				SUBJECT <br>
				CLASS <br>
				Room <br>
			</td>
			<c:forEach items="${TimeTable.value }" var="Entrie" varStatus="status">
				<td>
				 	<p> ${Entrie.courseName } <br> ${Entrie.tecaherName } <br> ${Entrie.roomNumber } </p>
				 	
				</td>
			</c:forEach>
			</tr>
		</c:forEach>
		<br>
		</form:form>
	</table>
	</div>
</section>
</body>
</html>