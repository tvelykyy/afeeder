<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<script type="text/javascript">
$(document).ready(function () {
	$('#generate-token-button').click(function (){
		$.ajax({  
			type: "POST",  
		  	url: "<c:url value="/user/generatetoken"/>",
		  	success: function(response) {
		  		$('#token-td').html(response.result.token);
		  		$('#last-token-usage-td').html(new Date(response.result.lastTokenUsage).format("yyyy-mm-dd hh:mm:ss"));
		  		$('#token-active-td').html("true");
		    },
		    error: function(response) {
		    	alert(response);
		    }
		}); 
	});
});
</script>

<h1><spring:message code="label.user_info" /></h1>
<table class="list-table">
	<tr>
		<td><spring:message code="label.user_name" /></td>
		<td>${user.name}</td>
	</tr>
	<tr>
		<td><spring:message code="label.user_login" /></td>
		<td>${user.login}</td>
	</tr>
	<tr>
		<td><spring:message code="label.user_token" /></td>
		<td id="token-td">
		<c:choose>
			<c:when test="${!empty user.token}">
				${user.token}
			</c:when>
			<c:otherwise>
	  			<spring:message code="label.user_no_token"/>
			</c:otherwise>
		</c:choose>
		</td>
	</tr>
	<tr>
		<td><spring:message code="label.user_last_token_usage" /></td>
		<td id="last-token-usage-td">
		<c:choose>	
			<c:when test="${!empty user.token}">
				${user.lastTokenUsage}
			</c:when>
			<c:otherwise>
	  			<spring:message code="label.user_no_token_last_usage"/>
			</c:otherwise>
		</c:choose>
		</td>
	</tr>
	<tr>
		<td><spring:message code="label.user_is_token_active" /></td>
		<td id="token-active-td">${tokenActive}</td>
	</tr>
</table>
<input id="generate-token-button" type="button" value="<spring:message code="label.user_generate_token"/>"/>


