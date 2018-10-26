package com;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.*;

public class Esc {
public static void main(String[] args) throws IOException {
	
	
      String inputsetfilePath = "D:/test/inputset.csv";
	
	File fileDir = new File(inputsetfilePath);
	BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileDir), "UTF8"));

	String str=new String();
	while ((str = in.readLine()) != null) {
	String[] fields = str.split("\\|");
	System.out.println(fields[0]+ "-->"+ fields[1]);
	String st = new String();
	st=StringEscapeUtils.unescapeJava(fields[0]);
	System.out.println(st);
	 st = StringEscapeUtils.unescapeJava(fields[1]);
	System.out.println(st);
	}
}
}
