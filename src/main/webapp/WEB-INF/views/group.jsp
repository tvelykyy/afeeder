<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<script type="text/javascript">
function clearForm() {
	//clear form values
	$('#name').val('');
	$('#id').val('');
	
	//clean errors div
	$('.errors').html('');
}
function showForm() {
	clearForm();
	
	$('#saveGroupForm').show('slow');
	
	$('#addBtn').html('<spring:message code="label.group_hide_form"/>');
}

function hideForm() {
	clearForm();
	
	$('#saveGroupForm').hide('slow');
	
	$('#addBtn').html('<spring:message code="label.group_show_form"/>');
}

function editBtnHandler(caller) {
	showForm();

	$('#name').val(caller.closest('tr').find('.name').html());
	$('#id').val(caller.closest('tr').find('.id').html());

	return false;
}

function removeBtnHandler(caller) {
	if (confirm('Are are sure want to delete group?')) {
		//clean errors div
		$('.errors').html('');
		
		var id = caller.closest('tr').attr('id');
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
}
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
		  			hideForm();
		  		} else {
		  			for (tmp in response.result) {
		  				$('.errors').html(response.result[tmp].defaultMessage + '<br>');	
		  			}
		  		}
			};
		} else {
			url = "<c:url value="/group/add"/>";
			
			successFn = function(response) {
				if(response.status == "SUCCESS"){
					var group = response.result;
		  			$('#group-table > tbody:last')
		  			.append($('<tr>')
		  				.attr('id', group.id)
		  		        .append($('<td>')
		  		        	.attr('class', 'id')
		  		        	.append(group.id)
		  		        )
		  		        .append($('<td>')
		  		        	.attr('class', 'name')
		  		        	.append(group.name)
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
		  			$('tr[id=' + group.id + '] .editBtn').click(function() {
		  				return editBtnHandler($(this));
		  			});
		  			$('tr[id=' + group.id + '] .removeBtn').click(function() {
		  				return removeBtnHandler($(this));
		  			});
		  			hideForm();
		  		} else {
		  			for (tmp in response.result) {
		  				$('.errors').html(response.result[tmp].defaultMessage + '<br>');	
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
	$('#addBtn').click(function() {
		if ($('#saveGroupForm').is(':visible')) {
			hideForm();
		} else {
			showForm();
		}
		
		return false;
	});
	
	$('.removeBtn').click(function() {
		return removeBtnHandler($(this));
	});
	
	$('.editBtn').click(function() {
		return editBtnHandler($(this));
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


<a id="addBtn" href="#"><spring:message code="label.group_show_form"/></a>

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
