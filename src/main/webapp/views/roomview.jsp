<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
	<div class="container">
		<div class="row">
			<div class="col-12 offset-md-3 col-md-6">
				<form action="/room" method="post">
					<div class="form-group" style="margin-top:1.6%">
					<label>Enter The Room Number:</label>
					<input type="text" class="form-control" name="roomnumber" placeholder="RoomNumber" required/>
				</div>
				<div class="form-group">
					<input type="submit" value="Get The class in Room" class="btn btn-outline-dark" style="text-align:center;width:100%"/>
				</div>
				
				
				</form>
			</div>
		</div>
	
	</div>

</body>
</html>