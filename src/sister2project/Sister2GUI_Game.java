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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import org.json.JSONException;

/**
 *
 * @author Imburden
 */
public class Sister2GUI_Game extends javax.swing.JPanel implements ActionListener {
    //variables
    private boolean statusJalan = false;
    private int counter = 0;
    private final boolean item[] = new boolean[10];
    private int lastXPos, lastYPos;

    //constants
    private final DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private final ImageIcon kuda[] = new ImageIcon[8];
    private final Timer tm = new Timer(200, this);
    private final String defaultImage[] = new String[10];
    private final String activeImage[] = new String[10];

    /**
     * Creates new form Sister2GUI_Game
     */
    public Sister2GUI_Game() {
        initComponents();
        
        lastXPos = 0; lastYPos = 0;
        
        customizePanels();
        setTooltips();

        tm.start();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {        
        if (Client.getGameScene()) {
            try {
                //Set item count
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
            } catch (JSONException ex) {
                Logger.getLogger(Sister2GUI_Game.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (Client.getLogin()) {                
                //Map set
                int mapWidth = Client.getWidth();
                int mapHeight = Client.getHeight();
                
                Map.setRowHeight(325/mapHeight);
                
                DefaultTableModel tableModel = new DefaultTableModel() {
                    @Override
                    public Class getColumnClass(int column)
                    {
                        return ImageIcon.class;
                    }
                };

                for (int i = 0; i < mapHeight; i++) {
                    tableModel.addColumn("");
                }
                for (int j = 0; j < mapWidth; j++) {
                    tableModel.addRow(new Object[]{new ImageIcon()});
                }

                Map.setModel(tableModel);
                
                Client.setLogin(false);
            }
            Client.setGameScene(false);
        }
        
        setCurrentCounter(dateFormat.format(Client.getTimeLogin().getTime()));
        
        if (statusJalan) {
            Map.setValueAt(kuda[counter], lastXPos, lastYPos);
            counter++;
            if (counter > 7) {
                counter = 0;
            }
            if (Client.getTimeLogin().after(Client.getTimeTravel())) {
                statusJalan = false;
                Map.setValueAt(null, lastXPos, lastYPos);
            }
        }
        else {
            lastXPos = Client.getX(); lastYPos = Client.getY();
            Map.setValueAt(kuda[counter], lastXPos, lastYPos);
        }
        Client.incrementTime();
    }

    public void AddButtonClickEvents(JPanel cards) throws InterruptedException {
        //non-items
        Map.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                ClientMailer.openConnection();
                
                JTable table = (JTable)e.getSource();
                int row = table.getSelectedRow();
                int col = table.getSelectedColumn();
                
                if (row == Client.getX() && col == Client.getY()) {
                    //current place
                    Object[] options = {"Field",
                                        "Move",
                                        "Cancel"};
                    
                    int n = JOptionPane.showOptionDialog(Map,
                        "What would you like to do?",
                        "Choose action",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[2]);

                    switch(n) {
                        case 0:
                            //field
                            Client.Field(Client.getToken());
                            try {
                                Client.setRes(ClientMailer.communicate(Client.getReq().toString()));
                                Client.resField();

                                Client.Inventory(Client.getToken());
                                Client.setRes(ClientMailer.communicate(Client.getReq().toString()));
                                Client.resInventory();
                                
                                JOptionPane.showMessageDialog(Map,
                                    Client.getRes().getString("status"),
                                    "Information",
                                    JOptionPane.INFORMATION_MESSAGE,
                                    null);
                                
                                Client.setGameScene(true);
                            } catch (JSONException ex) {
                                Logger.getLogger(Sister2GUI_Game.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            break;
                        case 1:
                            Client.Move(Client.getToken(), row, col);
                            try {
                                Client.setRes(ClientMailer.communicate(Client.getReq().toString()));
                                Client.resMove();
                                
                                JOptionPane.showMessageDialog(Map,
                                    Client.getRes().getString("status"),
                                    "Information",
                                    JOptionPane.INFORMATION_MESSAGE,
                                    null);
                            } catch (JSONException ex) {
                                Logger.getLogger(Sister2GUI_Game.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            break;
                        case 2:
                            //do nothing
                            break;
                    }
                }
                else {
                    //other place
                    Object[] options = {"Move to " + row + "," + col,
                                        "Cancel"};
                    
                    int n = JOptionPane.showOptionDialog(Map,
                        "What would you like to do?",
                        "Choose action",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[1]);

                    switch(n) {
                        case 0:
                            //move
                            statusJalan = true;
                            Client.Move(Client.getToken(), row, col);
                            try {
                                Client.setRes(ClientMailer.communicate(Client.getReq().toString()));
                                Client.resMove();
                                
                                JOptionPane.showMessageDialog(Map,
                                    Client.getRes().getString("status"),
                                    "Information",
                                    JOptionPane.INFORMATION_MESSAGE,
                                    null);

                                setTravelCounter(dateFormat.format(Client.getTimeTravel().getTime()));
                            } catch (JSONException ex) {
                                Logger.getLogger(Sister2GUI_Game.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            break;
                        case 1:
                            //do nothing
                            break;
                    }
                }

                ClientMailer.closeConnection();
            }
        });
        TradeBox.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                //GAME
                CardLayout cl = (CardLayout)(cards.getLayout());
                cl.show(cards, "Trade Box");
                
                Client.setTradeMenu(true);
            }
        });
        OfferItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                //OFFER ITEM
                CardLayout cl = (CardLayout)(cards.getLayout());
                cl.show(cards, "Offer Item");
            }
        });
        FindItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                //FIND ITEM
                CardLayout cl = (CardLayout)(cards.getLayout());
                cl.show(cards, "Find Item");
            }
        });
        Logout.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                //MAIN MENU
                CardLayout cl = (CardLayout)(cards.getLayout());
                cl.show(cards, "Main Menu");
                
                Client.setOnline(false);
            }
        });
        TransmuteIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                ClientMailer.openConnection();
                
                int mats[] = new int[2], j = 0;
                if (countActives() == 2) {
                    System.out.println("Ada 2");
                    for (int i = 0; i < 10; i++) {
                        if (item[i]) {
                            mats[j] = i;
                            j++;
                        }
                    }
                    Client.MixItem(Client.getToken(), mats[0], mats[1]);
                    try {
                        Client.setRes(ClientMailer.communicate(Client.getReq().toString()));
                        Client.resMixItem();
                        
                        JOptionPane.showMessageDialog(Map,
                            Client.getRes().getString("status"),
                            "Information",
                            JOptionPane.INFORMATION_MESSAGE,
                            null);
                        
                        Client.Inventory(Client.getToken());
                        Client.setRes(ClientMailer.communicate(Client.getReq().toString()));
                        Client.resInventory();
                    } catch (JSONException ex) {
                        Logger.getLogger(Sister2GUI_Game.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    Client.setGameScene(true);
                    setAllToDefault();
                }
                
                ClientMailer.closeConnection();
            }
        });

        //items
        Item1_1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                ImageIcon icon;
                if (!item[0]) { //not active
                    icon = new ImageIcon(getClass().getResource(activeImage[0]));
                }
                else { //active
                    icon = new ImageIcon(getClass().getResource(defaultImage[0]));
                }
                item[0] = !item[0];
                Item1_1.setIcon(icon);
            }
        });
        Item1_2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                ImageIcon icon;
                if (!item[1]) { //not active
                    icon = new ImageIcon(getClass().getResource(activeImage[1]));
                }
                else { //active
                    icon = new ImageIcon(getClass().getResource(defaultImage[1]));
                }
                item[1] = !item[1];
                Item1_2.setIcon(icon);
            }
        });
        Item1_3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                ImageIcon icon;
                if (!item[2]) { //not active
                    icon = new ImageIcon(getClass().getResource(activeImage[2]));
                }
                else { //active
                    icon = new ImageIcon(getClass().getResource(defaultImage[2]));
                }
                item[2] = !item[2];
                Item1_3.setIcon(icon);
            }
        });
        Item1_4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                ImageIcon icon;
                if (!item[3]) { //not active
                    icon = new ImageIcon(getClass().getResource(activeImage[3]));
                }
                else { //active
                    icon = new ImageIcon(getClass().getResource(defaultImage[3]));
                }
                item[3] = !item[3];
                Item1_4.setIcon(icon);
            }
        });
        Item2_1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                ImageIcon icon;
                if (!item[4]) { //not active
                    icon = new ImageIcon(getClass().getResource(activeImage[4]));
                }
                else { //active
                    icon = new ImageIcon(getClass().getResource(defaultImage[4]));
                }
                item[4] = !item[4];
                Item2_1.setIcon(icon);
            }
        });
        Item2_2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                ImageIcon icon;
                if (!item[5]) { //not active
                    icon = new ImageIcon(getClass().getResource(activeImage[5]));
                }
                else { //active
                    icon = new ImageIcon(getClass().getResource(defaultImage[5]));
                }
                item[5] = !item[5];
                Item2_2.setIcon(icon);
            }
        });
        Item2_3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                ImageIcon icon;
                if (!item[6]) { //not active
                    icon = new ImageIcon(getClass().getResource(activeImage[6]));
                }
                else { //active
                    icon = new ImageIcon(getClass().getResource(defaultImage[6]));
                }
                item[6] = !item[6];
                Item2_3.setIcon(icon);
            }
        });
        Item3_1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                ImageIcon icon;
                if (!item[7]) { //not active
                    icon = new ImageIcon(getClass().getResource(activeImage[7]));
                }
                else { //active
                    icon = new ImageIcon(getClass().getResource(defaultImage[7]));
                }
                item[7] = !item[7];
                Item3_1.setIcon(icon);
            }
        });
        Item3_2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                ImageIcon icon;
                if (!item[8]) { //not active
                    icon = new ImageIcon(getClass().getResource(activeImage[8]));
                }
                else { //active
                    icon = new ImageIcon(getClass().getResource(defaultImage[8]));
                }
                item[8] = !item[8];
                Item3_2.setIcon(icon);
            }
        });
        Item4_1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                ImageIcon icon;
                if (!item[9]) { //not active
                    icon = new ImageIcon(getClass().getResource(activeImage[9]));
                }
                else { //active
                    icon = new ImageIcon(getClass().getResource(defaultImage[9]));
                }
                item[9] = !item[9];
                Item4_1.setIcon(icon);
            }
        });
    }

    public final void customizePanels() {
        Map.setCellSelectionEnabled(false);
        jPanel1.setBackground(new Color(202, 202, 202, 225));
        jPanel2.setBackground(new Color(202, 202, 202, 225));

        kuda[0] = new ImageIcon(getClass().getResource("img/horse1.png"));
        kuda[1] = new ImageIcon(getClass().getResource("img/horse2.png"));
        kuda[2] = new ImageIcon(getClass().getResource("img/horse3.png"));
        kuda[3] = new ImageIcon(getClass().getResource("img/horse4.png"));
        kuda[4] = new ImageIcon(getClass().getResource("img/horse5.png"));
        kuda[5] = new ImageIcon(getClass().getResource("img/horse6.png"));
        kuda[6] = new ImageIcon(getClass().getResource("img/horse7.png"));
        kuda[7] = new ImageIcon(getClass().getResource("img/horse8.png"));

        for (int i=0; i<10; i++) {
            item[i] = false;
        }

        defaultImage[0] = "img/items/honey-small.png"; activeImage[0] = "img/items/honey-small-active.png";
        defaultImage[1] = "img/items/herb-small.png"; activeImage[1] = "img/items/herb-small-active.png";
        defaultImage[2] = "img/items/clay-small.png"; activeImage[2] = "img/items/clay-small-active.png";
        defaultImage[3] = "img/items/mineral-small.png"; activeImage[3] = "img/items/mineral-small-active.png";
        defaultImage[4] = "img/items/potion-small.png"; activeImage[4] = "img/items/potion-small-active.png";
        defaultImage[5] = "img/items/incense-small.png"; activeImage[5] = "img/items/incense-small-active.png";
        defaultImage[6] = "img/items/gem-small.png"; activeImage[6] = "img/items/gem-small-active.png";
        defaultImage[7] = "img/items/life-elixir-small.png"; activeImage[7] = "img/items/life-elixir-small-active.png";
        defaultImage[8] = "img/items/mana-crystal-small.png"; activeImage[8] = "img/items/mana-crystal-small-active.png";
        defaultImage[9] = "img/items/philosopher-small.png"; activeImage[9] = "img/items/philosopher-small-active.png";
    }

    public void setAllToDefault() {
        Item1_1.setIcon(new ImageIcon(getClass().getResource(defaultImage[0])));
        Item1_2.setIcon(new ImageIcon(getClass().getResource(defaultImage[1])));
        Item1_3.setIcon(new ImageIcon(getClass().getResource(defaultImage[2])));
        Item1_4.setIcon(new ImageIcon(getClass().getResource(defaultImage[3])));
        Item2_1.setIcon(new ImageIcon(getClass().getResource(defaultImage[4])));
        Item2_2.setIcon(new ImageIcon(getClass().getResource(defaultImage[5])));
        Item2_3.setIcon(new ImageIcon(getClass().getResource(defaultImage[6])));
        Item3_1.setIcon(new ImageIcon(getClass().getResource(defaultImage[7])));
        Item3_2.setIcon(new ImageIcon(getClass().getResource(defaultImage[8])));
        Item4_1.setIcon(new ImageIcon(getClass().getResource(defaultImage[9])));
        
        for (int i = 0; i < 10; i++) {
            item[i] = false;
        }
    }

    public int countActives() {
        int count = 0;
        for (int i = 0; i < 10; i++) {
            if (item[i]) count++;
        }
        return count;
    }

    public final void setTooltips() {
        TitleHeader.setToolTipText("Map name");

        Item1_1.setToolTipText("Honey"); Item1_2.setToolTipText("Herb");
        Item1_3.setToolTipText("Clay"); Item1_4.setToolTipText("Mineral");
        Item2_1.setToolTipText("Potion"); Item2_2.setToolTipText("Incense");
        Item2_3.setToolTipText("Gem");
        Item3_1.setToolTipText("Life Elixir"); Item3_2.setToolTipText("Mana Crystal");
        Item4_1.setToolTipText("Philosopher's Stone");

        TransmuteIcon.setToolTipText("Mix Two 3x3 Items");
    }

    public void setCurrentCounter(String date) {
        CTCounter.setText(date);
    }

    public void setTravelCounter (String time) {
        ATCounter.setText(time);
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
        jLayeredPane2 = new javax.swing.JLayeredPane();
        Inventory = new javax.swing.JLabel();
        Item1_1 = new javax.swing.JLabel();
        Item1_2 = new javax.swing.JLabel();
        Item1_4 = new javax.swing.JLabel();
        Item1_3 = new javax.swing.JLabel();
        Item2_1 = new javax.swing.JLabel();
        Item2_2 = new javax.swing.JLabel();
        Item2_3 = new javax.swing.JLabel();
        Item4_1 = new javax.swing.JLabel();
        Item3_1 = new javax.swing.JLabel();
        Item3_2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        TransmuteIcon = new javax.swing.JLabel();
        Item1Count = new javax.swing.JLabel();
        Item2Count = new javax.swing.JLabel();
        Item3Count = new javax.swing.JLabel();
        Item4Count = new javax.swing.JLabel();
        Item5Count = new javax.swing.JLabel();
        Item6Count = new javax.swing.JLabel();
        Item7Count = new javax.swing.JLabel();
        Item10Count = new javax.swing.JLabel();
        Item8Count = new javax.swing.JLabel();
        Item9Count = new javax.swing.JLabel();
        jLayeredPane3 = new javax.swing.JLayeredPane();
        FindItem = new javax.swing.JLabel();
        OfferItem = new javax.swing.JLabel();
        ArrivalTime = new javax.swing.JLabel();
        TradeBox = new javax.swing.JLabel();
        CurrentTime = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        CTCounter = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        ATCounter = new javax.swing.JLabel();
        TitleHeader = new javax.swing.JLabel();
        Logout = new javax.swing.JLabel();
        Map = new javax.swing.JTable();
        Background = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(640, 480));

        jLayeredPane1.setPreferredSize(new java.awt.Dimension(640, 480));

        Inventory.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sister2project/img/inventory.png"))); // NOI18N

        Item1_1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sister2project/img/items/honey-small.png"))); // NOI18N

        Item1_2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sister2project/img/items/herb-small.png"))); // NOI18N

        Item1_4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sister2project/img/items/mineral-small.png"))); // NOI18N

        Item1_3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sister2project/img/items/clay-small.png"))); // NOI18N

        Item2_1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sister2project/img/items/potion-small.png"))); // NOI18N

        Item2_2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sister2project/img/items/incense-small.png"))); // NOI18N

        Item2_3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sister2project/img/items/gem-small.png"))); // NOI18N

        Item4_1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sister2project/img/items/philosopher-small.png"))); // NOI18N

        Item3_1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sister2project/img/items/life-elixir-small.png"))); // NOI18N

        Item3_2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sister2project/img/items/mana-crystal-small.png"))); // NOI18N

        TransmuteIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sister2project/img/transmute-item.png"))); // NOI18N
        TransmuteIcon.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TransmuteIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(TransmuteIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        Item1Count.setForeground(new java.awt.Color(255, 255, 255));
        Item1Count.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        Item2Count.setForeground(new java.awt.Color(255, 255, 255));
        Item2Count.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        Item3Count.setForeground(new java.awt.Color(255, 255, 255));
        Item3Count.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        Item4Count.setForeground(new java.awt.Color(255, 255, 255));
        Item4Count.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        Item5Count.setForeground(new java.awt.Color(255, 255, 255));
        Item5Count.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        Item6Count.setForeground(new java.awt.Color(255, 255, 255));
        Item6Count.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        Item7Count.setForeground(new java.awt.Color(255, 255, 255));
        Item7Count.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        Item10Count.setForeground(new java.awt.Color(255, 255, 255));
        Item10Count.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        Item8Count.setForeground(new java.awt.Color(255, 255, 255));
        Item8Count.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        Item9Count.setForeground(new java.awt.Color(255, 255, 255));
        Item9Count.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jLayeredPane2Layout = new javax.swing.GroupLayout(jLayeredPane2);
        jLayeredPane2.setLayout(jLayeredPane2Layout);
        jLayeredPane2Layout.setHorizontalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Inventory, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(jLayeredPane2Layout.createSequentialGroup()
                .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLayeredPane2Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jLayeredPane2Layout.createSequentialGroup()
                                .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jLayeredPane2Layout.createSequentialGroup()
                                        .addComponent(Item3_1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(Item3_2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jLayeredPane2Layout.createSequentialGroup()
                                        .addComponent(Item8Count, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(Item9Count, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jLayeredPane2Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Item4_1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jLayeredPane2Layout.createSequentialGroup()
                                        .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(Item10Count, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 0, Short.MAX_VALUE))))))
                    .addGroup(jLayeredPane2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Item5Count, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Item2_1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Item2_2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Item6Count, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Item2_3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Item7Count, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(16, 16, 16))
            .addGroup(jLayeredPane2Layout.createSequentialGroup()
                .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Item1_1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Item1Count, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Item2Count, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Item1_2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Item1_3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Item3Count, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Item1_4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(Item4Count, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jLayeredPane2Layout.setVerticalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane2Layout.createSequentialGroup()
                .addComponent(Inventory, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Item1_4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Item1_3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(Item1_1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(Item1_2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Item1Count, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                    .addComponent(Item3Count, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Item2Count, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Item4Count, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Item2_1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Item2_2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Item2_3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Item5Count, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Item7Count, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Item6Count, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Item3_1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Item3_2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Item8Count, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Item9Count, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Item4_1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Item10Count, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );
        jLayeredPane2.setLayer(Inventory, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(Item1_1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(Item1_2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(Item1_4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(Item1_3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(Item2_1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(Item2_2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(Item2_3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(Item4_1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(Item3_1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(Item3_2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jPanel3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(Item1Count, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(Item2Count, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(Item3Count, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(Item4Count, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(Item5Count, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(Item6Count, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(Item7Count, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(Item10Count, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(Item8Count, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(Item9Count, javax.swing.JLayeredPane.DEFAULT_LAYER);

        FindItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sister2project/img/find-item.png"))); // NOI18N

        OfferItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sister2project/img/offer-item.png"))); // NOI18N

        ArrivalTime.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sister2project/img/travel-time.png"))); // NOI18N

        TradeBox.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sister2project/img/trade-box.png"))); // NOI18N

        CurrentTime.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sister2project/img/current-time.png"))); // NOI18N

        CTCounter.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        CTCounter.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 108, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(CTCounter, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 32, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(CTCounter, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE))
        );

        ATCounter.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ATCounter.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(ATCounter, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ATCounter, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jLayeredPane3Layout = new javax.swing.GroupLayout(jLayeredPane3);
        jLayeredPane3.setLayout(jLayeredPane3Layout);
        jLayeredPane3Layout.setHorizontalGroup(
            jLayeredPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TradeBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(OfferItem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jLayeredPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(FindItem, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(CurrentTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ArrivalTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jLayeredPane3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jLayeredPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jLayeredPane3Layout.setVerticalGroup(
            jLayeredPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane3Layout.createSequentialGroup()
                .addComponent(CurrentTime, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ArrivalTime, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 15, Short.MAX_VALUE)
                .addComponent(TradeBox)
                .addGap(30, 30, 30)
                .addComponent(OfferItem, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(FindItem, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
        );
        jLayeredPane3.setLayer(FindItem, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane3.setLayer(OfferItem, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane3.setLayer(ArrivalTime, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane3.setLayer(TradeBox, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane3.setLayer(CurrentTime, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane3.setLayer(jPanel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane3.setLayer(jPanel2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        Logout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sister2project/img/logout.png"))); // NOI18N

        Map.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "", "", "", "", ""
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Map.setFocusable(false);
        Map.setRowHeight(65);
        Map.setRowSelectionAllowed(false);

        Background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sister2project/img/game-background-blur.jpg"))); // NOI18N

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Logout, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addComponent(jLayeredPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(TitleHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Map, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addComponent(Background)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TitleHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLayeredPane2)
                        .addComponent(jLayeredPane3, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addComponent(Map, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addComponent(Logout, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(Background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jLayeredPane1.setLayer(jLayeredPane2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLayeredPane3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(TitleHeader, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(Logout, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(Map, javax.swing.JLayeredPane.DEFAULT_LAYER);

        if (Map.getColumnModel().getColumnCount() > 0) {
            Map.getColumnModel().getColumn(0).setResizable(false);
            Map.getColumnModel().getColumn(1).setResizable(false);
            Map.getColumnModel().getColumn(2).setResizable(false);
            Map.getColumnModel().getColumn(3).setResizable(false);
            Map.getColumnModel().getColumn(4).setResizable(false);
        }
        jLayeredPane1.setLayer(Background, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ATCounter;
    private javax.swing.JLabel ArrivalTime;
    private javax.swing.JLabel Background;
    private javax.swing.JLabel CTCounter;
    private javax.swing.JLabel CurrentTime;
    private javax.swing.JLabel FindItem;
    private javax.swing.JLabel Inventory;
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
    private javax.swing.JLabel Logout;
    private javax.swing.JTable Map;
    private javax.swing.JLabel OfferItem;
    private javax.swing.JLabel TitleHeader;
    private javax.swing.JLabel TradeBox;
    private javax.swing.JLabel TransmuteIcon;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JLayeredPane jLayeredPane3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    // End of variables declaration//GEN-END:variables
}
