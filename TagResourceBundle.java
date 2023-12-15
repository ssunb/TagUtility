/***************************************************************************/
//
//      Author : Subel Sunbeeb
//      Date:  January 22, 2001
//      Class:  TagResourceBundle
//      Description :  Default Resource Bundle (_en_US)  for Tagging Utility.
//      --------------------------------------------------------------------------------------------------------------------------------------
//			Change Log
//      --------------------------------------------------------------------------------------------------------------------------------------
//      Date        Author     Description
//      02/01/01    SS         Add TagExecute_MESSAGE54
//      02/14/01    SS         Add TagExecute_MESSAGE55, and TagExecute_MESSAGE56
//      02/27/01    SS         Add INCLUDE_SUBDIR,  INCLUDE_DHTML_PAGES,  and TagExecute_MESSAGE56
//      03/01/01    SS         Add TagExecute_MESSAGE58, TagExecute_MESSAGE59, and OUT_OF_MEMORY
//      03/02/01    SS         Add TagExecute_MESSAGE60, and TagExecute_MESSAGE61
/********************************************************************************************************/

import java.util.ListResourceBundle;
import java.text.*;
import java.lang.*;

public class TagResourceBundle extends ListResourceBundle {
	public Object[][] getContents(){
		return contents;
	}

	public static final String  R_TagUtility = "TagUtility";
	public static final String  R_TagExecutre = "TagExecute";

	static final Object[][] contents =	{

/*************************************************************************************/
//  Common strings,  menu items, buttons, etc.
/*************************************************************************************/
		{"FILE", "File"},
		{"EDIT", "Edit"},
		{"HELP", "Help"},
		{"NEW", "New"},
		{"OPEN", "Open"},
		{"SAVE", "Save"},
		{"CUT", "Cut"},
		{"COPY", "Copy"},
		{"PASTE", "Paste"},
		{"TOPICS", "Help Topics"},
		{"PASTE", "Paste"},
		{"FIND", "Find"},
		{"EXIT", "Exit"},
		{"SAVE_AS", "Save As"},
		{"EXECUTE", "Execute"},
// # of words above: 17


/* NOTE:   Error codes are only used in those error messages that will be printed in stdout  due to exceptions.   */
/* It will be helpful to determine from where the message is  generated.  Many file may not have these error 	       */
/*  messages but error code range is assigned to all files in case those are needed in future.		       */

/*************************************************************************************/
//  Strings used in TagCopyright.java	(Error code range:  100 - 109)
/*************************************************************************************/
		{"COPYRIGHT", "Sunbeeb Technology, All Rights Reserved."},
		{"WEBSITE", "http://www.sunbeeeb.com"},

	/*** following Copyright Warning  message is broken into 5 peices but should be translated as a single message and then
       			should be broken into 5 peices to be fitted into copyright window  ***/
		{"WARNING1", "WARNING:   This program is protected by copyright law and international treaties, is subject"},
		{"WARNING2", "to the Sunbeeb Technology License Agreement,  and is subject to U.S. and other export regulations."},
		{"WARNING3", "This program is provided with  RESTRICTED RIGHTS  for U.S. government users."},
		{"WARNING4", "Unauthorized reproduction or distribution of  this  program, or any portion of  it may result"},
		{"WARNING5", "in severe civil and criminal penalties, and will be prosecuted to the fullest extent of the law."},
// # of words above: 77

/*************************************************************************************/
//  Strings used in TagEditor.java		(Error code range:  110 - 119)
/*************************************************************************************/
		{"TagEditor_ERROR1", "(110)  Error: "},
// # of words above: 2

/*************************************************************************************/
//  Strings used in TagEditorFind.java 	(Error code range:  120 - 129)
/*************************************************************************************/
		// few strings are already in common area
		{"FIND_WHAT", "Find what:"},
		{"MATCH_CASE", "Match Case"},
		{"FIND_NEXT", "Find Next"},
		{"CANCEL", "Cancel"},
		{"FIND_ERROR", "Find ERROR"},
		{"FIND_WARNING", "Find WARNING"},
		{"ERROR", "ERROR:"},
		{"WARNING", "WARNING:"},
		{"TagEditorFind_MESSAGE1", "Cannot find  \"%1\"  in  %2."},
		{"TagEditorFind_MESSAGE2", "No more  \"%1\"  found in %2."},
// # of words above: 14

/*************************************************************************************/
//  Strings used in TagEditorMenu.java 	(Error code range:  130 - 139)
/*************************************************************************************/
		// several strings are in common area

/*************************************************************************************/
//  Strings used in TagEditorToolBar.java 	(Error code range:  140 - 149)
/*************************************************************************************/
		// several strings are in common area

/*************************************************************************************/
//  Strings used in TagFileFilter.java 	(Error code range:  150 - 159)
/*************************************************************************************/
		{"FILES", "files"},
// # of words above: 1

/*************************************************************************************/
//  Strings used in TagPageFrame.java 	(Error code range:  160 - 169)
/*************************************************************************************/
		// several strings are in common area
		{"TagPageFrame_MESSAGE1", "File  %1  not found."},
		{"TagPageFrame_MESSAGE2", "Do you want to create the file?"},
		{"TagPageFrame_MESSAGE3", "Unable to read file  %1"},
		{"TagPageFrame_MESSAGE4", "Unable to load file  %1"},
		{"TagPageFrame_MESSAGE5", "File  %1  saved."},
		{"TagPageFrame_MESSAGE6", "Unable to save file  %1"},
		{"TagPageFrame_MESSAGE7", "File  %1  already exists."},
		{"TagPageFrame_MESSAGE8", "Do you want to replace it?"},
		{"TagPageFrame_MESSAGE9", "The text in file  %1  has changed."},
		{"TagPageFrame_MESSAGE10", "Do you want to save the changes?"},
// # of words above: 46

/*************************************************************************************/
//  Strings used in TagUtility.java 	 	(Error code range:  170 - 179)
/*************************************************************************************/
		// several strings are in common area
		{"ABOUT", "About"},
		{"INSERT_TAG",  "Insert Tags"},
		{"REMOVE_TAG",  "Remove Tags"},
		{"UPDATE_TAG",  "Update Tags"},
		{"SELECT_BASE_DIRECTORY", "Base Directory:"},
		{"BROWSE", "browse..."},
		{"MODIFY_FILES_WITH", "Modify Files With Extension (or Files End With):"},
		{"MODIFY", "Modify:"},
		{"COPIES_OF_FILES", "Copies of Files"},
		{"ORIGINAL_FILES", "Original Files"},
		{"PUT_TOP_TAG_AFTER", "Put Top Tag After:"},
		{"PUT_BOTTOM_TAG_BEFORE", "Put Bottom Tag Before:"},
		{"SCRIPT_TEXT_FILE", "Script Text File:"},
		{"VIEW_TAGS", "View Tags"},
		{"EDIT_TAGS", "Edit Tags"},
		{"NOT_SELECTED", "%1  not selected."},
		{"NO_SPACES_ALLOWED", "No spaces are allowed before %1"},
		{"BASE_DIR", "Base Directory"},
		{"EXTENSION", "Extension"},
		{"ARE_YOU_SURE", "Are you sure you want to process"},
		{"THE_DIRECTORY", "the directory %1"},
		{"AND_ALL_SUBDIR", "and all of its subdirectories"},
		{"AND", "and"},
		{"UPDATE_IN_PLACE", "to update all matching original files?"},
		{"TEXT_FILES", "Text files"},
		{"TITLE", "Tagging Utility"},
		{"VERSION", "1.2.0"},
		{"EDITOR", "Editor"},
		{"INCLUDE_SUBDIR", "Include Subdirectories"},
		{"INCLUDE_DHTML_PAGES", "Include Dynamically Created Pages"},
		{"EXTENSION_NOT_ALLOWED", "Extension  %1  not allowed.  Use   *   or   *.*   to select all files."},

// # of words above: 83

/*************************************************************************************/
//  Strings used in TagUtilityMenu.java 	 	(Error code range:  180 - 189)
/*************************************************************************************/
		// several strings are in common area
		{"TOOLS", "Tools"},
		{"VIEW_TAGS_IN", "View Tags In Message Box"},
		{"EDIT_TAGS_IN", "Edit Tags In Script Text File"},
		{"VIEW_LOG", "View Log File"},
		{"CLEAR", "Clear Message Box"},
		{"VIEWER", "Viewer"},
// # of words above: 19

/*************************************************************************************/
//  Strings used in TagView.java 	 	(Error code range:  190 - 199)
/*************************************************************************************/
		{"TOP_TAG", "Top Tag"},
		{"BOTTOM_TAG", "Bottom Tag"},
		{"TagView_MESSAGE1", "Both Top Tag and Bottom Tag not found in file  %1"},
		{"TagView_MESSAGE2", "Top Tag not found in file  %1"},
		{"TagView_MESSAGE3", "Bottom Tag not found in file  %1"},
		{"TagView_MESSAGE4", "Unable to read Script Text file  %1"},

// # of words above: 32

/*************************************************************************************/
//  Strings used in TagExecute.java 	 	(Error code range:  200 - 299)
/*************************************************************************************/
		{"SEARCHING_FILES", "Searching files..."},
		{"INSERTING_TAGS", "Inserting tags in file"},
		{"REMOVING_TAGS", "Removing tags from file"},
		{"UPDATING_TAGS", "Updating tags in file"},
		{"PROCESSING_SUCCESSFUL", "Processing successful.  Original file modified."},
		{"PROCESSING_SUCCESSFUL_2", "Processing successful.  Original file unchanged."},
		{"PROCESSING_TERMINATED", "Processing terminated without any modifications."},
		{"OUT_OF_MEMORY", "Your system has run out of memory."},
		{"TagExecute_ERROR1", "ERROR (201): "},
		{"TagExecute_ERROR2", "ERROR (202): "},
		{"TagExecute_ERROR3", "ERROR (203): "},
		{"TagExecute_ERROR4", "ERROR (204): "},
		{"TagExecute_MESSAGE1", "Processing directory   %1"},
		{"TagExecute_MESSAGE2", "Base Directory  '%1'  not found."},
		{"TagExecute_MESSAGE3", "ERROR:  Unable to create log file.  Error Message:  %1"},
		{"TagExecute_MESSAGE4", "Processing terminated without any modifications."},
		{"TagExecute_MESSAGE5", "Processing started on:  %1"},
		{"TagExecute_MESSAGE6", "Found  %1  files.  Now processing ..."},
		{"TagExecute_MESSAGE7", "Processing File:  %1"},
		{"TagExecute_MESSAGE8", "Processing completed on:  %1"},
		{"TagExecute_MESSAGE9", "ERROR:  Unable to close log file.  Error Message:  %1"},
		{"TagExecute_MESSAGE10", "ERROR:  Unable to read file.  Error Message:  %1"},
		{"TagExecute_MESSAGE11", "WARNING:  Frameset code found.  No tags will be inserted."},
		{"TagExecute_MESSAGE12", "WARNING:  Tags already exist.  No tags will be inserted."},
		{"TagExecute_MESSAGE13", "WARNING:  Client side redirection code found.  No tags will be inserted."},
// # of words above: 120
		{"TagExecute_MESSAGE14", "WARNING:  No tags found to remove."},
		{"TagExecute_MESSAGE15", "WARNING:  No tags found to update."},
		{"TagExecute_MESSAGE16", "WARNING:  Client side redirection code found.  Top Tag cannot be inserted after  '%1>'."},
		{"TagExecute_MESSAGE17", "ERROR:  Error occured while removing previous tags.  Update not successful."},
		{"TagExecute_MESSAGE18", "ERROR:  Error occured while inserting tags.  Update not successful."},
		{"TagExecute_MESSAGE19", "ERROR:  Unable to create directory (%1).  Error Message:  %2"},
		{"TagExecute_MESSAGE20", "ERROR:  Unable to write in file (%1).  Error Message:  %2"},

		{"TagExecute_MESSAGE21", "WARNING:  Only Top Tag inserted.  %1> not found."},
		{"TagExecute_MESSAGE22", "WARNING:  Only Bottom Tag inserted.  %1> not found."},
		//{"TagExecute_MESSAGE21", "ERROR:  No tags inserted.  %1> not found."},
		//{"TagExecute_MESSAGE22", "ERROR:   %1  %2>  found but only  %3  %4>  found."},

		{"TagExecute_MESSAGE23", "ERROR:  Ending '>' not found for  %1"},
		{"TagExecute_MESSAGE24", "INFORMATION:  Dynamic HTML code found while inserting Top Tag."},
		{"TagExecute_MESSAGE25", "INFORMATION:  %1 Top Tag(s) inserted."},
		{"TagExecute_MESSAGE26", "INFORMATION:  Dynamic HTML code found while inserting Bottom Tag."},
		{"TagExecute_MESSAGE27", "INFORMATION:  %1 Bottom Tag(s) inserted."},
// # of words above: 101
		{"TagExecute_MESSAGE28", "WARNING: No tags inserted.  Both  %1>  and  %2>  not found."},
		{"TagExecute_MESSAGE29", "ERROR:  Unable to find  %1  while removing tag."},
		{"TagExecute_MESSAGE30", "Error occured while writing in log file.  Error message:  %1"},
		{"TagExecute_MESSAGE31", "%1 file(s) with extension  '%2'  were found in  '%3'  and all of its sub-directories."},
		{"TagExecute_MESSAGE32", "%1 file(s) were successfully modified."},
		{"TagExecute_MESSAGE33", "%1 had both Top Tag and Bottom Tag inserted."},

		{"TagExecute_MESSAGE34", "%1 had only Top Tag inserted."},
		{"TagExecute_MESSAGE35", "%1 had only Bottom Tag inserted."},
		//{"TagExecute_MESSAGE34", "%1 had no %2>"},
		//{"TagExecute_MESSAGE35", "%1 had different numbers of  %2>  and  %3>."},

		{"TagExecute_MESSAGE36", "%1 had total %2 tags (both Top Tag and Bottom Tag) removed."},
		{"TagExecute_MESSAGE37", "%1 had total %2 old tags removed and %3 new tags inserted."},
		{"TagExecute_MESSAGE38", "%1 file(s) were not modified."},
		{"TagExecute_MESSAGE39", "%1 had no %2> and %3>."},
		{"TagExecute_MESSAGE40", "%1 previously contained tags."},
		{"TagExecute_MESSAGE41", "%1 had %2 code."},
		{"TagExecute_MESSAGE42", "%1 had cliend side redirection code."},
		{"TagExecute_MESSAGE43", "%1 had no tags to remove."},
		{"TagExecute_MESSAGE44", "%1 had no tags to update."},
		{"TagExecute_MESSAGE45", "%1 had cliend side redirection code."},
		{"TagExecute_MESSAGE46", "%1 encountered processing error(s)."},
		{"TagExecute_MESSAGE47", "%1  total tag(s) inserted."},
		{"TagExecute_MESSAGE48", "%1 Top Tag inserted."},
		{"TagExecute_MESSAGE49", "%1 Bottom Tag inserted."},
		{"TagExecute_MESSAGE50", "%1  total tag(s) updated."},
		{"TagExecute_MESSAGE51", "%1 Top Tag updated."},
		{"TagExecute_MESSAGE52", "%1 Bottm Tag updated."},
		{"TagExecute_MESSAGE53", "Please  'View Log File'  from  'Tools'  menu for details."},
		{"TagExecute_MESSAGE54", "ERROR:  No character found after %1> at the end of the file while inserting Top Tag."},
		{"TagExecute_MESSAGE55", "%1 file(s) were successfully modified and placed into corresponding %2 subdirectory."},
		{"TagExecute_MESSAGE56", "Modified file saved as %1"},
		{"TagExecute_MESSAGE57", "%1 file(s) with extension  '%2'  were found in  '%3'."},
		{"TagExecute_MESSAGE58", "%1 file(s) with following extensions were found in  '%2'  and all of its sub-directories:"},
		{"TagExecute_MESSAGE59", "%1 file(s) with following extensions were found in  '%2':"},
		{"TagExecute_MESSAGE60", "ERROR:  'Out of Memory Error'  occured while processing file   %1.  File size:  %2 bytes."},
		{"TagExecute_MESSAGE61", "%1 encountered system error(s)."},
// # of words above: 175


/*************************************************************************************/
//  Strings used in TagHelp.java 	 	(Error code range:  300 - 310)
/*************************************************************************************/
		{"TagHelp_MESSAGE1", "Online Help is not supported in  %1  systems."},
		{"TagHelp_MESSAGE2", "Unable to start online help.  Error message: %1"},
// # of words above: 14

/*************************************************************************************/
//  Strings used in TagWindowMonitor.java 	 	(Error code range:  320 - 330)
/*************************************************************************************/
		// none at this time


/*************************************************************************************/
//  TagNLS, TagResource, TagResourceBundle files cannot have NLS string itself.  (Error code range:  310 - 320)
/*************************************************************************************/

    };
}

