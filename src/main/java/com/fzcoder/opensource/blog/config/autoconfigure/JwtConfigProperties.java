package com.fzcoder.opensource.blog.config.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "auth.jwt")
public class JwtConfigProperties {
    private String iss;
    private String secret;
    private Map<String, Long> expiration;

    public String getIss() {
        return iss;
    }

    public void setIss(String iss) {
        this.iss = iss;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Map<String, Long> getExpiration() {
        return expiration;
    }

    public void setExpiration(Map<String, Long> expiration) {
        this.expiration = expiration;
    }

}
