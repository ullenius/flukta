package se.anosh.flukta.service;

import java.io.IOException;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

	void store(MultipartFile file) throws IOException;
	Set<String> listFiles() throws IOException;
	byte[] getImage(String filename) throws IOException;
	
	
}
