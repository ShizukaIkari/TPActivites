/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taddic;

/**
 *
 * @author Serenna
 */
public class RegDados {
    private String palPt;
    private String palEn;
    
    public RegDados(String palPt, String palEng){
        this.palPt = palPt;
        this.palEn = palEng;
    }
    
    public String getWpt(){
        return palPt;
    }
    
    public String getWen(){
        return palEn;
    }
    
    public void setWpt(String pt){
        palPt = pt;
    }
    
    public void setWen(String en){
        palEn = en;
    }
}
