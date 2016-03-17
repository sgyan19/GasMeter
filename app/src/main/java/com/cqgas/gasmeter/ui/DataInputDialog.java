package com.cqgas.gasmeter.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.cqgas.gasmeter.R;

/**
 * Created by 国耀 on 2015/12/5.
 */
public class DataInputDialog extends Dialog {

    public interface DialogOnClickListener{
        boolean onClick(String v,boolean isClickTwice,TextView textView);
    }

    private DialogOnClickListener listener;
    private EditText editText;
    private TextView messgeTip;
    private String value;
    private boolean clickTwice = false;
    public DataInputDialog(Context context,DialogOnClickListener onPositiveClickListener,String value){
        super(context);
        setContentView(R.layout.input_dialog);
        listener = onPositiveClickListener;
        this.value = value;
    }
    public String getInputMsg(){
        return editText.getText().toString();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getContext().getResources().getString(R.string.input_dialog_title));
        messgeTip = (TextView)findViewById(R.id.input_dialog_message);
        editText = (EditText)findViewById(R.id.input_dialog_edit);
        //editText.setText(value);
        findViewById(R.id.input_dialog_sumbit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener.onClick(getInputMsg(),clickTwice,messgeTip))
                    dismiss();
                clickTwice = true;
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
                        if(listener.onClick(getInputMsg(),clickTwice,messgeTip))
                            dismiss();
                        clickTwice = true;
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
