
/***************************************************************************/
//
//      Author : Subel Sunbeeb
//      Date:  January 3, 2001
//      Class:  TagEditor
//      Description :  Class to create Editor and Viewer for Tag Utility.
//	        Editor will be used to edit script text file.  Viewer will be used to view the log file.
//      -----------------------------------------------------------------------------------------------
//			Change Log
//      -----------------------------------------------------------------------------------------------
//      Date             Author         Description
//      01/22/01         SS             Make it NLS ready.
//      01/31/01         SS             Get taU.gif from the jar file.
/***************************************************************************/

import java.awt.*;
import java.io.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;

public class TagEditor extends JFrame {

	// *** NLS variables *********************************************
	private final static String ERROR1 = TagResource.getString("TagEditor_ERROR1");
	// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^


	static String UNTITLED = "Untitled";

	JLayeredPane desktop;
	Vector popups = new Vector();
	int currentPage;
	Container contentPane;
	boolean viewOnly;
	TagEditorFind  tagEditorFind = null;

	String openName;
	int numberOfPageFrameDisplayed = 0;

	int newPageNumber = 0;

	TagEditorMenu        teMenu;
	TagEditorToolBar    teToolBar = null;
	TagUtility 	tagUtility;

	public TagEditor(TagUtility tu, String name, boolean viewO, String mode) {
		super(TagUtility.TITLE+" "+mode);
		//setIconImage(new ImageIcon(getClass().getResource(tu.ICON_IMAGE)).getImage());
   		setBounds(250,150, 700, 550);
   		addWindowListener(new TagEditorMonitor());
		openName = name;
   		contentPane = getContentPane();
		desktop = new JDesktopPane();
   		contentPane.add(desktop, BorderLayout.CENTER);
		viewOnly = viewO;
		tagUtility = tu;
		teMenu = new TagEditorMenu(this);
   		setJMenuBar(teMenu);
   		teToolBar = new TagEditorToolBar(this);
   		contentPane.add(teToolBar, BorderLayout.NORTH);
		openFrame(name, false);
	}

	public void openFrame(String name, boolean newFile) {
		System.out.println("openFrame");
		numberOfPageFrameDisplayed++;
		if (numberOfPageFrameDisplayed > 10 )
			numberOfPageFrameDisplayed = 1;
   		TagPageFrame pf = new TagPageFrame(name, this, numberOfPageFrameDisplayed, newFile);
		if (newFile || pf.loadContent()) {
		  	desktop.add(pf, new Integer(1));
  			pf.setIconifiable(true);
			popups.addElement(pf);
			try { pf.setSelected(true); }
			catch ( java.beans.PropertyVetoException e) {System.out.println(ERROR1+e.getMessage());};
			if (viewOnly)
				pf.tArea.setEditable(false);
			changeMenuAndButtonStatus(true);
		}
		else {
			numberOfPageFrameDisplayed--;
		}
 	 }

	public void openNewFrame() {
		String name = new String(System.getProperty("user.dir") + File.separator +
        				UNTITLED + (++newPageNumber));
        System.out.println("openNewFrame");
		openFrame(name, true);
	}


	public TagPageFrame getCurrentFrame() {
		for (int i=0; i<popups.size(); i++) {
			TagPageFrame currentFrame = (TagPageFrame)popups.elementAt(i);
			if (currentFrame.isSelected())
				return currentFrame;
		}
		return null;
	}


	public void selectCurrentFrame() {
		for (int i = popups.size() - 1; i >= 0; i--) {
			TagPageFrame selectedFrame = (TagPageFrame)popups.elementAt(i);
			if (!selectedFrame.isClosed()  && !selectedFrame.isIcon()) {
				try {
					selectedFrame.setSelected(true);
					changeMenuAndButtonStatus(true);
					return;
				}
				catch (java.beans.PropertyVetoException e) {
					System.out.println(ERROR1+e.getMessage());
				}
			}
		}
		changeMenuAndButtonStatus(false);
	}

	public void warnBeforeClosing() {
		if (!viewOnly) {
			for (int i=0; i<popups.size(); i++) {
				TagPageFrame currentFrame = (TagPageFrame)popups.elementAt(i);
				if (!currentFrame.isClosed())
					currentFrame.saveOrNot();
			}
		}
	}

	private class TagEditorMonitor extends WindowAdapter {
  		public void windowClosing(WindowEvent e) {
			warnBeforeClosing();
			Window w = e.getWindow();
    			w.setVisible(false);
    			w.dispose();
		}
  	}


	public void changeMenuAndButtonStatus(boolean boo) {
		if (!viewOnly) {
			if (teToolBar != null) {
				teToolBar.saveButton.setEnabled(boo);
				teToolBar.cutButton.setEnabled(boo);
				teToolBar.copyButton.setEnabled(boo);
				teToolBar.pasteButton.setEnabled(boo);
			}
			teMenu.save.setEnabled(boo);
			teMenu.saveAs.setEnabled(boo);
			teMenu.cut.setEnabled(boo);
			teMenu.copy.setEnabled(boo);
			teMenu.paste.setEnabled(boo);
		}
		if (teToolBar != null)
			teToolBar.findButton.setEnabled(boo);
		teMenu.find.setEnabled(boo);
		if (tagEditorFind != null)
			tagEditorFind.findButton.setEnabled(boo);
	}
}

