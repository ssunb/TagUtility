
/***************************************************************************/

//
//      Author : Subel Sunbeeb
//      Date:  January 3, 2001
//      Class:  TagCopyright
//      Description :  Class to create Copyright information.
//      -----------------------------------------------------------------------------------------------
//			Change Log
//      -----------------------------------------------------------------------------------------------
//      Date             Author         Description
//      01/22/01         SS             Make it NLS ready.
//      01/31/01         SS             Get sunbeeb.gif file from the jar file.
/***************************************************************************/


import java.awt.*;
import java.io.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
//import java.net.URL;

public class TagCopyright extends JWindow implements MouseListener {

	// *** NLS variables *******************************************
	private final static String  COPYRIGHT =  TagResource.getString("COPYRIGHT");
	private final static String  WEBSITE   =  TagResource.getString("WEBSITE");
	private final static String  WARNING1  =  TagResource.getString("WARNING1");
   	private final static String  WARNING2  =  TagResource.getString("WARNING2");
   	private final static String  WARNING3  =  TagResource.getString("WARNING3");
   	private final static String  WARNING4  =  TagResource.getString("WARNING4");
   	private final static String  WARNING5  =  TagResource.getString("WARNING5");
	// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

	public final static String  TITLE = TagUtility.ABOUT+" "+TagUtility.TITLE;
 	public final static String  SUNBEEB_GIF = "sunbeeb.gif";


	public TagCopyright(JFrame f) {
      	super(f);
   		setSize(485,275);
		Point pt = new Point(f.getLocation());
		pt.translate(58,110);
		setLocation(pt);
		addMouseListener(this);
     	Container c = getContentPane();
		c.setBackground(Color.lightGray);
 		c.setLayout(new FlowLayout(FlowLayout.CENTER, 5 , 15));
		JLabel l0 = new JLabel(new ImageIcon(getClass().getResource(SUNBEEB_GIF)));
		c.add(l0);
		JLabel l1 = new JLabel(TagUtility.TITLE+" "+TagUtility.VERSION);
		c.add(l1);
		JLabel l2 = new JLabel(COPYRIGHT);
		c.add(l2);
		JLabel l3 = new JLabel(WEBSITE);
		Font font = new Font("Dialog", Font.BOLD, 18);
		l3.setFont(font);
		//l3.setBorder(BorderFactory.createLineBorder(Color.blue));
		c.add(l3);

      	JPanel p3 = new JPanel();
		p3.setBackground(Color.lightGray);
		p3.setLayout(new GridLayout(0,1,0,0));
		JLabel l5 = new JLabel(WARNING1);
		font = new Font("Dialog", Font.PLAIN , 9);
		l5.setFont(font);
		p3.add(l5);
		JLabel l6 = new JLabel(WARNING2);
		l6.setFont(font);
		p3.add(l6);
		JLabel l7 = new JLabel(WARNING3);
		l7.setFont(font);
		p3.add(l7);
		JLabel l8 = new JLabel(WARNING4);
		l8.setFont(font);
		p3.add(l8);
		JLabel l9 = new JLabel(WARNING5);
		l9.setFont(font);
		p3.add(l9);
		c.add(p3);
		//BevelBorder bb = new BevelBorder(BevelBorder.RAISED, Color.blue, Color.gray);
		//(bb);
		setVisible(true);
	}

	public void mouseClicked(MouseEvent e) {
			setVisible(false);
			dispose();
	}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
}