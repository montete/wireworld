/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wireworld;

import java.io.File;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;

import java.util.StringTokenizer;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
/**
 *
 * @author Mon
 */
public class readFromFile  implements sphereLogic {
    private cell[][] target;
    private int size;
    
    private void errMsg(String message){
            JOptionPane.showMessageDialog(new JFrame(), message, "ERROR", JOptionPane.ERROR_MESSAGE);  
        }
    //czytanie z pliku linia, po linii//
    public readFromFile(File plik, int size, cell[][] tablica){
        String line;
        target = tablica;
        this.size =size;
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(plik));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(readFromFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            while((line = in.readLine())!=null){
               // System.out.println(line);
                StringTokenizer st = new StringTokenizer(line, " ");
                ArrayList<String> buffy = new ArrayList<>();
                while(st.hasMoreElements()){
                    buffy.add(st.nextToken());
                }
                if(!(buffy.size()==3)){
                    throw new RuntimeException("Blad pliku wsadowego - wiecej niz 3 tokeny");
                }
                for(int i=0;i<buffy.size()-1;i++){
                    if(!(isInteger(buffy.get(i)))){
                        cleanSphere();
                        throw new RuntimeException("Isinteger = false");
                        
                    }else if(Integer.parseInt(buffy.get(i))>size){
                        cleanSphere();
                        throw new RuntimeException("size error");
                        
                    }
                    
                }
                if(buffy.get(2).compareTo("CTOR") ==0){
                    
                }else if(buffy.get(2).compareTo("HEAD")==0){
                    
                }else if(buffy.get(2).compareTo("TAIL")==0){
                    
                }else if(buffy.get(2).compareTo("TAIL")==0){
                    
                }else{
                    throw new RuntimeException("Blad pliku wsadowego - stan nie zgada się z listą stanów");
                }
                int curX = Integer.parseInt(buffy.get(0));
                int curY = Integer.parseInt(buffy.get(1));
                
                try{
                    this.target[curX][curY].changeState(cell.state.valueOf(buffy.get(2)));
                }catch (java.lang.ArrayIndexOutOfBoundsException e){
                    errMsg("zła wartość w pliku");
                }
            }
                
            
        } catch (IOException ex) {
            Logger.getLogger(readFromFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    @Override
    public void cleanSphere(){
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                this.target[i][j].changeState(cell.state.EMPTY);
                this.target[i][j].repaint();
            }
        }
    }
    //metoda sprawdzająca czy liczba jest INT//
    public static boolean isInteger(String s){
        try{
            Integer.parseInt(s);
        } catch(NumberFormatException e){
            return false;
        }
        return true;
    }
    
    
    public cell[][] getTarget(){
        return this.target;
    }

    @Override
    public void changeState(cell.state nowystan) {
        throw new UnsupportedOperationException("rWont be supported here ever"); //To change body of generated methods, choose Tools | Templates.
    }
}
