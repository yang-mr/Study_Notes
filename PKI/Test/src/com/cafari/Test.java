package com.cafari;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;


public class Test {
	private static final Logger logger = Logger.getLogger(Test.class.getName());
	private static final String LINE_BREAKER = System.getProperty("line.separator");

	private static final String CERTIFACATE_FILE = "keystore.p12";
	private static final String TRUST_CERTIFACATE_FILE = "trust_store";
	private static final String CERTIFACATE_PASS = "Cafari2017!";
	private static final String CERTIFACATE_ALIAS = "test.clientssl.cafari.com";
	private static final String TARGET_URL = "https://38fdc237562267a08ac56978ea1ce303.cafe.cphotobox.com:58181";
	
	/*static {
        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
                new javax.net.ssl.HostnameVerifier() {

                    public boolean verify(String hostname,
                                          javax.net.ssl.SSLSession sslSession) {
                        //if (hostname.equals("38fdc237562267a08ac56978ea1ce303.cafe.cphotobox.com")) {
                    	if (hostname.equals("38fdc237562267a08ac56978ea1ce303.cafe.cphotobox.com")) {
                            return true;
                        }
                        return false;
                    }
                }
        );
    }*/
	
	public static void main(String[] args) {
		String targetURL = TARGET_URL;
		   URL url;
		   HttpsURLConnection connection = null;
		   BufferedReader bufferedReader = null;
		   InputStream is = null;

		   try {
		       //Create connection
		       url = new URL(targetURL);
		       //Uncomment this in case server demands some unsafe operations
		       //System.setProperty("sun.security.ssl.allowUnsafeRenegotiation", "true");
		       connection = (HttpsURLConnection) url.openConnection();

		       //connection.setRequestMethod("POST");
		       connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		       connection.setRequestProperty("Content-Language", "en-US");

		       SSLSocketFactory sslSocketFactory = getFactory(new File(CERTIFACATE_FILE), new File(TRUST_CERTIFACATE_FILE),CERTIFACATE_PASS);
		       connection.setSSLSocketFactory(sslSocketFactory);

		       //Process response
		       is = connection.getInputStream();

		       bufferedReader = new BufferedReader(new InputStreamReader(is));
		       String line;
		       StringBuffer lines = new StringBuffer();
		       while ((line = bufferedReader.readLine()) != null) {
		           lines.append(line).append(LINE_BREAKER);
		       }
		       System.out.println(lines.toString());
		       //logger.info("response from " + targetURL + ":" + LINE_BREAKER + lines);

		   } catch (Exception e) {
			   e.printStackTrace();
		   }
		
	}
	
	public static SSLSocketFactory getFactory(File keyStoreFile, File trustStoreFile, String password){
        SSLSocketFactory sslSocketFactory = null;
        try {
        	//KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
        	String def_alg = KeyManagerFactory.getDefaultAlgorithm();
        	System.out.println(def_alg);
        	KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            KeyStore keyStore = KeyStore.getInstance("PKCS12");

            InputStream keyInput = new FileInputStream(keyStoreFile);
            keyStore.load(keyInput, password.toCharArray());
            keyInput.close();

            keyManagerFactory.init(keyStore, password.toCharArray());

            SSLContext context = SSLContext.getInstance("TLS");
            //context.init(keyManagerFactory.getKeyManagers(), null, new SecureRandom());
            
            KeyStore trustStore = KeyStore.getInstance("PKCS12");
            InputStream trustInput = new FileInputStream(trustStoreFile);
            trustStore.load(trustInput, password.toCharArray());
            trustInput.close();
            
            
            String def_trust_alg = TrustManagerFactory.getDefaultAlgorithm();
            System.out.println(def_trust_alg);
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(def_trust_alg);
            trustManagerFactory.init(trustStore);
            
            context.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);
            
            sslSocketFactory = context.getSocketFactory();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        return sslSocketFactory;
    }
	
	
}
