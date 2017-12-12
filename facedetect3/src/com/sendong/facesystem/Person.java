package com.sendong.facesystem;

public class Person {
	private static final int MAN = 1;
	private static final int WOMAN = 2;
	private int mSex = -1;
	private String race;
	private String imagePath;
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getRace() {
		return race;
	}
	public void setRace(String race) {
		this.race = race;
	}
	public int getmSex() {
		return mSex;
	}
	public void setmSex(int mSex) {
		this.mSex = mSex;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public static int getMan() {
		return MAN;
	}
	public static int getWoman() {
		return WOMAN;
	}
	private int age;
}
