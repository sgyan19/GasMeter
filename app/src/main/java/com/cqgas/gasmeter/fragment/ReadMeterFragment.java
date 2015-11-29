package com.cqgas.gasmeter.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.cqgas.gasmeter.R;
import com.cqgas.gasmeter.adapter.UserMeterBaseAdapter;
import com.cqgas.gasmeter.center.MeterReadingCenter;

/**
 * Created by 国耀 on 2015/11/28.
 */
public class ReadMeterFragment extends Fragment {

    private View mRootView;
    private ListView mListView;
    private UserMeterBaseAdapter mAdaper;
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
    }

    private void initView(View rootView){
        mListView = (ListView)rootView.findViewById(R.id.user_list);
        mAdaper = new UserMeterBaseAdapter(getActivity(), MeterReadingCenter.getDataForUi());
        mListView.setAdapter(mAdaper);
    }
}
