/*
 * Copyright 2012 GitHub Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cqgas.gasmeter.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.cqgas.gasmeter.R;


/**
 * Progress dialog in Holo Light theme
 */
public class LightProgressDialog extends Dialog {
    private boolean showAnimation;
	/**
	 * Create progress dialog
	 * 
	 * @param context
	 * @param resId
	 * @return dialog
	 */
	public static Dialog create(Context context, int resId) {
		return create(context, context.getResources().getString(resId));
	}

	/**
	 * Create progress dialog
	 * 
	 * @param context
	 * @param message
	 * @return dialog
	 */
	@SuppressLint("NewApi")
	public static Dialog create(Context context, CharSequence message) {

		Dialog dialog = new LightProgressDialog(context,R.style.dialog);
		dialog.setContentView(R.layout.progress_dialog);

		dialog.setCanceledOnTouchOutside(false);
		dialog.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					return true;
				}
				return false;
			}
		});
		return dialog;
	}

    @SuppressLint("NewApi")
    public static Dialog create(Context context, CharSequence message,boolean showAnimation) {

        LightProgressDialog dialog = new LightProgressDialog(context);
        dialog.showAnimation = showAnimation;
        dialog.setContentView(R.layout.progress_dialog);

        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(DialogInterface dialog, int keyCode,
                                 KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return true;
                }
                return false;
            }
        });
        return dialog;
    }
    private LightProgressDialog(Context context) {
        super(context);
        showAnimation = true;
    }

	private LightProgressDialog(Context context,int theme) {
		super(context,theme);
        showAnimation = true;
	}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!showAnimation) {
            findViewById(R.id.pb_loading).setVisibility(View.GONE);
        }
    }
}
