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
    private Button mCompany;
    private Button mPlace;
    private Button mExit;
    private EditText mQueryCondition;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.query_meter_fragment, container, false);
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
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1, getListViewData(QueryMeterCenter.getUiQuery("")));
        mDetailsList.setAdapter(adapter);

        mQueryCondition = (EditText) v.findViewById(R.id.query_meter_input);
        mCompany = (Button) v.findViewById(R.id.query_meter_active);
        mPlace = (Button) v.findViewById(R.id.query_meter_place);
        mExit = (Button) v.findViewById(R.id.query_meter_exit);

        mCompany.setOnClickListener(this);
        mPlace.setOnClickListener(this);
        mExit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.query_meter_active:
                queryAndShowResult();
                break;
            case R.id.query_meter_place:
                queryAndShowResult();
                break;
            case R.id.query_meter_exit:
                getView().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getActivity().finish();
                    }
                },400);
                break;
        }
    }
    public void queryAndShowResult(){
        new QueryTask(getActivity(),mQueryCondition.getText().toString()).execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getActivity().getMenuInflater().inflate(R.menu.query_meter, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                getActivity().finish();
                break;

        }
        return false;
    }

    public List<String> getListViewData(QueryCore item){
        List<String>  ui = new ArrayList<>();
        if(item != null){
            ui.add(item.getUiUserCount());
            ui.add(item.getUiReadUserCount());
            ui.add(item.getUiUnReadUserCount());
            ui.add(item.getUiReadData());
        }
        return ui;
    }

    private class QueryTask extends ProgressDialogTask<QueryCore> {
        private String query;
        public QueryTask(Context context,String queryCondition){
            super(context);
            query = queryCondition;
        }

        @Override
        protected void onPreExecute() throws Exception {
            super.onPreExecute();
            showIndeterminate();
        }

        @Override
        public QueryCore call() throws Exception {
            return QueryMeterCenter.getUiQuery(query);
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
