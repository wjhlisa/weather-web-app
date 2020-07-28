package com.tq.db;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;


//组合主键
//https://github.com/xiongdahu/xiongdahu.github.io/wiki/spring-jpa-%E4%B8%ADcrudRepository%E5%A4%84%E7%90%86%E7%BB%84%E5%90%88%E4%B8%BB%E9%94%AE

@Embeddable
public class WeatherPK implements Serializable
{
	//主键不能超过1000 bytes，否则表无法创建。
	//String默认为Varchar(255)，所以这里需要指定长度
	@Column(length=50)
	private String areaCode;
	
	//不能用类型Date作为主键，否则无法查询
	@Column(length=50)
	private String date;
	//private Date date;
	
	//hashCode() and eqaules()的实现
	//https://www.jianshu.com/p/7a0de63d5f99
	
	public WeatherPK()
	{		
	}
	
	public WeatherPK(String areaCode, String date)
	{
		this.setAreaCode(areaCode);
		this.setDate(date);
	}
	
	@Override
	public int hashCode()
	{
		return getAreaCode().hashCode() + getDate().hashCode();
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		
		if (o == null || getClass() != o.getClass())
		{
			return false;
		}
		
		WeatherPK wp = (WeatherPK)o; 
		
		return getAreaCode().equals(wp.getAreaCode()) && getDate().equals(wp.getDate()); 
	}
	
	@Override
	public String toString()
	{
		return MessageFormat.format("{0}, {1}", getDate(), getAreaCode());
	}

	public String getAreaCode()
	{
		return areaCode;
	}

	public void setAreaCode(String areaCode)
	{
		this.areaCode = areaCode;
	}

	public String getDate()
	{
		return date;
	}

	public void setDate(String date)
	{
		this.date = date;
	}
}
