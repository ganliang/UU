package com.uugty.app.entity;

import java.util.List;

public class SQLEntity {

	private String sql;

	private String parameter;

	private String quots;

	private List<Object> list;

	public SQLEntity() {
		super();
	}

	public SQLEntity(String parameter, List<Object> list) {
		super();
		this.parameter = parameter;
		this.list = list;
	}

	public SQLEntity(String parameter, String quots, List<Object> list) {
		super();
		this.parameter = parameter;
		this.quots = quots;
		this.list = list;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getQuots() {
		return quots;
	}

	public void setQuots(String quots) {
		this.quots = quots;
	}

	public List<Object> getList() {
		return list;
	}

	public void setList(List<Object> list) {
		this.list = list;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	@Override
	public String toString() {
		return "SQLEntity [sql=" + sql + ", parameter=" + parameter
				+ ", quots=" + quots + ", list=" + list + "]";
	}
}
