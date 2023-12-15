
/***************************************************************************/
//
//      Author : Subel Sunbeeb
//      Date:  January 3, 2001
//      Class:  TagExecute
//      Description :  Class to implement insert / remove / update tags.
//      -----------------------------------------------------------------------------------------------
//			Change Log
//      -----------------------------------------------------------------------------------------------
//      Date            Author      Description
//      01/22/01        SS          Make it NLS ready.
//      01/31/01        SS          Fix typo (MESSAGE33).
//      02/14/01        SS          Add MESSAGE55, and MESSAGE56.
//      02/27/01        SS          Changes for user choices:  inclue subdir and include dhtml pages.
//      03/01/01        SS          Add wildcard ('*')  in file search.
//      03/22/01        SS          User 'line.separator' instead of '\r\n'.
//      05/08/01        SS          Add findEndBracket().
//      05/18/01        SS          Update findEndBracket(), dynamicHtml(), and insertTag()
/***************************************************************************/


import java.io.*;
import javax.swing.*;
import java.util.Vector;
import java.util.Date;
import java.lang.reflect.InvocationTargetException;

public class TagExecute {

	// *** NLS variables ***********************************************************************
	public final static String ERROR1 = TagResource.getString("TagExecute_ERROR1");
	public final static String ERROR2 = TagResource.getString("TagExecute_ERROR2");
	public final static String ERROR3 = TagResource.getString("TagExecute_ERROR3");
	public final static String ERROR4 = TagResource.getString("TagExecute_ERROR4");
	public final static String MESSAGE1 = TagResource.getString("TagExecute_MESSAGE1");
	public final static String MESSAGE2 = TagResource.getString("TagExecute_MESSAGE2");
	public final static String MESSAGE3 = TagResource.getString("TagExecute_MESSAGE3");
	public final static String MESSAGE4 = TagResource.getString("TagExecute_MESSAGE4");
	public final static String MESSAGE5 = TagResource.getString("TagExecute_MESSAGE5");
	public final static String MESSAGE6 = TagResource.getString("TagExecute_MESSAGE6");
	public final static String MESSAGE7 = TagResource.getString("TagExecute_MESSAGE7");
	public final static String MESSAGE8 = TagResource.getString("TagExecute_MESSAGE8");
	public final static String MESSAGE9 = TagResource.getString("TagExecute_MESSAGE9");
	public final static String MESSAGE10 = TagResource.getString("TagExecute_MESSAGE10");
	public final static String MESSAGE11 = TagResource.getString("TagExecute_MESSAGE11");
	public final static String MESSAGE12 = TagResource.getString("TagExecute_MESSAGE12");
	public final static String MESSAGE13 = TagResource.getString("TagExecute_MESSAGE13");
	public final static String MESSAGE14 = TagResource.getString("TagExecute_MESSAGE14");
	public final static String MESSAGE15 = TagResource.getString("TagExecute_MESSAGE15");
	public final static String MESSAGE16 = TagResource.getString("TagExecute_MESSAGE16");
	public final static String MESSAGE17 = TagResource.getString("TagExecute_MESSAGE17");
	public final static String MESSAGE18 = TagResource.getString("TagExecute_MESSAGE18");
	public final static String MESSAGE19 = TagResource.getString("TagExecute_MESSAGE19");
	public final static String MESSAGE20 = TagResource.getString("TagExecute_MESSAGE20");
	public final static String MESSAGE21 = TagResource.getString("TagExecute_MESSAGE21");
	public final static String MESSAGE22 = TagResource.getString("TagExecute_MESSAGE22");
	public final static String MESSAGE23 = TagResource.getString("TagExecute_MESSAGE23");
	public final static String MESSAGE24 = TagResource.getString("TagExecute_MESSAGE24");
	public final static String MESSAGE25 = TagResource.getString("TagExecute_MESSAGE25");
	public final static String MESSAGE26 = TagResource.getString("TagExecute_MESSAGE26");
	public final static String MESSAGE27 = TagResource.getString("TagExecute_MESSAGE27");
	public final static String MESSAGE28 = TagResource.getString("TagExecute_MESSAGE28");
	public final static String MESSAGE29 = TagResource.getString("TagExecute_MESSAGE29");
	public final static String MESSAGE30 = TagResource.getString("TagExecute_MESSAGE30");
	public final static String MESSAGE31 = TagResource.getString("TagExecute_MESSAGE31");
	public final static String MESSAGE32 = TagResource.getString("TagExecute_MESSAGE32");
	public final static String MESSAGE33 = TagResource.getString("TagExecute_MESSAGE33");
	public final static String MESSAGE34 = TagResource.getString("TagExecute_MESSAGE34");
	public final static String MESSAGE35 = TagResource.getString("TagExecute_MESSAGE35");
	public final static String MESSAGE36 = TagResource.getString("TagExecute_MESSAGE36");
	public final static String MESSAGE37 = TagResource.getString("TagExecute_MESSAGE37");
	public final static String MESSAGE38 = TagResource.getString("TagExecute_MESSAGE38");
	public final static String MESSAGE39 = TagResource.getString("TagExecute_MESSAGE39");
	public final static String MESSAGE40 = TagResource.getString("TagExecute_MESSAGE40");
	public final static String MESSAGE41 = TagResource.getString("TagExecute_MESSAGE41");
	public final static String MESSAGE42 = TagResource.getString("TagExecute_MESSAGE42");
	public final static String MESSAGE43 = TagResource.getString("TagExecute_MESSAGE43");
	public final static String MESSAGE44 = TagResource.getString("TagExecute_MESSAGE44");
	public final static String MESSAGE45 = TagResource.getString("TagExecute_MESSAGE45");
	public final static String MESSAGE46 = TagResource.getString("TagExecute_MESSAGE46");
	public final static String MESSAGE47 = TagResource.getString("TagExecute_MESSAGE47");
	public final static String MESSAGE48 = TagResource.getString("TagExecute_MESSAGE48");
	public final static String MESSAGE49 = TagResource.getString("TagExecute_MESSAGE49");
	public final static String MESSAGE50 = TagResource.getString("TagExecute_MESSAGE50");
	public final static String MESSAGE51 = TagResource.getString("TagExecute_MESSAGE51");
	public final static String MESSAGE52 = TagResource.getString("TagExecute_MESSAGE52");
	public final static String MESSAGE53 = TagResource.getString("TagExecute_MESSAGE53");
	public final static String MESSAGE54 = TagResource.getString("TagExecute_MESSAGE54");
	public final static String MESSAGE55 = TagResource.getString("TagExecute_MESSAGE55");
	public final static String MESSAGE56 = TagResource.getString("TagExecute_MESSAGE56");
	public final static String MESSAGE57 = TagResource.getString("TagExecute_MESSAGE57");
	public final static String MESSAGE58 = TagResource.getString("TagExecute_MESSAGE58");
	public final static String MESSAGE59 = TagResource.getString("TagExecute_MESSAGE59");
	public final static String MESSAGE60 = TagResource.getString("TagExecute_MESSAGE60");
	public final static String MESSAGE61 = TagResource.getString("TagExecute_MESSAGE61");
	public final static String SEARCHING_FILES = TagResource.getString("SEARCHING_FILES");
	public final static String INSERTING_TAGS = TagResource.getString("INSERTING_TAGS");
	public final static String REMOVING_TAGS = TagResource.getString("REMOVING_TAGS");
	public final static String UPDATING_TAGS = TagResource.getString("UPDATING_TAGS");
	public final static String PROCESSING_SUCCESSFUL = TagResource.getString("PROCESSING_SUCCESSFUL");
	public final static String PROCESSING_SUCCESSFUL_2 = TagResource.getString("PROCESSING_SUCCESSFUL_2");
	public final static String PROCESSING_TERMINATED = TagResource.getString("PROCESSING_TERMINATED");
	public final static String OUT_OF_MEMORY = TagResource.getString("OUT_OF_MEMORY");
	// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

   	public final static String ebahtmDir = "ebahtm";
   	private static String extension = null;

   	File dir;
  	int count = 0;
  	int totFile = 0;
	String topTag, bottomTag, dTopTag, dBottomTag;
	String topInsertPoint = "<html";
	String bottomInsertPoint = "</html";
	boolean inPlace = false;
	String newLine = System.getProperty("line.separator");
	int newLineLength = newLine.length();


   	int bottomTagLen, dBottomTagLen;
   	FileWriter  logFile = null;
   	boolean topInsertPointNotFound, bottomInsertPointNotFound;
   	int topTagCount, bottomTagCount, removeCt;
   	boolean checkClientSideRedirection = true;
   	boolean checkEventHandler = false;
   	boolean baseDir = true;
   	boolean memoryError = false;

	// success and error counters
	int  successCt = 0;			// counts how many files modified successfully
	int  systemErrorCt = 0;		// counts how many files encountered system errors such as memory, io, security errors.
	int  miscErrorCt = 0;		// counts how many files have processing errors
	int  bothInsertPointMissingCt = 0;		// counts how many files are missing both Top and Bottom insert points.
	//int  topInsertPointMissingCt = 0;
	//int  bottomInsertPointMissingCt = 0;
	int  tagsAlreadyPresentCt = 0;		// counts how many files already have tag(s)
	int  framesetPresentCt = 0;		    // counts how many files have <FRAMESET> code
	int  clientSideRedirectionCt = 0;	// counts how many files have Client Side Redirection code
	int  tagTopCt = 0; 			        // counts how many files modified with only Top tag
	int  tagBottomCt = 0;			    // counts how many files modified with only Bottom tag
	int  tagBothCt = 0;			        // counts how many files modified with both Top tag and Bottom tag
	int  tagRemoveCt = 0;			    // counts how many tags removed
	int  tagsRemoveMissingCt = 0;		// counts how many files don't have any tags to remove
	int  tagTopTotal = 0;			    // counts how many Top tags inserted
	int  tagBottomTotal = 0;		    // connts how many Bottom tags insreted
	//int  insertPointDifferenceCt = 0;

  	TagUtility tagUtility;

   	public TagExecute(TagUtility tu) {
		tagUtility = tu;
		extension = tagUtility.extField.getText().toLowerCase();
		dir = new File(tagUtility.dirField.getText());
		if (tagUtility.startBody.isSelected()) {
			 topInsertPoint = "<body";
			 checkClientSideRedirection = false;
			 checkEventHandler = true;
		}
		if (tagUtility.endBody.isSelected())
			 bottomInsertPoint = "</body";
		if (tagUtility.inPlace.isSelected())
			 inPlace = true;

       	if (dir.isDirectory()) {
       		if (!tagUtility.remove.isSelected()) {
			   	 topTag = tagUtility.topTag;
			   	 bottomTag = tagUtility.bottomTag;
			  	 dTopTag = dhtmlTag(topTag);
			  	 dBottomTag = dhtmlTag(bottomTag);
			  	 bottomTagLen = bottomTag.length();
			  	 dBottomTagLen = dBottomTag.length();
			}
			tagUtility.tArea.setText( TagNLS.getStringFrom(MESSAGE1, tagUtility.dirField.getText())+" ..."+newLine);
			new Search(this).start();
		}
       	else
	   		JOptionPane.showMessageDialog( tagUtility, TagNLS.getStringFrom(MESSAGE2, tagUtility.dirField.getText()),
		    		tagUtility.TITLE, JOptionPane.ERROR_MESSAGE);
   	}


	class Search extends Thread {

		TagExecute tagExecute;

		public Search(TagExecute te) {
			tagExecute = te;
			try {
				logFile = new FileWriter(new File(tagExecute.tagUtility.LOGFILE));
			}
			catch (IOException e) {
			   	 tagExecute.tagUtility.tArea.append( TagNLS.getStringFrom(MESSAGE3, e.getMessage())+newLine);
			   	 tagExecute.tagUtility.tArea.append(MESSAGE4 + newLine);
			}
		}

        // do GUI updates
        void doUpdate(Runnable r) {
        		try {
                	SwingUtilities.invokeAndWait(r);
         		}
                catch (InvocationTargetException e1) {
                	System.out.println(ERROR1+e1.getMessage());
                }
              	catch (InterruptedException e2) {
                	System.out.println(ERROR2+e2.getMessage());
                }
        }

        // get a list of all the files under a given directory
        void getFileList(File f, Vector list) {

        	 // update progress bar
           	 doUpdate(new Runnable() {
        		 public void run() {
					   if (totFile %10 == 0)
                       tagExecute.tagUtility.progBar.setValue(totFile % 100);
                 }
             });

             // recurse if a directory; base directory will always be counted.
             if (baseDir || (tagExecute.tagUtility.includeSubdir.isSelected() &&
					f.isDirectory() && !f.getName().equalsIgnoreCase(ebahtmDir))) {
				baseDir = false;
                String entries[] = f.list();
                totFile += entries.length;
                for (int i = 0; i < entries.length; i++)
                getFileList(new File(f, entries[i]), list);
             }

             // for files that end with extension, add to list and update progress bar
             else if (f.isFile()) {
 				if (f.getName().toLowerCase().endsWith(extension) &&  !f.getName().equals(TagUtility. LOGFILE))
                    				list.addElement(f.getPath());
				else if  ((extension.equals("*") || extension.equals("*.*"))  &&
					// Note:  add more file extensions below if appropriate and also change  'extensions' var in summary().
					(f.getName().toLowerCase().endsWith(".htm")  ||   f.getName().toLowerCase().endsWith(".html")     ||
	       			 f.getName().toLowerCase().endsWith(".asp")  ||   f.getName().toLowerCase().endsWith(".cfm")))

					list.addElement(f.getPath());
             }

          }  // end of getFileList()



          // call  appropriate method  insert/remvoe/update and display progress monitor
          public void run() {

          		if (logFile == null) return;
               	else {
				    write(tagExecute.tagUtility.TITLE+" "+tagExecute.tagUtility.VERSION);
			                   write(TagNLS.getStringFrom(MESSAGE5, new Date().toString())+newLine);
			    }

                Vector filelist = new Vector();

               	// clear old output
                doUpdate(new Runnable() {
                    			public void run() {
                        				 tagExecute.tagUtility.progBar.setVisible(true);
                        				 tagExecute.tagUtility.fileInfo.setText(SEARCHING_FILES+"  ");
                        				 tagExecute.tagUtility.fileInfo.setVisible(true);
					                     tagExecute.tagUtility.executeButton.setEnabled(false);
					                     tagExecute.tagUtility.tuMenu.exec.setEnabled(false);
                    			}
                });

                // get the list of files and display a count
                getFileList(tagExecute.dir, filelist);
               	//final int size = filelist.size();
               	count = filelist.size();
                doUpdate(new Runnable() {
                   			public void run() {
                        				tagExecute.tagUtility.progBar.setValue(0);
                        				tagExecute.tagUtility.progBar.setVisible(false);
                        				tagExecute.tagUtility.fileInfo.setText(TagNLS.getStringFrom(MESSAGE6, String.valueOf(count)));
                    		}
                });

                // set up a progress monitor
			    String pmText = "  ";
			    if (tagExecute.tagUtility.insert.isSelected())
				    pmText = INSERTING_TAGS;
			    else if (tagExecute.tagUtility.remove.isSelected())
				    pmText = REMOVING_TAGS;
			    else if (tagExecute.tagUtility.update.isSelected())
				    pmText = UPDATING_TAGS;

                final ProgressMonitor pm = new ProgressMonitor(tagExecute.tagUtility, pmText, "", 0, count - 1);
                pm.setMillisToDecideToPopup(0);
                pm.setMillisToPopup(0);

                // iterate across the files, updating  the progress monitor
               	for (int i = 0; i < count; i++) {
                   			final String fn = (String)filelist.elementAt(i);
                   			final int curr = i;
                   			if (pm.isCanceled())
                        				break;

                   			doUpdate(new Runnable() {
                       				 public void run() {
                            				pm.setProgress(curr);
						                    if ((curr < 10) && (fn.length() < 100)){
							                    int x = (120-fn.length()) / 5;
							                    StringBuffer sb = new StringBuffer();
							                    for (int j=0; j<x; j++) {
							 	                    sb.append("     ");
							                        pm.setNote(fn+sb.toString());
							                    }
						                    }
						                    else
						  	                    pm.setNote(fn);
					                }
                    		});

                            write(TagNLS.getStringFrom(MESSAGE7, fn));
				            if (processFile(fn)) {
					            successCt++;
					            if (inPlace)
					 	            write("      " + PROCESSING_SUCCESSFUL + newLine);
					            else
						            write("      " + PROCESSING_SUCCESSFUL_2 + newLine);
				            }
				            else
				                    write("      " + PROCESSING_TERMINATED + newLine);
                } // end of for loop

               	// close the progress monitor and make progress bar invisible
                doUpdate(new Runnable() {
                   			 public void run() {
                        				pm.close();
                        				tagExecute.tagUtility.fileInfo.setVisible(false);
					                    tagExecute.tagUtility.executeButton.setEnabled(true);
					                    tagExecute.tagUtility.tuMenu.exec.setEnabled(true);
                   		 	}
                });

                summary();

			    try {
				    logFile.write(newLine + TagNLS.getStringFrom(MESSAGE8, new Date().toString()));
				    logFile.flush();
				    logFile.close();
			    }
			    catch (IOException e) {tagExecute.tagUtility.tArea.append( TagNLS.getStringFrom(MESSAGE8, e.getMessage())+newLine);}
			    tagExecute.tagUtility.tuMenu.log.setEnabled(true);

          }  // end of run();
	} // end of Search


	// method to get tag for dynamic html code
	public String dhtmlTag(String s){
		StringBuffer sb1 = new StringBuffer();
		char c=0;
		int l = s.length();
		int x = 0;
		for (int i=0; i<l; i++) {
			c = s.charAt(i);
			StringBuffer sb2 = new StringBuffer();
			// while ((c != '\n') && (c != '\r') && (c != 0) ){
			while ((c != newLine.charAt(0)) && (c !=  newLine.charAt(newLineLength-1)) && (c != 0) ){
			 	sb2.append(c);
				i++;
				if (i >= l) break;
				c = s.charAt(i);
			}
			if ((sb2.toString().indexOf("<!--") > -1) && (sb2.toString().indexOf("-->") == -1))
				;
			else if ((x = sb2.toString().indexOf("//")) > -1) {
				sb1.append(sb2.toString().substring(0,x));
				sb1.append(" ");
			}
			else if ((sb2.toString().indexOf("-->") > -1) && (sb2.toString().indexOf("<!--") == -1))
				;
			else if (sb2.toString().length() > 0) {
					sb1.append(sb2.toString());
					sb1.append(" ");
			}
		}

		if (sb1.charAt(sb1.length()-1) == ' ') {
			String tempS = new String(sb1);
		   	 return tempS.substring(0, tempS.length()-1);
		}
		else
		   	 return sb1.toString();
	} // end of dhtmlTag()


	// method to read file and then process accordingly
	// returns true if successful; else returns false;
	public boolean processFile(String fn) {
	  try {
		if ((fn == null) || (fn.length() == 0)) return false;
		File f;
		FileReader in = null;
		char[] data;
		try{
			f = new File(fn);
			in  = new FileReader(f);
			int size = (int) f.length();
			data = new char[size];
			int c=0;
			while (c < size)
				c += in.read(data, c, size-c);
		}
		catch (IOException e) {
		    	systemErrorCt++;
		    	write("      " + TagNLS.getStringFrom(MESSAGE10,  e.getMessage()));
		  	    return false ;
		}
		finally {	if (in != null)
		                try {in.close();}
		                catch (IOException e) {System.out.println(ERROR3+e.getMessage());}
		}

		String s = new String(data);
		StringBuffer buffer = new StringBuffer(s);

		if (tagUtility.insert.isSelected()) {
			if (s.toLowerCase().indexOf("</frameset>") > -1){
				framesetPresentCt++;
				write("      " + MESSAGE11);
				return false;
			}
			if (s.indexOf(tagUtility.TAG_START) > -1){
				tagsAlreadyPresentCt++;
				write("      " + MESSAGE12);
				return false;
			}
			if ( checkClientSideRedirection &&  clientSideRedirection(s, fn)) {
				if (memoryError) {
					memoryError = false;
					return false;
				}
				clientSideRedirectionCt++;
				write("      " + MESSAGE13);
				return false;
			}
			if (insertTag(buffer, fn)) return false;
		}
		else if (tagUtility.remove.isSelected()) {
			if (s.indexOf(tagUtility.TAG_START) == -1){
				tagsRemoveMissingCt++;
				write("      " + MESSAGE14);
				return false;
			}
		    	if ((buffer = removeTag(buffer, fn)) == null) return false;
		}
		else if (tagUtility.update.isSelected()) {
			if (s.indexOf(tagUtility.TAG_START) == -1){
				tagsRemoveMissingCt++;
				write("      " + MESSAGE15);
				return false;
			}
			if ( checkClientSideRedirection &&  clientSideRedirection(s, fn)) {
				if (memoryError) {
					memoryError = false;
					return false;
				}
				clientSideRedirectionCt++;
				write("      " + TagNLS.getStringFrom(MESSAGE16, topInsertPoint));
				return false;
			}
		   	if ((buffer = removeTag(buffer, fn)) == null) {
		       		write("      " + MESSAGE17);
		        		return false;
            }
			if (insertTag(buffer, fn)){
			   	 write("      " + MESSAGE18);
		       		 return false;
           	}
		}

		String fname, dir;
		if (!inPlace) {
			int x = fn.lastIndexOf(File.separatorChar);
			dir = new String(fn.substring(0, x+1) + ebahtmDir);
			File ebaDir = new File(dir);
			try {
				if (!ebaDir.isDirectory())
					ebaDir.mkdir();
			}
			catch (SecurityException e) {
			    	systemErrorCt++;
			    	write("      " + TagNLS.getStringFrom(MESSAGE19, dir, e.getMessage()));
			   	    return false;
			}
			fname = new String(dir +File.separatorChar+f.getName());
		}
		else
			fname = fn;

		File fo = new File(fname);
		FileWriter out = null;
		try{
			out = new FileWriter(fo);
			out.write(buffer.toString(), 0, buffer.length());
		}
		catch (IOException e)  {
		    systemErrorCt++;
		   	write("      " + TagNLS.getStringFrom(MESSAGE20, fname, e.getMessage()));
		   	return false;
		}
		finally {	if (out != null)
		                try {out.close();}
		                catch (IOException e) {System.out.println(ERROR4+e.getMessage());;}
		}

		if (tagUtility.insert.isSelected()) {
			if (bottomInsertPointNotFound) {
	    	   		 write("      " + TagNLS.getStringFrom(MESSAGE21, bottomInsertPoint));
           			 tagTopCt++;
    		}
        	else if (topInsertPointNotFound) {
        			 write("      " + TagNLS.getStringFrom(MESSAGE22, bottomInsertPoint));
        			 tagBottomCt++;
        	}
        	else
            		tagBothCt++;

           	tagTopTotal += topTagCount;
           	tagBottomTotal += bottomTagCount;
       	}

       	else if (tagUtility.remove.isSelected())
           		 tagRemoveCt += removeCt;

       	else if (tagUtility.update.isSelected()) {
        		 tagTopTotal += topTagCount;
        		 tagBottomTotal += bottomTagCount;
        		 tagRemoveCt += removeCt;
        }

		if (!inPlace)
			write("      " + TagNLS.getStringFrom(MESSAGE56, fname));

	  } // end of try

	  catch(OutOfMemoryError err) {
		    System.gc();
		    File f = new File(fn);
		    write("      " +TagNLS.getStringFrom(MESSAGE60, fn, String.valueOf(f.length())));
		    systemErrorCt++;
		    return false;
	  }

	  return true;
	} // end of processFile()


	// mothod to check if client side redirection code exists;  returns true if exists; else returns false.
	public boolean clientSideRedirection(String s, String fn) {
	  try {
		int x = s.toLowerCase().indexOf("<meta");
		int len = s.length();
		while (x > -1){
		   	 StringBuffer temp = new StringBuffer();
		   	 x +=4;
		   	 char c = s.charAt(x);
		   	 while (c != '>') {
		        		//if ((c != ' ') && (c != '\t') && (c != '\r') && (c != '\n'))
		        		if ((c != ' ') && (c != '\t') && (c != newLine.charAt(0)) && (c != newLine.charAt(newLineLength-1)))
		            		temp.append(c);
		        		x++;
		       		    if (x < len)
		           			 c = s.charAt(x);
		        		else
		           			 break;
		     } // end of while
		   	 if ((temp.toString().toLowerCase().indexOf("http-equiv=\'refresh\'") > -1) ||
		               			 (temp.toString().toLowerCase().indexOf("http-equiv=\"refresh\"") > -1))
			    	return true;
             x = s.toLowerCase().indexOf("<meta", x-1);
        } // end of while
	  } // end of try

	  catch(OutOfMemoryError err) {
		    System.gc();
		    File f = new File(fn);
		    write("      " +TagNLS.getStringFrom(MESSAGE60, fn, String.valueOf(f.length())));
		    memoryError = true;
		    systemErrorCt++;
		    return true;
	  }

	  return false;
	}  // end of clientSideRedirection()


    // mothod to find '>' after '<body'; returns the address if found, else returns -1
	public int findEndBracket(StringBuffer s, int x) {
	         boolean quote = false;
		     int len = s.length();
		     char c, prev_c = 0;

		   	 while (x < len) {
		        c = s.charAt(x);

		        if (c == '>') {
		            if (!quote) return x;
		        }
		        else if ((c == '"') || (c == '\'')){
		             if (!quote) {
		                quote = true;
                        prev_c = c;
		             }
		             else if (c == prev_c)
		                quote = false;
		        }
		        x++;
		     } // end of while

		     return -1;
	}  // end of findEndBracket()



	// method to insert tags;  returns false if insserts successful;  else returns true.
	public boolean insertTag(StringBuffer buffer, String fn) {

        topTagCount = bottomTagCount = 0;
       	topInsertPointNotFound = bottomInsertPointNotFound = false;
       	int x = buffer.toString().toLowerCase().indexOf(topInsertPoint);
       	int y = x;
	    try {
       		 if (x == -1)
           			 topInsertPointNotFound = true;
       		 else {
            			while (true) {

            			    if (checkEventHandler)
            			        x = findEndBracket(buffer, x+4);
            			    else
            			        x = buffer.toString().indexOf(">", x+4);

               				if (x == -1) {
                    		 	miscErrorCt++;
                    			write("      " +  TagNLS.getStringFrom(MESSAGE23, topInsertPoint));
                    			return true;
               		        }
			    	        try {
				 	            // while (buffer.charAt(x+1) == '\r')
                    			//		x++;
                    			//if (buffer.charAt(x+1) == '\n')
                    			//		x++;

                    			while (buffer.charAt(x+1) == newLine.charAt(0))
                    			        x++;
                    			for (int i=1; i < newLineLength; i++) {
            	                    if (buffer.charAt(x+1) == newLine.charAt(i))
            	                        x++;
            	                    else
            	                        break;
            	                }
                    			x++;
				            }   //end  try
				            // following exception will occur if  <html> or <body> are the last 6 chars in the file.
			         	    catch (StringIndexOutOfBoundsException e) {
                    			 	miscErrorCt++;
               				     	write("      " + TagNLS.getStringFrom(MESSAGE54, topInsertPoint));
					                return true;
				            }

               				if (dynamicHtml(buffer, y, fn)) {
					            if (memoryError) {
						            memoryError = false;
						            return true;
					            }
					            if (tagUtility.includeDHTML.isSelected()) {
               				    		write("      " + MESSAGE24);
                   					    buffer.insert(x, dTopTag);
						                topTagCount++;
					            }
                   			}
               				else {
                   				buffer.insert(x, topTag+newLine);
                   			 	topTagCount++;
				            }

                			x = buffer.toString().toLowerCase().indexOf(topInsertPoint, x);
               				if (x == -1) {
               				     	write("      " + TagNLS.getStringFrom(MESSAGE25, String.valueOf(topTagCount)));
                   			    	 break;
                   			}
                   			y = x;
            			} // end of while
       		 } // end of else

       		 x = buffer.toString().toLowerCase().indexOf(bottomInsertPoint);
        	 if (x == -1)
           			 bottomInsertPointNotFound = true;
        	 else {
        	    y = x;
           		while (true) {
               		if (dynamicHtml(buffer, y, fn)) {
					    if (memoryError) {
						    memoryError = false;
						    return true;
					    }
					    if (tagUtility.includeDHTML.isSelected()) {
               				    write("      " + MESSAGE26);
               				    buffer.insert(x, dBottomTag);
                   				x = buffer.toString().toLowerCase().indexOf(bottomInsertPoint, x+dBottomTagLen+2);
               				 	bottomTagCount++;
					    }
					    // if we're not inserting tags to the dynamically generated pages,
					    //increase the index to go past bottomInsertPoint
					    else
						    x = buffer.toString().toLowerCase().indexOf(bottomInsertPoint, x+4);
               		} // end of if
                	else {
                    		buffer.insert(x, bottomTag+newLine);
                    		x = buffer.toString().toLowerCase().indexOf(bottomInsertPoint, x+bottomTagLen+4);
               			 	bottomTagCount++;
               		}

               		if (x == -1){
               		     	write("      " + TagNLS.getStringFrom(MESSAGE27, String.valueOf(bottomTagCount)));
                   	    	break;
                   	}
                   	y = x;
            	} // end of while
       		 } // end of else
	    } // end of try

	    catch(java.lang.OutOfMemoryError err) {
		    System.gc();
		    File f = new File(fn);
		    write("      " +TagNLS.getStringFrom(MESSAGE60, fn, String.valueOf(f.length())));
		    systemErrorCt++;
		    return true;
	    }


	    if (topInsertPointNotFound  && bottomInsertPointNotFound){
           		write("      " + TagNLS.getStringFrom(MESSAGE28, topInsertPoint, bottomInsertPoint));
		    	bothInsertPointMissingCt++;
           		return true;
    	}
	         /*else if (topInsertPointNotFound){
        	    		write("      " + TagNLS.getStringFrom(MESSAGE21, topInsertPoint));
			topInsertPointMissingCt++;
           			return true;
    	         }
	         else if (bottomInsertPointNotFound){
        	    		write("      " + TagNLS.getStringFrom(MESSAGE21, bottomInsertPoint));
			bottomInsertPointMissingCt++;
           			return true;
    	         }
	         else if (topTagCount > bottomTagCount) {
			write("      " + TagNLS.getStringFrom(MESSAGE22, String.valueOf(topTagCount), topInsertPoint, String.valueOf(bottomTagCount), bottomInsertPoint));
			insertPointDifferenceCt++;
           			return true;
	         }
	         else if (bottomTagCount > topTagCount) {
			write("      " + TagNLS.getStringFrom(MESSAGE22, String.valueOf(bottomTagCount), bottomInsertPoint, String.valueOf(topTagCount), topInsertPoint));
			insertPointDifferenceCt++;
           			return true;
	         }*/

	    return false;
	} // end of insertTag()


	// method to remove tags;  returns null if tags are not removed.
	public StringBuffer removeTag(StringBuffer buffer, String fn) {
	     int x;
	     removeCt = 0;
	     try {
	   	    while (true) {
	       		 x = buffer.toString().indexOf(tagUtility.TAG_START);
	       		 if (x == -1)  	break;
	        	 StringBuffer tempBuffer = new StringBuffer(buffer.toString().substring(0,x));
	        	 x = buffer.toString().indexOf(tagUtility.TAG_END, x);
	       		 if (x == -1) {
	            		write("      " + TagNLS.getStringFrom(MESSAGE29, tagUtility.TAG_END));
	            		miscErrorCt++;
	           		 	return null;
	        	 }
            	 x += tagUtility.TAG_END.length();

            	 for (int i =0; i < newLineLength; i++) {
            	    if (buffer.charAt(x) == newLine.charAt(i))
            	            x++;
            	    else
            	            break;
            	 }
            	 // preceeding FOR replaces the following IFs.
           		 //if ((buffer.charAt(x) == '\r'))
                 //	    x++;
                 //if ((buffer.charAt(x) == '\n'))
                 //  	x++;

	       		 tempBuffer.append(buffer.toString().substring(x, buffer.length()));
	        	 buffer = tempBuffer;
	        	 removeCt++;
	    	} // end of while
	    } // end of try

	    catch(OutOfMemoryError err) {
		    System.gc();
		    File f = new File(fn);
		    write("      " +TagNLS.getStringFrom(MESSAGE60, fn, String.valueOf(f.length())));
		    systemErrorCt++;
		    return null;
	    }

	    return buffer;
	} // end of removeTag()


	// method to verify if we are inserting tags inside code (vb, javascript, etc)  which will generate dynamic html.
	// returns true if dhtml code is found, else returns false.
	public boolean dynamicHtml(StringBuffer buffer, int x, String fn) {
	    boolean status = false;
	    try {
		    StringBuffer temp = new StringBuffer();
		    char c = buffer.charAt(x);
		    //while ((c != '\n') && (x > 0)) {
		    while ((c != newLine.charAt(newLineLength-1)) && (x > 0)) {
		   	    if ((c != ' ') && (c != '\t'))
		        		temp.insert(0, c);
		    	c = buffer.charAt(--x);
		    }
		    String s = temp.toString().toLowerCase();

		    // Note:  if you find  more scenarios to determine the code to dynamically create HTML page,  add here..
	   	    if ( (s.indexOf("write\"") > -1)  || (s.indexOf("write\'") > -1)  ||
            	 (s.indexOf("return\"") > -1) || (s.indexOf("return\'") > -1) ||
            	 (s.indexOf("=\"") > -1)      || (s.indexOf("='") > -1)      ||
				 (s.indexOf("(\"") > -1)      || (s.indexOf("('") > -1)  )
           			 status =  true;
            }
	    catch(OutOfMemoryError err) {
		    System.gc();
		    File f = new File(fn);
		    write("      " +TagNLS.getStringFrom(MESSAGE60, fn, String.valueOf(f.length())));
		    memoryError = true;
		    systemErrorCt++;
		    return true;
	    }

	    return status;
	}  // end of dynamicHtml()


	// method to write in log file
	public void write(String s){
	    	try {
	        		logFile.write(s+newLine);
	    	}
	    	catch (IOException e){
	        		tagUtility.tArea.append(TagNLS.getStringFrom(MESSAGE30, e.getMessage()));
	   	    }
	}


	// method to write summary of success/error
	public void summary() {
		String extensions = ".htm,  .html,  .asp,  .cfm";
	   	int errorTot = bothInsertPointMissingCt + tagsAlreadyPresentCt + tagsRemoveMissingCt + framesetPresentCt +
				clientSideRedirectionCt + miscErrorCt + systemErrorCt;
       	int totTagsInserted = tagTopTotal + tagBottomTotal;
		if (tagUtility.includeSubdir.isSelected()) {
			if (extension.equals("*") || extension.equals("*.*")) {
	   			 write(newLine+TagNLS.getStringFrom(MESSAGE58, String.valueOf(count), tagUtility.dirField.getText()));
         				 tagUtility.tArea.setText(TagNLS.getStringFrom(MESSAGE58, String.valueOf(count), tagUtility.dirField.getText())+newLine);
 				 write("     "+extensions+newLine);
         				 tagUtility.tArea.append("     "+extensions+ newLine+ newLine);
			}
			else {
	   			 write(newLine+TagNLS.getStringFrom(MESSAGE31, String.valueOf(count), extension, tagUtility.dirField.getText())+newLine);
         				 tagUtility.tArea.setText(TagNLS.getStringFrom(MESSAGE31, String.valueOf(count), extension, tagUtility.dirField.getText())+newLine+newLine);
			}
		}
		else {
			if (extension.equals("*") || extension.equals("*.*")) {
 				write(newLine+TagNLS.getStringFrom(MESSAGE59, String.valueOf(count), tagUtility.dirField.getText())+newLine);
         			 	tagUtility.tArea.setText(TagNLS.getStringFrom(MESSAGE59, String.valueOf(count), tagUtility.dirField.getText())+newLine+newLine);
			}
			else {
 				write(newLine+TagNLS.getStringFrom(MESSAGE57, String.valueOf(count), extension, tagUtility.dirField.getText())+newLine);
         			 	tagUtility.tArea.setText(TagNLS.getStringFrom(MESSAGE57, String.valueOf(count), extension, tagUtility.dirField.getText())+newLine+newLine);
			}
		}

		if ((successCt > 0) && !inPlace) {
			write(TagNLS.getStringFrom(MESSAGE55, String.valueOf(successCt), File.separator+ebahtmDir));
	   		tagUtility.tArea.append(TagNLS.getStringFrom(MESSAGE55, String.valueOf(successCt), File.separator+ebahtmDir)+newLine);
		}
		else {
			write(TagNLS.getStringFrom(MESSAGE32, String.valueOf(successCt)));
	   		tagUtility.tArea.append(TagNLS.getStringFrom(MESSAGE32, String.valueOf(successCt))+newLine);
		}

	   	if (tagUtility.insert.isSelected()){
	         if (tagBothCt > 0) {
	            			write("     "+TagNLS.getStringFrom(MESSAGE33, String.valueOf(tagBothCt)));
	            			tagUtility.tArea.append("       "+TagNLS.getStringFrom(MESSAGE33, String.valueOf(tagBothCt))+newLine);
	         }
			 if (tagTopCt > 0) {
	            			write("     "+TagNLS.getStringFrom(MESSAGE34, String.valueOf(tagTopCt)));
	            			tagUtility.tArea.append("       "+TagNLS.getStringFrom(MESSAGE34, String.valueOf(tagTopCt))+newLine);
	         }
	       	 if (tagBottomCt > 0) {
	            			write("     "+TagNLS.getStringFrom(MESSAGE35, String.valueOf(tagBottomCt)));
	            			tagUtility.tArea.append("       "+TagNLS.getStringFrom(MESSAGE35, String.valueOf(tagBottomCt))+newLine);
	         }
	   	 }
	   	 else if (tagUtility.remove.isSelected()) {
	       	    if (successCt > 0) {
	            		write("     "+TagNLS.getStringFrom(MESSAGE36, String.valueOf(successCt), String.valueOf(tagRemoveCt)));
	           			tagUtility.tArea.append("       "+TagNLS.getStringFrom(MESSAGE36, String.valueOf(successCt), String.valueOf(tagRemoveCt))+newLine);
	        	}
	   	 }
	     else if (tagUtility.update.isSelected()){
	        	if (successCt > 0) {
	            			write("     "+TagNLS.getStringFrom(MESSAGE37, String.valueOf(successCt), String.valueOf(tagRemoveCt), String.valueOf(totTagsInserted)));
	           			tagUtility.tArea.append("       "+TagNLS.getStringFrom(MESSAGE37, String.valueOf(successCt), String.valueOf(tagRemoveCt), String.valueOf(totTagsInserted))+newLine);
	       		}
	     }

       	 if (errorTot > 0) {
	        		write(newLine + TagNLS.getStringFrom(MESSAGE38, String.valueOf(errorTot)));
	        		tagUtility.tArea.append(newLine + TagNLS.getStringFrom(MESSAGE38, String.valueOf(errorTot)) + newLine);
	     }

	   	 if (tagUtility.insert.isSelected()){
	        		if (bothInsertPointMissingCt > 0){
	           			write("     "+TagNLS.getStringFrom(MESSAGE39, String.valueOf(bothInsertPointMissingCt), String.valueOf(topInsertPoint), String.valueOf(bottomInsertPoint)));
	            		tagUtility.tArea.append("       "+TagNLS.getStringFrom(MESSAGE39, String.valueOf(bothInsertPointMissingCt), String.valueOf(topInsertPoint), String.valueOf(bottomInsertPoint))+newLine);
	                }
		    //if (topInsertPointMissingCt > 0){
	          		//	write("     "+TagNLS.getStringFrom(MESSAGE34, String.valueOf(topInsertPointMissingCt), String.valueOf(topInsertPoint), String.valueOf(bottomInsertPoint)));
	           		//	tagUtility.tArea.append("       "+TagNLS.getStringFrom(MESSAGE34, String.valueOf(topInsertPointMissingCt), String.valueOf(topInsertPoint))+newLine);
	        //}
			//if (bottomInsertPointMissingCt > 0){
	           		//	write("     "+TagNLS.getStringFrom(MESSAGE34, String.valueOf(bottomInsertPointMissingCt), String.valueOf(topInsertPoint), String.valueOf(bottomInsertPoint)));
	            		//	tagUtility.tArea.append("       "+TagNLS.getStringFrom(MESSAGE34, String.valueOf(bottomInsertPointMissingCt), String.valueOf(bottomInsertPoint))+newLine);
	       		//}
			//if (insertPointDifferenceCt > 0){
	           		//	write("     "+TagNLS.getStringFrom(MESSAGE35, String.valueOf(insertPointDifferenceCt), String.valueOf(topInsertPoint), String.valueOf(bottomInsertPoint)));
	            		//	tagUtility.tArea.append("       "+TagNLS.getStringFrom(MESSAGE35, String.valueOf(insertPointDifferenceCt), String.valueOf(topInsertPoint), String.valueOf(bottomInsertPoint))+newLine);
	       		//}
	                if (tagsAlreadyPresentCt > 0) {
	           			write("     "+TagNLS.getStringFrom(MESSAGE40, String.valueOf(tagsAlreadyPresentCt)));
	           			tagUtility.tArea.append("       "+TagNLS.getStringFrom(MESSAGE40, String.valueOf(tagsAlreadyPresentCt))+newLine);
	                }
	                if (framesetPresentCt > 0) {
	         			 write("     "+TagNLS.getStringFrom(MESSAGE41, String.valueOf(framesetPresentCt), "<FRAMESET>"));
	           			 tagUtility.tArea.append("       "+TagNLS.getStringFrom(MESSAGE41, String.valueOf(framesetPresentCt), "<FRAMESET>")+newLine);
	                }
	                if (clientSideRedirectionCt > 0) {
	       			    write("     "+TagNLS.getStringFrom(MESSAGE42, String.valueOf(clientSideRedirectionCt)));
	           			tagUtility.tArea.append("       "+TagNLS.getStringFrom(MESSAGE42, String.valueOf(clientSideRedirectionCt))+newLine);
	                }
	    }
	    else if (tagUtility.remove.isSelected()) {
	       		if (tagsRemoveMissingCt > 0) {
	           			 write("     "+TagNLS.getStringFrom(MESSAGE43, String.valueOf(tagsRemoveMissingCt)));
	            			tagUtility.tArea.append("       "+TagNLS.getStringFrom(MESSAGE43, String.valueOf(tagsRemoveMissingCt))+newLine);
	       		}
	    }
	   	else if (tagUtility.update.isSelected()) {
	       	 if (tagsRemoveMissingCt > 0) {
	           		 	write("     "+TagNLS.getStringFrom(MESSAGE44, String.valueOf(tagsRemoveMissingCt)));
	           			tagUtility.tArea.append("       "+TagNLS.getStringFrom(MESSAGE44, String.valueOf(tagsRemoveMissingCt))+newLine);
	         }
			 if (clientSideRedirectionCt > 0) {
	           			write("     "+TagNLS.getStringFrom(MESSAGE45, String.valueOf(clientSideRedirectionCt)));
	            			tagUtility.tArea.append("       "+TagNLS.getStringFrom(MESSAGE45, String.valueOf(clientSideRedirectionCt))+newLine);
	         }
			 if (bothInsertPointMissingCt > 0){
	           			write("     "+TagNLS.getStringFrom(MESSAGE39, String.valueOf(bothInsertPointMissingCt), String.valueOf(topInsertPoint), String.valueOf(bottomInsertPoint)));
	            			tagUtility.tArea.append("       "+TagNLS.getStringFrom(MESSAGE39, String.valueOf(bothInsertPointMissingCt), String.valueOf(topInsertPoint), String.valueOf(bottomInsertPoint))+newLine);
	       	 }
			//if (topInsertPointMissingCt > 0){
	           		//	write("     "+TagNLS.getStringFrom(MESSAGE34, String.valueOf(topInsertPointMissingCt), String.valueOf(topInsertPoint), String.valueOf(bottomInsertPoint)));
	            		//	tagUtility.tArea.append("       "+TagNLS.getStringFrom(MESSAGE34, String.valueOf(topInsertPointMissingCt), String.valueOf(topInsertPoint))+newLine);
	       		//}
			//if (bottomInsertPointMissingCt > 0){
	           		//	write("     "+TagNLS.getStringFrom(MESSAGE34, String.valueOf(bottomInsertPointMissingCt), String.valueOf(topInsertPoint), String.valueOf(bottomInsertPoint)));
	            		//	tagUtility.tArea.append("       "+TagNLS.getStringFrom(MESSAGE34, String.valueOf(bottomInsertPointMissingCt), String.valueOf(bottomInsertPoint))+newLine);
	       		//}
			//if (insertPointDifferenceCt > 0){
	           		//	write("     "+TagNLS.getStringFrom(MESSAGE35, String.valueOf(insertPointDifferenceCt), String.valueOf(topInsertPoint), String.valueOf(bottomInsertPoint)));
	            		//	tagUtility.tArea.append("       "+TagNLS.getStringFrom(MESSAGE35, String.valueOf(insertPointDifferenceCt), String.valueOf(topInsertPoint), String.valueOf(bottomInsertPoint))+newLine);
	       		//}
	    }
	   	if (miscErrorCt > 0) {
	       		write("     "+TagNLS.getStringFrom(MESSAGE46, String.valueOf(miscErrorCt)));
	       		tagUtility.tArea.append("       "+TagNLS.getStringFrom(MESSAGE46, String.valueOf(miscErrorCt))+newLine);
	   	}
		if (systemErrorCt > 0) {
	       		write("     "+TagNLS.getStringFrom(MESSAGE61, String.valueOf(systemErrorCt)));
	       		tagUtility.tArea.append("       "+TagNLS.getStringFrom(MESSAGE61, String.valueOf(systemErrorCt))+newLine);
	   	}

       	if (totTagsInserted > 0) {
	       		if (tagUtility.insert.isSelected()){
	           			write(newLine+TagNLS.getStringFrom(MESSAGE47, String.valueOf(totTagsInserted)));
	           			tagUtility.tArea.append(newLine+TagNLS.getStringFrom(MESSAGE47, String.valueOf(totTagsInserted))+newLine);
	           			write("     "+TagNLS.getStringFrom(MESSAGE48, String.valueOf(tagTopTotal)));
	           			tagUtility.tArea.append("       "+TagNLS.getStringFrom(MESSAGE48, String.valueOf(tagTopTotal))+newLine);
	           			write("     "+TagNLS.getStringFrom(MESSAGE49, String.valueOf(tagBottomTotal)));
	           			tagUtility.tArea.append("       "+TagNLS.getStringFrom(MESSAGE49, String.valueOf(tagBottomTotal))+newLine);
	       		}
	       		else if (tagUtility.update.isSelected()){
	            		write(newLine+TagNLS.getStringFrom(MESSAGE50, String.valueOf(totTagsInserted)));
	           			tagUtility.tArea.append(newLine+TagNLS.getStringFrom(MESSAGE50, String.valueOf(totTagsInserted))+newLine);
	           			write("     "+TagNLS.getStringFrom(MESSAGE51, String.valueOf(tagTopTotal)));
	           			tagUtility.tArea.append("       "+TagNLS.getStringFrom(MESSAGE51, String.valueOf(tagTopTotal))+newLine);
	           			write("     "+TagNLS.getStringFrom(MESSAGE52, String.valueOf(tagBottomTotal)));
	           			tagUtility.tArea.append("       "+TagNLS.getStringFrom(MESSAGE52, String.valueOf(tagBottomTotal))+newLine);
	       		}
	   	}

        tagUtility.tArea.append(newLine+MESSAGE53+newLine);
	} // end of summary()

} // end of class TagExecute