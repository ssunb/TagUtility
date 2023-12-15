
/***************************************************************************/
//
//      Author : Subel Sunbeeb
//      Date:  January 3, 2001
//      Class:  TagEditorMenu
//      Description :  Class to create menu for editor and viewer
//      -----------------------------------------------------------------------------------------------
//			Change Log
//      -----------------------------------------------------------------------------------------------
//      Date             Author         Description
//      01/22/01         SS             Make it NLS ready.
//      02/23/01         SS             Create  instance of  TagHelp when  'Help' is clicked.
/***************************************************************************/


import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;

public class TagEditorMenu extends JMenuBar implements ActionListener {

	// *** NLS variables *********************************************
    private final static String  FILE = TagResource.getString("FILE");
    private final static String  EDIT = TagResource.getString("EDIT");
    private final static String  HELP = TagResource.getString("HELP");
    private final static String  NEW = TagResource.getString("NEW");
    private final static String  OPEN = TagResource.getString("OPEN");
    private final static String  SAVE = TagResource.getString("SAVE");
    private final static String  SAVE_AS = TagResource.getString("SAVE_AS");
    private final static String  EXIT = TagResource.getString("EXIT");
    private final static String  CUT = TagResource.getString("CUT");
    private final static String  COPY = TagResource.getString("COPY");
    private final static String  PASTE = TagResource.getString("PASTE");
    private final static String  FIND = TagResource.getString("FIND");
    private final static String  TOPICS = TagResource.getString("TOPICS");
	// ISSUE:  languages that don't use A-Z alphabets, how should following chars be translated???
    private final static char    N = 'N';
    private final static char    O = 'O';
    private final static char    S = 'S';
    private final static char    V = 'V';
    private final static char    X = 'X';
    private final static char    T = 'T';
    private final static char    C = 'C';
    private final static char    P = 'P';
    private final static char    F = 'F';
    private final static char    H = 'H';
    private final static char    A = 'A';
	// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

	public final static String  ABOUT = TagUtility.ABOUT+" "+TagUtility.TITLE;

    String[] fileItems = new String[] {NEW, OPEN, SAVE, SAVE_AS, EXIT };
    String[] editItems = new String[] { CUT, COPY, PASTE, FIND };
    String[] helpItems = new String[] { TOPICS, ABOUT };
	char[] fileShortcuts = { N,O,S,V,X };
	char[] editShortcuts = { T,C,P,F};
	char[] helpShortcuts = { H,A};

	TagEditor  tagEditor;
  	JMenuItem save, saveAs, cut, copy, paste, find;

	public TagEditorMenu(TagEditor  te) {

		 tagEditor  =  te;

	 	 JMenu fileMenu = new JMenu(FILE);
	 	 JMenu editMenu = new JMenu(EDIT);
     	 JMenu helpMenu = new JMenu(HELP);

		//  Assemble the file menus with keyboard accelerators
     	for (int i=0; i < fileItems.length; i++) {
        	JMenuItem item = new JMenuItem(fileItems[i]);
        	item.setAccelerator(KeyStroke.getKeyStroke(fileShortcuts[i],
                              			 java.awt.Event.CTRL_MASK, false));
        	item.addActionListener(this);
			if (tagEditor.viewOnly && (i != 1) &&  (i != 4))	{ ; }
        	else 				fileMenu.add(item);

			if (i == 2)  		save = item;
			else if (i == 3) 	saveAs = item;
        }
		if (tagEditor.viewOnly)
	       		fileMenu.insertSeparator(1);
		else
			fileMenu.insertSeparator(4);

		//  Assemble the edit menus with keyboard accelerators
      	for (int i=0; i < editItems.length; i++) {
        	JMenuItem item = new JMenuItem(editItems[i]);
        	item.setAccelerator(KeyStroke.getKeyStroke(editShortcuts[i],
                           		 	 java.awt.Event.CTRL_MASK, false));
        	item.addActionListener(this);
          	if (tagEditor.viewOnly && (i != 3))	{ ; }
          	else 			editMenu.add(item);

			if (i==0) 		    cut = item;
			else if (i == 1) 	copy = item;
			else if (i == 2) 	paste = item;
			else if (i == 3) 	find = item;
        }
		if (!tagEditor.viewOnly)
			editMenu.insertSeparator(3);

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
		add(editMenu);
		add(helpMenu);
	}

 	 public void actionPerformed(ActionEvent e) {
      	String command = e.getActionCommand();
		if (command.equals(NEW)) {
			tagEditor.openNewFrame();
  		}
		else if (command.equals(OPEN)) {
			JFileChooser chooser = new JFileChooser(tagEditor.openName);
			int option = chooser.showOpenDialog(tagEditor);
			if (option == JFileChooser.APPROVE_OPTION)
				tagEditor.openFrame(chooser.getSelectedFile().getPath(), false);
  		}
		else if (command.equals(SAVE)) {
			JInternalFrame currentFrame =  tagEditor.getCurrentFrame();
			if (currentFrame == null) 	return;
    			((TagPageFrame)currentFrame).saveContent(true);
  		}
		else if (command.equals(SAVE_AS)) {
			JInternalFrame currentFrame =  tagEditor.getCurrentFrame();
			if (currentFrame == null) 	return;
    			((TagPageFrame)currentFrame).saveAsContent();
  		}
		else if (command.equals(EXIT)){
			tagEditor.warnBeforeClosing();
			tagEditor.dispose();
	  	}
		else if (command.equals(CUT)) {
			JInternalFrame currentFrame =  tagEditor.getCurrentFrame();
			if (currentFrame == null) 	return;
    			((TagPageFrame)currentFrame).cutText();
  		}
		else if (command.equals(COPY)) {
			JInternalFrame currentFrame =  tagEditor.getCurrentFrame();
			if (currentFrame == null) 	return;
    			((TagPageFrame)currentFrame).copyText();
  		}
		else if (command.equals(PASTE)) {
			JInternalFrame currentFrame =  tagEditor.getCurrentFrame();
			if (currentFrame == null) 	return;
    			((TagPageFrame)currentFrame).pasteText();
  		}
		else if (command.equals(FIND)) {
			if (tagEditor.tagEditorFind == null)
				tagEditor.tagEditorFind = new TagEditorFind(tagEditor);
  		}
	  	else if (command.equals(TOPICS))
			new TagHelp(tagEditor);
	  	else if (command.equals(ABOUT)){
			new TagCopyright(tagEditor);
		}
   	}
}