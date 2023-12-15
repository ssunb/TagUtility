
/***************************************************************************/
//
//      Author : Subel Sunbeeb
//      Date:  February 23, 2001
//      Class:  TagHelp
//      Description :  Class to display 'help'.
//      ---------------------------------------------------------------------
//			Change Log
//      ---------------------------------------------------------------------
//      Date                Author         Description
/***************************************************************************/

import java.io.IOException;
import javax.swing.*;

public class TagHelp {

	// *** NLS variables *********************************************
	private final static String MESSAGE1 = TagResource.getString("TagHelp_MESSAGE1");
    private final static String MESSAGE2 = TagResource.getString("TagHelp_MESSAGE2");
	// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

    // Platform dependent bOsUNIX and bOsWindow flags
	public static boolean bOsUNIX = false;
    public static boolean bOsWindow = false;
	public static String osName;

    // Static block that set up the OS flags
    static {
       		// Get the current operating system name
       		osName = System.getProperty("os.name");
       		if( osName == null || osName.toLowerCase().indexOf("windows") != -1 )
            			bOsWindow = true;
       		else if (osName.toLowerCase().indexOf("solaris") != -1  ||  osName.toLowerCase().indexOf("aix") != -1  ||
                  				osName.toLowerCase().indexOf("hp-ux") != -1)
            			bOsUNIX = true;
   	 } //End Static Block

	// constructor
	public TagHelp(JFrame f) {

		try {
			if (bOsWindow)
                Runtime.getRuntime().exec("tagHelp.bat");
			else if (bOsUNIX)
				Runtime.getRuntime().exec("tagHelp.sh");
			else
				JOptionPane.showMessageDialog( f, TagNLS.getStringFrom(MESSAGE1, osName) ,
		    			TagUtility.TITLE, JOptionPane.ERROR_MESSAGE);
		}
		catch(IOException e) {
			JOptionPane.showMessageDialog( f, TagNLS.getStringFrom(MESSAGE2, e.getMessage()) ,
		    		TagUtility.TITLE, JOptionPane.ERROR_MESSAGE);
		}
	}

} // end of class TagHelp