package com.library.citadel_library.service;

import com.library.citadel_library.domain.ImageFile;
import com.library.citadel_library.exception.ImageFileException;
import com.library.citadel_library.exception.ResourceNotFoundException;
import com.library.citadel_library.exception.message.ErrorMessage;
import com.library.citadel_library.repository.ImageFileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Service
@AllArgsConstructor
public class ImageFileService {

	private ImageFileRepository imageFileRepository;

	public String saveImage(MultipartFile file) {
		     String fileName= StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
		     ImageFile imageFile;
			try {
				imageFile = new ImageFile(fileName, file.getContentType(), file.getBytes());
			} catch (IOException e) {
				 throw new ImageFileException(ErrorMessage.IMAGE_NOT_FOUND_MESSAGE);
			}
		     imageFileRepository.save(imageFile);
		     return imageFile.getId();
	}

	public ImageFile getImageById(String id) {
		ImageFile imageFile=  imageFileRepository.findById(id).
				orElseThrow(()->new ResourceNotFoundException(String.format(ErrorMessage.IMAGE_NOT_FOUND_MESSAGE, id)));
		return imageFile;
	}

	public String updateImage(String id, MultipartFile file) {

		ImageFile foundImage = imageFileRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException
				(String.format(ErrorMessage.IMAGE_NOT_FOUND_MESSAGE, id)));

		String fileName= StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
		ImageFile image;
		try {
			image = new ImageFile(fileName, file.getContentType(), file.getBytes());
		} catch (IOException e) {
			throw new ImageFileException(ErrorMessage.IMAGE_NOT_FOUND_MESSAGE);
		}

		foundImage.setData(image.getData());
		foundImage.setName(image.getName());
		foundImage.setType(image.getType());

		imageFileRepository.save(foundImage);

		return foundImage.getId();
	}


}
