package se.anosh.flukta.service;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.TreeSet;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
class StorageServiceImpl implements StorageService {

	private static final String UPLOAD_DIR = "upload";
	private final Path dir;
	private final Logger logger;


	public StorageServiceImpl() throws IOException {
		logger = LoggerFactory.getLogger(StorageServiceImpl.class);
		dir = Paths.get(UPLOAD_DIR);
		if (!Files.exists(dir)) {
			Files.createDirectory(dir);
		}
	}

	@Override
	public void store(MultipartFile file) throws IOException {
		String filename = file.getOriginalFilename();
		logger.info("original filename: {}", filename);
		Path fullPath = Paths.get(UPLOAD_DIR, filename);

		try (DataOutputStream dataOutputStream = new DataOutputStream(Files.newOutputStream(fullPath))) {
			dataOutputStream.write(file.getBytes());
		}
		if (!isImage(fullPath)) {
			logger.info("deleting file: {}", fullPath);
			Files.deleteIfExists(fullPath);
			throw new IIOException("File is not a valid image-file");
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

	private boolean isImage(Path file) {
		try {
			return ImageIO.read(file.toFile()) != null;
		} catch (IOException ex) {
			logger.error("Image type not recognised", ex);
			return false;
		}
	}

	@Override
	public byte[] getImage(String filename) throws IOException {
		if (filename.contains("..") || filename.isEmpty())
			throw new IllegalArgumentException("Illegal filename");
		Path path = Paths.get(UPLOAD_DIR, filename);
		byte[] contents = Files.readAllBytes(path);
		return contents;
	}



}
