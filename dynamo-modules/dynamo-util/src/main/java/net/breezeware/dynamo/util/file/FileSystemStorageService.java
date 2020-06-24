package net.breezeware.dynamo.util.file;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileSystemStorageService {

	Logger logger = LoggerFactory.getLogger(FileSystemStorageService.class);

	@Value("${dynamo.fileUploadLocation}")
	private Path rootLocation;

	public String store(MultipartFile file, List<String> acceptedFileTypes, String originalFilename,
			String uniqueFilename) throws StorageException {
		logger.info(
				"Entering store(). File's type = {}. # of accpeted file types = {}. Original filename = {}. Unique filename = {}",
				file.getContentType(), acceptedFileTypes.size(), originalFilename, uniqueFilename);

		String filePath = "";

		try {
			if (file.isEmpty()) {
				logger.info("MultiPartfile is empty!");
				throw new StorageException("Failed to store empty file " + uniqueFilename);
			}

			// check for accepted file type
			boolean accepted = false;
			if (file != null && file.getContentType() != null && acceptedFileTypes != null
					&& acceptedFileTypes.size() > 0) {
				String fileExt = "";

				for (String acceptedFT : acceptedFileTypes) {
					logger.debug("File type to accept = {}", acceptedFT);
					String ft = file.getContentType().toLowerCase();

					if (file.getOriginalFilename() != null && file.getOriginalFilename().lastIndexOf(".") > 0) {
						fileExt = originalFilename.substring(originalFilename.lastIndexOf(".") + 1,
								originalFilename.length());
						logger.debug("File extension = {}", fileExt);
					}

					if (ft.indexOf(acceptedFT.toLowerCase()) > 0 && (fileExt.length() == 0 || (fileExt.length() > 0
							&& fileExt.toLowerCase().equalsIgnoreCase(acceptedFT.toLowerCase())))) {
						accepted = true;
						break;
					}
				}

				if (accepted == false) {
					logger.info("File type is not in the accpeted file list. Not storing the file.");
					String message = "";
					if (fileExt.length() > 0) {
						message = "File '" + originalFilename + "' with file type '" + file.getContentType()
								+ "' and extension '" + fileExt + "' is not supported.";
					} else {
						message = "File '" + originalFilename + "' with file type '" + file.getContentType()
								+ "' is not supported.";
					}

					throw new StorageException(message);
				}
			}

			if (uniqueFilename.contains("..")) {
				// This is a security check
				throw new StorageException(
						"Cannot store file with relative path outside current directory " + uniqueFilename);
			}
			Files.copy(file.getInputStream(), this.rootLocation.resolve(uniqueFilename),
					StandardCopyOption.REPLACE_EXISTING);

			filePath = this.rootLocation.resolve(uniqueFilename).toAbsolutePath().toString();
			logger.debug("Path where file is stored = {}", filePath);

		} catch (IOException e) {
			logger.info("IOException occured!!!");
			throw new StorageException("Failed to store file " + uniqueFilename, e);
		}
		logger.info("Leaving store()");

		return filePath;
	}

	public Stream<Path> loadAll() throws StorageException {
		try {
			return Files.walk(this.rootLocation, 1).filter(path -> !path.equals(this.rootLocation))
					.map(path -> this.rootLocation.relativize(path));
		} catch (IOException e) {
			throw new StorageException("Failed to read stored files", e);
		}

	}

	public Path load(String filename) {
		return rootLocation.resolve(filename);
	}

	public Resource loadAsResource(String filename) throws StorageException {
		try {
			Path file = load(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new StorageFileNotFoundException("Could not read file: " + filename);

			}
		} catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("Could not read file: " + filename, e);
		}
	}

	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

	public void init() throws StorageException {
		try {
			Files.createDirectories(rootLocation);
		} catch (IOException e) {
			throw new StorageException("Could not initialize storage", e);
		}
	}
}