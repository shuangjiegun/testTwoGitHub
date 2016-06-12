package com.goldfinance.jinGC.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpSession;

import com.goldfinance.jinGC.entity.User;

public class Utils {
	/**
	 * 把对象转换成Map<String, Object>
	 * 
	 * @param t
	 * @return
	 */
	public static <T> Map<String, Object> ObjectToMap(T t) {
		Map<String, Object> requ = new LinkedHashMap<String, Object>();
		Class<T> clazz = (Class<T>) t.getClass();
		Method[] m = clazz.getDeclaredMethods();
		Field[] f = clazz.getDeclaredFields();

		for (int j = 0; j < m.length; j++) {

			if (m[j].getName().startsWith("get")) {
				Object o;
				try {
					o = m[j].invoke(t);
					if (o != null) {
						requ.put(m[j].getName().substring(3).toLowerCase(), o);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}

		return requ;
	}
	/**
	 * 填充对象
	 */
	public static <T> T  getObject(T t,Map<String, Object> requ){
		for( Entry<String, Object> it:requ.entrySet()){
			Class<T> clazz = (Class<T>) t.getClass();
			Method[] m = clazz.getDeclaredMethods();
			for(int i=0;i<m.length;i++){
				if (m[i].getName().startsWith("set")) {
					String name=m[i].getName().substring(3).toLowerCase();
					try {
						 m[i].invoke(t,requ.get(name));
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}
		}
	
		return t;
		
		
	}

	/**
	 * 
	 * @param session
	 * @return
	 * 从session中取出user对象
	 */
	public static User getUser(HttpSession session) {
		return (User) session.getAttribute("user");
	}
	
	public static String StringToString(String str){
		int len = str.length();// 取得字符串的长度
		int index = 0;// 预定义第一个非零字符串的位置

		char strs[] = str.toCharArray();// 将字符串转化成字符数组
		for (int i = 0; i < len; i++) {
			if ('0' != strs[i]) {
				index = i;// 找到非零字符串并跳出
				break;
			}
		}
		return str.substring(index, str.lastIndexOf("."));// 截取字符串;
	}
	
	public static String toreplace(String s,String s1,String s2){//"$1**********$2""(\\d{6})\\d*(\\d{2})"
		return s.replaceAll(s1,s2);
		
	}
	
	public static void main(String[] args) {
		//float f=Float.parseFloat("1234.56");
		System.out.println(StringToString("0003400"));
	}
	
	public static String dateStrFormat(Date time, String string) {
		SimpleDateFormat format = new SimpleDateFormat(string);
		String str = format.format(time);
		return str;
	}
	
}
class Person{
	private String name;
	private String age;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	@Override
	public String toString() {
		return "Person [name=" + name + ", age=" + age + "]";
	}
	
}
