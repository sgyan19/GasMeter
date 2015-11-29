package com.cqgas.gasmeter.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cqgas.gasmeter.R;
import com.cqgas.gasmeter.center.MetersShowCenter;

/**
 * Created by 国耀 on 2015/11/29.
 */
public class QueryMeterFragment extends Fragment {
    private ListView mDetailsList;
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
    }
}
