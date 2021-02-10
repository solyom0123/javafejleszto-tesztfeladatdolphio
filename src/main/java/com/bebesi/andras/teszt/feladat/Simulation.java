package com.bebesi.andras.teszt.feladat;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Bebesi András
 * @apiNote It stores incoming and out coming value and length data.
 */

@Data
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Simulation {
    private ArrayList<Block> blocks = new ArrayList<>();
    private int generation;
    private Rule rule;
    private String fileName;

    public Simulation(String rule,int generation){
        this.rule =  new Rule(rule);
        this.generation = generation;
        this.blocks = new ArrayList<>();
    }

    public Simulation(Simulation simulation){
        this.setGeneration(simulation.getGeneration());
        this.setRule(simulation.getRule());
        this.blocks = new ArrayList<>();
        this.fileName = simulation.getFileName();
    }

}
