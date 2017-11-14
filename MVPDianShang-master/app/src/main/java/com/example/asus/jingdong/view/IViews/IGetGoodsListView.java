package com.example.asus.jingdong.view.IViews;


import com.example.asus.jingdong.model.bean.GoodsListDataBean;
/**
 * 类描述
 */

public interface IGetGoodsListView extends IView {

    //    获取数据成功
    void onGetDataSucceed(GoodsListDataBean bean);
    //    获取数据失败
    void onGetDataFail(String e);

}
