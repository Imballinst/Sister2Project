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
import javax.swing.JPanel;
import javax.swing.Timer;
import org.json.JSONException;

/**
 *
 * @author Imburden
 */
public class Sister2GUI_FindItem extends javax.swing.JPanel implements ActionListener {
    private int activeLabel = 0;
    private int findItem = -99;
    private ImageIcon FindLabel;
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
     * Creates new form Sister2GUI_Finditem
     */
    public Sister2GUI_FindItem() {
        initComponents();
        
        FindLabel = new ImageIcon(getClass().getResource("img/items/_frame-default.png"));
        Container.setBackground(new Color(202, 202, 202, 225));
        
        tm.start();
    }
    
    public void AddButtonClickEvents(JPanel cards) {
        BackButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) 
            {
                //MAIN MENU
                CardLayout cl = (CardLayout)(cards.getLayout());
                cl.show(cards, "Game");
            }
        });
        SubmitFind.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) 
            {
                activeLabel = 0;
                ImageIcon img = new ImageIcon(getClass().getResource("img/items/_frame-default.png"));
                ItemFind_1.setIcon(img);
                
                if (findItem != -99) {
                    Client.setFindItem(findItem);
                    Client.setFindMenu(true);
                    
                    //FIND RESULTS
                    CardLayout cl = (CardLayout)(cards.getLayout());
                    cl.show(cards, "Find Results");
                }
            }
        });
        ItemFind_1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) 
            {
                //CHANGE LABEL
                if (activeLabel == 1) {
                    activeLabel = 0;
                    ImageIcon img = new ImageIcon(getClass().getResource("img/items/_frame-default.png"));
                    ItemFind_1.setIcon(img);
                }
                else {
                    activeLabel = 1;
                    ImageIcon img = new ImageIcon(getClass().getResource("img/items/_frame.png"));
                    ItemFind_1.setIcon(img);
                }
                findItem = -99;
            }
        });
        Item1_1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) 
            {
                //CHANGE LABEL
                ImageIcon img = new ImageIcon(getClass().getResource("img/items/honey.png"));
                if (activeLabel == 1) {
                    ItemFind_1.setIcon(img);
                    findItem = 0;
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
                    ItemFind_1.setIcon(img);
                    findItem = 1;
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
                    ItemFind_1.setIcon(img);
                    findItem = 2;
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
                    ItemFind_1.setIcon(img);
                    findItem = 3;
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
                    ItemFind_1.setIcon(img);
                    findItem = 4;
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
                    ItemFind_1.setIcon(img);
                    findItem = 5;
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
                    ItemFind_1.setIcon(img);
                    findItem = 6;
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
                    ItemFind_1.setIcon(img);
                    findItem = 7;
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
                    ItemFind_1.setIcon(img);
                    findItem = 8;
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
                    ItemFind_1.setIcon(img);
                    findItem = 9;
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
        TitleHeader = new javax.swing.JLabel();
        BackButton = new javax.swing.JLabel();
        SubmitFind = new javax.swing.JLabel();
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
        ItemFind_1 = new javax.swing.JLabel();
        Container = new javax.swing.JPanel();
        Background = new javax.swing.JLabel();

        jLayeredPane1.setPreferredSize(new java.awt.Dimension(640, 480));

        Item1Count.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Item1Count.setLabelFor(Item1Count);

        Item2Count.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Item2Count.setLabelFor(Item2Count);

        Item3Count.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Item3Count.setLabelFor(Item3Count);

        Item4Count.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Item4Count.setLabelFor(Item4Count);

        Item5Count.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Item5Count.setLabelFor(Item5Count);

        Item6Count.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Item6Count.setLabelFor(Item6Count);

        Item7Count.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Item7Count.setLabelFor(Item7Count);

        Item8Count.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Item8Count.setLabelFor(Item8Count);

        Item9Count.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Item9Count.setLabelFor(Item9Count);

        Item10Count.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Item10Count.setLabelFor(Item10Count);

        TitleHeader.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sister2project/img/find-item-title.png"))); // NOI18N

        BackButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sister2project/img/backButton.png"))); // NOI18N

        SubmitFind.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sister2project/img/find-item-submit.png"))); // NOI18N

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

        ItemFind_1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sister2project/img/items/_frame-default.png"))); // NOI18N

        javax.swing.GroupLayout ContainerLayout = new javax.swing.GroupLayout(Container);
        Container.setLayout(ContainerLayout);
        ContainerLayout.setHorizontalGroup(
            ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 512, Short.MAX_VALUE)
        );
        ContainerLayout.setVerticalGroup(
            ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 334, Short.MAX_VALUE)
        );

        Background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sister2project/img/marketplace.jpg"))); // NOI18N

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap(239, Short.MAX_VALUE)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BackButton)
                    .addComponent(SubmitFind))
                .addGap(236, 236, 236))
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addGap(220, 220, 220)
                .addComponent(TitleHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addComponent(Item6Count, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(74, 74, 74)
                        .addComponent(Item7Count, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Item8Count, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addComponent(Item1Count, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(74, 74, 74)
                        .addComponent(Item2Count, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(77, 77, 77)
                        .addComponent(Item3Count, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(75, 75, 75)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Item4Count, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Item9Count, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(69, 69, 69)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Item10Count, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Item5Count, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(94, Short.MAX_VALUE))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(Background, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                    .addContainerGap(56, Short.MAX_VALUE)
                    .addComponent(Container, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(72, Short.MAX_VALUE)))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addGap(83, 83, 83)
                    .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(Item1_1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jLayeredPane1Layout.createSequentialGroup()
                            .addComponent(Item2_2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(55, 55, 55)
                            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(Item2_3)
                                .addComponent(Item1_2))
                            .addGap(56, 56, 56)
                            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(Item3_1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(Item1_3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(ItemFind_1))
                            .addGap(56, 56, 56)
                            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(Item1_4)
                                .addComponent(Item3_2))
                            .addGap(56, 56, 56)
                            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(Item4_1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(Item2_1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addContainerGap(84, Short.MAX_VALUE)))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TitleHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(105, 105, 105)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Item4Count, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Item5Count, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Item2Count, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Item3Count, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Item1Count, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(82, 82, 82)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Item9Count, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Item10Count, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Item7Count, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Item8Count, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Item6Count, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 108, Short.MAX_VALUE)
                .addComponent(SubmitFind)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BackButton, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(Background, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                    .addContainerGap(56, Short.MAX_VALUE)
                    .addComponent(Container, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(90, Short.MAX_VALUE)))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addGap(95, 95, 95)
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
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 72, Short.MAX_VALUE)
                    .addComponent(ItemFind_1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(96, 96, 96)))
        );
        jLayeredPane1.setLayer(Item1Count, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(Item2Count, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(Item3Count, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(Item4Count, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(Item5Count, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(Item6Count, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(Item7Count, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(Item8Count, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(Item9Count, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(Item10Count, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(TitleHeader, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(BackButton, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(SubmitFind, javax.swing.JLayeredPane.DEFAULT_LAYER);
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
        jLayeredPane1.setLayer(ItemFind_1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(Container, javax.swing.JLayeredPane.DEFAULT_LAYER);
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
    private javax.swing.JLabel ItemFind_1;
    private javax.swing.JLabel SubmitFind;
    private javax.swing.JLabel TitleHeader;
    private javax.swing.JLayeredPane jLayeredPane1;
    // End of variables declaration//GEN-END:variables
}
