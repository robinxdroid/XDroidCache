package com.xdroid.cache.example.bean;

import java.io.Serializable;

public class PersonBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name;
	private String email;

	public PersonBean(String name, String email) {
		super();
		this.name = name;
		this.email = email;
	}

	public PersonBean() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "PersonBean [name=" + name + ", email=" + email + "]";
	}

}
