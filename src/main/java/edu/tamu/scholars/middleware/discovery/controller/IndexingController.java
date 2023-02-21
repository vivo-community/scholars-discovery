package edu.tamu.scholars.middleware.discovery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.tamu.scholars.middleware.discovery.service.IndexService;

@RestController
@RequestMapping("/index")
public class IndexingController {

    @Autowired
    private IndexService indexService;

    @GetMapping("/isIndexing")
    public ResponseEntity<Boolean> isIndexing() {
        Boolean isIndexing = indexService.isIndexing();

        return isIndexing
            ? ResponseEntity.status(HttpStatus.TOO_EARLY)
                .body(isIndexing)
            : ResponseEntity.ok(isIndexing);
    }

}
