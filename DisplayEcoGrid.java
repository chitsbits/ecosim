/* [DisplayEcoGrid.java]
 * A Small program for Display a 2D String Array graphically
 * @author Mangat, Sunny Jiao
 */

// Graphics Imports
import javax.swing.*;
import java.awt.*;

class DisplayEcoGrid { 

  private JFrame frame;
  private int maxX,maxY, GridToScreenRatio;
  private Entity[][] world;
  //GridAreaPanel worldPanel;
  
  DisplayEcoGrid(EcoSim ecosystem) { 
    this.world = ecosystem.getWorld();
    
    maxX = 1600; //Toolkit.getDefaultToolkit().getScreenSize().width;
    maxY = 900; //Toolkit.getDefaultToolkit().getScreenSize().height;
    GridToScreenRatio = maxY / (world.length+1);  //ratio to fit in screen as square map
    
    System.out.println("Map size: "+world.length+" by "+world[0].length + "\nScreen size: "+ maxX +"x"+maxY+ " Ratio: " + GridToScreenRatio);
    
    this.frame = new JFrame("EcoSim");

    GridAreaPanel worldPanel = new GridAreaPanel(ecosystem);
    
    frame.getContentPane().add(BorderLayout.CENTER, worldPanel);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(1600, 900);
    System.out.println(Toolkit.getDefaultToolkit().getScreenSize());
    //frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
    frame.setVisible(true);
  }
  
  
  public void refresh() {
    frame.repaint();
  }
  
  class GridAreaPanel extends JPanel {

    EcoSim ecosystem;

    Image sheep = Toolkit.getDefaultToolkit().getImage("sheep.png");
    Image sheeplow = Toolkit.getDefaultToolkit().getImage("sheeplow.png");
    Image wolf = Toolkit.getDefaultToolkit().getImage("wolf.png"); 
    Image wolflow = Toolkit.getDefaultToolkit().getImage("wolflow.png"); 
    Image plant = Toolkit.getDefaultToolkit().getImage("plant.png"); 
    Image plantlow = Toolkit.getDefaultToolkit().getImage("plantlow.png"); 
    Image background = Toolkit.getDefaultToolkit().getImage("background.png");

    // Text
    Font f = new Font("Arial",Font.PLAIN,30);
    JLabel labelTitle = new JLabel("EcoSim");
    JLabel labelCycles = new JLabel();    
    JLabel labelSheepCount = new JLabel();
    JLabel labelWolfCount = new JLabel();
    JLabel labelPlantCount = new JLabel();
    JLabel labelExtinction = new JLabel();

    public GridAreaPanel(EcoSim eco){
      super();
      ecosystem = eco;
    }

    public void paintComponent(Graphics g) {        
      
      setDoubleBuffered(true); 

      labelTitle.setBounds((maxX / 4) * 3, 10, 1000, 100);
      labelTitle.setFont(f);
      this.add(labelTitle);

      labelCycles.setBounds(maxX / 2 + 100, 100, 1000, 100);
      labelCycles.setFont(f);
      labelCycles.setText("# of Cycles: " + ecosystem.getCycles());
      this.add(labelCycles);

      labelSheepCount.setBounds(maxX / 2 + 100, 150, 1000, 100);
      labelSheepCount.setFont(f);
      labelSheepCount.setText("Sheep Count: " + ecosystem.getEntityCount("Sheep"));
      this.add(labelSheepCount);

      labelWolfCount.setBounds(maxX / 2 + 100, 200, 1000, 100);
      labelWolfCount.setFont(f);
      labelWolfCount.setText("Wolf Count: " + ecosystem.getEntityCount("Wolf"));
      this.add(labelWolfCount);

      labelPlantCount.setBounds(maxX / 2 + 100, 250, 1000, 100);
      labelPlantCount.setFont(f);
      labelPlantCount.setText("Plant Count: " + ecosystem.getEntityCount("Plant"));
      this.add(labelPlantCount);

      if(ecosystem.getEndOnExtinction() && ecosystem.checkExistance() != null){
        labelExtinction.setBounds(maxX / 2 + 200, 400, 1000, 100);
        labelExtinction.setFont(f);
        labelExtinction.setText(ecosystem.checkExistance() + " have gone extinct.");
        this.add(labelExtinction);
      }else{
        labelExtinction.setBounds(0, 0, 0, 0);
      }
      
      for(int i = 0; i < world[0].length; i++)
      { 
        for(int j = 0; j < world.length; j++) 
        { 
          
          if (world[i][j] instanceof Wolf){   //This block can be changed to match character-color pairs
            if(world[i][j].getHealth() < 10 || world[i][j].getAge() > (int)(0.9 * world[i][j].getMaxAge())){
                g.drawImage(wolflow,j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
            }else{
              g.drawImage(wolf,j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
            }
          }
          else if (world[i][j] instanceof Sheep){
            if(world[i][j].getHealth() < 10 || world[i][j].getAge() > (int)(0.9 * world[i][j].getMaxAge())){
              g.drawImage(sheeplow,j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
            }else{
              g.drawImage(sheep,j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
            }
          }
          else if (world[i][j] instanceof Plant){
            //g.setColor(Color.GREEN);
            if(world[i][j].getHealth() < 10 || world[i][j].getAge() > (int)(0.9 * world[i][j].getMaxAge())){
              g.drawImage(plantlow,j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
            }else{
              g.drawImage(plant,j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
            }
          }
          else {
            g.drawImage(background,j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
          }
        }
      }
    }
  }//end of GridAreaPanel
  
} //end of DisplayGrid

