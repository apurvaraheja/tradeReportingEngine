package main.java.com.tradeReportingEngine.controller;

import main.java.com.tradeReportingEngine.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import main.java.com.tradeReportingEngine.service.EventService;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {
    @Autowired
    private EventService eventService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadXmlFile(@RequestParam("file") MultipartFile file) {
        try {
            eventService.processXmlFile(file.getInputStream());
            return ResponseEntity.ok("File uploaded and processed successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing the file.");
        }
    }

    @GetMapping("/filtered-events")
    public ResponseEntity<List<Event>> getFilteredEvents() {
        List<Event> filteredEvents = eventService.getFilteredEvents();
        return ResponseEntity.ok(filteredEvents);
    }
}
