package com.cqgas.gasmeter.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.cqgas.gasmeter.R;
import com.cqgas.gasmeter.adapter.UserMeterBaseAdapter;
import com.cqgas.gasmeter.center.ReadMeterCenter;

/**
 * Created by 国耀 on 2015/11/28.
 */
public class ReadMeterFragment extends BasePageFragment {

    private View mRootView;
    private ListView mListView;
    private UserMeterBaseAdapter mAdaper;
    private MenuItem mFilterItem;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.read_meter_fragment,container,false);
        mRootView = view;
        getActivity().setTitle(R.string.user_list);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        ActionBar bar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if(bar != null) {
            bar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initView(View rootView){
        mListView = (ListView)rootView.findViewById(R.id.user_list);
        mAdaper = new UserMeterBaseAdapter(getActivity(), ReadMeterCenter.getUiAll());
        mListView.setAdapter(mAdaper);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getActivity().getMenuInflater().inflate(R.menu.read_meter, menu);
        mFilterItem = menu.findItem(R.id.read_meter_filter_unread);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                getActivity().finish();
                break;
            case R.id.read_meter_filter_unread:
                if(mFilterItem.isChecked()){
                    mAdaper.clear();
                    mAdaper.addAll(ReadMeterCenter.getUiAll());
                }else{
                    mAdaper.clear();
                    mAdaper.addAll(ReadMeterCenter.getUiUnRead());
                }
                mFilterItem.setChecked(!mFilterItem.isChecked());
                break;
        }
        return true;
    }
}
