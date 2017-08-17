/**
 * Copyright (c) 2011-2015, Javen  (javenlife@126.com).
 *
 * Licensed under the Apache License, Version 1.0 (the "License");
 */
package com.mzs.main.weixin.button;

/**
 * @author Javen 2015年12月19日
 */
public enum MenuType {
	click, 		//推送消息和scancode_waitmsg的回调方法是一个，均为MsgCoontroller中的processInMenuEvent()
	view, 	//跳转URL
	scancode_waitmsg, 	//扫码发送信息
	scancode_push, 	//扫码跳转
	pic_sysphoto, 	//系统拍照发图
	pic_photo_or_album, //拍照或者手机相册发图
	pic_weixin, 	//从微信相册发图
	location_select, 	//发生定位
	media_id, 		//永久素材类，永久素材类型可以是图片、音频、视频、图文消息
	view_limited	//点击直接阅读原文
}
