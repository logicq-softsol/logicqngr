package com.logicq.ngr.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "KEY_IDENTIFIER")
public class KeyIdentifier implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8279920405179418682L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "TYPE")
	private String type;

	@Column(name = "MYSQL_TYPE")
	private String mysqlType;

	@Column(name = "DRUID_TYPE")
	private String druidType;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMysqlType() {
		return mysqlType;
	}

	public void setMysqlType(String mysqlType) {
		this.mysqlType = mysqlType;
	}

	public String getDruidType() {
		return druidType;
	}

	public void setDruidType(String druidType) {
		this.druidType = druidType;
	}

	@Override
	public String toString() {
		return "KeyIdentifier [id=" + id + ", type=" + type + ", mysqlType=" + mysqlType + ", druidType=" + druidType
				+ "]";
	}
}