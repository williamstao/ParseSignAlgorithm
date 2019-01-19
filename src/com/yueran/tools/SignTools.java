package com.yueran.tools;
import java.io.InputStream;

import sun.security.pkcs.PKCS7;
import sun.security.pkcs.SignerInfo;
import sun.security.x509.AlgorithmId;

public class SignTools {
	public static String parseAlgName(InputStream is) throws Exception{
		String algname = null;
		PKCS7 pkcs7 = new PKCS7(is);
		is.close();
		
		SignerInfo[] signInfo = pkcs7.getSignerInfos();
		for (int i = 0; i < signInfo.length; i++){
			SignerInfo item = signInfo[i];
			algname = AlgorithmId.makeSigAlg(item.getDigestAlgorithmId().getName(), item.getDigestEncryptionAlgorithmId().getName());
		}
		return algname;
	}
}
