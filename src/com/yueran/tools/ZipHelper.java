package com.yueran.tools;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

public class ZipHelper {
	
	@SuppressWarnings("deprecation")
	public static byte[] unZipIt(String zipFile)
	{
		byte[] buffer = new byte[1024];
		OutputStream out = null;
		byte[] data = null;
		try
		{
			ZipInputStream zis = null;
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
			zis.close();
			data = ((ByteOutputStream) out).toByteArray();
		}
		catch(Exception e)
		{
			data = null;
		}
		finally
		{
			if(out != null)
			{
				try
				{
					out.close();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		return data;
	}
	
	public static byte[] parseAlgName(String zipFile)
	{
		return unZipIt(zipFile);
	}
}
