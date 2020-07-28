package com.tq.controller.system;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;

import com.tq.db.Area;
import com.tq.db.AreaRepository;
import com.tq.db.WeatherRecord;
import com.tq.db.WeatherRepository;

@Controller
public class WeatherRecordController
{
	@Autowired
	private WeatherRepository weatherRepository;
	
	@Autowired
	private AreaRepository areaRepository; 
	
	//返回首页
	@RequestMapping(value = "/wr", method = RequestMethod.GET)
	public String wr(Model model)
	{		
		List<String> shis = areaRepository.findAllShis();
		model.addAttribute("shis", shis);
		
		return "weather_record";
	}
	
	//Ajax请求，返回天气数据
	@RequestMapping(value = "/wr/{shiName}", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Map<String, Object> wr(@PathVariable String shiName, 
									@RequestParam(value = "qu") String quID, 
									@RequestParam(value = "page", defaultValue = "1") int currentPage)	
	{
		Pageable pageable = PageRequest.of(currentPage - 1, 20, new Sort(Direction.DESC, "pk.date"));
		Page<WeatherRecord> page;
				
		if (shiName.equalsIgnoreCase("0") && quID.equalsIgnoreCase("0"))
		{	//返回所有
			page = weatherRepository.findAllRecords(pageable);
		}
		else if (!shiName.equalsIgnoreCase("0") && quID.equalsIgnoreCase("0"))
		{	//返回某个城市所有辖区
			page = weatherRepository.findShiRecords(shiName, pageable);
		}
		else if (!shiName.equalsIgnoreCase("0") && !quID.equalsIgnoreCase("0"))
		{	//返回某个辖区
			page = weatherRepository.findRecords(quID, pageable);
		}
		else
		{	//不可能的组合
			return null;
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("weatherRecords", page);
		map.put("currentPage", currentPage);
		map.put("totalPages", page.getTotalPages());
		
		return map;
	}
	
	//Ajax请求，返回某个城市的辖区信息
	@RequestMapping(value = "/area/{shiName}", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<Area> area(@PathVariable String shiName)
	{
		return areaRepository.findByShi(shiName);
	}	
}
