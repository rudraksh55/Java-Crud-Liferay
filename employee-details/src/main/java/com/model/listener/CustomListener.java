/**
 * Copyright 2000-present Liferay, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.model.listener;

import com.employee.utils.EmployeUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;

import org.osgi.service.component.annotations.Component;

@Component(
	immediate = true,
	service = ModelListener.class
)
public class CustomListener extends BaseModelListener<LayoutSet> {
	public static final Log log = LogFactoryUtil.getLog(EmployeUtil.class);
	@Override
	public void onBeforeCreate(LayoutSet model) throws ModelListenerException {
		
		
		log.info("Site Created");
		
	}
	@Override
	public void onAfterUpdate(LayoutSet model) throws ModelListenerException {
		log.info("Site Updated");
		super.onAfterUpdate(model);
	}
}