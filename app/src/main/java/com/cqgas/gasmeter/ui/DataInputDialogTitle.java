package com.cqgas.gasmeter.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.cqgas.gasmeter.R;

/**
 * Created by 国耀 on 2015/12/5.
 */
public class DataInputDialogTitle extends Dialog {

    public interface DialogOnClickListener{
        void onClick(String v);
    }

    private DialogOnClickListener listener;
    private EditText editText;
    private String title;
    public DataInputDialogTitle(Context context, DialogOnClickListener onPositiveClickListener, String title){
        super(context);
        setContentView(R.layout.input_dialog_title);
        listener = onPositiveClickListener;
        this.title = title;
    }
    public String getInputMsg(){
        return editText.getText().toString();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(title);
        editText = (EditText)findViewById(R.id.input_dialog_edit);
        //editText.setText(value);
        findViewById(R.id.input_dialog_sumbit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(getInputMsg());
                dismiss();
            }
        });
        findViewById(R.id.input_dialog_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    if (listener != null) {
                        listener.onClick(getInputMsg());
                        dismiss();
                    }
                }
                return false;
            }
        });
        editText.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager inputManager =
                        (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(editText, 0);
            }
        },200);
    }
}
