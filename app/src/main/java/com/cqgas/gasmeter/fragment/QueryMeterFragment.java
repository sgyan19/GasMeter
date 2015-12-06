package com.cqgas.gasmeter.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.cqgas.gasmeter.R;
import com.cqgas.gasmeter.center.QueryMeterCenter;
import com.cqgas.gasmeter.core.QueryCore;
import com.cqgas.gasmeter.task.ProgressDialogTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by 国耀 on 2015/11/29.
 */
public class QueryMeterFragment extends BasePageFragment implements View.OnClickListener{
    private ListView mDetailsList;
    private EditText mBeginDateEdit;
    private EditText mEndDateEdit;
    private Button mCompany;
    private Button mPlace;
    private Date mBeginDate;
    private Date mEndDate;
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
        ActionBar bar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if(bar != null) {
            bar.setDisplayHomeAsUpEnabled(true);
        }
    }
    private void initView(View v){
        mDetailsList = (ListView) v.findViewById(R.id.meter_details_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1, getListViewData(QueryMeterCenter.getUiQuery(0,0,0)));
        mDetailsList.setAdapter(adapter);

        mBeginDateEdit = (EditText) v.findViewById(R.id.date_begin);
        mBeginDateEdit.setOnClickListener(this);

        mEndDateEdit = (EditText) v.findViewById(R.id.date_end);
        mEndDateEdit.setOnClickListener(this);

        mCompany = (Button) v.findViewById(R.id.query_meter_company);
        mPlace = (Button) v.findViewById(R.id.query_meter_place);

        mCompany.setOnClickListener(this);
        mPlace.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.date_begin:
                getDatePicked(mBeginDateEdit,true);
                break;
            case R.id.date_end:
                getDatePicked(mEndDateEdit,false);
            case R.id.query_meter_company:
                queryAndShowResult(QueryMeterCenter.QUERY_TYPE_COMPANY);
                break;
            case R.id.query_meter_place:
                queryAndShowResult(QueryMeterCenter.QUERY_TYPE_PLACE);
                break;
        }
    }
    public void queryAndShowResult(int type){
        if(mBeginDate == null || mEndDate == null){
            return;
        }
        if(mEndDate.before(mBeginDate)){
            mEndDateEdit.setText("");
            Toast.makeText(getActivity(),"结束日期应在开始日期之后",Toast.LENGTH_SHORT).show();
            return;
        }
        new QueryTask(getActivity(),mBeginDate.getTime(),mEndDate.getTime(),type).execute();

    }

    public void getDatePicked(final EditText v,final boolean begin){
        Calendar d = Calendar.getInstance();
        int year = d.get(Calendar.YEAR);
        final int month = d.get(Calendar.MONTH);
        int day = d.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(getActivity(),new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                v.setText(String.format("%04d-%02d-%02d",year,monthOfYear,dayOfMonth));
                if(begin){
                    mBeginDate = new Date(year,monthOfYear,dayOfMonth,0,0,0);
                }else{
                    mEndDate = new Date(year,monthOfYear,dayOfMonth,0,0,0);
                }
            }
        },year,month,day);
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getActivity().getMenuInflater().inflate(R.menu.query_meter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                getActivity().finish();
                break;

        }
        return true;
    }

    public List<String> getListViewData(QueryCore item){
        List<String>  ui = new ArrayList<>();
        if(item != null){
            ui.add(item.getUiUserCount());
            ui.add(item.getUiReadUserCount());
            ui.add(item.getUiUnReadUserCount());
            ui.add(item.getUiReadData());
            ui.add(item.getUiReadRate());
            ui.add(item.getUiDayAverageUserCount());
            ui.add(item.getUiHourAverageUserCount());
            ui.add(item.getUiReReadCount());
            ui.add(item.getUiReReadProbability());
            ui.add(item.getUiEstimateReadTimes());
            ui.add(item.getUiTorchTimes());
            ui.add(item.getUiLocationTimes());
        }
        return ui;
    }

    private class QueryTask extends ProgressDialogTask<QueryCore> {
        private long begin;
        private long end;
        private int type;
        public QueryTask(Context context,long begin,long end,int type){
            super(context);
            this.begin = begin;
            this.end = end;
            this.type = type;
        }

        @Override
        protected void onPreExecute() throws Exception {
            super.onPreExecute();
            showIndeterminate();
        }

        @Override
        public QueryCore call() throws Exception {
            return QueryMeterCenter.getUiQuery(type,begin,end);
        }

        @Override
        protected void onSuccess(QueryCore queryCore) throws Exception {
            super.onSuccess(queryCore);
            ListAdapter adapter = mDetailsList.getAdapter();
            if(adapter instanceof ArrayList<?>) {
                ((ArrayList<String>)adapter).clear();
                ((ArrayList<String>)adapter).addAll(getListViewData(queryCore));
            }
        }
    }
}
