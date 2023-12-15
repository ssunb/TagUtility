
/***************************************************************************/
//
//      Author : Subel Sunbeeb
//      Date:  January 3, 2001
//      Class:  TagEditorToolBar
//      Description :  Class to create tool bar for editor and viewer.
//      -----------------------------------------------------------------------------------------------
//			Change Log
//      -----------------------------------------------------------------------------------------------
//      Date             Author         Description
//      01/22/01         SS             Make it NLS ready.
//      01/31/01         SS	        Add separators in the toolbar.
/***************************************************************************/


import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;

public class TagEditorToolBar extends JToolBar implements ActionListener {

	// *** NLS variables ************************************
    public final static String  NEW   = TagResource.getString("NEW");
   	public final static String  OPEN  = TagResource.getString("OPEN");
   	public final static String  SAVE  = TagResource.getString("SAVE");
   	public final static String  CUT   = TagResource.getString("CUT");
   	public final static String  COPY  = TagResource.getString("COPY");
	public final static String  PASTE = TagResource.getString("PASTE");
	public final static String  FIND  = TagResource.getString("FIND");
	// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^


    String[] toolItems =  new String[] {NEW, OPEN, SAVE, CUT, COPY, PASTE, FIND};
	String[] toolImages =  new String[] {"new.gif","open.gif", "save.gif", "cut.gif", "copy.gif", "paste.gif","glass.gif"};
    JButton saveButton, cutButton, copyButton, pasteButton, findButton;

	TagEditor  tagEditor;

	public TagEditorToolBar(TagEditor  te) {

	    tagEditor  =  te;
	    int x;
	    if (tagEditor.viewOnly)
			x = 2;
	    else
			x = toolItems.length-1;
	    for (int i=0; i<x; i++) {
			JButton button = new JButton(new ImageIcon(getClass().getResource(toolImages[i])))  {
     	        public JToolTip createToolTip() {
            	    JToolTip tip = super.createToolTip();
			        tip.setBackground(Color.yellow);
                    tip.setFont(new Font("TimesRoman", Font.BOLD, 12));
                    return tip;
     		    }
            };
	        button.setToolTipText(toolItems[i]);
	        button.addActionListener(this);
	        if (tagEditor.viewOnly && (i == 0 ))	{ ; }
	        else				add(button);

	        if (i == 2){
			    saveButton = button;
			    addSeparator(); addSeparator();	addSeparator();
	        }
	        else if (i == 3) 	cutButton = button;
	        else if (i == 4) 	copyButton = button;
	        else if (i == 5) {
			    pasteButton = button;
			    addSeparator(); addSeparator();	addSeparator();
	        }
		} // end of for

		// ISSUE:  when a better glass.gif is found, use above loop instead of following code
		x = toolItems.length-1;
		//findButton = new JButton(" ", new ImageIcon(getClass().getResource(toolImages[x])))  {
		findButton = new JButton(" ", new ImageIcon("new.gif"))  {
		
           			public JToolTip createToolTip() {
                    			JToolTip tip = super.createToolTip();
				                tip.setBackground(Color.yellow);
                   			    tip.setFont(new Font("TimesRoman", Font.BOLD, 12));
                   			    return tip;
             		}
        };
		findButton.setToolTipText(toolItems[x]);
		findButton.addActionListener(this);
		add(findButton);
	}

 	public void actionPerformed(ActionEvent e) {
		if (tagEditor.tagEditorFind != null)
			tagEditor.tagEditorFind.setVisible(true);
    	String s = ((JButton)e.getSource()).getToolTipText();
		if (s.equals(toolItems[0])) {
			System.out.println(s);
			tagEditor.openNewFrame();
  		}
		else if (s.equals(toolItems[1])) {
			System.out.println(s);
			JFileChooser chooser = new JFileChooser(tagEditor.openName);
			int option = chooser.showOpenDialog(tagEditor);
			if (option == JFileChooser.APPROVE_OPTION)
				tagEditor.openFrame(chooser.getSelectedFile().getPath(), false);
  		}
		else if (s.equals(toolItems[2])) {
			JInternalFrame currentFrame =  tagEditor.getCurrentFrame();
			if (currentFrame == null) 	return;
    			((TagPageFrame)currentFrame).saveContent(true);
  		}
		else if (s.equals(toolItems[3])) {
			JInternalFrame currentFrame =  tagEditor.getCurrentFrame();
			if (currentFrame == null) 	return;
    			((TagPageFrame)currentFrame).cutText();
  		}
		else if (s.equals(toolItems[4])) {
			JInternalFrame currentFrame =  tagEditor.getCurrentFrame();
			if (currentFrame == null) 	return;
    			((TagPageFrame)currentFrame).copyText();
  		}
		else if (s.equals(toolItems[5])) {
			JInternalFrame currentFrame =  tagEditor.getCurrentFrame();
			if (currentFrame == null) 	return;
    			((TagPageFrame)currentFrame).pasteText();
  		}
		else if (s.equals(toolItems[6])) {
			if (tagEditor.tagEditorFind == null)
				tagEditor.tagEditorFind = new TagEditorFind(tagEditor);
  		}

    }
}