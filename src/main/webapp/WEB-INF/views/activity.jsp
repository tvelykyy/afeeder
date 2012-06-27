<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<script type="text/javascript">
$(document).ready(function () {
	$('#activity-form-btn').click(function (){
		if ($('#activity-form').is(':visible')) {
			$(this).html('<spring:message code="label.activity_show_add_form"/>');
			$('#activity-form').hide('slow');
		} else {
			$(this).html('<spring:message code="label.activity_hide_add_form"/>');
			$('#activity-form').show('slow');
		}
		return false;
	});
	
	$('#submit-activity-form').click(function(){
		$('.errors').html('');
		
		$.ajax({  
			type: "POST",  
		  	url: "activity/add",  
		  	data: $('#activity-form').serialize(),  
		  	success: function(response) {
		  		if(response.status == "SUCCESS"){
		  			alert('Success');
		  		} else {
		  			alert('Error processing your request');
		  		}
		    },
		    error: function(response) {
		    	alert(response);
		    }
		}); 
		
		return false;
	});
});
	
</script>



<form:form id="activity-form" method="post" style="display: none;" commandName="activity" modelAttribute="activity">
	<table>
		<tr><td><div class="errors"> </div></td></tr>
		<tr>
			<td><form:label path="text"><spring:message code="label.activity_text" /></form:label></td>
			<td><form:textarea rows="5" cols="30" path="text"/></td>
			<td><form:errors path="text" cssClass="errors"/></td>
		</tr>
		<tr>
			<td><form:label path="group.id"><spring:message code="label.activity_group" /></form:label></td>
			<td>
				<form:select path="group.id">
					<form:options items="${groups}" itemValue="id" itemLabel="name"/>
				</form:select>
			</td>
		</tr>
		<tr>
			<td><input id="submit-activity-form" type="submit" value="<spring:message code="label.activity_add"/>"></td>
		</tr>
	</table>
</form:form>

<a id="activity-form-btn" href="#"><spring:message code="label.activity_show_add_form"/></a>

<c:choose>
	<c:when test="${!empty activities}">
		<c:forEach items="${activities}" var="activity">
			<div class="activity">
				<h5>${activity.user.name} <spring:message code="label.activity_user_says"/> ${activity.group.name}</h5>
				<div class="activity-text">${activity.text}</div>
			</div>
		</c:forEach>
	</c:when>
	<c:otherwise>
  		<h3><spring:message code="label.activity_no_items"/></h3>
	</c:otherwise>
</c:choose>
