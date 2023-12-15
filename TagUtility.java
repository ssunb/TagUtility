
/***************************************************************************/
//
//      Author : Subel Sunbeeb
//      Date:  January 3, 2001
//      Class:  TagUtility
//      Description :  Class to create Tag Utiliy.  Contains main() method.
//      -----------------------------------------------------------------------------------------------
//			Change Log
//      -----------------------------------------------------------------------------------------------
//      Date        Author   Description
//      01/22/01    SS       Make it NLS ready.
//      01/31/01    SS       Get tagU.gif from the jar file.
//      02/14/01    SS       Change labels  (extLabel and modifyFileLabel)
//      02/27/01    SS       Give user choice to include subdirectories and dhtml files.
//      03/01/01    SS       Add wildcard ('*')  in file search.
//      06/14/01    SS       Enable 'View Tags' and 'Edit Tags' button even when 'Remove' is selected.
/***************************************************************************/


import java.awt.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class TagUtility extends JFrame  implements ActionListener{

	// ******* NLS variables ************************************
   	public final static String ABOUT = TagResource.getString("ABOUT");
	public final static String INSERT_TAG = TagResource.getString("INSERT_TAG");
	public final static String REMOVE_TAG = TagResource.getString("REMOVE_TAG");
	public final static String UPDATE_TAG = TagResource.getString("UPDATE_TAG");
	public final static String SELECT_BASE_DIRECTORY = TagResource.getString("SELECT_BASE_DIRECTORY");
	public final static String BROWSE = TagResource.getString("BROWSE");
	public final static String MODIFY_FILES_WITH = TagResource.getString("MODIFY_FILES_WITH");
	public final static String MODIFY = TagResource.getString("MODIFY");
	public final static String COPIES_OF_FILES = TagResource.getString("COPIES_OF_FILES");
	public final static String ORIGINAL_FILES = TagResource.getString("ORIGINAL_FILES");
	public final static String PUT_TOP_TAG_AFTER = TagResource.getString("PUT_TOP_TAG_AFTER");
	public final static String PUT_BOTTOM_TAG_BEFORE = TagResource.getString("PUT_BOTTOM_TAG_BEFORE");
	public final static String SCRIPT_TEXT_FILE = TagResource.getString("SCRIPT_TEXT_FILE");
	public final static String EXECUTE = TagResource.getString("EXECUTE");
	public final static String VIEW_TAGS = TagResource.getString("VIEW_TAGS");
	public final static String EDIT_TAGS = TagResource.getString("EDIT_TAGS");
   	public final static String NOT_SELECTED = TagResource.getString("NOT_SELECTED");
   	public final static String NO_SPACES_ALLOWED = TagResource.getString("NO_SPACES_ALLOWED");
	public final static String BASE_DIR = TagResource.getString("BASE_DIR");
	public final static String EXTENSION = TagResource.getString("EXTENSION");
	public final static String ARE_YOU_SURE = TagResource.getString("ARE_YOU_SURE");
	public final static String THE_DIRECTORY = TagResource.getString("THE_DIRECTORY");
	public final static String AND_ALL_SUBDIR = TagResource.getString("AND_ALL_SUBDIR");
	public final static String AND = TagResource.getString("AND");
	public final static String UPDATE_IN_PLACE = TagResource.getString("UPDATE_IN_PLACE");
	public final static String TEXT_FILES = TagResource.getString("TEXT_FILES");
   	public final static String INCLUDE_SUBDIR = TagResource.getString("INCLUDE_SUBDIR");
   	public final static String INCLUDE_DHTML_PAGES = TagResource.getString("INCLUDE_DHTML_PAGES");
   	public final static String EXTENSION_NOT_ALLOWED = TagResource.getString("EXTENSION_NOT_ALLOWED");
	public final static String TITLE = TagResource.getString("TITLE");
	public final static String VERSION = TagResource.getString("VERSION");
   	public final static String EDITOR = TagResource.getString("EDITOR");
	// ******* NLS variables ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^


    	public final static String TAG_START = "<!-- KYV_TAG";
    	public final static String TAG_END = " -->";
    	public final static String TOP = "TOP";
   	public final static String BOTTOM = "BOTTOM";
   	public final static String  LOGFILE = "TagUtility.log";
	public final static String  ICON_IMAGE = "tagU.gif";

	public JTextArea tArea;
	public JTextField  dirField, fileField, extField;
	public JButton dirBrowse, fileBrowse, executeButton,  viewButton, editButton;
	public JRadioButton insert, remove, update, startHtml, startBody, endHtml, endBody, inPlace, inEbahtm;
	public JLabel  insertTopPointLabel,  insertBottomPointLabel, scriptFileLabel, fileInfo;
	public JCheckBox includeSubdir, includeDHTML;
	public String topTag, bottomTag;
	public JProgressBar progBar;
	boolean tagsDisplaying = false;

	TagUtilityMenu tuMenu;

   	public TagUtility() {
      		super(TITLE);
		//setIconImage(new ImageIcon(getClass().getResource(ICON_IMAGE)).getImage());
      		setBounds(200,100,590,580);
		setResizable(false);
      		addWindowListener(new TagWindowMonitor());
     		Container c = getContentPane();

		tuMenu = new TagUtilityMenu(this);
      		setJMenuBar(tuMenu);

      		JPanel p1 = new JPanel();
		Dimension size0 = new Dimension(510, 30);
		Dimension size = new Dimension(510, 28);
		p1.setPreferredSize(size0);
		p1.setMinimumSize(size0);
		p1.setLayout(new FlowLayout(FlowLayout.CENTER, 60, 0));
     		insert = new JRadioButton(INSERT_TAG, true);
		insert.setSize(500,20);
           		insert.addActionListener(this);
     		remove = new JRadioButton(REMOVE_TAG, false);
           		remove.addActionListener(this);
      		update = new JRadioButton(UPDATE_TAG, false);
           		update.addActionListener(this);
      		ButtonGroup group1 = new ButtonGroup();
      		group1.add(insert);
      		group1.add(remove);
      		group1.add(update);
      		EtchedBorder ti = new EtchedBorder(EtchedBorder.LOWERED);
      		p1.setBorder(ti);
      		p1. add(insert);
     		p1.add(remove);
      		p1.add(update);
      		c.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 0));
     		c.add(p1);

		// Since we want all three radio button groups to be aligned, using BorderLayout
		// is a good choice for  p4, p5, and p6.  Also, we want alignment between  p2 and  p7.
		// Other layout managers could be used; BorderLayout is just one good option  without
		// breaking NLS readiness (note:  languages that are read from right to left may have some
		// issues that need to be addressed if needed; other languages should be OK).
		JLabel  dirLabel = new JLabel("   "+SELECT_BASE_DIRECTORY);
		dirField = new JTextField(20);
		dirField.setText( System.getProperty("user.dir"));
		dirBrowse = new JButton(BROWSE);
           		dirBrowse.addActionListener(this);
		JPanel p2 = new JPanel();
		p2.setPreferredSize(size);
		p2.setMinimumSize(size);
      		p2.setLayout(new BorderLayout(0, 0));
      		p2.setBorder(ti);
		p2.add("West", dirLabel);
		JPanel p2p1 = new JPanel();
		p2p1.setLayout(new BorderLayout(5,0));
		Dimension size2 = new Dimension(340, 28);
		p2p1.setPreferredSize(size2);
		p2p1.setMinimumSize(size2);
		p2p1.add("Center", dirField);
		p2p1.add("East", dirBrowse);
		p2.add("East", p2p1);
     		c.add(p2);

		// Panel p12, p11, and p10 were added later and p## are higher than next panels.
		JPanel p12 = new JPanel();
		p12.setPreferredSize(size0);
		p12.setMinimumSize(size0);
		p12.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		includeSubdir = new JCheckBox(INCLUDE_SUBDIR, true);
		JPanel p10 = new JPanel();
		p10.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		p10.add(includeSubdir);
      		p10.setBorder(ti);
		Dimension size10 = new Dimension(205, 30);
		p10.setPreferredSize(size10);
		p10.setMinimumSize(size10);
		p12.add(p10);
		includeDHTML = new JCheckBox(INCLUDE_DHTML_PAGES);
		JPanel p11 = new JPanel();
		p11.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		p11.add(includeDHTML, "Center");
      		p11.setBorder(ti);
		Dimension size11 = new Dimension(305, 30);
		p11.setPreferredSize(size11);
		p11.setMinimumSize(size11);
		p12.add(p11);
		c.add(p12);


		JLabel  extLabel = new JLabel("   "+MODIFY_FILES_WITH);
		extField = new JTextField(16);
		extField.setText(".htm");
		JPanel p3 = new JPanel();
		p3.setPreferredSize(size);
		p3.setMinimumSize(size);
      		p3.setLayout(new BorderLayout(10, 0));
      		p3.setBorder(ti);
		p3.add("West" ,extLabel);
		p3.add("East", extField);
     		c.add(p3);

		JLabel  modifyFileLabel = new JLabel("   "+MODIFY);
		inPlace = new JRadioButton(ORIGINAL_FILES, false);
      		inEbahtm = new JRadioButton(COPIES_OF_FILES, true);
      		ButtonGroup group2 = new ButtonGroup();
      		group2.add(inPlace);
      		group2.add(inEbahtm);
		JPanel p4 = new JPanel();
		p4.setPreferredSize(size);
		p4.setMinimumSize(size);
      		p4.setLayout(new BorderLayout(0,0));
      		p4.setBorder(ti);
		p4.add("West", modifyFileLabel);
		JPanel p4p1 = new JPanel();
		Dimension size3 = new Dimension(320, 28);
		p4p1.setPreferredSize(size3);
		p4p1.setMinimumSize(size3);
		p4p1.setLayout(new BorderLayout(0,0));
		p4p1.add("West",inEbahtm);
		JPanel p4p1p1 = new JPanel();
		Dimension size4 = new Dimension(145, 28);
		p4p1p1.setPreferredSize(size4);
		p4p1p1.setMinimumSize(size4);
		p4p1p1.setLayout(new BorderLayout(0,0));
		p4p1p1.add("West", inPlace);
		p4p1.add("East", p4p1p1);
		p4.add("East", p4p1);
     		c.add(p4);

		insertTopPointLabel = new JLabel("   "+PUT_TOP_TAG_AFTER);
		startHtml = new JRadioButton("<HTML>", true);
      		startBody = new JRadioButton("<BODY>", false);
      		ButtonGroup group3 = new ButtonGroup();
      		group3.add(startHtml);
      		group3.add(startBody);
		JPanel p5 = new JPanel();
		p5.setPreferredSize(size);
		p5.setMinimumSize(size);
		p5.setLayout(new BorderLayout(0,0));
		p5.setBorder(ti);
		p5.add("West", insertTopPointLabel);
		JPanel p5p1 = new JPanel();
		p5p1.setPreferredSize(size3);
		p5p1.setMinimumSize(size3);
		p5p1.setLayout(new BorderLayout(0,0));
		p5p1.add("West", startHtml);
		JPanel p5p1p1 = new JPanel();
		p5p1p1.setPreferredSize(size4);
		p5p1p1.setMinimumSize(size4);
		p5p1p1.setLayout(new BorderLayout(0,0));
		p5p1p1.add("West", startBody);
		p5p1.add("East", p5p1p1);
		p5.add("East", p5p1);
     		c.add(p5);

		insertBottomPointLabel = new JLabel("   "+PUT_BOTTOM_TAG_BEFORE);
		endHtml = new JRadioButton("</HTML>", true);
      		endBody = new JRadioButton("</BODY>", false);
      		ButtonGroup group4 = new ButtonGroup();
      		group4.add(endHtml);
      		group4.add(endBody);
		JPanel p6 = new JPanel();
		p6.setPreferredSize(size);
		p6.setMinimumSize(size);
		p6.setLayout(new BorderLayout(0,0));
		p6.setBorder(ti);
		p6.add("West", insertBottomPointLabel);
		JPanel p6p1 = new JPanel();
		p6p1.setPreferredSize(size3);
		p6p1.setMinimumSize(size3);
		p6p1.setLayout(new BorderLayout(0,0));
		p6p1.add("West", endHtml);
		JPanel p6p1p1 = new JPanel();
		p6p1p1.setPreferredSize(size4);
		p6p1p1.setMinimumSize(size4);
		p6p1p1.setLayout(new BorderLayout(0,0));
		p6p1p1.add("West", endBody);
		p6p1.add("East", p6p1p1);
		p6.add("East", p6p1);
     		c.add(p6);

		scriptFileLabel = new JLabel("   "+SCRIPT_TEXT_FILE);
		fileField = new JTextField(23);
		fileField.setText( System.getProperty("user.dir")+File.separator+"tags.txt");
		fileBrowse = new JButton(BROWSE);
           		fileBrowse.addActionListener(this);
		JPanel p7 = new JPanel();
		p7.setPreferredSize(size);
		p7.setMinimumSize(size);
      		p7.setLayout(new BorderLayout(0, 0));
      		p7.setBorder(ti);
		p7.add("West", scriptFileLabel);
		JPanel p7p1 = new JPanel();
		p7p1.setLayout(new BorderLayout(5,0));
		p7p1.setPreferredSize(size2);
		p7p1.setMinimumSize(size2);
		p7p1.add("Center", fileField);
		p7p1.add("East", fileBrowse);
		p7.add("East", p7p1);
     		c.add(p7);

		executeButton = new JButton(" "+EXECUTE+" ");
		executeButton.addActionListener(this);
		viewButton = new JButton(VIEW_TAGS);
		viewButton.addActionListener(this);
		editButton = new JButton(EDIT_TAGS);
		editButton.addActionListener(this);
		JPanel p8 = new JPanel();
		p8.setLayout(new FlowLayout(FlowLayout.CENTER, 121, 15));
		p8.add(executeButton);
		p8.add(viewButton);
		p8.add(editButton);
     		c.add(p8);

		tArea = new JTextArea(13,46);
		JScrollPane jsp = new JScrollPane(tArea);
		tArea.setEditable(false);
		c.add(jsp);

		JPanel p9 = new JPanel();
		p9.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
            		fileInfo = new JLabel();
           		p9.add(fileInfo);
		progBar = new JProgressBar(0, 99);
            		p9.add(progBar);
		progBar.setVisible(false);
		fileInfo.setVisible(false);
		c.add(p9);
   	}

	 public void actionPerformed(ActionEvent e) {
      		Object object = e.getSource();
		if (object == dirBrowse)
			pickDir();
		else if (object == fileBrowse)
			pickFile();
		else if ((object == insert) || (object == update)) {
			startHtml.setEnabled(true);
			endHtml.setEnabled(true);
			startBody.setEnabled(true);
			endBody.setEnabled(true);
			fileBrowse.setEnabled(true);
			insertTopPointLabel.setEnabled(true);
			insertBottomPointLabel.setEnabled(true);
			scriptFileLabel.setEnabled(true);
			fileField.setEnabled(true);
			// editButton.setEnabled(true);
			// viewButton.setEnabled(true);
			// tuMenu.edit.setEnabled(true);
			// tuMenu.view.setEnabled(true);
			includeDHTML.setEnabled(true);
		}
		else if (object == remove) {
			startHtml.setEnabled(false);
			endHtml.setEnabled(false);
			startBody.setEnabled(false);
			endBody.setEnabled(false);
			fileBrowse.setEnabled(false);
			insertTopPointLabel.setEnabled(false);
			insertBottomPointLabel.setEnabled(false);
			scriptFileLabel.setEnabled(false);
			fileField.setEnabled(false);
			// editButton.setEnabled(false);
			// viewButton.setEnabled(false);
			// tuMenu.edit.setEnabled(false);
			// tuMenu.view.setEnabled(false);
			includeDHTML.setEnabled(false);
		}
		else if (object == executeButton)
			processFiles();
		else if (object == viewButton)
			viewTags();
		else if (object == editButton)
			editTags();
	}

 	public void editTags() {
		String scriptFile = new String(fileField.getText());
		if (scriptFile.length() < 1)
			JOptionPane.showMessageDialog( this, TagNLS.getStringFrom(NOT_SELECTED, SCRIPT_TEXT_FILE),
		    		TITLE, JOptionPane.ERROR_MESSAGE);
		else if (scriptFile.charAt(0) == ' ')
			JOptionPane.showMessageDialog( this,TagNLS.getStringFrom(NO_SPACES_ALLOWED, SCRIPT_TEXT_FILE),
		    		TITLE, JOptionPane.ERROR_MESSAGE);
		else {
			if (scriptFile.indexOf(File.separator) == -1)
				scriptFile = System.getProperty("user.dir") + File.separator + scriptFile;
			TagEditor  tagEditor = new TagEditor(this, scriptFile, false, EDITOR);
			tagEditor.setVisible(true);
		}
	}

	public void viewTags() {
		new TagView(this, fileField.getText(), true);
		tagsDisplaying = true;
	}

	public void processFiles() {
		tagsDisplaying = false;
		if (dirField.getText().length() < 1)
			JOptionPane.showMessageDialog( this, TagNLS.getStringFrom(NOT_SELECTED, BASE_DIR),
		    		TITLE, JOptionPane.ERROR_MESSAGE);
		else if (dirField.getText().charAt(0) == ' ')
			JOptionPane.showMessageDialog( this, TagNLS.getStringFrom(NO_SPACES_ALLOWED, BASE_DIR),
		    		TITLE, JOptionPane.ERROR_MESSAGE);
		else if (extField.getText().length() < 1)
			JOptionPane.showMessageDialog( this, TagNLS.getStringFrom(NOT_SELECTED, EXTENSION),
		    		TITLE, JOptionPane.ERROR_MESSAGE);
		else if (extField.getText().charAt(0) == ' ')
			JOptionPane.showMessageDialog( this, TagNLS.getStringFrom(NO_SPACES_ALLOWED, EXTENSION) ,
		    		TITLE, JOptionPane.ERROR_MESSAGE);
		else if ((extField.getText().indexOf("*") > -1)  && !extField.getText().equals("*") && !extField.getText().equals("*.*"))
			JOptionPane.showMessageDialog( this, TagNLS.getStringFrom(EXTENSION_NOT_ALLOWED, extField.getText()) ,
		    		TITLE, JOptionPane.ERROR_MESSAGE);
		else {
			if  (!remove.isSelected()) {
  				new TagView(this, fileField.getText(), false);
				if ((topTag == null) || (bottomTag == null))
					return;
			}
			int x;
			if (includeSubdir.isSelected()) {
				if (inEbahtm.isSelected()) {
					String msg[] = {ARE_YOU_SURE, TagNLS.getStringFrom(THE_DIRECTORY,dirField.getText()),
						AND_ALL_SUBDIR+"?"};
					x = JOptionPane.showConfirmDialog( this, msg, TITLE, JOptionPane.YES_NO_OPTION);
				}
				else {
					String msg[] = {ARE_YOU_SURE, TagNLS.getStringFrom(THE_DIRECTORY,dirField.getText()),
						AND_ALL_SUBDIR+" "+AND,  UPDATE_IN_PLACE};
					x = JOptionPane.showConfirmDialog( this, msg, TITLE, JOptionPane.YES_NO_OPTION);
				}
			}
			else {
				if (inEbahtm.isSelected()) {
					String msg[] = {ARE_YOU_SURE, TagNLS.getStringFrom(THE_DIRECTORY,dirField.getText())+"?"};
					x = JOptionPane.showConfirmDialog( this, msg, TITLE, JOptionPane.YES_NO_OPTION);
				}
				else {
					String msg[] = {ARE_YOU_SURE, TagNLS.getStringFrom(THE_DIRECTORY,dirField.getText()),
								AND+" "+UPDATE_IN_PLACE};
					x = JOptionPane.showConfirmDialog( this, msg, TITLE, JOptionPane.YES_NO_OPTION);
				}
			}
			if  (x == JOptionPane.YES_OPTION)
				new TagExecute(this);
		}
	}

	public void pickDir() {
		JFileChooser chooser = new JFileChooser(dirField.getText());
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int option = chooser.showOpenDialog(this);
		if (option == JFileChooser.APPROVE_OPTION)
			dirField.setText(chooser.getSelectedFile().getPath());
	}

	public void pickFile() {
		String [] txt = new String[] {"txt"};
		JFileChooser chooser = new JFileChooser(fileField.getText());
		chooser.addChoosableFileFilter(new TagFileFilter(txt, TEXT_FILES+" (*.txt)"));
		int option = chooser.showOpenDialog(this);
		if (option == JFileChooser.APPROVE_OPTION)
			fileField.setText(chooser.getSelectedFile().getPath());
	}

   	static public void main(String s[]) {
     		TagUtility tu;
    		tu =  new TagUtility();
     		tu.setVisible(true);
   	}
}










