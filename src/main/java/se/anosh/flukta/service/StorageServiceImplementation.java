package se.anosh.flukta.service;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageServiceImplementation implements StorageService {

	private static final String UPLOAD_DIR = "upload";
	private final Path dir;
	private final Logger logger;


	public StorageServiceImplementation() throws IOException {
		
		logger = LoggerFactory.getLogger(StorageServiceImplementation.class);
		
		dir = Paths.get(UPLOAD_DIR);

		if (!Files.exists(dir)) {
			Files.createDirectory(dir);
		}

	}

	@Override
	public void store(MultipartFile file) throws IOException {

		String filename = file.getOriginalFilename();
		logger.info("original filename: {}", filename);

		try (DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(UPLOAD_DIR + "/" + filename))) {
			dataOutputStream.write(file.getBytes());
		}
	}

	@Override
	public Set<String> listFiles() throws IOException {

		Set<String> directoryListing = new TreeSet<>();

		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
			for (Path p : stream) {
				logger.info("file: {}", p);
				directoryListing.add(p.toString());
			}
			return directoryListing;
		}
	}



}
