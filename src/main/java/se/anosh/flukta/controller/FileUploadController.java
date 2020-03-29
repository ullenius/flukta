package se.anosh.flukta.controller;

import java.io.IOException;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	private final Logger logger;

	@Autowired
	public FileUploadController(StorageService service) {
		this.service = service;
		logger = LoggerFactory.getLogger(FileUploadController.class);
	}

	@GetMapping("/")
	public ModelAndView uploadForm() {
		Set<String> listOfFiles;
		try {
			listOfFiles = service.listFiles();
			return new ModelAndView("upload", "files", listOfFiles);
		} catch (IOException ex) {
			logger.error("uploadForm (GET)", ex);
			return new ModelAndView("upload", "message", "Something went terribly wrong! :(");
		}
	}

	@PostMapping("/upload")
	public ModelAndView upload(@RequestParam("file") MultipartFile file) {

		try {
			service.store(file);
		} catch (IOException ex) {
			logger.error("upload (POST) - ", ex);
			return new ModelAndView("upload", "message", "Something went terribly wrong! :(");
		}
		return new ModelAndView("redirect:/");

	}

}
