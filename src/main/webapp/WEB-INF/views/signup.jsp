<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf8">
	<link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/style.css"/>" />
	<title><spring:message code="label.title" /></title>
</head>
<body>

<div align="center">
	<h3><spring:message code="label.signup"/></h3>
	<form:form method="post" commandName="user" modelAttribute="user">
		<table>
			<tr>
				<td><form:label path="login"><spring:message code="label.user_login" /></form:label></td>
				<td><form:input path="login"/></td>
				<td><form:errors path="login" cssClass="errors"/></td>
			</tr>
			<tr>
				<td><form:label path="password"><spring:message code="label.user_password" /></form:label></td>
				<td><form:password path="password"/></td>
				<td><form:errors path="password" cssClass="errors"/></td>
			</tr>
			<tr>
				<td><form:label path="name"><spring:message code="label.user_name" /></form:label></td>
				<td><form:input path="name"/></td>
				<td><form:errors path="name" cssClass="errors"/></td>
			</tr>
			<tr>
				<td><input type="submit" value="<spring:message code="label.save"/>"></td>
			</tr>
		</table>
	</form:form>
</div>
</body>
</html>