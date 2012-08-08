<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=utf-8"/>
		<title>Activity Feeder</title>
		<link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/style.css"/>" />
		
		<link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/extended_menu.css"/>" />		
	
		<script type="text/javascript" src="<c:url value="/resources/js/jquery-1.7.min.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/resources/js/menu.js"/>"></script> 				
		
		<script type="text/javascript">
			$(document).ready(function () {
				initialize_menu();	
				$('#centreContent').css('margin-left', 0);	
			});
		</script>
		<script type="text/javascript">
			<tiles:insertAttribute name="js"/>	
		</script>
	</head>
	<body>
		<div id="wrapper">
			<div id="outer">
				<div id="blueBlock">
					<div id="menu">
					
						<ul class="topnav">   
							<li><a href="<c:url value="/"/>">Main</a></li>
							<sec:authorize access="hasRole('ROLE_ADMIN')">  
								<li><a href="<c:url value="/group"/>">Group Management</a></li> 
							</sec:authorize>
							<li><a href="<c:url value="/user/info"/>">My Account</a></li>
							<li><a href="<c:url value="/logout"/>">Logout</a></li> 
						</ul>  
						
					</div>
				</div>
				<div id="centreContent">
					<tiles:insertAttribute name="body" />	
				</div>
			</div>
		</div>
	</body>
</html>