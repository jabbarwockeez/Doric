package com.github.pengfeizhou.hego;

/**
 * @Description: Android
 * @Author: pengfei.zhou
 * @CreateDate: 2019-07-18
 */
public class HegoConstant {
    public static final String INJECT_LOG = "nativeLog";
    public static final String INJECT_REQUIRE = "nativeRequire";
    public static final String INJECT_BRIDGE = "nativeBridge";

    public static final String TEMPLATE_CONTEXT_CREATE = "Reflect.apply(" +
            "function(hego,context,require,exports){" + "\n" +
            "%s" + "\n" +
            "},hego.jsObtainContext(%s),[" +
            "undefined," +
            "hego.jsObtainContext(%s)," +
            "hego.__require__" +
            ",{}" +
            "])";
    public static final String TEMPLATE_CONTEXT_DESTORY = "hego.jsRelease(%s)";
    public static final String GLOBAL_HEGO = "hego";
    public static final String HEGO_CONTEXT_RELEASE = "jsReleaseContext";
    public static final String HEGO_CONTEXT_INVOKE = "jsCallEntityMethod";
}
