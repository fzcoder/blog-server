package com.fzcoder.opensource.blog.utils.oss;

import lombok.Data;

/**
 * 获取OSS上传授权返回结果
 * @author Frank Fang
 *
 */
@Data
public class OssPolicyResult {

	private String accessKeyId;

    private String policy;

    private String signature;

	private String key;

    private String dir;

    private String host;

	private String callback;
}
