/**
 * 
 */
package com.wxmp.backstage.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * 功能：一些数据结构处理类型
 * @author xiongliang
 *
 * mobile enterprise application platform
 * Version 0.1
 */
public class DataUtil {
	
	/**
	 * 获得对象列表
	 * @param <T>
	 * @param colls
	 * @return
	 */
	public static <T> List<T> getList(Collection<T> colls){
		if(colls==null || colls.isEmpty())return Collections.emptyList();
		List<T> list = new ArrayList<T>(colls.size());
		for(T id:colls){
			list.add(id);
		}
		return list;
	}
	
	/**
	 * 获得对象列表
	 * @param <T>
	 * @param ids
	 * @return
	 */
	public static <T> List<T> getList(T... ids) {
		if(ids==null || ids.length<1)return Collections.emptyList();
	    List<T> list = new ArrayList<T>(ids.length);
	    for (T id : ids) {
	        list.add(id);
	    }
	    return list;
	}
	/**
	 * 在原有基础上增加对象
	 * @param <T>
	 * @param list
	 * @param ids
	 * @return
	 */
	public static <T> List<T> getList(List<T> list,T...ids){
		if(ids==null || ids.length<1)return list;
		List<T> resultList = new ArrayList<T>();
		resultList.addAll(list);
		for (T id : ids) {
			resultList.add(id);
	    }
		return resultList;
	}
	/**
	 * 获得Integer对象列表
	 * @param ints
	 * @return
	 */
	public static List<Integer> getList(int... ints){
		List<Integer> list = new ArrayList<Integer>();
		for(int i:ints){
			list.add(i);
		}
		return list;
	}
	
	/**
	 * 获得调用的方法名
	 * @return
	 */
	public static String getInvokeMethodName(){
		String methodName = null;
		try {
            Thread thr = Thread.currentThread();
            StackTraceElement[] ele = thr.getStackTrace();
            methodName = ele[ele.length>3?3:2].getMethodName();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return methodName;
	}
	
	/**
	 * 获得调用的类
	 * @return
	 */
	public static Class<?> getInvokeClass(){
		Class<?> clazz = null;
		try {
            Thread thr = Thread.currentThread();
            StackTraceElement[] ele = thr.getStackTrace();
            clazz = Class.forName(ele[ele.length>3?3:2].getClassName());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return clazz;
	}
	
	/**
	 * 打印调用的类链
	 */
	public static void printStackInvokeClass(){
		try {
            Thread thr = Thread.currentThread();
            StackTraceElement[] ele = thr.getStackTrace();
            int index = 0 ;
            for(StackTraceElement ste:ele){
            	if(index++>2){
            		System.out.println(ste);
            	}
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * 获得调用的类名
	 * @return
	 */
	public static String getInvokeClassName(){
		String clazzName = null;
		try {
            Thread thr = Thread.currentThread();
            StackTraceElement[] ele = thr.getStackTrace();
            clazzName = ele[ele.length>3?3:2].getClassName();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return clazzName;
	}
	
	/**
	 * 获得反转的list
	 * @param <T>
	 * @param list
	 * @return
	 */
	public static <T> List<T> getReversionList(List<T> list) {
		
		Stack<T> stack = new Stack<T>();
		for(T t : list){
			stack.push(t);
		}
		return getListFromStack(stack);
	}
	
	/**
	 * 获得List :stack转list
	 * @param <T>
	 * @param stack
	 * @return
	 */
	public static <T> List<T> getListFromStack(Stack<T> stack){
		List<T> l = new ArrayList<T>();
		while(!stack.empty())l.add(stack.pop());
		return l;
	}
	
	/**
	 * 获得int数组
	 * @param strings
	 * @return
	 */
	public static int[] getIntArray(String...strings){
		if(strings==null || strings.length<1)return new int[]{};
		int[] array = new int[strings.length];
		for(int i=0;i<array.length;i++)
			array[i] = StringUtil.getInt(strings[i]);
		return array;
	}

	/**
	 * 获得int数组
	 * @return
	 */
	public static int[] getIntArray(int...k) {
		
		return k;
	}

	/**
	 * 返回String的Map格式:key,value,key,value
	 * @param strings
	 * @return
	 */
	public static Map<String, String> getMap(String...strings) {
		
		if(strings==null || strings.length%2!=0)return Collections.emptyMap();
		Map<String,String> map = new LinkedHashMap<String,String>();
		for(int i=0;i<strings.length;i+=2){
			map.put(strings[i], strings[i+1]);
		}
		return map;
	}
	/**
	 * 获得Integer数组
	 * @param it
	 * @return
	 */
	public static Integer[] getIntegerArrayFromIterable(Iterable<?> it){
		if(it==null)return new Integer[0];
		List<Integer> list = new ArrayList<Integer>();
		for(Object obj : it){
			list.add(StringUtil.getInt(obj));
		}
		return list.toArray(new Integer[list.size()]);
	}
	/**
	 * 获得Integer数组
	 * @param strings
	 * @return
	 */
	public static Integer[] getIntegerArray(String...strings){
		if(strings==null)return new Integer[0];
		List<Integer> list = new ArrayList<Integer>();
		for(String str :strings)
			list.add(StringUtil.getInt(str));
		return list.toArray(new Integer[list.size()]);
	}
	
	/**
	 * 获得列表中的某个索引,如果不存在返回null
	 * @param <T>
	 * @param list
	 * @param index
	 * @return
	 */
	public static <T> T get(List<T> list , int index){
		if(list==null || list.size()<index-1)return null;
		return list.get(index);
	}

	/**
	 * 获得按值排序的map
	 * @param <K>
	 * @param <V>
	 * @param map
	 * @param comparator 值排序器
	 * @return
	 */
	public static <K extends Object,V extends Comparable<?>> Map<K,V> getSortedByValueMap(final Map<K,V> map, Comparator<V> comparator) {
		
		if(map==null || map.isEmpty() || comparator==null)
			return map;
		
		Map<K,V> resultMap = new LinkedHashMap<K,V>();
		
		final List<V> values = new ArrayList<V>();
		values.addAll(map.values());
		Collections.sort( values, comparator);
		
		List<K> keys = new ArrayList<K>();
		keys.addAll(map.keySet());
		Collections.sort(keys,new Comparator<K>(){

			public int compare(K o1, K o2) {
				
				int index0 = values.indexOf(map.get(o1));
				int index1 = values.indexOf(map.get(o2));
				if(index0==index1)return 0;
				if(index0>index1)return 1;
				return -1;
			}
			
		});
		for(K k:keys){
			resultMap.put(k, map.get(k));
		}
		return resultMap;
	}

	/**
	 * 获得非空选择对象
	 * @param <T>
	 * @param t
	 * @param def
	 * @return
	 */
	public static <T> T getNonNull(T t,T def) {
		
		return t==null?def:t;
	}
	
	/**
	 * 获得循环索引值
	 * @param current 当前索引值
	 * @param step 每步值
	 * @param start 起始值
	 * @param length 列表长度
	 * @param zero 是否包含零
	 * @return -1 为停止 
	 */
	public static int getCycleIndex(int current,int step,int start, int length, boolean zero){
		if(current == start-1) return -1;
		int index = current + step;
		boolean cycle = false;
		if(index>length || (zero && index >= length)){
			index = index - length;
			cycle = true;
		}
		if(cycle && index >= start)
				return -1;
		return index;
	}
	
	/**
	 * 获得下一个循环索引,到边界重新开始
	 * @param current
	 * @param step
	 * @param length
	 * @param zero
	 * @return
	 */
	public static int getNextCycleIndex(int current, int step, int length ,boolean zero){
		if(length<0 || current>length) return -1;
		int index = current + step;
		if(index>length || (zero && index >= length)){
			index = index - length;
		}
		return index;
	}

	/**
	 * 获得list0中有而list1中没有的对象列表
	 * @param <T>
	 * @param list0
	 * @param list1
	 * @return
	 */
	public static <T extends Object> List<T> getExtList(List<T> list0,
			List<T> list1) {
		
		if(list1==null || list1.isEmpty())return list0;
		List<T> tList = new ArrayList<T>();
		if(list0!=null && !list0.isEmpty()){
			for(T t:list0){
				if(!list1.contains(t)){
					tList.add(t);
				}
			}
		}
		return tList;
	}
	/**
	 * 动态调用方法
	 * @param target
	 * @param method
	 * @param clazz
	 * @param args
	 * @return
	 */
	public static Object getInvokeReturn(Object target,String method,Object...args){
		if(target==null) return null;
		try {
			args = ValidateUtil.isNullArray(args)?new Object[]{}:args;
			Class<?>[] clazz = new Class[args.length];
			for(int i=0,j=args.length;i<j;i++){
				clazz[i] = args[i].getClass();
			}
			return target.getClass().getMethod(method, clazz).invoke(target, ValidateUtil.isNullArray(args)?new Object[0]:args);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 动态调用类静态方法
	 * @param class1
	 * @param string
	 * @param element
	 * @return
	 */
	public static Object getInvokeStaticReturn(
			Class<?> targetClazz,String method,Object... args) {
		
		try {
			args = ValidateUtil.isNullArray(args)?new Object[]{}:args;
			Class<?>[] clazz = new Class[args.length];
			for(int i=0,j=args.length;i<j;i++){
				clazz[i] = args[i].getClass();
			}
			return targetClazz.getMethod(method, clazz).invoke(targetClazz, ValidateUtil.isNullArray(args)?new Object[0]:args);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获得泛型的对象,可以设置默认值
	 * @param <T>
	 * @param clazz
	 * @param value
	 * @param t
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getEnumType(Class<T> clazz,String value, T t) {
		try{
			return (T) DataUtil.getInvokeStaticReturn(clazz, "valueOf", value);
		}catch(Exception e){
			return t;
		}
	}
}
