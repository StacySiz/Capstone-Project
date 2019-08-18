package ru.stacy.capstone.controller.admin;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.stacy.capstone.dto.PlaceDTO;
import ru.stacy.capstone.model.Place;
import ru.stacy.capstone.service.PlaceService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/places/")
public class PlaceController {

    private PlaceService placeService;

    private ModelMapper modelMapper;

    @Autowired
    public PlaceController(PlaceService placeService, ModelMapper modelMapper) {
        this.placeService = placeService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/add")
    public ResponseEntity addPlace(@RequestBody PlaceDTO placeDTO) {
        Place place = placeService.addPlace(placeDTO);
        return ResponseEntity.ok(modelMapper.map(place, PlaceDTO.class));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deletePlace(@PathVariable Long id) {
        placeService.deletePlace(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/update/{id}")
    public ResponseEntity updatePlace(@RequestBody PlaceDTO placeDTO, @PathVariable Long id) {
        Place place = placeService.updatePlace(placeDTO, id);
        return ResponseEntity.ok(modelMapper.map(place, PlaceDTO.class));
    }


    @GetMapping("/{id}")
    public ResponseEntity getPlace(@PathVariable Long id) {
        if (placeService.findPlace(id) != null) {
            Place place = placeService.findPlace(id);
            return ResponseEntity.ok(modelMapper.map(place, PlaceDTO.class));
        } else return ResponseEntity.notFound().build();
    }

    @GetMapping("/all")
    public ResponseEntity getAll() {
        List<Place> allPlaces = placeService.findAllPlaces();
        List<PlaceDTO> placeDTOList = allPlaces.stream()
                .map(place -> modelMapper.map(place, PlaceDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(placeDTOList);
    }
}
