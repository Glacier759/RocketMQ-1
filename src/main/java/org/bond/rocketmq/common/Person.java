package org.bond.rocketmq.common;

import java.io.Serializable;

public class Person implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8915329602453911889L;
	private String userName;
	private int age;
	private String address;

	public Person(String userName, int age, String address) {
		this.userName = userName;
		this.age = age;
		this.address = address;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "userName:" + userName + ";age:" + age + ";address:" + address;
	}
}
