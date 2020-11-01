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
</head>
<body>
	<%

		response.setHeader("Cache-Control", "no-cache , no-store, must-revalidate");   /// http/1.1
		response.setHeader("Pragna", "no-cache"); // http 1.0
		response.setHeader("Expirse","0");
	%>
<%
	if(session.getAttribute("isAdmin")==null){
		response.sendRedirect("/");
	}


%>
<header>
		<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
			  <!-- Brand/logo -->
			  <a class="navbar-brand" href="/">Time Table Application</a>
			  
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
	<div class="container">
		<div class="row">
				  <%
				  System.out.println(session.getAttribute("success"));
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
	<br>
		<div class="row">
		<div class="col-12 offset-md-3 col-md-5">
			<form action="/edituser" method="post" style="width: 100%;">
			<div class="form-group" style="margin-top:1.6%">
			<input type="hidden" name="userId" value="${user.id }"/>
				<label>User Name:</label>${user.username }<br>
				<label>User password:</label><input type="text" name="password" class="form-control" value="${user.password }"/><br>
				<label>Is The User admin :</label>
				<c:if test="${user.isAdmin==1 }">
						<input type="radio" name="isAdmin" value="1"  checked/><label>YES</label>
						<input type="radio" name="isAdmin" value="0"  /><label>NO</label>
				</c:if>
				<c:if test="${user.isAdmin==0 }">
						<input type="radio" name="isAdmin" value="1"  /><label>YES</label>
						<input type="radio" name="isAdmin" value="0"  checked/><label>NO</label>
				</c:if>
				<input type="submit" value="Modify USER" class="btn btn-outline-dark" style="text-align:center;width:100%"/>
			</div>
			</form>
			<a href="/"><button class="btn btn-outline-dark" style="text-align:center;width:100%" >Go back To Home</button></a>
			</div>
		</div>
	</div>

</body>
</html>