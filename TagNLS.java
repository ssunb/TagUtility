
/***************************************************************************/
//
//      Author : Subel Sunbeeb
//      Date:  January 22, 2001
//      Class:  TagNLS
//      Description :  Class to format messages.
//      ---------------------------------------------------------------------
//			Change Log
//      ---------------------------------------------------------------------
//      Date        Author       Description
//
/***************************************************************************/

public class TagNLS {

    /* Message with 1 arg */
    public static String getStringFrom( String stringTemplate, String arg1 ) {

        String answer;
        int p;

        // Check for %1
        if(! ((p = stringTemplate.indexOf("%1")) >= 0) )
        {
            //No sub found; return the string without modification
            return stringTemplate;
        }

        answer = stringTemplate.substring(0, p);    //Leading substring
        answer += arg1;                             //Argument
        answer += stringTemplate.substring(p+2);    //Trailing substring

        return answer;

    }//End Method


    /* Message with 2 arg */
    public static String getStringFrom( String stringTemplate, String arg1, String arg2 ) {

        String temp = stringTemplate;
        String answer = temp;
        int p;

        // Check for %1
        if( (p = temp.indexOf("%1")) >= 0 )
        {

            answer = temp.substring(0, p);    //Leading substring
            answer += arg1;                   //Argument
            answer += temp.substring(p+2);    //Trailing substring
            temp = answer;

        }
        // Check for %2
        if( (p = temp.indexOf("%2")) > 0 )
        {
            answer = temp.substring(0, p);    //Leading substring
            answer += arg2;                   //Argument
            answer += temp.substring(p+2);    //Trailing substring
            temp = answer;
        }
        return answer;

    }//End Method

    /* Message with 3 arg */
    public static String getStringFrom( String stringTemplate, String arg1, String arg2, String arg3){

        String temp = stringTemplate;
        String answer = temp;
        int p;

        // Check for %1
        if( (p = temp.indexOf("%1")) >= 0 )
        {
            answer = temp.substring(0, p);    //Leading substring
            answer += arg1;                   //Argument
            answer += temp.substring(p+2);    //Trailing substring
            temp = answer;

        }
        // Check for %2
        if( (p = temp.indexOf("%2")) > 0 )
        {
            answer = temp.substring(0, p);    //Leading substring
            answer += arg2;                   //Argument
            answer += temp.substring(p+2);    //Trailing substring
            temp = answer;
        }
        //Check for %3
        if( (p = temp.indexOf("%3")) > 0 )
        {
            answer = temp.substring(0, p);    //Leading substring
            answer += arg3;                   //Argument
            answer += temp.substring(p+2);    //Trailing substring
            temp = answer;
        }
        return answer;

    }//End Method

    /* Message with 4 arg */
    public static String getStringFrom( String stringTemplate, String arg1, String arg2, String arg3,String arg4) {

        String temp = stringTemplate;
        String answer = temp;
        int p;

        // Check for %1
        if( (p = temp.indexOf("%1")) >= 0 )
        {
            answer = temp.substring(0, p);    //Leading substring
            answer += arg1;                   //Argument
            answer += temp.substring(p+2);    //Trailing substring
            temp = answer;
        }
        // Check for %2
        if( (p = temp.indexOf("%2")) > 0 )
        {
            answer = temp.substring(0, p);    //Leading substring
            answer += arg2;                   //Argument
            answer += temp.substring(p+2);    //Trailing substring
            temp = answer;
        }
        //Check for %3
        if( (p = temp.indexOf("%3")) > 0 )
        {
            answer = temp.substring(0, p);    //Leading substring
            answer += arg3;                   //Argument
            answer += temp.substring(p+2);    //Trailing substring
            temp = answer;
        }
        //Check for %4
        if( (p = temp.indexOf("%4")) > 0 )
        {
            answer = temp.substring(0, p);    //Leading substring
            answer += arg4;                   //Argument
            answer += temp.substring(p+2);    //Trailing substring
            temp = answer;
        }

        return answer;

    }//End Method
}//End Class