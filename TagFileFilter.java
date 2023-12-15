/***************************************************************************/
//
//      Author : Subel Sunbeeb
//      Date:  January 3, 2001
//      Class:  TagFileFilter
//      Description :  Class to implement file filter for file chooser.
//      --------------------------------------------------------------------
//			Change Log
//      --------------------------------------------------------------------
//      Date             Author         Description
//      01/22/01         SS             Make it NLS ready.
/***************************************************************************/


import javax.swing.filechooser.*;
import java.io.File;

public class TagFileFilter extends FileFilter {

	// *** NLS variables **********************************
	public final static String FILES = TagResource.getString("FILES");
	// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

  	String[] extensions;
	String description;

  	public TagFileFilter(String ext) {
   		 this (new String[] {ext}, null);
  	}

 	 public TagFileFilter(String[] exts, String descr) {
    		// clone and lowercase the extensions
    		extensions = new String[exts.length];
    		for (int i = exts.length - 1; i >= 0; i--) {
     			 extensions[i] = exts[i].toLowerCase();
    		}
    		// make sure we have a valid (if simplistic) description
   		 description = (descr == null ? exts[0] + " "+ FILES : descr);
 	 }

	  public boolean accept(File f) {
   		 // we always allow directories, regardless of their extension
   		 if (f.isDirectory()) { return true; }

    		// ok, it's a regular file so check the extension
    		String name = f.getName().toLowerCase();
   		 for (int i = extensions.length - 1; i >= 0; i--) {
    			  if (name.endsWith(extensions[i])) {
       				 return true;
     			 }
   		 }
   		 return false;
 	 }

  	public String getDescription() { return description; }
}
