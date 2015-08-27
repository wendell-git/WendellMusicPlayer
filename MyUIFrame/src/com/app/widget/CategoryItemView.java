package com.app.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.app.myuiframe.R;

public class CategoryItemView extends LinearLayout{

	public CategoryItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public void initView(Context context){
		LayoutInflater.from(context).inflate(R.layout.category_item, this, true);
	}
}
