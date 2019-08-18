package ru.stacy.capstone.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.stacy.capstone.dto.PlaceDTO;
import ru.stacy.capstone.model.Place;
import ru.stacy.capstone.repository.PlaceRepository;

import java.util.List;

@Service
public class PlaceService {

    private ModelMapper modelMapper;

    private PlaceRepository placeRepository;

    @Autowired
    public PlaceService(ModelMapper modelMapper, PlaceRepository placeRepository) {
        this.modelMapper = modelMapper;
        this.placeRepository = placeRepository;
    }

    public Place addPlace(PlaceDTO placeDTO) {
        Place place = modelMapper.map(placeDTO, Place.class);
        placeRepository.save(place);
        return place;
    }

    public void deletePlace(Long id) {
        placeRepository.delete(id);
    }

    public Place updatePlace(PlaceDTO placeDTO, Long id) {
        Place place = modelMapper.map(placeDTO, Place.class);
        place.setId(id);
        placeRepository.save(place);
        return place;
    }

    public List<Place> findAllPlaces() {
        return placeRepository.findAll();
    }

    public Place findPlace(Long id) {
        return placeRepository.findOne(id);
    }
}
