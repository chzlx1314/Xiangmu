package com.example.asus.jingdong.presenter;

import com.example.asus.jingdong.model.bane.GetGoodsCarBeanModel;
import com.example.asus.jingdong.model.bean.CarBean;
import com.example.asus.jingdong.view.IViews.IGoodsSelectCartView;

public class GetGoodsCarPresentes extends BasePresenter<IGoodsSelectCartView>{

    private final GetGoodsCarBeanModel moreModel;


    public GetGoodsCarPresentes(IGoodsSelectCartView iView) {
        super(iView);
        moreModel = new GetGoodsCarBeanModel();
    }
    //    得到购物车的数据
    public void getCarData() {

        moreModel.getCarData(new GetGoodsCarBeanModel.DataCallBack() {
            @Override
            public void onGetDataSucceed(CarBean bean) {
                iView.onCartSelectSucceed(bean);
            }

            @Override
            public void onGetDataFail(String e) {
                iView.onCartDelectFail(e);
            }
        });



    }
}
