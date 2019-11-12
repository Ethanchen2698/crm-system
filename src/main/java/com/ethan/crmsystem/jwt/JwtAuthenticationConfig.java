package com.ethan.crmsystem.jwt;

import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

/**
 * Description: jwt配置
 *
 * @author <a href="mailto:chencq@hzwesoft.com">chencq</a>
 * @version $Id: JwtAuthenticationConfig.java, 2018/9/6 15:41 $
 */
@Getter
@ToString
public class JwtAuthenticationConfig {

    @Value("${jwt.url:/login}")
    private String url;

    @Value("${jwt.header:Authorization}")
    private String header;

    @Value("${jwt.prefix}")
    private String prefix;

    /**
     * 默认1小时超时
     */
    @Value("${jwt.expiration:#{60*60}}")
    private int expiration;

    @Value("${jwt.secret}")
    private String secret;
}
