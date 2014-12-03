<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="css/tableStyle.css" type="text/css" />

<title>AWWW YISS</title>
</head>
<body>
	<p>Logged in!</p>
	<p>Users currently in the database</p>
	<div style="float: left; overflow: auto; height: 400px;">
		<table id="infoTable" class="innertable">
			<thead>
				<tr>
					<th>ID</th>
					<th>Username</th>
					<th>Password</th>
				</tr>
			</thead>
			
			<c:forEach items="${allUsers}" var="user" varStatus="status">
				<tr height = "20px">
					<td>${user.id}</td>
					<td>${user.name}</td>
					<td>${user.password}</td>
				</tr>
			</c:forEach>
			
		</table>
	</div>
	<p>Graveyards currently in the database</p>
	
	<div style="float: left; overflow: auto; height: 400px;">
		<table id="infoTable" class="innertable">
			<thead>
				<tr>
					<th>ID</th>
					<th>name</th>
					<th>deleted</th>
				</tr>
			</thead>
			
			<c:forEach items="${allGraveyards}" var="graveyard" varStatus="status">
				<tr height = "20px">
					<td>${graveyard.id}</td>
					<td>${graveyard.name}</td>
					<td>${graveyard.deleted}</td>
				</tr>
			</c:forEach>
			
		</table>
	</div>
</body>
</html>