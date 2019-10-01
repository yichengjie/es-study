package com.yicj.study.common;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonUtil {
	public static String local_sp = File.separator ;
	public static String server_sp = "/" ;
	
	
	public static String joinLocalPath(String basePath,String subPath) {
		return joinPath(basePath, subPath, local_sp) ;
	}
	
	public static String joinServerPath(String basePath,String subPath) {
		return joinPath(basePath, subPath, server_sp) ;
	}
	
	private static String joinPath(String basePath,String subPath, String sp) {
		basePath = dealPath(basePath, sp) ;
		subPath = dealPath(subPath, sp) ;
		return basePath +sp + subPath ;
	}
	
	private static String dealPath(String path, String sp) {
		path = dealNull(path) ;
		if(!path.endsWith(sp)) {
			return path ;
		}
		return dealPath(path.substring(0,path.length()-1), sp) ;
	}

	public static String dealNull(String str) {
		if(str == null) {
			return "" ;
		}
		return str.trim() ;
	}
	
    public static String json2xml(String json) throws JSONException {
        JSONObject jsonObj = new JSONObject(json);
        return "<xml>" + XML.toString(jsonObj) + "</xml>";
    } 
    public static JSONObject xml2json(String xml) throws JSONException {
    	xml = xml.replace("<xml>", "").replace("</xml>", "") ;
        JSONObject xmlJSONObj = XML.toJSONObject(xml);
        return xmlJSONObj;
    }
    
    
    public static JSONObject msgToJsonObject(String message) throws JSONException {
    	JSONObject json = new JSONObject() ;
    	json.put("msg", message) ;
    	return json ;
    }
    
    
   
    
    public static void dump(InputStream in, OutputStream out) throws IOException {
    	BufferedInputStream bin = new BufferedInputStream(in) ;
    	byte [] buf = new byte[1024] ;
    	int len = -1 ;
    	try {
    		while( (len = bin.read(buf)) != -1 ) {
        		out.write(buf,0,len);
        	}
		} finally {
			IOUtils.closeQuietly(out);
			IOUtils.closeQuietly(in);
		}
    }

    public static boolean isEmpty(String str){
		if(str == null || str.trim().length() == 0){
			return true ;
		}
		return false;
	}

    public static String readFile2OneLine(String basePath ,String fileName,String charset)  {
    	FileInputStream fis = null ;
    	FileChannel fc = null ;
		try {
			File file = new File(basePath,fileName) ; 
			fis =  new FileInputStream(file);
	        fc = fis.getChannel();
	        ByteBuffer bb = ByteBuffer.allocate(new Long(file.length()).intValue());
	        //fc向buffer中读入数据
	        fc.read(bb);
	        bb.flip();
	        String str=new String(bb.array(),charset);
	        return str;
		} catch (Exception e) {
			throw new RuntimeException(e) ;
		}finally {
			IOUtils.closeQuietly(fc);
			IOUtils.closeQuietly(fis);
		}
    }
    
   /* public static String readFileToOneLine(
    		String basePath ,String fileName,String charset) {
		StringBuilder builder = new StringBuilder();
		File file = new File(basePath,fileName) ;
		FileInputStream in = null ;
		BufferedReader br = null ;
		try {
			in = new FileInputStream(file) ;
			br = new BufferedReader(new InputStreamReader(in,charset)) ;
			String line = null ;
			while ((line = br.readLine()) != null){
				builder.append(dealNull(line)) ;
			}
		} catch (IOException e) {
			log.error("读取文件["+CommonUtil.joinLocalPath(basePath, fileName)+"]出错:",e);
			return "读取文件["+CommonUtil.joinLocalPath(basePath, fileName)+"]出错:" +e.getMessage() ;
		} finally {
			IOUtils.closeQuietly(br);
			IOUtils.closeQuietly(in);
		}
		String retStr = builder.toString();
		return retStr ;
	}*/
    
    
    public static void main(String[] args) throws JSONException {
		/*String basePath = "E:\\code2\\java8\\company\\step4" ;
		String fileName = "test.xml" ;*/
    	String basePath = "D:\\opt\\data" ;
		String fileName = "test.xml" ;
		String charset = "gbk" ;
		String xmlContent = readFile2OneLine(basePath, fileName, charset) ;
    	System.out.println(CommonUtil.xml2json(xmlContent));
	}
}
