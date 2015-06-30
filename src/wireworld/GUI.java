/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wireworld;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 *
 * @author Mon
 */
public final class GUI extends JFrame implements Runnable,wireworldLogic,cellLogic,sphereLogic {

    private JPanel panel;
    private int size;
    private boolean startflag = false;
    private JLabel status;
    private long timeInMilis;
    private cell[][] sfera;
    private ScheduledExecutorService executor;
    private JFileChooser fc;
    private JButton headState,tailState,ctorState,emptyState,openFile,saveFile,evolveButton,stopButton,cleanButton;
    private cell.state stateChangable = cell.state.CTOR;

    //Action Listnery odpowiedzialne za zmiany stanów poszczególnych komórek //
    private ActionListener changeHead = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent ae) {
           changeState(cell.state.HEAD);
           
        }
        
    };
    private ActionListener changeEmpty = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent ae) {
           changeState(cell.state.EMPTY);
           
        }
        
    };
    private ActionListener changeTail = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent ae) {
           changeState(cell.state.TAIL);
           
        }
        
    };
    private ActionListener changeCtor = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent ae) {
           changeState(cell.state.CTOR);
           
        }
        
    };
    //ActionListnery odpowiedzialne za zapis i wczytywanie z pliku//
    private ActionListener changeFile = new ActionListener() {

    @Override
    public void actionPerformed(ActionEvent ae) {
            File dir = new File(fc.getCurrentDirectory()+ "\\NetBeansProjects\\wireworld\\src\\wireworld");
            fc.setCurrentDirectory(dir);
            if(startflag ==true){
                String message = "Nie moge otworzyć pliku, ponieważ trwa ewolucja, trzeba ewolucje zastopować";
                JOptionPane.showMessageDialog(new JFrame(), message, "ERROR", JOptionPane.ERROR_MESSAGE);
            }else{
           if (ae.getSource() == openFile) {
            int returnVal = fc.showOpenDialog(GUI.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                cleanSphere();
                
                readFromFile in = new readFromFile(file, size, sfera);
                
                repaint();
            }}}}}; 
    private ActionListener savetoFile = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent ae) {
            File dir = new File(fc.getCurrentDirectory()+ "\\NetBeansProjects\\wireworld\\src\\wireworld");
            fc.setCurrentDirectory(dir);
            
            if(startflag == true){
                String message = "Nie moge zapisać pliku bo trwa ewolucja, aby zapisać, zatrzymaj wątek";
                JOptionPane.showMessageDialog(new JFrame(), message, "ERROR", JOptionPane.ERROR_MESSAGE);
                
            }else{
            int returnVal = fc.showOpenDialog(GUI.this);
            System.out.println(fc.getCurrentDirectory());
            
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                saveToFile in = new saveToFile(file, sfera,size);
                
                 
                
            }}}};
    //ActionListnery odpowiedzialne za Starowanie i stopowanie ewolucji za pomocą wątków//
    private ActionListener stop = new ActionListener(){

        @Override
        public void actionPerformed(ActionEvent ae) {
            if(startflag==false){
                String message ="Nie moge zatrzymać wątku, który nie jest wystartowany";
                JOptionPane.showMessageDialog(new JFrame(), message, "ERROR", JOptionPane.ERROR_MESSAGE);
            }else{
            executor.shutdownNow();
            startflag = false;
            status.setText("State: not running");
        }
        }  
    };
    private ActionListener evolve = new ActionListener(){
        
        @Override
        public void actionPerformed(ActionEvent ae) {
            
            if(startflag == true){
               String message = "Wątek wyruszył";
               JOptionPane.showMessageDialog(new JFrame(), message, "ERROR", JOptionPane.ERROR_MESSAGE);
            }else if(startflag==false){
            executor = Executors.newScheduledThreadPool(1);
            
            executor.scheduleAtFixedRate(nextStateRunnable, 0, timeInMilis, TimeUnit.MILLISECONDS);
            startflag = true;
            
            status.setText("State: running");
            }
        }
        
    };
    
    //Wątek ewoluujący//
    private Runnable nextStateRunnable = new Runnable() {
        @Override
        public void run() {
            nextState();
        }
    };
    
    //wyczyszczenie planszy//
    private ActionListener cleanBoard = new ActionListener(){

        @Override
        public void actionPerformed(ActionEvent ae) {
            cleanSphere();
            repaint();
        }
        
    };
    
   
    //Główne GUI które dodaje panele odpowiedzialne, które w swoim ciele dodaje plansze z //    
    public GUI(int size, long time){
        super("WIREWORLD");
        this.size = size;
        this.timeInMilis = time;
        this.panel = new JPanel(new GridLayout(1,2));
        this.panel.add(wireworldPanel());
        this.panel.add(buttonSuperPanel());
                
    }
    
    //Panel z "szachownicą" wireworld//

    public JPanel wireworldPanel(){
        
        this.sfera = new cell[this.size][this.size];
        JPanel panel0 = new JPanel(new GridLayout(this.size,this.size,2,2));
        for(int i =0; i<this.size; i++){
           for(int j = 0; j<this.size; j++){
               cell cll = new cell(i,j,cell.state.EMPTY);
               cll.setPreferredSize(new Dimension(10,10));
               cll.newstate = this.stateChangable;
               panel0.add(cll);
               sfera[i][j] = cll;
               
               
            }
        }
        createworld();
        
        return panel0;
    }

    //Panel z Przyciskami//

    public final JPanel buttonSuperPanel(){
        JPanel panel1 = new JPanel(new GridLayout(7,1,2,2));
        panel1.setSize(this.size*10, this.size*10);
       //evolve Panel//
        JPanel evoPanel = new JPanel(new GridLayout(1,2,2,2));
        this.evolveButton = new JButton("Start Evolving");
        evoPanel.add(evolveButton);
        this.evolveButton.addActionListener(this.evolve);
        this.stopButton = new JButton("Stop Evolving");
        evoPanel.add(stopButton);
        this.stopButton.addActionListener(this.stop);
        panel1.add(evoPanel);
        JPanel panelState = new JPanel(new GridLayout(2,2,2,2));
        this.headState = new JButton("HEAD");
        panelState.add(headState);
        this.headState.addActionListener(this.changeHead);
        this.tailState = new JButton("TAIL");
        panelState.add(tailState);
        this.tailState.addActionListener(this.changeTail);
        this.ctorState = new JButton("CTOR");
        panelState.add(ctorState);
        this.ctorState.addActionListener(this.changeCtor);
        this.emptyState = new JButton("EMPTY");
        panelState.add(emptyState);
        panel1.add(panelState);
        JPanel filePanel = new JPanel(new GridLayout(1,2,2,2));
        this.emptyState.addActionListener(this.changeEmpty);
        this.fc = new JFileChooser();
        this.fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        this.openFile = new JButton("Open file...");
        filePanel.add(openFile);
        openFile.addActionListener(this.changeFile);
        this.saveFile = new JButton("Save file...");
        filePanel.add(saveFile);
        saveFile.addActionListener(this.savetoFile);
        panel1.add(filePanel);
        this.cleanButton = new JButton("Clear Board");
        cleanButton.addActionListener(this.cleanBoard);
        panel1.add(this.cleanButton);
        this.status = new JLabel("State: not running");
        status.setBorder(new LineBorder(Color.BLACK));
        panel1.add(status);
        return panel1;
    }

    // wyczszczenie planszy //
    
    @Override
    public void cleanSphere(){
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                this.sfera[i][j].changeState(cell.state.EMPTY);
            }
        }
    }
   
    //Zmiana stanów przy pomocy przycisków//
    
    @Override
    public void changeState(cell.state Nowystan){
        this.stateChangable=Nowystan;
        for(int i =0; i<this.size; i++){
            for(int j = 0; j<this.size; j++){
                sfera[i][j].newstate = this.stateChangable;
                
            }
        }
    }
 
    //Logika wireworld z interfejsu wireworldLogic//
    @Override
    public void createworld(){
        for(int i = 0; i < this.size; i++) {
            for(int j = 0; j < this.size; j++) {
            
            if(j != 0) {
                this.sfera[i][j].getSasiedzi().add(this.sfera[i][j - 1]);
            }
 
            if(i != 0) {
                this.sfera[i][j].getSasiedzi().add(this.sfera[i - 1][j]);
            }
 
            if(j != (size - 1)) {
                this.sfera[i][j].getSasiedzi().add(this.sfera[i][j + 1]);
            }
 
            if(i != (size - 1)) {
                this.sfera[i][j].getSasiedzi().add(this.sfera[i + 1][j]);
            }
 
            if((i != 0) && (j != 0)) {
                this.sfera[i][j].getSasiedzi().add(this.sfera[i - 1][j - 1]);
            }
 
            if((i != (size - 1)) && (j != (size - 1))) {
                this.sfera[i][j].getSasiedzi().add(this.sfera[i + 1][j + 1]);
            }
 
            if((i != (size - 1)) && (j != 0)) {
                this.sfera[i][j].getSasiedzi().add(this.sfera[i + 1][j - 1]);
            }
 
            if((i != 0) && (j != (size - 1))) {
                this.sfera[i][j].getSasiedzi().add(this.sfera[i - 1][j + 1]);
            }
        }
    }
    }
    
    //następny stan//
       @Override
    public void nextState(){
        int head;
        for(int i=0;i<this.size; i++){
            for(int j=0; j<this.size; j++ ){
                if(this.sfera[i][j].state() == cell.state.EMPTY){
                    
                }
                if(this.sfera[i][j].state() == cell.state.CTOR){
                    head =0;
                    for(int x=0;x<this.sfera[i][j].getSasiedzi().size();x++){
                        cell.state test = this.sfera[i][j].getSasiedzi().get(x).state();
                        if(test == cell.state.TOVER || test == cell.state.HEAD ){
                        
                        head++;

                        }
                    }

                    if(head==1 || head==2){

                        this.sfera[i][j].changeState(cell.state.HOVER);
                        head=0;
                    }
                }
                
                if(this.sfera[i][j].state() == cell.state.HEAD){
                    this.sfera[i][j].changeState(cell.state.TOVER);
                }
                if(this.sfera[i][j].state() == cell.state.TAIL){
                    this.sfera[i][j].changeState(cell.state.CTOVER);
                }
                
            }
        }
        for(int i = 0;i<this.size;i++){
            for(int j=0;j<this.size;j++){
                if(this.sfera[i][j].state() == cell.state.CTOVER){
                    this.sfera[i][j].changeState(cell.state.CTOR);
                    repaint();
                }
                if(this.sfera[i][j].state() == cell.state.HOVER){
                    this.sfera[i][j].changeState(cell.state.HEAD);
                    repaint();
                }
                if(this.sfera[i][j].state() == cell.state.TOVER){
                    this.sfera[i][j].changeState(cell.state.TAIL);
                    repaint();
                }
            }
        }
            
    }
    
    //RUN//
    @Override
    public void run() {
        
        setSize(2*size*10,size*10);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        getContentPane().add(this.panel);
        setVisible(true);
        setResizable(false);
    }


    

 

   
}
