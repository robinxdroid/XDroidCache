# XDroidCache
二级缓存框架，包括内存缓存于磁盘缓存，两者均使用Lru(近期最少使用算法),轻松为你的应用接入缓存机制，简单的场景比如你的网络请求，很方便的为你的网络请求模块添加缓存功能。
存储数据，先存入Memory cache,再存入Diskcache，取出数据时，先检查Memory中是否存在缓存，存在直接返回数据，不存在从Disk查找。当缓存过期的时候会进行删除，返回null

#Provide
	 1.MemoryCache（内存缓存），使用Android自带LruCache
	 2.DiskCache（磁盘缓存），使用JakeWharton的DiskLruCache
	 3.SecondLevelCache（二级缓存），一级MemoryCache,二级DiskCache
	 4.支持缓存时间设置，最小时间单位至秒，最大至年
	 5.支持永久缓存
	 6.缓存多种数据类型，基本的包括
	   String
	   byte[]
	   JSONObject
	   JSONArray
	   Bitmap
	   Drawable
	   Serialize（任何序列化的对象）
#Here is the sample
[Download demo.apk](https://github.com/robinxdroid/XDroidCache/blob/master/XDroidCacheExample.apk?raw=true)
#Screenshot
![](https://github.com/robinxdroid/XDroidCache/blob/master/screenshoot4.png?raw=true) 
![](https://github.com/robinxdroid/XDroidCache/blob/master/screenshoot1.png?raw=true) 
![](https://github.com/robinxdroid/XDroidCache/blob/master/screenshoot2.png?raw=true)
![](https://github.com/robinxdroid/XDroidCache/blob/master/screenshoot3.png?raw=true) 

# Usage
以String数据存取为例
```java
//key：key_stirng
//value:"测试数据"
//缓存时间：10
//时间单位：秒
SecondLevelCacheKit.getInstance(this).put("key_string", "测试数据", 10, TimeUnit.SECOND);
```
```java
//key：key_stirng
//value:"测试数据"
//未设置缓存时间，则为永久缓存
SecondLevelCacheKit.getInstance(this).put("key_string", "测试数据");
```
```java
//取缓存
String result = SecondLevelCacheKit.getInstance(this).getAsString("key_string");
```
其他详细请参见Demo
#Thanks
[DiskLruCache](https://github.com/JakeWharton/DiskLruCache)<br>
[ASimpleCache](https://github.com/yangfuhai/ASimpleCache)
#About me
Email:735506404@robinx.net<br>
Blog:[www.robinx.net](http://www.robinx.net)
 
