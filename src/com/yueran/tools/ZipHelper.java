package com.yueran.tools;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

public class ZipHelper {
	
	public static OutputStream unZipIt(String zipFile)
	{
		byte[] buffer = new byte[1024];
		OutputStream out = null;
		ZipInputStream zis = null;
		try
		{
			zis = new ZipInputStream(new FileInputStream(zipFile));
			ZipEntry ze = zis.getNextEntry();
			while(ze!=null)
			{
				String fileName = ze.getName();
				if(fileName.startsWith("META-INF")&&(fileName.endsWith(".RSA")||fileName.endsWith(".DSA")||fileName.endsWith(".EC")))
				{
					out = new ByteOutputStream();
					int len = 0;
	    			        while((len = zis.read(buffer))>0)
	    			        {
	    				    out.write(buffer, 0, len);
					}
	    			        break;
				}
    			        ze = zis.getNextEntry();
			}
		}
		catch(Exception e)
		{
			out = null;
		}
		finally
		{
			if(zis!=null)
			{
				try
				{
				    zis.close();
				}
				catch(Exception e)
				{
				    
				}
			}
		}
		return out;
	}
	
	@SuppressWarnings("deprecation")
	public static InputStream parse(OutputStream out)
	{
		InputStream in = null;
		try
		{
			byte[] data = ((ByteOutputStream) out).toByteArray();
			in = new ByteArrayInputStream(data);
		}
		catch(Exception e)
		{
			in = null;
		}
		finally
		{
			if(out!=null)
			{
				try 
				{
					out.close();
				}
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		}
		
		return in;
	}
	
	public static InputStream parseAlgName(String zipFile)
	{
		OutputStream out = unZipIt(zipFile);
		return parse(out);
	}
}
