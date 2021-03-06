package pub.doric.plugin;

import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.github.pengfeizhou.jscore.JSDecoder;
import com.github.pengfeizhou.jscore.JSObject;
import com.github.pengfeizhou.jscore.JSValue;
import com.github.pengfeizhou.jscore.JavaValue;

import java.util.concurrent.Callable;

import pub.doric.DoricContext;
import pub.doric.async.AsyncResult;
import pub.doric.extension.bridge.DoricMethod;
import pub.doric.extension.bridge.DoricPlugin;
import pub.doric.extension.bridge.DoricPromise;
import pub.doric.shader.ViewNode;
import pub.doric.utils.ThreadMode;

/**
 * @Description: pub.doric.plugin
 * @Author: pengfei.zhou
 * @CreateDate: 2019-11-29
 */
@DoricPlugin(name = "popover")
public class PopoverPlugin extends DoricJavaPlugin {
    private static final String TYPE = "popover";

    private FrameLayout mFullScreenView;

    public PopoverPlugin(DoricContext doricContext) {
        super(doricContext);
    }

    @DoricMethod
    public void show(JSDecoder decoder, final DoricPromise promise) {
        try {
            final JSObject jsObject = decoder.decode().asObject();
            getDoricContext().getDriver().asyncCall(new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    if (mFullScreenView == null) {
                        mFullScreenView = new FrameLayout(getDoricContext().getContext());
                        ViewGroup decorView = (ViewGroup) getDoricContext().getRootNode().getNodeView().getRootView();
                        decorView.addView(mFullScreenView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT));
                    }
                    mFullScreenView.bringToFront();
                    String viewId = jsObject.getProperty("id").asString().value();
                    String type = jsObject.getProperty("type").asString().value();
                    ViewNode node = ViewNode.create(getDoricContext(), type);
                    node.setId(viewId);
                    node.init(new FrameLayout.LayoutParams(0, 0));
                    node.blend(jsObject.getProperty("props").asObject());
                    mFullScreenView.addView(node.getNodeView());
                    getDoricContext().addHeadNode(TYPE, node);
                    return null;
                }
            }, ThreadMode.UI).setCallback(new AsyncResult.Callback<Object>() {
                @Override
                public void onResult(Object result) {
                    promise.resolve();
                }

                @Override
                public void onError(Throwable t) {
                    t.printStackTrace();
                    promise.reject(new JavaValue(t.getLocalizedMessage()));
                }

                @Override
                public void onFinish() {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            promise.reject(new JavaValue(e.getLocalizedMessage()));
        }
    }

    @DoricMethod
    public void dismiss(final JSValue value, final DoricPromise promise) {
        try {
            getDoricContext().getDriver().asyncCall(new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    if (value.isObject()) {
                        String viewId = value.asObject().getProperty("id").asString().value();
                        ViewNode node = getDoricContext().targetViewNode(viewId);
                        dismissViewNode(node);
                    } else {
                        dismissPopover();
                    }
                    return null;
                }
            }, ThreadMode.UI).setCallback(new AsyncResult.Callback<Object>() {
                @Override
                public void onResult(Object result) {
                    promise.resolve();
                }

                @Override
                public void onError(Throwable t) {
                    t.printStackTrace();
                    promise.reject(new JavaValue(t.getLocalizedMessage()));
                }

                @Override
                public void onFinish() {

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
            promise.reject(new JavaValue(e.getLocalizedMessage()));
        }
    }

    private void dismissViewNode(ViewNode node) {
        getDoricContext().removeHeadNode(TYPE, node);
        mFullScreenView.removeView(node.getNodeView());
        if (getDoricContext().allHeadNodes(TYPE).isEmpty()) {
            ViewGroup decorView = (ViewGroup) getDoricContext().getRootNode().getNodeView().getRootView();
            decorView.removeView(mFullScreenView);
            mFullScreenView = null;
        }
    }

    private void dismissPopover() {
        for (ViewNode node : getDoricContext().allHeadNodes(TYPE)) {
            dismissViewNode(node);
        }
    }

    @Override
    public void onTearDown() {
        super.onTearDown();
        this.dismissPopover();
    }
}
