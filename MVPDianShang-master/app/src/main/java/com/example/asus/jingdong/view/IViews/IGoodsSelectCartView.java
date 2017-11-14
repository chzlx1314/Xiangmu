package com.example.asus.jingdong.view.IViews;

import com.example.asus.jingdong.model.bean.CarBean;

/**
 * 类描述    查询购物车商品的 接口
 */
public interface IGoodsSelectCartView extends IView {

    //    查询成功
    void onCartSelectSucceed(CarBean bean);
    //    失败
    void onCartDelectFail(String e);
}
