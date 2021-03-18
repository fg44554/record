package com.lwz.demo;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication

@ComponentScan("com.lwz.demo.controller")
@ComponentScan("com.lwz.demo.service")
public class DemoApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SpringBootApplication.class);
    }
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setLocation("/home/wenzhe");
        return factory.createMultipartConfig();
    }
//    /**
//     *@Despription HTTP自动转向HTTPS 设置1
//     *@Params
//     *@return
//     *@Created Charles.Zhang
//     *@Time 2018/8/29
//     *@Modify
//     */
//    @Bean
//    public Connector connector(){
//        Connector connector=new Connector("org.apache.coyote.http11.Http11NioProtocol");
//        connector.setScheme("http");
//        connector.setPort(8080);
//        connector.setSecure(false);
//        connector.setRedirectPort(8082);
//        return connector;
//    }
//
//    /**
//     *@Despription HTTP自动转向HTTPS 设置2
//     *@Params
//     *@return
//     *@Created Charles.Zhang
//     *@Time 2018/8/29
//     *@Modify
//     */
//    @Bean
//    public TomcatServletWebServerFactory tomcatServletWebServerFactory(){
//        TomcatServletWebServerFactory tomcat =new TomcatServletWebServerFactory(){
//            @Override
//            protected void postProcessContext(Context context) {
//                SecurityConstraint securityConstraint=new SecurityConstraint();
//                securityConstraint.setUserConstraint("CONFIDENTIAL");
//                SecurityCollection collection=new SecurityCollection();
//                collection.addPattern("/");
//                securityConstraint.addCollection(collection);
//                context.addConstraint(securityConstraint);
//            }
//        };
//        tomcat.addAdditionalTomcatConnectors(connector());
//        return tomcat;
//    }
}
