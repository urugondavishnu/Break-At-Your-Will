import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class mapGenerator {
    public int brickarray[][];
    public int brickWidth;
    public int brickHeight;
    public mapGenerator(int row,int col){
        brickarray = new int[row][col];
        for(int i=0;i<brickarray.length;i++){
            for(int j=0;j<brickarray[0].length;j++){
                brickarray[i][j]=1;
            }
        }

        brickWidth = 540/col;
        brickHeight = 150/row;
    }
    public void draw(Graphics2D g){
        for(int i=0;i<brickarray.length;i++){
            for(int j=0;j<brickarray[0].length;j++){
                if(brickarray[i][j]>0){
                    if((i+j)%2==0) g.setColor(Color.white);
                    else g.setColor(Color.green);
                    g.fillRect(j*brickWidth+80,i*brickHeight+50,brickWidth,brickHeight);
                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.black);
                    g.drawRect(j*brickWidth+80,i*brickHeight+50,brickWidth,brickHeight);

                }
            }
        }
    }
    public void setBrickValue(int val,int row,int col){
        brickarray[row][col]=val;
    }
}
