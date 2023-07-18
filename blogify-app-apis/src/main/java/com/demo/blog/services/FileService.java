package com.demo.blog.services;

import java.io.FileNotFoundException;
import java.io.InputStream;
import org.springframework.web.multipart.MultipartFile;

/**
 * contain all method related to file service and their implementations are present in the FileServiceImpl
 * @author samundar singh rathore
 *
 */
public interface FileService {
	
	/**
	 * to upload the file/image in image folder
	 * @param path
	 * @param multipartFile
	 * @return name of the file
	 * @throws FileNotFoundException
	 */
	String uploadImage(String path,MultipartFile multipartFile) throws FileNotFoundException;
	
	
	/**
	 * fetch file and image from the folder image
	 * @param path
	 * @param fileName
	 * @return file/image
	 * @throws FileNotFoundException
	 */
	InputStream getFileResource(String path,String fileName) throws FileNotFoundException;

}
