package com.example.asus.jingdong.presenter;

import com.example.asus.jingdong.model.bane.GetMoreGoodsClassDataModel;
import com.example.asus.jingdong.model.bean.GoodsClassData;
import com.example.asus.jingdong.view.IViews.IGetGoodsClassViews;


public class GetGoodsClassPresentes extends BasePresenter<IGetGoodsClassViews>{

    private final GetMoreGoodsClassDataModel moreModel;


    public GetGoodsClassPresentes(IGetGoodsClassViews iView) {
        super(iView);
        moreModel = new GetMoreGoodsClassDataModel();
    }
    //    得到右侧 商品分类的数据
    public void getMoreData(String goodsId) {

        moreModel.getMoreData(new GetMoreGoodsClassDataModel.DataCallBack() {
            @Override
            public void onGetDataSucceed(GoodsClassData bean) {
                iView.onGetDataSucceed(bean);
            }

            @Override
            public void onGetDataFail(String e) {

                iView.onGetDataFail(e);
            }
        },goodsId);


    }
}
