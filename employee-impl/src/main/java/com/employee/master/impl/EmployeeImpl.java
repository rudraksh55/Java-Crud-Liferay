package com.employee.master.impl;

import java.util.List;

import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import com.employee.api.EmployeeApi;
import com.employee.bean.EmployeeBean;
import com.employee.service.service.EmployeeLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

@Component(immediate = true, property = {}, service = EmployeeApi.class)
public class EmployeeImpl implements EmployeeApi {

	public static final Log log = LogFactoryUtil.getLog(EmployeeImpl.class);

	@Reference
	EmployeeLocalService localservice;

	@Override
	public void addemployeedata(EmployeeBean employeeBean) {
		localservice.addEmployee(employeeBean);
	}

	@Override
	public List<EmployeeBean> getAllEmployee() {
		
		  return localservice.getAllEmployee();
	}

	@Override
	public void deleteEmployeeById(long employeeId) {
		
		localservice.deleteEmployeeById(employeeId);
	}

	@Override
	public EmployeeBean employeeById(long employeeId) {
	
		return localservice.employeeById(employeeId);
	}

	@Override
	public boolean getuserrole(long loginUserId) throws PortalException {
		
		return localservice.getuserrole(loginUserId);
		
		
	}


	
}
