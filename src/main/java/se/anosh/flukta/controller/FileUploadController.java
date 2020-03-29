package se.anosh.flukta.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import se.anosh.flukta.service.StorageService;

@Controller
public class FileUploadController {
	
	private final StorageService service;
	
	@Autowired
	public FileUploadController(StorageService service) {
		this.service = service;
	}
	
	@GetMapping("/")
	public ModelAndView uploadForm() {
		return new ModelAndView("upload");
	}
	
	@PostMapping("/upload")
	public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
		
		try {
			service.store(file);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Something went terribly wrong", HttpStatus.I_AM_A_TEAPOT);
		}
		return new ResponseEntity<String>("Success!", HttpStatus.ACCEPTED);
		
	}

}
