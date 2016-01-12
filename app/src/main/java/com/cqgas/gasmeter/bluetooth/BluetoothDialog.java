package com.cqgas.gasmeter.bluetooth;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.cqgas.gasmeter.R;
import com.cqgas.gasmeter.center.BluetoothCenter;
import com.cqgas.gasmeter.task.ProgressDialogTask;

/**
 * Created by 国耀 on 2015/12/20.
 */
public class BluetoothDialog extends DialogFragment implements View.OnClickListener {
    EditText mDevicesEdit;
    Button mConnect;
    Button mDisConnect;
    String mDefaultDevice;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bluetooth_dialog_fragment, container, false);
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDevicesEdit = (EditText)view.findViewById(R.id.input_dialog_edit);
        mConnect = (Button) view.findViewById(R.id.input_dialog_sumbit);
        mDisConnect = (Button) view.findViewById(R.id.input_dialog_cancel);
        mConnect.setOnClickListener(this);
        mDisConnect.setOnClickListener(this);
        updateUi();
    }

    private void updateUi(){
        mDevicesEdit.setText(BluetoothCenter.getUiDevicesName());
        if(BluetoothCenter.isConnect()){
            mConnect.setEnabled(false);
            mDisConnect.setEnabled(true);
            mDevicesEdit.setEnabled(false);
            getDialog().setTitle(getActivity().getResources().getString(R.string.bluetooth_title_yes));
        }else{
            mConnect.setEnabled(true);
            mDisConnect.setEnabled(false);
            mDevicesEdit.setEnabled(true);
            getDialog().setTitle(getActivity().getResources().getString(R.string.bluetooth_title_no));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.input_dialog_sumbit:
                new BluetoothTask(true).execute();
                break;
            case R.id.input_dialog_cancel:
                new BluetoothTask(false).execute();
                break;
        }
    }

    private class BluetoothTask extends ProgressDialogTask<Void>{
        private boolean doOrDis;
        public BluetoothTask(boolean doOrDis){
            super(getActivity());
            this.doOrDis = doOrDis;
        }
        @Override
        protected void onPreExecute() throws Exception {
            super.onPreExecute();
            showIndeterminate();
        }
        @Override
        public Void call() throws Exception {
            if(doOrDis){
                BluetoothCenter.connect(mDevicesEdit.getText().toString());
            }else{
                BluetoothCenter.disConnect();
            }
            return null;
        }

        @Override
        protected void onException(Exception e) throws RuntimeException {
            super.onException(e);
            updateUi();
            Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_SHORT).show();
        }
        @Override
        protected void onSuccess(Void aVoid) throws Exception {
            super.onSuccess(aVoid);
            updateUi();
            String info = (doOrDis?"连接":"断开") + "成功";
            Toast.makeText(getActivity(),info,Toast.LENGTH_SHORT).show();
            dismiss();
        }
    }
}
