package com.ruijing.base.local.upload.filter.local;

@FunctionalInterface
public interface LocalFilterChain<Context, EX1 extends Throwable, EX2 extends Throwable> {

    /**
     * @param context 上下文
     * @throws Throwable
     */
    void doFilter(Context context) throws EX1, EX2;
}