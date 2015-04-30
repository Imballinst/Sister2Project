/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sister2project;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

/**
 *
 * @author Imburden
 */
public class Sister2Project {

    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        // TODO code application logic here
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new Sister2GUI();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Sister2Project.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
}
