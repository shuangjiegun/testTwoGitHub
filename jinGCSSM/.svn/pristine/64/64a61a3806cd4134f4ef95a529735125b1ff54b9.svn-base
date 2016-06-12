package com.goldfinance.jinGC.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.hundsun.fcloud.ufx.sdk.UfxSDK;
import com.hundsun.fcloud.ufx.sdk.builder.PropertiesUfxSDKBuilder;

public abstract class UfxSDKCommon {
    private UfxSDK ufxSDK;
    public UfxSDKCommon() {
        try {
            InputStream input = getClass().getClassLoader().getResourceAsStream("ufxsdk.properties");
            Properties props = new Properties();
            props.load(input);
            this.ufxSDK = new PropertiesUfxSDKBuilder(props).build();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public UfxSDK getUfxSDK() {
        return ufxSDK;
    }
}
