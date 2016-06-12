package com.hundsun.test;

import com.hundsun.fcloud.ufx.sdk.UfxSDK;
import com.hundsun.fcloud.ufx.sdk.builder.PropertiesUfxSDKBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * UFX SDK 单元测试
 *
 * @author Gavin Hu
 * @create 2015/4/20
 */
public abstract class UfxSDKTest {

    private UfxSDK ufxSDK;

    public UfxSDKTest() {
        try {
            InputStream input = getClass().getClassLoader().getResourceAsStream("ufxsdk-sit.properties");
            Properties props = new Properties();
            props.load(input);
            //
            this.ufxSDK = new PropertiesUfxSDKBuilder(props).build();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public UfxSDK getUfxSDK() {
        return ufxSDK;
    }

}
