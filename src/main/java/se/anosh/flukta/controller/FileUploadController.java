package se.anosh.flukta.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import se.anosh.flukta.service.StorageService;

@Controller
public class FileUploadController {
	
	private final StorageService service;
	
	@Autowired
	public FileUploadController(StorageService service) {
		this.service = service;
	}
	
	
	@PostMapping("/upload")
	public String upload(@RequestParam("file") MultipartFile file) {
		
		try {
			service.store(file);
		} catch (IOException e) {
			e.printStackTrace();
			return "Something went terribly wrong";
		}
		return "success";
		
	}

}
