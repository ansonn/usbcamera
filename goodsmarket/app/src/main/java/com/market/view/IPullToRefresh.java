package com.market.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.market.view.PullToRefreshBase.OnRefreshListener;

/**
 * ����������ˢ�µĽӿ�
 */
public interface IPullToRefresh<T extends View> {
    
    /**
     * ���õ�ǰ����ˢ���Ƿ����
     * 
     * @param pullRefreshEnabled true��ʾ���ã�false��ʾ������
     */
    public void setPullRefreshEnabled(boolean pullRefreshEnabled);
    
    /**
     * ���õ�ǰ�������ظ����Ƿ����
     * 
     * @param pullLoadEnabled true��ʾ���ã�false��ʾ������
     */
    public void setPullLoadEnabled(boolean pullLoadEnabled);
    
    /**
     * �������ײ��Ƿ��Զ����ظ�������
     * 
     * @param scrollLoadEnabled ������ֵΪtrue�Ļ�����ô�������ظ���Ĺ��ܽ������
     */
    public void setScrollLoadEnabled(boolean scrollLoadEnabled);
    
    /**
     * �жϵ�ǰ����ˢ���Ƿ����
     * 
     * @return true������ã�false������
     */
    public boolean isPullRefreshEnabled();
    
    /**
     * �ж����������Ƿ����
     * 
     * @return true���ã�false������
     */
    public boolean isPullLoadEnabled();
    
    /**
     * �������ײ������Ƿ����
     * 
     * @return true���ã����򲻿���
     */
    public boolean isScrollLoadEnabled();
    
    /**
     * ����ˢ�µļ�����
     * 
     * @param refreshListener ����������
     */
    public void setOnRefreshListener(OnRefreshListener<T> refreshListener);
    
    /**
     * ��������ˢ��
     */
    public void onPullDownRefreshComplete();
    
    /**
     * �����������ظ���
     */
    public void onPullUpRefreshComplete();
    
    /**
     * �õ���ˢ�µ�View����
     * 
     * @return ���ص���{@link #createRefreshableView(Context, AttributeSet)} �������صĶ���
     */
    public T getRefreshableView();
    
    /**
     * �õ�Header���ֶ���
     * 
     * @return Header���ֶ���
     */
    public LoadingLayout getHeaderLoadingLayout();
    
    /**
     * �õ�Footer���ֶ���
     * 
     * @return Footer���ֶ���
     */
    public LoadingLayout getFooterLoadingLayout();
    
    /**
     * ���������µ�ʱ���ı�
     * 
     * @param label �ı�
     */
    public void setLastUpdatedLabel(CharSequence label);
}
