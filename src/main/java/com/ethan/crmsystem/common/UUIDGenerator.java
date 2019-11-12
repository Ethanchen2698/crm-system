package com.ethan.crmsystem.common;

import java.util.UUID;

/**
 * Description: uuid生成器
 *
 * @author <a href="mailto:chencq@hzwesoft.com">chencq</a>
 * @version $Id: UUIDGenerator.java, 2018/7/30 14:07 $
 */
public final class UUIDGenerator {

    /**
     * 生成UUID
     *
     * @return 生成uuid
     */
    public static String random() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
