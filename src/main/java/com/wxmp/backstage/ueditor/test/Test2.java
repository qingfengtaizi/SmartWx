package com.wxmp.backstage.ueditor.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
 * @title : 
 * @description : 
 * @projectname : wxmp
 * @classname : Test2
 * @version 1.0
 * @author : hermit
 * @createtime : 2017年7月13日 下午3:48:07
*/
public class Test2 {
   public static void main(String[] args) {
	   String s = "<p>Image 1:<img width='199' src='_image/10/label' alt=\"\"/>"
		   		+ " Image 2: "
		   		+ "<img width=\"199\" src=\"_image/11/label\" alt=\"\"/>"
		   		+ "<img width=\"199\" src=\"_image/12/label\" alt=\"\"/></p>";
	       Pattern p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
	       Matcher m = p.matcher(s);
	       // System.out.println(m.find());
	       //System.out.println(m.groupCount());
	       
	       String filePath = "";
	       while(m.find()){
	          // System.out.println(m.group()+"-------------↓↓↓↓↓↓");
	         
	           filePath = filePath + m.group(1) + ",";
	       }
	       String subFilePath = filePath.substring(0, filePath.length()-1);
	       //System.out.println(subFilePath);
	       String[] imgPathArry = subFilePath.split(",");
	       
	       String[] newPathArry = new String[imgPathArry.length];
	       for(int i=0;i<imgPathArry.length;i++){
	    	   String abc = imgPathArry[i]+"_new";
	    	   newPathArry[i] = abc;
	       }
	       
	       
//	       for(int i=0;i<newPathArry.length;i++){
//	           System.out.println(newPathArry[i]);
//	       }
	       
	       for(int i=0;i<imgPathArry.length;i++){
	    	 s =  s.replace(imgPathArry[i], newPathArry[i]);
	       }
	       
	       
	      System.out.println(s);
   }
}
