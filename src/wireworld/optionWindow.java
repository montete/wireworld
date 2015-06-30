/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wireworld;


import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
/**
 *
 * @author Mon
 */
public class optionWindow extends JFrame implements Runnable{
    

    private JPanel panel;
    private JTextField sizeField,timeField;
    private JButton button;
    //Listener do startu//
    private ActionListener start = new ActionListener(){
        private static final int sizeMin=30,sizeMax=100;
        private static final long timeMin=10,timeMax=1000;
        
        public void errMsg(){
            String message ="Błędne wartości w polach";
            JOptionPane.showMessageDialog(new JFrame(), message, "ERROR", JOptionPane.ERROR_MESSAGE); 
        }
        public void errMsgSize(){
            String message ="Błędna wartość w polu tekstowym Size:";
            JOptionPane.showMessageDialog(new JFrame(), message, "ERROR", JOptionPane.ERROR_MESSAGE);  
        }
        public void errMsgTime(){
            String message ="Błędna wartość w polu tekstowym Time(ms):";
            JOptionPane.showMessageDialog(new JFrame(), message, "ERROR", JOptionPane.ERROR_MESSAGE);  
        }
        

        @Override
        public void actionPerformed(ActionEvent ae) {
            String sSize = sizeField.getText();
            String sTime = timeField.getText();
            long time=0;
            int size=0;
           
                try{
                    size = Integer.parseInt(sSize);

                }catch (NumberFormatException e){
                    errMsgSize();  

                }
            
            
                try{
                    time = Long.parseLong(sTime);

                }catch(NumberFormatException e){
                    errMsgTime();
                }
            if(size>sizeMax || size<sizeMin || time>timeMax || time<timeMin){
                errMsg();
            }else{
                setVisible(false);
                GUI okno = new GUI(size,time);  
                javax.swing.SwingUtilities.invokeLater(okno);
                
            }
                
                
            
        }
        
    };
    
    //Okno ustawiń opcji//
    public optionWindow(){
        super("Opcje startu");
        this.panel = new JPanel(new GridLayout(3,3,2,2));
        JPanel sizePanel = new JPanel(new GridLayout(1,2,2,2));
        JPanel timePanel = new JPanel(new GridLayout(1,2,2,2));
        //size panel//
        JLabel labelSize = new JLabel("Size: ");
        this.sizeField = new JTextField();
        sizePanel.add(labelSize);
        sizePanel.add(sizeField);
        this.panel.add(sizePanel);
        //time panel//

        JLabel labelTime = new JLabel("Time(ms): ");
        this.timeField = new JTextField();
        timePanel.add(labelTime);
        timePanel.add(timeField);
        //button//
        this.button = new JButton("Odpal program");
        this.button.addActionListener(start);
        this.panel.add(timePanel);
        this.panel.add(this.button);
        
    }
     
    @Override
    public void run() {
        
        setSize(200,150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        getContentPane().add(this.panel);
        setVisible(true);
        setResizable(false);
    }

}