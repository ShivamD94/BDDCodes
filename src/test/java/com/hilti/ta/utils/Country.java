package com.hilti.ta.utils;

/**
 * Enumeration class containing part of countries supported by HILTI website.
 */
public enum Country {
	US("United States", ".com"),
	DE("Germany", ".de"),
	RU("Russia", ".ru"),
	JP("Japan", ".co.jp"),
	CN("China", ".cn");

	private String name;
	private String domain;

	Country(final String name, final String domain) {
		this.name = name;
		this.domain = domain;
	}

	public String getName() {
		return name;
	}

	public String getDomain() {
		return domain;
	}
}
