package com.jokyxray.hotfiximpl.bean;

public class Student {
	private String name;

	public Student() {
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return "Miss." + this.name;
	}
}