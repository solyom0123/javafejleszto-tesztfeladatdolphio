package com.bebesi.andras.teszt.feladat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Bebesi András.
 */
@RestController
public class GenerationController {

    @Autowired
    private GenerationService generationService;
    @Autowired
    private SimulationService simulationService;

    @PostMapping("/next")
    Simulation calculateNextGeneration(@RequestBody Simulation simulation ) {
        generationService.setSimulation(simulation);
        return generationService.calculateNextGeneration();
    }

    @PostMapping("/prev")
    Simulation calculatePreviousGeneration(@RequestBody Simulation simulation ) {
        simulationService.setFileName(simulation.getFileName());
        generationService.setSimulation(simulationService.readLifFileToInitSimulation());
        return generationService.calculatePreviousGeneration(simulation.getGeneration());

    }

    @GetMapping("/")
    String home() {
        return "BEBESI ANDRÁS TESZT FELADAT MEGOLDÁS BACKEND";
    }
}
