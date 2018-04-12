/*
 * Created on Nov 3, 2005
 */
package com.gps.util;

import com.ibm.bluepages.BPResults;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;

/**
 * @author Lane Holloway (holloway)
 *
 */
public class BluePages {
  /* Logger for the class */
  public static Logger log = Logger.getLogger(BluePages.class.getName());
  private static BluePages bluePages = null;
  
  /* Properties that are shared for all classes */
  Properties p;
  
  /* Makes a mapping of all BluePages functions to the correct url */
  Hashtable<String, String> URLMaps;
  
  /* Makes a mapping of all BluePages functions to the correct url */
  Hashtable<String, String> BPresults;
  
  public BluePages(Properties p) throws Exception {
    this.p = p;
    try {
      URLMaps = handleBluePagesLocatorCall(new URL("http://bluepages.ibm.com/BpHttpApisv3/apilocator"));
    } catch (Exception e) {
      //log.error("Unable to determine URL Mappings for BluePages API");
      throw e;
    }
  }
  
  /**
   * Checks the email address to see if it is ok
   * @param emailAddr the email address
   * @return a false if it isn't a good address, or true if it is or we can't
   *         access BluePages. We don't want to halt a form being submitted b/c of
   *         BluePages.
   */
  public boolean checkEmail(String emailAddr) throws Exception {
    URL urlCheck;
    String urlPiece;
    
    if (isNotesID(emailAddr)) {
      emailAddr = formatNotesID(emailAddr);
      urlPiece = (String)URLMaps.get("allByNotesID");
    } else {
      try {
      emailAddr = URLEncoder.encode(emailAddr,"UTF-8"); //add the enc UTF-8 ?
      } catch (Exception e) {
        //log.error(StringUtils.getStackTraceAsString(e));
      }
      urlPiece = (String)URLMaps.get("byInternetAddr");
    }
    
    try {
    urlCheck = new URL(urlPiece+emailAddr);
    } catch (MalformedURLException murle) {
      //log.error("URL was malformed: " + urlPiece+emailAddr);
      return true;  
    }
    
    try
    {
    if (handleBluePagesCall(urlCheck) != null)
      return true;
    else
      return false;
    }
    catch(Exception e)
    {
    	//log.error("Unable to communicate with Blue Pages");
    	throw e;
    }
  }
  
  /*
   * Method accepts intranetId and returns the lotus notes id
   */
  public String getNotesID(String intranetId) {
      
      String notesID = "";
      URL urlCheck;
      String urlPiece;
      
      try {
          intranetId = URLEncoder.encode(intranetId,"UTF-8"); //add the enc UTF-8 ?
      } catch (Exception e) {
          //log.error(StringUtils.getStackTraceAsString(e));
      }
      urlPiece = (String)URLMaps.get("byInternetAddr");

      try {
          urlCheck = new URL(urlPiece+intranetId);
          if (handleBluePagesCall(urlCheck) != null) {
              notesID = BPresults.get("NOTESID");
              notesID = formatPrettyNotesID(notesID);
              
          }
      } catch (MalformedURLException murle) {
          //log.error("URL was malformed: " + urlPiece+intranetId);

      } catch (Exception e) {
          //log.error("Unable to communicate with Blue Pages "+e.getMessage());
      }

      return notesID;
  }
  /*
   * ******************************************************************
   */
  
  /*
   * Method accepts notesId and returns the intranetId
   */
  public static String getIntranetIdFromNotesId(String notesId) {
      
      String internetId = "";
      //notesId = "Mary Beth Westin/Detroit/IBM";
      notesId = "CN="+notesId;
      int start = notesId.indexOf("/", 0);
      int end = notesId.indexOf("/", start+1);
      String toReplace = notesId.substring(start+1, end);
      //notesId = notesId.replace(notesId.substring(notesId.indexOf("/"), endIndex), "/OU=Contr");
      notesId = notesId.replace(toReplace, "OU="+toReplace);
      notesId = notesId.replace("/Contr", "/OU=Contr");
      notesId = notesId.replace("/AT&T", "/OU=AT&T");
      notesId = notesId.replace("/IBM", "/O=IBM@IBM");
      notesId = notesId.replace("/IDE", "/O=IDE@IBM");
      
      BPResults peopleResults = com.ibm.bluepages.BluePages.getPersonsByNotesID(notesId);
      Vector internetColumn = peopleResults.getColumn("INTERNET");
      
      internetId = peopleResults.rows() > 0 ? ((String) internetColumn.elementAt(0)).toLowerCase() : "";
      
      //internetId = "";
      //peopleResults.rows()
      
      //BluePages bp = new BluePages(null);
      //String prettyNotesId = BluePages.formatPrettyNotesID("CN=Daud Ahmad Khokhar/OU=Pakistan/OU=Contr/O=IBM@IBM");
      
      /*try {
          intranetId = URLEncoder.encode(intranetId,"UTF-8"); //add the enc UTF-8 ?
      } catch (Exception e) {
          log.error(StringUtils.getStackTraceAsString(e));
      }
      urlPiece = (String)URLMaps.get("byInternetAddr");

      try {
          urlCheck = new URL(urlPiece+intranetId);
          if (handleBluePagesCall(urlCheck) != null) {
              notesID = BPresults.get("NOTESID");
              notesID = formatPrettyNotesID(notesID);
              
          }
      } catch (MalformedURLException murle) {
          log.error("URL was malformed: " + urlPiece+intranetId);

      } catch (Exception e) {
          log.error("Unable to communicate with Blue Pages "+e.getMessage());
      }*/

      return internetId;
  }
  /*
   * ******************************************************************
   */
  
  
  /*
   * Method accepts notesId and returns the intranetId
   */
  public static String getNotesIdFromIntranetId(String intranetId) {

      String notesId = "";
      BPResults peopleResults = com.ibm.bluepages.BluePages.getPersonsByInternet(intranetId);
      Vector notesColumn = peopleResults.getColumn("NOTESID");
      notesId = peopleResults.rows() > 0 ? ((String) notesColumn.elementAt(0)) : "";
      return formatPrettyNotesID(notesId);
      
  }
  /*
   * ******************************************************************
  */
  
  
  public Hashtable<String, String> handleBluePagesCall(URL url_in) throws Exception {
    BPresults = new Hashtable<String, String>();
    
    try {
    	
      URL url = url_in;
      BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
      String inputLine;

  	  while ((inputLine = in.readLine()) != null) {  	
  	  	if (inputLine.indexOf(":") != -1) {
  	      String key = inputLine.substring(0,inputLine.indexOf(":"));
  	      String value = inputLine.substring(inputLine.indexOf(":")+1,inputLine.length()).trim();
  	      BPresults.put(key, value);
  	    }	else if (inputLine.indexOf("# rc=0, count=0, message=Success") != -1) {
  	      BPresults = null;	
  	    }
  	  }
  	  in.close();
    } catch (Exception e) {
    	e.printStackTrace();
    	throw e;
    }
    
    return BPresults;
  }
  
  public Hashtable<String, String> handleBluePagesLocatorCall(URL url_in) throws Exception {
    Hashtable<String, String> h = new Hashtable<String, String>();
    
    try {
    	
      URL url = url_in;
      BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
      String inputLine;

  	  while ((inputLine = in.readLine()) != null) {  	
  	  	if (inputLine.indexOf(":") != -1) {
  	      String key = inputLine.substring(inputLine.indexOf(":")+1,inputLine.length()).trim();
  	      inputLine = in.readLine();
  	      String value = inputLine.substring(inputLine.indexOf(":")+1,inputLine.length()).trim();
  	      h.put(key, value);
  	    }	else if (inputLine.indexOf("# rc=0, count=0, message=Success") != -1) {
  	      h = null;	
  	    }
  	  }
  	  in.close();
    } catch (Exception e) {
    	e.printStackTrace();
    	throw e;
    }
    
    return h;
  }
  
//******************************//
// Some utility methods         //
//******************************//
  
  public static boolean isNotesID(String id) {
  	if (id.indexOf("/") != -1) {
  		return true;
  	} else {
  	  return false;	
  	}
  }
  
  public String formatNotesID(String id) {
    String ans = "";
    String cn = "";
    String ou = "";
    String o = "";
    
    cn = "cn%3D"+id.substring(0,id.indexOf("/"));
    id = id.substring(id.indexOf("/")+1, id.length());
    
    //there could be multiple ou's so while id.indexOf("/") != id.lastIndexOf("/"), keep
    //adding ou's
    
    while (id.indexOf("/") != -1) {
      ou += "%2Fou%3D"+id.substring(0,id.indexOf("/"));
      id = id.substring(id.indexOf("/")+1, id.length());
    }
    
    //the o is always going to be IBM
    o = "%2Fo%3D"+id;
    ans = cn + ou + o + "%"; //the % is a wildcard
    while (ans.indexOf(" ") != -1) {
      ans = ans.substring(0,ans.indexOf(" ")) + "+" + ans.substring(ans.indexOf(" ")+1,ans.length());	
    }
    while (ans.indexOf("@") != -1) {
      ans = ans.substring(0,ans.indexOf("@")) + "%40" + ans.substring(ans.indexOf("@")+1, ans.length());	
    }
    //System.out.println("Returning a Notes ID of: " + ans);    
    return ans;
  }
  
  /**
   * Remove the cn=, ou=, o= and @IBMUS stuff from address
   * @param id the id to clean up
   * @return
   */
  public static String formatPrettyNotesID(String id) {
    while (id.toLowerCase().indexOf("cn=") != -1) {
      id = id.substring(0,id.toLowerCase().indexOf("cn="))+id.substring(id.toLowerCase().indexOf("cn=")+3,id.length());	
    }
    while (id.toLowerCase().indexOf("ou=") != -1) {
      id = id.substring(0,id.toLowerCase().indexOf("ou="))+id.substring(id.toLowerCase().indexOf("ou=")+3,id.length());	
    }
    while (id.toLowerCase().indexOf("o=") != -1) {
      id = id.substring(0,id.toLowerCase().indexOf("o="))+id.substring(id.toLowerCase().indexOf("o=")+2,id.length());	
    }
    
    
    //now hack out the @blah part
    if (id.indexOf("@") != -1) {
      id = id.substring(0,id.indexOf("@"));	
    }
    
  	return id;	
  }
  public String getInternetID(){
	  return BPresults.get("INTERNET");
  }
  
  
  /**
   * This method returns First Line Manager Intranet ID of the ID passed
   * 
   * @param id ID of the user for which First Line Manager ID is to be sought
   * @return
   */
  public static String getFirstLineMgrIntranetId(String id) {

      String emailId = id;
      if(id.contains("/")) {
          emailId = BluePages.getIntranetIdFromNotesId(id.trim());
      }

      BPResults peopleResults = com.ibm.bluepages.BluePages.getPersonsByInternet(emailId);
      if(peopleResults != null) {
    	  Vector cnumColumn = peopleResults.getColumn("CNUM");
    	  if(cnumColumn != null && cnumColumn.size() > 0) {
    		  BPResults mgrResults =  com.ibm.bluepages.BluePages.getMgrChainOf(cnumColumn.elementAt(0).toString());
    		  if(mgrResults != null) {
    			  Vector nameColumn = mgrResults.getColumn("INTERNET");
    			  if(nameColumn != null && nameColumn.size() > 0) {
    				  System.out.println((String) nameColumn.elementAt(0));
    				  //System.out.println((String) nameColumn.elementAt(1));
    				  
    				  return (String) nameColumn.elementAt(0);
    			  }
    		  }
    	  }
      }
      
      return ""; //return empty string in case of error/invalid argument value
  }
  
  /**
   * This method returns First Line Manager Lotus Notes ID of the ID passed
   * 
   * @param id ID of the user for which First Line Manager ID is to be sought
   * @return
   */
  public static String getFirstLineMgrNotesId(String id) {
	  String intranetId = getFirstLineMgrIntranetId(id);
	  if(StringUtils.isNullOrEmpty(intranetId)) {
		  return "";
	  }
	  
	  return getNotesIdFromIntranetId(intranetId);
  }
  
  public static BluePages getInstance() throws Exception {
		if (bluePages == null) {
			bluePages = new BluePages();
		}
		return bluePages;
	}
  private BluePages() throws Exception {
		try {
			URLMaps = handleBluePagesLocatorCall(new URL("http://bluepages.ibm.com/BpHttpApisv3/apilocator"));
		} catch (Exception e) {
			log.error("Unable to determine URL Mappings for BluePages API");
			throw e;
		}
	}
}
