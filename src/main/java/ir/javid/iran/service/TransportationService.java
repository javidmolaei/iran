package ir.javid.iran.service;

import ir.javid.iran.model.Transportation;
import ir.javid.iran.repository.TransportationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author: Mr.javidmolaei
 */

@Service
public class TransportationService {

    private final TransportationRepository transportationRepository;


    @Autowired
    public TransportationService(TransportationRepository transportationRepository) {
        this.transportationRepository = transportationRepository;
    }

    public void saveTransportation(Transportation transportation){
        this.transportationRepository.save(transportation);
    }
}
