<%@ include file="./init.jsp" %>
<c:set var="isgroupmember" value="${authoriseduser}" />
<liferay-ui:success key="added" message="Saved Successfully" />
<liferay-ui:success key="deleted" message="Deleted Suceesfully"/>
<liferay-ui:success key="updated" message="Updated Suceesfully"/>
<portlet:renderURL var="addemployeeRenderURL">
	<portlet:param name="mvcPath" value="/employee/EmployeeDetails.jsp" />
</portlet:renderURL>
		<c:if test="${not isgroupmember}">
				<div class="col-reverse  pull-right">	
			<a href="${addemployeeRenderURL}"
				class="btn  btn-primary btn-default "> <i
				class="glyphicon glyphicon-plus"></i> Add Employee
			</a>
		</div>
</c:if>
<div class="search-container-wrapper">
	<liferay-ui:search-container id="employee" delta="5"
		iteratorURL="${iteratorURL}" emptyResultsMessage="No Records"
		total="${employeeSearchContainer.total}"
		deltaConfigurable="<%= true %>">
		<liferay-ui:search-container-results
			results="${employeeSearchContainer.results}" />
		<liferay-ui:search-container-row 
		className="com.employee.bean.EmployeeBean" modelVar="employeebean" keyProperty="employeeId">

			<portlet:renderURL var="updateRenderURL">

				<portlet:param name="mvcPath"
					value="/employee/EmployeeDetails.jsp" />
				<portlet:param name="fullName" value="${employeebean.fullName}" />
				<portlet:param name="gender" value="${employeebean.gender}" />
				<portlet:param name="department" value="${employeebean.department}" />
				<portlet:param name="emailAddress" value="${employeebean.emailAddress}" />
				<portlet:param name="birthDate" value="${employeebean.birthDate}" />
				<portlet:param name="age" value="${employeebean.age}" />
				<portlet:param name="employeeId" value="${employeebean.employeeId}" />
				<portlet:param name="update" value="update" />
				</portlet:renderURL>
				
				
			<portlet:actionURL name="deleteEmployee" var="deleteActionURL">

				<portlet:param name="employeeId" value="${employeebean.employeeId}" />
			</portlet:actionURL>

			
			<liferay-ui:search-container-column-text name="Full Name"
				property="fullName" />
	
			<liferay-ui:search-container-column-text name="Birth Date"
				property="birthDate" />

			<liferay-ui:search-container-column-text name="Age"
				property="age" />

			<liferay-ui:search-container-column-text name="Gender"
				property="gender" />

			<liferay-ui:search-container-column-text name="Department"
				property="department" />
				
			<liferay-ui:search-container-column-text name="Email"
				property="emailAddress" />
			
				<%@ include file="/employee/elapses.jsp"%>
		</liferay-ui:search-container-row>
		<liferay-ui:search-iterator markupView="lexicon"
			searchContainer="${searchContainer}" />
	</liferay-ui:search-container>

</div>
	