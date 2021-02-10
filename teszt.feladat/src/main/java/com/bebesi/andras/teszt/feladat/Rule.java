package com.bebesi.andras.teszt.feladat;

import lombok.*;
import org.springframework.util.StringUtils;

import java.util.ArrayList;

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
public class Rule {
    private ArrayList<Integer> survival;
    private ArrayList<Integer> birth;

    public Rule(String rule){
        survival = new ArrayList<>();
        birth = new ArrayList<>();
        initRule(rule);
    }

    private void initRule(String rule) {
        String ruleNumberData="";
        if(rule.contains("#R")) {
            ruleNumberData = rule.replace("#R ", "");
        }else if(rule.contains("#N")){
            ruleNumberData= rule.replace("#N","");
        }
        if(!StringUtils.hasLength(ruleNumberData)){
            survival.add(2);
            survival.add(3);
            birth.add(3);
        }else{
            String survivalNumbers= rule.split("/")[0];
            String birthNumbers= rule.split("/")[1];
            for (char c:survivalNumbers.toCharArray()) {
                survival.add(Character.getNumericValue(c));
            }
            for (char c:birthNumbers.toCharArray()) {
                birth.add(Character.getNumericValue(c));
            }
        }
    }

}
