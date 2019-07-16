package com.example.yangyang.mvpproject.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.example.yangyang.mvpproject.R;


public class ProgressDialog extends Dialog {

    private TextView textView;

    private String message;
    public ProgressDialog(@NonNull Context context) {
        super(context);
        init();

    }

    private void init(){

        setCanceledOnTouchOutside(false); //外部点击不会消失

        setContentView(R.layout.dialog_progress);

        textView = findViewById(R.id.dialog_progress_text);

    }

    public void setMessage(String message) {
        textView.setText(message.trim());
    }
}
