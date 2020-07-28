package com.tq.db;

import javax.persistence.*;

@Entity
public class Area
{
	// 主键不能超过1000 bytes，否则表无法创建。
	// String默认为Varchar(255)，所以这里需要指定长度
	@Column(length = 50)
	@Id
	private String areaCode;

	@Column(nullable = false)
	private String sheng;

	@Column(nullable = false)
	private String shi;

	@Column(nullable = false)
	private String qu;

	public String getAreaCode()
	{
		return areaCode;
	}

	public void setAreaCode(String areaCode)
	{
		this.areaCode = areaCode;
	}

	public String getSheng()
	{
		return sheng;
	}

	public void setSheng(String sheng)
	{
		this.sheng = sheng;
	}

	public String getShi()
	{
		return shi;
	}

	public void setShi(String shi)
	{
		this.shi = shi;
	}

	public String getQu()
	{
		return qu;
	}

	public void setQu(String qu)
	{
		this.qu = qu;
	}

}
