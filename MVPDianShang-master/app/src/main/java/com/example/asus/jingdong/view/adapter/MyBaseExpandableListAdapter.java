package com.example.asus.jingdong.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.asus.jingdong.R;
import com.example.asus.jingdong.model.bean.Bean;
import com.example.asus.jingdong.model.bean.CarBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 购物车二级链表的适配器
 */

public class MyBaseExpandableListAdapter extends BaseExpandableListAdapter {

    private ArrayList<HashMap<Integer, Boolean>> groupList;//保存一级列表checkbox状态
    private ArrayList<ArrayList<HashMap<Integer, Boolean>>> childList;//保存二级列表checkbox状态
    private String[] groups;//保存一级列表内容
    private CarBean.DataBean.ListBean[][] childs;//保存二级列表内容
    //保存商品单价和数量,里面的GWC_Data是自定义的JavaBean,根据自己需求来定义属性
    private List<List<Bean>> dataList;
    Context context;
    List<CarBean.DataBean> mlist;//传过来的数据

    public MyBaseExpandableListAdapter(Context context, List<CarBean.DataBean> mlist) {
        this.mlist = mlist;
        this.context = context;
        //初始化数据
        initData();
    }

    private void initData() {
        dataList = new ArrayList<>();//保存商品单价和数量
        groupList = new ArrayList<>();//保存一级列表checkbox状态
        childList = new ArrayList<>();//保存二级列表checkbox状态
        groups = new String[mlist.size()];//保存一级列表内容
        childs = new CarBean.DataBean.ListBean[mlist.size()][];//保存二级列表内容

        for (int i = 0; i < mlist.size(); i++) {
            //保存一级列表的名字,商家1,商家2...
            groups[i] = mlist.get(i).getSellerName();
            //保存一级列表中的checkbox是否选中
            HashMap<Integer, Boolean> gmap = new HashMap<>();
            gmap.put(i, false);
            groupList.add(gmap);

            //这个是设置二级列表的数据
            //得到数据里面保存的商品信息的集合
            List<CarBean.DataBean.ListBean> goods_list = mlist.get(i).getList();
            //这里-5的原因是因为返回的集合太长,只为了实现效果,所以让他只显示了3条数据,方便操作
            CarBean.DataBean.ListBean[] strings = new CarBean.DataBean.ListBean[goods_list.size()];
            ArrayList<HashMap<Integer, Boolean>> listHashMap = new ArrayList<>();
            ArrayList<Bean> gwc_datas = new ArrayList<>();//每个二级列表的单价和数量
            for (int y = 0; y < strings.length; y++) {
                //创建每一个二级列表中数据的HashMap集合,保存状态
                HashMap<Integer, Boolean> hashMap = new HashMap<>();
                hashMap.put(y, false);
                listHashMap.add(hashMap);
                //保存二级列表的数据
                strings[y] = goods_list.get(y);
                //保存单价,默认数量是一个
                double price = goods_list.get(y).getPrice();
                String prices = String.valueOf(price);
                gwc_datas.add(new Bean ("1",prices));
            }
            childList.add(listHashMap);
            childs[i] = strings;
            dataList.add(gwc_datas);
        }
    }

    @Override
    public int getGroupCount() {
        return groups.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childs[groupPosition].length;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childs[childPosition];
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupholder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.gouwuche_group_item, null);
            groupholder = new GroupViewHolder();
            groupholder.ck = (CheckBox) convertView.findViewById(R.id.group_checkbox);
            groupholder.title = (TextView) convertView.findViewById(R.id.group_name);
            convertView.setTag(groupholder);
        } else {
            groupholder = (GroupViewHolder) convertView.getTag();
        }
        groupholder.title.setText(groups[groupPosition]);

        final GroupViewHolder finalGroupholder = groupholder;
        groupholder.ck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<Integer, Boolean> hashMap = groupList.get(groupPosition);
                hashMap.put(groupPosition, !groupList.get(groupPosition).get(groupPosition));
                //点击一级列表,让二级列表全部选中
                setChildCheckBox(groupPosition);
                //得到选中的商品数和价格拼成的字符串
                String shop = getShopNumber();
                //查询是否全部选中
                boolean b = selectShopAll();
                //调用自定义接口,把数据传出去
                numberAndIsCheckAll.getNumber(v, shop, b);
                notifyDataSetChanged();
            }
        });

        Boolean aBoolean = groupList.get(groupPosition).get(groupPosition);
        groupholder.ck.setChecked(aBoolean);
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.gouwuche_child_item, null);
            holder = new ChildViewHolder();
            holder.ck = (CheckBox) convertView.findViewById(R.id.child_checkbox);
            holder.img = (ImageView) convertView.findViewById(R.id.gwc_item_img);
            holder.title = (TextView) convertView.findViewById(R.id.gwc_item_name);
            holder.price = (TextView) convertView.findViewById(R.id.gwc_item_price);
            holder.jianshao = (Button) convertView.findViewById(R.id.btn_decreases);
            holder.num = (EditText) convertView.findViewById(R.id.ed_amounts);
            holder.zengjia = (Button) convertView.findViewById(R.id.btn_increases);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }

        CarBean.DataBean.ListBean goodsCommendListBean = childs[groupPosition][childPosition];
        //价格
        String goods_price = goodsCommendListBean.getPrice() + "";
        holder.price.setText("￥"+goods_price);
        //标题
        holder.title.setText((CharSequence) goodsCommendListBean.getTitle().substring(0,25));
        String images = goodsCommendListBean.getImages();
        String[] split = images.split("\\|");
        Glide.with(context).load(split[0]).into(holder.img);
        //复选框的点击事件
        holder.ck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<HashMap<Integer, Boolean>> mapArrayList = childList.get(groupPosition);
                HashMap<Integer, Boolean> hashMap = mapArrayList.get(childPosition);
                hashMap.put(childPosition, !hashMap.get(childPosition));
                //判断二级列表选中的个数
                setGroupCheckBox(groupPosition);
                //得到选中的商品数和价格拼成的字符串
                String shop = getShopNumber();
                //查询是否全部选中
                boolean b = selectShopAll();
                //调用自定义接口,把数据传出去
                numberAndIsCheckAll.getNumber(v, shop, b);
                notifyDataSetChanged();
            }
        });

        //设置他的选中状态,根据集合来变化
        ArrayList<HashMap<Integer, Boolean>> arrayList = childList.get(groupPosition);
        HashMap<Integer, Boolean> hashMap = arrayList.get(childPosition);
        holder.ck.setChecked(hashMap.get(childPosition));

        //当个数大于一的时候减少按钮才可以点击,否则不能点击
        int number = Integer.parseInt(holder.num.getText().toString().trim());
        if (number > 1) {
            holder.jianshao.setEnabled(true);
        } else if (number <= 1) {
            holder.jianshao.setEnabled(false);
        }
        //增加数量
        final ChildViewHolder finalHolder = holder;
        holder.zengjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(finalHolder.num.getText().toString());
                number += 1;
                //修改集合中相对应的数量
                List<Bean> gwc_datas = dataList.get(groupPosition);
                gwc_datas.get(childPosition).setNumber(number + "");
                //修改页面的显示数量
                finalHolder.num.setText(number + "");
                //判断商品是否全部选中
                boolean b = selectShopAll();
                //得到选中的商品数和价格拼成的字符串
                String shop = getShopNumber();
                //调用自定义接口,把数据传出去
                numberAndIsCheckAll.getNumber(v, shop, b);
                notifyDataSetChanged();
            }
        });

        holder.jianshao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(finalHolder.num.getText().toString());
                if (number > 1) {
                    --number;
                    //修改集合中相对应的数量
                    List<Bean> gwc_datas = dataList.get(groupPosition);
                    gwc_datas.get(childPosition).setNumber(number + "");
                    //修改页面的显示数量
                    finalHolder.num.setText(number + "");
                }
                //判断商品是否全部选中
                boolean b = selectShopAll();
                //得到选中的商品数和价格拼成的字符串
                String shop = getShopNumber();
                //调用自定义接口,把数据传出去
                numberAndIsCheckAll.getNumber(v, shop, b);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    public NumberAndIsCheckAll numberAndIsCheckAll;

    public interface NumberAndIsCheckAll {
        void getNumber(View view, String shop, boolean ischecked);
    }

    public void getNumberAndIsCheckAll(NumberAndIsCheckAll numberAndIsCheckAll) {
        this.numberAndIsCheckAll = numberAndIsCheckAll;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }


    //点击一级列表,让二级列表全部选中
    public void setChildCheckBox(int index) {
        //遍历存放二级列表数据的集合,设置成一级列表的选中状态
        ArrayList<HashMap<Integer, Boolean>> hashMaps = childList.get(index);
        for (int i = 0; i < hashMaps.size(); i++) {
            HashMap<Integer, Boolean> hashMap = hashMaps.get(i);
            Set<Map.Entry<Integer, Boolean>> set = hashMap.entrySet();
            for (Map.Entry<Integer, Boolean> entry : set) {
                entry.setValue(groupList.get(index).get(index));
            }
        }
        notifyDataSetChanged();
    }


    //点击二级列表的时候,判断是否全选中,如果全选中让一级列表也选中
    public void setGroupCheckBox(int grouIndex) {
        ArrayList<HashMap<Integer, Boolean>> arrayList = childList.get(grouIndex);
        boolean isChecked = true;
        for (int i = 0; i < arrayList.size(); i++) {
            HashMap<Integer, Boolean> hashMap = arrayList.get(i);
            Set<Map.Entry<Integer, Boolean>> set = hashMap.entrySet();

            for (Map.Entry<Integer, Boolean> entry : set) {
                if (!entry.getValue()) {
                    isChecked = false;
                    break;
                }
            }
        }

        HashMap<Integer, Boolean> groupHashMap = groupList.get(grouIndex);
        groupHashMap.put(grouIndex, isChecked);
        notifyDataSetChanged();

    }

    //全选,传的参数判断全选按钮是否选中(true,false),让列表随之改变
    public void setCheckedAll() {
        boolean boo = false;
        //判断是否有没选中,有没选中的话boo=true,在下面的遍历中会全部设置成true
        //如果都是选中状态,boo=false,在下面的遍历中会全部设置成false
        for (int i = 0; i < groupList.size(); i++) {
            HashMap<Integer, Boolean> hashMap = groupList.get(i);
            Set<Map.Entry<Integer, Boolean>> set = hashMap.entrySet();
            for (Map.Entry<Integer, Boolean> entry : set) {
                //如果有没选中的,就把boo设置成true
                //然后下面的那个循环中,把boo设置到value中
                if (!entry.getValue()) {
                    boo = true;
                    break;
                }
            }
        }
        //设置成一级列表的选中状态
        for (int i = 0; i < groupList.size(); i++) {
            HashMap<Integer, Boolean> hashMap = groupList.get(i);
            Set<Map.Entry<Integer, Boolean>> set = hashMap.entrySet();
            for (Map.Entry<Integer, Boolean> entry : set) {
                entry.setValue(boo);
            }
            setChildCheckBox(i);
        }
        notifyDataSetChanged();
    }

    //计算选中的商品个数和总价格
    public String getShopNumber() {
        double Total_price = 0;//总价格
        int Total_number = 0;//总数量
        String str;
        for (int i = 0; i < childList.size(); i++) {
            ArrayList<HashMap<Integer, Boolean>> childArrayList = childList.get(i);
            for (int y = 0; y < childArrayList.size(); y++) {
                HashMap<Integer, Boolean> hashMap = childArrayList.get(y);
                Set<Map.Entry<Integer, Boolean>> set = hashMap.entrySet();
                //如果店铺选中,得到总价和数量
                for (Map.Entry<Integer, Boolean> entry : set) {
                    if (entry.getValue()) {
                        //如果选中,得到单价个数量
                        Bean gwc_data = dataList.get(i).get(y);
                        double price = Double.parseDouble(gwc_data.getPrice());
                        double number = Double.parseDouble(gwc_data.getNumber());
                        Total_price += (price * number);
                        Total_number += number;
                    }
                }
            }
        }
        str = Total_price + "|" + Total_number;
        //把总价格和数量拼成字符串
        return str;
    }

    //当所有商品全部选中,全选按钮也选中,遍历一级列表,如果一级列表全部选中,则按钮也选中
    public boolean selectShopAll() {
        //默认让全选按钮选中
        boolean boo = true;
        //循环遍历,如果有一个没选中,就设置全选按钮不选中
        for (int i = 0; i < groupList.size(); i++) {
            HashMap<Integer, Boolean> hashMap = groupList.get(i);
            Set<Map.Entry<Integer, Boolean>> set = hashMap.entrySet();
            for (Map.Entry<Integer, Boolean> entry : set) {
                if (!entry.getValue()) {
                    boo = false;
                    break;
                }
            }
        }

        return boo;
    }

    class GroupViewHolder {
        CheckBox ck;
        TextView title;
    }

    class ChildViewHolder {
        CheckBox ck;
        TextView title;
        ImageView img;
        TextView price;
        EditText num;
        Button zengjia;
        Button jianshao;

    }
}