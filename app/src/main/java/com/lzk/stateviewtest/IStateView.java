package com.lzk.stateviewtest;

/**
 * @author LiaoZhongKai
 * @date 2019/8/27.
 */
public interface IStateView {
    void showLoading(String tip);
    void showError(String tip);
    void showEmpty(String tip);
    void showNormal();
    void reload();
}
