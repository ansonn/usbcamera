package com.market.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * ����ඨ����Header��Footer�Ĺ�ͨ��Ϊ

 */
public abstract class LoadingLayout extends FrameLayout implements ILoadingLayout {
    
    /**��������*/
    private View mContainer;
    /**��ǰ��״̬*/
    private State mCurState = State.NONE;
    /**ǰһ��״̬*/
    private State mPreState = State.NONE;
    
    /**
     * ���췽��
     * 
     * @param context context
     */
    public LoadingLayout(Context context) {
        this(context, null);
    }
    
    /**
     * ���췽��
     * 
     * @param context context
     * @param attrs attrs
     */
    public LoadingLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    
    /**
     * ���췽��
     * 
     * @param context context
     * @param attrs attrs
     * @param defStyle defStyle
     */
    public LoadingLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        
        init(context, attrs);
    }
    
    /**
     * ��ʼ��
     * 
     * @param context context
     * @param attrs attrs
     */
    protected void init(Context context, AttributeSet attrs) {
        mContainer = createLoadingView(context, attrs);
        if (null == mContainer) {
            throw new NullPointerException("Loading view can not be null.");
        }
        
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, 
                LayoutParams.WRAP_CONTENT);
        addView(mContainer, params);
    }

    /**
     * ��ʾ�������������
     * 
     * @param show flag
     */
    public void show(boolean show) {
        // If is showing, do nothing.
        if (show == (View.VISIBLE == getVisibility())) {
            return;
        }
        
        ViewGroup.LayoutParams params = mContainer.getLayoutParams();
        if (null != params) {
            if (show) {
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            } else {
                params.height = 0;
            }
            setVisibility(show ? View.VISIBLE : View.INVISIBLE);
        }
    }
    
    /**
     * ���������µ�ʱ���ı�
     * 
     * @param label �ı�
     */
    public void setLastUpdatedLabel(CharSequence label) {
        
    }
    
    /**
     * ���ü����е�ͼƬ
     * 
     * @param drawable ͼƬ
     */
    public void setLoadingDrawable(Drawable drawable) {
        
    }

    /**
     * �����������ı������͵��ǡ���������ˢ�¡�
     * 
     * @param pullLabel �������ı�
     */
    public void setPullLabel(CharSequence pullLabel) {
        
    }

    /**
     * ��������ˢ�µ��ı������͵��ǡ�����ˢ�¡�
     * 
     * @param refreshingLabel ˢ���ı�
     */
    public void setRefreshingLabel(CharSequence refreshingLabel) {
        
    }

    /**
     * �����ͷŵ��ı������͵��ǡ��ɿ�����ˢ�¡�
     * 
     * @param releaseLabel �ͷ��ı�
     */
    public void setReleaseLabel(CharSequence releaseLabel) {
        
    }

    @Override
    public void setState(State state) {
        if (mCurState != state) {
            mPreState = mCurState;
            mCurState = state;
            onStateChanged(state, mPreState);
        }
    }
    
    @Override
    public State getState() {
        return mCurState;
    }

    @Override
    public void onPull(float scale) {
        
    }
    
    /**
     * �õ�ǰһ��״̬
     * 
     * @return ״̬
     */
    protected State getPreState() {
        return mPreState;
    }
    
    /**
     * ��״̬�ı�ʱ����
     * 
     * @param curState ��ǰ״̬
     * @param oldState �ϵ�״̬
     */
    protected void onStateChanged(State curState, State oldState) {
        switch (curState) {
        case RESET:
            onReset();
            break;
            
        case RELEASE_TO_REFRESH:
            onReleaseToRefresh();
            break;
            
        case PULL_TO_REFRESH:
            onPullToRefresh();
            break;
            
        case REFRESHING:
            onRefreshing();
            break;
            
        case NO_MORE_DATA:
            onNoMoreData();
            break;
            
        default:
            break;
        }
    }
    
    /**
     * ��״̬����Ϊ{@link State#RESET}ʱ����
     */
    protected void onReset() {
        
    }
    
    /**
     * ��״̬����Ϊ{@link State#PULL_TO_REFRESH}ʱ����
     */
    protected void onPullToRefresh() {
        
    }
    
    /**
     * ��״̬����Ϊ{@link State#RELEASE_TO_REFRESH}ʱ����
     */
    protected void onReleaseToRefresh() {
        
    }
    
    /**
     * ��״̬����Ϊ{@link State#REFRESHING}ʱ����
     */
    protected void onRefreshing() {
        
    }
    
    /**
     * ��״̬����Ϊ{@link State#NO_MORE_DATA}ʱ����
     */
    protected void onNoMoreData() {
        
    }
    
    /**
     * �õ���ǰLayout�����ݴ�С��������Ϊһ��ˢ�µ��ٽ��
     * 
     * @return �߶�
     */
    public abstract int getContentSize();
    
    /**
     * ����Loading��View
     * 
     * @param context context
     * @param attrs attrs
     * @return Loading��View
     */
    protected abstract View createLoadingView(Context context, AttributeSet attrs);
}
