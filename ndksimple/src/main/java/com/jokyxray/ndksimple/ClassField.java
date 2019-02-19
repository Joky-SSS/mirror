package com.jokyxray.ndksimple;

public class ClassField {
	public static int num;
	public String name;

	public static int getNum() {
		return num;
	}

	public static void setNum(int num) {
		ClassField.num = num;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
