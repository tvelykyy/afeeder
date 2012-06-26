<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<script type="text/javascript">
$(document).ready(function () {
	$("#saveBtn").click(function(){
		//clean errors div
		$('.errors').html('');
		var dataString = 'name='+ $('#name').val();
		var url, successFn;
		
		if ($('#id').val()) {
			url = "<c:url value="/group/edit"/>";
			dataString += '&id=' + $('#id').val();
			successFn = function(response) {
				if(response.status == "SUCCESS"){
					$('tr[id="' + response.result.id + '"]').find('.name').html(response.result.name);
		  			$('#saveGroupForm').hide('slow');
		  		} else {
		  			for (tmp in response.result) {
		  				$('.errors').html(response.result[tmp].code + '<br>');	
		  			}
		  		}
			};
		} else {
			url = "<c:url value="/group/add"/>";
			
			successFn = function(response) {
				if(response.status == "SUCCESS"){
		  			$('#group-table > tbody:last')
		  			.append($('<tr>')
		  		        .append($('<td>')
		  		        	.append(response.result.id)
		  		        )
		  		        .append($('<td>')
		  		        	.append(response.result.name)
		  		        )
		  		        .append($('<td>')
		  		        	.append($('<a>')
		  		        		.attr('class', 'editBtn')
		  		        		.attr('href', '#')
		  		        		.text('<spring:message code="label.edit"/>')
		  		        	)
		  		        )
		  		        .append($('<td>')
	  		        		.append($('<a>')
		  		        		.attr('class', 'removeBtn')
		  		        		.attr('href', '#')
		  		        		.text('<spring:message code="label.remove"/>')
		  		        	)
		  		        )
		  		    );
		  			$('#saveGroupForm').hide('slow');
		  		} else {
		  			for (tmp in response.result) {
		  				$('.errors').html(response.result[tmp].code + '<br>');	
		  			}
		  		}
			};
		}
		
		
		$.ajax({  
			type: "POST",  
		  	url: url,  
		  	data: dataString, 
		  	success: successFn,
		  	
		    error: function(response) {
		    	alert(response);
		    }
		});  
		return false;
	});
	$('.addBtn').click(function() {
		//clear form values
		$('#name').val('');
		$('#id').val('');
		
		//hide form
		$('#saveGroupForm').show('slow');
		
		//clean errors div
		$('.errors').html('');
		
		return false;
	});
	
	$('.removeBtn').click(function() {
		if (confirm('Are are sure want to delete group?')) {
			//clean errors div
			$('.errors').html('');
			
			var id = $(this).closest('tr').attr('id');
			var dataString = 'id='+ id;
			
			$.ajax({  
				type: "POST",  
			  	url: "<c:url value="/group/remove"/>",  
			  	data: dataString,  
			  	success: function(response) {
			  		if(response.status == "SUCCESS"){
			  			//Removing table row
			  			$('tr[id="' + id + '"]').remove();
			  		} else {
			  			alert('Error processing your request');
			  		}
			    },
			    error: function(response) {
			    	alert(response);
			    }
			});  
		}
		return false;
	});
	
	$('.editBtn').click(function() {
		//clear form values
		$('#name').val($(this).closest('tr').find('.name').html());
		$('#id').val($(this).closest('tr').find('.id').html());
		
		//hide form
		$('#saveGroupForm').show('slow');
		
		//clean errors div
		$('.errors').html('');
		
		return false;
	});
});
	
</script>

<div class="errors"> </div>

<form id="saveGroupForm" style="display: none;">
	<table class="list-table">
		<tbody>
			<tr>
				<th>
					<spring:message code="label.group_save" />
				</th>
			</tr>
			<tr>
				<td>
					<label for="name"><spring:message code="label.group_name" /></label>:&nbsp;
					<input id="name" name="name" type="text"></input>
				</td>
			</tr>
			<tr>
				<td>
					<input id="id" name="id" type="hidden"></input>
				</td>
			</tr>
			<tr>
				<td>
					<input id="saveBtn" name="saveBtn" type="submit" value="<spring:message code="label.save" />"/>
				</td>
			</tr>
		</tbody>
	</table>
</form>


<a class="addBtn" href="#"><spring:message code="label.group_add"/></a>

<c:choose>
	<c:when test="${!empty groups}">
		<table class="list-table" id="group-table">
			<tr>
				<th><spring:message code="label.group_id" /></th>
				<th><spring:message code="label.group_name" /></th>
				<th>&nbsp;</th>
				<th>&nbsp;</th>
			</tr>
			<c:forEach items="${groups}" var="group">
				<tr id="${group.id}">
					<td class='id'>${group.id}</td>
					<td class='name'>${group.name}</td>
					<td><a class="editBtn" href="#"><spring:message code="label.edit"/></a></td>
					<td><a class="removeBtn" href="#"><spring:message code="label.remove"/></a></td>
				</tr>
			</c:forEach>
		</table>
	</c:when>
	<c:otherwise>
  		<h3><spring:message code="label.group_no_items"/></h3>
	</c:otherwise>
</c:choose>
