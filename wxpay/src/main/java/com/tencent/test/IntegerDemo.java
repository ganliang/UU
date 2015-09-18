package com.tencent.test;

import java.lang.reflect.Field;

import com.tencent.protocol.auth_protocol.AuthResData;

public class IntegerDemo {

	public static void main(String[] args) {

		AuthResData data = new AuthResData();

		Field[] declaredFields = data.getClass().getDeclaredFields();

		for (Field field : declaredFields) {

			field.setAccessible(true);

			String name = field.getType().getName();
			System.out.println(field.getName() + "  " + name);
		}

	}
}
