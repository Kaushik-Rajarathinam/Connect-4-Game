import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;

public class ConnectFourGame {
    
    private ConnectFourGrid grid = new ConnectFourGrid();
    private JFrame frame = new JFrame("Connect Four");
    private JPanel panel;
    private boolean botTurn=false;
    
    //private boolean blackTurn = true;
    //private int checkersPlayed = 0;
    //private int redWins = 0;
    //private int blackWins = 0;
    
    
    
    public static void main(String[] args){
        new ConnectFourGame().start();
    }
    private void start() {
        setUpPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
    private void setUpPanel() {
        panel = new JPanel(){
            @Override
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                grid.draw(g);
                drawGameInfo(g);
            }
        };
        panel.addMouseListener(new MouseInputAdapter(){
            @Override
            public void mousePressed(MouseEvent me){
                clickedAt(me);
            }
        });
        panel.setPreferredSize(new Dimension(1000, 1200));
        panel.setBackground(Color.white);
        frame.add(panel);
        frame.pack();


    }
    protected void clickedAt(MouseEvent me) {
        int x = grid.colFromX(me.getX());
        int r = (int) ((Math.random() * (6)));
        if (botTurn==true){
          System.out.println("Bot picked column "+r);
          grid.colClicked(r);
          botTurn=false;
        }
        else {
        System.out.println("You just clicked at column "+x);
        grid.colClicked(x);
        botTurn=true;
        }  
        
        frame.repaint();
        
    }
    /**
     * Who's turn is it?  How many Checkers played?  Who has won the most games?
     * @param g
     */
    protected void drawGameInfo(Graphics g) {
      g.setColor(Color.black);
    if (grid.getTurn()){
      g.drawString("Turn: Black",300,10);
    }
    else {
      g.drawString("Turn: Red",300,10);
    }
    g.drawString("Plays: "+grid.getCheckersPlayed(),300,30);
    g.drawString("Black Wins: "+grid.getBlackWins(),300,50);
    g.drawString("Red Wins: "+grid.getRedWins(),300,70);
      

    }
  /**
    * Reset the game so that it is ready to be played again.
    */
    public void resetGame(){
      grid.clearBoard();


    }

}
