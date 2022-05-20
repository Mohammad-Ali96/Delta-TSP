package delta_tsp;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Draw_Graph extends JFrame {

    public static boolean press = false;

    ///////////////////////////////////////////////////////
    public Draw_Graph() {
        this.setVisible(true);
        this.setContentPane(new GraphPanel());
        this.pack();
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

    static class GraphPanel extends JPanel {

        public static final int SIZE = 20;  // radius of each node

        public Point point1 = null, point2 = null;

        public static int num = 0;

        public static ArrayList<Point> nodeList;   // Graph nodes
        private ArrayList<Edge> edgeList;    // Graph edges

        private JTable Table;
        public String MemberTable[][];
        public String MemberTable1[][];
        public double Parameter[][];
        public double ParameterHelp[][];

        private JButton print;
        private JButton save;
        private JButton clear;
        private JButton undo;
        private JButton exit;
        private JLabel label1;
        private JLabel label2;
        private JSeparator sperater1;
        private JSeparator sperater2;
        public static boolean savePressed = false;

        public static double[][] adj_Matrix = new double[1000][1000];  // Graph adjacency matrix

        public GraphPanel() {
            nodeList = new ArrayList<Point>();
            edgeList = new ArrayList<Edge>();
            adj_Matrix = new double[DELTA_TSP.TSP.numberOfNodes+1][DELTA_TSP.TSP.numberOfNodes+1];
            adj_Matrix = new double[1000][1000];
            this.setLayout(null);
            this.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.blue));
            setBackground(Color.white);
            setPreferredSize(new Dimension(1000, 650));
            GraphListener listener = new GraphListener();
            addMouseListener(listener);
            addMouseMotionListener(listener);

            print = new JButton("Show adjacency matrix");
            print.setToolTipText("show the adjacency matrix for the graph that you draw");
            print.setFont(new Font("Helvetica", Font.PLAIN, 16));
            print.setBackground(Color.LIGHT_GRAY);

            save = new JButton("Save The Graph");
            save.setToolTipText("Save The Graph that you Draw");
            save.setFont(new Font("Helvetica", Font.PLAIN, 16));
            save.setBackground(Color.LIGHT_GRAY);

            save.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    num = nodeList.size();
                    savePressed = true;

                }
            });

            exit = new JButton("Exit");
            exit.setToolTipText("Exit from project5");
            exit.setFont(new Font("Helvetica", Font.PLAIN, 16));
            exit.setBackground(Color.LIGHT_GRAY);

            clear = new JButton("clear");
            clear.setToolTipText("Clear The Graph"); // adg[][] = 0;repaint
            clear.setFont(new Font("Helvetica", Font.PLAIN, 16));
            clear.setBackground(Color.LIGHT_GRAY);

            clear.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    adj_Matrix = new double[0][0];
                    nodeList.clear();
                    edgeList.clear();

                    adj_Matrix = new double[1000][1000];
                    String Title[] = new String[1];
                    MemberTable = new String[0][1];
                    Parameter = new double[0][1];
                    ParameterHelp = new double[0][1];

                    Title[0] = "Nodes";
                    Table.setModel(new DefaultTableModel(MemberTable, Title));

                    press = false;
                    savePressed = false;
                    num = 0;
                    repaint();
                }
            });

            undo = new JButton("undo + z");
            undo.setToolTipText("undo"); // adg[][] = 0;repaint
            undo.setFont(new Font("Helvetica", Font.PLAIN, 16));
            undo.setBackground(Color.LIGHT_GRAY);
            label1 = new JLabel("Adacency Matrix For Drawing Graph");
            label1.setFont(new Font("Helvetica", Font.PLAIN, 25));

            label2 = new JLabel("Drawing The Graph");
            label2.setFont(new Font("Helvetica", Font.ITALIC, 30));

            sperater1 = new JSeparator();
            sperater2 = new JSeparator();

            print.addActionListener(new ButtonListener());

            add(print);
            add(save);
            add(clear);
            //add(exit);
            add(undo);
            add(label1);
            add(label2);
            add(sperater1);
            add(sperater2);
            print.setBounds(720, 50, 280, 30);
            save.setBounds(720, 85, 280, 30);
            undo.setBounds(720, 120, 280, 30);
            clear.setBounds(720, 155, 280, 30);
            //exit.setBounds(720, 190, 280, 30);
            label1.setBounds(100, 1, 600, 60);
            sperater1.setBounds(5, 230, 1000, 60);

            label2.setBounds(380, 225, 600, 60);
            sperater2.setBounds(370, 275, 290, 60);

            num = nodeList.size();
            String Title[] = new String[1];
            MemberTable = new String[0][1];
            Parameter = new double[0][1];
            ParameterHelp = new double[0][1];

            Title[0] = "Nodes";

            Table = new JTable(MemberTable, Title);

            Table.setFillsViewportHeight(true);

            JScrollPane scroll = new JScrollPane(Table);
            Table.setName("Table");

            this.add(scroll);
            //this.add(Table);
            scroll.setName("ADJ MATRIX");
            scroll.setBounds(13, 50, 700, 170);

        }

//  Draws the graph
        public void paintComponent(Graphics page) {
            super.paintComponent(page);

            page.setColor(Color.black);

            //page.drawRect(10, 300, 990, 30);
            // Draws the edge that is being dragged
            page.setColor(Color.red);
            //page.fillRect(10, 300, 990, 30);
            if (point1 != null && point2 != null) {
                page.drawLine(point1.x, point1.y, point2.x, point2.y);
                page.fillOval(point2.x - 3, point2.y - 3, 6, 6);
            }
// Draws the nodes      
            for (int i = 0; i < nodeList.size(); i++) {
                page.setColor(Color.black);
                page.fillOval(nodeList.get(i).x - SIZE, nodeList.get(i).y - SIZE, SIZE * 2, SIZE * 2);
                page.setColor(Color.white);

                page.drawString(String.valueOf(i + 1), nodeList.get(i).x - SIZE / 3, nodeList.get(i).y + SIZE / 3);

            }
// Draws the edges
            page.setFont(new Font("Helvetica", Font.PLAIN, 20));
            for (int i = 0; i < edgeList.size(); i++) {
                page.setColor(Color.black);
                page.drawLine(edgeList.get(i).a.x, edgeList.get(i).a.y, edgeList.get(i).b.x, edgeList.get(i).b.y);
                page.fillOval(edgeList.get(i).b.x - 3, edgeList.get(i).b.y - 3, 6, 6);

                // distance;
                Point Center = new Point((Math.abs(edgeList.get(i).b.x - edgeList.get(i).a.x) / 2), Math.abs(edgeList.get(i).b.y - edgeList.get(i).a.y) / 2);
                Point p1 = new Point(edgeList.get(i).a.x, edgeList.get(i).a.y);
                Point p2 = new Point(edgeList.get(i).b.x, edgeList.get(i).b.y);
                //page.setFont( new Font("Helvetica", Font.PLAIN, 33));
                page.setColor(Color.black);
                page.setFont(new Font("Helvetica", Font.PLAIN, 15));
                if (p2.x <= p1.x && p2.y >= p1.y) {
                    page.drawString(String.valueOf((int) edgeList.get(i).weight), edgeList.get(i).a.x - Center.x - 15, edgeList.get(i).a.y + Center.y - 6);
                } else if (p2.x > p1.x && p2.y >= p1.y) {
                    page.drawString(String.valueOf((int) edgeList.get(i).weight), edgeList.get(i).a.x + Center.x, edgeList.get(i).a.y + Center.y);
                } else if (p2.x >= p1.x && p2.y <= p1.y) {
                    page.drawString(String.valueOf((int) edgeList.get(i).weight), edgeList.get(i).a.x + Center.x - 18, edgeList.get(i).a.y - Center.y - 3);
                } else if (p2.x <= p1.x && p2.y <= p1.y) {
                    page.drawString(String.valueOf((int) edgeList.get(i).weight), edgeList.get(i).a.x - Center.x, edgeList.get(i).a.y - Center.y);
                }

            }
        }

// Euclidean distance function      
        private int distance(Point p1, Point p2) {
            return (int) Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y));
        }

//  The listener for mouse events.
        private class GraphListener implements MouseListener, MouseMotionListener {

            public void mouseClicked(MouseEvent event) {
                if (event.getX() >= 30 && event.getX() <= 970 && event.getY() >= 300 && event.getY() <= 650) {
                    nodeList.add(event.getPoint());
                    repaint();
                }
            }

            public void mousePressed(MouseEvent event) {
                if (event.getX() >= 30 && event.getX() <= 970 && event.getY() >= 300 && event.getY() <= 650) {
                    point1 = event.getPoint();
                }
            }

            public void mouseDragged(MouseEvent event) {
                if (event.getX() >= 30 && event.getX() <= 970 && event.getY() >= 300 && event.getY() <= 650) {
                    point2 = event.getPoint();
                    repaint();
                }
            }

            public void mouseReleased(MouseEvent event) {
                point2 = event.getPoint();
                if (event.getX() >= 30 && event.getX() <= 970 && event.getY() >= 300 && event.getY() <= 650) {
                    if (point1.x != point2.x && point1.y != point2.y) {
                        int u = -1, v = -1;

                        for (int j = 0; j < nodeList.size(); j++) {
                            if (distance(nodeList.get(j), point1) <= SIZE + 3) {
                                for (int k = 0; k < nodeList.size(); k++) {
                                    if (distance(nodeList.get(k), point2) <= SIZE + 3) {
                                        System.out.println(j + 1 + "->" + (k + 1));
                                        u = j + 1;
                                        v = k + 1;
                                        break;
                                        // a[j+1][k+1] = 1;
                                        // a[k+1][j+1] = 1;
                                    }
                                }
                            }
                        }

                        if (u != -1 && v != -1) {
                            String cost = JOptionPane.showInputDialog("please input the weight between nodes u = "
                                    + u + ", v = " + v);
                            double weight = 0.0;

                            weight = Double.valueOf(cost);
                            edgeList.add(new Edge(point1, point2, weight));
                            adj_Matrix[u][v] = weight;
                            adj_Matrix[v][u] = weight;
                            repaint();
                        }

                    }
                }
            }

//  Empty definitions for unused event methods.
            public void mouseEntered(MouseEvent event) {
            }

            public void mouseExited(MouseEvent event) {
            }

            public void mouseMoved(MouseEvent event) {
            }
        }

// Represents the graph edges
        private class Edge {

            Point a, b;
            double weight;

            public Edge(Point a, Point b, double weight) {
                this.a = a;
                this.b = b;
                this.weight = weight;
            }
        }

        private class ButtonListener implements ActionListener {

            public void actionPerformed(ActionEvent event) {
// Initializes graph adjacency matrix
                /*for (int i = 0; i <= nodeList.size(); i++) {
                 for (int j = 0; j <= nodeList.size(); j++) {
                 a[i][j] = 0;
                 }
                 }*/

// Includes the edges in the graph adjacency matrix
                /*for (int i = 0; i < edgeList.size(); i++) {
                 for (int j = 0; j < nodeList.size(); j++) {
                 if (distance(nodeList.get(j), edgeList.get(i).a) <= SIZE + 3) {
                 for (int k = 0; k < nodeList.size(); k++) {
                 if (distance(nodeList.get(k), edgeList.get(i).b) <= SIZE + 3) {
                 System.out.println(j+1 + "->" + k+1);
                 a[j+1][k+1] = ;
                 a[k+1][j+1] = 1;
                 }
                 }
                 }
                 }
                 }*/
                num = nodeList.size();

                String Title[] = new String[num + 1];
                MemberTable = new String[num + 1][num + 1];
                Parameter = new double[num + 1][num + 1];
                ParameterHelp = new double[num + 1][num + 1];

                Title[0] = String.valueOf("nodes");
                for (int i = 1; i <= num; i++) {
                    Title[i] = String.valueOf(i);
                }

                for (int i = 0; i < num; i++) {
                    MemberTable[i][0] = String.valueOf(i + 1);
                }

                for (int i = 1; i <= num; i++) {
                    for (int j = 1; j <= num; j++) {
                        MemberTable[i - 1][j] = String.valueOf(adj_Matrix[i][j]);
                    }
                }

                Table.setModel(new DefaultTableModel(MemberTable, Title));

// Prints the graph adjacency matrix
                for (int i = 1; i <= nodeList.size(); i++) {
                    for (int j = 1; j <= nodeList.size(); j++) {
                        System.out.print(adj_Matrix[i][j] + "\t");
                    }
                    System.out.println();
                }
            }

        }
    }

}
