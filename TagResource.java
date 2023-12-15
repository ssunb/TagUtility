
/***************************************************************************/
//
//      Author : Subel Sunbeeb
//      Date:  January 22, 2001
//      Class:  TagResource
//      Description :  Class to load Resource Bundle for Tagging Utility.
//      ---------------------------------------------------------------------
//			Change Log
//      ---------------------------------------------------------------------
//      Date        Author       Description
//
/***************************************************************************/

import java.awt.*;
import java.util.*;
import java.text.*;

public class TagResource {

  	static ResourceBundle resource = null;
  	static String  bundleName = "TagResourceBundle";
  	static String  result= null;

  	final static Object resourceSync = new Object();

	 //Conctructor
 	 public static ResourceBundle getBundle() {
    		synchronized(resourceSync) {
       	  	         if (resource == null) {
           			 // ... load it now
       			  try { resource = ResourceBundle.getBundle(bundleName);}
          			  // ... reporting failure to load the ResourceBundle
      			   catch(MissingResourceException e) {
         			              System.out.println("ERROR: "+ e.getMessage());
                                                         }
                                              }
        		}
       		return resource;
        	}


 	 public static String getString (String key){
         		ResourceBundle resource = getBundle();
          		 if (resource == null){
               			System.out.println("ERROR:  No Resource (null) for '"+ key + "'.");
		 }
           		 else {
               		         try {
                  			 result = resource.getString(key);
               		         }
		        catch(MissingResourceException e) {
                   		 System.out.println("ERROR: No Resource  for  '"+ key +"'.  "+ e.getMessage());
                		         }
		 }

        		  return result;
             	 }
  }