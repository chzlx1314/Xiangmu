package com.example.asus.jingdong.presenter;

import com.example.asus.jingdong.model.bane.AddOrDelCartModel;
import com.example.asus.jingdong.model.bean.AddCatBean;
import com.example.asus.jingdong.view.IViews.IGoodsAddCartView;

public class AddCartPresenter extends BasePresenter<IGoodsAddCartView> {


    private final AddOrDelCartModel model;

    public AddCartPresenter(IGoodsAddCartView iView) {
        super(iView);


        model = new AddOrDelCartModel();
    }

    public void AddGoodsToCart(String key, String goods_id, String quantity) {

        model.AddGoodsToCart(new AddOrDelCartModel.DataCallBack() {
            @Override
            public void onSucceed(AddCatBean bean) {

                iView.onCartAddSucceed(bean);
            }

            @Override
            public void onFail(String e) {

                iView.onCartAddFail(e);

            }
        }, key, goods_id, quantity);
    }


}

