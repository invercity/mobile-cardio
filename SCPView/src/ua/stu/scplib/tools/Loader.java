package ua.stu.scplib.tools;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Loader {
	
	public String load(String name,String root) {
		String rs = "";
		try {
			URL url = new URL(name);
			URLConnection connection = url.openConnection();
			connection.connect();
			BufferedInputStream in = new BufferedInputStream(connection.getInputStream());
			String file = url.getFile();
	        int pos = file.lastIndexOf("/");
	        rs = file.substring(pos + 1);
	        System.out.println("load test filename" + rs);
            java.io.FileOutputStream fos = new java.io.FileOutputStream(root + rs);
            java.io.BufferedOutputStream out = new BufferedOutputStream(fos,1024);
            byte[] data = new byte[1024];
            int x=0;
            while((x=in.read(data,0,1024))>=0){
                out.write(data,0,x);               
            }
            fos.flush();
            out.flush();
            fos.close();
            out.close();
            in.close();
	     }
	     catch (MalformedURLException e){}
	     catch (IOException e){}
		return rs;
	}

}
