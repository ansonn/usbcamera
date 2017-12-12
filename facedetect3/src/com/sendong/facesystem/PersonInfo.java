package com.sendong.facesystem;

import java.util.ArrayList;
import java.util.List;

public class PersonInfo {

	private long mEndTime = 0;
	private float mLastTime;
	private long mStartTime = 0;
	private boolean isAnalysing=false;
	private String imagePath;
	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	private List<Person> persons = new ArrayList<Person>();

	public void addPerson(Person person) {
		persons.add(person);
	}

	public List<Person> getPersons() {
		return persons;
	}

	public void setStartTime(long a) {
		this.mStartTime = a;
	}

	public long getStartTime() {
		return mStartTime;
	}

	public void setEndTime(long a) {
		this.mEndTime = a;
		this.mLastTime = (float) (mEndTime - mStartTime) / 1000f; // 单位为秒
	}

	public long getEndTime() {

		return mEndTime;
	}

	public float getLastTime() {
		return mLastTime;
	}

	public void clearAllInfo() {
		this.mEndTime = 0;
		this.mStartTime = 0;
		this.mLastTime = 0.f;
		isAnalysing=false;
		persons.clear();

	}

	public boolean isAnalysing() {
		return isAnalysing;
	}

	public void setAnalysing(boolean isAnalysing) {
		this.isAnalysing = isAnalysing;
	}
}
