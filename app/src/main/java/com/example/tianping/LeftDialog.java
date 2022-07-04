package com.example.tianping;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import android.util.SparseArray;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class LeftDialog extends Dialog {

    private Button yes;//确定按钮
    private Button no;//取消按钮
    private Button clear;//清空按钮
    private EditText editText;
    private TextView messageView;
    private ListView listView;
    private ValueAdapter valueAdapter;
    private SparseArray<ValueBean> temp;
    private List<ValueBean> temp2;
    private int sum1=0;//上面总和
    private onYesOnclickListener yesOnclickListener;//确定按钮被点击了的监听器
    private onNoOnclickListener noOnclickListener;//取消按钮被点击了的监听器

    public LeftDialog(Context context, ValueAdapter valueAdapter) {
        super(context);
        this.valueAdapter = valueAdapter;
    }

    public int getSum1() {
        return sum1;
    }
    public int getSum2(){
        if (!editText.getText().toString().equals("")){
            return Integer.parseInt(editText.getText().toString());
        }else {
            return 0;
        }
    }

    // 设置确定按钮监听
    public void setYesOnclickListener(onYesOnclickListener yesOnclickListener) {
        this.yesOnclickListener = yesOnclickListener;
    }

    // 设置取消按钮监听
    public void setNoOnclickListener(onNoOnclickListener noOnclickListener) {
        this.noOnclickListener = noOnclickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leftdialog);

        //初始化界面控件
        initView();

        //初始化界面数据
        initData();

        //初始化界面控件的事件
        initEvent();
    }

    // 初始化界面控件
    private void initView() {
        yes = findViewById(R.id.yes);
        no = findViewById(R.id.no);
        clear = findViewById(R.id.clear);
        messageView = findViewById(R.id.message);
        editText = findViewById(R.id.edit_left);
        listView =findViewById(R.id.lv);

    }

    // 初始化界面控件的显示数据
    private void initData() {
        listView.setAdapter(valueAdapter);
        temp = valueAdapter.getDataList();
        temp2 = valueAdapter.getList();
        messageView.setText("0");
    }

    // 初始化界面的确定和取消按钮监听
    private void initEvent() {
        //设置确定按钮被点击后，向外界提供监听
        yes.setOnClickListener(v -> {
            if (yesOnclickListener != null) {
                yesOnclickListener.onYesOnclick();
            }
        });

        //设置取消按钮被点击后，向外界提供监听
        no.setOnClickListener(v -> {
            if (noOnclickListener != null) {
                noOnclickListener.onNoOnclick();
            }
        });

        clear.setOnClickListener(view -> update(0));

    }

    //更新列表选择总和,1为新增，0为清除
    public void update(int flag) {
        if (flag == 1) {
            //获取列表sum1
            int sum4 = 0;
            for (int i = 0; i < temp.size(); i++) {
                sum4 += temp.valueAt(i).getCount() * temp.valueAt(i).getNum();
            }
            sum1 = sum4;
            messageView.setText(String.valueOf(sum4));


        } else if (flag == 0) {
            temp.clear();
            if (temp2.size() > 0) {
                for (int j = 0; j < temp2.size(); j++) {
                    temp2.get(j).setCount(0);
                }
                sum1 =0;
                //刷新不能删
                messageView.setText(String.valueOf(sum1));
            }
            valueAdapter.notifyDataSetChanged();
        }
    }

    public interface onYesOnclickListener {
        void onYesOnclick();
    }

    public interface onNoOnclickListener {
        void onNoOnclick();
    }



}