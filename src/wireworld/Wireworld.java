/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wireworld;

/**
 *
 * @author Mon
 */
public class Wireworld {

    /**
     * @param args the command line arguments
     */

    
    
    public static void main(String[] args) {
      System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
      optionWindow start = new optionWindow();
      //window okno = new window(30);  
      javax.swing.SwingUtilities.invokeLater(start);
    }


}
