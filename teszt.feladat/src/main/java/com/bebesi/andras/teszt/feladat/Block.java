package com.bebesi.andras.teszt.feladat;

import lombok.*;

import java.awt.*;
import java.util.ArrayList;

@Data
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Block {
  private ArrayList<Cell> cells;
  private Point startPoint;

  public Block(int x,int y){
      this.cells = new ArrayList<>();
      this.startPoint= new Point(x,y);
  }
}
