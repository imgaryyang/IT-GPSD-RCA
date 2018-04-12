package com.gps.util;

import org.apache.log4j.Logger;

import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;


public class StringUtils {
  static Logger log = Logger.getLogger(StringUtils.class.getName());
  
  /**
   * Get the stack trace of an Exception as a String
   * 
   * @param e the Exception to get the StackTrace from
   * @return the Stack Trace as a String
   */
  public static String getStackTraceAsString(Exception e) {
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw, true);
    e.printStackTrace(pw);
    pw.flush();
    sw.flush();
    return sw.toString();
  }
  
  public static boolean containedInCSV(String value, String csvString){
	  int ptr=0, s1=0;
	  String index="";
	  while((ptr=csvString.indexOf(",",s1))!=-1){
			index=csvString.substring(s1,ptr).trim();
			if(index.equals(value.trim())) return true;
			s1=ptr+1;
	  }
	  index=csvString.substring(s1,csvString.length()).trim();
	  if(index.equals(value.trim())) return true;
	  return false;
  }
  
  public static String trim(String value){
	  if(value==null) return "";
	  else return value.trim();
  }
  
  public static String addToCSVString(String base, String addValue){
	  String output = base;
	  if(output == null){
		  output = addValue;
	  }
	  else{
		  output = output +","+ addValue;
	  }
	  return output;
  }
  
  public static boolean isNullOrEmpty(String value){
	  if(value==null) return true;
	  else if(value.trim().equals("")) return true;
	  return false;
  }
  
  
  public static String readSingleFile(String pathAndName) {
    InputStream is = StringUtils.class.getResourceAsStream(pathAndName);
    InputStreamReader isr = new InputStreamReader(is);
    BufferedReader br = new BufferedReader(isr);
    
    String s;
    StringBuffer file = new StringBuffer();
    try {
      while ((s = br.readLine()) != null) {
        file.append(s);
        file.append("\n");
      }
    } catch (Exception e) {
      log.error("Error reading \"" + pathAndName + "\" " + getStackTraceAsString(e));
    } finally {
      try {
        br.close();
      } catch (Exception e) {
        log.error("Error closing \"" + pathAndName + "\" " + getStackTraceAsString(e));
      }
    }
    
    return file.toString();
  }
  

  
  public static String replaceAll(String data, Hashtable keys) {
//	log.debug(""+data);
    StringBuffer sb = new StringBuffer();
    
    int init = 0, lastCopy = 0, first, second;
//    log.debug("first accurance of $: "+data.indexOf("$"));
    while ((first = data.indexOf("$",init)) != -1) {
      second = data.indexOf("$",first+1);
      if (second == -1) {
//    	  log.debug("weired, but no end of $");
        break;
      } else {
    	//first+1 to skip the $ itself.
        String key = data.substring(first+1,second);
//        log.debug("key = > "+key);
        if (keys.get(key) != null) {
          sb.append(data.substring(lastCopy,first));
          sb.append(keys.get(key));
          init = second + 1;
          lastCopy = second + 1;
        } else {
//        	log.debug(key+" not found.");
        	init = second;
        }
      }
    }
    if (lastCopy < data.length())
      sb.append(data.substring(lastCopy));
    log.trace(""+sb.toString());
    return sb.toString();
  }
  
  public static String fixNullToEmptyString(String s) {
    if (s == null)
      return "";
    else 
      return s;
  }
  
  public static String fixNullToEmptyString(Date s) {
	    if (s == null)
	      return "";
	    else 
	      return s.toString();
	  }
  
 
 
  
  /**
   * Prepare a String for use in the html page.  
   * '&', '<', '>', '\', ''', and '"' are the characters that
   * get replaced.
   * @param s the String to change
   * @return the String with all offending characters removed
   */
  public static String htmlEncode(String s) {
    String output = s;
    output = output.replaceAll("&", "&amp;");
    output = output.replaceAll("<", "&lt;");
    output = output.replaceAll(">", "&gt;");
    output = output.replaceAll("\"", "&quot;");
    output = output.replaceAll("'", "&rsquo;");
    output = output.replaceAll("�", "&lsquo;");
    return output;
  }
  
  public static String htmlDecode(String s) {
	    String output = s;
	    output = output.replaceAll("&amp", "&");
	    output = output.replaceAll("&lt;", "<");
	    output = output.replaceAll("&gt;", ">");
	    output = output.replaceAll("&quot;", "\"");
	    output = output.replaceAll("&rsquo;", "'");
	    output = output.replaceAll("&lsquo;", "�");
	    return output;
	  }
  
  public static String URLEncode(String str) {
    String encoded = "";
    if (str == null)
      return encoded;
    try {
      encoded = URLEncoder.encode(str,"UTF8");
    } catch (Exception e) {
      log.error(getStackTraceAsString(e));
    }
    return encoded;
  } 
  
  public static String URLDecode(String str) {
    String decoded = "";
    if (str == null)
      return decoded;
    try {
      decoded = URLDecoder.decode(str,"UTF8");
    } catch (Exception e) {
      log.error(getStackTraceAsString(e));
    }
    return decoded;
  }
  
  public static String[] URLDecode(String[] data) {
    if (data == null)
      return null;
    String[] d = new String[data.length];
    for (int i = 0; i < data.length; i++)
      d[i] = URLDecode(data[i]);
    return d;
  }

  /**
   * Checks if a String is empty ("") or null.
   *
   * @param str  the String to check, may be null
   * @return true if the String is empty or null
   */
  public static boolean isEmpty(String str) {
      return str == null || str.length() == 0;
  }

  /**
   * Counts how many times the substring appears in the larger String.
   *
   * A null or empty ("") String input returns 0.
   *
   * StringUtils.countMatches(null, *)       = 0
   * StringUtils.countMatches("", *)         = 0
   * StringUtils.countMatches("abba", null)  = 0
   * StringUtils.countMatches("abba", "")    = 0
   * StringUtils.countMatches("abba", "a")   = 2
   * StringUtils.countMatches("abba", "ab")  = 1
   * StringUtils.countMatches("abba", "xxx") = 0
   *
   * @param str  the String to check, may be null
   * @param sub  the substring to count, may be null
   * @return the number of occurrences, 0 if either String is null
   */
  public static int countMatches(String str, String sub) {
      if (isEmpty(str) || isEmpty(sub)) {
          return 0;
      }
      int count = 0;
      int idx = 0;
      while ((idx = str.indexOf(sub, idx)) != -1) {
          count++;
          idx += sub.length();
      }
      return count;
  }
  
  public static String cleanString(byte[] data) {
      LineNumberReader lnr = new LineNumberReader(new InputStreamReader(new ByteArrayInputStream(data)));
      StringBuffer all = new StringBuffer();
      String r;
      try {
        while ((r = lnr.readLine()) != null) {
          all.append(r);
        }
      } catch (Exception e) {
        
      } finally{
        try {
          lnr.close();
        } catch (Exception ee) {
          
        }
      }
      String t = all.toString();
      //control codes from: http://www.w3schools.com/tags/ref_ascii.asp
      return t.replaceAll("&#(?:0[0-8]|1[1-2]|1[4-9]|2[0-9]|3[0-1]);"," ");
  }
  
  public static String listToString(List<?> list) {
	  Object[] objs = list.toArray(new Object[list.size()]);
	  return Arrays.toString(objs);
  }

    public static String[] parseCSVLine(String csvLine) {
        String[] parsedLine = null;
        try {
            //now parse the argList w/ the csv reader
            CSVReader csvr = new CSVReader(new StringReader(csvLine+"\r\n"));
            parsedLine = csvr.getLine();
        } catch (Exception e) {
            log.error(StringUtils.getStackTraceAsString(e));
        }
        return parsedLine;
    }

    public static List<String> commaSepareatedToList(String commaSepareated){
    	List<String> items = new ArrayList<String>();
    	if(commaSepareated == null || commaSepareated.isEmpty()){
    		return items;
    	}
    	items = Arrays.asList(commaSepareated.split("\\s*,\\s*"));
    	return items;
    }
    
    public static String getCommaSepareated(List<String> list){
    	String separator = ",";
    	StringBuilder sb = new StringBuilder();
    	for (String s : list) {
    	    sb.append(separator).append(s);
    	}
    	return sb.toString();
    }
    
    public static String getCommaSepareated(Map<String, String> list){
    	String separator = ",";
    	StringBuilder sb = new StringBuilder();
    	if(list == null || list.isEmpty()){
    		return sb.toString();
    	}
    	for (String s : list.keySet()) {
    	    sb.append(separator).append(s);
    	}
    	return sb.toString();
    }
    
    public static Map<String, String> commaSepareatedToMap(String commaSepareated){
    	List<String> items = new ArrayList<String>();
    	Map<String, String> map = new HashMap<String, String>();
    	if(commaSepareated == null || commaSepareated.isEmpty()){
    		return map;
    	}
    	items = Arrays.asList(commaSepareated.split("\\s*,\\s*"));
    	for(String s : items){
    		map.put(s, s);
    	}
    	
    	return map;
    }
    
    public static boolean isNotBlank(String str){
        return org.apache.commons.lang.StringUtils.isNotBlank(str);

    }
    public static String toLowerCase(String str){
        return org.apache.commons.lang.StringUtils.lowerCase(str);
    }

    public static boolean isBlank(String str){
        return org.apache.commons.lang.StringUtils.isBlank(str);
    }




}
