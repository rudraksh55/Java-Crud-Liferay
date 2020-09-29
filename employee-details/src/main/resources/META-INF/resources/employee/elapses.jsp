<c:set var="isgroupmember" value="${authoriseduser}" />
<c:if test="${not isgroupmember}">
<liferay-ui:search-container-column-text align="right" name="Action">
	<liferay-ui:icon-menu direction="right-side" markupView="lexicon"
		showWhenSingleIcon="<%=true%>">

				<liferay-ui:icon message="Edit" url="${updateRenderURL}" />
				<liferay-ui:icon message="Delete" url="${deleteActionURL}" />
	</liferay-ui:icon-menu>
</liferay-ui:search-container-column-text>
</c:if>