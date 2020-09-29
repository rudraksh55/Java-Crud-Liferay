/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.employee.service.service.impl;

import com.employee.bean.EmployeeBean;
import com.employee.service.model.Employee;
import com.employee.service.service.EmployeeLocalServiceUtil;
import com.employee.service.service.base.EmployeeLocalServiceBaseImpl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.UserGroupLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


import org.osgi.service.component.annotations.Component;

/**
 * The implementation of the employee local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are
 * added, rerun ServiceBuilder to copy their definitions into the
 * <code>com.employee.service.service.EmployeeLocalService</code> interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security
 * checks based on the propagated JAAS credentials because this service can only
 * be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see EmployeeLocalServiceBaseImpl
 */
@Component(property = "model.class.name=com.employee.service.model.Employee", service = AopService.class)
public class EmployeeLocalServiceImpl extends EmployeeLocalServiceBaseImpl {
	public static final Log log = LogFactoryUtil.getLog(EmployeeLocalServiceImpl.class);
	

	public void addEmployee(EmployeeBean employeeBean) {
	
		try {
			Employee employee;
			if (Validator.isNull(employeeBean.getEmployeeId())) {
				
				employee = employeeLocalService.createEmployee(counterLocalService.increment(Employee.class.getName()));
				employee.setCreateDate(employeeBean.getCreatedDate());
				employee.setGroupId(employeeBean.getCompanyId());
				employee.setCompanyId(employeeBean.getCompanyId());
				employee.setUserId(employeeBean.getUserId());
				employee.setUserName(employeeBean.getUserName());
				

			} else {
				employee = employeeLocalService.getEmployee(employeeBean.getEmployeeId());
				
				employee.setGroupId(employee.getCompanyId());
				employee.setCreateDate(employee.getCreateDate());
				employee.setCompanyId(employee.getCompanyId());
			}
			employee.setModifiedDate(employeeBean.getModifiedDate());
			employee.setFullName(employeeBean.getFullName());
			employee.setGender(employeeBean.getGender());
			employee.setDepartment(employeeBean.getDepartment());
			employee.setAge(employeeBean.getAge());
			employee.setBirthDate(employeeBean.getBirthDate());
			employee.setEmail(employeeBean.getEmailAddress());
			
			employeeLocalService.updateEmployee(employee);
			
		}catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}

	}
	
		
	public EmployeeBean employeeById(long employeeId) {

		EmployeeBean employeeBean = new EmployeeBean();
		try {

			Employee tempemployee = employeeLocalService.getEmployee(employeeId);
			employeeBean.setEmployeeId(tempemployee.getEmployeeId());
			employeeBean.setFullName(tempemployee.getFullName());
			employeeBean.setBirthDate(tempemployee.getBirthDate());
			employeeBean.setAge(tempemployee.getAge());
			employeeBean.setDepartment(tempemployee.getDepartment());
			employeeBean.setGender(tempemployee.getGender());
			employeeBean.setEmailAddress(tempemployee.getEmail());
			employeeBean.setCreatedDate(tempemployee.getCreateDate());
			employeeBean.setModifiedDate(tempemployee.getModifiedDate());
			employeeBean.setUserId(tempemployee.getUserId());
			employeeBean.setUserName(tempemployee.getUserName());
			employeeBean.setGroupId(tempemployee.getCompanyId());
			employeeBean.setCompanyId(tempemployee.getCompanyId());
			
		} catch (Exception e) {
			log.error("Exception in employeerByID method" + e);
		}

		return employeeBean;
	}

	public List<EmployeeBean> getAllEmployee() {
		List<EmployeeBean> employeeList = new ArrayList<>();
		try {
			List<Employee> dbemployeeList = employeeLocalService.getEmployees(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
			for (Employee tempemployee : dbemployeeList) {
				
				EmployeeBean employeeBean = employeeById(tempemployee.getEmployeeId());
				employeeList.add(employeeBean);
			}
		} catch (Exception e) {

			log.error(e.getMessage(), e);
		}
			employeeList.sort((EmployeeBean o1,EmployeeBean o2)->o1.getFullName().compareTo(o2.getFullName()));
		return employeeList;

	}
	public boolean getuserrole(long loginUserId) throws PortalException {
		boolean isFound = false;
		boolean isgroupmember = false;
		List<UserGroup> loginUserGroup = UserGroupLocalServiceUtil.getUserUserGroups(loginUserId);
		for (UserGroup userGroup : loginUserGroup) {
			isFound = userGroup.getName().indexOf(userGroup.getName()) != -1?true:false;
			isgroupmember =  userGroup.getName().indexOf("Technical") != -1?true:false;
			if(isFound && isgroupmember) {
				break;
				
			}
			
		}
		
		return isgroupmember;
		 
	}


	public void deleteEmployeeById(long employeeId) {
		try {
			employeeLocalService.deleteEmployee(employeeId);
		} catch (PortalException e) {
			log.error(e.getMessage(), e);
		}
	}

}