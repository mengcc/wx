package com.mzs.main.admin;



import javax.servlet.http.HttpSession;

import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.mzs.main.model.Sysuser;

public class LoginController extends Controller {
	
	@Clear
	public void index(){
		render("/WEB-INF/jsp/admin/login.html");
	}
	
	@Before(LoginValidator.class)
	@Clear(AdminInterceptor.class)
	public void login() {
		String username= getPara("sysuser.username");
		String password = getPara("sysuser.password");
		String code = getPara("verifyCode");
		HttpSession session = getSession();
		String verifycode = (String)session.getAttribute("varifycode")==null?"":(String)session.getAttribute("varifycode");
		Sysuser sysuser = Sysuser.dao.findUserByName(username);
		if(sysuser != null && password.equals(sysuser.getPassword())){
			if(verifycode.equals(code)){
				session.removeAttribute("varifycode");;
				session.setAttribute("sysuser", sysuser);
				redirect("/index");
			}else{
				setAttr("error_msg", "请输入正确的验证码!");
				render("/WEB-INF/jsp/admin/login.html");
			}
		}else{
			setAttr("error_msg", "用户名或密码错误!");
			render("/WEB-INF/jsp/admin/login.html");
		}
	}
}
