
/***************************************************************************/
//
//      Author : Subel Sunbeeb
//      Date:  January 3, 2001
//      Class:  TagPageFrame
//      Description :  Class that create a single page frame (new or existing file) to edit or view.
//      -----------------------------------------------------------------------------------------------
//			Change Log
//      -----------------------------------------------------------------------------------------------
//      Date             Author         Description
//      01/22/01         SS             Make it NLS ready.
/***************************************************************************/


import java.awt.*;
import java.io.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;

public class TagPageFrame extends JInternalFrame {

	// *** NLS variables ********************************************
	public final static String MESSAGE1   =  TagResource.getString("TagPageFrame_MESSAGE1");
	public final static String MESSAGE2   =  TagResource.getString("TagPageFrame_MESSAGE2");
	public final static String MESSAGE3   =  TagResource.getString("TagPageFrame_MESSAGE3");
	public final static String MESSAGE4   =  TagResource.getString("TagPageFrame_MESSAGE4");;
	public final static String MESSAGE5   =  TagResource.getString("TagPageFrame_MESSAGE5");
	public final static String MESSAGE6   =  TagResource.getString("TagPageFrame_MESSAGE6");
	public final static String MESSAGE7   =  TagResource.getString("TagPageFrame_MESSAGE7");
	public final static String MESSAGE8   =  TagResource.getString("TagPageFrame_MESSAGE8");
	public final static String MESSAGE9   =  TagResource.getString("TagPageFrame_MESSAGE9");
	public final static String MESSAGE10 =  TagResource.getString("TagPageFrame_MESSAGE10");
	public final static String SAVE_AS      =  TagResource.getString("SAVE_AS");
	// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

  	TagEditor tagEditor;
  	String filename;
  	JTextArea tArea;
	String initialContent = null;
	boolean saveBeforeClosing = true;
	boolean newFile = false;
	boolean pageClosing = false;
	boolean noFileSelected = false;

  	public TagPageFrame(String name, TagEditor te, int i, boolean newF) {
    		super(name, true, true, true, true);
    		tagEditor = te;
    		setBounds(i*15,i*15,630,405);

    		Container contentPane = getContentPane();

    		// Create a text area to display the contents of our file in
   		 // and stick it in a scrollable pane so we can see everything
    		 tArea = new JTextArea();
   		 JScrollPane jsp = new JScrollPane(tArea);
   		 contentPane.add(jsp, BorderLayout.CENTER);
   		         System.out.println("TagPageFrame1");

    		filename = name;
		if (newF)
			newFile = newF;
		addInternalFrameListener(new TagPageFrameMonitor());
		show();
		  System.out.println("TagPageFrame2");
  	}

 	 public boolean loadContent() {
		FileReader fr;
   		try {
    			 fr = new FileReader(filename);
		}
		catch (FileNotFoundException e) {
			if (tagEditor.viewOnly) {
				JOptionPane.showMessageDialog( tagEditor, TagNLS.getStringFrom(MESSAGE1, filename),
			    		TagUtility.TITLE, JOptionPane.WARNING_MESSAGE);
				try { setClosed(true); }
				catch (java.beans.PropertyVetoException ex) {System.out.println(ex.getMessage());}
				dispose();
				return false;
			}
			else {
				String msg[] = {TagNLS.getStringFrom(MESSAGE1, filename),  MESSAGE2};
				newFile = true;
				int x =  JOptionPane.showConfirmDialog( tagEditor, msg, TagUtility.TITLE,
					JOptionPane.YES_NO_OPTION);
				if  (x == JOptionPane.NO_OPTION) {
					try { setClosed(true); }
					catch (java.beans.PropertyVetoException ex) {System.out.println(ex.getMessage());}
					dispose();
					return false;
				}
				return true;
			}
		}
		try{
     			 tArea.read(fr, null);
			 if (!tagEditor.viewOnly)
			 	initialContent = new String(tArea.getText());
			 return true;
    		}
   		catch (IOException e) {
			JOptionPane.showMessageDialog( tagEditor, TagNLS.getStringFrom(MESSAGE3, filename),
			    	TagUtility.TITLE, JOptionPane.WARNING_MESSAGE);
			dispose();
			 return false;
		}
		catch (Error e) {
			JOptionPane.showMessageDialog( tagEditor, TagNLS.getStringFrom(MESSAGE4, filename),
			    	TagUtility.TITLE, JOptionPane.WARNING_MESSAGE);
			dispose();
			return false;
		}
		finally { try{ fr.close(); } catch (IOException e) {System.out.println(e.getMessage());} }
 	 }

  	public void saveContent(boolean showSavedMsg) {
		 if (newFile &&  (filename.indexOf(TagEditor.UNTITLED) != -1)){
			saveAsContent();
			return;
		 }
   		 try {
      			FileWriter fw = new FileWriter(filename);
     			tArea.write(fw);
     			fw.close();
			initialContent = new String(tArea.getText());
			if (showSavedMsg)
				JOptionPane.showMessageDialog( tagEditor, TagNLS.getStringFrom(MESSAGE5, filename),
			   		TagUtility.TITLE, JOptionPane.INFORMATION_MESSAGE);

			// update tags in the console if displayed at this time
			if ((filename.compareTo(tagEditor.tagUtility.fileField.getText()) == 0) && (tagEditor.tagUtility.tagsDisplaying))
				tagEditor.tagUtility.viewTags();
  		}
  		catch (Exception e) {
			JOptionPane.showMessageDialog( tagEditor, TagNLS.getStringFrom(MESSAGE6, filename),
			    TagUtility.TITLE, JOptionPane.WARNING_MESSAGE);
		}
 	 }

	public void saveAsContent() {
		String fn = null;
   		 try {
			JFileChooser chooser = new JFileChooser(filename);
			chooser.setSelectedFile(new File(filename));
			chooser.setDialogTitle(SAVE_AS);

			// ISSUE:  Following is a workaround for the JFileChooser bug (bug id: 4220176; JFileChooser ActionEvent's
			// are all the same ie. cancel events). Remove the following ActionListener when it's fixed in future Swing releases.
			ActionListener fileChooserListener = new ActionListener() {
				public void actionPerformed(ActionEvent e){
					if (((JFileChooser)e.getSource()).getSelectedFile() == null)
						noFileSelected = true;
				}
			};

			chooser.addActionListener(fileChooserListener);

			int option = chooser.showSaveDialog(tagEditor);
			if (option == JFileChooser.APPROVE_OPTION) {
				fn = chooser.getSelectedFile().getPath();
				int x=0;
				File f = new File(fn);
				if (f.isFile()) {
					String msg[] = {TagNLS.getStringFrom(MESSAGE7, fn),  MESSAGE8};
					x =  JOptionPane.showConfirmDialog( tagEditor, msg, TagUtility.TITLE,
						JOptionPane.YES_NO_OPTION);
				}
				if   (!f.isFile() || (x == JOptionPane.YES_OPTION)) {
					FileWriter fw = new FileWriter(fn);
     					tArea.write(fw);
     					fw.close();
					tagEditor.numberOfPageFrameDisplayed--;
					if (!(newFile && pageClosing))
						tagEditor.openFrame(fn, false);
					saveBeforeClosing = false;
					try { setClosed(true); }
					catch (java.beans.PropertyVetoException ex) {System.out.println(ex.getMessage());}
					dispose();
				}
				else if  (x == JOptionPane.NO_OPTION)
					saveAsContent();
			}
			// ISSUE:  following else statement will  not be needed when Swing bug (id: 4220176) is fixed.
			else if (noFileSelected) {
				noFileSelected = false;
				saveAsContent();
			}

			// update tags in the console if displayed at this time
			if ((filename.compareTo(tagEditor.tagUtility.fileField.getText()) == 0) && (tagEditor.tagUtility.tagsDisplaying))
				tagEditor.tagUtility.viewTags();
  		}
  		catch (Exception e) {
			JOptionPane.showMessageDialog( tagEditor, TagNLS.getStringFrom(MESSAGE6, fn),
			   TagUtility.TITLE, JOptionPane.WARNING_MESSAGE);
		}
 	 }

  	public void cutText() { tArea.cut(); }
  	public void copyText() { tArea.copy(); }
 	public void pasteText() { tArea.paste(); }


	public void saveOrNot() {
		pageClosing = true;
		String finalContent = new String(tArea.getText());
		if (saveBeforeClosing &&   ((initialContent == null) || (finalContent.compareTo(initialContent) != 0))) {
			if (newFile && (finalContent.length() < 1))  	return;
			String msg[] = {TagNLS.getStringFrom(MESSAGE9, filename),  MESSAGE10};
			int x =  JOptionPane.showConfirmDialog( tagEditor, msg, TagUtility.TITLE,
				JOptionPane.YES_NO_OPTION);
			if  (x == JOptionPane.YES_OPTION)
				saveContent(false);
		}
	}


	public class TagPageFrameMonitor extends InternalFrameAdapter {
  		public void internalFrameClosing(InternalFrameEvent e) {
			if (!tagEditor.viewOnly)
				saveOrNot();
			if  (tagEditor.numberOfPageFrameDisplayed > 1)
				tagEditor.numberOfPageFrameDisplayed--;
			tagEditor.selectCurrentFrame();
		}

		public void internalFrameIconified(InternalFrameEvent e) {
			if  (tagEditor.numberOfPageFrameDisplayed > 1)
				tagEditor.numberOfPageFrameDisplayed--;
			tagEditor.selectCurrentFrame();
		}

		public void internalFrameDeiconified(InternalFrameEvent e) {
			show();
			tagEditor.numberOfPageFrameDisplayed++;
			tagEditor.changeMenuAndButtonStatus(true);
		}
	}
}
