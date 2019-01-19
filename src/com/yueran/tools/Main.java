package com.yueran.tools;

import java.io.InputStream;

public class Main {
	static final String version = "1.0.4(2016/12/12)";
	public static void main(String args[]){
		if (args.length < 1){
			System.out.println("Error:invalid paramters");
			System.exit(-1);
		}
		
		String cmd = args[0];
		boolean success = false;
		InputStream is = null;
		/**
		 * --algname CERT.RSA
		 */ 
		try
		{
			if (cmd.equals("--algname"))
			{
				if (args.length !=2 )
				{
					System.out.println("Error:invalid paramters");
					System.exit(-1);
				}
				
				String zip = args[1];
				if(!zip.endsWith(".apk"))
				{
					System.out.println("Error:invalid apk files");
					System.exit(-1);
				}
				
				is= ZipHelper.parseAlgName(zip);
				if(is == null)
				{
					System.out.println("Error:Get FileStream fail");
					System.exit(-1);
				}
				
				String algname = SignTools.parseAlgName(is);
				if (algname != null)
				{
					success = true;
					System.out.println("ALGNAME:" + algname);
				}
			}
			else
			{
				System.exit(-1);
			}
			
			if (success)
			{
				System.exit(0);
			}
			else
			{
				System.out.println("Failed");
				System.exit(-1);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(-1);
		}
		finally
		{
			if(is != null)
			{
				try 
				{
					is.close();
				}
				catch(Exception e) 
				{
					e.printStackTrace();
				}
			}
		}
	}
}
