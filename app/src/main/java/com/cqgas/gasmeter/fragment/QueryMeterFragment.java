package com.cqgas.gasmeter.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import com.cqgas.gasmeter.R;
import com.cqgas.gasmeter.center.MetersShowCenter;

import java.util.Calendar;

/**
 * Created by 国耀 on 2015/11/29.
 */
public class QueryMeterFragment extends Fragment implements View.OnClickListener{
    private ListView mDetailsList;
    private EditText mDateBegin;
    private EditText mDateEnd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.query_meter_fragment,container,false);
        getActivity().setTitle(R.string.meter_statistics);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }
    private void initView(View v){
        mDetailsList = (ListView) v.findViewById(R.id.meter_details_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1, MetersShowCenter.getUiMeterDetails());
        mDetailsList.setAdapter(adapter);

        mDateBegin = (EditText) v.findViewById(R.id.date_begin);
        mDateBegin.setOnClickListener(this);

        mDateEnd = (EditText) v.findViewById(R.id.date_end);
        mDateEnd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.date_begin:
                getDatePicked(mDateBegin);
                break;
            case R.id.date_end:
                getDatePicked(mDateEnd);
                break;
        }
    }

    public void getDatePicked(final EditText v){
        Calendar d = Calendar.getInstance();
        int year = d.get(Calendar.YEAR);
        int month = d.get(Calendar.MONTH);
        int day = d.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(getActivity(),new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                v.setText(String.format("%04d-%02d-%02d",year,monthOfYear,dayOfMonth));
            }
        },year,month,day);
        dialog.show();
    }
}
