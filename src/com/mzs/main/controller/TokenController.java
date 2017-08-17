package com.mzs.main.controller;

import java.io.IOException;

import com.jfinal.core.Controller;
import com.mzs.main.utils.SignUtil;


public class TokenController extends Controller  {
	
	
	/**
	 * 用于公众号配置验证
	 * @throws IOException
	 */
	public void token() throws IOException{
		String signature = getPara("signature");
        String timestamp = getPara("timestamp");
        String nonce = getPara("nonce");
        String echostr = getPara("echostr");
        if (SignUtil.checkSignature(signature, timestamp, nonce)) {
            renderText(echostr);
        }
	}
}
