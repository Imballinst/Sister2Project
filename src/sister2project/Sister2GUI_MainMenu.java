/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sister2project;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import static javax.swing.border.TitledBorder.*;
import org.json.JSONException;

/**
 *
 * @author Imburden
 */
public class Sister2GUI_MainMenu extends javax.swing.JPanel implements ActionListener {
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Client.setName(jTextField1.getText());
    }   
    
    /**
     * Creates new form Sister2GUI_MainMenu
     */
    public Sister2GUI_MainMenu() {
        initComponents();
        TitledBorder b = BorderFactory.createTitledBorder(null, "Server IP Address", CENTER, DEFAULT_POSITION, null, Color.white);
        Title.setBorder(b);
    }
    
    public void AddButtonClickEvents(JPanel cards) {
        Register.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) 
            {
                //REGISTER
                String user = jTextField1.getText();
                String pass = new String (jPasswordField1.getPassword());
                String hostport = jTextField3.getText();
                int del = hostport.lastIndexOf(":");
                String host = hostport.substring(0, del);
                int port = Integer.valueOf(hostport.substring(del+1, hostport.length()));
                
                ClientMailer.setHostName(host);
                ClientMailer.setHostPort(port);
                ClientMailer.openConnection();
                
                Client.SignUp(user, pass);
                try {
                    Client.setRes(ClientMailer.communicate(Client.getReq().toString()));
                    Client.resSignUp();
                    
                    JOptionPane.showMessageDialog(Username,
                        Client.getRes().getString("status"),
                        "Information",
                        JOptionPane.INFORMATION_MESSAGE,
                        null);
                } catch (JSONException ex) {
                    Logger.getLogger(Sister2GUI_MainMenu.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                ClientMailer.closeConnection();
            }
        });
        Login.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) 
            {
                //LOGIN
                String user = jTextField1.getText();
                String pass = new String (jPasswordField1.getPassword());
                String hostport = jTextField3.getText();
                int del = hostport.lastIndexOf(":");
                String host = hostport.substring(0, del);
                int port = Integer.valueOf(hostport.substring(del+1, hostport.length()));
                
                ClientMailer.setHostName(host);
                ClientMailer.setHostPort(port);
                ClientMailer.openConnection();
                
                Client.Login(user, pass);
                try {
                    Client.setRes(ClientMailer.communicate(Client.getReq().toString()));
                    
                    JOptionPane.showMessageDialog(Username,
                        Client.getRes().getString("status"),
                        "Information",
                        JOptionPane.INFORMATION_MESSAGE,
                        null);
                    
                    if (Client.getRes().get("status").toString().compareTo("ok") == 0) {
                        Client.resLogin();                        
                        Client.setLogin(true);
                        Client.setOnline(true);
                        Client.setGameScene(true);
                        
                        //GET MAP
                        Client.Map(Client.getToken());
                        try {
                            Client.setRes(ClientMailer.communicate(Client.getReq().toString()));
                            Client.resMap();
                        } catch (JSONException ex) {
                            Logger.getLogger(Sister2GUI_Game.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        //GET INVENTORY
                        Client.Inventory(Client.getToken());
                        try {
                            Client.setRes(ClientMailer.communicate(Client.getReq().toString()));
                            Client.resInventory();
                        } catch (JSONException ex) {
                            Logger.getLogger(Sister2GUI_Game.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        //GAME
                        CardLayout cl = (CardLayout)(cards.getLayout());
                        cl.show(cards, "Game");
                    }
                } catch (JSONException ex) {
                    Logger.getLogger(Sister2GUI_MainMenu.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                ClientMailer.closeConnection();
            }
        });
        Exit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) 
            {
                //EXIT
                System.exit(0);
            }
        });
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        Password = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jPasswordField1 = new javax.swing.JPasswordField();
        jTextField3 = new javax.swing.JTextField();
        Username = new javax.swing.JLabel();
        Title = new javax.swing.JLabel();
        Register = new javax.swing.JLabel();
        Login = new javax.swing.JLabel();
        Exit = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        Password.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Password.setForeground(new java.awt.Color(204, 255, 255));
        Password.setText("Password");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        Username.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Username.setForeground(new java.awt.Color(204, 255, 255));
        Username.setText("Username");

        Title.setForeground(new java.awt.Color(255, 255, 255));
        Title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Title.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Server IP Address", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        Register.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sister2project/img/register.png"))); // NOI18N

        Login.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sister2project/img/login.png"))); // NOI18N

        Exit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sister2project/img/exit.png"))); // NOI18N

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sister2project/img/compass-and-old-map.jpg"))); // NOI18N

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addGap(267, 267, 267)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addComponent(Register, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                        .addComponent(Login, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jLayeredPane1Layout.createSequentialGroup()
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Username)
                            .addComponent(Password))
                        .addGap(14, 14, 14)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField1)
                            .addComponent(jPasswordField1))
                        .addGap(3, 3, 3)))
                .addContainerGap(165, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                        .addComponent(Exit, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(151, 151, 151))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(251, 251, 251))))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(Title, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 193, Short.MAX_VALUE)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Username, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Password, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Register, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Login, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Exit)
                .addGap(66, 66, 66))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addComponent(Title, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 427, Short.MAX_VALUE)))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE))
        );
        jLayeredPane1.setLayer(Password, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jTextField1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jPasswordField1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jTextField3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(Username, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(Title, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(Register, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(Login, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(Exit, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Exit;
    private javax.swing.JLabel Login;
    private javax.swing.JLabel Password;
    private javax.swing.JLabel Register;
    private javax.swing.JLabel Title;
    private javax.swing.JLabel Username;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
}
