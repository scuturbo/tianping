package com.example.tianping;

import static com.example.tianping.R.drawable.showleft;
import static com.example.tianping.R.drawable.showright;

import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;


import android.util.SparseArray;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private int ByqCount;
    private double XlCount;
    private TextView left_text;
    private TextView right_text;
    private boolean isExit = false;
    private AnimationDrawable animationDrawable;
    public List<ValueBean>list = new ArrayList<>();
    public ValueAdapter valueAdapter;
    public SparseArray<ValueBean>selectedList = new SparseArray<>();
    public LeftDialog dialog;
    public RightDialog right_dialog;
    private final String[] StrArr={"30kVA","50kVA","80kVA","100kVA","125kVA","160kVA","200kVA","250kVA","315kVA","400kVA","500kVA","630kVA","800kVA","1000kVA","1250kVA","1600kVA","2000kVA","2500kVA"};

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();

        Button button = findViewById(R.id.button);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        left_text = findViewById(R.id.left_text);
        right_text = findViewById(R.id.right_text);
        ImageView imageView = findViewById(R.id.img_show);

        //左边按钮
        button.setOnClickListener(view -> ShowLeftDialogByq());

        //右边按钮
        button2.setOnClickListener(view -> showTextChoiceDialogXl());

        //计算按钮
        button3.setOnClickListener(view -> {
            if (ByqCount!=0&&XlCount!=0){
                double left_sum = ByqCount*27.5;
                double right_sum = XlCount*34;

                if (left_sum<right_sum){
                    imageView.setBackground(getResources().getDrawable(showright,null));
                    animationDrawable = (AnimationDrawable) imageView.getBackground();
                    animationDrawable.start();
                    Mytoast.showText(MainActivity.this,"选择变压器容量计算基本电费用电成本较低");
                    //Toast.makeText(MainActivity.this,"选择变压器容量",Toast.LENGTH_SHORT).show();
                }
                else if(left_sum>right_sum){
                    imageView.setBackground(getResources().getDrawable(showleft,null));
                    animationDrawable = (AnimationDrawable) imageView.getBackground();
                    animationDrawable.start();
                    Mytoast.showText(MainActivity.this,"选择最大需量计算基本电费用电成本较低");
                    //Toast.makeText(MainActivity.this,"选择实际最大需量",Toast.LENGTH_SHORT).show();
                } else {
                    Mytoast.showText(MainActivity.this,"选择变压器容量或最大需量均可");
                    //Toast.makeText(MainActivity.this,"两者相等",Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Mytoast.showText(MainActivity.this,"请输入数据");
                //Toast.makeText(MainActivity.this,"请输入数据",Toast.LENGTH_SHORT).show();
            }
        });

        button4.setOnClickListener(view -> {
            ByqCount=0;
            XlCount=0;
            left_text.setText("");
            right_text.setText("");
            if (animationDrawable !=null){
                if (animationDrawable.isRunning()){
                    animationDrawable.stop();
                    imageView.setBackground(getResources().getDrawable(R.drawable.mid,null));
                }
            }
        });

    }
    private void  initData(){
        for(int i =0;i<18;i++ ){
            int index = StrArr[i].indexOf("k");
            int volume = Integer.parseInt(StrArr[i].substring(0,index));
            ValueBean valuebean = new ValueBean();
            valuebean.setProduct_id(i);
            valuebean.setTitle(StrArr[i]);
            valuebean.setNum(volume);
            valuebean.setCount(0);
            list.add(valuebean);
        }
        valueAdapter = new ValueAdapter(this,list,selectedList);
    }

    //左边对话框
    @SuppressLint("SetTextI18n")
    private void ShowLeftDialogByq(){
        dialog = new LeftDialog(MainActivity.this,valueAdapter);
        dialog.setYesOnclickListener(() -> {
          if(dialog.getSum1()>0&&dialog.getSum2()>0){
              Mytoast.showText(MainActivity.this,"请勿同时勾选和手动填写");
            //Toast.makeText(MainActivity.this,"请勿同时勾选和手动填写",Toast.LENGTH_SHORT).show();
          }else if (dialog.getSum1()>0&&dialog.getSum2()==0) {
              ByqCount = dialog.getSum1();
              left_text.setText(ByqCount +"kVA");
              //Toast.makeText(MainActivity.this, ByqCount +"kVA",Toast.LENGTH_SHORT).show();
              dialog.update(0);
              dialog.dismiss();
          }else if(dialog.getSum1()==0&&dialog.getSum2()>0){
              ByqCount = dialog.getSum2();
              left_text.setText(ByqCount +"kVA");
              //Toast.makeText(MainActivity.this, ByqCount +"kVA",Toast.LENGTH_SHORT).show();
              dialog.update(0);
              dialog.dismiss();
          }
          else if(dialog.getSum1()==0&&dialog.getSum2()==0){
              Mytoast.showText(MainActivity.this,"请输入数据");
              //Toast.makeText(MainActivity.this,"请输入数据!!!",Toast.LENGTH_SHORT).show();
          }
          else {
              dialog.update(0);
              dialog.dismiss();
          }
        });
        dialog.setNoOnclickListener(() -> {
            dialog.update(0);
            dialog.dismiss();

        });
        dialog.show();
        Window dialogWindow = dialog.getWindow();
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = dialogWindow.getAttributes();
        p.height = (int)(d.getHeight()*0.85);
        p.width = (int)(d.getWidth()*0.85);
        p.gravity = Gravity.CENTER;
        dialogWindow.setAttributes(p);
    }

    //右边对话框
    private void showTextChoiceDialogXl(){
        right_dialog = new RightDialog(MainActivity.this);
        right_dialog.setYesOnclickListener(() -> {
            if (right_dialog.getSum3()==0){
                Mytoast.showText(MainActivity.this,"请输入数据");
                //Toast.makeText(MainActivity.this,"请输入数据",Toast.LENGTH_SHORT).show();
            }else {
                XlCount = right_dialog.getSum3();
                right_text.setText(XlCount+"kW");
                //Toast.makeText(MainActivity.this,XlCount+"",Toast.LENGTH_SHORT).show();
                right_dialog.dismiss();
            }
        });
        right_dialog.setNoOnclickListener(() ->{
            right_dialog.dismiss();
        });
        right_dialog.setNoOnclickListener(() -> right_dialog.dismiss());
        right_dialog.show();
        Window dialogWindow = right_dialog.getWindow();
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = dialogWindow.getAttributes();
        p.height = (int)(d.getHeight()*0.25);
        p.width = (int)(d.getWidth()*0.7);
        p.gravity = Gravity.CENTER;
        dialogWindow.setAttributes(p);

    }

    public boolean onKeyDown(int keycode, KeyEvent event){
        if (keycode == KeyEvent.KEYCODE_BACK){ //当返回按键被按下
            exit();
        }
        return false;
    }

    //声明一个定时器
    public void exit(){
        Timer timer;
        if (!isExit){ //如果isExit为false,执行下面代码
            isExit = true;   //改变值为true
            Toast.makeText(this,"再按一次退出",Toast.LENGTH_SHORT).show();
            timer = new Timer();   //得到定时器对象
            //执行定时任务,两秒内如果没有再次按下,调用run,把isExit值为false。如果按下了，就重新调用了onKeyDown。
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false;
                }
            },2000);
        } else { //如果两秒内再次按下了返回键,这时isExit的值已经在第一次按下时赋值为true了,因此不会进入if后的代码,直接执行下面的代码
            this.finish();
            System.exit(0);
        }
    }

    public void handlerCarNum(int type, ValueBean valueBean){
        if (type == 0) {
            ValueBean temp = selectedList.get(valueBean.getProduct_id());
            if(temp!=null){
                if(temp.getCount()<2){
                    valueBean.setCount(0);
                    selectedList.remove(valueBean.getProduct_id());

                }else{
                    int i =  valueBean.getCount();
                    valueBean.setCount(--i);
                }
            }

        } else if (type == 1) {
            ValueBean temp = selectedList.get(valueBean.getProduct_id());
            if(temp==null){
                valueBean.setCount(1);
                selectedList.append(valueBean.getProduct_id(),valueBean);
            }else{
                int i= valueBean.getCount();
                valueBean.setCount(++i);
            }
        }
        if(valueAdapter!=null){
            valueAdapter.notifyDataSetChanged();
        }
        dialog.update(1);
    }

}


