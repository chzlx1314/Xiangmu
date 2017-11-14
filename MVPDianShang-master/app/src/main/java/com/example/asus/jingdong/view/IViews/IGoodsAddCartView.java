package com.example.asus.jingdong.view.IViews;

import com.example.asus.jingdong.model.bean.AddCatBean;

/**
 * 类描述   添加购物车商品的 接口
 */
public interface IGoodsAddCartView  extends IView {

    //    添加成功
    void onCartAddSucceed(AddCatBean bean);
    //    失败
    void onCartAddFail(String e);
}
