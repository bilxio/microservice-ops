package com.yonyou.microservice.gate.server;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.mvc.Controller;

import com.yonyou.cloud.zuul.db.api.EnableZuulProxyStore;
import com.yonyou.microservice.auth.client.EnableAceAuthClient;
import com.yonyou.microservice.filter.annotation.EnableGroovyFilter;
import com.yonyou.microservice.gate.ratelimit.EnableAceGateRateLimit;
import com.yonyou.microservice.gate.ratelimit.config.IUserPrincipal;
import com.yonyou.microservice.gate.ratelimit.config.properties.RateLimitProperties;
import com.yonyou.microservice.gate.server.config.UserPrincipal;
import com.yonyou.microservice.gate.server.utils.DbLog;

/**
 * @author joy
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients({"com.yonyou.microservice.auth.client.feign","com.yonyou.microservice.gate.server.feign"})
@EnableZuulProxyStore
@ComponentScan(basePackages = {"com.yonyou.cloud.zuul.db","com.yonyou.microservice.gate.server"})
@EnableScheduling
@EnableAceAuthClient
@EnableAceGateRateLimit
@RestController
@EnableGroovyFilter
public class GateBootstrap {
	@Autowired
	private RateLimitProperties p;

    
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(){
    	return "ok";
    }
    public static void main(String[] args) throws ParseException, UnsupportedEncodingException {
        DbLog.getInstance().start();
        SpringApplication.run(GateBootstrap.class, args);
    }

    @Bean
    @Primary
    IUserPrincipal userPrincipal(){
        return new UserPrincipal();
    }
}
 