package com.mzs.main.admin;

import com.jfinal.core.Controller;

public class MenuController extends Controller{

	public void index(){
		render("/WEB-INF/jsp/admin/menu.html");
	}
}
