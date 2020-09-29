package com.employee.web.portlet.portlet;

import com.employee.api.EmployeeApi;
import com.employee.bean.EmployeeBean;
import com.employee.utils.EmployeUtil;
import com.employee.web.portlet.constants.EmployeeDetailsPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ParamUtil;

import java.io.IOException;
import java.text.ParseException;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.ProcessAction;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author rudraksh.kumawat
 */
@Component(immediate = true, property = { "com.liferay.portlet.display-category=Employee",
		"com.liferay.portlet.header-portlet-css=/css/main.css", "com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=EmployeeDetails", "javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/employee/view.jsp",
		"javax.portlet.name=" + EmployeeDetailsPortletKeys.EMPLOYEEDETAILS,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user" }, service = Portlet.class)
public class EmployeeDetailsPortlet extends MVCPortlet {
	private EmployeeApi employeeApi;
	private EmployeeBean employeeBean;

	@Reference(unbind = "-")
	public void getemployee(EmployeeApi employeeApi) {
		this.employeeApi = employeeApi;

	}

	public static final Log log = LogFactoryUtil.getLog(EmployeeDetailsPortlet.class);

	@ProcessAction(name = "addemployee")
	public void addEmployeeData(ActionRequest actionRequest, ActionResponse actionResponse) throws ParseException, PortalException {
		EmployeUtil employeUtil = new EmployeUtil(employeeApi);
		employeUtil.addEmployeeData(actionRequest, actionResponse);

	}

	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse) 
			throws IOException, PortletException {
		long loginUserId ;
		loginUserId=Long.valueOf(renderRequest.getRemoteUser());
	
		try {
			
			EmployeUtil employeUtil = new EmployeUtil(employeeApi);
			String updateVar = ParamUtil.getString(renderRequest, EmployeeDetailsPortletKeys.EMP_UPDATE);
			if (updateVar.equals(EmployeeDetailsPortletKeys.EMP_UPDATE)) {
				employeeBean = employeUtil.setBean(renderRequest, renderResponse);
				renderRequest.setAttribute("bean", employeeBean);
			}
			else {		
				employeeBean=null;
			}
			boolean isgroupMember= 	employeUtil.getuserrole(renderRequest, renderResponse,loginUserId);
			employeUtil.setRegularListing(renderRequest, renderResponse);
			renderRequest.setAttribute("authoriseduser",isgroupMember );
		
			super.render(renderRequest, renderResponse);
		} catch (PortalException e) {
			
			e.printStackTrace();
		}

	}

	@ProcessAction(name = "deleteEmployee")
	public void deleteEmployee(ActionRequest actionRequest, ActionResponse actionResponse) {
		EmployeUtil employeUtil = new EmployeUtil(employeeApi);
		employeUtil.deleteEmployeeById(actionRequest, actionResponse);

	}

}