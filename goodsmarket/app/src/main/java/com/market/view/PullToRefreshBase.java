package com.market.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.market.view.ILoadingLayout.State;

/**
 * ���ʵ��������ˢ�º��������ظ���Ĺ���
 * 
 * @author Li Hong
 * @since 2013-7-29
 * @param <T>
 */
public abstract class PullToRefreshBase<T extends View> extends LinearLayout implements IPullToRefresh<T> {
    /**
     * ����������ˢ�º��������ظ���Ľӿڡ�
     * 
     * @author Li Hong
     * @since 2013-7-29
     */
    public interface OnRefreshListener<V extends View> {
     
        /**
         * �������ֺ�ᱻ����
         * 
         * @param refreshView ˢ�µ�View
         */
        void onPullDownToRefresh(final PullToRefreshBase<V> refreshView);
        
        /**
         * ���ظ���ʱ�ᱻ���û�����ʱ����
         * 
         * @param refreshView ˢ�µ�View
         */
        void onPullUpToRefresh(final PullToRefreshBase<V> refreshView);
    }
    
    /**�ع���ʱ��*/
    private static final int SCROLL_DURATION = 150;
    /**����ϵ��*/
    private static final float OFFSET_RADIO = 2.5f;
    /**��һ���ƶ��ĵ� */
    private float mLastMotionY = -1;
    /**����ˢ�ºͼ��ظ���ļ����� */
    private OnRefreshListener<T> mRefreshListener;
    /**����ˢ�µĲ��� */
    private LoadingLayout mHeaderLayout;
    /**�������ظ���Ĳ���*/
    private LoadingLayout mFooterLayout;
    /**HeaderView�ĸ߶�*/
    private int mHeaderHeight;
    /**FooterView�ĸ߶�*/
    private int mFooterHeight;
    /**����ˢ���Ƿ����*/
    private boolean mPullRefreshEnabled = true;
    /**���������Ƿ����*/
    private boolean mPullLoadEnabled = false;
    /**�жϻ������ײ������Ƿ����*/
    private boolean mScrollLoadEnabled = false;
    /**�Ƿ�ض�touch�¼�*/
    private boolean mInterceptEventEnable = true;
    /**��ʾ�Ƿ�������touch�¼�������ǣ��򲻵��ø����onTouchEvent����*/
    private boolean mIsHandledTouchEvent = false;
    /**�ƶ���ı�����Χֵ*/
    private int mTouchSlop;
    /**������״̬*/
    private State mPullDownState = State.NONE;
    /**������״̬*/
    private State mPullUpState = State.NONE;
    /**��������ˢ�µ�View*/
    T mRefreshableView;
    /**ƽ��������Runnable*/
    private SmoothScrollRunnable mSmoothScrollRunnable;
    /**��ˢ��View�İ�װ����*/
    private FrameLayout mRefreshableViewWrapper;
    
    /**
     * ���췽��
     * 
     * @param context context
     */
    public PullToRefreshBase(Context context) {
        super(context);
        init(context, null);
    }

    /**
     * ���췽��
     * 
     * @param context context
     * @param attrs attrs
     */
    public PullToRefreshBase(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    /**
     * ���췽��
     * 
     * @param context context
     * @param attrs attrs
     * @param defStyle defStyle
     */
    public PullToRefreshBase(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        
        init(context, attrs);
    }

    /**
     * ��ʼ��
     * 
     * @param context context
     */
    private void init(Context context, AttributeSet attrs) {
        setOrientation(LinearLayout.VERTICAL);
        
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        
        mHeaderLayout = createHeaderLoadingLayout(context, attrs);
        mFooterLayout = createFooterLoadingLayout(context, attrs);
        mRefreshableView = createRefreshableView(context, attrs);
        
        if (null == mRefreshableView) {
            throw new NullPointerException("Refreshable view can not be null.");
        }
        
        addRefreshableView(context, mRefreshableView);
        addHeaderAndFooter(context);

        // �õ�Header�ĸ߶ȣ�����߶���Ҫ�����ַ�ʽ�õ�����onLayout��������õ��ĸ߶�ʼ����0
        getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                refreshLoadingViewsSize();
                getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }
    
    /**
     * ��ʼ��padding�����Ǹ���header��footer�ĸ߶�������top padding��bottom padding
     */
    private void refreshLoadingViewsSize() {
        // �õ�header��footer�����ݸ߶ȣ���������Ϊ�϶�ˢ�µ�һ���ٽ�ֵ������϶������������߶�
        // Ȼ�����ɿ��֣��ͻᴥ��ˢ�²���
        int headerHeight = (null != mHeaderLayout) ? mHeaderLayout.getContentSize() : 0;
        int footerHeight = (null != mFooterLayout) ? mFooterLayout.getContentSize() : 0;
        
        if (headerHeight < 0) {
            headerHeight = 0;
        }
        
        if (footerHeight < 0) {
            footerHeight = 0;
        }
        
        mHeaderHeight = headerHeight;
        mFooterHeight = footerHeight;
        
        // ����õ�Header��Footer�ĸ߶ȣ����õ�padding��top��bottom��Ӧ����header��footer�ĸ߶�
        // ��Ϊheader��footer����ȫ��������
        headerHeight = (null != mHeaderLayout) ? mHeaderLayout.getMeasuredHeight() : 0;
        footerHeight = (null != mFooterLayout) ? mFooterLayout.getMeasuredHeight() : 0;
        if (0 == footerHeight) {
            footerHeight = mFooterHeight;
        }
        
        int pLeft = getPaddingLeft();
        int pTop = getPaddingTop();
        int pRight = getPaddingRight();
        int pBottom = getPaddingBottom();
        
        pTop = -headerHeight;
        pBottom = -footerHeight;
        
        setPadding(pLeft, pTop, pRight, pBottom);
    }
    
    @Override
    protected final void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        
        // We need to update the header/footer when our size changes
        refreshLoadingViewsSize();
        
        // ����ˢ��View�Ĵ�С
        refreshRefreshableViewSize(w, h);
        
        /**
         * As we're currently in a Layout Pass, we need to schedule another one
         * to layout any changes we've made here
         */
        post(new Runnable() {
            @Override
            public void run() {
                requestLayout();
            }
        });
    }
    
    @Override
    public void setOrientation(int orientation) {
        if (LinearLayout.VERTICAL != orientation) {
            throw new IllegalArgumentException("This class only supports VERTICAL orientation.");
        }
        
        // Only support vertical orientation
        super.setOrientation(orientation);
    }
    
    @Override
    public final boolean onInterceptTouchEvent(MotionEvent event) {
        if (!isInterceptTouchEventEnabled()) {
            return false;
        }
        
        if (!isPullLoadEnabled() && !isPullRefreshEnabled()) {
            return false;
        }
        
        final int action = event.getAction();
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mIsHandledTouchEvent = false;
            return false;
        }
        
        if (action != MotionEvent.ACTION_DOWN && mIsHandledTouchEvent) {
            return true;
        }
        
        switch (action) {
        case MotionEvent.ACTION_DOWN:
            mLastMotionY = event.getY();
            mIsHandledTouchEvent = false;
            break;
            
        case MotionEvent.ACTION_MOVE:
            final float deltaY = event.getY() - mLastMotionY;
            final float absDiff = Math.abs(deltaY);
            // ����������������
            // 1��λ�Ʋ����mTouchSlop������Ϊ�˷�ֹ�����϶�����ˢ��
            // 2��isPullRefreshing()�������ǰ��������ˢ�µĻ������������ϻ���������ˢ�µ�HeaderView����ȥ
            // 3��isPullLoading()���������2����ͬ
            if (absDiff > mTouchSlop || isPullRefreshing() || isPullLoading())  {
                mLastMotionY = event.getY();
                // ��һ����ʾ������Header�Ѿ���ʾ������
                if (isPullRefreshEnabled() && isReadyForPullDown()) {
                    // 1��Math.abs(getScrollY()) > 0����ʾ��ǰ������ƫ�����ľ���ֵ����0����ʾ��ǰHeaderView�������˻���ȫ
                    // ���ɼ�����������һ��case��������ˢ��ʱ����RefreshableView�Ѿ��������������ϻ�������ô���������Ľ����
                    // ��Ȼ�����ϻ�����ֱ��HeaderView��ȫ���ɼ�
                    // 2��deltaY > 0.5f����ʾ������ֵ����0.5f
                    mIsHandledTouchEvent = (Math.abs(getScrollYValue()) > 0 || deltaY > 0.5f);
                    // ����ض��¼�����������Ȼ������¼�����ˢ��Viewȥ�������͵��������ListView/GridView������
                    // Child��Selector����
                    if (mIsHandledTouchEvent) {
                        mRefreshableView.onTouchEvent(event);
                    }
                } else if (isPullLoadEnabled() && isReadyForPullUp()) {
                    // ԭ������
                    mIsHandledTouchEvent = (Math.abs(getScrollYValue()) > 0 || deltaY < -0.5f);
                }
            }
            break; 
            
        default:
            break;
        }
        
        return mIsHandledTouchEvent;
    }

    @Override
    public final boolean onTouchEvent(MotionEvent ev) {
        boolean handled = false;
        switch (ev.getAction()) {
        case MotionEvent.ACTION_DOWN:
            mLastMotionY = ev.getY();
            mIsHandledTouchEvent = false;
            break;
            
        case MotionEvent.ACTION_MOVE:
            final float deltaY = ev.getY() - mLastMotionY;
            mLastMotionY = ev.getY();
            if (isPullRefreshEnabled() && isReadyForPullDown()) {
                pullHeaderLayout(deltaY / OFFSET_RADIO);
                handled = true;
            } else if (isPullLoadEnabled() && isReadyForPullUp()) {
                pullFooterLayout(deltaY / OFFSET_RADIO);
                handled = true;
            } else {
                mIsHandledTouchEvent = false;
            }
            break;
            
        case MotionEvent.ACTION_CANCEL:
        case MotionEvent.ACTION_UP:
            if (mIsHandledTouchEvent) {
                mIsHandledTouchEvent = false;
                // ����һ����ʾ����ʱ
                if (isReadyForPullDown()) {
                    // ����ˢ��
                    if (mPullRefreshEnabled && (mPullDownState == State.RELEASE_TO_REFRESH)) {
                        startRefreshing();
                        handled = true;
                    }
                    resetHeaderLayout();
                } else if (isReadyForPullUp()) {
                    // ���ظ���
                    if (isPullLoadEnabled() && (mPullUpState == State.RELEASE_TO_REFRESH)) {
                        startLoading();
                        handled = true;
                    }
                    resetFooterLayout();
                }
            }
            break;

        default:
            break;
        }
        
        return handled;
    }
    
    @Override
    public void setPullRefreshEnabled(boolean pullRefreshEnabled) {
        mPullRefreshEnabled = pullRefreshEnabled;
    }
    
    @Override
    public void setPullLoadEnabled(boolean pullLoadEnabled) {
        mPullLoadEnabled = pullLoadEnabled;
    }
    
    @Override
    public void setScrollLoadEnabled(boolean scrollLoadEnabled) {
        mScrollLoadEnabled = scrollLoadEnabled;
    }
    
    @Override
    public boolean isPullRefreshEnabled() {
        return mPullRefreshEnabled && (null != mHeaderLayout);
    }
    
    @Override
    public boolean isPullLoadEnabled() {
        return mPullLoadEnabled && (null != mFooterLayout);
    }
  
    @Override
    public boolean isScrollLoadEnabled() {
        return mScrollLoadEnabled;
    }
    
    @Override
    public void setOnRefreshListener(OnRefreshListener<T> refreshListener) {
        mRefreshListener = refreshListener;
    }
    
    @Override
    public void onPullDownRefreshComplete() {
        if (isPullRefreshing()) {
            mPullDownState = State.RESET;
            onStateChanged(State.RESET, true);
            
            // �ع�����һ��ʱ�䣬�����ڻع���ɺ�������״̬Ϊnormal
            // �ڽ�LoadingLayout��״̬����Ϊnormal֮ǰ������Ӧ�ý�ֹ
            // �ض�Touch�¼�����Ϊ������һ��post״̬�������post��Runnable
            // δ��ִ��ʱ���û���һ�η�������ˢ�£��������ˢ��ʱ�����Runnable
            // �ٴα�ִ�е�����ô�ͻ������ˢ�µ�״̬��Ϊ����״̬����Ͳ���������
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    setInterceptTouchEventEnabled(true);
                    mHeaderLayout.setState(State.RESET);
                }
            }, getSmoothScrollDuration());
            
            resetHeaderLayout();
            setInterceptTouchEventEnabled(false);
        }
    }
    
    @Override
    public void onPullUpRefreshComplete() {
        if (isPullLoading()) {
            mPullUpState = State.RESET;
            onStateChanged(State.RESET, false);

            postDelayed(new Runnable() {
                @Override
                public void run() {
                    setInterceptTouchEventEnabled(true);
                    mFooterLayout.setState(State.RESET);
                }
            }, getSmoothScrollDuration());
            
            resetFooterLayout();
            setInterceptTouchEventEnabled(false);
        }
    }
    
    @Override
    public T getRefreshableView() {
        return mRefreshableView;
    }
    
    @Override
    public LoadingLayout getHeaderLoadingLayout() {
        return mHeaderLayout;
    }
    
    @Override
    public LoadingLayout getFooterLoadingLayout() {
        return mFooterLayout;
    }

    @Override
    public void setLastUpdatedLabel(CharSequence label){
        if (null != mHeaderLayout) {
            mHeaderLayout.setLastUpdatedLabel(label);
        }
        
        if (null != mFooterLayout) {
            mFooterLayout.setLastUpdatedLabel(label);
        }
    }
    
    /**
     * ��ʼˢ�£�ͨ�����ڵ���������ˢ�£����͵�����ǽ�����棬��ʼ����ˢ�£����ˢ�²��������û����������
     * 
     * @param smoothScroll ��ʾ�Ƿ���ƽ��������true��ʾƽ��������false��ʾ��ƽ������
     * @param delayMillis �ӳ�ʱ��
     */
    public void doPullRefreshing(final boolean smoothScroll, final long delayMillis) {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                int newScrollValue = -mHeaderHeight;
                int duration = smoothScroll ? SCROLL_DURATION : 0;
                
                startRefreshing();
                smoothScrollTo(newScrollValue, duration, 0);
            }
        }, delayMillis);
    }
    
    /**
     * ��������ˢ�µ�View
     * 
     * @param context context
     * @param attrs ����
     * @return View
     */
    protected abstract T createRefreshableView(Context context, AttributeSet attrs);
    
    /**
     * �ж�ˢ�µ�View�Ƿ񻬶�������
     * 
     * @return true��ʾ�Ѿ�����������������false
     */
    protected abstract boolean isReadyForPullDown();
    
    /**
     * �ж�ˢ�µ�View�Ƿ񻬶�����
     * 
     * @return true��ʾ�Ѿ��������ײ�������false
     */
    protected abstract boolean isReadyForPullUp();
    
    /**
     * ����Header�Ĳ���
     * 
     * @param context context
     * @param attrs ����
     * @return LoadingLayout����
     */
    protected LoadingLayout createHeaderLoadingLayout(Context context, AttributeSet attrs) {
        return new HeaderLoadingLayout(context);
    }
    
    /**
     * ����Footer�Ĳ���
     * 
     * @param context context
     * @param attrs ����
     * @return LoadingLayout����
     */
    protected LoadingLayout createFooterLoadingLayout(Context context, AttributeSet attrs) {
        return new FooterLoadingLayout(context);
    }
    
    /**
     * �õ�ƽ��������ʱ�䣬�����������д����������ؼ�����ʱ��
     * 
     * @return ����ֵʱ��Ϊ����
     */
    protected long getSmoothScrollDuration() {
        return SCROLL_DURATION;
    }
    
    /**
     * ����ˢ��View�Ĵ�С
     * 
     * @param width ��ǰ�����Ŀ��
     * @param height ��ǰ�����Ŀ��
     */
    protected void refreshRefreshableViewSize(int width, int height) {
        if (null != mRefreshableViewWrapper) {
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mRefreshableViewWrapper.getLayoutParams();
            if (lp.height != height) {
                lp.height = height;
                mRefreshableViewWrapper.requestLayout();
            }
        }
    }
    
    /**
     * ��ˢ��View��ӵ���ǰ������
     * 
     * @param context context
     * @param refreshableView ����ˢ�µ�View
     */
    protected void addRefreshableView(Context context, T refreshableView) {
        int width  = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        
        // ����һ����װ����
        mRefreshableViewWrapper = new FrameLayout(context);
        mRefreshableViewWrapper.addView(refreshableView, width, height);

        // �����Refresh view�ĸ߶�����Ϊһ����С��ֵ�����ĸ߶����ջ���onSizeChanged()����������ΪMATCH_PARENT
        // ��������ԭ���ǣ������������height��MATCH_PARENT����ôfooter�õ��ĸ߶Ⱦ���0�����ԣ����������ø߶Ⱥ�С
        // ���ǾͿ��Եõ�header��footer�������߶ȣ���onSizeChanged��Refresh view�ĸ߶��ֻ��Ϊ������
        height = 10;
        addView(mRefreshableViewWrapper, new LinearLayout.LayoutParams(width, height));
    }
    
    /**
     * ���Header��Footer
     * 
     * @param context context
     */
    protected void addHeaderAndFooter(Context context) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        
        final LoadingLayout headerLayout = mHeaderLayout;
        final LoadingLayout footerLayout = mFooterLayout;
        
        if (null != headerLayout) {
            if (this == headerLayout.getParent()) {
                removeView(headerLayout);
            }
            
            addView(headerLayout, 0, params);
        }
        
        if (null != footerLayout) {
            if (this == footerLayout.getParent()) {
                removeView(footerLayout);
            }
            
            addView(footerLayout, -1, params);
        }
    }
    
    /**
     * ����Header Layoutʱ����
     * 
     * @param delta �ƶ��ľ���
     */
    protected void pullHeaderLayout(float delta) {
        // ���ϻ��������ҵ�ǰscrollYΪ0ʱ��������
        int oldScrollY = getScrollYValue();
        if (delta < 0 && (oldScrollY - delta) >= 0) {
            setScrollTo(0, 0);
            return;
        }
        
        // ���»�������
        setScrollBy(0, -(int)delta);
        
        if (null != mHeaderLayout && 0 != mHeaderHeight) {
            float scale = Math.abs(getScrollYValue()) / (float) mHeaderHeight;
            mHeaderLayout.onPull(scale);
        }
        
        // δ����ˢ��״̬�����¼�ͷ
        int scrollY = Math.abs(getScrollYValue());
        if (isPullRefreshEnabled() && !isPullRefreshing()) { 
            if (scrollY > mHeaderHeight) {
                mPullDownState = State.RELEASE_TO_REFRESH;
            } else {
                mPullDownState = State.PULL_TO_REFRESH;
            }
            
            mHeaderLayout.setState(mPullDownState);
            onStateChanged(mPullDownState, true);
        }
    }

    /**
     * ��Footerʱ����
     * 
     * @param delta �ƶ��ľ���
     */
    protected void pullFooterLayout(float delta) {
        int oldScrollY = getScrollYValue();
        if (delta > 0 && (oldScrollY - delta) <= 0) {
            setScrollTo(0, 0);
            return;
        }
        
        setScrollBy(0, -(int)delta);
        
        if (null != mFooterLayout && 0 != mFooterHeight) {
            float scale = Math.abs(getScrollYValue()) / (float) mFooterHeight;
            mFooterLayout.onPull(scale);
        }
        
        int scrollY = Math.abs(getScrollYValue());
        if (isPullLoadEnabled() && !isPullLoading()) {
            if (scrollY > mFooterHeight) {
                mPullUpState = State.RELEASE_TO_REFRESH;
            } else {
                mPullUpState = State.PULL_TO_REFRESH;
            }
            
            mFooterLayout.setState(mPullUpState);
            onStateChanged(mPullUpState, false);
        }
    }

    /**
     * ����header
     */
    protected void resetHeaderLayout() {
        final int scrollY = Math.abs(getScrollYValue());
        final boolean refreshing = isPullRefreshing();
        
        if (refreshing && scrollY <= mHeaderHeight) {
            smoothScrollTo(0);
            return;
        }
        
        if (refreshing) {
            smoothScrollTo(-mHeaderHeight);
        } else {
            smoothScrollTo(0);
        }
    }
    
    /**
     * ����footer
     */
    protected void resetFooterLayout() {
        int scrollY = Math.abs(getScrollYValue());
        boolean isPullLoading = isPullLoading();
        
        if (isPullLoading && scrollY <= mFooterHeight) {
            smoothScrollTo(0);
            return;
        }
        
        if (isPullLoading) {
            smoothScrollTo(mFooterHeight);
        } else {
            smoothScrollTo(0);
        }
    }
    
    /**
     * �ж��Ƿ���������ˢ��
     * 
     * @return true����ˢ�£�����false
     */
    protected boolean isPullRefreshing() {
        return (mPullDownState == State.REFRESHING);
    }
    
    /**
     * �Ƿ������������ظ���
     * 
     * @return true���ڼ��ظ��࣬����false
     */
    protected boolean isPullLoading() {
        return (mPullUpState == State.REFRESHING);
    }
    
    /**
     * ��ʼˢ�£��������ɿ��󱻵���
     */
    protected void startRefreshing() {
        // �������ˢ��
        if (isPullRefreshing()) {
            return;
        }
        
        mPullDownState = State.REFRESHING;
        onStateChanged(State.REFRESHING, true);
        
        if (null != mHeaderLayout) {
            mHeaderLayout.setState(State.REFRESHING);
        }
        
        if (null != mRefreshListener) {
            // ��Ϊ������ԭʼλ�õ�ʱ����200��������Ҫ�Ȼع�����ִ��ˢ�»ص�
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    mRefreshListener.onPullDownToRefresh(PullToRefreshBase.this);
                }
            }, getSmoothScrollDuration()); 
        }
    }

    /**
     * ��ʼ���ظ��࣬�����ɿ������
     */
    protected void startLoading() {
        // ������ڼ���
        if (isPullLoading()) {
            return;
        }
        
        mPullUpState = State.REFRESHING;
        onStateChanged(State.REFRESHING, false);
        
        if (null != mFooterLayout) {
            mFooterLayout.setState(State.REFRESHING);
        }
        
        if (null != mRefreshListener) {
            // ��Ϊ������ԭʼλ�õ�ʱ����200��������Ҫ�Ȼع�����ִ�м��ػص�
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    mRefreshListener.onPullUpToRefresh(PullToRefreshBase.this);
                }
            }, getSmoothScrollDuration()); 
        }
    }
    
    /**
     * ��״̬�����仯ʱ����
     * 
     * @param state ״̬
     * @param isPullDown �Ƿ�����
     */
    protected void onStateChanged(State state, boolean isPullDown) {
        
    }
    
    /**
     * ���ù���λ��
     * 
     * @param x ��������xλ��
     * @param y ��������yλ��
     */
    private void setScrollTo(int x, int y) {
        scrollTo(x, y);
    }
    
    /**
     * ���ù�����ƫ��
     * 
     * @param x ����xλ��
     * @param y ����yλ��
     */
    private void setScrollBy(int x, int y) {
        scrollBy(x, y);
    }
    
    /**
     * �õ���ǰY�Ĺ���ֵ
     * 
     * @return ����ֵ
     */
    private int getScrollYValue() {
        return getScrollY();
    }
    
    /**
     * ƽ������
     * 
     * @param newScrollValue ������ֵ
     */
    private void smoothScrollTo(int newScrollValue) {
        smoothScrollTo(newScrollValue, getSmoothScrollDuration(), 0);
    }
    
    /**
     * ƽ������
     * 
     * @param newScrollValue ������ֵ
     * @param duration ����ʱ��
     * @param delayMillis �ӳ�ʱ�䣬0�����ӳ�
     */
    private void smoothScrollTo(int newScrollValue, long duration, long delayMillis) {
        if (null != mSmoothScrollRunnable) {
            mSmoothScrollRunnable.stop();
        }
        
        int oldScrollValue = this.getScrollYValue();
        boolean post = (oldScrollValue != newScrollValue);
        if (post) {
            mSmoothScrollRunnable = new SmoothScrollRunnable(oldScrollValue, newScrollValue, duration);
        }
        
        if (post) {
            if (delayMillis > 0) {
                postDelayed(mSmoothScrollRunnable, delayMillis);
            } else {
                post(mSmoothScrollRunnable);
            }
        }
    }
    
    /**
     * �����Ƿ�ض�touch�¼�
     * 
     * @param enabled true�ضϣ�false���ض�
     */
    private void setInterceptTouchEventEnabled(boolean enabled) {
        mInterceptEventEnable = enabled;
    }
    
    /**
     * ��־�Ƿ�ض�touch�¼�
     * 
     * @return true�ضϣ�false���ض�
     */
    private boolean isInterceptTouchEventEnabled() {
        return mInterceptEventEnable;
    }
    
    /**
     * ʵ����ƽ��������Runnable
     * 
     * @author Li Hong
     * @since 2013-8-22
     */
    final class SmoothScrollRunnable implements Runnable {
        /**����Ч��*/
        private final Interpolator mInterpolator;
        /**����Y*/
        private final int mScrollToY;
        /**��ʼY*/
        private final int mScrollFromY;
        /**����ʱ��*/
        private final long mDuration;
        /**�Ƿ��������*/
        private boolean mContinueRunning = true;
        /**��ʼʱ��*/
        private long mStartTime = -1;
        /**��ǰY*/
        private int mCurrentY = -1;

        /**
         * ���췽��
         * 
         * @param fromY ��ʼY
         * @param toY ����Y
         * @param duration ����ʱ��
         */
        public SmoothScrollRunnable(int fromY, int toY, long duration) {
            mScrollFromY = fromY;
            mScrollToY = toY;
            mDuration = duration;
            mInterpolator = new DecelerateInterpolator();
        }

        @Override
        public void run() {
            /**
             * If the duration is 0, we scroll the view to target y directly.
             */
            if (mDuration <= 0) {
                setScrollTo(0, mScrollToY);
                return;
            }
            
            /**
             * Only set mStartTime if this is the first time we're starting,
             * else actually calculate the Y delta
             */
            if (mStartTime == -1) {
                mStartTime = System.currentTimeMillis();
            } else {
                
                /**
                 * We do do all calculations in long to reduce software float
                 * calculations. We use 1000 as it gives us good accuracy and
                 * small rounding errors
                 */
                final long oneSecond = 1000;    // SUPPRESS CHECKSTYLE
                long normalizedTime = (oneSecond * (System.currentTimeMillis() - mStartTime)) / mDuration;
                normalizedTime = Math.max(Math.min(normalizedTime, oneSecond), 0);

                final int deltaY = Math.round((mScrollFromY - mScrollToY)
                        * mInterpolator.getInterpolation(normalizedTime / (float) oneSecond));
                mCurrentY = mScrollFromY - deltaY;
                
                setScrollTo(0, mCurrentY);
            }

            // If we're not at the target Y, keep going...
            if (mContinueRunning && mScrollToY != mCurrentY) {
                PullToRefreshBase.this.postDelayed(this, 16);// SUPPRESS CHECKSTYLE
            }
        }

        /**
         * ֹͣ����
         */
        public void stop() {
            mContinueRunning = false;
            removeCallbacks(this);
        }
    }
}
