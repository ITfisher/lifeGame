import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
//生命游戏
public class LifeGame {
    // GUI组件
    JFrame liFrame;    // 存放细胞状态
    boolean[][] cell;
    // 显示细胞状态
    JPanel[][] cellP;
    JPanel map;//地图面板
    
   
    public LifeGame(int rows, int cols) {
        // GUI组件初始化
        liFrame = new JFrame("Life Game");
        cell = new boolean[rows][cols];
        cellP = new JPanel[rows][cols];
        liFrame.setLayout(new GridLayout(1, 2, 2, 2));
        map.setLayout(new GridLayout(rows, cols, 2, 2));
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                cellP[i][j] = new JPanel();
                cellP[i][j].setBackground(Color.WHITE);
                map.add(cellP[i][j]);
            }
        }
        liFrame.add(map);
        liFrame.setSize(500, 600);
        liFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        randomStart();
        reColor();
        liFrame.setVisible(true);
    }
   
    public void randomStart() {
        for (int i = 1; i < cell.length - 1; i++) {
            for (int j = 1; j < cell[i].length - 1; j++) {
                if (Math.random() > 0.5) {
                    cell[i][j] = true;
                } else {
                    cell[i][j] = false;
                }
            }
        }
    }
   
    public void generation() {
        for (int i = 1; i < cell.length - 1; i++) {
            for (int j = 1; j < cell[i].length - 1; j++) {
                int counter = 0;
                if (cellP[i - 1][j - 1].getBackground() == Color.BLUE) {
                    counter++;
                }
                if (cellP[i - 1][j].getBackground() == Color.BLUE) {
                    counter++;
                }
                if (cellP[i - 1][j + 1].getBackground() == Color.BLUE) {
                    counter++;
                }
                if (cellP[i][j - 1].getBackground() == Color.BLUE) {
                    counter++;
                }
                if (cellP[i][j + 1].getBackground() == Color.BLUE) {
                    counter++;
                }
                if (cellP[i + 1][j - 1].getBackground() == Color.BLUE) {
                    counter++;
                }
                if (cellP[i + 1][j].getBackground() == Color.BLUE) {
                    counter++;
                }
                if (cellP[i + 1][j + 1].getBackground() == Color.BLUE) {
                    counter++;
                }
                if (cellP[i][j].getBackground() == Color.BLUE) {
                    if (counter <= 1) {
                        cell[i][j] = false;
                    } else if (counter > 3) {
                        cell[i][j] = false;
                    }
                } else {
                    if (counter == 3) {
                        cell[i][j] = true;
                    }
                }
            }
        }
    }
   
    public void reColor() {
        for (int i = 1; i < cell.length - 1; i++) {
            for (int j = 1; j < cell[i].length - 1; j++) {
                if (cell[i][j]) {
                    cellP[i][j].setBackground(Color.BLUE);
                } else {
                    cellP[i][j].setBackground(Color.WHITE);
                }
            }
        }
    }
   
    public static void main(String[] args) {
        // 创建游戏对象
        LifeGame lg = new LifeGame(80, 80);
        // 进行初始化
        lg.randomStart();
        // 更新显示
        lg.reColor();
        while (true) {
            // 取得下一代
            lg.generation();
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 更新显示
            lg.reColor();
        }
    }
}