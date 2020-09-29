package com.employee.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.employee.api.EmployeeApi;
import com.employee.bean.EmployeeBean;
import com.employee.web.portlet.constants.EmployeeDetailsPortletKeys;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

public class EmployeUtil {
	public static final Log log = LogFactoryUtil.getLog(EmployeUtil.class);
	EmployeeApi employeeApi;

	public EmployeUtil(EmployeeApi employeeApi) {
		this.employeeApi = employeeApi;
	}

	public void addEmployeeData(ActionRequest actionRequest, ActionResponse actionResponse) throws PortalException {
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		String tempDate = ParamUtil.getString(actionRequest, EmployeeDetailsPortletKeys.EMP_BIRTH_DATE);
		DateFormat dateFormat = new SimpleDateFormat(EmployeeDetailsPortletKeys.DATE_FORMATE);
		Date birtDate = null;
		User user = themeDisplay.getUser();
		try {
			birtDate = dateFormat.parse(tempDate);
		} catch (ParseException e1) {
			log.error(e1.getMessage(),e1);
		}
		
		long employeeId = ParamUtil.getLong(actionRequest, EmployeeDetailsPortletKeys.EMPLOYEE_ID,
				GetterUtil.DEFAULT_LONG);
		try {
			EmployeeBean employeebean = new EmployeeBean();
			
			Date now = new Date();
			if (Validator.isNotNull(employeeId)) {
				employeebean.setEmployeeId(employeeId);
				employeebean.setCreatedDate(now);
				employeebean.setGroupId(themeDisplay.getCompanyGroupId());
				employeebean.setCompanyId(themeDisplay.getCompanyId());
				employeebean.setUserId(user.getUserId());
				
				employeebean.setUserName(user.getEmailAddress());
				SessionMessages.add(actionRequest, "updated");

			} else {
				
				employeebean.setGroupId(themeDisplay.getCompanyGroupId());
				employeebean.setCreatedDate(now);
				employeebean.setCompanyId(themeDisplay.getCompanyId());
				SessionMessages.add(actionRequest, "added");
			
			}
			employeebean.setModifiedDate(now);
			employeebean.setFullName(ParamUtil.getString(actionRequest, EmployeeDetailsPortletKeys.EMP_FULL_NAME));
			employeebean.setGender(ParamUtil.getString(actionRequest, EmployeeDetailsPortletKeys.EMP_GENDER));
			employeebean.setDepartment(ParamUtil.getString(actionRequest, EmployeeDetailsPortletKeys.EMP_DEPARTMENT));
			employeebean.setEmailAddress(ParamUtil.getString(actionRequest, EmployeeDetailsPortletKeys.EMP_EMAIL));
			employeebean.setBirthDate(birtDate);
			employeebean.setAge(ParamUtil.getString(actionRequest, EmployeeDetailsPortletKeys.EMP_AGE));
			employeeApi.addemployeedata(employeebean);

			/*List<UserGroupRole> userGroupRoleList = UserGroupRoleLocalServiceUtil.getUserGroupRoles(user.getUserId(),
					user.getGroupId());
			if (userGroupRoleList != null) {
				for (UserGroupRole userGroupRole : userGroupRoleList) {
					 Get Role object based on userGroupRole.getRoleId() 
					Role role = RoleLocalServiceUtil.getRole(userGroupRole.getRoleId());
					log.info("roleId : " + role.getRoleId());
					log.info("roleName : " + role.getName());
				}
			}*/

		}catch(

	Exception e)
	{

		log.error(e.getMessage(), e);
	}

	}

	public EmployeeBean setBean(RenderRequest renderRequest, RenderResponse renderResponse) throws  PortalException{
		
		
		long employeeId = ParamUtil.getLong(renderRequest, EmployeeDetailsPortletKeys.EMPLOYEE_ID,
				GetterUtil.DEFAULT_LONG);
	
		return employeeApi.employeeById(employeeId);
	}

	public void deleteEmployeeById(ActionRequest actionRequest, ActionResponse actionResponse) {
		long employeeId = ParamUtil.getLong(actionRequest, EmployeeDetailsPortletKeys.EMPLOYEE_ID,
				GetterUtil.DEFAULT_LONG);
		employeeApi.deleteEmployeeById(employeeId);
		SessionMessages.add(actionRequest, "deleted");

	}

	public void setRegularListing(RenderRequest renderRequest, RenderResponse renderResponse) {
		PortletURL iteratorURL = PortletURLUtil.getCurrent(renderRequest, renderResponse);

		SearchContainer<EmployeeBean> searchContainer = new SearchContainer<>(renderRequest, iteratorURL, null,
				StringPool.BLANK);

		List<EmployeeBean> employeeBeanList = employeeApi.getAllEmployee();
		searchContainer.setSearch(true);
		searchContainer
				.setResults(ListUtil.subList(employeeBeanList, searchContainer.getStart(), searchContainer.getEnd()));
		searchContainer.setTotal(employeeBeanList.size());
		searchContainer.setDeltaConfigurable(true);
		renderRequest.setAttribute(EmployeeDetailsPortletKeys.EMPLOYEE_PAGINATION, searchContainer);

	}

	public boolean getuserrole( RenderRequest renderRequest, RenderResponse renderResponse,long logiusererid) throws PortalException {
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		long userId;
		boolean isFound = false;
		boolean isgroupmember = false;
		userId = Long.valueOf(renderRequest.getRemoteUser());
		List<UserGroup> loginUserGroup = UserGroupLocalServiceUtil.getUserUserGroups(userId);
		for (UserGroup userGroup : loginUserGroup) {
			isFound = userGroup.getName().indexOf(userGroup.getName()) != -1?true:false;
			isgroupmember =  userGroup.getName().indexOf("Technical") != -1?true:false;
			if(isFound && isgroupmember) {
				break;
				
			}
			
		}
		return isgroupmember;
		 
	}
}