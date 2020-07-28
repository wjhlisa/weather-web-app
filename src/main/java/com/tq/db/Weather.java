package com.tq.db;

import javax.persistence.*;

@Entity
public class Weather
{
	@EmbeddedId
	private WeatherPK pk;

	// ****************Summary info****************
	@Column(nullable = false)
	private String weatherPicName;

	@Column(nullable = false)
	private String daySummary;

	@Column(nullable = false)
	private String dayWind;

	@Column(nullable = false)
	private int dayHighTemp;

	@Column(nullable = false)
	private String nightSummary;

	@Column(nullable = false)
	private String nightWind;

	@Column(nullable = false)
	private int nightLowTemp;

	@Column(nullable = false)
	private String sunRise;

	@Column(nullable = false)
	private String sunSet;

	// ****************Details info****************
	@Column(nullable = true)
	private int aqi;

	@Column(nullable = true)
	private int pm25;

	@Column(nullable = true)
	private String chuanYi;

	@Column(nullable = true)
	private String ganMao;

	@Column(nullable = true)
	private String xiChe;

	@Column(nullable = true)
	private String yunDong;

	@Column(nullable = true)
	private String ziWaiXian;

	@Column(nullable = true)
	private String wuRan;

	// **************** Methods ****************
	public Weather()
	{
	}

	public Weather(WeatherPK pk)
	{
		this.pk = pk;
	}

	public WeatherPK getPk()
	{
		return pk;
	}

	public void setPk(WeatherPK pk)
	{
		this.pk = pk;
	}

	public String getWeatherPicName()
	{
		return weatherPicName;
	}

	public void setWeatherPicName(String weatherPicName)
	{
		this.weatherPicName = weatherPicName;
	}

	public String getDaySummary()
	{
		return daySummary;
	}

	public void setDaySummary(String daySummary)
	{
		this.daySummary = daySummary;
	}

	public String getDayWind()
	{
		return dayWind;
	}

	public void setDayWind(String dayWind)
	{
		this.dayWind = dayWind;
	}

	public int getDayHighTemp()
	{
		return dayHighTemp;
	}

	public void setDayHighTemp(int dayHighTemp)
	{
		this.dayHighTemp = dayHighTemp;
	}

	public String getNightSummary()
	{
		return nightSummary;
	}

	public void setNightSummary(String nightSummary)
	{
		this.nightSummary = nightSummary;
	}

	public String getNightWind()
	{
		return nightWind;
	}

	public void setNightWind(String nightWind)
	{
		this.nightWind = nightWind;
	}

	public int getNightLowTemp()
	{
		return nightLowTemp;
	}

	public void setNightLowTemp(int nightLowTemp)
	{
		this.nightLowTemp = nightLowTemp;
	}

	public String getSunRise()
	{
		return sunRise;
	}

	public void setSunRise(String sunRise)
	{
		this.sunRise = sunRise;
	}

	public String getSunSet()
	{
		return sunSet;
	}

	public void setSunSet(String sunSet)
	{
		this.sunSet = sunSet;
	}

	public int getAqi()
	{
		return aqi;
	}

	public void setAqi(int aqi)
	{
		this.aqi = aqi;
	}

	public int getPm25()
	{
		return pm25;
	}

	public void setPm25(int pm25)
	{
		this.pm25 = pm25;
	}

	public String getChuanYi()
	{
		return chuanYi;
	}

	public void setChuanYi(String chuanYi)
	{
		this.chuanYi = chuanYi;
	}

	public String getGanMao()
	{
		return ganMao;
	}

	public void setGanMao(String ganMao)
	{
		this.ganMao = ganMao;
	}

	public String getXiChe()
	{
		return xiChe;
	}

	public void setXiChe(String xiChe)
	{
		this.xiChe = xiChe;
	}

	public String getYunDong()
	{
		return yunDong;
	}

	public void setYunDong(String yunDong)
	{
		this.yunDong = yunDong;
	}

	public String getZiWaiXian()
	{
		return ziWaiXian;
	}

	public void setZiWaiXian(String ziWaiXian)
	{
		this.ziWaiXian = ziWaiXian;
	}

	public String getWuRan()
	{
		return wuRan;
	}

	public void setWuRan(String wuRan)
	{
		this.wuRan = wuRan;
	}

	@Override
	public String toString()
	{
		return pk.toString();
	}
}
