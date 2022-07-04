package com.example.tianping;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.provider.ContactsContract;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Mytoast extends Toast {
    private static Mytoast mytoast;
    private static ImageView toast_img;
    private static TextView toast_text;

    public Mytoast(Context context){
        super(context);
    }
    public static void cancelToast(){
        if (mytoast!=null){
            mytoast.cancel();
        }
    }
    public void cancel(){
        try{
            super.cancel();
        }catch (Exception e){

        }
    }
    @Override
    public void show(){
        try {
            super.show();
        }catch (Exception e){

        }
    }
    private static void initToast(Context context, CharSequence text){
        try {
            cancelToast();
            View layout;

            mytoast = new Mytoast(context);

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (text.toString()=="请输入数据"||text.toString()=="请勿同时勾选和手动填写"){
                layout = inflater.inflate(R.layout.toastwrong,null);

            }else {
                layout = inflater.inflate(R.layout.toastshow,null);

            }
//
//            //toast_img = (ImageView)layout.findViewById(R.id.toast_img);
//
            toast_text = (TextView) layout.findViewById(R.id.toast_text);

            toast_text.setText(text);

            mytoast.setView(layout);

            mytoast.setGravity(Gravity.CENTER, 0, 70);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void showToast(Context context, CharSequence text, int time) {
        // 初始化一个新的Toast对象
        initToast(context, text);

        // 设置显示时长
        if (time==Toast.LENGTH_LONG){
            mytoast.setDuration(Toast.LENGTH_LONG);
        }
        else{
            mytoast.setDuration(Toast.LENGTH_SHORT);
        }
        //toast_img.setBackgroundResource(R.drawable.toastpic);
        //toast_img.setVisibility(View.VISIBLE);

        mytoast.show();
    }
    public static void showText(Context context, CharSequence text) {
        if (text.toString()=="请输入数据"||text.toString()=="请勿同时勾选和手动填写"){
            showToast(context, text, Toast.LENGTH_SHORT);
        }else {
            showToast(context, text, Toast.LENGTH_LONG);
        }

    }



}
