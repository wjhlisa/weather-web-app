package com.tq.controller.api;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tq.TqUtil;
import com.tq.db.WeatherDataTimer;
import com.tq.db.WeatherRecord;
import com.tq.db.WeatherRepository;

@RestController
public class APIController
{
	@Autowired
	private WeatherRepository weatherRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(WeatherDataTimer.class);
	
	//小程序调用，返回json
	@RequestMapping(value = "/api/{areaID}", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<WeatherRecord> api(@PathVariable String areaID)	
	{
		//获取该地区所有天气记录
		List<WeatherRecord> allRecords = weatherRepository.findRecords(areaID);
		
		//找到第一个ChuanYi不为空，即是今天
		int todayIndex = -1;
		for (int i = 0; i < allRecords.size(); i++)
		{
			String chuanyi = allRecords.get(i).getChuanYi(); 
			if (chuanyi != null && !chuanyi.isEmpty())
			{
				todayIndex = i;
				break;
			}
		}
		
		if (todayIndex == -1)
		{	//数据库出了问题
			String error = MessageFormat.format("No today for area {0}",  areaID); 
			logger.error(error);
			TqUtil.SendMail("天气服务器错误", error);
			throw new RuntimeException(error);
		}
		
		//返回过去和未来几天的记录
		List<WeatherRecord> subList = allRecords.subList(0, 2 * todayIndex + 2);
		Collections.reverse(subList);
		
		logger.info(MessageFormat.format("Returned WeChat request for {0}", areaID));
		return subList;
	}

}
