* Instant——时间戳 
* LocalDate——处理日期。 
* LocalTime——处理时间 
* LocalDateTime——它包含了日期及时间 
* ZonedDateTime——时区的日期时间。

* 年————-getYear() 
* 月————getMonthValue() 
* 日————-getDayOfMonth() 
* 星期返回1-7————getDayOfWeek() 
* 是否为闰年—————isLeapYear() 
* 获取月份的天数———–lengthOfMonth() 
* 获取年的天数——–lengthOfYear() 
* 当前加longXXX———plusXXX(long) 
* 当前减minusXXX———minusXXX(long) 
* 在这之前还是在这之后———isAfter和isBefore

* ZoneId
* DateTimeFormatter

https://blog.csdn.net/u010002184/article/details/74858320

@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")  
private LocalDateTime dateTime;  
  
@DateTimeFormat(pattern = "yyyy-MM-dd")  
private LocalDate date;  
  
@DateTimeFormat(pattern = "HH:mm:ss")  
private LocalTime time;  

https://blog.csdn.net/leolu007/article/details/53112363