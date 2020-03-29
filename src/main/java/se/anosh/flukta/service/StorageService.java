package se.anosh.flukta.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

	void store(MultipartFile file) throws IOException;

}
