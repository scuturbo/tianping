package com.example.tianping;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputFilter;
import android.widget.Button;
import android.widget.EditText;


public class RightDialog extends Dialog {

    private EditText editText;
    private RightDialog.onYesOnclickListener yesOnclickListener;//确定按钮被点击了的监听器
    private RightDialog.onNoOnclickListener noOnclickListener;//取消按钮被点击了的监听器

    public RightDialog(Context context) {
        super(context);
    }

    public double getSum3() {
        if (!editText.getText().toString().equals("")){
            return Double.parseDouble(editText.getText().toString());
        }else {
            return 0;
        }
    }

    // 设置确定按钮监听
    public void setYesOnclickListener(RightDialog.onYesOnclickListener yesOnclickListener) {
        this.yesOnclickListener = yesOnclickListener;
    }

    // 设置取消按钮监听
    public void setNoOnclickListener(RightDialog.onNoOnclickListener noOnclickListener) {
        this.noOnclickListener = noOnclickListener;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rightdialog);
        //确定按钮
        Button yes = findViewById(R.id.right_yes);
        //取消按钮
        Button no = findViewById(R.id.right_no);
        editText = findViewById(R.id.right_edit);

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

        editText.addTextChangedListener(new DecimalInputTextWatcher(editText, 8, 2));

    }

    public interface onYesOnclickListener {
        void onYesOnclick();
    }

    public interface onNoOnclickListener {
        void onNoOnclick();
    }
}