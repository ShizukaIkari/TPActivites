/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taddic;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

/**
 *
 * @author Serenna
 */
public class HashEngineDefault extends Hash_engine {

    @Override
    public long hash_func(Object k) {
        long soma = 0;
        
        ByteArrayOutputStream bus = new ByteArrayOutputStream();
        ObjectOutput out = null;
        byte[] vetBytes = null;
               
        try{
            out = new ObjectOutputStream(bus);
            out.writeObject(k);
            out.flush();
            vetBytes = bus.toByteArray();
        }catch(IOException e) {
            e.printStackTrace();
        }
        try {
            bus.close();
        } catch (IOException ex) {
            //ignore
        }
        for(int i=0; i<vetBytes.length;i++){
            soma += (int)vetBytes[i];
        }
        return soma;
    }
    
    
}
