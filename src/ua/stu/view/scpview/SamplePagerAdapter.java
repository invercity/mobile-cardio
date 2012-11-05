package ua.stu.view.scpview;

import java.util.List;

import android.app.ActionBar.Tab;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

public class SamplePagerAdapter extends PagerAdapter {

	private List<View> pages = null;
	private static String TAG = "SamplePagerAdapter";
    
    public SamplePagerAdapter(List<View> pages){
        this.pages = pages;
        Log.d(TAG, "SamplePagerAdapter");
    }
    
    @Override
    public Object instantiateItem(View collection, int position){
        View v = pages.get(position);
        ((ViewPager) collection).addView(v, 0);
        
        Log.d(TAG, "instantiateItem");
        
        return v;
    }
    
    @Override
    public void destroyItem(View collection, int position, Object view){
        ((ViewPager) collection).removeView((View) view);
        Log.d(TAG, "destroyItem");
    }
    
    @Override
    public int getCount(){
    	Log.d(TAG, "getCount");
        return pages.size();
    }
    
    @Override
    public boolean isViewFromObject(View view, Object object){
    	Log.d(TAG, "isViewFromObject");
        return view.equals(object);
    }

    @Override
    public void finishUpdate(View arg0){
    	Log.d(TAG, "finishUpdate");
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1){
    	Log.d(TAG, "restoreState");
    }

    @Override
    public Parcelable saveState(){
    	Log.d(TAG, "saveState");
        return null;
    }

    @Override
    public void startUpdate(View arg0){
    	Log.d(TAG, "startUpdate");
    }

}
