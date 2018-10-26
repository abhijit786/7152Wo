package com;

import java.awt.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.apache.commons.lang3.StringEscapeUtils;
import org.w3c.dom.UserDataHandler;

class Sample{
	public static HashMap characterMappings = new HashMap();


	public static String logfilepath="D:/test/log.log";
	public static String inputfilePath = "D:/test/in.csv";
	public static String inputsetfilePath = "D:/test/inputset.csv";
	public static String outputfilepath="D:/test/output.csv";

	public static int k=1;



public static void main(String[] args) throws IOException {



// this is the hashmap that stores the mappings of the characters to their ascii equivalent




	System.out.println("Starting Program Execution.");
	logging("Program Execution Started","info");
	
	System.out.println("Preparing Set Mappings");
	logging("Preparing Set Mappings","info");
	
//	prepareCsvSet("\u0142", "l");
//	prepareCsvSet("\u00EB", "e");
//	prepareCsvSet("\u2022", ".");
//	prepareCsvSet(83,70);
//	prepareCsvSet("\u0020",56);
//	prepareCsvSet('Â','A');
//	
	
	
	File fileDir = new File(inputsetfilePath);
	BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileDir), "UTF8"));

	String str=new String();
	while ((str = in.readLine()) != null) {
	String[] fields = str.split("\\|");
	System.out.println(fields[0]+ "-->"+ fields[1]);
	String st1 = new String();
	st1=StringEscapeUtils.unescapeJava(fields[0]);
	
	String st2 = new String();
	st2=StringEscapeUtils.unescapeJava(fields[1]);
	
	try{
		int a=Integer.valueOf(st1);
		try{
			int b=Integer.valueOf(st2);
			prepareCsvSet(a,b);
			}catch(Exception e)
			{
				prepareCsvSet(a,st2);
			}

		}catch(Exception e)
		{
			try{
				int b=Integer.valueOf(st2);
				prepareCsvSet(st1,b);
			}catch(Exception ex)
			{
				prepareCsvSet(st1,st2);
			}
		}
	
	


	

	}
	
	
	
	System.out.println("Total Mappings:"+characterMappings.size());
	
	System.out.println("starting read input Set file");
	logging("starting read input Set file","info");
	readCsv(inputfilePath);





}

public static void prepareCsvSet(String a,String b) {

characterMappings.put(a,b);

}


public static void prepareCsvSet(int a,int b) {

	String s1=new String();
	String s2=new String();
	
	s1=s1+(char)(a);
	s2=s2+(char)(b);
characterMappings.put(s1,s2);

}


public static void prepareCsvSet(char a,char b) {

	String s1=new String();
	String s2=new String();
	
	s1=s1+a;
	s2=s2+b;
characterMappings.put(s1,s2);

}



public static void prepareCsvSet(String a,int b) {

	String s1=new String();
	String s2=new String();
	
	s1=a;
	s2=s2+(char)(b);
characterMappings.put(s1,s2);

}

public static void prepareCsvSet(int a,String b) {

	String s1=new String();
	String s2=new String();
	
	s1=s1+(char)(a);
	s2=b;
characterMappings.put(s1,s2);

}

public static void readCsv(String filePath) {
	ArrayList<User> users = new ArrayList();
	
try {
File fileDir = new File(filePath);

BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileDir), "UTF8"));

String str=new String();

str = in.readLine();
String[] filedate = str.split("\\|");
System.out.println("date="+filedate[1]);

str = in.readLine();
String[] header = str.split("\\|");
System.out.println("header="+header[0]+header[1]);

Date date = new Date();
SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
String todaysdate=(String)(sdf.format(date));
sdf = new SimpleDateFormat("dd-MM-yyyy");
todaysdate=(String)(sdf.format(date));


File file = new File(inputfilePath);


String filemoddate=sdf.format(file.lastModified());
if(filemoddate.equals(todaysdate))
{
if(filedate[1].equals(todaysdate))
{
	
if(header[0].equals("region") && header[1].equals("country"))
{
if(file.length()>30)
{
	System.out.println("starting read input CSV file");
	logging("starting read input CSV file","info");
	
	
while ((str = in.readLine()) != null) {
	String[] fields = str.split("\\|");

User u=new User();

try
{
u.setRegion(fields[0]);

}catch(Exception e)
{
	u.setRegion("");
	
}


try
{
u.setCountry(fields[1]);

}catch(Exception e)
{
	
	u.setCountry("");
	
}

if(u.getRegion().equals("")&&u.getCountry().equals(""))
{
	
}
else
{
	users.add(u);
}






}

for(User u:users)
{
	
	System.out.println(u.getRegion()+" "+u.getCountry());
}

System.out.println("Total Records in input file "+users.size());
logging("Total Records to be processed:"+users.size(),"info");

if(users.size()>0)
{
	writeCsv(outputfilepath, users);
}
else
{
	System.out.println("No Records in input CSV file");
	logging("No Records in input CSV file","info");
	
}


}
else
{
	System.out.println("Input CSV File is Empty!!");
	logging("Input CSV File is Empty!!","severe");
	logging("Program Execution Ended","info");
}
}
else
{
	System.out.println("Input CSV Header is invalid !!!");
	logging("Input CSV Header is invalid !!!","info");
	logging("Program Execution Ended","info");
}
}
else
{
	System.out.println("Issue with The Bancs File Date!!");
	logging("Issue with The Bancs File Date!!","info");
}
}
else
{
	System.out.println("Input CSV file is not updated Please Contact SOS Team to run the Job !!!");
	logging("Input CSV file is not updated Please Contact SOS Team to run the Job !!!","severe");
}

in.close();
} 
//catch (UnsupportedEncodingException e) 
//{
//System.out.println(e.getMessage());
//logging(e.getMessage(),"severe");
//} 
//catch (IOException e) 
//{
//System.out.println(e.getMessage());
//logging(e.getMessage(),"severe");
//}
catch (Exception e)
{
System.out.println(e.getMessage());
logging(e.getMessage(),"severe");
}
}






public static void writeCsv(String filePath,ArrayList<User> users ) throws IOException {
//create demo Users
	System.out.println("Generating Output File !!");
	logging("Generating Output File !!","severe");
	
	
	BufferedWriter fileWriter = new BufferedWriter (new OutputStreamWriter (new FileOutputStream(filePath), "UTF8"));

try {

fileWriter.append("Region Name|Country\n");

for(User u : users)
{

fileWriter.append(replace(u.getRegion(),1));
fileWriter.append("|");
fileWriter.append(replace(u.getCountry(),2));
fileWriter.append("\n");
k++;
}

System.out.println("Output File Generated !!");
logging("Output File Generated !!","severe");
logging("Program Execution Ended","info");

} catch (Exception ex) {
ex.printStackTrace();
logging(ex.getMessage(),"severe");
} finally {
try {
fileWriter.flush();
fileWriter.close();
} catch (Exception e) {
e.printStackTrace();
logging(e.toString(),"severe");
}
}
}







public static String replace(String token,int col)
{

String newString = "";


for(int i = 0 ; i < token.length() ; ++i){
String tok=new String();
tok=tok+token.charAt(i);
//System.out.println("ascii valoe"+token.charAt(i)+" "+" ====="+(int)token.charAt(i) );

if( characterMappings.containsKey(tok) )
{

newString += characterMappings.get(tok);

System.out.println("For Line :"+k+" "+"Position:"+(i+1)+" "+"Column:"+(col)+token.charAt(i)+"->"+characterMappings.get(tok));

}

else if((int)(token.charAt(i)) >255 || (int)(token.charAt(i)) ==215 || (int)(token.charAt(i)) ==247 || (token.charAt(i) >=127 && token.charAt(i) <=191)) 
{

//System.out.println("New Character is introduced "+token.charAt(i)+" at Line "+k+"positon :"+(i+1)+"Please add it into the Set");
}

else if((token.charAt(i) >=32 && token.charAt(i) <=126)||(token.charAt(i) >=192 && token.charAt(i) <=255) )
{
newString += token.charAt(i);
}


}

return newString;

}



public static void logging(String message,String severity)
{
Logger logger = Logger.getLogger("MyLog");
FileHandler fh;

try {

// This block configure the logger with handler and formatter
fh = new FileHandler(logfilepath,true);
logger.addHandler(fh);
//logger.setLevel(Level.ALL);
SimpleFormatter formatter = new SimpleFormatter();
fh.setFormatter(formatter);

// the following statement is used to log any messages
if(severity.equals("info"))
{
logger.log(Level.INFO, message); 
}
else if(severity.equals("warning"))
{
logger.log(Level.WARNING, message); 
}
else if(severity.equals("severe"))
{
logger.log(Level.SEVERE, message); 
}
fh.close();

} catch (SecurityException e) {
e.printStackTrace();

logging(e.getMessage(),"severe");
} catch (IOException e) {
e.printStackTrace();
logging(e.getMessage(),"severe");
}
}

}