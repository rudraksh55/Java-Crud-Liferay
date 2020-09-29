package com.employee.api;

import java.util.List;

import javax.portlet.PortletException;

import com.employee.bean.EmployeeBean;
import com.liferay.portal.kernel.exception.PortalException;

public interface EmployeeApi {
	public void addemployeedata(EmployeeBean employeebean);
	
	public List<EmployeeBean> getAllEmployee();
	
	public boolean getuserrole(long loginUserId) throws PortalException;
	
	public void deleteEmployeeById(long employeeId);
	
	public EmployeeBean employeeById(long employeeId);
	
	
}
