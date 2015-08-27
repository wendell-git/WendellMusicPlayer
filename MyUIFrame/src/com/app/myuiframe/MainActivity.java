package com.app.myuiframe;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.app.myuiframe.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.shizhefei.view.indicator.FragmentListPageAdapter;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.IndicatorViewPager.IndicatorFragmentPagerAdapter;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;

public class MainActivity extends FragmentActivity {
	private IndicatorViewPager indicatorViewPager;
	private LayoutInflater inflate;
	private String[] names = { "我的", "推荐", "发现", "GINGERBREAD", "HONEYCOMB", "ICE CREAM SANDWICH", "JELLY BEAN", "KITKAT" };
	private ScrollIndicatorView indicator;

	private SlidingMenu menu;
	private ListView menuListView;
	private String[] pageNnames = { "界面1", "界面2", "界面3", "界面4" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_moretab);
		ImageView menu_logo = (ImageView) findViewById(R.id.menu_logo);
		menu_logo.setOnClickListener(onClickListener);
		
		ViewPager viewPager = (ViewPager) findViewById(R.id.moretab_viewPager);
		indicator = (ScrollIndicatorView) findViewById(R.id.moretab_indicator);
		indicator.setScrollBar(new ColorBar(this, Color.WHITE, 5));

		// 设置滚动监听
		int selectColorId = R.color.tab_top_text_2;
		int unSelectColorId = R.color.tab_top_text_1;
		indicator.setOnTransitionListener(new OnTransitionTextListener().setColorId(this, selectColorId, unSelectColorId));

		viewPager.setOffscreenPageLimit(2);
		indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
		inflate = LayoutInflater.from(getApplicationContext());
		indicatorViewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));

		// 默认true ，自动布局
		indicator.setSplitAuto(false);
		
		/**
		 * 初始化右边菜单
		 */
		menu = new SlidingMenu(this);
		menu.setMenu(R.layout.left_menu_frame);
		Fragment leftMenuFragment = new MenuLeftFragment();  
        getSupportFragmentManager().beginTransaction()  
                .replace(R.id.id_left_menu_frame, leftMenuFragment).commit(); 
        
		// 滑动 菜单 配置
		menu.setShadowWidthRes(R.dimen.ap_base_menu_shadow_width);
		menu.setBehindOffsetRes(R.dimen.ap_base_menu_shadow_offset);
		menu.setShadowDrawable(R.drawable.ap_base_menu_shadow);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		menu.setBehindScrollScale(0.5f);

		
		
		/**
		 * 设置右边菜单项
		 */
//		menuListView = (ListView) menu.getMenu().findViewById(R.id.slideMenu_listView);
//		menuListView.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.item_slidemenu, pageNnames));
//		menuListView.setOnItemClickListener(onMenuItemClickListener);
//		

	}
	private OnClickListener onClickListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.menu_logo:
				menu.showMenu();
				break;
			}
			
		}
		
	};
//	private OnItemClickListener onMenuItemClickListener = new OnItemClickListener() {
//
//		@Override
//		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//			//viewPager.setCurrentItem(position,false);
//			menu.toggle();
//		}
//	};
	private int size = 3;
	private class MyAdapter extends IndicatorFragmentPagerAdapter {

		public MyAdapter(FragmentManager fragmentManager) {
			super(fragmentManager);
		}

		@Override
		public int getCount() {
			return size;
		}

		@Override
		public View getViewForTab(int position, View convertView, ViewGroup container) {
			System.out.println("init View Tab position = "+ position);
			if (convertView == null) {
				convertView = inflate.inflate(R.layout.tab_top, container, false);
			}
			TextView textView = (TextView) convertView;
			textView.setText(names[position % names.length]);
			textView.setTextSize(18);//设定文字大小
			textView.setPadding(20, 0, 20, 0);
			return convertView;
		}

		@Override
		public Fragment getFragmentForPage(int position) {
			
			System.out.println("initFragment in pager position = "+ position);
			MoreFragment fragment = new MoreFragment();
			Bundle bundle = new Bundle();
			bundle.putInt("intent_int_index", position);
			fragment.setArguments(bundle);
			return fragment;
		}

		@Override
		public int getItemPosition(Object object) {
			return FragmentListPageAdapter.POSITION_NONE;
		}

	};

}
