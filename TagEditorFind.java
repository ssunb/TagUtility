
/***************************************************************************/
//
//      Author : Subel Sunbeeb
//      Date:  January 3, 2001
//      Class:  TagEditorFind
//      Description :  Class to implement 'find' in editor and viewer.
//      -----------------------------------------------------------------------------------------------
//			        Change Log
//      -----------------------------------------------------------------------------------------------
//      Date             Author         Description
//      01/31/01         SS             Make it NLS ready.
/***************************************************************************/


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JCheckBox;

public class TagEditorFind extends JDialog implements ActionListener {

	// *** NLS variables ********************************************************
	private final static String FIND = TagResource.getString("FIND");
	private final static String FIND_WHAT = TagResource.getString("FIND_WHAT");
	private final static String MATCH_CASE = TagResource.getString("MATCH_CASE");
	private final static String FIND_NEXT = TagResource.getString("FIND_NEXT");
	private final static String CANCEL = TagResource.getString("CANCEL");
	private final static String FIND_ERROR = TagResource.getString("FIND_ERROR");
	private final static String FIND_WARNING = TagResource.getString("FIND_WARNING");
	private final static String ERROR = TagResource.getString("ERROR");
	private final static String WARNING = TagResource.getString("WARNING");
	private final static String MESSAGE1 = TagResource.getString("TagEditorFind_MESSAGE1");
	private final static String MESSAGE2 = TagResource.getString("TagEditorFind_MESSAGE2");
	// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

	TagEditor  tagEditor;
	JTextField searchField;
	JButton findButton, cancelButton, errorButton, warningButton;
	JCheckBox  caseCheckBox;
	String s,  txt;
	String previous_s = new String();
	int offset = 0;
	int length = 0;
	TagPageFrame previous_tpf;
	boolean found = false;

	public TagEditorFind(TagEditor  te) {
		super(te, FIND);
		if (te.viewOnly)
			setSize(320, 145);
		else
			setSize(320, 110);
		addWindowListener(new TagEditorFindMonitor());
		tagEditor = te;
		Point pt = new Point(tagEditor.getLocation());
		pt.translate(100,100);
		setLocation(pt);
		Container c = getContentPane();
 		c.setLayout(new FlowLayout(FlowLayout.CENTER, 15 ,10));
		JLabel fLabel = new JLabel(FIND_WHAT);
		c.add(fLabel);
		searchField = new JTextField(20);
		c.add(searchField);
		caseCheckBox = new JCheckBox(MATCH_CASE);
		c.add(caseCheckBox);
		findButton = new JButton(FIND_NEXT);
		findButton.addActionListener(this);
		c.add(findButton);
		cancelButton = new JButton(CANCEL);
		cancelButton.addActionListener(this);
		c.add(cancelButton);
		if (te.viewOnly) {
			errorButton = new JButton(FIND_ERROR);
			errorButton.addActionListener(this);
			c.add(errorButton);
			warningButton = new JButton(FIND_WARNING);
			warningButton.addActionListener(this);
			c.add(warningButton);
		}
		setVisible(true);
		//setIconifiable(false);
 		setResizable(false);
	}


 	public void actionPerformed(ActionEvent e) {
		Object object = e.getSource();
		if (object == findButton) {
			s = new String(searchField.getText());
			length = s.length();
			if (length > 0)
				find();
		}
		else if (object == cancelButton) {
			setVisible(false);
			dispose();
			tagEditor.tagEditorFind = null;
		}
		if (object == errorButton) {
			searchField.setText(ERROR);
			s = new String(ERROR);
			caseCheckBox.setSelected(true);
			length = s.length();
			find();
		}
		if (object == warningButton) {
			searchField.setText(WARNING);
			s = new String(WARNING);
			caseCheckBox.setSelected(true);
			length = s.length();
			find();
		}
	}


	private class TagEditorFindMonitor extends WindowAdapter {
  		public void windowClosing(WindowEvent e) {
    			Window w = e.getWindow();
    			w.setVisible(false);
    			w.dispose();
			tagEditor.tagEditorFind = null;
		}
  	}


	public void find() {
		int x;
		TagPageFrame tpf =  tagEditor.getCurrentFrame();
		if (tpf == null) 	return;
		if (tpf != previous_tpf) {
			if (tagEditor.viewOnly)
				txt = tpf.tArea.getText();
			offset = 0;
			found = false;
		}

		if (!tagEditor.viewOnly)
			txt = tpf.tArea.getText();

		if (s.compareTo(previous_s) != 0) {
			offset = 0;
			found = false;
		}
		if (!caseCheckBox.isSelected())
			x = txt.toLowerCase().indexOf(s.toLowerCase(), offset);
		else
			x = txt.indexOf(s, offset);

		if (x == -1) {
			if (!found)
				JOptionPane.showMessageDialog(tagEditor, TagNLS.getStringFrom(MESSAGE1, s, tpf.filename),
		    			TagUtility.TITLE, JOptionPane.INFORMATION_MESSAGE);
			else {
				JOptionPane.showMessageDialog(tagEditor, TagNLS.getStringFrom(MESSAGE2, s, tpf.filename),
		    			TagUtility.TITLE, JOptionPane.INFORMATION_MESSAGE);
				found = false;
			}
			offset = 0;
		}
		else {
			tpf.tArea.select(x, x+length);
			offset = x+length;
			found = true;
		}
		previous_tpf = tpf;
		previous_s = s;
	}
}