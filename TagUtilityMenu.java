
/***************************************************************************/
//
//      Author : Subel Sunbeeb
//      Date:  January 3, 2001
//      Class:  TagUtilityMenu
//      Description :  Class to create menu bar for Tag Utility
//      -----------------------------------------------------------------------------------------------
//			Change Log
//      -----------------------------------------------------------------------------------------------
//      Date        Author    Description
//      01/22/01    SS        Make it NLS ready.
//      02/23/01    SS        Create  instance of  TagHelp when  'Help' is clicked.
//      05/22/01    SS        Enable 'View Log File' at starup if LOGFILE exists.
/***************************************************************************/


import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;

public class TagUtilityMenu extends JMenuBar implements ActionListener {

	// *** NLS variables *********************************************
	private final static String  FILE = TagResource.getString("FILE");
    private final static String  TOOLS = TagResource.getString("TOOLS");
    private final static String  HELP = TagResource.getString("HELP");
    private final static String  EXECUTE = TagResource.getString("EXECUTE");
    private final static String  EXIT = TagResource.getString("EXIT");;
    private final static String  VIEW_TAGS_IN = TagResource.getString("VIEW_TAGS_IN");
    private final static String  EDIT_TAGS_IN = TagResource.getString("EDIT_TAGS_IN");
    private final static String  VIEW_LOG = TagResource.getString("VIEW_LOG");
    private final static String  CLEAR = TagResource.getString("CLEAR");
    private final static String  TOPICS = TagResource.getString("TOPICS");
    private final static String  VIEWER = TagResource.getString("VIEWER");
	// ISSUE:  languages that don't use A-Z alphabets, how should following chars be translated???
	private final static char    E = 'E';
    private final static char    X = 'X';
    private final static char    V = 'V';
    private final static char    D = 'D';
    private final static char    L = 'L';
    private final static char    C = 'C';
    private final static char    H = 'H';
    private final static char    A = 'A';
	// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

	// public final static String  SHOW_ERROR = "Show Error Messages In Message Box";
	// public final static String  SHOW_WARNING = "Show Warning Messages In Message Box";


	private final static String  ABOUT = TagUtility.ABOUT+" "+TagUtility.TITLE;

    String[] fileItems = new String[] { EXECUTE, EXIT };
    String[] toolsItems = new String[] { VIEW_TAGS_IN, EDIT_TAGS_IN, VIEW_LOG, CLEAR };
    String[] helpItems = new String[] { TOPICS, ABOUT };
	char[] fileShortcuts = { E,X};
	char[] toolsShortcuts = { V,D,L,C};
	char[] helpShortcuts = { H,A};

	TagUtility  tagUtility;
  	JMenuItem view, edit, log, exec;
 	JCheckBoxMenuItem checkBoxErrMsg,  checkBoxWrnMsg;

	public TagUtilityMenu(TagUtility  tu) {

		 tagUtility  =  tu;

	 	 JMenu fileMenu = new JMenu(FILE);
	 	 JMenu toolsMenu = new JMenu(TOOLS);
       		 JMenu helpMenu = new JMenu(HELP);

		//  Assemble the file menus with keyboard accelerators
      		for (int i=0; i < fileItems.length; i++) {
          			  JMenuItem item = new JMenuItem(fileItems[i]);
          			  item.setAccelerator(KeyStroke.getKeyStroke(fileShortcuts[i],
                              		 	 java.awt.Event.CTRL_MASK, false));
           			  item.addActionListener(this);
          			  fileMenu.add(item);
			  if (i==0) exec = item;
        		}
	       	fileMenu.insertSeparator(1);

		//  Assemble the tools menus with keyboard accelerators
      		for (int i=0; i < toolsItems.length; i++) {
          			  JMenuItem item = new JMenuItem(toolsItems[i]);
          			  item.setAccelerator(KeyStroke.getKeyStroke(toolsShortcuts[i],
                              		 	 java.awt.Event.CTRL_MASK, false));
           			  item.addActionListener(this);
          			  toolsMenu.add(item);
			  if (i==0) view = item;
			  else if (i == 1) edit = item;
			  else if (i == 2) log = item;
        		}

		//toolsMenu.add(checkBoxErrMsg = new JCheckBoxMenuItem(SHOW_ERROR));
 		//checkBoxErrMsg.setAccelerator(KeyStroke.getKeyStroke('S', java.awt.Event.CTRL_MASK, false));
        //checkBoxErrMsg.addActionListener(this);
		//toolsMenu.add(checkBoxWrnMsg = new JCheckBoxMenuItem(SHOW_WARNING));
 		//checkBoxWrnMsg.setAccelerator(KeyStroke.getKeyStroke('S', java.awt.Event.CTRL_MASK, false));
        //checkBoxWrnMsg.addActionListener(this);
		toolsMenu.insertSeparator(2);
	    toolsMenu.insertSeparator(4);

	    File f = new File(System.getProperty("user.dir")+ File.separator+tagUtility.LOGFILE);
	    if (!f.exists())
		    log.setEnabled(false);

 	 	//  Assemble the help menus with keyboard accelerators
	    for (int i=0; i < helpItems.length; i++) {
           	JMenuItem item = new JMenuItem(helpItems[i]);
     		item.setAccelerator(KeyStroke.getKeyStroke(helpShortcuts[i],
                     			 java.awt.Event.CTRL_MASK, false));
			item.addActionListener(this);
	        helpMenu.add(item);
  		}
   		helpMenu.insertSeparator(1);

       	//  Finally, add all the menus to the menubar
		add(fileMenu);
		add(toolsMenu);
		add(helpMenu);
	}

 	 public void actionPerformed(ActionEvent e) {
      	String command = e.getActionCommand();
        if (command.equals(EXECUTE))
			tagUtility.processFiles();
	  	else if (command.equals(EXIT)){
			tagUtility.dispose();
			System.exit(0);
	  	}
		else if (command.equals(VIEW_TAGS_IN))
			tagUtility.viewTags();
		else if (command.equals(EDIT_TAGS_IN))
			tagUtility.editTags();
		else if (command.equals(VIEW_LOG)){
			TagEditor  tagEditor = new TagEditor(tagUtility, System.getProperty("user.dir")+
			                            File.separator+tagUtility.LOGFILE, true, VIEWER);
			tagEditor.setVisible(true);
		}
		else if (command.equals(CLEAR))
			tagUtility.tArea.setText("");
	  	else if (command.equals(TOPICS))
			new TagHelp(tagUtility);
		else if (command.equals(ABOUT))
			new TagCopyright(tagUtility);
    }
}