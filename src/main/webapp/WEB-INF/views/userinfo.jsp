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
		  		$('#token-td').html(response.result);
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
		<c:choose>
			<c:when test="${!empty user.token}">
				<td id="token-td">${user.token}</td>
			</c:when>
			<c:otherwise>
	  			<td><spring:message code="label.user_no_token"/></htd>
			</c:otherwise>
		</c:choose>
	</tr>
	<tr>
		<td><spring:message code="label.user_last_token_usage" /></td>
		<c:choose>
			<c:when test="${!empty user.token}">
				<td>${user.lastTokenUsage}</td>
			</c:when>
			<c:otherwise>
	  			<td><spring:message code="label.user_no_token_last_usage"/></htd>
			</c:otherwise>
		</c:choose>	
	</tr>
	<tr>
		<td><spring:message code="label.user_is_token_active" /></td>
		<td>${tokenActive}</td>
	</tr>
	<tr>
		<td colspan="2">
			<input id="generate-token-button" type="button" 
				value="<spring:message code="label.user_generate_token"/>"/>
		</td>
	</tr>
</table>


