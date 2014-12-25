package com.soledede.classfy.bayes.util;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class MyJsonUtil {
	private Map jsonMap = new HashMap();
	private MyJsonConfig myJsonConfig = null;

	private SimpleDateFormat formatter = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public void clear() {
		jsonMap.clear();
	}

	public MyJsonConfig getMyJsonConfig() {
		return myJsonConfig;
	}

	public void setMyJsonConfig(MyJsonConfig myJsonConfig) {
		this.myJsonConfig = myJsonConfig;
	}

	public Map put(String key, Object value) {
		jsonMap.put(key, value);
		return jsonMap;
	}

	public Map getMap() {
		return this.jsonMap;
	}

	// 判断是否要加引号
	private boolean isNoQuote(Object value) {
		return (value instanceof Integer || value instanceof Boolean
				|| value instanceof Double || value instanceof Float
				|| value instanceof Short || value instanceof Long
				|| value instanceof Byte || value instanceof BigDecimal);
	}

	private boolean isQuote(Object value) {
		return (value instanceof String || value instanceof Character);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		Set set = jsonMap.entrySet();
		for (int i = 0; i < set.size(); i++) {
			Object value = ((Entry) set.toArray()[i]).getValue();
			if (value == null) {
				continue;// 对于null值，不进行处理，页面上的js取不到值时也是null
			}
			sb.append("\"").append(((Entry) set.toArray()[i]).getKey())
					.append("\":");
			if (value instanceof MyJsonUtil) {
				sb.append(value.toString());
			} else if (isNoQuote(value)) {
				sb.append(value);
			} else if (value instanceof Date) {
				sb.append("\"").append(formatter.format(value)).append("\"");
			} else if (isQuote(value)) {
				sb.append("\"").append(value).append("\"");
			} else if (value.getClass().isArray()) {
				sb.append(ArrayToStr((int[]) value));
			} else if (value instanceof Map) {
				sb.append(fromObject((Map) value).toString());
			} else if (value instanceof List) {
				sb.append(ListToStr((List) value));
			} else if (value instanceof Set) {
				sb.append(fromSet((Set) value).toString());
			} else {
				sb.append(fromObject(value).toString());
			}
			sb.append(",");
		}
		int len = sb.length();
		if (len > 1) {
			sb.delete(len - 1, len);
		}
		sb.append("}");
		return sb.toString();
	}

	public String ArrayToStr(Object array) {
		if (!array.getClass().isArray())
			return "[]";
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		int len = Array.getLength(array);
		Object v = null;
		for (int i = 0; i < len; i++) {
			v = Array.get(array, i);
			if (v instanceof Date) {
				sb.append("\"").append(formatter.format(v)).append("\"")
						.append(",");
			} else if (isQuote(v)) {
				sb.append("\"").append(v).append("\"").append(",");
			} else if (isNoQuote(v)) {
				sb.append(i).append(",");
			} else {
				sb.append(fromObject(v)).append(",");
			}
		}
		len = sb.length();
		if (len > 1)
			sb.delete(len - 1, len);
		sb.append("]");
		return sb.toString();
	}

	public String ListToStr(List list) {
		if (list == null)
			return null;
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		Object value = null;
		for (java.util.Iterator it = list.iterator(); it.hasNext();) {
			value = it.next();
			if (value instanceof Map) {
				sb.append(fromObject((Map) value).toString()).append(",");
			} else if (isNoQuote(value)) {
				sb.append(value).append(",");
			} else if (isQuote(value)) {
				sb.append("\"").append(value).append("\"").append(",");
			} else if (value instanceof Set) {
				sb.append(fromSet((Set) value).toString()).append(",");
			} else {
				sb.append(fromObject(value).toString()).append(",");
			}
		}
		int len = sb.length();
		if (len > 1)
			sb.delete(len - 1, len);
		sb.append("]");
		return sb.toString();
	}

	public MyJsonUtil fromObject(Object bean) {
		MyJsonUtil json = new MyJsonUtil();
		if (bean == null)
			return json;
		Class cls = bean.getClass();
		Field[] fs = cls.getDeclaredFields();
		Object value = null;
		String fieldName = null;
		Method method = null;
		int len = fs.length;
		for (int i = 0; i < len; i++) {
			Boolean flag = false;
			fieldName = fs[i].getName();
			if (myJsonConfig != null) {
				json.setMyJsonConfig(myJsonConfig);
				for (String filedString : myJsonConfig.getExcludes()) {
					if (filedString.trim().equalsIgnoreCase(fieldName)) {
						flag = true;
						break;
					}
				}
				if (flag)
					continue;
			}

			try {
				method = cls.getMethod(getGetter(fieldName), (Class[]) null);
				value = method.invoke(bean, (Object[]) null);
				if (value == null) {
					continue;
				}
			} catch (Exception e) {
				// System.out.println(method.getName());
				// e.printStackTrace();
				continue;
			}
			json.put(fieldName, value);
		}
		return json;
	}

	public MyJsonUtil fromObject(Map map) {
		MyJsonUtil json = new MyJsonUtil();
		if (map == null)
			return json;
		json.getMap().putAll(map);
		return json;
	}

	public String fromSet(Set set) {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		if (set == null) {
			sb.append("]");
			return sb.toString();
		}
		Object value = null;
		for (java.util.Iterator it = set.iterator(); it.hasNext();) {
			value = it.next();
			if (value instanceof Map) {
				sb.append(fromObject((Map) value).toString()).append(",");
			} else if (isNoQuote(value)) {
				sb.append(value).append(",");
			} else if (isQuote(value)) {
				sb.append("\"").append(value).append("\"").append(",");
			} else if (value instanceof Set) {
				sb.append(fromSet((Set) value).toString()).append(",");
			} else {
				sb.append(fromObject(value).toString()).append(",");
			}
		}
		int len = sb.length();
		if (len > 1)
			sb.delete(len - 1, len);
		sb.append("]");
		return sb.toString();
	}

	private String getGetter(String property) {
		return "get" + property.substring(0, 1).toUpperCase()
				+ property.substring(1, property.length());
	}

}
