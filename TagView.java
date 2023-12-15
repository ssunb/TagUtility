
/***************************************************************************/
//
//      Author : Subel Sunbeeb
//      Date:  January 3, 2001
//      Class:  TagView
//      Description :  Class to display Top Tag and Bottom Tag in the console of  Tag Utility.
//      --------------------------------------------------------------------------------------
//			Change Log
//      --------------------------------------------------------------------------------------
//      Date         Author     Description
//      01/22/01     SS         Make it NLS ready.
/***************************************************************************/


import java.io.*;
import javax.swing.*;

public class TagView {

    // *** NLS variables ***********************
    public final static String  TOP_TAG = TagResource.getString("TOP_TAG");
    public final static String  BOTTOM_TAG = TagResource.getString("BOTTOM_TAG");
    public final static String  MESSAGE1 = TagResource.getString("TagView_MESSAGE1");
    public final static String  MESSAGE2 = TagResource.getString("TagView_MESSAGE2");
    public final static String  MESSAGE3 = TagResource.getString("TagView_MESSAGE3");
    public final static String  MESSAGE4 = TagResource.getString("TagView_MESSAGE4");
	// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

    TagUtility tagUtility;
    String newLine = System.getProperty("line.separator");
    String name;

    public TagView(TagUtility tu, String filename, boolean show) {
		tagUtility = tu;
		name = filename;
		try {
			File f = new File(name);
			FileReader fr = new FileReader(f);
			int start, end=0;
			boolean topTagFound, bottomTagFound;
			int size = (int) f.length();
			char[] data = new char [size];
			int chars_read = 0;
			while(chars_read < size)
				chars_read += fr.read(data, chars_read, size - chars_read);
			String buffer = new String(data);
			topTagFound=bottomTagFound=false;
			//for (int i =0; i<2; i++) {
			while ((start = buffer.indexOf(tagUtility.TAG_START, end)) > -1) {
				if ((end = buffer.indexOf(tagUtility.TAG_END, start)) > -1) {
					String tag = buffer.substring(start, end + tagUtility.TAG_END.length());
					if((!topTagFound) && (tag.indexOf(tagUtility.TOP) > -1)){
						if  (show && !bottomTagFound)
							tagUtility.tArea.setText("****************** "+TOP_TAG+" ******************"+newLine);
						else if (show)
							tagUtility.tArea.append("****************** "+TOP_TAG+" ******************"+newLine);
						tagUtility.topTag = new String(tag);
						topTagFound = true;
					}
					else if((!bottomTagFound) && (tag.indexOf(tagUtility.BOTTOM) > -1)){
						if (show && !topTagFound)
							tagUtility.tArea.setText(newLine+"**************** "+BOTTOM_TAG+" ****************"+newLine);
						else if (show)
							tagUtility.tArea.append(newLine+"**************** "+BOTTOM_TAG+" ****************"+newLine);
						tagUtility.bottomTag = new String(tag);
						bottomTagFound = true;
					}
					if (show) {
						tagUtility.tArea.append(tag);
						tagUtility.tArea.append(newLine+newLine);
					}
				}
				if (topTagFound && bottomTagFound)
				break;
			}

			if ((topTagFound == false) && (bottomTagFound == false)) {
				tagUtility.tArea.setText("");
				tagUtility.bottomTag = null;
				tagUtility.topTag = null;
				JOptionPane.showMessageDialog( tagUtility,  TagNLS.getStringFrom(MESSAGE1, name),
			  		  tagUtility.TITLE, JOptionPane.ERROR_MESSAGE);
			}
			else if (topTagFound == false) {
				tagUtility.topTag = null;
				JOptionPane.showMessageDialog( tagUtility, TagNLS.getStringFrom(MESSAGE2, name),
			   		 tagUtility.TITLE, JOptionPane.ERROR_MESSAGE);
			}
			else if (bottomTagFound == false) {
				tagUtility.bottomTag = null;
				JOptionPane.showMessageDialog( tagUtility, TagNLS.getStringFrom(MESSAGE3, name),
			    		tagUtility.TITLE, JOptionPane.ERROR_MESSAGE);
			}
			fr.close();
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog( tagUtility, TagNLS.getStringFrom(MESSAGE4, e.getMessage()),
			   	 tagUtility.TITLE, JOptionPane.ERROR_MESSAGE);
		}
   	}
}
