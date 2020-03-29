package se.anosh.flukta.service;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageServiceImplementation implements StorageService {

	@Override
	public void store(MultipartFile file) throws IOException {
		
		String filename = file.getOriginalFilename();
		System.out.println("original filename: " + filename);
		
		DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(filename));
		dataOutputStream.write(file.getBytes());
		dataOutputStream.close();
	}
	
	

}
