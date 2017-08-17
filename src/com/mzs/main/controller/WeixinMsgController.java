package com.mzs.main.controller;

import com.jfinal.aop.Clear;
import com.jfinal.kit.PropKit;
import com.jfinal.weixin.sdk.api.ApiConfig;
import com.jfinal.weixin.sdk.jfinal.MsgController;
import com.jfinal.weixin.sdk.msg.in.InImageMsg;
import com.jfinal.weixin.sdk.msg.in.InLinkMsg;
import com.jfinal.weixin.sdk.msg.in.InLocationMsg;
import com.jfinal.weixin.sdk.msg.in.InNotDefinedMsg;
import com.jfinal.weixin.sdk.msg.in.InShortVideoMsg;
import com.jfinal.weixin.sdk.msg.in.InTextMsg;
import com.jfinal.weixin.sdk.msg.in.InVideoMsg;
import com.jfinal.weixin.sdk.msg.in.InVoiceMsg;
import com.jfinal.weixin.sdk.msg.in.event.InCustomEvent;
import com.jfinal.weixin.sdk.msg.in.event.InFollowEvent;
import com.jfinal.weixin.sdk.msg.in.event.InLocationEvent;
import com.jfinal.weixin.sdk.msg.in.event.InMassEvent;
import com.jfinal.weixin.sdk.msg.in.event.InMenuEvent;
import com.jfinal.weixin.sdk.msg.in.event.InMerChantOrderEvent;
import com.jfinal.weixin.sdk.msg.in.event.InNotDefinedEvent;
import com.jfinal.weixin.sdk.msg.in.event.InPoiCheckNotifyEvent;
import com.jfinal.weixin.sdk.msg.in.event.InQrCodeEvent;
import com.jfinal.weixin.sdk.msg.in.event.InShakearoundUserShakeEvent;
import com.jfinal.weixin.sdk.msg.in.event.InSubmitMemberCardEvent;
import com.jfinal.weixin.sdk.msg.in.event.InTemplateMsgEvent;
import com.jfinal.weixin.sdk.msg.in.event.InUpdateMemberCardEvent;
import com.jfinal.weixin.sdk.msg.in.event.InUserPayFromCardEvent;
import com.jfinal.weixin.sdk.msg.in.event.InUserViewCardEvent;
import com.jfinal.weixin.sdk.msg.in.event.InVerifyFailEvent;
import com.jfinal.weixin.sdk.msg.in.event.InVerifySuccessEvent;
import com.jfinal.weixin.sdk.msg.in.event.InWifiEvent;
import com.jfinal.weixin.sdk.msg.in.speech_recognition.InSpeechRecognitionResults;
import com.jfinal.weixin.sdk.msg.out.OutImageMsg;
import com.jfinal.weixin.sdk.msg.out.OutMusicMsg;
import com.jfinal.weixin.sdk.msg.out.OutNewsMsg;
import com.jfinal.weixin.sdk.msg.out.OutTextMsg;
import com.jfinal.weixin.sdk.msg.out.OutVoiceMsg;
import com.mzs.main.admin.AdminInterceptor;
@Clear(AdminInterceptor.class)
public class WeixinMsgController extends MsgController {

	private static final String helpStr = "\t发送 help 可获得帮助，回复\"孟叔\" 获取福利信息，发送\"视频\" 可获取视频教程，发送 \"舍长\" 可看美女，发送 music 可听音乐 ，发送\"news\"查看行业动态";
	/**
	 * 如果要支持多公众账号，只需要在此返回各个公众号对应的 ApiConfig 对象即可 可以通过在请求 url 中挂参数来动态从数据库中获取
	 * ApiConfig 属性值
	 */
	public ApiConfig getApiConfig() {
		ApiConfig ac = new ApiConfig();	
		// 配置微信 API 相关常量
		ac.setToken(PropKit.get("token"));
		ac.setAppId(PropKit.get("appId"));
		ac.setAppSecret(PropKit.get("appSecret"));
		/**
		 * 是否对消息进行加密，对应于微信平台的消息加解密方式： 1：true进行加密且必须配置 encodingAesKey
		 * 2：false采用明文模式，同时也支持混合模式
		 */
		ac.setEncryptMessage(PropKit.getBoolean("encryptMessage", false));
		ac.setEncodingAesKey(PropKit.get("encodingAesKey", "setting it in config file"));
		return ac;
	}

	/**
	 * 实现父类抽方法，处理文本消息 本例子中根据消息中的不同文本内容分别做出了不同的响应，同时也是为了测试 jfinal weixin
	 * sdk的基本功能： 本方法仅测试了 OutTextMsg、OutNewsMsg、OutMusicMsg 三种类型的OutMsg，
	 * 其它类型的消息会在随后的方法中进行测试
	 */
	protected void processInTextMsg(InTextMsg inTextMsg) {
		String msgContent = inTextMsg.getContent().trim();
		// 帮助提示
		if ("help".equalsIgnoreCase(msgContent) || "帮助".equals(msgContent)) {
			OutTextMsg outMsg = new OutTextMsg(inTextMsg);
			outMsg.setContent(helpStr);
			render(outMsg);
		}
		// 图文消息测试
		else if ("news".equalsIgnoreCase(msgContent) || "新闻".equalsIgnoreCase(msgContent)) {
			OutNewsMsg outMsg = new OutNewsMsg(inTextMsg);
			outMsg.addNews("孟叔的微信公众号--新闻测试", "先做几个小小的测试",
					"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1501837943835&di=cf8ebd44bf57137e104c78f2cec36cf0&imgtype=0&src=http%3A%2F%2Ffiles.15w.com%2Flol%2F2014%2F0411%2F1397145600455.jpg",
					"http://weibo.com/528778911?source=blog&sudaref=blog.sina.com.cn&retcode=6102&is_all=1");
			outMsg.addNews("孟叔的微信公众号", "先做几个小小的测试",
					"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1501837943835&di=cf8ebd44bf57137e104c78f2cec36cf0&imgtype=0&src=http%3A%2F%2Ffiles.15w.com%2Flol%2F2014%2F0411%2F1397145600455.jpg",
					"http://weibo.com/528778911?source=blog&sudaref=blog.sina.com.cn&retcode=6102&is_all=1");
			outMsg.addNews("孟叔的微信公众号", "先做几个小小的测试",
					"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1501837943835&di=cf8ebd44bf57137e104c78f2cec36cf0&imgtype=0&src=http%3A%2F%2Ffiles.15w.com%2Flol%2F2014%2F0411%2F1397145600455.jpg",
					"http://weibo.com/528778911?source=blog&sudaref=blog.sina.com.cn&retcode=6102&is_all=1");
			outMsg.addNews("孟叔的微信公众号", "先做几个小小的测试",
					"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1501837943835&di=cf8ebd44bf57137e104c78f2cec36cf0&imgtype=0&src=http%3A%2F%2Ffiles.15w.com%2Flol%2F2014%2F0411%2F1397145600455.jpg",
					"http://weibo.com/528778911?source=blog&sudaref=blog.sina.com.cn&retcode=6102&is_all=1");
			render(outMsg);
		}
		// 音乐消息测试
		else if ("music".equalsIgnoreCase(msgContent) || "音乐".equals(msgContent)) {
			OutMusicMsg outMsg = new OutMusicMsg(inTextMsg);
			outMsg.setTitle("宽恕");
			outMsg.setDescription("建议在 WIFI 环境下流畅欣赏此音乐");
			outMsg.setMusicUrl("http://m10.music.126.net/20170804151148/e58cbb6802102b97303868c641e3e3df/ymusic/9a73/a02d/c580/d5764e2cf3a4da408939338fd3fd2476.mp3");
			outMsg.setHqMusicUrl("http://m10.music.126.net/20170804151148/e58cbb6802102b97303868c641e3e3df/ymusic/9a73/a02d/c580/d5764e2cf3a4da408939338fd3fd2476.mp3");
			outMsg.setFuncFlag(true);
			render(outMsg);
		} else if ("舍长".equalsIgnoreCase(msgContent)) {
			OutNewsMsg outMsg = new OutNewsMsg(inTextMsg);
			outMsg.addNews("fulifuli", "年度终极福利",
					"http://image.tianjimedia.com/uploadImages/2015/159/42/3JFPCDDY03A1.jpg",
					"https://baike.baidu.com/item/%E8%91%AB%E8%8A%A6%E5%A8%83/33960?fr=aladdin");

			render(outMsg);
		}else if("孟叔".equals(msgContent)){
			OutNewsMsg outMsg = new OutNewsMsg(inTextMsg);
			outMsg.addNews("下载歌曲的方法", "当你没钱充会员的时候", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1501840302506&di=135da15774e40429290eabb9638fd2a8&imgtype=0&src=http%3A%2F%2Fec8.images-amazon.com%2Fimages%2FI%2F61sVlwmtI8L._SL500_AA300_.png", "http://79e01ea2.ngrok.io/image");
			render(outMsg);
		}
		// 其它文本消息直接返回原值 + 帮助提示
		else {
			renderOutTextMsg("\t文本消息已成功接收，内容为： " + inTextMsg.getContent() + "\n\n" + helpStr);
		}
	}

	/**
	 * 实现父类抽方法，处理图片消息
	 */
	protected void processInImageMsg(InImageMsg inImageMsg) {
		OutImageMsg outMsg = new OutImageMsg(inImageMsg);
		// 将刚发过来的图片再发回去
		outMsg.setMediaId(inImageMsg.getMediaId());
		render(outMsg);
	}

	/**
	 * 实现父类抽方法，处理语音消息
	 */
	protected void processInVoiceMsg(InVoiceMsg inVoiceMsg) {
		OutVoiceMsg outMsg = new OutVoiceMsg(inVoiceMsg);
		// 将刚发过来的语音再发回去
		outMsg.setMediaId(inVoiceMsg.getMediaId());
		render(outMsg);
	}

	/**
	 * 实现父类抽方法，处理视频消息
	 */
	protected void processInVideoMsg(InVideoMsg inVideoMsg) {
		/*
		 * 腾讯 api 有 bug，无法回复视频消息，暂时回复文本消息代码测试 OutVideoMsg outMsg = new
		 * OutVideoMsg(inVideoMsg); outMsg.setTitle("OutVideoMsg 发送");
		 * outMsg.setDescription("刚刚发来的视频再发回去"); // 将刚发过来的视频再发回去，经测试证明是腾讯官方的 api
		 * 有 bug，待 api bug 却除后再试 outMsg.setMediaId(inVideoMsg.getMediaId());
		 * render(outMsg);
		 */
		OutTextMsg outMsg = new OutTextMsg(inVideoMsg);
		outMsg.setContent("\t视频消息已成功接收，该视频的 mediaId 为: " + inVideoMsg.getMediaId());
		render(outMsg);
	}

	@Override
	protected void processInShortVideoMsg(InShortVideoMsg inShortVideoMsg) {
		OutTextMsg outMsg = new OutTextMsg(inShortVideoMsg);
		outMsg.setContent("\t视频消息已成功接收，该视频的 mediaId 为: " + inShortVideoMsg.getMediaId());
		render(outMsg);
	}

	/**
	 * 实现父类抽方法，处理地址位置消息
	 */
	protected void processInLocationMsg(InLocationMsg inLocationMsg) {
		OutTextMsg outMsg = new OutTextMsg(inLocationMsg);
		outMsg.setContent("已收到地理位置消息:" + "\nlocation_X = " + inLocationMsg.getLocation_X() + "\nlocation_Y = "
				+ inLocationMsg.getLocation_Y() + "\nscale = " + inLocationMsg.getScale() + "\nlabel = "
				+ inLocationMsg.getLabel());
		render(outMsg);
	}

	/**
	 * 实现父类抽方法，处理链接消息 特别注意：测试时需要发送我的收藏中的曾经收藏过的图文消息，直接发送链接地址会当做文本消息来发送
	 */
	protected void processInLinkMsg(InLinkMsg inLinkMsg) {
		OutNewsMsg outMsg = new OutNewsMsg(inLinkMsg);
		outMsg.addNews("链接消息已成功接收", "链接使用图文消息的方式发回给你，还可以使用文本方式发回。点击图文消息可跳转到链接地址页面，是不是很好玩 :)",
				"http://mmbiz.qpic.cn/mmbiz/zz3Q6WSrzq1ibBkhSA1BibMuMxLuHIvUfiaGsK7CC4kIzeh178IYSHbYQ5eg9tVxgEcbegAu22Qhwgl5IhZFWWXUw/0",
				inLinkMsg.getUrl());
		render(outMsg);
	}

	@Override
	protected void processInCustomEvent(InCustomEvent inCustomEvent) {
		System.out.println("processInCustomEvent() 方法测试成功");
	}

	/**
	 * 实现父类抽方法，处理关注/取消关注消息
	 */
	protected void processInFollowEvent(InFollowEvent inFollowEvent) {
		OutTextMsg outMsg = new OutTextMsg(inFollowEvent);
		outMsg.setContent("感谢关注  来自mzs的微信接口测试 :) \n\n\n " + helpStr);
		// 如果为取消关注事件，将无法接收到传回的信息
		render(outMsg);
	}

	/**
	 * 实现父类抽方法，处理扫描带参数二维码事件
	 */
	protected void processInQrCodeEvent(InQrCodeEvent inQrCodeEvent) {
		OutTextMsg outMsg = new OutTextMsg(inQrCodeEvent);
		outMsg.setContent("processInQrCodeEvent() 方法测试成功");
		render(outMsg);
	}

	/**
	 * 实现父类抽方法，处理上报地理位置事件
	 */
	protected void processInLocationEvent(InLocationEvent inLocationEvent) {
		OutTextMsg outMsg = new OutTextMsg(inLocationEvent);
		outMsg.setContent("processInLocationEvent() 方法测试成功");
		render(outMsg);
	}

	@Override
	protected void processInMassEvent(InMassEvent inMassEvent) {
		System.out.println("processInMassEvent() 方法测试成功");
	}

	/**
	 * 实现父类抽方法，处理自定义菜单事件,
	 * 
	 */
	protected void processInMenuEvent(InMenuEvent inMenuEvent) {
		
		
		OutNewsMsg outMsg = new OutNewsMsg(inMenuEvent);
		outMsg.addNews("孟叔的微信公众号--菜单扫码返回信息", "先做几个小小的测试",
				"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1501837943835&di=cf8ebd44bf57137e104c78f2cec36cf0&imgtype=0&src=http%3A%2F%2Ffiles.15w.com%2Flol%2F2014%2F0411%2F1397145600455.jpg",
				"http://weibo.com/528778911?source=blog&sudaref=blog.sina.com.cn&retcode=6102&is_all=1");
		outMsg.addNews("孟叔的微信公众号", "先做几个小小的测试",
				"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1501837943835&di=cf8ebd44bf57137e104c78f2cec36cf0&imgtype=0&src=http%3A%2F%2Ffiles.15w.com%2Flol%2F2014%2F0411%2F1397145600455.jpg",
				"http://weibo.com/528778911?source=blog&sudaref=blog.sina.com.cn&retcode=6102&is_all=1");
		outMsg.addNews("孟叔的微信公众号", "先做几个小小的测试",
				"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1501837943835&di=cf8ebd44bf57137e104c78f2cec36cf0&imgtype=0&src=http%3A%2F%2Ffiles.15w.com%2Flol%2F2014%2F0411%2F1397145600455.jpg",
				"http://weibo.com/528778911?source=blog&sudaref=blog.sina.com.cn&retcode=6102&is_all=1");
		outMsg.addNews("孟叔的微信公众号", "先做几个小小的测试",
				"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1501837943835&di=cf8ebd44bf57137e104c78f2cec36cf0&imgtype=0&src=http%3A%2F%2Ffiles.15w.com%2Flol%2F2014%2F0411%2F1397145600455.jpg",
				"http://weibo.com/528778911?source=blog&sudaref=blog.sina.com.cn&retcode=6102&is_all=1");
		
		render(outMsg);   
//		renderOutTextMsg("扫码成功");
	}

	/**
	 * 实现父类抽方法，处理接收语音识别结果
	 */
	protected void processInSpeechRecognitionResults(InSpeechRecognitionResults inSpeechRecognitionResults) {
		renderOutTextMsg("语音识别结果： " + inSpeechRecognitionResults.getRecognition());
	}

	// 处理接收到的模板消息是否送达成功通知事件
	protected void processInTemplateMsgEvent(InTemplateMsgEvent inTemplateMsgEvent) {
		String status = inTemplateMsgEvent.getStatus();
		renderOutTextMsg("模板消息是否接收成功：" + status);
	}



	@Override
	protected void processInPoiCheckNotifyEvent(InPoiCheckNotifyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void processInShakearoundUserShakeEvent(InShakearoundUserShakeEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void processInVerifyFailEvent(InVerifyFailEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void processInVerifySuccessEvent(InVerifySuccessEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void processInWifiEvent(InWifiEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void processIsNotDefinedEvent(InNotDefinedEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void processIsNotDefinedMsg(InNotDefinedMsg arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void processInMerChantOrderEvent(InMerChantOrderEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void processInSubmitMemberCardEvent(InSubmitMemberCardEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void processInUpdateMemberCardEvent(InUpdateMemberCardEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void processInUserPayFromCardEvent(InUserPayFromCardEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void processInUserViewCardEvent(InUserViewCardEvent arg0) {
		// TODO Auto-generated method stub
		
	}



}
