package com.cqgas.gasmeter.fragment;

import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by 国耀 on 2015/12/5.
 */
public abstract class BasePageFragment extends Fragment{


    public abstract boolean onCreateOptionsMenu(Menu menu);

    public abstract boolean onOptionsItemSelected(MenuItem item);

}
