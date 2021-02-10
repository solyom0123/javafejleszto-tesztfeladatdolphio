package com.bebesi.andras.teszt.feladat;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Bebesi András.
 */
@Data
@Service
public class SimulationService {

    private String uploadDir;
    private String fileName;

    public SimulationService(@Value( "${file.upload-dir}" ) String uploadDir) {
    }

    public Simulation readLifFileToInitSimulation() {
        Simulation simulation = new Simulation("#N",0);
        try {
            Path file = Paths.get(fileName)
                    .toAbsolutePath().normalize();
            BufferedReader br = new BufferedReader(new FileReader(file.toFile()));
            String row = br.readLine();
            Block block = null;
            int blockRowNumber =0;
            while ((row = br.readLine()) != null) {
                if(row.startsWith("#N")){
                    simulation = new Simulation(row,0);
                }else if(row.startsWith("#R")){
                    simulation = new Simulation(row,0);
                }else if(row.startsWith("#P")){
                    blockRowNumber=0;
                    String[] coordinate = row.replace("#P ","").split(" ");
                    block = new Block(Integer.parseInt(coordinate[0]),Integer.parseInt(coordinate[1]));
                    simulation.getBlocks().add(block);
                }else if(row.startsWith(".")||row.startsWith("*")){
                    for (int i =0 ;i<row.length();i++){
                        char c= row.toCharArray()[i];
                        Cell cell = new Cell(setStateOfCell(c), calcCoordinateOfCell(i, block.getStartPoint().x), calcCoordinateOfCell(blockRowNumber, block.getStartPoint().y));
                        block.getCells().add(cell);
                    }
                    blockRowNumber++;
                }
            }
            simulation.setFileName(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return simulation;
    }

    public String readHomePage() {
        String homepage ="";
        try {
            Path file = Paths.get("index.html")
                    .toAbsolutePath().normalize();
            BufferedReader br = new BufferedReader(new FileReader(file.toFile()));
            String row ="";
            while ((row = br.readLine()) != null) {
             homepage+=row;
            }
           } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return homepage;
    }

    private int calcCoordinateOfCell(int i, int x) {
        return x + i;
    }

    private boolean setStateOfCell(char c) {
        return c == '.';
    }
}
