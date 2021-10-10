package com.fzcoder.opensource.blog.config.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "aliyun.oss")
public class OssConfigProperties {
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
    private Map<String, String> policy;
    private Integer maxSize;
    private Map<String, String> dir;
    private String callback;
    private String bindDomainName;

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public Map<String, String> getPolicy() {
        return policy;
    }

    public void setPolicy(Map<String, String> policy) {
        this.policy = policy;
    }

    public Integer getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(Integer maxSize) {
        this.maxSize = maxSize;
    }

    public Map<String, String> getDir() {
        return dir;
    }

    public void setDir(Map<String, String> dir) {
        this.dir = dir;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public String getBindDomainName() {
        return bindDomainName;
    }

    public void setBindDomainName(String bindDomainName) {
        this.bindDomainName = bindDomainName;
    }
}
