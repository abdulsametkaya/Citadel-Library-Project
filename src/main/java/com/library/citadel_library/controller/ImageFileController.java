package com.library.citadel_library.controller;


import com.library.citadel_library.domain.ImageFile;
import com.library.citadel_library.service.ImageFileService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
@AllArgsConstructor
public class ImageFileController {
	
	private ImageFileService imageFileService;
	
	@PostMapping("/upload")
	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file ){
		String imageId= imageFileService.saveImage(file);

		return ResponseEntity.ok(imageId);
		
	}
	
	
	@GetMapping("/download/{id}")
	public ResponseEntity<byte []> getImageFile(@PathVariable String id){
		ImageFile imageFile= imageFileService.getImageById(id);
		
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename="+
		imageFile.getName()).body(imageFile.getData());
	}
	
	
	@GetMapping("/display/{id}")
	public ResponseEntity<byte []> displayFile(@PathVariable String id){
		ImageFile imageFile= imageFileService.getImageById(id);
	
		HttpHeaders header=new HttpHeaders();
		header.setContentType(MediaType.IMAGE_PNG);
		
		return new ResponseEntity<>(imageFile.getData(),header,HttpStatus.OK);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<String> updateFile(@PathVariable String id, @RequestParam("file") MultipartFile file){
		String imageId= imageFileService.updateImage(id,file);

		return ResponseEntity.ok(imageId);
	}

}
