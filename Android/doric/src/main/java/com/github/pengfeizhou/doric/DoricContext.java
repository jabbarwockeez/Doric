package com.github.pengfeizhou.doric;

import com.github.pengfeizhou.doric.async.AsyncResult;
import com.github.pengfeizhou.doric.plugin.DoricNativePlugin;
import com.github.pengfeizhou.doric.extension.bridge.DoricPluginInfo;
import com.github.pengfeizhou.jscore.JSDecoder;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: Doric
 * @Author: pengfei.zhou
 * @CreateDate: 2019-07-18
 */
public class DoricContext {
    private final String mContextId;
    private final Map<String, DoricNativePlugin> mPluginMap = new HashMap<>();

    DoricContext(String contextId) {
        this.mContextId = contextId;
    }

    public static DoricContext createContext(String script, String alias) {
        return DoricDriver.getInstance().createContext(script, alias);
    }

    public AsyncResult<JSDecoder> callEntity(String methodName, Object... args) {
        return getDriver().invokeContextEntityMethod(mContextId, methodName, args);
    }

    public DoricDriver getDriver() {
        return DoricDriver.getInstance();
    }


    public String getContextId() {
        return mContextId;
    }

    public void teardown() {
        DoricDriver.getInstance().destroyContext(mContextId).setCallback(new AsyncResult.Callback<Boolean>() {
            @Override
            public void onResult(Boolean result) {

            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onFinish() {
                mPluginMap.clear();
            }
        });
    }

    public DoricNativePlugin obtainPlugin(DoricPluginInfo doricPluginInfo) {
        DoricNativePlugin plugin = mPluginMap.get(doricPluginInfo.getName());
        if (plugin == null) {
            plugin = doricPluginInfo.createPlugin(this);
            mPluginMap.put(doricPluginInfo.getName(), plugin);
        }
        return plugin;
    }
}