package com.mzs.main.common;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.template.Engine;
import com.jfinal.weixin.sdk.api.ApiConfig;
import com.jfinal.weixin.sdk.api.ApiConfigKit;
import com.mzs.main.admin.AdminInterceptor;
import com.mzs.main.admin.IndexController;
import com.mzs.main.admin.LoginController;
import com.mzs.main.admin.MenuController;
import com.mzs.main.admin.VerifyCode;
import com.mzs.main.controller.WeixinApiController;
import com.mzs.main.controller.WeixinMsgController;
import com.mzs.main.model._MappingKit;


public class JfinalConfig extends JFinalConfig {
	
	private Log log = Log.getLog(JfinalConfig.class);

	/**
	 * 如果生产环境配置文件存在，则优先加载该配置，否则加载开发环境配置文件
	 * 
	 * @param pro
	 *            生产环境配置文件
	 * @param dev
	 *            开发环境配置文件
	 */
	public void loadProp(String pro, String dev) {
		try {
			PropKit.use(pro);
		} catch (Exception e) {
			PropKit.use(dev);
		}
	}

	/**
	 * 
	 * @return
	 */
	public ApiConfig getApiConfig() {
		ApiConfig ac = new ApiConfig();
		// 配置微信 API 相关常量
		//wx1fc8973668a2b34a
		//f0e78de3957a5863c0aaaa45c75a0371
		ac.setToken(PropKit.get("token"));
		ac.setAppId("wx1fc8973668a2b34a");
		ac.setAppSecret("f0e78de3957a5863c0aaaa45c75a0371");
		ac.setEncodingAesKey(PropKit.get("encodingAesKey"));
		ac.setEncryptMessage(PropKit.getBoolean("encryptMessage",false));
		ApiConfigKit.setDevMode(false);
		ApiConfigKit.setThreadLocalApiConfig(ac);
		return ac;
	}

	@Override
	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {
		loadProp("a_little_config_pro.txt", "a_little_config.txt");
		/*
		 * 设置模式： true：开发模式 false：生产模式
		 */
		me.setDevMode(PropKit.getBoolean("devMode", true));
		me.setEncoding("utf-8");
		
//		me.setViewType(ViewType.FREE_MARKER);
//		me.setJsonFactory(new JacksonFactory());
	}

	/**
	 * 路由
	 */
	@Override
	public void configRoute(Routes me) {
		me.add("/login", LoginController.class);
		me.add("/index", IndexController.class,"page");
		me.add("/verifyCode",VerifyCode.class);
		me.add("/msg",WeixinMsgController.class);
		me.add("/menu",MenuController.class);
		me.add("/api",WeixinApiController.class);
	}

	/**
	 * HTML模板引擎
	 */
	@Override
	public void configEngine(Engine me) {
		me.addSharedFunction("WEB-INF/jsp/admin/_layout.html");
		me.addSharedFunction("WEB-INF/jsp/admin/_menuModals.html");
	}

	/**
	 * 插件
	 */
	@Override
	public void configPlugin(Plugins me) {
		// 配置C3p0数据库连接池插件
		DruidPlugin druidPlugin = createDruidPlugin();
		me.add(druidPlugin);
		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
		_MappingKit.mapping(arp);
		arp.setShowSql(true);
		// 所有映射在 MappingKit 中自动化搞定
		// arp.setTransactionLevel("")//事务隔离级别
		// arp.setDialect(dialect)//设置sql方言
		arp.setContainerFactory(new CaseInsensitiveContainerFactory());// 配置字段名大小写不敏感工厂
		me.add(arp);
		
	}

	@Override
	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {
		log.info("配置全局拦截器");
		me.add(new AdminInterceptor());
	}

	@Override
	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers me) {
		log.info("配置处理器");
	}

	/**
	 * 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
	 * 
	 * 使用本方法启动过第一次以后，会在开发工具的 debug、run config 中自动生成
	 * 一条启动配置，可对该自动生成的配置再添加额外的配置项，例如 VM argument 可配置为： -XX:PermSize=64M
	 * -XX:MaxPermSize=256M
	 */
	public static void main(String[] args) {
		/**
		 * 特别注意：Eclipse 之下建议的启动方式
		 */
		JFinal.start("WebContent", 80, "/", 5);

		/**
		 * 特别注意：IDEA 之下建议的启动方式，仅比 eclipse 之下少了最后一个参数
		 */
		// JFinal.start("WebRoot", 80, "/");
	}

	/**
	 * 数据库连接池
	 * 
	 * @return
	 */
	public static DruidPlugin createDruidPlugin() {
		return new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
	}

}