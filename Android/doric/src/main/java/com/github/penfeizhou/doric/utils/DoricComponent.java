package com.github.penfeizhou.doric.utils;

import com.github.penfeizhou.doric.DoricContext;

/**
 * @Description: com.github.penfeizhou.doric.utils
 * @Author: pengfei.zhou
 * @CreateDate: 2019-07-20
 */
public abstract class DoricComponent {
    private final DoricContext doricContext;

    public DoricComponent(DoricContext doricContext) {
        this.doricContext = doricContext;
    }

    public DoricContext getDoricContext() {
        return doricContext;
    }
}
