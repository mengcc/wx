package com.mzs.main.admin;

import com.jfinal.core.Controller;

public class IndexController extends Controller{
	
	public void index(){
		render("/WEB-INF/jsp/admin/index.html");
	}
}
