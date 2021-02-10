package com.bebesi.andras.teszt.feladat;

import lombok.Data;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Bebesi András.
 */
@Service
@Data
public class GenerationService {

    private Simulation simulation;
    private Simulation newGenerationSimulation;
    private HashMap<String, Point> directions = new HashMap<>();

    public GenerationService(){
        initDirectionHashMap();
     }

    public void setSimulation(Simulation simulation){
        this.simulation = simulation;
        this.newGenerationSimulation = new Simulation(simulation);
    }

    private void initDirectionHashMap() {
        directions.put("L",new Point(-1,0));
        directions.put("R",new Point(1,0));
        directions.put("LTC",new Point(-1,1));
        directions.put("RTC",new Point(1,1));
        directions.put("T",new Point(0,1));
        directions.put("B",new Point(0,-1));
        directions.put("LBC",new Point(-1,-1));
        directions.put("RBC",new Point(1,-1));
    }

    public Simulation calculateNextGeneration(){
        newGenerationSimulation.setGeneration(newGenerationSimulation.getGeneration()+1);
        for (Block block:simulation.getBlocks()){
            Block newLocalBlock = new Block(block.getStartPoint().x,block.getStartPoint().y);
            newGenerationSimulation.getBlocks().add(newLocalBlock);
            setActualCellNewState(block, newLocalBlock);
        }
        return newGenerationSimulation;
    }

    public Simulation calculatePreviousGeneration(int generation){
        if(generation ==1) {
            newGenerationSimulation = simulation;
        }
        for(int i=1;i<generation;i++){
            simulation = calculateNextGeneration();
            if(i<generation-1) {
                newGenerationSimulation = new Simulation(simulation);
            }
        }
        newGenerationSimulation.setGeneration(generation-1);
        return newGenerationSimulation;
    }

    private void setActualCellNewState(Block block, Block newLocalBlock) {
        for (Cell cell: block.getCells()){
            long livingNeighboursNumber = calculateLivingNeighboursNumber(cell,simulation.getBlocks());
            Cell newCell =new Cell(cell);
            newCell.setIsDead(newCell.checkState(simulation.getRule(),livingNeighboursNumber));
            newLocalBlock.getCells().add(newCell);
            createNewNeighbourCellsForAlreadyUsedCell(newLocalBlock, cell);
        }
    }

    private void createNewNeighbourCellsForAlreadyUsedCell(Block newLocalBlock, Cell cell) {
        directions.values().stream().forEach(point -> {
            Cell newCell = new Cell(true,cell.getPoint().x+point.x, cell.getPoint().y+point.y);
            if(!isAlreadyUsedCell(newCell,simulation.getBlocks())
                    &&!isAlreadyUsedCell(newCell,newGenerationSimulation.getBlocks())
            ){
                long livingNeighboursNumberForNewCell = calculateLivingNeighboursNumber(newCell,simulation.getBlocks());
                newCell.setIsDead(newCell.checkState(simulation.getRule(),livingNeighboursNumberForNewCell));
                if(!newCell.getIsDead()) {
                    newLocalBlock.getCells().add(newCell);
                }
            }
        });
    }

    private boolean isAlreadyUsedCell(Cell newCell, ArrayList<Block> blocks) {
         return blocks.stream().map(
                 block -> block.getCells().stream().filter(
                         cell -> newCell.getPoint().x==cell.getPoint().x&&newCell.getPoint().y==cell.getPoint().y
                 ).findFirst().isPresent()).filter(
                         aBoolean -> aBoolean
         ).findFirst().isPresent();
    }

    private long calculateLivingNeighboursNumber(Cell originCell, ArrayList<Block> blocks) {
        Long number= directions.values().stream().map((point) -> {
            int x = originCell.getPoint().x+point.x;
            int y = originCell.getPoint().y+point.y;
            long cellNumberInDirection =0L;
            cellNumberInDirection =  blocks.stream().map(block -> findNeighbours(block,x,y)).filter(cell -> cell.isPresent()&&!cell.get().getIsDead()).count();
            return cellNumberInDirection;
        }).collect(Collectors.toList()).stream().reduce(0L,(aLong, aLong2) -> aLong+aLong2);
        return number;
    }

    private Optional<Cell> findNeighbours(Block block, int x, int y) {
        return block.getCells().stream().filter(cell -> cell.getPoint().x==x&&cell.getPoint().y==y).findFirst();
    }
}
