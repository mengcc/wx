package com.mzs.main.admin;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import com.mzs.main.model.Sysuser;

public class LoginValidator extends Validator {

	@Override
	protected void validate(Controller c) {
		validateRequiredString("sysuser.username", "error_msg", "请输入用户名!");
		validateRequiredString("sysuser.password", "error_msg", "请输入密码!");
	}

	@Override
	protected void handleError(Controller c) {
		// TODO Auto-generated method stub
		controller.keepModel(Sysuser.class);
		controller.render("/WEB-INF/jsp/admin/login.html");
	}

}
