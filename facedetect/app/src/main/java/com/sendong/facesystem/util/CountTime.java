package com.sendong.facesystem.util;

public class CountTime {
	public long mStart;
	public long mEnd;
//	public CountTime(long start, long end){
//		this.mStart = start;
//		this.mEnd = end;				
//	}
	public CountTime(){
		this.mStart = 0;
		this.mEnd = 0;				
	}
	public void setStart(long a){
		this.mStart = a;
	}
	public void setEnd(long a){
		this.mEnd = a;
	}
	public float getCountTime(){
		float time = (float)(mEnd - mStart)/1000f; //单位为秒
		return time;
	}
	public void clearCountTime(){
		this.mStart = 0;
		this.mEnd = 0;
	}
}
