/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sister2project;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import org.json.JSONException;

/**
 *
 * @author Imburden
 */

public class Sister2GUI_TradeBox extends javax.swing.JPanel implements ActionListener {
    private Timer timer = new Timer(200,this);
    private String defaultImage[] = new String[10];
    
    /**
     * Creates new form Sister2GUI_Tradebox
     */
    public Sister2GUI_TradeBox() {
        initComponents();
        
        defaultImage[0] = "img/items/honey-small.png";
        defaultImage[1] = "img/items/herb-small.png";
        defaultImage[2] = "img/items/clay-small.png";
        defaultImage[3] = "img/items/mineral-small.png";
        defaultImage[4] = "img/items/potion-small.png";
        defaultImage[5] = "img/items/incense-small.png";
        defaultImage[6] = "img/items/gem-small.png";
        defaultImage[7] = "img/items/life-elixir-small.png";
        defaultImage[8] = "img/items/mana-crystal-small.png";
        defaultImage[9] = "img/items/philosopher-small.png";
        
        timer.start();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (Client.getTradeMenu()) {
            ClientMailer.openConnection();
                
            Client.TradeBox(Client.getToken());
            try {
                Client.setRes(ClientMailer.communicate(Client.getReq().toString()));
                Client.resTradeBox();
            } catch (JSONException ex) {
                Logger.getLogger(Sister2GUI_Game.class.getName()).log(Level.SEVERE, null, ex);
            }

            ClientMailer.closeConnection();
            
            int numOffer = Client.getNumTrades();
            
            DefaultTableModel tableModel = new DefaultTableModel() {
                @Override
                public Class getColumnClass(int column)
                {
                    return getValueAt(0, column).getClass();
                }
            };
            
            tableModel.addColumn("Offered Item");
            tableModel.addColumn("Number Offered");
            tableModel.addColumn("Demanded Item");
            tableModel.addColumn("Number Demanded");
            tableModel.addColumn("Act");
            
            for (int j = 0; j < numOffer; j++) {
                tableModel.addRow(new Object[]{new ImageIcon(), new Integer(0), new ImageIcon(), new Integer(0), new ImageIcon()});
            }
            
            TradeList.setModel(tableModel);
            
            for (int i = 0; i < numOffer; i++) {
                for (int j = 0; j < 5; j++) {
                    switch(j) {
                        case 0:
                            ImageIcon imgO = new ImageIcon(getClass().getResource(defaultImage[Integer.parseInt(Client.getListOffer(i,j))]));
                            TradeList.setValueAt(imgO, i, j);
                            break;
                        case 1:
                            TradeList.setValueAt(Integer.parseInt(Client.getListOffer(i,j)), i, j);
                            break;
                        case 2:
                            ImageIcon imgD = new ImageIcon(getClass().getResource(defaultImage[Integer.parseInt(Client.getListOffer(i,j))]));
                            TradeList.setValueAt(imgD, i, j);
                            break;
                        case 3:
                            TradeList.setValueAt(Integer.parseInt(Client.getListOffer(i,j)), i, j);
                            break;
                        case 4:
                            if (Client.getListStatus(i).compareTo("true") == 0) {
                                ImageIcon imgE = new ImageIcon(getClass().getResource("img/cancelButton.png"));
                                TradeList.setValueAt(imgE, i, j);
                            }
                            else {
                                ImageIcon imgF = new ImageIcon(getClass().getResource("img/fetchButton.png"));
                                TradeList.setValueAt(imgF, i, j);
                            }
                            break;
                    }
                }
            }
            Client.setTradeMenu(false);
            
            ClientMailer.closeConnection();
        }
        
        TradeList.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JTable table = (JTable)e.getSource();
                    int row = table.getSelectedRow();
                    int col = table.getSelectedColumn();

                    System.out.println(row + "," + col);

                    if (col == 4) {
                        ClientMailer.openConnection();

                        if (Client.getListStatus(row).compareTo("True") == 0) {
                            Client.SendAccept(Client.getToken(), Client.getListToken(row));
                            try {
                                Client.setRes(ClientMailer.communicate(Client.getReq().toString()));
                                
                                JOptionPane.showMessageDialog(TradeList,
                                    Client.getRes().getString("status"),
                                    "Information",
                                    JOptionPane.INFORMATION_MESSAGE,
                                    null);
                            } catch (JSONException ex) {
                                Logger.getLogger(Sister2GUI_TradeBox.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        else {
                            Client.CancelOffer(Client.getToken(), Client.getListToken(row));
                            try {
                                Client.setRes(ClientMailer.communicate(Client.getReq().toString()));
                                
                                JOptionPane.showMessageDialog(TradeList,
                                    Client.getRes().getString("status"),
                                    "Information",
                                    JOptionPane.INFORMATION_MESSAGE,
                                    null);
                            } catch (JSONException ex) {
                                Logger.getLogger(Sister2GUI_TradeBox.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                        Client.Inventory(Client.getToken());
                        try {
                            Client.setRes(ClientMailer.communicate(Client.getReq().toString()));
                        } catch (JSONException ex) {
                            Logger.getLogger(Sister2GUI_TradeBox.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        Client.resInventory();
                        
                        Client.setTradeMenu(true);
                        ClientMailer.closeConnection();
                    }
                }
            });
    }
    
    public void AddButtonClickEvents(JPanel cards) {
        BackButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) 
            {
                //MAIN MENU
                CardLayout cl = (CardLayout)(cards.getLayout());
                cl.show(cards, "Game");
                
                Client.setGameScene(true);
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
        TitleHeader = new javax.swing.JLabel();
        BackButton = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TradeList = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(640, 480));

        jLayeredPane1.setPreferredSize(new java.awt.Dimension(640, 480));

        TitleHeader.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sister2project/img/trade-box-title.png"))); // NOI18N

        BackButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sister2project/img/backButton.png"))); // NOI18N

        TradeList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Offered Item", "Number Offered", "Demanded Item", "Number Demanded", "Act"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TradeList.setFocusable(false);
        TradeList.setRowHeight(50);
        TradeList.setRowSelectionAllowed(false);
        jScrollPane1.setViewportView(TradeList);
        if (TradeList.getColumnModel().getColumnCount() > 0) {
            TradeList.getColumnModel().getColumn(0).setResizable(false);
            TradeList.getColumnModel().getColumn(1).setResizable(false);
            TradeList.getColumnModel().getColumn(2).setResizable(false);
            TradeList.getColumnModel().getColumn(3).setResizable(false);
            TradeList.getColumnModel().getColumn(4).setResizable(false);
        }

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sister2project/img/auction-house.jpg"))); // NOI18N
        jLabel1.setText("jLabel1");

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGap(237, 237, 237)
                        .addComponent(BackButton, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGap(216, 216, 216)
                        .addComponent(TitleHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 640, Short.MAX_VALUE))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addComponent(TitleHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(BackButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(130, 130, 130))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE))
        );
        jLayeredPane1.setLayer(TitleHeader, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(BackButton, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jScrollPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 640, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 480, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel BackButton;
    private javax.swing.JLabel TitleHeader;
    private javax.swing.JTable TradeList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
