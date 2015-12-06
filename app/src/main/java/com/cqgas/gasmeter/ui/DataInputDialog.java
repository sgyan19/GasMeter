package com.cqgas.gasmeter.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cqgas.gasmeter.R;

/**
 * Created by 国耀 on 2015/12/5.
 */
public class DataInputDialog extends Dialog {

    public interface DialogOnClickListener{
        void onClick(String v);
    }

    private DialogOnClickListener listener;
    private EditText editText;
    private String value;
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
        editText = (EditText)findViewById(R.id.input_dialog_edit);
        editText.setText(value);
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
    }
}
