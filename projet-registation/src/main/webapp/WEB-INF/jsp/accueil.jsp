<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<div>
		<c:forEach items="${document}" var="document">
			<p>${document}</p>
			</c:forEach>
	</div>

	<div>
		<c:forEach items="${materiel}" var="materiel">
			<p>${materiel}</p>
			</c:forEach>
	</div>
	
	
	
</body>
</html>