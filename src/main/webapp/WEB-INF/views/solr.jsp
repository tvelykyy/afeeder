<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script type="text/javascript">
$(document).ready(function () {
	$('#replicate-button').click(function (){
		$.ajax({  
			type: "POST",  
		  	url: "<c:url value="/solr/replicate"/>",
		  	success: function(response) {
		  		$('#result-td').html(response.result);
		    },
		    error: function(response) {
		    	$('#result-td').html(response.result);
		    }
		}); 
	});
});
</script>

<h1><spring:message code="label.solr_replicate_extended" /></h1>
<table>
	<tr>
		<td>
			<input id="replicate-button" type="button" value="<spring:message code="label.solr_replicate"/>"/>
		</td>
		<td id="result-td"></td>
	</tr>
</table>



