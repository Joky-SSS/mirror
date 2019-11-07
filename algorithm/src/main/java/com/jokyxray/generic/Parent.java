package com.jokyxray.generic;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class Parent<S, D, T> {
	public void getGenericType() {
		Class clazz = this.getClass();
		Type[] types = ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments();
		System.out.println(clazz);
		for (Type t : types) {
			System.out.println(t.getClass());
			System.out.println(t.getTypeName());
		}
	}
}
