package ghibli.ghibliapi.controller;

import ghibli.ghibliapi.dto.TextGenarationRequestDTO;
import ghibli.ghibliapi.service.GhibliArtService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"})
public class GenarationController {

    private final GhibliArtService ghibliArtService;

    public GenarationController(GhibliArtService ghibliArtService) {
        this.ghibliArtService = ghibliArtService;
    }

    @PostMapping(value = "/genarate", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> genarateGhibliArt(@RequestParam("image") MultipartFile image,
                                                    @RequestParam("promt") String promt) {
        try {
            byte[] imageBytes = ghibliArtService.createghibliart(image, promt);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(imageBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping(value = "/genarate-from-text", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> genarateGhibliArtFromText(@RequestBody TextGenarationRequestDTO requestDTO) {
        try {
            byte[] imageBytes = ghibliArtService.createGhibliArtFromText(
                    requestDTO.getPromt(), requestDTO.getStyle()
            );
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(imageBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
