package com.library.citadel_library.controller;

import com.library.citadel_library.domain.Publisher;
import com.library.citadel_library.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/publishers")
public class PublisherController {

    @Autowired
    private PublisherService publisherService;

    /* Get All Publisher with Pageable */
    @GetMapping()
    public ResponseEntity<Page<Publisher>> getAllPublishers(@RequestParam(required = false,value = "page", defaultValue = "0") int page,
                                                            @RequestParam(required = false,value = "size", defaultValue = "20") int size,
                                                            @RequestParam(required = false,value = "sort", defaultValue = "name") String prop,
                                                            @RequestParam(required = false,value = "direction", defaultValue = "ASC") Direction direction){

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction,prop));
        Page<Publisher> publisherPage = publisherService.getAllPublishersWithPage(pageable);
        return new ResponseEntity<>(publisherPage, HttpStatus.OK);
    }

    @GetMapping("/allpublishers")
    public ResponseEntity<List<Publisher>> getAll(){

        List<Publisher> publishers = publisherService.findAll();

        return new ResponseEntity<>(publishers,HttpStatus.OK);
    }

    /* Get One Publisher with Publisher Id */
    @GetMapping("/{id}")
    public ResponseEntity<Publisher> getPublisherById(@PathVariable Long id){
        Publisher publisher = publisherService.getPublisherById(id);
        return new ResponseEntity<>(publisher, HttpStatus.OK);
    }

    /* Create new Publisher */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<Publisher> createPublisher(@Valid @RequestBody Publisher publisher){
        publisherService.createPublisher(publisher);
        return new ResponseEntity<>(publisher, HttpStatus.CREATED);
    }

    /* Updates Current Publisher */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Publisher> updatePublisherById(@PathVariable Long id, @Valid @RequestBody Publisher newPublisher){
        Publisher publisher = publisherService.updatePublisherById(id, newPublisher);
        return ResponseEntity.ok(publisher);

    }

    /* Delete Current Publisher */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Publisher> deletePublisherById(@PathVariable Long id){
        Publisher publisher = publisherService.deletePublisherById(id);
        return ResponseEntity.ok(publisher);
    }
}
