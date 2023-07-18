package com.demo.blog.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.demo.blog.exceptions.ResourceNotFoundException;
import com.demo.blog.exceptions.ServiceInternalException;
import com.demo.blog.services.FileService;

/**
 * implementation of file related services
 * 
 * @author samundar Singh rathore
 *
 */
@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws FileNotFoundException {

		// if file is empty
		if (file.isEmpty()) {
			throw new ResourceNotFoundException(901, "request must content the image");
		}

		// get content type
		if (((file.getContentType()) == "image/jpeg")) {
			throw new ResourceNotFoundException(901, "only jpeg format is allowed");
		}

		// get the file name
		String incomingFileName = file.getOriginalFilename();

		// random name generate for file
		String randomID = UUID.randomUUID().toString();

		// set random name
		String fileName = randomID.concat(incomingFileName).substring(incomingFileName.lastIndexOf("."));

		// file path
		String filePath = path + File.separator + fileName;

		// create folder if there is no folder
		File f = new File(path);
		if (!f.exists()) {
			f.mkdir();
		}

		// file copy in our app folder
		try {
			Files.copy(file.getInputStream(), Paths.get(filePath));
		} catch (IOException e) {
			throw new ServiceInternalException(902, "internal error during the saving the image");
		}
		return fileName;

	}

	@Override
	public InputStream getFileResource(String path, String fileName) throws FileNotFoundException {
		try {
			String fullpath = path + File.separator + fileName;
			InputStream is = new FileInputStream(fullpath);
			return is;
		} catch (Exception e) {
			throw new ResourceNotFoundException(903, "something went wrong" + e.getMessage());
		}
	}

}
