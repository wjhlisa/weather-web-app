package com.tq.db;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.tq.TqUtil;

@Component
public class WeatherDataTimer
{
	@Autowired
	private WeatherRepository weatherRepository;

	@Autowired
	private AreaRepository areaRepository;

	@Autowired
	private RestTemplate restTemplate;

	private static final Logger logger = LoggerFactory.getLogger(WeatherDataTimer.class);

	private static final String WEATHER_URL = "https://cdn.weather.hao.360.cn/sed_api_weather_info.php?app=guideEngine&fmt=json";

	@Scheduled(fixedDelay = 3600000)
	public void downloadWeatherData()
	{
		List<Area> areas = areaRepository.findAll();

		// 异常邮件时使用
		String url = "";
		try
		{
			for (Area area : areas)
			{
				// Get 请求天气数据
				url = MessageFormat.format("{0}&code={1}", WEATHER_URL, area.getAreaCode());
				ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

				logger.info(MessageFormat.format("------------ Downloaded weahter data for {0}", area.getAreaCode()));
				logger.info(response.getBody());

				// 用Jackson解析Json响应
				// https://www.jianshu.com/p/4733aeaaf104
				JsonFactory jf = new JsonFactory();
				jf.enable(Feature.ALLOW_COMMENTS);
				ObjectMapper om = new ObjectMapper(jf);
				JsonNode root = om.readTree(response.getBody());
				String areaCode = root.path("area").path(2).path(1).asText();

				// 插入/更新五天记录
				for (int k = 0; k < root.path("weather").size(); k++)
				{
					JsonNode dayWeatherNode = root.path("weather").path(k);

					// 查询是否存在记录，否则创建
					WeatherPK pk = new WeatherPK(areaCode, dayWeatherNode.path("date").asText());
					Optional<Weather> op = weatherRepository.findById(pk);
					Weather w;
					if (op.isPresent())
					{ // 记录存在
						w = op.get();
					}
					else
					{
						w = new Weather(pk);
					}

					w.setWeatherPicName(dayWeatherNode.path("info").path("day").path(0).asText());

					w.setDaySummary(dayWeatherNode.path("info").path("day").path(1).asText());
					w.setDayWind(MessageFormat.format("{0} {1}",
												dayWeatherNode.path("info").path("day").path(3).asText(),
												dayWeatherNode.path("info").path("day").path(4).asText()));
					w.setDayHighTemp(dayWeatherNode.path("info").path("day").path(2).asInt());

					w.setNightSummary(dayWeatherNode.path("info").path("night").path(1).asText());
					w.setNightWind(MessageFormat.format("{0} {1}",
												dayWeatherNode.path("info").path("night").path(3).asText(),
												dayWeatherNode.path("info").path("night").path(4).asText()));
					w.setNightLowTemp(dayWeatherNode.path("info").path("night").path(2).asInt());

					w.setSunRise(dayWeatherNode.path("info").path("day").path(5).asText());
					w.setSunSet(dayWeatherNode.path("info").path("night").path(5).asText());

					weatherRepository.save(w);
				}

				// 更新今天的详情记录
				Weather todayWeather = weatherRepository
											.findById(new WeatherPK(areaCode, root.path("pubdate").asText())).get();
				todayWeather.setAqi(root.path("pm25").path("aqi").path(0).asInt());
				todayWeather.setPm25(root.path("pm25").path("pm25").path(0).asInt());
				todayWeather.setChuanYi(root.path("life").path("info").path("chuanyi").path(0).asText());
				todayWeather.setGanMao(root.path("life").path("info").path("ganmao").path(0).asText());
				todayWeather.setXiChe(root.path("life").path("info").path("xiche").path(0).asText());
				todayWeather.setYunDong(root.path("life").path("info").path("yundong").path(0).asText());
				todayWeather.setZiWaiXian(root.path("life").path("info").path("ziwaixian").path(0).asText());
				todayWeather.setWuRan(root.path("life").path("info").path("wuran").path(0).asText());
				weatherRepository.save(todayWeather);

				logger.info(MessageFormat.format("------------ Updated weahter data in DB for {0}", areaCode));
			}
		}
		catch (Exception ex)
		{
			logger.error(MessageFormat.format("------------ Failed to download weather data: {0}", ex.getMessage()));

			TqUtil.SendMail("下载天气失败", MessageFormat.format("{0}\n{1}", url, ex.getMessage()));
		}
	}

}