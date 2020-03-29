package se.anosh.flukta.service;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageServiceImplementation implements StorageService {
	
	private static final String UPLOAD_DIR = "upload";
	
	
	public StorageServiceImplementation() throws IOException {
		
		Path dir = Paths.get(UPLOAD_DIR);
		
		if (!Files.exists(dir)) {
			Files.createDirectory(dir);
		}
		
	}

	@Override
	public void store(MultipartFile file) throws IOException {
		
		String filename = file.getOriginalFilename();
		System.out.println("original filename: " + filename);
		
		try (DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(UPLOAD_DIR + "/" + filename))) {
			dataOutputStream.write(file.getBytes());
		}
	
	}
	
	

}
