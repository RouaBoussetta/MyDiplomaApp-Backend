package tn.uae.nawanera.spring.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public final class FileUploadUtil {


	public static void saveFile(
	           MultipartFile multipartFile) throws IOException {
	     
		 
		 
			File convertFile = new File(
					"C:\\wamp64\\www\\images\\" + multipartFile.getOriginalFilename());
			FileOutputStream fout = new FileOutputStream(convertFile);

			try {
			 
 					fout.write(multipartFile.getBytes());
				 
				 
			 
		 	
			} catch (IOException e) {
				  throw new IOException(String.format("Can't process multipart data item, %s", e));
			} finally {
				  fout.close();
		    }
			
			 
	   }
}
