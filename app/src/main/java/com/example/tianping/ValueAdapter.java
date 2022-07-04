package com.example.tianping;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class ValueAdapter extends BaseAdapter {

    private  Context context;
    private  List<ValueBean> list;
    private  SparseArray<ValueBean>dataList;
    /*构造函数*/
    public ValueAdapter(Context context,List<ValueBean>list,SparseArray<ValueBean>dataList) {
        this.context = context;
        this.list = list;
        this.dataList = dataList;
    }

    public SparseArray<ValueBean>getDataList(){
        return dataList;
    }
    public List<ValueBean>getList(){
        return list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /*书中详细解释该方法*/
    @SuppressLint("InflateParams")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_item, null);
            holder = new ViewHolder();
            /*得到各个控件的对象*/
            holder.btnIncrease = convertView.findViewById(R.id.btnIncrease);
            holder.btnDecrease = convertView.findViewById(R.id.btnDecrease);
            holder.name = convertView.findViewById(R.id.name);
            holder.etAmount = convertView.findViewById(R.id.etAmount);
            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象
        }
        /*设置TextView显示的内容，即我们存放在动态数组中的数据*/
        holder.name.setText(list.get(position).getTitle());
        holder.etAmount.setText(String.valueOf(list.get(position).getCount()));

        /*为Button添加点击事件*/
        holder.btnIncrease.setOnClickListener(v -> {
            ((MainActivity)context).handlerCarNum(1,list.get(position));
            holder.etAmount.setText(String.valueOf(list.get(position).getCount()));
        });

        holder.btnDecrease.setOnClickListener(v -> {
            ((MainActivity)context).handlerCarNum(0,list.get(position));
            holder.etAmount.setText(String.valueOf(list.get(position).getCount()));
        });

        return convertView;
    }

    }

/*存放控件*/
    class ViewHolder {
    public Button btnIncrease;
    public Button btnDecrease;
    public TextView name;
    public TextView etAmount;
}


