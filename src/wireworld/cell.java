/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wireworld;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import javax.swing.JPanel;


/**
 *
 * @author Mon
 */


public class cell extends JPanel implements MouseListener, cellLogic{

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.posX;
        hash = 17 * hash + this.posY;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final cell other = (cell) obj;
        if (this.posX != other.posX) {
            return false;
        }
        if (this.posY != other.posY) {
            return false;
        }
        return true;
    }

   public enum state {EMPTY, HEAD, TAIL, CTOR, HOVER, TOVER, CTOVER;} // dodatkowe stany prejściowe, ponieważ treba spawdzić całą tablice//
   private final int posX;
   private final int posY;

   private state state;
   public state newstate;
   

   private List<cell> sasiedzi = new ArrayList<>();
      
   public cell(int x, int y, state stateNew){
       this.posX = x;
       this.posY = y;
       this.state = stateNew;
       addMouseListener(this);
     }
   //metody dostępowe//
   public List<cell> getSasiedzi(){
       return this.sasiedzi;
   }
   
   public int getPosX(){
       return this.posX;
   }
   
   public int getPosY(){
       return this.posY;
   }
   
   public state state() {
       return this.state;
   }
   

   
  
    
   
    //zmiana stanu
   @Override
   public void changeState(state newState){
       this.state = newState;
   }
   
   @Override
   public String toString(){
       
       String stringed = this.posX + " " +  this.posY + " " + this.state;
       return stringed;
   
   
   }
   //rysowanie obiektu odpowiednim kolorem//
   @Override
   public void paintComponent(Graphics g){
       super.paintComponent(g);
       if(this.state == this.state.EMPTY){
            g.setColor(Color.white);
            g.drawRect(0, 0, getWidth()-1 , getHeight()-1);
            g.fillRect(0, 0, getWidth()-1 , getHeight()-1);}
       if(this.state == this.state.HEAD){

            g.setColor(Color.blue);
            g.drawRect(0, 0, getWidth()-1 , getHeight()-1);
            g.fillRect(1, 1, getWidth()-2 , getHeight()-2);
       }
       if(this.state == this.state.TAIL){

            g.setColor(Color.red);
            g.drawRect(0, 0, getWidth()-1 , getHeight()-1);
            g.fillRect(1, 1, getWidth()-2 , getHeight()-2);
             
       }
       if(this.state == this.state.CTOR){
            g.setColor(Color.yellow);
            g.drawRect(0, 0, getWidth()-1 , getHeight()-1);
            g.fillRect(1, 1, getWidth()-2 , getHeight()-2);
       }
       
   }
   //metody dziedziczące po MouseListener
   @Override
    public void mouseClicked(MouseEvent me) {
       // changeState(newstate);
    }

    @Override
    public void mousePressed(MouseEvent me) {
        
        changeState(newstate);
        repaint();
   }

    @Override
    public void mouseReleased(MouseEvent me) {
        
    }

    @Override
    public void mouseEntered(MouseEvent me) {

    }

    @Override
    public void mouseExited(MouseEvent me) {
        
    }
}
