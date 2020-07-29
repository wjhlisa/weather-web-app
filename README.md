# weather-web-app
This spring-boot web app collects the latest weather data regularly from a public weather server with RestTemplate, and saves them into MySql with the ORM framework - Spring Data JPA. Restful+json API is provided to clients, such as web browsers and WeChat apps.  
  
The backend web page is built on the Thymeleaf template. It is responsive and adaptive using the Bootstrap CSS framework.  
The following page lists all the collected weather data. The page is updated by sending Ajax requests, and then updating the HTML elements directly.  
    
![image](https://github.com/wjhlisa/weather-web-app/blob/master/tq.gif)  
