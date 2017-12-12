package com.market.view;

import java.util.ArrayList;

import com.haoyikuai.shop.android.R;
import com.market.bean.SkuBean;
import com.market.bean.Sku_list;
import com.market.bean.Spec;
import com.market.bean.ValueBean;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.view.View.OnClickListener;;
/**
 * 商品规格列表布局
 * 
 * @author pumkid
 *
 */
public class GoodsSpecChoosedLayout extends ViewGroup implements OnClickListener{
	private Context context;
	private Spec spec;
	private Sku_list sku_list;
	private ValueBean specValues[];
	private ArrayList<TextView> tv_specs;
	private int windowsWidth;
	private int windowsHeight;
	private float PADDINGWALL = 25f;
	private int und = 0;
	private int lnr = 0;
	private int WINDOWS_WIDTH;
	private int WINDOWS_HEIGHT;
	private OnSpecItemSelcetListener onSpecItemSelcetListener;
	private final int BASEIDLINE = 2000000;
	private int currentSelectedId = 0;
	private View currentSelectedView = null;
	private int paddingLeft;
	private int paddingTop;
	private int paddingRight;
	private int paddingBottom;
	private int totalArryLength;
	private int viewPosition = -1;
	private Long initSpecID;
	private String keys;
	
	
	private boolean getIsSelected(String keys, Long id, Long specId)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(specId);
		sb.append(":");
		sb.append(id);
		String matcher = sb.toString();
		return keys.contains(matcher);
	}

	private ArrayList<SpecWindowsProfile> mspecWindowsProfiles;

	public GoodsSpecChoosedLayout(Context context) {
		super(context);
		init(context, null);
	}

	public GoodsSpecChoosedLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public GoodsSpecChoosedLayout(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs);

	}
	
	/**
	 * 这方法必需要调用
	 * @param spec
	 * @param keys
	 * @param sku_list
	 * @param onSpecItemSelcetListener
	 */
	public void setDatas(Spec spec, String keys ,Sku_list sku_list ,OnSpecItemSelcetListener onSpecItemSelcetListener) {
		this.removeAllViews();
		this.keys = keys;
		this.sku_list = sku_list;
		if(spec == null)
			return;
		this.spec = spec;
		this.onSpecItemSelcetListener = onSpecItemSelcetListener;
		ArrayList<SkuBean> skuBean_flited = fliterNoStore(sku_list,keys);
		this.specValues = spec.getValue();
		if (this.specValues != null) {
			tv_specs = new ArrayList<TextView>();
			LayoutParams lap = new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
			for (int i = 0; i < specValues.length; i++) {
				TextView spectemp = new TextView(context);
				spectemp.setId(BASEIDLINE+i);
				spectemp.setText(specValues[i].getName());
				spectemp.setTag(specValues[i].getSpec_id());
				spectemp.setTextSize(14);
				spectemp.setTextColor(Color.BLACK);
				spectemp.setOnClickListener(this);
				boolean isClickable = checkClickAble(specValues[i],skuBean_flited);
				spectemp.setEnabled(isClickable);
				
				
				boolean isselected = getIsSelected(this.keys, specValues[i].getSpec_value_id(), specValues[i].getSpec_id());
				if(isselected)
				{
					currentSelectedId = spectemp.getId();
					spectemp.setBackgroundResource(R.drawable.bg_common_btn_empty);
					currentSelectedView = spectemp;
					if(onSpecItemSelcetListener != null)
					onSpecItemSelcetListener.OnSpecItemInit(currentSelectedView, spec,
							specValues[spectemp.getId()-BASEIDLINE], spectemp.getId()-BASEIDLINE,
							totalArryLength, this.viewPosition);
				}
				else if(!isClickable)
					spectemp.setBackgroundResource(R.drawable.bg_common_bgonly_empty);
				else
					spectemp.setBackgroundResource(R.drawable.bg_common_btn_empty_gray);
				tv_specs.add(spectemp);
				this.addView(spectemp, lap);
			}
			
		}
	}
	
	/**
	 * 过滤没有库存的选项
	 * @param sku_list
	 * @param keys
	 * @return
	 */
	private ArrayList<SkuBean> fliterNoStore(Sku_list sku_list, String  keys)
	{
//		Log.d("pumkid", "keys "+keys);
		ArrayList<SkuBean> skuBeanlist = new ArrayList<SkuBean>();
		SkuBean[] skuBeans = null;
		if(sku_list != null)
			skuBeans  = sku_list.getSku_list();
		
		if(skuBeans != null)
			for(int i = 0 ; i < skuBeans.length ; i++)
				if(matchKeys(skuBeans[i].getSpec_key(),keys) && skuBeans[i].getStore_num() > 0)
					skuBeanlist.add(skuBeans[i]);
		return skuBeanlist;
	}
	
	/**
	 * 匹配在选项下其他选项
	 * @param spec_key
	 * @param keys
	 * @return
	 */
	private boolean matchKeys(String spec_key, String keys)
	{
		int countIndex = 0;
		int countMatch = 0;
		String[] keyarr = keys.split(";");
		for(String item : keyarr)
			if(item.length() > 2)
			{
				String[] getSpecId= item.split(":");
				if(!getSpecId[0].contains(this.spec.getSpec_id()+""))
				countIndex++;
				if(spec_key.contains(item))
				countMatch++;
			}
		if(countIndex == countMatch)
			return true;
		return false;
	}
	
	/**
	 * 检查它是否可选
	 * @param valueBean
	 * @param skuBean_flited
	 * @return
	 */
	private boolean checkClickAble(ValueBean valueBean,ArrayList<SkuBean> skuBean_flited)
	{
		if(skuBean_flited != null)
			for(int i = 0 ; i < skuBean_flited.size() ; i++)
			{
				SkuBean bean = skuBean_flited.get(i);
				if(getIsSelected(bean.getSpec_key(), valueBean.getSpec_value_id(), valueBean.getSpec_id()))
					return true;
			}
		return false;
	}

	public void setCurrentSelectedId(int currentSelectedId)
	{
		this.currentSelectedId = currentSelectedId;
	}
	
	public void setItemShell(int upanddown, int leftandright)
	{
		und = upanddown;
		lnr = leftandright;
	}
	
	public void setItemPadding(int paddingLeft , int paddingTop, int paddingRight, int paddingBottom)
	{
		this.paddingLeft = paddingLeft;
		this.paddingTop = paddingTop;
		this.paddingRight = paddingRight;
		this.paddingBottom = paddingBottom;
	}
	

	private void init(Context context, AttributeSet attrs) {
		this.context = context;
		WindowManager wm = (WindowManager) getContext().getSystemService(
				Context.WINDOW_SERVICE);

		windowsWidth = wm.getDefaultDisplay().getWidth();
		windowsHeight = wm.getDefaultDisplay().getHeight();
		WINDOWS_WIDTH = (int) (windowsWidth - 2 * PADDINGWALL);
		WINDOWS_HEIGHT = windowsHeight;
	}

	private int mwindowsWidth;
	private int mwindowsHeight;

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		mwindowsWidth = this.getMeasuredWidth();
		mwindowsHeight = this.getMeasuredHeight();
		if(MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY)
			WINDOWS_WIDTH = mwindowsWidth;
		if(MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY)
			WINDOWS_HEIGHT = mwindowsHeight;
		
		int childrenCount = this.getChildCount();
		mspecWindowsProfiles = new ArrayList<GoodsSpecChoosedLayout.SpecWindowsProfile>();
		for (int i = 0; i < childrenCount; i++) {
			TextView vspec = (TextView) this.getChildAt(i);
			vspec.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
			vspec.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
			
			if(vspec.getWidth() > WINDOWS_WIDTH)
			{	
				vspec.setWidth(WINDOWS_WIDTH);
			}
			mspecWindowsProfiles.add(new SpecWindowsProfile(vspec
					.getMeasuredWidth(), vspec.getMeasuredHeight()));
			
		}

		if (widthMeasureSpec == MeasureSpec.UNSPECIFIED) {
			
			int totalWidth = 0;
			for (int i = 0; i < mspecWindowsProfiles.size(); i++) {
				SpecWindowsProfile smp = mspecWindowsProfiles.get(i);
				if (smp.getmSpecValueWindowWidth() < WINDOWS_WIDTH)
					totalWidth = Math.max(totalWidth,
							smp.getmSpecValueWindowWidth());
				else {
					totalWidth = Math.max(totalWidth, WINDOWS_WIDTH);
					View vspec = this.getChildAt(i);
					vspec.measure(totalWidth, heightMeasureSpec);
					smp.setmSpecValueWindowWidth(totalWidth);
				}

			}
			mwindowsWidth = totalWidth;
		} else {
			mwindowsWidth = WINDOWS_WIDTH;
		}
		
		if (heightMeasureSpec == MeasureSpec.UNSPECIFIED) {
			int totalHeight = 0;
			int allWidth = 0;
			int sizeofmspecWindowsProfiles = mspecWindowsProfiles.size();
			for (int i = 0; i < mspecWindowsProfiles.size(); i++)
			{
				allWidth += mspecWindowsProfiles.get(i).getmSpecValueWindowWidth();
				if(allWidth > mwindowsWidth)
				{
					if((i-1) > 0)
					totalHeight += mspecWindowsProfiles.get(i-1).getmSpecValueWindowHeight();
				}
			}
			if((sizeofmspecWindowsProfiles-1)>0 && (sizeofmspecWindowsProfiles-1)<mspecWindowsProfiles.size())
			totalHeight += mspecWindowsProfiles.get(sizeofmspecWindowsProfiles-1).getmSpecValueWindowHeight();
			mwindowsHeight = totalHeight;
		}
		else
		{
			mwindowsHeight = WINDOWS_HEIGHT;
		}
		
		this.setMeasuredDimension(mwindowsWidth, mwindowsHeight+50);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		int width = (right - left);
		int mTotalHeight = 0;
		int mTotalWidth = 0;
		int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
			View childView = getChildAt(i);

			int measureHeight ;
			int measuredWidth ;
			measuredWidth = mspecWindowsProfiles .get(i).getmSpecValueWindowWidth();
			measureHeight = mspecWindowsProfiles .get(i).getmSpecValueWindowHeight();

			if((measuredWidth + mTotalWidth) > width)
			{
				mTotalHeight += measureHeight+this.und;
				mTotalWidth = 0;
			}
			
			childView.layout(mTotalWidth, mTotalHeight, mTotalWidth
					+ measuredWidth, mTotalHeight + measureHeight);
			
			mTotalWidth += measuredWidth+this.lnr;

		}
	}

	private int indexWidth = 0;
	private int indexHeight = 0;
	private final int LEFTOFWIDGET = 0;
	private final int TOPOFWIDGET = 1;
	private final int RIGHTOFWIDGET = 2;
	private final int BOTTOMOFWIDGET = 3;
	private int currLineMaxHeight = 0;

	public int[] getLayoutSide(SpecWindowsProfile mspecWindowsProfiles,
			final int WINDOWS_WIDTH, final int WINDOWS_HEIGHT) {
		int[] layoutparams = new int[4];

		layoutparams[LEFTOFWIDGET] = indexWidth;
		layoutparams[TOPOFWIDGET] = indexHeight;

		layoutparams[RIGHTOFWIDGET] = indexWidth
				+ mspecWindowsProfiles.getmSpecValueWindowWidth();
		layoutparams[BOTTOMOFWIDGET] = indexHeight
				+ mspecWindowsProfiles.getmSpecValueWindowHeight();

		if (layoutparams[RIGHTOFWIDGET] > WINDOWS_WIDTH) {
			indexWidth = 0;
			layoutparams[RIGHTOFWIDGET] = indexWidth
					+ mspecWindowsProfiles.getmSpecValueWindowWidth();
			indexHeight += currLineMaxHeight;
			layoutparams[BOTTOMOFWIDGET] = indexHeight
					+ mspecWindowsProfiles.getmSpecValueWindowHeight();
		}
		currLineMaxHeight = Math.max(currLineMaxHeight,
				mspecWindowsProfiles.getmSpecValueWindowHeight());
		indexWidth = layoutparams[RIGHTOFWIDGET];

		return layoutparams;
	}

	class SpecWindowsProfile {
		private int mSpecValueWindowWidth;
		private int mSpecValueWindowHeight;

		SpecWindowsProfile(int mSpecValueWindowWidth, int mSpecValueWindowHeight) {
			this.mSpecValueWindowHeight = mSpecValueWindowHeight;
			this.mSpecValueWindowWidth = mSpecValueWindowWidth;
		}

		public int getmSpecValueWindowWidth() {
			return mSpecValueWindowWidth;
		}

		public int getmSpecValueWindowHeight() {
			return mSpecValueWindowHeight;
		}

		public void setmSpecValueWindowWidth(int mSpecValueWindowWidth) {
			this.mSpecValueWindowWidth = mSpecValueWindowWidth+lnr;
		}

		public void setmSpecValueWindowHeight(int mSpecValueWindowHeight) {
			this.mSpecValueWindowHeight = mSpecValueWindowHeight+und;
		}

	}
	
	public int getTotalArryLength() {
		return totalArryLength;
	}

	public void setViewPosition(int viewPosition) {
		this.viewPosition = viewPosition;
	}

	
	public void setTotalArryLength(int totalArryLength) {
		this.totalArryLength = totalArryLength;
	}

	public interface OnSpecItemSelcetListener
	{
		/**
		 * 
		 * @param view the item view
		 * @param valueposition "value" data in Spec
		 * @param position when it's positive that means adding a spec condition,and when it's negative that means eliminating one
		 * @param totalArryLength size of spceList
		 * @param viewPosition the position of the view
		 */
		public void OnSpecItemSelceted(View view,Spec spec, ValueBean valueposition, int position, int totalArryLength, int viewPosition);
		public void OnSpecItemInit(View view,Spec spec, ValueBean valueposition, int position, int totalArryLength, int viewPosition);
	}

	public OnSpecItemSelcetListener getOnSpecItemSelcetListener() {
		return onSpecItemSelcetListener;
	}

	public void setOnSpecItemSelcetListener(
			OnSpecItemSelcetListener onSpecItemSelcetListener) {
		this.onSpecItemSelcetListener = onSpecItemSelcetListener;
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		ClickItems(view);
	}
	public Long getInitSpecID() {
		return initSpecID;
	}

	public void setInitSpecID(Long initSpecID) {
		this.initSpecID = initSpecID;
	}

	private void ClickItems(View view )
	{
		int viewId = view.getId();
		if(viewId > 0 && viewId == currentSelectedId)
		{
			currentSelectedId = 0;
			currentSelectedView.setBackgroundResource(R.drawable.bg_common_btn_empty_gray);
			currentSelectedView = null;
			if(onSpecItemSelcetListener != null)
				onSpecItemSelcetListener.OnSpecItemSelceted(view,this.spec, specValues[viewId-BASEIDLINE],-1,totalArryLength, this.viewPosition);
			return;
		}
		if(viewId >= BASEIDLINE && viewId <(BASEIDLINE+specValues.length))
		{
			if(currentSelectedView != null)
			currentSelectedView.setBackgroundResource(R.drawable.bg_common_btn_empty_gray);
			if(onSpecItemSelcetListener != null)
				onSpecItemSelcetListener.OnSpecItemSelceted(view,this.spec, specValues[viewId-BASEIDLINE],viewId-BASEIDLINE,totalArryLength, this.viewPosition);
			view.setBackgroundResource(R.drawable.bg_common_btn_empty);
			currentSelectedId = viewId;
			currentSelectedView = view;
		}
	}
	
	
}
