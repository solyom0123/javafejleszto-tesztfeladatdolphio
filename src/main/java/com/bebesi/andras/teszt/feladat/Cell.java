package com.bebesi.andras.teszt.feladat;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Bebesi András
 * @apiNote It stores incoming and out coming value and length data.
 */

@lombok.Data
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Cell {
    private Boolean isDead;
    private Point point;

    public Cell(Boolean isDead,int x,int y){
        this.isDead=isDead;
        this.point = new Point(x,y);
    }

    public Cell(int x,int y){
        this.point = new Point(x,y);
    }


    public Cell(Cell cell) {
        this.isDead=cell.getIsDead();
        this.point = cell.getPoint();
    }

    public boolean checkState(Rule rule,long calculatedLiveNeighboursNumber){
        boolean isDying = true;
        if(isDead){
            for (int nm :rule.getBirth()) {
                if (calculatedLiveNeighboursNumber == nm) {
                    isDying = false;
                }
            }
        }else{
            for (int nm:rule.getSurvival()) {
                if (calculatedLiveNeighboursNumber == nm) {
                    isDying = false;
                }
            }
        }
        return isDying;
    }

}
