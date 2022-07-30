package com.mofa.shiro.config;

import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean//负责身份验证和授权
    public Realm realm(){
        SimpleAccountRealm realm = new SimpleAccountRealm();
        realm.addAccount("admin","admin","ADMIN");
        realm.addAccount("normal","normal","NORMAL");
        return realm;
    }

    @Bean
    public DefaultWebSecurityManager securityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //设置其使用的Realm
        securityManager.setRealm(this.realm());
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(){
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        filterFactoryBean.setSecurityManager(this.securityManager());

        filterFactoryBean.setLoginUrl("/login");//登录url
        filterFactoryBean.setSuccessUrl("/login_success");//登录成功url
        filterFactoryBean.setUnauthorizedUrl("/unauthorized");//无权限url
        filterFactoryBean.setFilterChainDefinitionMap(this.filterChainDefinitionMap());

        return filterFactoryBean;
    }

    private Map<String, String> filterChainDefinitionMap() {
        Map<String,String> filterMap = new LinkedHashMap<>();//注意要使用有序的 LinkedHashMap ，顺序匹配
        filterMap.put("/test/echo","anon");// 允许匿名访问
        filterMap.put("/test/admin","roles[ADMIN]");// 需要 ADMIN 角色
        filterMap.put("/test/normal","roles[NORMAL]");// 需要 NORMAL 角色
        filterMap.put("/logout","logout");// 退出
        filterMap.put("/**","authc");// 默认剩余的 URL ，需要经过认证
        return filterMap;
    }
}
