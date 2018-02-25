package com.wxmp.backstage.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;

import org.apache.commons.io.FileUtils;

import com.wxmp.backstage.common.Identities;

/**
 * 功能：文件操作工具类
 * @author 
 *
 * mobile enterprise application platform
 * Version 0.1
 */
public class FileUtil {
	
	/**
	 * 获得去除扩展名的文件名
	 * @param oldFilename
	 * @return
	 */
	public static String removeExtension(String oldFilename) {
		final File oldFile = new File(oldFilename);
		final int iExtPos = oldFile.getName().lastIndexOf('.');
		if (iExtPos != -1) {
			final String parentPath = oldFilename.substring(0,
			oldFilename.length() - oldFile.getName().length());
			return parentPath + oldFile.getName().substring(0, iExtPos);
		}
		return oldFilename;
	}
	/**
	 * 返回文件名(比如abc.jpg)
	 * @param path
	 * @return
	 */
	public static String getFileName(String path){
		if(ValidateUtil.isNull(path))return null;
		final int p = path.lastIndexOf(File.separatorChar);
		if (p != -1) {
			return path.substring(p + 1);
		} else {
			return "";
		}
	}
	
	/**
	 * 返回文件名
	 * @param file 文件对象
	 * @param prefixDir 取文件地址前缀
	 * @return
	 */
	public static String getFileName(File file,File prefixDir){
		if(file==null)return null;
		if(prefixDir==null){
			return file.getName();
		}else{
			return StringUtil.substring(file.getPath(), prefixDir.getPath().length()+1, 0xFFFF);
		}
	}
	
	/**
	 * 返回文件所在目录
	 * @param path
	 * @return
	 */
	public static String getFileDirectory(String path){
		if(ValidateUtil.isNull(path))return null;
		final int p = path.lastIndexOf(File.separatorChar);
		if (p != -1) {
			return path.substring(0, p);
		} else {
			return "";
		}
	}
	/**
	 * 更改扩展名
	 * @param oldFilename
	 * @param newExtension
	 * @return
	 */
	public static String changeExtension(String oldFilename, String newExtension) {
		String result = removeExtension(oldFilename);
		if (newExtension.length() != 0) {
			result = StringUtil.concat(result,".",newExtension);
		}	
		return result;
	}
	/**
	 * 获得文件扩展名
	 * @param file
	 * @return
	 */
	public static String getExtension(File file) {
		final int ipos = file.getName().lastIndexOf(".");
		if (ipos != -1) {
			return file.getName().substring(ipos + 1);
		} else {
			return StringUtil.EMPTY_STRING;
		}
	}
	
	/**
	 * 获得文件的扩展名
	 * @param fileName
	 * @return
	 */
	public static String getExtension(String fileName){
		if(ValidateUtil.isNull(fileName))return "";
		return fileName.substring(fileName.lastIndexOf(".")+1);
	}
	/**
     * 获得文件格式问yyyyMMddHHmmss的文件名
     *
     * @return String format as "yyyyMMddHHmmss"
     */
    public static String getTempFileName() {
    	return Identities.uuid()+".tmp";
    }
    
    /**
     * 剪切文件
     * @param sourceFile
     * @param destFile
     */
    public static void cutFile(File sourceFile, File destFile){
    	copyFile(sourceFile,destFile);
    	sourceFile.delete();
    }
    /**
     * 复制文件
     * @param sourceFile
     * @param destFile
     * @throws IOException
     */
    public static void copyFile(File sourceFile, File destFile){
    	try{
    		if (sourceFile.equals(destFile)) {
        		return;
        	}
        	if (!destFile.getParentFile().exists() &&
        			!destFile.getParentFile().mkdirs()) {
        		throw new IOException("Cannot create directory " + destFile.getParent());
        	}
        	final int BUFFER = 2048;
        	BufferedInputStream source = new BufferedInputStream(new FileInputStream(
        			sourceFile), BUFFER);
    	    try {
    	    	BufferedOutputStream dest = new BufferedOutputStream(new FileOutputStream(destFile), BUFFER);
    	    	try {
    	    		int count;
    	    		byte data[] = new byte[BUFFER];
    	    		while ( (count = source.read(data, 0, BUFFER)) != -1) {
    	    			dest.write(data, 0, count);
    	    		}
    	    	}finally {
    	    		FileUtil.close(dest);
    	    	}
    	    }finally {
    	    	FileUtil.close(source);
    	    }
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    /**
     * 删除子目录和子文件
     * @param directory
     * @return
     */
    public static boolean deleteChildren(File directory) {
    	if(!directory.canRead())return false;
    	File[] files = directory.listFiles();
    	for (int i = 0; i < files.length; i++) {
    		if (files[i].isDirectory()) {
    			if (!deleteChildren(files[i]) || !files[i].delete()) {
    				return false;
    			}
    		} else if (!files[i].delete()) {
    			return false;
    		}
    	}
    	return directory.delete();
    }
    /**
     * 复制文件夹包括里面的文件
     * @param fromDirectory
     * @param toDirectory
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void copyDirectory(File fromDirectory, File toDirectory) throws FileNotFoundException, IOException {
    	if (!toDirectory.exists() && !toDirectory.mkdirs()) {
    		throw new IllegalStateException("Cannot create directory " + toDirectory.getAbsolutePath());
    	}
    	File[] fromFiles = fromDirectory.listFiles();
    	for (int i = 0; i < fromFiles.length; i++) {
    		File toFile = new File(toDirectory, fromFiles[i].getName());
    		if (fromFiles[i].isFile()) {
    			copyFile(fromFiles[i], toFile);
    		}else {
    			copyDirectory(fromFiles[i], toFile);
    		}
    	}
    }
    /**
     * 删除文件
     * @param files
     * @return
     */
    public static boolean delete(String... files){
    	List<File> list = new ArrayList<File>();
    	for(String file : files)
    		list.add(new File(file));
    	return delete(list);
    }
    /**
     * 删除指定文件
     * @param file
     * @return
     */
    public static boolean delete(File file){
    	return delete(DataUtil.getList(file));
    }
    /**
     * 删除文件数组
     * @param files
     * @return
     */
    public static boolean delete(List<File> files) {
    	for (File file :files) {
    		if(file.exists()){
    			if(file.isFile()){
        			file.delete(); 
        		}else if(file.isDirectory()){
        			File fs[] = file.listFiles(); 
        		    for(int i=0;i<fs.length;i++){
        		    	delete(fs[i]); 
        		    } 
        		} 
        		file.delete();
    		}else{
    			return false;
    		}
    	}
    	return true;
    }
    /**
     * 获得文件夹中的文件列表
     * @param directory
     * @return
     */    
	public static List<File> getFiles(File directory) {
    	return getFiles(directory,null);
    }
    
    /**
     * 获得文件夹中的指定规则的文件列表
     * @param directory
     * @param regex
     * @return
     */
    public static List<File> getFiles(File directory,String regex){
    	if(!directory.isDirectory())return Collections.emptyList();
    	List<File> result = new ArrayList<File>();
    	File[] files = directory.listFiles();
    	for (int i = 0; i < files.length; i++) {
    		if (files[i].isFile() && (ValidateUtil.isNull(regex) || ValidateUtil.isRegex(files[i].getName(), regex))) {
    			result.add(files[i]);
    		} else if(files[i].isDirectory()){
    			result.addAll(getFiles(files[i],regex));
    		}
    	}
    	return result;
    }
    
    /**
     * 读取文件到stream
     * @param file
     * @return
     */
    public static byte[] readFileStream(File file){
    	return readFileStream(file.getAbsolutePath());
    }
    
    /**
     * 读取文件到stream
     * @param path
     * @return
     */
    public static byte[] readFileStream(String path){
    	FileInputStream fis = null;
    	byte[] bytes = null;
    	try{
    		fis = new FileInputStream(path);
    		bytes = new byte[fis.available()];
    		fis.read(bytes);
    	}catch(Exception e){    		
    	}finally{
    		FileUtil.close(fis);
    	}
    	return bytes;
    }
    
    /**
     * 字节数组转换成输入流
     * @param buf
     * @return
     */
    public static InputStream byteArray2InputStream(byte[] buf) {   
        return new ByteArrayInputStream(buf);   
    }   
  
    /**
     * 输入流转byte数组
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] inputStream2ByteArray(InputStream inputStream)   
            throws IOException {   
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();   
        byte[] buff = new byte[100];   
        int rc = 0;   
        while ((rc = inputStream.read(buff, 0, 100)) > 0) {   
            swapStream.write(buff, 0, rc);   
        }   
        byte[] in2b = swapStream.toByteArray();   
        return in2b;   
    } 
    
    /**
     * 读取文件
     * @param filename
     * @return
     * @throws IOException
     */
    public static String readFile(String filename) {
    	return readFile(new File(filename));
    }
    
    /**
     * 读取文件内容
     * @param file
     * @return
     */
    public static String readFile(File file){
    	String text = null;
    	try {
			text = FileUtils.readFileToString(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return text;
    }
    
    /**
     * 读出文件到output
     * @param file
     * @param output
     * @throws IOException
     */
    public static void readFile(File file, OutputStream output) throws IOException {
        FileInputStream input = null;
        FileChannel fc = null;
        try {
            input = new FileInputStream(file);
            fc = input.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(4096);
            for(;;) {
                buffer.clear();
                int n = fc.read(buffer);
                if(n==(-1))break;
                output.write(buffer.array(), 0, buffer.position());
            }
        }finally {
        	FileUtil.close(fc);
        	FileUtil.close(input);
        }
    }
    
    /**
     * 读取文件
     * @param file
     * @param encoding
     * @return
     */
    public static String readFile(File file,String encoding){
    	String text = null;
    	try {
			text = FileUtils.readFileToString(file, encoding);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return text;
    }
    
    /**
     * 读取文件到数组每行一个数组中的字符串
     * Read file to array of String.
     * @param file
     * @return
     * @throws IOException
     */
	public static String[] readFileToArray(File file) throws IOException {
    	List<String> lines = FileUtils.readLines(file);
    	return lines.toArray(new String[lines.size()]);
    }
    
    /**
     * 写出文件
     * @param file
     * @param text
     * @param encoding
     * @throws IOException
     */
    public static void writeFile(String filePath,String text,String encoding)throws IOException{
    	File file = new File(filePath);
    	FileUtil.writeFile(file, text, encoding);
    }
    
    /**
     * 写出文件
     * @param file
     * @param text
     * @param encoding
     * @throws IOException
     */
    public static void writeFile(File file,String text,String encoding)throws IOException{
    	FileUtils.writeStringToFile(file, text, encoding);
    }
    
    /**
     * 验证文件是否存在
     * @param strings 文件地址组合 strings[0] + strings[1] +strings[2]
     */
	public static boolean exists(Object...objs) {		
		try{			
			return new File(StringUtil.concat(objs)).exists();
		}catch(Exception e){
			return false;
		}
	}
	/**
	 * 查看文件件是否存在不存就创建
	 * @param thisImageDir
	 */
	public static void mkdirs(String dir) {
		if(!(new File(dir).isDirectory())){
            try{
                new File(dir).mkdirs();
            }catch(SecurityException e){
            	e.printStackTrace();
            }
        }
	}
	
	/**
	 * 写出文件
	 * @param filePath
	 * @param _byte
	 */
	public static void writeFile(String filePath,byte[] _byte){
		ByteArrayInputStream bais = new ByteArrayInputStream(_byte);
		try{
			writeFile(new File(filePath),bais);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			FileUtil.close(bais);
		}
	}
	/**
	 * 写出文件
	 * @param file
	 * @param is
	 * @param encoding
	 */
	public static void writeFile(File file, InputStream is) {		
		OutputStream os = null;
		try {
			os = new FileOutputStream(file);
			int size = is.available();
			for (int j = 0; j < size; j++) {
				os.write(is.read());
			}
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		} catch (IOException e) {			
			e.printStackTrace();
		}finally{
			FileUtil.close(os);
			FileUtil.close(is);
		}
	}
	
	/**
	 * 获得文件大小
	 * @param f
	 * @return
	 */
	public static long getFileSize(File f) {
		
		if(f==null || !f.exists())return 0;
		long s=0;
		FileInputStream fis = null;
		try{
			fis = new FileInputStream(f);
        	s = fis.available();        	
        }catch(IOException e){
        	e.printStackTrace();
        }finally{
        	FileUtil.close(fis);
        }
        return s;
	}
	/**
	 * 获得文件的KB大小
	 * @param f
	 * @return
	 */
	public static BigDecimal getKBFileSize(File f){
		return BigDecimal.valueOf(getFileSize(f)).divide(BigDecimal.valueOf(1024)).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 获得文件的MB大小
	 * @param f
	 * @return
	 */
	public static BigDecimal getMBFileSize(File f){
		return getKBFileSize(f).divide(BigDecimal.valueOf(1024)).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 获得友好的文件大小
	 * @param file
	 * @return
	 */
	public static String getFriendlyFileSize(File file){
		long size = getFileSize(file);
		if(size < 1024){
			return StringUtil.concat(BigDecimal.valueOf(size).setScale(2, BigDecimal.ROUND_HALF_UP),"B");
		}else if(size < 1024*1024){
			return StringUtil.concat(BigDecimal.valueOf(size).divide(BigDecimal.valueOf(1024)).setScale(2, BigDecimal.ROUND_HALF_UP),"KB");
		}else if(size < 1024*1024*1024){
			return StringUtil.concat(BigDecimal.valueOf(size).divide(BigDecimal.valueOf(1024)).divide(BigDecimal.valueOf(1024)).setScale(2, BigDecimal.ROUND_HALF_UP),"MB");
		}else{
			return StringUtil.concat(BigDecimal.valueOf(size).divide(BigDecimal.valueOf(1024)).divide(BigDecimal.valueOf(1024)).divide(BigDecimal.valueOf(1024)).setScale(2, BigDecimal.ROUND_HALF_UP),"GB");
		}
	}
	
	/**
	 * 替换文件的分隔符
	 * @param removeExtension
	 * @return
	 */
	public static String replaceSeparator(String src,String to) {		
		return StringUtil.getNonNull(src).replaceAll( Matcher.quoteReplacement(File.separator), to);
	}

	/**
	 * 获得文本的行数
	 * @param file
	 * @return
	 */
	public static int getFileLines(File file){
		int lines = 0;
		if(file.canRead()){
			BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader(file));
				while (br.readLine() != null) {
					lines ++ ;
				}
				br.close();
			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
			} catch (IOException e) {
				
				e.printStackTrace();
			} finally{
				FileUtil.close(br);
			}
		}
		return lines;
	}
	
	/**
	 * 读取远程文件到本地
	 * @param fileUrl 远程文件url 如：http://www.baidu.com/1.jpg
	 * @param localFilePath 本地硬盘绝对路径 c:/1.jpg
	 * @return
	 */
	public static boolean readDistanceFile(String fileUrl, String localFilePath) {
		java.io.BufferedInputStream bis = null;
		OutputStream bos = null;
		URL url = null;
		try {
			// 实例化url
			url = new URL(fileUrl);
			// 载入图片到输入流
			bis = new BufferedInputStream(url.openStream());
			// 实例化存储字节数组
			byte[] bytes = new byte[100];
			// 设置写入路径以及图片名称
			bos = new FileOutputStream(new File(localFilePath));
			int len;
			while ((len = bis.read(bytes)) > 0) {
				bos.write(bytes, 0, len);
			}
			return true;
		} catch (Exception e) {
			// 如果图片未找到
			return false;
		} finally {
			// 关闭输出流
			FileUtil.close(bis);
			FileUtil.close(bos);
		}

	}
	
	/**
	 * 获得文件编码
	 * @param file
	 * @return
	 */
	public static String getFileCharacterEncoding(File file){
		return CharsetUtil.getLocalteFileEncode(file);
	}
	
	/**
	 * 关闭流
	 * @param os
	 */
	public static void close(Closeable os){
		if(os!=null){
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 输入流进行MD5散列
	 * @param input
	 * @return
	 * @throws IOException
	 */
	public static byte[] md5(InputStream input) throws IOException {
		return HashUtil.digest(input, HashUtil.MD5);
	}
	
	/**
	 * 输入流进行SHA散列
	 * @param input
	 * @return
	 * @throws IOException
	 */
	public static byte[] sha1(InputStream input) throws IOException {
		return HashUtil.digest(input, HashUtil.SHA1);
	}
	
}
