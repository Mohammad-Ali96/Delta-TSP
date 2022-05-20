package delta_tsp;

import static delta_tsp.Draw_Graph.GraphPanel.nodeList;
import static delta_tsp.Draw_Graph.GraphPanel.num;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;

public class DELTA_TSP {

    static class point {

        public double x;
        public double y;

        public point(double a, double b) {
            this.x = a;
            this.y = b;
        }

        public static double distance(point p1, point p2) {
            return Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y));
        }
    }

    static class TSP extends JPanel {

// Components of main JFrame
        private JPanel ControlPanel;

        private JPanel Drawing;
        private JButton inputGraph;
        private JButton showGraph;
        private JButton CheckRules;
        private JButton SolveAutomatic;
        private JButton SolveManually;
        private JButton Clear;
        private JButton Exit;
        private JButton AboutTSP;

        private JLabel check;
        private JLabel solution;
        private JLabel path;
        private JRadioButton DrawGraph;
        private JRadioButton GenerateRandomGraph;
        private JRadioButton InputGraphFromFile;
        private JRadioButton InputGraphManually;
        private ButtonGroup InputGroup;
        private ButtonGroup algoGroup;
        public String msg = "";
        public String msg3 = "";
        public int cnt = 0;

        private JCheckBox Complete;
        private JCheckBox Triangle;
        private JSeparator sperater1;
        private JSeparator sperater2;
        private JRadioButton optimal2;
        private JRadioButton optimal2_3;
        private JRadioButton optimal;
        private JLabel img;

        public int[][] adj_Matrix;
        public static int numberOfNodes;
        boolean triange = false;
        boolean complete = false;
        boolean Solveoptimal2 = false;
        boolean Solveoptimal3 = false;
        final int c0 = 550;
        final int c1 = 300;
        final int r0 = 300;
        point[] GraphPoints;
        int r2;

        class InputAdjMatrix extends JFrame {

            // Components of input adjacency matrix
            private JLabel labelnumofnodes;
            private JButton makeAdgMatrix;
            private JButton saveAdgMatrix;

            private JTextField textNumNodes;
            private JSeparator sep1;
            private JSeparator sep2;
            private JLabel labelAdj;
            private JTable table;

            public InputAdjMatrix() {

                this.setSize(800, 600);
                this.setVisible(true);
                this.setResizable(false);
                this.setTitle("Input Graph");
                this.setLocationRelativeTo(null);
                this.setLayout(null);
                labelnumofnodes = new JLabel("input the number of nodes : ");
                labelnumofnodes.setFont(new Font("Helvetica", Font.PLAIN, 16));

                makeAdgMatrix = new JButton("Create Adjacency Matrix");
                makeAdgMatrix.setToolTipText("Create Adjacency Matrix with size n");
                makeAdgMatrix.setFont(new Font("Helvetica", Font.PLAIN, 16));
                makeAdgMatrix.setBackground(Color.LIGHT_GRAY);

                saveAdgMatrix = new JButton("Save Adjacency Matrix");
                saveAdgMatrix.setToolTipText("Save The Graph That Inout");
                saveAdgMatrix.setFont(new Font("Helvetica", Font.PLAIN, 16));
                saveAdgMatrix.setBackground(Color.LIGHT_GRAY);

                textNumNodes = new JTextField();
                sep1 = new JSeparator();
                sep2 = new JSeparator();
                labelAdj = new JLabel("Adjacency Matrix");
                labelAdj.setFont(new Font("Helvetica", Font.ITALIC, 25));

                table = new JTable();

                String Title[] = new String[1];
                String[][] MemberTable = new String[0][1];
                double[][] Parameter = new double[0][1];

                Title[0] = "Nodes";

                table = new JTable(MemberTable, Title);

                table.setFillsViewportHeight(true);

                JScrollPane scroll = new JScrollPane(table);

                this.add(scroll);
                //this.add(Table);

                scroll.setBounds(5, 200, 785, 250);

                this.add(makeAdgMatrix);
                this.add(saveAdgMatrix);
                this.add(labelnumofnodes);
                this.add(textNumNodes);
                this.add(sep1);
                this.add(sep2);
                this.add(labelAdj);

                labelnumofnodes.setBounds(270, 10, 250, 30);
                textNumNodes.setBounds(470, 10, 81, 30);
                makeAdgMatrix.setBounds(270, 50, 280, 30);
                saveAdgMatrix.setBounds(270, 87, 280, 30);
                sep1.setBounds(2, 135, 798, 10);
                labelAdj.setBounds(310, 140, 280, 40);
                sep2.setBounds(289, 180, 230, 10);

                makeAdgMatrix.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String ss = textNumNodes.getText();
                        numberOfNodes = Integer.parseInt(ss);

                        String[] Title = new String[numberOfNodes + 1];
                        String[][] MemberTable = new String[numberOfNodes][numberOfNodes + 1];

                        Title[0] = String.valueOf("nodes");
                        for (int i = 1; i <= numberOfNodes; i++) {
                            Title[i] = String.valueOf(i);
                        }

                        for (int i = 0; i < numberOfNodes; i++) {
                            MemberTable[i][0] = String.valueOf(i + 1);
                        }

                        table.setModel(new DefaultTableModel(MemberTable, Title));

                    }
                });

                saveAdgMatrix.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        adj_Matrix = new int[numberOfNodes + 1][numberOfNodes + 1];
                        initAdjMatrix();
                        try {
                            GraphPoints = new point[numberOfNodes];
                            for (int i = 0; i < numberOfNodes; i++) {
                                for (int j = 1; j <= numberOfNodes; j++) {
                                    adj_Matrix[i + 1][j] = Integer.parseInt(String.valueOf(table.getValueAt(i, j)));
                                }

                            }
                        } catch (Exception exception) {
                            System.out.println(exception);
                            //numberOfNodes = 0;
                            JOptionPane.showMessageDialog(null, "Error Command :\n"
                                    + "Please Fill The adjacency matrix first then click save adjacency matrix", "Problem", JOptionPane.ERROR_MESSAGE);

                        }
                    }
                });

            }

        }

        public TSP() {

            this.setLayout(null);

            setPreferredSize(new Dimension(1300, 660));
            this.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.blue));
            setBackground(Color.white);

            // Components of ControlPanel
            ControlPanel = new JPanel();
            ControlPanel.setVisible(true);
            ControlPanel.setLayout(null);

            img = new JLabel();

            URL iconImage = getClass().getResource("images/photo_1.png");
            ImageIcon image = new ImageIcon(iconImage);
            img.setIcon(image);
            img.setVisible(true);

            //nodeList = new ArrayList<Point>();
            // Buttons and Radio Buttons
            inputGraph = new JButton(" input the Graph ");
            inputGraph.setToolTipText("Select First The Type of input the Graph");
            inputGraph.setFont(new Font("Helvetica", Font.PLAIN, 16));
            inputGraph.setBackground(Color.LIGHT_GRAY);
            //inputGraph.setEnabled(false);

            showGraph = new JButton("show Graph");
            showGraph.setToolTipText("Show the Graph");
            showGraph.setFont(new Font("Helvetica", Font.PLAIN, 14));
            showGraph.setBackground(Color.LIGHT_GRAY);
            showGraph.setEnabled(false);

            showGraph.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    CheckRules.setEnabled(true);

                    if (Draw_Graph.GraphPanel.savePressed) {
                        numberOfNodes = Draw_Graph.GraphPanel.num;
                        GraphPoints = new point[numberOfNodes];
                        adj_Matrix = new int[numberOfNodes + 1][numberOfNodes + 1];
                        initAdjMatrix();
                        // adj_Matrix = Arrays.copyOf(Draw_Graph.GraphPanel.adj_Matrix, Draw_Graph.GraphPanel.adj_Matrix.length);

                        for (int i = 1; i <= numberOfNodes; i++) {
                            for (int j = 1; j <= numberOfNodes; j++) {
                                adj_Matrix[i][j] = (int) Draw_Graph.GraphPanel.adj_Matrix[i][j];
                            }
                        }

                    }
                    img.setVisible(false);
                    if (numberOfNodes >= 80) {
                        r2 = 18;
                    } else if (numberOfNodes >= 55) {
                        r2 = 20;
                    } else if (numberOfNodes >= 40) {
                        r2 = 30;
                    } else {
                        r2 = 50;
                    }
                    fillGraphPoints();
                    repaint();

                }
            });

            inputGraph.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    inputGraph.setEnabled(false);
                    showGraph.setEnabled(true);
                    if (DrawGraph.isSelected()) {
                        Draw_Graph D_G = new Draw_Graph();
                        D_G.setVisible(true);
                    } else if (GenerateRandomGraph.isSelected()) {

                        String n = JOptionPane.showInputDialog("Please Input the number of nodes ");
                        numberOfNodes = Integer.valueOf(n);
                        GenerateRandomGraphAdjMatrix(numberOfNodes);

                    } else if (InputGraphManually.isSelected()) {

                        InputAdjMatrix input = new InputAdjMatrix();

                    } else if (InputGraphFromFile.isSelected()) {

                    }

                }

            });

            DrawGraph = new JRadioButton("Draw Graph");
            DrawGraph.setToolTipText("Draw Graph");
            DrawGraph.setFont(new Font("Helvetica", Font.PLAIN, 14));

            GenerateRandomGraph = new JRadioButton("Generate Random Graph");
            GenerateRandomGraph.setToolTipText("Generate Random Graph depend on points");
            GenerateRandomGraph.setFont(new Font("Helvetica", Font.PLAIN, 14));

            InputGraphManually = new JRadioButton("Input Graph Manually");
            InputGraphManually.setToolTipText("Input Graph Manually");
            InputGraphManually.setFont(new Font("Helvetica", Font.PLAIN, 14));

            InputGraphFromFile = new JRadioButton("Input Graph From File");
            InputGraphFromFile.setToolTipText("Select File that you want read from its");
            InputGraphFromFile.setFont(new Font("Helvetica", Font.PLAIN, 14));

            InputGroup = new ButtonGroup();
            InputGroup.add(InputGraphFromFile);
            InputGroup.add(InputGraphManually);
            InputGroup.add(GenerateRandomGraph);
            InputGroup.add(DrawGraph);

            JPanel inpuPanel = new JPanel();
            inpuPanel.setBorder(javax.swing.BorderFactory.
                    createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(),
                            "Types Of Input Graph", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                            javax.swing.border.TitledBorder.TOP, new java.awt.Font("Helvetica", 0, 18)));

            DrawGraph.setSelected(true);

            check = new JLabel("Verify If Graph achieves following properties:");
            check.setFont(new Font("Helvetica", Font.BOLD, 13));

            /*JPanel checkPanel = new JPanel();
             checkPanel.setBorder(javax.swing.BorderFactory.
             createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(),
             "Check Rule", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
             javax.swing.border.TitledBorder.TOP, new java.awt.Font("Helvetica", 0, 20)));
             */
            CheckRules = new JButton(" Check Rules ");
            CheckRules.setToolTipText("Select the rules then click this button");
            CheckRules.setFont(new Font("Helvetica", Font.PLAIN, 16));
            CheckRules.setBackground(Color.LIGHT_GRAY);
            CheckRules.setEnabled(false);

            CheckRules.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    if (Triangle.isSelected()) {
                        if (checkTriangleEquation()) {
                            triange = true;
                            JOptionPane.showMessageDialog(null, "The Graph Achieves The Triangle Equation");

                        }
                    }

                    if (Complete.isSelected()) {
                        if (checkIfGraphIsComplete()) {
                            complete = true;
                            JOptionPane.showMessageDialog(null, "The Graph is Complete");

                        }
                    }
                    if (!Triangle.isSelected() && !Complete.isSelected()) {

                        JOptionPane.showMessageDialog(null, "Please Select What do you want to check");
                    }

                    if (triange && complete) {
                        SolveAutomatic.setEnabled(true);
                        SolveManually.setEnabled(true);
                    }

                }
            });

            Triangle = new JCheckBox("Triangle Equation");

            Triangle.setToolTipText("Check if the graph achieves the triangle equation");
            //Triangle.setFont(new Font("Helvetica", Font.PLAIN, 14));

            Complete = new JCheckBox("Complete Graph");

            Complete.setToolTipText("Check if the graph is complete");
            //Complete.setFont(new Font("Helvetica", Font.PLAIN, 14));

            sperater1 = new JSeparator();
            sperater2 = new JSeparator();
            sperater1.setBackground(Color.DARK_GRAY);
            sperater2.setBackground(Color.DARK_GRAY);

            solution = new JLabel("Approximate Solution For TSP:");
            solution.setFont(new Font("Helvetica", Font.PLAIN, 19));

            path = new JLabel();
            path.setFont(new Font("Helvetica", Font.PLAIN, 15));
            path.setVisible(true);

            optimal2 = new JRadioButton("optimal2");
            optimal2.setToolTipText("optimal2");
            optimal2.setFont(new Font("Helvetica", Font.BOLD, 15));

            optimal2_3 = new JRadioButton("optimal2_3");
            optimal2_3.setToolTipText("optimal2_3");
            optimal2_3.setFont(new Font("Helvetica", Font.BOLD, 15));

            optimal = new JRadioButton("optimal");
            optimal.setToolTipText("optimal");
            optimal.setFont(new Font("Helvetica", Font.BOLD, 15));

            algoGroup = new ButtonGroup();
            algoGroup.add(optimal);
            algoGroup.add(optimal2);
            algoGroup.add(optimal2_3);

            JPanel algoPanel = new JPanel();
            algoPanel.setBorder(javax.swing.BorderFactory.
                    createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(),
                            "Algorithm Of TSP", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                            javax.swing.border.TitledBorder.TOP, new java.awt.Font("Helvetica", 0, 20)));

            SolveAutomatic = new JButton("Find Path Automatically");
            SolveAutomatic.setToolTipText("Click here to find the aprroximate solution automatically");
            SolveAutomatic.setFont(new Font("Helvetica", Font.PLAIN, 16));
            SolveAutomatic.setBackground(Color.LIGHT_GRAY);
            SolveAutomatic.setEnabled(false);

            SolveManually = new JButton("Show The Basic Graph");
            SolveManually.setToolTipText("Click here to find the aprroximate solution manually");
            SolveManually.setFont(new Font("Helvetica", Font.PLAIN, 16));
            SolveManually.setBackground(Color.LIGHT_GRAY);
            SolveManually.setEnabled(false);

            Clear = new JButton("Clear");
            Clear.setToolTipText("Clear and input new Graph");
            Clear.setFont(new Font("Helvetica", Font.PLAIN, 16));
            Clear.setBackground(Color.LIGHT_GRAY);
            Clear.setEnabled(true);

            Exit = new JButton("Exit");
            Exit.setToolTipText("Exit From Project");
            Exit.setFont(new Font("Helvetica", Font.PLAIN, 16));
            Exit.setBackground(Color.LIGHT_GRAY);
            Exit.setEnabled(true);

            Exit.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });

            Clear.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    adj_Matrix = new int[0][0];
                    inputGraph.setEnabled(true);
                    showGraph.setEnabled(false);
                    GraphPoints = new point[0];
                    numberOfNodes = 0;
                    num = 0;
                    SolveAutomatic.setEnabled(false);
                    SolveManually.setEnabled(false);
                    DrawGraph.setSelected(true);
                    Triangle.setSelected(false);
                    Complete.setSelected(false);
                    Draw_Graph.GraphPanel.savePressed = false;
                    Draw_Graph.GraphPanel.adj_Matrix = new double[0][0];
                    Draw_Graph.GraphPanel.num = 0;
                    triange = false;
                    complete = false;
                    Solveoptimal2 = false;
                    adj_Matrix = new int[0][0];
                    Adj_After_MST = new ArrayList[0];
                    Find_TSP_Path = false;

                    Adj_After_MST = new ArrayList[0];
                    Adj_After_MST_Duplicate = new ArrayList[0];
                    TSP_Path = new ArrayList<>();
                    visited = new boolean[0];
                    node_in_path = new boolean[0];
                    key = new int[0];
                    parent = new int[0];

                    cost = 0;
                    msg_Path = "";
                    cost_Path = 0;
                    Find_TSP_Path = false;

                    Adj_After_MST3 = new ArrayList[0];
                    Adj_After_Matching = new ArrayList[0];
                    Matching = new ArrayList[0];
                    TSP_Path3 = new ArrayList<>();
                    Tmp_For_Match = new ArrayList<>();
                    visited3 = new boolean[0];
                    node_in_path3 = new boolean[0];
                    Odd_Degree = new boolean[0];
                    key3 = new int[0];
                    parent3 = new int[0];
                    cost3 = 0;
                    msg_Path3 = "";
                    cost_Path3 = 0;
                    Solveoptimal2 = false;
                    Solveoptimal3 = false;
                    Find_TSP_Path = false;

                    repaint();
                    img.setVisible(true);

                }
            });
            
            
            SolveManually.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    Find_TSP_Path = false;
                    Solveoptimal2 = false;
                    Solveoptimal3 = false;
                    SolveAutomatic.setEnabled(true);
                    
                    repaint();
                    
                    }
            });
            

            SolveAutomatic.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    if (optimal.isSelected()) {

                    } else if (optimal2.isSelected()) {
                        showGraph.setEnabled(false);
                        CheckRules.setEnabled(false);
                        //SolveManually.setEnabled(false);
                        SolveAutomatic.setEnabled(false);
                        Solveoptimal2 = true;
                        Solveoptimal3 = false;

                        SolveOptimalSolution2();

                    } else if (optimal2_3.isSelected()) {

                        Solveoptimal2 = false;
                        Solveoptimal3 = true;

                        showGraph.setEnabled(false);
                        CheckRules.setEnabled(false);
                        //SolveManually.setEnabled(false);
                        SolveAutomatic.setEnabled(false);
                        SolveOptimalSolution3();

                    } else {
                        JOptionPane.showMessageDialog(null, "Please Select The algorithm to Find The Path");
                    }

                }
            });

            AboutTSP = new JButton("About TSP Project");
            AboutTSP.setToolTipText("information about program and programer");
            AboutTSP.setFont(new Font("Helvetica", Font.PLAIN, 16));
            AboutTSP.setBackground(Color.LIGHT_GRAY);
            AboutTSP.setEnabled(true);

            // ADD Components to Control Panel;
            this.add(ControlPanel);

            //this.add(Drawing);
            ControlPanel.add(inputGraph);
            ControlPanel.add(showGraph);
            ControlPanel.add(CheckRules);
            ControlPanel.add(solution);
            ControlPanel.add(DrawGraph);
            ControlPanel.add(GenerateRandomGraph);
            ControlPanel.add(InputGraphManually);
            ControlPanel.add(InputGraphFromFile);
            ControlPanel.add(inpuPanel);
            ControlPanel.add(check);
            //ControlPanel.add(checkPanel);
            ControlPanel.add(Complete);
            ControlPanel.add(Triangle);
            ControlPanel.add(sperater1);
            ControlPanel.add(sperater2);
            ControlPanel.add(optimal);
            ControlPanel.add(optimal2);
            ControlPanel.add(optimal2_3);
            ControlPanel.add(algoPanel);
            ControlPanel.add(SolveAutomatic);
            ControlPanel.add(SolveManually);
            ControlPanel.add(Clear);
            ControlPanel.add(Exit);
            ControlPanel.add(AboutTSP);
            this.add(img);
            this.add(path);

            // Detrmine Coordinates and size for each component
            ControlPanel.setBounds(1006, 5, 300, 658);
            //Drawing.setBounds(5, 5, 700, 658);
            //input.setBounds(715, 20, 280, 40);
            inputGraph.setBounds(10, 10, 280, 30);
            showGraph.setBounds(100, 175, 120, 25);
            CheckRules.setBounds(10, 290, 280, 30);
            SolveAutomatic.setBounds(10, 475, 280, 30);
            SolveManually.setBounds(10, 508, 280, 30);
            Clear.setBounds(10, 543, 280, 30);
            Exit.setBounds(10, 578, 280, 30);
            AboutTSP.setBounds(10, 613, 280, 30);

            DrawGraph.setBounds(15, 75, 200, 20);
            GenerateRandomGraph.setBounds(15, 95, 200, 20);
            InputGraphManually.setBounds(15, 115, 200, 20);
            InputGraphFromFile.setBounds(15, 135, 200, 20);
            check.setBounds(5, 205, 300, 40);
            solution.setBounds(10, 330, 300, 30);

            Complete.setBounds(15, 238, 200, 20);
            Triangle.setBounds(15, 258, 200, 20);
            path.setBounds(15, 15, 1000, 200);

            inpuPanel.setLocation(10, 50);
            inpuPanel.setLocation(10, 50);
            inpuPanel.setSize(210, 120);

            //checkPanel.setLocation(10, 200);
            //checkPanel.setSize(210, 80);
            sperater1.setBounds(2, 205, 300, 10);
            sperater2.setBounds(2, 330, 300, 10);

            optimal.setBounds(15, 385, 200, 20);
            optimal2.setBounds(15, 405, 200, 20);
            optimal2_3.setBounds(15, 425, 200, 20);
            img.setBounds(250, 40, 600, 630);
            algoPanel.setLocation(10, 360);
            algoPanel.setSize(210, 100);

        }

        ///////////////////////////////////////////////////////
        public void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            graphics.setColor(Color.RED);
            graphics.translate(0, 0);
            // Draw Nodes
            graphics.setFont(new Font("Helvetica", Font.PLAIN, 20));

            if (Find_TSP_Path == true) {

                for (int i = 0; i < numberOfNodes; i++) {
                    graphics.setColor(Color.black);
                    graphics.fillOval((int) GraphPoints[i].x - (r2 / 2), (int) GraphPoints[i].y - (r2 / 2), r2, r2);
                    graphics.setColor(Color.white);
                    graphics.drawString(i + 1 + "", (int) GraphPoints[i].x - 7, (int) GraphPoints[i].y + 4);
                }

                // Draw Edges
                graphics.setFont(new Font("Helvetica", Font.PLAIN, 20));
                graphics.setColor(Color.black);
                for (int i = 0; i < TSP_Path.size() - 1; i++) {

                    int from = TSP_Path.get(i);
                    int to = TSP_Path.get(i + 1);
                        //System.out.println("MST");

                    //System.out.println((i+1) + "     " + node+(1));
                    if (adj_Matrix[from][to] > 0) {
                        from--;
                        to--;

                        graphics.drawLine((int) GraphPoints[from].x + 7, (int) GraphPoints[from].y + 7, (int) GraphPoints[to].x + 7, (int) GraphPoints[to].y + 7);

                        point Center = new point((Math.abs(GraphPoints[to].x - GraphPoints[from].x) / 2), Math.abs(GraphPoints[to].y - GraphPoints[from].y) / 2);
                        point p1 = new point(GraphPoints[from].x, GraphPoints[from].y);
                        point p2 = new point(GraphPoints[to].x, GraphPoints[to].y);

                        graphics.setColor(Color.BLACK);

                        from++;
                        to++;
                        if (p2.x < p1.x && p2.y >= p1.y) {
                            graphics.drawString(String.valueOf((int) adj_Matrix[from][to]), (int) (GraphPoints[from - 1].x - Center.x - 15), (int) (GraphPoints[from - 1].y + Center.y - 6));
                        } else if (p2.x > p1.x && p2.y >= p1.y) {
                            graphics.drawString(String.valueOf((int) adj_Matrix[from][to]), (int) (GraphPoints[from - 1].x + Center.x), (int) (GraphPoints[from - 1].y + Center.y));
                        } else if (p2.x >= p1.x && p2.y < p1.y) {
                            graphics.drawString(String.valueOf((int) adj_Matrix[from][to]), (int) (GraphPoints[from - 1].x + Center.x - 18), (int) (GraphPoints[from - 1].y - Center.y - 3));
                        } else if (p2.x < p1.x && p2.y < p1.y) {
                            graphics.drawString(String.valueOf((int) adj_Matrix[from][to]), (int) (GraphPoints[from - 1].x - Center.x), (int) (GraphPoints[from - 1].y - Center.y));
                        }

                    }

                }

                int from = TSP_Path.get(TSP_Path.size() - 1);
                int to = TSP_Path.get(TSP_Path.get(0));

                //System.out.println("MST");
                //System.out.println((i+1) + "     " + node+(1));
                if (adj_Matrix[from][to] > 0) {
                    from--;
                    to--;

                    graphics.drawLine((int) GraphPoints[from].x + 7, (int) GraphPoints[from].y + 7, (int) GraphPoints[to].x + 7, (int) GraphPoints[to].y + 7);

                    point Center = new point((Math.abs(GraphPoints[to].x - GraphPoints[from].x) / 2), Math.abs(GraphPoints[to].y - GraphPoints[from].y) / 2);
                    point p1 = new point(GraphPoints[from].x, GraphPoints[from].y);
                    point p2 = new point(GraphPoints[to].x, GraphPoints[to].y);

                    graphics.setColor(Color.BLACK);

                    from++;
                    to++;
                    if (p2.x < p1.x && p2.y >= p1.y) {
                        graphics.drawString(String.valueOf((int) adj_Matrix[from][to]), (int) (GraphPoints[from - 1].x - Center.x - 15), (int) (GraphPoints[from - 1].y + Center.y - 6));
                    } else if (p2.x > p1.x && p2.y >= p1.y) {
                        graphics.drawString(String.valueOf((int) adj_Matrix[from][to]), (int) (GraphPoints[from - 1].x + Center.x), (int) (GraphPoints[from - 1].y + Center.y));
                    } else if (p2.x >= p1.x && p2.y < p1.y) {
                        graphics.drawString(String.valueOf((int) adj_Matrix[from][to]), (int) (GraphPoints[from - 1].x + Center.x - 18), (int) (GraphPoints[from - 1].y - Center.y - 3));
                    } else if (p2.x < p1.x && p2.y < p1.y) {
                        graphics.drawString(String.valueOf((int) adj_Matrix[from][to]), (int) (GraphPoints[from - 1].x - Center.x), (int) (GraphPoints[from - 1].y - Center.y));
                    }
                }

            } else if (Solveoptimal3 && !Solveoptimal2) {
                boolean vis[][] = new boolean[numberOfNodes + 1][numberOfNodes + 1];

                for (int i = 0; i < numberOfNodes; i++) {
                    graphics.setColor(Color.GREEN);
                    graphics.fillOval((int) GraphPoints[i].x - (r2 / 2), (int) GraphPoints[i].y - (r2 / 2), r2, r2);
                    graphics.setColor(Color.black);
                    graphics.drawString(i + 1 + "", (int) GraphPoints[i].x - 7, (int) GraphPoints[i].y + 4);
                }

                // Draw Edges
                graphics.setFont(new Font("Helvetica", Font.PLAIN, 20));

                for (int i = 0; i < numberOfNodes; i++) {
                    for (int j = 0; j < Adj_After_MST3[i + 1].size(); j++) {
                        int node = Adj_After_MST3[i + 1].get(j).intValue();
                        //System.out.println("MST");
                        node--;
                        //System.out.println((i+1) + "     " + node+(1));
                        if (adj_Matrix[i + 1][node + 1] > 0 && !vis[i + 1][node + 1]) {
                            vis[i + 1][node + 1] = true;
                            vis[node + 1][i + 1] = true;

                            graphics.setColor(Color.GREEN);
                            graphics.drawLine((int) GraphPoints[i].x + 7, (int) GraphPoints[i].y + 7, (int) GraphPoints[node].x + 7, (int) GraphPoints[node].y + 7);

                            point Center = new point((Math.abs(GraphPoints[node].x - GraphPoints[i].x) / 2), Math.abs(GraphPoints[node].y - GraphPoints[i].y) / 2);
                            point p1 = new point(GraphPoints[i].x, GraphPoints[i].y);
                            point p2 = new point(GraphPoints[node].x, GraphPoints[node].y);

                            graphics.setColor(Color.BLACK);

                            if (p2.x < p1.x && p2.y >= p1.y) {
                                graphics.drawString(String.valueOf((int) adj_Matrix[i + 1][node + 1]), (int) (GraphPoints[i].x - Center.x - 15), (int) (GraphPoints[i].y + Center.y - 6));
                            } else if (p2.x > p1.x && p2.y >= p1.y) {
                                graphics.drawString(String.valueOf((int) adj_Matrix[i + 1][node + 1]), (int) (GraphPoints[i].x + Center.x), (int) (GraphPoints[i].y + Center.y));
                            } else if (p2.x >= p1.x && p2.y < p1.y) {
                                graphics.drawString(String.valueOf((int) adj_Matrix[i + 1][node + 1]), (int) (GraphPoints[i].x + Center.x - 18), (int) (GraphPoints[i].y - Center.y - 3));
                            } else if (p2.x < p1.x && p2.y < p1.y) {
                                graphics.drawString(String.valueOf((int) adj_Matrix[i + 1][node + 1]), (int) (GraphPoints[i].x - Center.x), (int) (GraphPoints[i].y - Center.y));
                            }

                        }
                    }
                }
                //////////////////for matching

                vis = new boolean[numberOfNodes + 1][numberOfNodes + 1];

                for (int i = 0; i < numberOfNodes; i++) {
                    for (int j = 0; j < Matching[i + 1].size(); j++) {
                        int node = Matching[i + 1].get(j).intValue();
                        //System.out.println("MST");
                        node--;
                        //System.out.println((i+1) + "     " + node+(1));
                        if (adj_Matrix[i + 1][node + 1] > 0 && !vis[i + 1][node + 1]) {
                            vis[i + 1][node + 1] = true;
                            vis[node + 1][i + 1] = true;

                            graphics.setColor(Color.red);
                            graphics.drawLine((int) GraphPoints[i].x - 7, (int) GraphPoints[i].y - 10, (int) GraphPoints[node].x - 7, (int) GraphPoints[node].y - 10);

                            point Center = new point((Math.abs(GraphPoints[node].x - GraphPoints[i].x) / 2), Math.abs(GraphPoints[node].y - GraphPoints[i].y) / 2);
                            point p1 = new point(GraphPoints[i].x, GraphPoints[i].y);
                            point p2 = new point(GraphPoints[node].x, GraphPoints[node].y);

                            graphics.setColor(Color.BLACK);

                            if (p2.x < p1.x && p2.y >= p1.y) {
                                graphics.drawString(String.valueOf((int) adj_Matrix[i + 1][node + 1]), (int) (GraphPoints[i].x - Center.x - 15), (int) (GraphPoints[i].y + Center.y - 6));
                            } else if (p2.x > p1.x && p2.y >= p1.y) {
                                graphics.drawString(String.valueOf((int) adj_Matrix[i + 1][node + 1]), (int) (GraphPoints[i].x + Center.x), (int) (GraphPoints[i].y + Center.y));
                            } else if (p2.x >= p1.x && p2.y < p1.y) {
                                graphics.drawString(String.valueOf((int) adj_Matrix[i + 1][node + 1]), (int) (GraphPoints[i].x + Center.x - 18), (int) (GraphPoints[i].y - Center.y - 3));
                            } else if (p2.x < p1.x && p2.y < p1.y) {
                                graphics.drawString(String.valueOf((int) adj_Matrix[i + 1][node + 1]), (int) (GraphPoints[i].x - Center.x), (int) (GraphPoints[i].y - Center.y));
                            }

                        }
                    }
                }

            } else if (Solveoptimal2 && !Solveoptimal3) {
                boolean vis[][] = new boolean[numberOfNodes + 1][numberOfNodes + 1];

                for (int i = 0; i < numberOfNodes; i++) {
                    graphics.setColor(Color.GREEN);
                    graphics.fillOval((int) GraphPoints[i].x - (r2 / 2), (int) GraphPoints[i].y - (r2 / 2), r2, r2);
                    graphics.setColor(Color.black);
                    graphics.drawString(i + 1 + "", (int) GraphPoints[i].x - 7, (int) GraphPoints[i].y + 4);
                }

                // Draw Edges
                graphics.setFont(new Font("Helvetica", Font.PLAIN, 20));

                for (int i = 0; i < numberOfNodes; i++) {
                    for (int j = 0; j < Adj_After_MST[i + 1].size(); j++) {
                        int node = Adj_After_MST[i + 1].get(j).intValue();
                        System.out.println("MST");

                        node--;
                        //System.out.println((i+1) + "     " + node+(1));
                        if (adj_Matrix[i + 1][node + 1] > 0 && !vis[i + 1][node + 1]) {
                            vis[i + 1][node + 1] = true;
                            vis[node + 1][i + 1] = true;
                            graphics.setColor(Color.GREEN);
                            graphics.drawLine((int) GraphPoints[i].x + 7, (int) GraphPoints[i].y + 7, (int) GraphPoints[node].x + 7, (int) GraphPoints[node].y + 7);

                            point Center = new point((Math.abs(GraphPoints[node].x - GraphPoints[i].x) / 2), Math.abs(GraphPoints[node].y - GraphPoints[i].y) / 2);
                            point p1 = new point(GraphPoints[i].x, GraphPoints[i].y);
                            point p2 = new point(GraphPoints[node].x, GraphPoints[node].y);

                            graphics.setColor(Color.BLACK);

                            if (p2.x < p1.x && p2.y >= p1.y) {
                                graphics.drawString(String.valueOf((int) adj_Matrix[i + 1][node + 1]), (int) (GraphPoints[i].x - Center.x - 15), (int) (GraphPoints[i].y + Center.y - 6));
                            } else if (p2.x > p1.x && p2.y >= p1.y) {
                                graphics.drawString(String.valueOf((int) adj_Matrix[i + 1][node + 1]), (int) (GraphPoints[i].x + Center.x), (int) (GraphPoints[i].y + Center.y));
                            } else if (p2.x >= p1.x && p2.y < p1.y) {
                                graphics.drawString(String.valueOf((int) adj_Matrix[i + 1][node + 1]), (int) (GraphPoints[i].x + Center.x - 18), (int) (GraphPoints[i].y - Center.y - 3));
                            } else if (p2.x < p1.x && p2.y < p1.y) {
                                graphics.drawString(String.valueOf((int) adj_Matrix[i + 1][node + 1]), (int) (GraphPoints[i].x - Center.x), (int) (GraphPoints[i].y - Center.y));
                            }

                        }
                    }
                }
                vis = new boolean[numberOfNodes + 1][numberOfNodes + 1];
                graphics.setColor(Color.red);
                for (int i = 0; i < numberOfNodes; i++) {
                    for (int j = 0; j < Adj_After_MST[i + 1].size(); j++) {
                        int node = Adj_After_MST[i + 1].get(j).intValue();

                        node--;
                        //System.out.println((i+1) + "     " + node+(1));
                        if (adj_Matrix[i + 1][node + 1] > 0 && !vis[i + 1][node + 1]) {
                            vis[i + 1][node + 1] = true;
                            vis[node + 1][i + 1] = true;

                            graphics.drawLine((int) GraphPoints[i].x - 7, (int) GraphPoints[i].y - 10, (int) GraphPoints[node].x - 7, (int) GraphPoints[node].y - 10);

                        }

                    }
                }

            } else {
                
                for (int i = 0; i < numberOfNodes; i++) {
                    graphics.setColor(Color.BLACK);
                    graphics.fillOval((int) GraphPoints[i].x - (r2 / 2), (int) GraphPoints[i].y - (r2 / 2), r2, r2);
                    graphics.setColor(Color.WHITE);
                    graphics.drawString(i + 1 + "", (int) GraphPoints[i].x - 7, (int) GraphPoints[i].y + 4);
                }

                // Draw Edges
                graphics.setFont(new Font("Helvetica", Font.PLAIN, 20));
                for (int i = 0; i < numberOfNodes; i++) {
                    for (int j = i + 1; j < numberOfNodes; j++) {
                        int x = 0;

                        if (adj_Matrix[i + 1][j + 1] > 0) {
                            graphics.setColor(Color.black);
                            graphics.drawLine((int) GraphPoints[i].x, (int) GraphPoints[i].y, (int) GraphPoints[j].x, (int) GraphPoints[j].y);

                            point Center = new point((Math.abs(GraphPoints[j].x - GraphPoints[i].x) / 2), Math.abs(GraphPoints[j].y - GraphPoints[i].y) / 2);
                            point p1 = new point(GraphPoints[i].x, GraphPoints[i].y);
                            point p2 = new point(GraphPoints[j].x, GraphPoints[j].y);

                            graphics.setColor(Color.black);

                            if (p2.x <= p1.x && p2.y >= p1.y) {
                                graphics.drawString(String.valueOf((int) adj_Matrix[i + 1][j + 1]), (int) (GraphPoints[i].x - Center.x - 15), (int) (GraphPoints[i].y + Center.y - 6));
                            } else if (p2.x > p1.x && p2.y >= p1.y) {
                                graphics.drawString(String.valueOf((int) adj_Matrix[i + 1][j + 1]), (int) (GraphPoints[i].x + Center.x), (int) (GraphPoints[i].y + Center.y));
                            } else if (p2.x >= p1.x && p2.y <= p1.y) {
                                graphics.drawString(String.valueOf((int) adj_Matrix[i + 1][j + 1]), (int) (GraphPoints[i].x + Center.x - 18), (int) (GraphPoints[i].y - Center.y - 3));
                            } else if (p2.x <= p1.x && p2.y <= p1.y) {
                                graphics.drawString(String.valueOf((int) adj_Matrix[i + 1][j + 1]), (int) (GraphPoints[i].x - Center.x), (int) (GraphPoints[i].y - Center.y));
                            }

                        }
                    }
                }
            }

        }

        // تحديد نقاط الدائرة من اجل الرسم
        public final void fillGraphPoints() {
            for (int i = 0; i < numberOfNodes; i++) {
                double theta = 360 * i / numberOfNodes;
                double radian = Math.toRadians(theta);
                GraphPoints[i] = new point(c0 + r0 * Math.cos(radian) + 50, c1 + r0 * Math.sin(radian) + 40);
            }
        }

        public void GenerateRandomGraphAdjMatrix(int n) {
            adj_Matrix = new int[n + 1][n + 1];
            initAdjMatrix();
            GraphPoints = new point[numberOfNodes + 1];
            /////////////////////////////////////
            Random rand = new Random();

            double x = rand.nextDouble();
            double y = rand.nextDouble();
            GraphPoints[0] = new point(x, y);
            x = rand.nextDouble();
            y = rand.nextDouble();
            GraphPoints[1] = new point(x, y);

            adj_Matrix[1][2] = (int) Math.ceil(point.distance(GraphPoints[0], GraphPoints[1]));
            adj_Matrix[2][1] = (int) Math.ceil(point.distance(GraphPoints[0], GraphPoints[1]));

            int node = 3;

            while (node <= n) {
                x = rand.nextInt(100);
                y = rand.nextInt(100);

                point p = new point(x, y);
                boolean ok = true;
                for (int i = 0; i < node - 1; i++) {
                    if (p.x == GraphPoints[i].x && p.y == GraphPoints[i].y) {
                        ok = false;
                        break;
                    }
                }

                if (ok) {
//System.out.println(p.x+"     "+p.y+ "             "+node);
                    GraphPoints[node - 1] = new point(p.x, p.y);
                    for (int i = 0; i < node - 1; i++) {
                        adj_Matrix[i + 1][node] = (int) Math.ceil(point.distance(GraphPoints[i], p));
                        adj_Matrix[node][i + 1] = (int) Math.ceil(point.distance(GraphPoints[i], p));
                    }
                    node++;
                }
            }// end of while

            ///////////////////////////
            /*for (int i = 0; i <= n; i++) {
             for (int j = 0; j <= n; j++) {
             adj_Matrix[i][j] = 1;
             }
             }*/
        }

        ///////////////////////
        //Function Of Algorithm
        public boolean checkTriangleEquation() {

            for (int i = 1; i <= numberOfNodes; i++) {
                for (int j = i + 1; j <= numberOfNodes; j++) {
                    for (int k = j + 1; k <= numberOfNodes; k++) {
                        if (adj_Matrix[i][j] + adj_Matrix[i][k] < adj_Matrix[j][k]
                                || adj_Matrix[i][k] + adj_Matrix[k][j] < adj_Matrix[i][j]
                                || adj_Matrix[j][i] + adj_Matrix[j][k] < adj_Matrix[i][k]
                                && adj_Matrix[i][j] > 0 && adj_Matrix[i][k] > 0
                                && adj_Matrix[j][k] > 0) {
                            JOptionPane.showMessageDialog(null, "The Nodes i = " + i + "and node j = "
                                    + j + " and node k = " + k + " is not achieve a Triangle Rule");
                            return false;

                        }
                    }
                }
            }
            return true;
        }

        public boolean checkIfGraphIsComplete() {
            for (int i = 1; i <= numberOfNodes; i++) {
                for (int j = 1; j <= numberOfNodes; j++) {
                    if (i != j && adj_Matrix[i][j] <= 0) {
                        JOptionPane.showMessageDialog(null, "The Graph is not Complete");
                        return false;
                    }

                }
            }
            return true;
        }

        class Edge {

            public int from, to;
            public double weight;

            Edge(int from, int to, double weight) {
                this.from = from;
                this.to = to;
                this.weight = weight;
            }

        }

        ArrayList<Integer> Adj_After_MST[];
        ArrayList<Integer>[] Adj_After_MST_Duplicate;
        ArrayList<Integer> TSP_Path;
        boolean[] visited;
        boolean[] node_in_path;
        int[] key;
        int[] parent;
        int cost = 0;
        String msg_Path = "";
        int cost_Path = 0;
        boolean Find_TSP_Path = false;

        public void initMST() {

            Adj_After_MST = new ArrayList[numberOfNodes + 1];
            Adj_After_MST3 = new ArrayList[numberOfNodes + 1];
            Adj_After_MST_Duplicate = new ArrayList[numberOfNodes + 1];
            Adj_After_Matching = new ArrayList[numberOfNodes + 1];
            visited = new boolean[numberOfNodes + 1];
            node_in_path = new boolean[numberOfNodes + 1];
            key = new int[numberOfNodes + 1];
            parent = new int[numberOfNodes + 1];
            Adj_After_MST[0] = new ArrayList<>();
            Adj_After_MST3[0] = new ArrayList<>();
            Adj_After_MST_Duplicate[0] = new ArrayList<>();
            Adj_After_Matching[0] = new ArrayList<>();
            for (int i = 1; i <= numberOfNodes; i++) {
                visited[i] = false;
                node_in_path[i] = false;
                key[i] = (int) 1e6;
                Adj_After_MST[i] = new ArrayList<>();
                Adj_After_MST3[i] = new ArrayList<>();
                Adj_After_MST_Duplicate[i] = new ArrayList<>();
                Adj_After_Matching[i] = new ArrayList<>();
            }
            key[1] = 0;
            parent[1] = -1;
        }

        public void initVisitedArray(boolean tmp) {
            visited = new boolean[numberOfNodes + 1];
            for (int i = 1; i <= numberOfNodes; i++) {
                visited[i] = tmp;
            }
        }

        int minKey() {
            // Initialize min value
            int mn = Integer.MAX_VALUE, min_index = 0;

            for (int v = 1; v <= numberOfNodes; v++) {
                if (visited[v] == false && key[v] < mn) {
                    mn = key[v];
                    min_index = v;
                }
            }
            return min_index;
        }

        //Compute Minimum Spaning Tree
        public void computeMST() {

            initMST();

            for (int cnt = 1; cnt <= numberOfNodes; cnt++) {
                int u = minKey();
                visited[u] = true;
                for (int v = 1; v <= numberOfNodes; v++) {
                    if (adj_Matrix[u][v] > 0 && visited[v] == false && adj_Matrix[u][v] < key[v]) {
                        parent[v] = u;
                        key[v] = adj_Matrix[u][v];
                    }

                }

            }

        }// End Of Compute MST

        public void graphAfterMST() {
            for (int i = 2; i <= numberOfNodes; i++) {
                Adj_After_MST[i].add(parent[i]);
                Adj_After_MST3[i].add(parent[i]);
                Adj_After_Matching[i].add(parent[i]);
                Adj_After_MST_Duplicate[i].add(parent[i]);
                //cost += adj_Matrix[i][parent[i]];
                Adj_After_MST[parent[i]].add(i);
                Adj_After_MST3[parent[i]].add(i);
                Adj_After_MST_Duplicate[parent[i]].add(i);
                Adj_After_Matching[parent[i]].add(i);
            }
        }

        public void DublicateEdge() {
            for (int i = 2; i <= numberOfNodes; i++) {
                Adj_After_MST[i].add(parent[i]);
                Adj_After_MST_Duplicate[i].add(parent[i]);
                Adj_After_MST[parent[i]].add(i);
                Adj_After_MST_Duplicate[parent[i]].add(i);
            }
        }

        public void DFS(int node) {
            //cout<<"node   "<<node<<endl;
            visited[node] = true;
            for (int i = 0; i < Adj_After_MST_Duplicate[node].size(); i++) {
                if (!visited[Adj_After_MST_Duplicate[node].get(i)]) {
                    DFS(Adj_After_MST_Duplicate[node].get(i));
                }
            }

        }

        public boolean isConnected() {
            // Mark all the vertices as not visited
            initVisitedArray(false);
            int i = 0;
            // Find a vertex with non-zero degree
            for (i = 1; i <= numberOfNodes; i++) {
                if (Adj_After_MST_Duplicate[i].size() != 0) {
                    break;
                }
            }
            // If there are no edges in the graph, return true
            if (i == numberOfNodes + 1) {
                return true;
            }

            DFS(i);

            // Check if all non-zero degree vertices are visited
            for (i = 1; i <= numberOfNodes; i++) {
                if (visited[i] == false && Adj_After_MST_Duplicate[i].size() > 0) {
                    return false;
                }
            }

            return true;
        }

        public int isEulerian() {
            // Check if all non-zero degree vertices are connected
            if (isConnected() == false) {
                return 0;
            }

            // Count vertices with odd degree
            int odd = 0;
            for (int i = 1; i <= numberOfNodes; i++) {
                if (Adj_After_MST_Duplicate[i].size() % 2 != 0) {
                    odd++;
                }
            }

            // If count is more than 2, then graph is not Eulerian
            if (odd > 2) {
                return 0;
            }

            // If odd count is 2, then semi-eulerian.
            // If odd count is 0, then eulerian
            // Note that odd count can never be 1 for undirected graph
            if (odd == 1) {
                return 1;
            }
            if (odd == 0) {
                return 2;
            }
            return 1000;
            //return (odd) ? 1 : 2;
        }

        public int testEuler() {
            int res = isEulerian();
            //cout<<"res        " <<res<<endl;
            if (res == 0) {
                msg = "graph is not Eulerian\n";
                return 0;
            } else if (res == 1) {
                msg = "graph has a Euler path\n";
                return 1;
            } else {
                msg = "graph has a Euler cycle\n";
                return 2;
            }
        }

        int DFSCount(int node) {
            // Mark the current node as visited
            visited[node] = true;
            int cnt = 1;

            // Recur for all vertices adjacent to this vertex
            for (int i = 0; i < Adj_After_MST_Duplicate[node].size(); i++) {
                if (Adj_After_MST_Duplicate[node].get(i) != -1 && !visited[Adj_After_MST_Duplicate[node].get(i)]) {
                    cnt += DFSCount(Adj_After_MST_Duplicate[node].get(i));
                }
            }

            return cnt;
        }

        void addEdge(int u, int v) {
            Adj_After_MST_Duplicate[u].add(v);
            Adj_After_MST_Duplicate[v].add(u);
        }

        void rmvEdge(int u, int v) {
            // Find v in adjacency list of u and replace it with -1
            int index = Adj_After_MST_Duplicate[u].indexOf(v);
            Adj_After_MST_Duplicate[u].remove(index);

            // Find u in adjacency list of v and replace it with -1
            index = Adj_After_MST_Duplicate[v].indexOf(u);
            Adj_After_MST_Duplicate[v].remove(index);
        }

        boolean isValidNextEdge(int u, int v) {
            // The edge u-v is valid in one of the following two cases:
            // 1) If v is the only adjacent vertex of u
            int cnt = 0;  // To store count of adjacent vertices
            for (int i = 0; i < Adj_After_MST_Duplicate[u].size(); i++) {
                if (Adj_After_MST_Duplicate[u].get(i) != -1) {
                    cnt++;
                }
            }
            if (cnt == 1) {
                return true;
            }
  // 2) If there are multiple adjacents, then u-v is not a bridge
            // Do following steps to check if u-v is a bridge

            // 2.a) count of vertices reachable from u
            //bool visited[V];
            initVisitedArray(false);
            int cnt1 = DFSCount(u);

            // 2.b) Remove edge (u, v) and after removing the edge, count
            // vertices reachable from u
            rmvEdge(u, v);
            initVisitedArray(false);
            int cnt2 = DFSCount(u);

            // 2.c) Add the edge back to the graph
            addEdge(u, v);

            // 2.d) If count1 is greater, then edge (u, v) is a bridge
            return (cnt1 > cnt2) ? false : true;
        }

        void printEulerUtil(int u) {
            // Recur for all the vertices adjacent to this vertex

            for (int i = 0; i < Adj_After_MST_Duplicate[u].size(); i++) {
                int v = Adj_After_MST_Duplicate[u].get(i);

                // If edge u-v is not removed and it's a a valid next edge
                if (v != -1 && isValidNextEdge(u, v)) {
                    msg += u + "--" + v + ",\n";
                    cost += adj_Matrix[u][v];
                    /*cout << u << "-" << v << "  ";
                     Path.push_back(u);
                     Path.push_back(v);*/
                    if (!node_in_path[u]) {
                        node_in_path[u] = true;
                        TSP_Path.add(u);

                    }
                    if (!node_in_path[v]) {
                        node_in_path[v] = true;
                        TSP_Path.add(v);
                    }

                    rmvEdge(u, v);
                    printEulerUtil(v);
                }
            }
        }

        void printEulerTour() {
            // Find a vertex with odd degree

            int u = 1;
            for (int i = 1; i <= numberOfNodes; i++) {
                if (Adj_After_MST_Duplicate[i].size() % 2 != 0) {
                    u = i;
                    break;
                }
            }
            // Print tour starting from oddv
            msg = "The Path is { ";
            printEulerUtil(u);
            //cost*=2;
            msg += " }\n The Cost is : " + cost;
            JOptionPane.showMessageDialog(null, msg);

        }

        public void SolveOptimalSolution2() {

            TSP_Path = new ArrayList<>();
            node_in_path = new boolean[numberOfNodes + 1];
            
            
            cost = 0;
            msg = "";
            msg_Path = "";
            cost_Path = 0;
            computeMST();
            graphAfterMST();
            repaint();
            String s = "";
            int tmp = 0;
            for (int i = 1; i <= numberOfNodes; i++) {
                s += i + "\n";
                for (int j = 0; j < Adj_After_MST[i].size(); j++) {
                    s += Adj_After_MST[i].get(j) + "   ";
                    tmp+=adj_Matrix[i][Adj_After_MST[i].get(j)];
                }
                s += "\n";
            }
            
            JOptionPane.showMessageDialog(null, s);
            
            JOptionPane.showMessageDialog(null, "The cost of Mst is : "+tmp/2);

            DublicateEdge();
            path.setText(msg);
            repaint();
            initVisitedArray(false);
            int res = testEuler();

            JOptionPane.showMessageDialog(null, msg);
            if (res == 2) {
                printEulerTour();
                path.setText(msg);
                int node = TSP_Path.get(0);
                TSP_Path.add(TSP_Path.get(0));
                for (int i = 0; i < TSP_Path.size(); i++) {
                    msg_Path += TSP_Path.get(i) + " --> ";
                    cost_Path += adj_Matrix[node][TSP_Path.get(i)];
                    node = TSP_Path.get(i);
                }
                //cost_Path += adj_Matrix[node][TSP_Path.get(0)];
                //msg_Path += TSP_Path.get(0);

                JOptionPane.showMessageDialog(null, msg_Path + "\nThe Cost is : " + cost_Path);

                Find_TSP_Path = true;
                repaint();

            } else {
                return;
            }
            msg_Path = msg_Path.substring(0,msg_Path.length()-4);
            path.setText(msg_Path);
            repaint();

        }

        ArrayList<Integer> Adj_After_MST3[];
        ArrayList<Integer> Adj_After_Matching[];
        ArrayList<Integer> Matching[];
        ArrayList<Integer> TSP_Path3;
        ArrayList<Edge> Tmp_For_Match;
        boolean[] visited3;
        boolean[] node_in_path3;
        boolean[] Odd_Degree;
        int[] key3;
        int[] parent3;
        int cost3 = 0;
        String msg_Path3 = "";
        int cost_Path3 = 0;

        public void DFS3(int node) {
            //cout<<"node   "<<node<<endl;
            visited3[node] = true;
            for (int i = 0; i < Adj_After_Matching[node].size(); i++) {
                if (!visited3[Adj_After_Matching[node].get(i)]) {
                    DFS3(Adj_After_Matching[node].get(i));
                }
            }

        }

        public boolean isConnected3() {
            // Mark all the vertices as not visited
            initVisitedArray3(false);
            int i = 0;
            // Find a vertex with non-zero degree
            for (i = 1; i <= numberOfNodes; i++) {
                if (Adj_After_Matching[i].size() != 0) {
                    break;
                }
            }
            // If there are no edges in the graph, return true
            if (i == numberOfNodes + 1) {
                return true;
            }

            DFS3(i);

            // Check if all non-zero degree vertices are visited
            for (i = 1; i <= numberOfNodes; i++) {
                if (visited3[i] == false && Adj_After_Matching[i].size() > 0) {
                    return false;
                }
            }

            return true;
        }

        public int isEulerian3() {
            // Check if all non-zero degree vertices are connected
            if (isConnected3() == false) {

                return 0;
            }

            // Count vertices with odd degree
            int odd = 0;
            for (int i = 1; i <= numberOfNodes; i++) {
                if (Adj_After_Matching[i].size() % 2 != 0) {
                    odd++;
                }
            }

            // If count is more than 2, then graph is not Eulerian
            if (odd > 2) {
                return 0;
            }

            // If odd count is 2, then semi-eulerian.
            // If odd count is 0, then eulerian
            // Note that odd count can never be 1 for undirected graph
            if (odd == 1) {
                return 1;
            }
            if (odd == 0) {
                return 2;
            }
            return 1000;
            //return (odd) ? 1 : 2;
        }

        public int testEuler3() {
            int res = isEulerian3();

            //cout<<"res        " <<res<<endl;
            if (res == 0) {
                msg = "graph is not Eulerian\n";
                return 0;
            } else if (res == 1) {
                msg = "graph has a Euler path\n";
                return 1;
            } else {
                msg = "graph has a Euler cycle\n";
                return 2;
            }
        }

        int DFSCount3(int node) {
            // Mark the current node as visited
            visited3[node] = true;
            int cnt = 1;

            // Recur for all vertices adjacent to this vertex
            for (int i = 0; i < Adj_After_Matching[node].size(); i++) {
                if (Adj_After_Matching[node].get(i) != -1 && !visited3[Adj_After_Matching[node].get(i)]) {
                    cnt += DFSCount3(Adj_After_Matching[node].get(i));
                }
            }

            return cnt;
        }

        void addEdge3(int u, int v) {
            Adj_After_Matching[u].add(v);
            Adj_After_Matching[v].add(u);
        }

        void rmvEdge3(int u, int v) {
            // Find v in adjacency list of u and replace it with -1
            int index = Adj_After_Matching[u].indexOf(v);
            Adj_After_Matching[u].remove(index);

            // Find u in adjacency list of v and replace it with -1
            index = Adj_After_Matching[v].indexOf(u);
            Adj_After_Matching[v].remove(index);
        }

        boolean isValidNextEdge3(int u, int v) {
            // The edge u-v is valid in one of the following two cases:
            // 1) If v is the only adjacent vertex of u
            int cnt = 0;  // To store count of adjacent vertices
            for (int i = 0; i < Adj_After_Matching[u].size(); i++) {
                if (Adj_After_Matching[u].get(i) != -1) {
                    cnt++;
                }
            }
            if (cnt == 1) {
                return true;
            }
  // 2) If there are multiple adjacents, then u-v is not a bridge
            // Do following steps to check if u-v is a bridge

            // 2.a) count of vertices reachable from u
            //bool visited[V];
            initVisitedArray3(false);
            int cnt1 = DFSCount3(u);

            // 2.b) Remove edge (u, v) and after removing the edge, count
            // vertices reachable from u
            rmvEdge3(u, v);
            initVisitedArray3(false);
            int cnt2 = DFSCount3(u);

            // 2.c) Add the edge back to the graph
            addEdge3(u, v);

            // 2.d) If count1 is greater, then edge (u, v) is a bridge
            return (cnt1 > cnt2) ? false : true;
        }

        void printEulerUtil3(int u) {
            // Recur for all the vertices adjacent to this vertex

            for (int i = 0; i < Adj_After_Matching[u].size(); i++) {
                int v = Adj_After_Matching[u].get(i);

                // If edge u-v is not removed and it's a a valid next edge
                if (v != -1 && isValidNextEdge3(u, v)) {
                    msg3 += u + "--" + v + ",\n";
                    cost3 += adj_Matrix[u][v];
                    /*cout << u << "-" << v << "  ";
                     Path.push_back(u);
                     Path.push_back(v);*/
                    if (!node_in_path3[u]) {
                        node_in_path3[u] = true;
                        TSP_Path.add(u);

                    }
                    if (!node_in_path3[v]) {
                        node_in_path3[v] = true;
                        TSP_Path.add(v);
                    }

                    rmvEdge3(u, v);
                    printEulerUtil3(v);
                }
            }
        }

        void printEulerTour3() {
            // Find a vertex with odd degree

            int u = 1;
            for (int i = 1; i <= numberOfNodes; i++) {
                if (Adj_After_Matching[i].size() % 2 != 0) {
                    u = i;
                    break;
                }
            }
            // Print tour starting from oddv
            msg3 = "The Path is { ";
            printEulerUtil3(u);
            //cost*=2;
            msg3 += " }\n The Cost is : " + cost3;
            

        }

        public void initVisitedArray3(boolean tmp) {
            visited3 = new boolean[numberOfNodes + 1];
            for (int i = 1; i <= numberOfNodes; i++) {
                visited3[i] = tmp;
            }
        }

        boolean isDegreeOdd(int node) {
            if (Adj_After_Matching[node].size() % 2 != 0) {
                return true;
            } else {
                return false;
            }
        }

        boolean isAllNodeHaveEvenDegree() {

            for (int i = 1; i <= numberOfNodes; i++) {
                if (isDegreeOdd(i)) {
                    return false;
                }
            }
            return true;
        }

        public void FindVertexWithOddDegree() {
            // to mark each node if degree is odd
            for (int i = 1; i <= numberOfNodes; i++) {
                if (isDegreeOdd(i)) {
                    Odd_Degree[i] = true;
                    for (int j = i + 1; j <= numberOfNodes; j++) {
                        if (isDegreeOdd(j) && adj_Matrix[i][j] > 0) {

                            Edge e = new Edge(i, j, adj_Matrix[i][j]);
                            Tmp_For_Match.add(e);
                        }// end of if
                    } // end of nesteed for
                } // end of if
            }// end of for
        }

        public void FindGreedyMinimumMatching() {

            for (int i = 0; i < Tmp_For_Match.size(); i++) {
                int index = i;
                double weight = Tmp_For_Match.get(i).weight;

                for (int j = i + 1; j < Tmp_For_Match.size(); j++) {

                    if (Tmp_For_Match.get(j).weight < weight) {
                        index = j;
                        weight = Tmp_For_Match.get(j).weight;
                    }
                }
                if (index != i) {
                    Edge tmp = new Edge(Tmp_For_Match.get(i).from, Tmp_For_Match.get(i).to, Tmp_For_Match.get(i).weight);
                    Tmp_For_Match.get(i).from = Tmp_For_Match.get(index).from;
                    Tmp_For_Match.get(i).to = Tmp_For_Match.get(index).to;
                    Tmp_For_Match.get(i).weight = Tmp_For_Match.get(index).weight;

                    Tmp_For_Match.get(index).from = tmp.from;
                    Tmp_For_Match.get(index).to = tmp.to;
                    Tmp_For_Match.get(index).weight = tmp.weight;

                }

            }
/*
            String tmp = "";
            String tt = "";

            for (int i = 0; i < Tmp_For_Match.size(); i++) {
                tmp += Tmp_For_Match.get(i).from + "    " + Tmp_For_Match.get(i).to + "  " + Tmp_For_Match.get(i).weight + " ----- ";
                tt += Tmp_For_Match.get(i).from + "    " + Tmp_For_Match.get(i).to + "  " + Tmp_For_Match.get(i).weight + " \n ";
            }

            System.out.println(tt);*/
            for (int i = 0; i < Tmp_For_Match.size(); i++) {
                if (isDegreeOdd(Tmp_For_Match.get(i).from) && isDegreeOdd(Tmp_For_Match.get(i).to)) {
                    Adj_After_Matching[Tmp_For_Match.get(i).from].add(Tmp_For_Match.get(i).to);
                    Adj_After_Matching[Tmp_For_Match.get(i).to].add(Tmp_For_Match.get(i).from);

                    Matching[Tmp_For_Match.get(i).from].add(Tmp_For_Match.get(i).to);
                    Matching[Tmp_For_Match.get(i).to].add(Tmp_For_Match.get(i).from);

                    Odd_Degree[Tmp_For_Match.get(i).from] = false;
                    Odd_Degree[Tmp_For_Match.get(i).to] = false;
                }

            }

        }

        public void initMST3() {
            TSP_Path = new ArrayList<>();
            Adj_After_MST = new ArrayList[numberOfNodes + 1];
            Adj_After_MST3 = new ArrayList[numberOfNodes + 1];
            Adj_After_MST_Duplicate = new ArrayList[numberOfNodes + 1];
            Adj_After_Matching = new ArrayList[numberOfNodes + 1];
            Matching = new ArrayList[numberOfNodes + 1];
            visited = new boolean[numberOfNodes + 1];
            visited3 = new boolean[numberOfNodes + 1];
            node_in_path = new boolean[numberOfNodes + 1];
            key = new int[numberOfNodes + 1];
            key3 = new int[numberOfNodes + 1];
            parent = new int[numberOfNodes + 1];
            parent3 = new int[numberOfNodes + 1];
            Adj_After_MST[0] = new ArrayList<>();
            Adj_After_MST3[0] = new ArrayList<>();
            Adj_After_MST_Duplicate[0] = new ArrayList<>();
            Adj_After_Matching[0] = new ArrayList<>();
            Matching[0] = new ArrayList<>();
            for (int i = 1; i <= numberOfNodes; i++) {
                visited[i] = false;
                visited3[i] = false;
                node_in_path[i] = false;
                key[i] = (int) 1e6;
                key3[i] = (int) 1e6;
                Adj_After_MST[i] = new ArrayList<>();
                Adj_After_MST3[i] = new ArrayList<>();
                Adj_After_MST_Duplicate[i] = new ArrayList<>();
                Adj_After_Matching[i] = new ArrayList<>();
                Matching[i] = new ArrayList<>();
            }
            key[1] = 0;
            key3[1] = 0;
            parent[1] = -1;
            parent3[1] = -1;
        }

        int minKey3() {
            // Initialize min value
            int mn = Integer.MAX_VALUE, min_index = 0;

            for (int v = 1; v <= numberOfNodes; v++) {
                if (visited3[v] == false && key3[v] < mn) {
                    mn = key3[v];
                    min_index = v;
                }
            }
            return min_index;
        }

        //Compute Minimum Spaning Tree
        public void computeMST3() {

            initMST3();

            for (int cnt = 1; cnt <= numberOfNodes; cnt++) {
                int u = minKey3();
                visited3[u] = true;
                for (int v = 1; v <= numberOfNodes; v++) {
                    if (adj_Matrix[u][v] > 0 && visited3[v] == false && adj_Matrix[u][v] < key3[v]) {
                        parent3[v] = u;
                        key3[v] = adj_Matrix[u][v];
                    }

                }

            }

        }// End Of Compute MST

        public void graphAfterMST3() {
            for (int i = 2; i <= numberOfNodes; i++) {
                Adj_After_MST[i].add(parent[i]);
                Adj_After_MST3[i].add(parent3[i]);
                Adj_After_Matching[i].add(parent3[i]);
                Adj_After_MST_Duplicate[i].add(parent[i]);
                //cost += adj_Matrix[i][parent[i]];
                Adj_After_MST[parent[i]].add(i);
                Adj_After_MST3[parent3[i]].add(i);
                Adj_After_MST_Duplicate[parent[i]].add(i);
                Adj_After_Matching[parent3[i]].add(i);
            }
        }

        public void SolveOptimalSolution3()  {
            double total = 0;
            double startTime = System.nanoTime();
            double endTime = 0;
            cost3 = 0;
            cost_Path = 0;
            cost_Path3 = 0;
            msg3 = "";
            msg_Path3 = "";
            TSP_Path = new ArrayList<>();
            node_in_path3 = new boolean[numberOfNodes + 1];
            computeMST3();
            graphAfterMST3();
            //path.setText(msg3);
            endTime = System.nanoTime();
            total = endTime - startTime;
            repaint();
            String s = "";
            int tmp = 0;
            
            for (int i = 1; i <= numberOfNodes; i++) {
                s += i + "\n";
                for (int j = 0; j < Adj_After_MST3[i].size(); j++) {
                    s += Adj_After_MST3[i].get(j) + "   ";
                    tmp += adj_Matrix[i][Adj_After_MST3[i].get(j)];
                }
                s += "\n";
            }
            JOptionPane.showMessageDialog(null, s);
            JOptionPane.showMessageDialog(null, "The cost of Mst is : "+tmp/2);
            Odd_Degree = new boolean[numberOfNodes + 1];
            for (int i = 0; i <= numberOfNodes; i++) {
                Odd_Degree[i] = false;
            }
            startTime = System.nanoTime();
            Tmp_For_Match = new ArrayList<>();
            FindVertexWithOddDegree();

            FindGreedyMinimumMatching();
            endTime = System.nanoTime();
            total += (endTime - startTime);
            repaint();
            
            startTime = System.nanoTime();
            
            if (isAllNodeHaveEvenDegree()) {
                JOptionPane.showMessageDialog(null, "All nodes have even Degree");
            } else {
                JOptionPane.showMessageDialog(null, "Some thing is wrong and Degree is odd \n");
                return;
            }
            startTime = System.nanoTime();
            initVisitedArray3(false);
            int res = testEuler3();

            //JOptionPane.showMessageDialog(null, msg3);
            //JOptionPane.showMessageDialog(null, res);

            if (res == 2) {
                printEulerTour3();
                endTime = System.nanoTime();
                total += (endTime - startTime);
                JOptionPane.showMessageDialog(null, msg3);
                //path.setText(msg3);
                int node = TSP_Path.get(0);
                TSP_Path.add(TSP_Path.get(0));
                for (int i = 0; i < TSP_Path.size(); i++) {
                    msg_Path3 += TSP_Path.get(i) + " --> ";
                    cost_Path3 += adj_Matrix[node][TSP_Path.get(i)];
                    node = TSP_Path.get(i);
                }
                cost_Path3 += adj_Matrix[node][1];
                
                
                JOptionPane.showMessageDialog(null, msg_Path3 + "\nThe Cost is : " + cost_Path3);
                Find_TSP_Path = true;
                repaint();
                
            } else {
                return;
            }
            msg_Path3 ="The Path is:\n { "+ msg_Path3.substring(0,msg_Path3.length()-4)+" } ";
            //path.setText(msg_Path3);
            
            
            double duration = (total)/1000000000;
            String information = "";
            information = "Information : \n"+
                    "The Weight Of Minimum Spainng Tree is : "+tmp/2 + "\n"+
                    "The Weight of Graph After Greedy Matching is : "+cost3+"\n"+
                    "The Cost of Path That visit every vertex only once is : "+cost_Path3+"\n"+
                    "The Algorithm's time is : "+duration+" ms "+"\n"+
                    "And The Path is : \n"+msg_Path3+"\n";
            
            JOptionPane.showMessageDialog(null,information);
            cnt++;
            try{
            FileWriter fw = new FileWriter("result3/"+cnt);
            fw.write(information);
            fw.close();
            }catch (IOException e){}
            
            repaint();

        }

        public void initAdjMatrix() {
            for (int i = 1; i <= numberOfNodes; i++) {
                for (int j = 1; j <= numberOfNodes; j++) {
                    adj_Matrix[i][j] = 0;
                }
            }
        }

    }// end of Panel TSP

    // Class For Drawing The Graph
    public static void main(String[] args) {
        JFrame frame = new JFrame("Approximate Solution For Delta TSP Problem");

        frame.setVisible(true);
        frame.setContentPane(new TSP());
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

    }

}
