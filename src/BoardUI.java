

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class BoardUI extends JPanel implements ActionListener {
    private Board board;
    private JButton[][] boardButtons;
    private HashMap<String, ImageIcon> icons;

    private Boolean isMoving;
    private String start;
    private String end;

    //extras
    DefaultTableModel movesModel = new DefaultTableModel(0, 0);
    private int moveCounter = 2;

    public BoardUI(Board board) {
        super();

        setVisible(true);
        setSize(640, 490);
        setPreferredSize(getSize());
        setLayout(null);

        this.board = board;
        this.icons = new HashMap<>();
        this.isMoving = false;
        this.start = "";
        this.end = "";

        try {
            this.icons.put("bK", new ImageIcon(ImageIO.read(new File("res/black/king.png")).getScaledInstance(424 / 8, 416 / 8, java.awt.Image.SCALE_SMOOTH)));
            this.icons.put("bQ", new ImageIcon(ImageIO.read(new File("res/black/queen.png")).getScaledInstance(424 / 8, 416 / 8, java.awt.Image.SCALE_SMOOTH)));
            this.icons.put("bR", new ImageIcon(ImageIO.read(new File("res/black/rook.png")).getScaledInstance(424 / 8, 416 / 8, java.awt.Image.SCALE_SMOOTH)));
            this.icons.put("bN", new ImageIcon(ImageIO.read(new File("res/black/knight.png")).getScaledInstance(424 / 8, 416 / 8, java.awt.Image.SCALE_SMOOTH)));
            this.icons.put("bB", new ImageIcon(ImageIO.read(new File("res/black/bishop.png")).getScaledInstance(424 / 8, 416 / 8, java.awt.Image.SCALE_SMOOTH)));
            this.icons.put("bp", new ImageIcon(ImageIO.read(new File("res/black/pawn.png")).getScaledInstance(424 / 8, 416 / 8, java.awt.Image.SCALE_SMOOTH)));

            this.icons.put("wK", new ImageIcon(ImageIO.read(new File("res/white/king.png")).getScaledInstance(424 / 8, 416 / 8, java.awt.Image.SCALE_SMOOTH)));
            this.icons.put("wQ", new ImageIcon(ImageIO.read(new File("res/white/queen.png")).getScaledInstance(424 / 8, 416 / 8, java.awt.Image.SCALE_SMOOTH)));
            this.icons.put("wR", new ImageIcon(ImageIO.read(new File("res/white/rook.png")).getScaledInstance(424 / 8, 416 / 8, java.awt.Image.SCALE_SMOOTH)));
            this.icons.put("wN", new ImageIcon(ImageIO.read(new File("res/white/knight.png")).getScaledInstance(424 / 8, 416 / 8, java.awt.Image.SCALE_SMOOTH)));
            this.icons.put("wB", new ImageIcon(ImageIO.read(new File("res/white/bishop.png")).getScaledInstance(424 / 8, 416 / 8, java.awt.Image.SCALE_SMOOTH)));
            this.icons.put("wp", new ImageIcon(ImageIO.read(new File("res/white/pawn.png")).getScaledInstance(424 / 8, 416 / 8, java.awt.Image.SCALE_SMOOTH)));

            this.icons.put("~bK", new ImageIcon(ImageIO.read(new File("res/shdw/black/king.png")).getScaledInstance(424 / 8, 416 / 8, java.awt.Image.SCALE_SMOOTH)));
            this.icons.put("~bQ", new ImageIcon(ImageIO.read(new File("res/shdw/black/queen.png")).getScaledInstance(424 / 8, 416 / 8, java.awt.Image.SCALE_SMOOTH)));
            this.icons.put("~bR", new ImageIcon(ImageIO.read(new File("res/shdw/black/rook.png")).getScaledInstance(424 / 8, 416 / 8, java.awt.Image.SCALE_SMOOTH)));
            this.icons.put("~bN", new ImageIcon(ImageIO.read(new File("res/shdw/black/knight.png")).getScaledInstance(424 / 8, 416 / 8, java.awt.Image.SCALE_SMOOTH)));
            this.icons.put("~bB", new ImageIcon(ImageIO.read(new File("res/shdw/black/bishop.png")).getScaledInstance(424 / 8, 416 / 8, java.awt.Image.SCALE_SMOOTH)));
            this.icons.put("~bp", new ImageIcon(ImageIO.read(new File("res/shdw/black/pawn.png")).getScaledInstance(424 / 8, 416 / 8, java.awt.Image.SCALE_SMOOTH)));

            this.icons.put("~wK", new ImageIcon(ImageIO.read(new File("res/shdw/white/king.png")).getScaledInstance(424 / 8, 416 / 8, java.awt.Image.SCALE_SMOOTH)));
            this.icons.put("~wQ", new ImageIcon(ImageIO.read(new File("res/shdw/white/queen.png")).getScaledInstance(424 / 8, 416 / 8, java.awt.Image.SCALE_SMOOTH)));
            this.icons.put("~wR", new ImageIcon(ImageIO.read(new File("res/shdw/white/rook.png")).getScaledInstance(424 / 8, 416 / 8, java.awt.Image.SCALE_SMOOTH)));
            this.icons.put("~wN", new ImageIcon(ImageIO.read(new File("res/shdw/white/knight.png")).getScaledInstance(424 / 8, 416 / 8, java.awt.Image.SCALE_SMOOTH)));
            this.icons.put("~wB", new ImageIcon(ImageIO.read(new File("res/shdw/white/bishop.png")).getScaledInstance(424 / 8, 416 / 8, java.awt.Image.SCALE_SMOOTH)));
            this.icons.put("~wp", new ImageIcon(ImageIO.read(new File("res/shdw/white/pawn.png")).getScaledInstance(424 / 8, 416 / 8, java.awt.Image.SCALE_SMOOTH)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.boardButtons = new JButton[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                JButton button = new JButton();
                button.setBounds(i * 424 / 8 + 36, j * 416 / 8 + 40, 424 / 8, 416 / 8);
                button.setActionCommand((char) ('a' + i) + "" + (8 - j));
                button.setContentAreaFilled(false);
                button.setBorderPainted(false);

                button.addActionListener(this);

                if (board.getBoard()[j][i] != null) {
                    button.setIcon(icons.get(board.getBoard()[j][i].getChessPiece().getType()));
                }

                add(button);
                boardButtons[j][i] = button;
            }
        }

        movesModel.setColumnIdentifiers(new String[]{"White", "Black"});
        JTable tbl = new JTable(movesModel);

        JScrollPane scrollPane = new JScrollPane(tbl);
        scrollPane.setBounds(500, 0, 160, 500);
        scrollPane.setBackground(new Color(99, 66, 44));
        scrollPane.setVisible(true);

        add(scrollPane);
        createFrame();
    }

    private void update() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                JButton button = boardButtons[j][i];
                if (board.getBoard()[j][i] != null) {
                    button.setIcon(icons.get(board.getBoard()[j][i].getChessPiece().getType()));
                    if (isMoving && j == 8 - Integer.parseInt(String.valueOf(start.charAt(1))) && i == Integer.parseInt(String.valueOf(start.charAt(0) - 'a')))
                        button.setIcon(icons.get("~" + board.getBoard()[j][i].getChessPiece().getType()));
                } else {
                    button.setIcon(null);
                }
            }
        }
    }

    private void createFrame() {
        JFrame frame = new JFrame();

        frame.setContentPane(this);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            g.drawImage(ImageIO.read(new File("res/board.png")), 0, 0, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());
        if (!isMoving) {
            if (board.getBoard()[8 - Integer.parseInt(String.valueOf(e.getActionCommand().charAt(1)))][Integer.parseInt(String.valueOf(e.getActionCommand().charAt(0) - 'a'))] != null) {
                start = e.getActionCommand();
                isMoving = true;
            }
        } else {
            if (start.equals(e.getActionCommand())) {
                isMoving = false;
            } else {
                end = e.getActionCommand();

                if (board.getBoard()[8 - Integer.parseInt(String.valueOf(start.charAt(1)))][Integer.parseInt(String.valueOf(start.charAt(0) - 'a'))].getChessPiece().getType().charAt(1) == 'K' &&
                        (end.charAt(0) == 'g' || end.charAt(0) == 'c')) {
                    if (!board.castle(String.valueOf(end.charAt(0)))) {
                        isMoving = false;
                        if (board.getCurrentPlayer() == 1)
                            movesModel.addRow(new String[]{moveCounter / 2 + "- castle " + end.charAt(0) + " X", ""});
                        else
                            movesModel.addRow(new String[]{"", moveCounter / 2 + "- castle " + end.charAt(0) + " X"});


                        //bad move
                    } else {
                        if (board.getCurrentPlayer() == 1)
                            movesModel.addRow(new String[]{moveCounter / 2 + "- castle " + end.charAt(0) + " ", ""});
                        else
                            movesModel.addRow(new String[]{"", moveCounter / 2 + "- castle " + end.charAt(0) + " "});
                        board.setCurrentPlayer(board.getCurrentPlayer() == 1 ? 2 : 1);

                        isMoving = false;
                        moveCounter++;
                    }
                } else {

                    if (!board.move(start, end)) {
                        //bad move
                        if (board.getCurrentPlayer() == 1)
                            movesModel.addRow(new String[]{moveCounter / 2 + "- " + start + " " + end + " X", ""});
                        else
                            movesModel.addRow(new String[]{"", moveCounter / 2 + "- " + start + " " + end + " X"});
                        isMoving = false;
                    } else {
                        if (board.getCurrentPlayer() == 1)
                            movesModel.addRow(new String[]{moveCounter / 2 + "- " + start + " " + end + "", ""});
                        else
                            movesModel.addRow(new String[]{"", moveCounter / 2 + "- " + start + " " + end + ""});
                        board.setCurrentPlayer(board.getCurrentPlayer() == 1 ? 2 : 1);

                        moveCounter++;
                        isMoving = false;
                    }
                }
            }
        }
        update();
    }
}
