package com.mzs.main.admin;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.mzs.main.model.Sysuser;

public class AdminInterceptor implements Interceptor {

	@Override
	public void intercept(Invocation inv) {
		System.out.println("拦截器开始");
		Controller controller = inv.getController();
		Sysuser sysuser = (Sysuser)controller.getSession().getAttribute("sysuser");
		if(sysuser != null){
			inv.invoke();
		}else{
			controller.render("/WEB-INF/jsp/admin/login.html");
		}
		System.out.println("拦截器结束");
		
	}

}
