<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ page isErrorPage="true" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Time Table Application</title>
<meta charset="utf-8">
   <meta name="viewport" content="width=device-width, initial-scale=1"> 
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
  <link href="https://cdn.datatables.net/1.10.22/css/dataTables.bootstrap4.min.css" rel="stylesheet">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
  <script src="https://code.jquery.com/jquery-3.5.1.js"></script>
  <script src="https://cdn.datatables.net/1.10.22/js/jquery.dataTables.min.js"></script>
  <script src="https://cdn.datatables.net/1.10.22/js/dataTables.bootstrap4.min.js"></script>
  
  
  <style>
  
  .table-dark{
  	margin:1en;
  }
  .adeco{
  	text-decoration: none;
    color: white;
  }
  .classRow:hover{
  	background-color:#454443;
  	test-decoration:none;
  }
  .adeco:hover{
  	text-size:125%;
  	color:white;
  	test-decoration:none;
  }
  table,th,td{
  	border:1px solid black;
  }
  table{
  	border-collapse: collapse;
  }
  .navbar-nav{
  	width:100%; 
  	margin-left:50%;
  }
  
  @media(max-width:575px){
  .classbutton{
  		width:80%;
  	}
  }
  
  @media(min-width:575px){
  	.navbar-nav{
  		margin-left:30%;
  	}
  	.classbutton{
  		max-width:100%;
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
	<%

		response.setHeader("Cache-Control", "no-cache , no-store, must-revalidate");   /// http/1.1
		response.setHeader("Pragna", "no-cache"); // http 1.0
		response.setHeader("Expirse","0");
	%>
	<header>
		<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
			  <!-- Brand/logo -->
			  <a class="navbar-brand" href="/">Time Table Application</a>
			  <%
			  	if(session.getAttribute("isAdmin")!=null){
			  		System.out.println("in is admin"+session.getAttribute("isAdmin"));
			  %>
			  |<a class="navbar-brand" href="/admin">ADMIN</a>
			  <%} %>
			  <ul class="navbar-nav" > 
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
				  if(session.getAttribute("success")!=null){  %>
				  
				     <div class="alert alert-success alert-dismissible fade show " style="width:100%">
	    			 <button type="button" class="close" data-dismiss="alert">&times;</button>
				  <%
					out.println(session.getAttribute("success"));
					session.removeAttribute("success");
					 
				%></div>
				<%} %>
			<%
				  System.out.println(session.getAttribute("error"+"in home"));
				  if(session.getAttribute("error")!=null){  %>
				  
				     <div class="alert alert-danger alert-dismissible fade show" style="width:100%">
	    			 <button type="button" class="close" data-dismiss="alert">&times;</button>
				  <%
					out.println(session.getAttribute("error"));
					session.removeAttribute("error");
					 
				%></div>
				<%} %>
		</div>
		<div class="row">
			<div class="col-12 " style="margin-top:1%">
				<h3>Sorry an exception occured !</h3>  
  
				Exception is: <%= exception %>   
		  </div>
		</div>
		<div class="row">
		
		<a href="/"><button class="btn btn-outline-dark" style="width:100%">Go To Home</button></a>
		</div>
		
	</div>
	<br>
	<script >
	$(document).ready(function() {
    	$('#tableID').DataTable();
	} );

	</script>
</body>
</html>