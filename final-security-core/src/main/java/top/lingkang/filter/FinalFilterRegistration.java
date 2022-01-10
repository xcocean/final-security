package top.lingkang.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.lingkang.FinalManager;

/**
 * @author lingkang
 * @date 2022/1/9
 */
@Configuration
public class FinalFilterRegistration {
    @Autowired
    private FinalManager manager;

    @Bean(name = "finalSecurityFilter")
    public FilterRegistrationBean AuthFilterBean() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.addUrlPatterns("/*");//注意，不能使用/**两个 * 号
        bean.setName("finalSecurityFilter");
        bean.setOrder(1);//越小优先级越高
        bean.setFilter(new FinalSecurityFilter(manager));
        return bean;
    }
}
