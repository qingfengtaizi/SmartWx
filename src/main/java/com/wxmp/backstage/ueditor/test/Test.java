package com.wxmp.backstage.ueditor.test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
 * @title : 
 * @description : 
 * @projectname : wxmp
 * @classname : Test
 * @version 1.0
 * @author : hermit
 * @createtime : 2017年7月13日 下午2:36:46
*/
public class Test {
   public static void main(String[] args) {
	   String s = "/wxmp/res/js/plugins/ueditor/jsp/upload/image/20160708/1467970636527089999.jpg";
	   s  = s.substring(s.indexOf('/', 2)+1, s .length());
       System.out.println(s);
   }
   
   public static void StringControl(String s){
       String[] ma = Pattern.compile("<IMG.*src=(.*?)[^>]*?>").split(s);
       
       List<String> strL=new ArrayList<String>();
       for(int i=0;i<ma.length;i++){
           System.out.println(ma[i]);
       }
   }
}
