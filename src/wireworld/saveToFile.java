/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wireworld;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mon
 */
public class saveToFile {
    cell[][] target;
    
    public saveToFile(File fName, cell[][] tablica, int size){
 try {
     try (PrintWriter writer = new PrintWriter(fName, "UTF-8")) {
         for(int i=0;i<size;i++){
             for(int j=0;j<size;j++){
                 if(tablica[i][j].state() == cell.state.EMPTY){
                     
                 }else{
                     
                     writer.println(tablica[i][j].toString());
                 }
             }
         }
     }
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            Logger.getLogger(saveToFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
        
    }

