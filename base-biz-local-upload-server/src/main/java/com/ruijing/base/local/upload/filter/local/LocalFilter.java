package com.ruijing.base.local.upload.filter.local;

@FunctionalInterface
public interface LocalFilter<Context, EX1 extends Throwable, EX2 extends Throwable> {

    /**
     * @param context 过滤器上下文
     * @param chain   过滤链路
     * @throws Throwable
     */
    void doFilter(Context context, LocalFilterChain<Context, EX1, EX2> chain) throws EX1, EX2;

    default void destroy() {
        //no op
    }

}