package com.bebesi.andras.teszt.feladat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Bebesi András.
 */
@RestController
public class SimulationController {

    @Autowired
    private SimulationService simulationService;

    @GetMapping("/create/{fileName}")
    Simulation makeNewSimulation(@PathVariable String fileName) {
        simulationService.setFileName(fileName);
        return simulationService.readLifFileToInitSimulation();
    }
}
