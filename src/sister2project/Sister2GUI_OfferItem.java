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
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import org.json.JSONException;

/**
 *
 * @author Imburden
 */
public class Sister2GUI_OfferItem extends javax.swing.JPanel implements ActionListener {
    private int activeLabel = 0;
    private ImageIcon OfferLabel;
    private ImageIcon DemandLabel;
    private int offeredItem[] = new int[2];
    private final Timer tm = new Timer(200,this);
    
    @Override
    public void actionPerformed(ActionEvent e) {
        //Set item count
        try {
            if (Client.getOnline()) {
                Item1Count.setText(String.valueOf(Client.getInventory().getInt(0)));
                Item2Count.setText(String.valueOf(Client.getInventory().getInt(1)));
                Item3Count.setText(String.valueOf(Client.getInventory().getInt(2)));
                Item4Count.setText(String.valueOf(Client.getInventory().getInt(3)));
                Item5Count.setText(String.valueOf(Client.getInventory().getInt(4)));
                Item6Count.setText(String.valueOf(Client.getInventory().getInt(5)));
                Item7Count.setText(String.valueOf(Client.getInventory().getInt(6)));
                Item8Count.setText(String.valueOf(Client.getInventory().getInt(7)));
                Item9Count.setText(String.valueOf(Client.getInventory().getInt(8)));
                Item10Count.setText(String.valueOf(Client.getInventory().getInt(9)));
            }
        } catch (JSONException ex) {
            Logger.getLogger(Sister2GUI_OfferItem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Creates new form Sister2GUI_Offeritem
     */
    public Sister2GUI_OfferItem() {
        initComponents();
        
        OfferLabel = new ImageIcon(getClass().getResource("img/items/_frame-default.png"));
        DemandLabel = new ImageIcon(getClass().getResource("img/items/_frame-default.png"));
        Container.setBackground(new Color(202, 202, 202, 225));
        
        tm.start();
    }
    
    public void AddButtonClickEvents(JPanel cards) {
        SubmitOffer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) 
            {
                ClientMailer.openConnection();
                
                Client.Offer(Client.getToken(), offeredItem[0], (Integer) spinnerOffer.getValue(), offeredItem[1], (Integer) spinnerDemand.getValue());
                try {
                    Client.setRes(ClientMailer.communicate(Client.getReq().toString()));
                    Client.resOffer();
                    
                    JOptionPane.showMessageDialog(Container,
                            Client.getRes().getString("status"),
                            "Information",
                            JOptionPane.INFORMATION_MESSAGE,
                            null);
                    
                    Client.Inventory(Client.getToken());
                    Client.setRes(ClientMailer.communicate(Client.getReq().toString()));
                    Client.resInventory();
                    
                    activeLabel = 0;
                    ImageIcon img = new ImageIcon(getClass().getResource("img/items/_frame-default.png"));
                    ItemOffer_1.setIcon(img);
                    ItemDemand_1.setIcon(img);
                } catch (JSONException ex) {
                    Logger.getLogger(Sister2GUI_OfferItem.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                ClientMailer.closeConnection();
            }
        });
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
        ItemOffer_1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) 
            {
                //CHANGE LABEL
                if (activeLabel == 1) {
                    activeLabel = 0;
                    ImageIcon img = new ImageIcon(getClass().getResource("img/items/_frame-default.png"));
                    ItemOffer_1.setIcon(img);
                }
                else {
                    activeLabel = 1;
                    ImageIcon img = new ImageIcon(getClass().getResource("img/items/_frame.png"));
                    ItemOffer_1.setIcon(img);
                }
            }
        });
        ItemDemand_1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) 
            {
                //CHANGE LABEL
                if (activeLabel == 2) {
                    activeLabel = 0;
                    ImageIcon img = new ImageIcon(getClass().getResource("img/items/_frame-default.png"));
                    ItemDemand_1.setIcon(img);
                }
                else {
                    activeLabel = 2;
                    ImageIcon img = new ImageIcon(getClass().getResource("img/items/_frame.png"));
                    ItemDemand_1.setIcon(img);
                }
            }
        });
        Item1_1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) 
            {
                //CHANGE LABEL
                ImageIcon img = new ImageIcon(getClass().getResource("img/items/honey.png"));
                if (activeLabel == 1) {
                    ItemOffer_1.setIcon(img);
                    offeredItem[0] = 0;
                }
                else if (activeLabel == 2) {
                    ItemDemand_1.setIcon(img);
                    offeredItem[1] = 0;
                }
                activeLabel = 0;
            }
        });
        Item1_2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) 
            {
                //CHANGE LABEL
                ImageIcon img = new ImageIcon(getClass().getResource("img/items/herb.png"));
                 if (activeLabel == 1) {
                    ItemOffer_1.setIcon(img);
                    offeredItem[0] = 1;
                }
                else if (activeLabel == 2) {
                    ItemDemand_1.setIcon(img);
                    offeredItem[1] = 1;
                }
                activeLabel = 0;
            }
        });
        Item1_3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) 
            {
                //CHANGE LABEL
                ImageIcon img = new ImageIcon(getClass().getResource("img/items/clay.png"));
                 if (activeLabel == 1) {
                    ItemOffer_1.setIcon(img);
                    offeredItem[0] = 2;
                }
                else if (activeLabel == 2) {
                    ItemDemand_1.setIcon(img);
                    offeredItem[1] = 2;
                }
                activeLabel = 0;
            }
        });
        Item1_4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) 
            {
                //CHANGE LABEL
                ImageIcon img = new ImageIcon(getClass().getResource("img/items/mineral.png"));
                 if (activeLabel == 1) {
                    ItemOffer_1.setIcon(img);
                    offeredItem[0] = 3;
                }
                else if (activeLabel == 2) {
                    ItemDemand_1.setIcon(img);
                    offeredItem[1] = 3;
                }
                activeLabel = 0;
            }
        });
        Item2_1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) 
            {
                //CHANGE LABEL
                ImageIcon img = new ImageIcon(getClass().getResource("img/items/potion.png"));
                 if (activeLabel == 1) {
                    ItemOffer_1.setIcon(img);
                    offeredItem[0] = 4;
                }
                else if (activeLabel == 2) {
                    ItemDemand_1.setIcon(img);
                    offeredItem[1] = 4;
                }
                activeLabel = 0;
            }
        });
        Item2_2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) 
            {
                //CHANGE LABEL
                ImageIcon img = new ImageIcon(getClass().getResource("img/items/incense.png"));
                 if (activeLabel == 1) {
                    ItemOffer_1.setIcon(img);
                    offeredItem[0] = 5;
                }
                else if (activeLabel == 2) {
                    ItemDemand_1.setIcon(img);
                    offeredItem[1] = 5;
                }
                activeLabel = 0;
            }
        });
        Item2_3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) 
            {
                //CHANGE LABEL
                ImageIcon img = new ImageIcon(getClass().getResource("img/items/gem.png"));
                 if (activeLabel == 1) {
                    ItemOffer_1.setIcon(img);
                    offeredItem[0] = 6;
                }
                else if (activeLabel == 2) {
                    ItemDemand_1.setIcon(img);
                    offeredItem[1] = 6;
                }
                activeLabel = 0;
            }
        });
        Item3_1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) 
            {
                //CHANGE LABEL
                ImageIcon img = new ImageIcon(getClass().getResource("img/items/life-elixir.png"));
                 if (activeLabel == 1) {
                    ItemOffer_1.setIcon(img);
                    offeredItem[0] = 7;
                }
                else if (activeLabel == 2) {
                    ItemDemand_1.setIcon(img);
                    offeredItem[1] = 7;
                }
                activeLabel = 0;
            }
        });
        Item3_2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) 
            {
                //CHANGE LABEL
                ImageIcon img = new ImageIcon(getClass().getResource("img/items/mana-crystal.png"));
                 if (activeLabel == 1) {
                    ItemOffer_1.setIcon(img);
                    offeredItem[0] = 8;
                }
                else if (activeLabel == 2) {
                    ItemDemand_1.setIcon(img);
                    offeredItem[1] = 8;
                }
                activeLabel = 0;
            }
        });
        Item4_1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) 
            {
                //CHANGE LABEL
                ImageIcon img = new ImageIcon(getClass().getResource("img/items/philosopher.png"));
                 if (activeLabel == 1) {
                    ItemOffer_1.setIcon(img);
                    offeredItem[0] = 9;
                }
                else if (activeLabel == 2) {
                    ItemDemand_1.setIcon(img);
                    offeredItem[1] = 9;
                }
                activeLabel = 0;
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
        Item1_1 = new javax.swing.JLabel();
        Item1_2 = new javax.swing.JLabel();
        Item1_3 = new javax.swing.JLabel();
        Item1_4 = new javax.swing.JLabel();
        Item2_1 = new javax.swing.JLabel();
        Item2_2 = new javax.swing.JLabel();
        Item2_3 = new javax.swing.JLabel();
        Item3_1 = new javax.swing.JLabel();
        Item3_2 = new javax.swing.JLabel();
        Item4_1 = new javax.swing.JLabel();
        BackButton = new javax.swing.JLabel();
        ItemOffer_1 = new javax.swing.JLabel();
        ItemDemand_1 = new javax.swing.JLabel();
        spinnerOffer = new javax.swing.JSpinner();
        spinnerDemand = new javax.swing.JSpinner();
        Container = new javax.swing.JPanel();
        Item1Count = new javax.swing.JLabel();
        Item2Count = new javax.swing.JLabel();
        Item3Count = new javax.swing.JLabel();
        Item4Count = new javax.swing.JLabel();
        Item5Count = new javax.swing.JLabel();
        Item6Count = new javax.swing.JLabel();
        Item7Count = new javax.swing.JLabel();
        Item8Count = new javax.swing.JLabel();
        Item9Count = new javax.swing.JLabel();
        Item10Count = new javax.swing.JLabel();
        SubmitOffer = new javax.swing.JLabel();
        TitleHeader = new javax.swing.JLabel();
        Background = new javax.swing.JLabel();

        jLayeredPane1.setPreferredSize(new java.awt.Dimension(640, 480));

        Item1_1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sister2project/img/items/honey.png"))); // NOI18N

        Item1_2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sister2project/img/items/herb.png"))); // NOI18N

        Item1_3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sister2project/img/items/clay.png"))); // NOI18N

        Item1_4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sister2project/img/items/mineral.png"))); // NOI18N

        Item2_1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sister2project/img/items/potion.png"))); // NOI18N

        Item2_2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sister2project/img/items/incense.png"))); // NOI18N

        Item2_3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sister2project/img/items/gem.png"))); // NOI18N

        Item3_1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sister2project/img/items/life-elixir.png"))); // NOI18N

        Item3_2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sister2project/img/items/mana-crystal.png"))); // NOI18N

        Item4_1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sister2project/img/items/philosopher.png"))); // NOI18N

        BackButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sister2project/img/backButton.png"))); // NOI18N

        ItemOffer_1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sister2project/img/items/_frame-default.png"))); // NOI18N

        ItemDemand_1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sister2project/img/items/_frame-default.png"))); // NOI18N

        Item1Count.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        Item2Count.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        Item3Count.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        Item4Count.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        Item5Count.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        Item6Count.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        Item7Count.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        Item8Count.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        Item9Count.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        Item10Count.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout ContainerLayout = new javax.swing.GroupLayout(Container);
        Container.setLayout(ContainerLayout);
        ContainerLayout.setHorizontalGroup(
            ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ContainerLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Item1Count, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Item6Count, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 72, Short.MAX_VALUE)
                .addGroup(ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Item2Count, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Item7Count, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(75, 75, 75)
                .addGroup(ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Item3Count, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Item8Count, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(74, 74, 74)
                .addGroup(ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Item4Count, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(ContainerLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(Item9Count, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(78, 78, 78)
                .addGroup(ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Item10Count, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Item5Count, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );
        ContainerLayout.setVerticalGroup(
            ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ContainerLayout.createSequentialGroup()
                .addGap(88, 88, 88)
                .addGroup(ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Item4Count, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Item5Count, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Item2Count, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Item3Count, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Item1Count, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(82, 82, 82)
                .addGroup(ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Item9Count, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Item10Count, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Item7Count, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Item8Count, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Item6Count, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(121, Short.MAX_VALUE))
        );

        SubmitOffer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sister2project/img/offer-item-submit.png"))); // NOI18N

        TitleHeader.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sister2project/img/offer-item-title.png"))); // NOI18N

        Background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sister2project/img/coin-stack.jpg"))); // NOI18N

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap(73, Short.MAX_VALUE)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Item1_1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                            .addComponent(Item2_2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(55, 55, 55)
                            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(ItemOffer_1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(Item2_3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(spinnerOffer))
                                .addComponent(Item1_2))
                            .addGap(56, 56, 56)
                            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(Item3_1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(Item1_3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(ItemDemand_1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(spinnerDemand))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                    .addGap(56, 56, 56)
                                    .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(Item1_4)
                                        .addComponent(Item3_2))
                                    .addGap(18, 60, Short.MAX_VALUE)))
                            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(Item4_1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(Item2_1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(90, 90, 90))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(BackButton)
                                .addComponent(SubmitOffer))
                            .addGap(236, 236, 236)))))
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addGap(212, 212, 212)
                .addComponent(TitleHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(Background, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                    .addContainerGap(65, Short.MAX_VALUE)
                    .addComponent(Container, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(73, Short.MAX_VALUE)))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TitleHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(Item1_4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(Item1_3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(Item1_1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(Item2_1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(Item1_2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(67, 67, 67)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Item3_1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Item2_2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Item2_3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Item4_1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Item3_2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ItemOffer_1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ItemDemand_1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spinnerOffer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spinnerDemand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addComponent(SubmitOffer)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BackButton, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(Background, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                    .addContainerGap(56, Short.MAX_VALUE)
                    .addComponent(Container, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(86, Short.MAX_VALUE)))
        );
        jLayeredPane1.setLayer(Item1_1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(Item1_2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(Item1_3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(Item1_4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(Item2_1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(Item2_2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(Item2_3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(Item3_1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(Item3_2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(Item4_1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(BackButton, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(ItemOffer_1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(ItemDemand_1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(spinnerOffer, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(spinnerDemand, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(Container, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(SubmitOffer, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(TitleHeader, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(Background, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel BackButton;
    private javax.swing.JLabel Background;
    private javax.swing.JPanel Container;
    private javax.swing.JLabel Item10Count;
    private javax.swing.JLabel Item1Count;
    private javax.swing.JLabel Item1_1;
    private javax.swing.JLabel Item1_2;
    private javax.swing.JLabel Item1_3;
    private javax.swing.JLabel Item1_4;
    private javax.swing.JLabel Item2Count;
    private javax.swing.JLabel Item2_1;
    private javax.swing.JLabel Item2_2;
    private javax.swing.JLabel Item2_3;
    private javax.swing.JLabel Item3Count;
    private javax.swing.JLabel Item3_1;
    private javax.swing.JLabel Item3_2;
    private javax.swing.JLabel Item4Count;
    private javax.swing.JLabel Item4_1;
    private javax.swing.JLabel Item5Count;
    private javax.swing.JLabel Item6Count;
    private javax.swing.JLabel Item7Count;
    private javax.swing.JLabel Item8Count;
    private javax.swing.JLabel Item9Count;
    private javax.swing.JLabel ItemDemand_1;
    private javax.swing.JLabel ItemOffer_1;
    private javax.swing.JLabel SubmitOffer;
    private javax.swing.JLabel TitleHeader;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JSpinner spinnerDemand;
    private javax.swing.JSpinner spinnerOffer;
    // End of variables declaration//GEN-END:variables
}
