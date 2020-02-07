package com.config;

import com.shiro.ShiroRealm;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import static org.apache.commons.codec.digest.MessageDigestAlgorithms.MD5;

/**
 * 20191122
 * @auther lfp
 */
@Configuration
public class ShiroConfig {


    /**
     * 1.安全管理器
     * @return
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager(){
        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
        securityManager.setAuthenticator(authenticator());
        securityManager.setCacheManager(ehcacheManager());
        securityManager.setRealm(jdbcRealm());
        return securityManager;
    }

    /**
     * 2. 配置 CacheManager.
     * 2.1 需要加入 ehcache 的 jar 包及配置文件.
     * @return
     */
    @Bean(name = "ehcacheManager")
    public EhCacheManager ehcacheManager() {
        EhCacheManager manager = new EhCacheManager();
        manager.setCacheManagerConfigFile("classpath:ehcache.xml");
        return manager;
    }

    /**
     * 策略
     * @return
     */
    @Bean
    public Authenticator authenticator(){
        ModularRealmAuthenticator modularRealmAuthenticator = new ModularRealmAuthenticator();
        modularRealmAuthenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        return modularRealmAuthenticator;

    }

    /**
     * 3. 配置 Realm
     * 3.1 直接配置实现了 org.apache.shiro.realm.Realm 接口的 bean
     * @return
     */
    @Bean(name = "jdbcRealm")
    public ShiroRealm jdbcRealm(){
        ShiroRealm jdbcRealm = new ShiroRealm();
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName(MD5);
        hashedCredentialsMatcher.setHashIterations(1);
        jdbcRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return jdbcRealm;
    }

    /**
     * 4. 配置 LifecycleBeanPostProcessor. 可以自定的来调用配置在 Spring IOC 容器中 shiro bean 的生命周期方法.
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }


    /**
     * 5. 启用 IOC 容器中使用 shiro 的注解. 但必须在配置了 LifecycleBeanPostProcessor 之后才可以使用.
     * @return
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
        defaultAAP.setProxyTargetClass(true);
        return defaultAAP;
    }

    /**
     * 6.开启Shiro注解通知器
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }



    /**
     * 7. 配置 ShiroFilter.
     * 7.1 id 必须和 web.xml 文件中配置的 DelegatingFilterProxy 的 <filter-name> 一致.
     * 若不一致, 则会抛出: NoSuchBeanDefinitionException. 因为 Shiro 会来 IOC 容器中查找和 <filter-name> 名字对应的 filter bean.
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter() {
        //定义shiroFactoryBean
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置securityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        //设置默认登录的url
        shiroFilterFactoryBean.setLoginUrl("/login/index.do");
        shiroFilterFactoryBean.setSuccessUrl("/index/index.do");
        shiroFilterFactoryBean.setUnauthorizedUrl("/login/unauthorized.do");

        Map<String, String> filterDeMap = new LinkedHashMap<>();
        filterDeMap.put("/login/index.do", "anon");
        filterDeMap.put("/login/login.do", "anon");
        filterDeMap.put("/swagger-ui.html", "anon");
        filterDeMap.put("//webjars/**", "anon");
        filterDeMap.put("/v2/**", "anon");
        filterDeMap.put("/swagger-resources/**", "anon");
        filterDeMap.put("/res/**", "anon");
        filterDeMap.put("/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterDeMap);
        return shiroFilterFactoryBean;
    }


    /**
     *  8.访问未授权时跳转页面
     * @return
     */
    @Bean
    public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
        SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();

        Properties mappers = new Properties();

        /**
         * 参数一：异常的类型，注意必须是异常类型的全名
         * 参数二：视图名称
         */
        mappers.put("org.apache.shiro.authz.UnauthorizedException", "/views/sys/unauthorized");

        // 设置异常与视图的映射信息
        resolver.setExceptionMappings(mappers);
        return resolver;

    }
}
