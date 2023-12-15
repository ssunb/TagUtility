/***************************************************************************/
//
//      Author : Subel Sunbeeb
//      Date:  January 3, 2001
//      Class:  TagWindowMonitor
//      Description :  Class to monitor the closing  of  Tag Utility.
//      ---------------------------------------------------------------------
//			Change Log
//      ---------------------------------------------------------------------
//      Date        Author       Description
//
/***************************************************************************/


import java.awt.event.*;
import java.awt.Window;

public class TagWindowMonitor extends WindowAdapter {

	public void windowClosing(WindowEvent e) {
    		Window w = e.getWindow();
    		w.setVisible(false);
   		w.dispose();
    		System.exit(0);
  	}
}
