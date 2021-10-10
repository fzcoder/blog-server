package com.fzcoder.opensource.blog.config.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "tomcat")
public class TomcatConfigProperties {
    private Map<String, String> listen;
    private Map<String, String> redirect;

    public Map<String, String> getListen() {
        return listen;
    }

    public void setListen(Map<String, String> listen) {
        this.listen = listen;
    }

    public Map<String, String> getRedirect() {
        return redirect;
    }

    public void setRedirect(Map<String, String> redirect) {
        this.redirect = redirect;
    }
}
