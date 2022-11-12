package com.opensource.weathercloset.record.controller;

import com.opensource.weathercloset.record.dto.RecordRequestDTO;
import com.opensource.weathercloset.record.dto.RecordResponseDTO;
import com.opensource.weathercloset.record.dto.RecordUpdateRequestDTO;
import com.opensource.weathercloset.record.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(value = "/post")
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;


    @PostMapping
    @ResponseStatus(OK)
    public ResponseEntity<RecordResponseDTO> postRecord(@RequestBody RecordRequestDTO requestDTO) {
        String imageUrl = requestDTO.getImageUrl();
        int temperature = requestDTO.getTemperature();
        int stars = requestDTO.getStars();
        String comment = requestDTO.getComment();
        boolean heart = requestDTO.isHeart();

        return ResponseEntity.ok(
                recordService.addRecord(imageUrl, temperature, stars, comment, heart)
        );
    }

    @PutMapping("/{recordId}")
    @ResponseStatus(NO_CONTENT)
    public void updateRecord(@PathVariable Long recordId, @RequestBody RecordUpdateRequestDTO requestDTO) {
        int stars = requestDTO.getStars();
        String comment = requestDTO.getComment();
        boolean heart = requestDTO.isHeart();

        recordService.updateRecord(recordId, stars, comment, heart);
    }

    @DeleteMapping("/{recordId}")
    @ResponseStatus(NO_CONTENT)
    public void deleteRecord(@PathVariable Long recordId) {
        recordService.deleteRecord(recordId);
    }

}
