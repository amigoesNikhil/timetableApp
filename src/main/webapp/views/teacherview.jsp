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
  
  .navbar-nav{
  	width:100%; 
  	margin-left:50%;
  }
  
  @media(min-width:575px){
  	.navbar-nav{
  		margin-left:30%;
  	}
  }
  
  @media(min-width:815px){
  	.navbar-nav{
  		margin-left:40%;
  	}
  }
  @media(min-width:950px){
  	.navbar-nav{
  		margin-left:60%;
  	}
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
			  <ul class="navbar-nav" style="">
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
			<div class="col-12 offset-md-3 col-md-6">
	
				<form action="/teacher" method="post">
				<div class="form-group" style="margin-top:1.6%">
					<label>Enter The Teacher Code:</label>
					<input type="text" class="form-control" name="teachercode" placeholder="Enter Teacher code" required/>
				</div>
				<div class="form-group">
					<input type="submit" value="Get The Teacher Classes" class="btn btn-outline-dark" style="text-align:center;width:100%"/>
				</div>
				
				</form>
			</div>
		</div>
	</div>

</body>
</html>