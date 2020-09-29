<%@ include file="./init.jsp"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<liferay-ui:success key="added" message="Site Created" />
		
<html>
<head>
<meta charset="ISO-8859-1">
<title>Employee Detail</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/css/bootstrap-datepicker.min.css">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/js/bootstrap-datepicker.min.js"></script>
</head>
<c:set var="bean" value="${bean}" />
<portlet:actionURL name="addemployee" var="addemployeeURL">
	<portlet:param name="mvcPath" value="/employee/view.jsp" />
</portlet:actionURL>
<form action="${addemployeeURL}" method="POST" id="form">
<input type="hidden"
		name="<portlet:namespace/>employeeId" 
		value="${bean.employeeId}" />
	<div class="col-12">
		<h1>Employee Details</h1>
		<hr>
		<div class="row">
			<div class="col-6 col-md-5">
				<label class="form-label">Full Name</label> <input type="text"
					name="<portlet:namespace/>fullName" class="form-control" id="name" value="${bean.fullName}">
			</div>

			<div class="col-6 col-md-5">
				<label class="form-label">Gender</label>
				<div class="clearfix">
					<div class="radio">
						<label class="form-label" for="option1" style="margin-left:4%;"> <input 
							id="option1"  name="<portlet:namespace/>gender"  value="Male"  type="radio" checked="checked">Male<i class="custom-radio">&nbsp;</i>	
							<span id="m">${bean.gender}</span>
						</label>
						<br>
					</div>
					
					<div class="radio">
						<label class="form-label" for="option2" style="margin-left:4%;"> <input 
							id="option2" name="<portlet:namespace/>gender" value="Female" type="radio">
							Female<i class="custom-radio">&nbsp;</i>
							<span id="f">${bean.gender}</span>
						</label>
					</div>
				</div>
			</div>

		
			<div class="col-6 col-md-5">
			<fmt:formatDate type="date" var="dateTemp" value="${bean.birthDate}"
				pattern="dd/MM/yyyy" />
	
				<label class="form-label" title="Birth Date">Birth Date</label>
				<div class="input-group date" id="birthdate">
					<input type="text" name="<portlet:namespace/>birthDate" 
						 class="form-control" onchange="autocalculateAge()" id="dateofbirth" value="${dateTemp}">
				</div>
			</div>
			<div class="col-6 col-md-5">
				<label class="form-label" title="Age">Age</label> <input value="${bean.age}"
					class="form-control" name="<portlet:namespace/>age" 
					title="Enter your age" type="text"   id="age">
			</div>

			<div class="col-6 col-md-5">
				<label class="form-label" title="Email address">Email
					Address</label> <input type="text" class="form-control" value="${bean.emailAddress}"
					name="<portlet:namespace/>emailAddress" title="Enter email Address"
					 id="email" >
			</div>

			<div class="col-6 col-md-5">
			<br>
				<div class="dropdown">
					<select id="department" name="<portlet:namespace/>department" value="${bean.department}"
						title="Select department"
						class="btn btn-secondary form-control dropdown-toggle">
						<div class="dropdown-menu">
							<c:choose>
								<c:when test="${empty bean}">
			<option  class="dropdown-item" >Select
								Department</option>
							
							<option class="dropdown-item">IT</option>
							<option class="dropdown-item">Technical</option>
							<option class="dropdown-item">Tester</option>
							<option class="dropdown-item">Designer</option>
							<option class="dropdown-item">UI/UX Developer</option>
							</c:when>
							<c:otherwise>
										<option value="${bean.department}" class="dropdown-item">${bean.department}</option>
										<option class="dropdown-item">IT</option>
							<option class="dropdown-item">Technical</option>
							<option class="dropdown-item">Tester</option>
							<option class="dropdown-item">Designer</option>
							<option class="dropdown-item">UI/UX Developer</option>
									</c:otherwise>
					</c:choose>
						</div>
					</select>

				</div>

			</div>

		</div>
	</div>
	<br>
	<div class="container">
	<div class="row">
		<div class="col-1">
			<input type=button value="Back" class="btn btn-primary"
				onClick="javascript: window.history.go(-1)" title="Back" />
		</div>
		<div class="col-2">
			<input  type="submit" class="btn btn-primary"  name="submit" value="Save Details">
		</div>
	</div>
	</div>
</form>
</html>
<script>
  var d = new Date();
var year = d.getFullYear() - 18;
d.setFullYear(year);

 jQuery(document).ready(function() {
	var gender='${bean.gender}';
	if(gender=="Female"){
		$('#m').empty();
		$('#f').empty();
		 $('#option2').attr("checked", "checked");
		 
	}
	if(gender=="Male"){
		$('#f').empty();
		$('#m').empty();
		 $('#option1').attr("checked", "checked");
		
	}
	
	$('#dateofbirth').datepicker({
	    autoclose: true,
	     changeMonth: true,
	    changeYear: true,
	    maxDate: year,
	    minDate: "-100Y",
        yearRange: '-100:' + year + '',
        defaultDate: d  
 	});	
});

 function autocalculateAge(){

	var now = new Date();
	var dob=$('#dateofbirth').val();
	var birthdate = dob.split("/");
	var born = new Date(birthdate[2], birthdate[1]-1, birthdate[0]);
	age=get_age(born,now);
	//console.log(birthdate[2]+" : "+birthdate[1]+" : "+birthdate[0]);
    //console.log(age);
    if(age!=0 || age!=-1){
	$('#age').val(age);
    }
}


function get_age(born, now) {
    var birthday = new Date(now.getFullYear(), born.getMonth(), born.getDate());
    if (now >= birthday) 
      return now.getFullYear() - born.getFullYear();
    else
      return now.getFullYear() - born.getFullYear() - 1;
  }  
	</script>

	