package com.example.asus.jingdong.presenter;


import com.example.asus.jingdong.model.bane.GetGoodsClassDataModel;
import com.example.asus.jingdong.model.bean.GoodsClassBean;
import com.example.asus.jingdong.view.IViews.IGetGoodsClassView;


public class GetGoodsClassPresenter extends BasePresenter<IGetGoodsClassView> {


    private final GetGoodsClassDataModel model;
    public GetGoodsClassPresenter(IGetGoodsClassView iView) {
        super(iView);

        model = new GetGoodsClassDataModel();
    }

    //    得到左侧 商品分类的数据
    public void getData() {

        model.getData(new GetGoodsClassDataModel.DataCallBack() {
            @Override
            public void onGetDataSucceed(GoodsClassBean bean) {
                iView.onGetDataSucceed(bean);
            }

            @Override
            public void onGetDataFail(String e) {

                iView.onGetDataFail(e);
            }
        });


    }




}
