package sii.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.undertow.UndertowEmbeddedServletContainerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.*;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@SpringBootApplication
public class DeployToWidlFlyApplication {
    public static void main(String[] args) {
        SpringApplication.run(DeployToWidlFlyApplication.class, args);
    }
}

@Component
class MyCustomizer implements EmbeddedServletContainerCustomizer {
    private static final Log log = LogFactory.getLog(MyCustomizer.class);

    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
        if (TomcatEmbeddedServletContainerFactory.class.isAssignableFrom(container.getClass())) {
            TomcatEmbeddedServletContainerFactory tc = TomcatEmbeddedServletContainerFactory.class.cast(container);
            log.info( "is tomcat");
        }
        if (UndertowEmbeddedServletContainerFactory.class.isAssignableFrom(container.getClass())) {
            UndertowEmbeddedServletContainerFactory undertow = UndertowEmbeddedServletContainerFactory.class.cast(container);
            log.info("is undertow");
        }
        if (JettyEmbeddedServletContainerFactory.class.isAssignableFrom(container.getClass())) {
            JettyEmbeddedServletContainerFactory jetty = JettyEmbeddedServletContainerFactory.class.cast(container);
            log.info("is jetty");
        }
    }
}

@Component
class MyCustomFilter implements Filter {
    public static final Log log = LogFactory.getLog(MyCustomFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("int(" + filterConfig + ")");
        log.info("int(" + filterConfig + ")");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("doFilter(" + servletRequest + "," + servletResponse + ")");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        log.info("destroy()");
    }
}

@RestController
class HiRestController {

    @RequestMapping("/hi")
    public Map<String, String> hi() {
        return Collections.singletonMap("hi", "Sii");
    }
}
