package com.mzs.main.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.jfinal.aop.Clear;
import com.jfinal.kit.JsonKit;
import com.jfinal.kit.PropKit;
import com.jfinal.weixin.sdk.api.ApiConfig;
import com.jfinal.weixin.sdk.api.ApiResult;
import com.jfinal.weixin.sdk.api.MediaApi;
import com.jfinal.weixin.sdk.api.MediaApi.MediaType;
import com.jfinal.weixin.sdk.api.MediaArticles;
import com.jfinal.weixin.sdk.api.MenuApi;
import com.jfinal.weixin.sdk.api.UserApi;
import com.jfinal.weixin.sdk.jfinal.ApiController;
import com.mzs.main.admin.AdminInterceptor;
import com.mzs.main.weixin.button.MenuManager;

@Clear(AdminInterceptor.class)
public class WeixinApiController extends ApiController {

	@Override
	public ApiConfig getApiConfig() {
		ApiConfig ac = new ApiConfig();
		// 配置微信 API 相关常量
		ac.setToken(PropKit.get("token"));
//		ac.setAppId(PropKit.get("appId"));
//		ac.setAppSecret(PropKit.get("appSecret"));
		
		ac.setAppId("wx1fc8973668a2b34a");
		ac.setAppSecret("f0e78de3957a5863c0aaaa45c75a0371");
		/**
		 * 是否对消息进行加密，对应于微信平台的消息加解密方式： 1：true进行加密且必须配置 encodingAesKey
		 * 2：false采用明文模式，同时也支持混合模式
		 */
		ac.setEncryptMessage(PropKit.getBoolean("encryptMessage", false));
		ac.setEncodingAesKey(PropKit.get("encodingAesKey", "setting it in config file"));

		// ApiConfigKit.setDevMode(false);
		// ApiConfigKit.setThreadLocalApiConfig(ac);
		return ac;
	}

	/**
	 * 获取公众号关注用户
	 */
	public void getFollowers() {
		ApiResult apiResult = UserApi.getFollows();
		renderText(apiResult.getJson());
	}
	
	/**
	 * 获取用户信息
	 */
	public void getUserInfo(){
		ApiResult apiResult = UserApi.getUserInfo("owIegv8k-XDF5aGQB0nb-6_HMXrY");
		renderText(apiResult.getJson());
	}


	//创建菜单
	public void creatMenuApi() {
		String json = JsonKit.toJson(MenuManager.getMenu()).toString();
		System.out.println(json);
		renderText(MenuApi.createMenu(json).getJson());
	}
	
	//新增永久图片素材
	public void addMediaPic(){
		File file = new File("D://aa.jpg");
		ApiResult apiResult = MediaApi.addMaterial(file, "联想R720", "发烧级游戏本");
		renderText(apiResult.getJson());
	}
	
	//新增永久图文素材
	public void addMediaNew(){
		List<MediaArticles> mediaArticles = new ArrayList<MediaArticles>();
		
		MediaArticles mediaArticle1 = new MediaArticles();
		mediaArticle1.setThumb_media_id("lhoUkuxMu-nnxU3GWkmwqaFXL1K3Q2r_BEIedUTXKVs");
		mediaArticle1.setAuthor("孟志昇");//作者
		mediaArticle1.setContent("图文素材测试");//正文
		mediaArticle1.setDigest("digest  这是什么鬼");
		mediaArticle1.setShow_cover_pic(true);
		mediaArticle1.setContent_source_url("https://www.baidu.com");//阅读原文URL
		mediaArticle1.setTitle("标题");
		mediaArticles.add(mediaArticle1);
		
		MediaArticles mediaArticle2 = new MediaArticles();
		mediaArticle2.setThumb_media_id("lhoUkuxMu-nnxU3GWkmwqaFXL1K3Q2r_BEIedUTXKVs");
		mediaArticle2.setAuthor("孟志昇");//作者
		mediaArticle2.setContent("图文素材测试11");//正文
		mediaArticle2.setDigest("digest  这是什么鬼");
		mediaArticle2.setShow_cover_pic(true);
		mediaArticle2.setContent_source_url("https://www.baidu.com");//阅读原文URL
		mediaArticle2.setTitle("标题11");
		mediaArticles.add(mediaArticle2);
		
		
		ApiResult apiResult = MediaApi.addNews(mediaArticles);
		renderText(apiResult.getJson());
	}
	
	//删除永久素材
	public void delMedia(){
		String[] strs = {"lhoUkuxMu-nnxU3GWkmwqUN57pMq3eaey5I6JrjfeIE","lhoUkuxMu-nnxU3GWkmwqVdwvuUrfr1S-wMqNMVGc9c","lhoUkuxMu-nnxU3GWkmwqWInykYW9KyhlXvmppQlI-4"};
		for (String string : strs) {
			ApiResult apiResult2 = MediaApi.delMaterial(string);
			System.out.println(apiResult2.getJson());
		}
		renderText("删除成功");
	}
	
	
	//获取图片素材列表
	public void getMediaPic(){
		ApiResult apiResult2 = MediaApi.batchGetMaterial(MediaType.IMAGE, 0, 10);
		ApiResult apiResult = MediaApi.getMaterialCount();
		renderText(apiResult.getJson()+"\n\n"+apiResult2.getJson());
	}
	
	//获取图wen素材列表
	public void getMediaNew(){
		ApiResult apiResult2 = MediaApi.batchGetMaterial(MediaType.NEWS, 0, 10);
		ApiResult apiResult = MediaApi.getMaterialCount();
		renderText(apiResult.getJson()+"\n\n"+apiResult2.getJson());
	}

}
