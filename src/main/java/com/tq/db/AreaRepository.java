package com.tq.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AreaRepository extends JpaRepository<Area, String>
{
	//@Query使用了JPQL语言（类似SQL），所以表名称和字段名称，必须对应class和members，而不是数据库中的表名和字段名。否则运行报错 not mapped
	@Query("select distinct shi from Area")
	List<String> findAllShis();	
	
	//Spring Data JPA can deduce the queries from method names as long as method names adhere to Spring Data’s method-naming conventions
	List<Area> findByShi(String shiName);
} 
