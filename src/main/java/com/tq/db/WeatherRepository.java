package com.tq.db;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WeatherRepository extends JpaRepository<Weather, WeatherPK>
{
	//@Query使用了JPQL语言（类似SQL），所以表名称和字段名称，必须对应class和members，而不是数据库中的表名和字段名。否则运行报错 not mapped
	static final String JOIN_SQL = "select a.qu as qu, a.shi as shi, w.pk.date as date, w.weatherPicName as weatherPicName, w.daySummary as daySummary, w.dayWind as dayWind, w.dayHighTemp as dayHighTemp, "
			+ "w.nightSummary as nightSummary, w.nightWind as nightWind, w.nightLowTemp as nightLowTemp, w.sunRise as sunRise, w.sunSet as sunSet, w.aqi as aqi, "
			+ "w.pm25 as pm25, w.chuanYi as chuanYi, w.ganMao as ganMao, w.xiChe as xiChe, w.yunDong as yunDong, w.ziWaiXian as ziWaiXian, w.wuRan as wuRan "
			+ "from Weather w inner join Area a on w.pk.areaCode = a.areaCode";
		
	//联合查询需要创建interface，来接收结果   https://www.jianshu.com/p/391011c5d529		
	@Query(JOIN_SQL + " where a.areaCode = ?1 order by w.pk.date desc")	
	Page<WeatherRecord> findRecords(String areaID, Pageable pageable);
	
	//小程序API调用
	@Query(JOIN_SQL + " where a.areaCode = ?1 order by w.pk.date desc")	
	List<WeatherRecord> findRecords(String areaID);
		
	@Query(JOIN_SQL + " order by w.pk.date desc")	
	Page<WeatherRecord> findAllRecords(Pageable pageable);
	
	@Query(JOIN_SQL + " where a.shi = ?1 order by w.pk.date desc")	
	Page<WeatherRecord> findShiRecords(String shiName, Pageable pageable);
} 

