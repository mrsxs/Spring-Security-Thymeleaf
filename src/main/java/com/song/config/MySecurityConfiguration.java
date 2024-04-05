package com.song.config;

import com.song.handler.MyAccessDeniedHandler;
import com.song.handler.MyAuthenticationSuccessHandler;
import com.song.handler.MyLogoutHandler;
import com.song.handler.MyLogoutSuccessHandler;
import com.song.service.impl.MyUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.annotation.web.configurers.RememberMeConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
//开启web安全
@EnableWebSecurity
//开启方法级别的安全控制
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class MySecurityConfiguration {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private MyUserDetailsServiceImpl myUserDetailsService;

    /**
     * 创建SecurityFilterChain bean 用于配置安全过滤器链
     *
     * @return
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //创建匿名内部类实现Customizer接口
        Customizer<FormLoginConfigurer<HttpSecurity>> formLoginConfigurerCustomizer = new Customizer<FormLoginConfigurer<HttpSecurity>>() {

            @Override
            public void customize(FormLoginConfigurer<HttpSecurity> configurer) {
                //具体配置
                //设置登录页面 默认/login 必须是get请求 否则会报错
                configurer.loginPage("/login").permitAll()
                        // 设置登录请求的url 默认是/login
                        .loginProcessingUrl("/userLogin")
                        //设置登录成功后重定向的url
                        .defaultSuccessUrl("/main")
                        //设置登录成功后转发的url
                        .successForwardUrl("/main")
                        //设置登录成功参数是Authentication
                        .successHandler(new MyAuthenticationSuccessHandler("/main?perm=main", true))
                        //设置登录失败后重定向的url 默认是/login?error
                        .failureUrl("/loginFail");
                //设置登录失败后转发的url
//                        .failureForwardUrl("/loginError");
                //设置登录失败后的处理
//                        .failureHandler(new MyAuthenticationFailureHandler("/loginError"));
                //设置请求参数名 默认是username和password
               /* .usernameParameter("username")
                        .passwordParameter("password")*/


            }
        };
        http.formLogin(formLoginConfigurerCustomizer);
        http.authorizeRequests()
                //设置登录页面和登录请求不需要认证 anonymous() 匿名访问
                .requestMatchers("/login", "loginFail", "/userLogin").anonymous()
//                设置静态资源不需要认证/
                .requestMatchers("/js/**", "/css/**", "/images/**").permitAll()
                //设置退出登录 必须是已经认证的用户才能访问
                .requestMatchers("logout").authenticated()
                //设置其他请求都需要认证
                .anyRequest().authenticated();
//                .requestMatchers("/**").access("@myPermissionCheckerImpl.checkPermission(request,authentication)");
        //记住我功能 需要配置tokenRepository
        Customizer<RememberMeConfigurer<HttpSecurity>> rememberMeConfigurerCustomizer = new Customizer<RememberMeConfigurer<HttpSecurity>>() {
            @Override
            public void customize(RememberMeConfigurer<HttpSecurity> configurer) {
                //设置tokenRepository 保存记住我功能的token
                configurer.tokenRepository(persistentTokenRepository(dataSource))
                        //请求参数名 默认是remember-me
                        .rememberMeParameter("rememberMe")
                        //记住我服务器端保存到数据库
                        .rememberMeCookieName("SONG_REMEMBER_ME")
                        //cookie 的domain 默认是当前域名
                        .rememberMeCookieDomain("localhost")
                        //记住我功能有效时间 默认是2周 单位是秒
                        .tokenValiditySeconds(60 * 60 * 24 * 7)
                        //设置自定义的UserDetailsService接口实现对象
                        .userDetailsService(myUserDetailsService);


            }
        };
        http.rememberMe(rememberMeConfigurerCustomizer);
        //设置退出登录功能
        Customizer<LogoutConfigurer<HttpSecurity>> logoutConfigurerCustomizer = new Customizer<LogoutConfigurer<HttpSecurity>>() {
            @Override
            public void customize(LogoutConfigurer<HttpSecurity> configurer) {
                //设置登出url 默认是/logout
                configurer.logoutUrl("/logout")
                        //退出成功后重定向的url 默认是/login?logout
                        .logoutSuccessUrl("/login")
                        .logoutSuccessHandler(new MyLogoutSuccessHandler())
                        //增加额外的登出处理
                        .addLogoutHandler(new MyLogoutHandler());

            }
        };
        http.logout(logoutConfigurerCustomizer);
        //配置异常处理

        Customizer<ExceptionHandlingConfigurer<HttpSecurity>> exceptionHandlingConfigurerCustomizer = new Customizer<ExceptionHandlingConfigurer<HttpSecurity>>() {
            @Override
            public void customize(ExceptionHandlingConfigurer<HttpSecurity> configurer) {
                //设置403错误处理
                configurer.accessDeniedHandler(new MyAccessDeniedHandler());
            }
        };
        http.exceptionHandling(exceptionHandlingConfigurerCustomizer);

        //关闭csrf
        http.csrf().disable();
        return http.build();
    }

    /**
     * 创建bean
     * 用于配置记住我功能
     * jdbcTokenRepositoryImpl 基于数据库的实现 把token信息存储到数据库
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository(DataSource dataSource) {
        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();

        repository.setDataSource(dataSource);
        //设置数据源 初始化参数 仅在第一次启动时创建表 默认是false
        //自动会创建表persistent_logins
        repository.setCreateTableOnStartup(false);
        return repository;
    }

    /**
     * 创建passwordEncoder bean
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
