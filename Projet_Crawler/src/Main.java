import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Main {

    JFrame displayZoneFrame;
    JFrame displayStart;

    public static RenderEngine renderEngine;
    public static PhysicEngine physicEngine;


    RenderEngine renderEngine2;
    GameEngine gameEngine;


    public Main() throws Exception{

        displayStart= new JFrame("Donjon Crawler");
        JPanel panelStart = new JPanel();
        panelStart.setLayout(new BoxLayout(panelStart, BoxLayout.Y_AXIS));
        panelStart.add(Box.createVerticalGlue());




        BufferedImage backgroundImage = ImageIO.read(new File("./img/background.jpg"));
        BackgroundPanel backgroundPanel = new BackgroundPanel(backgroundImage);
        backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.Y_AXIS));

        backgroundPanel.add(Box.createVerticalGlue());

        JLabel label = new JLabel("Press Start to play", JLabel.CENTER);
        label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        backgroundPanel.add(label);
        backgroundPanel.add(Box.createVerticalStrut(20));

        JButton buttonStart = new JButton("Start");
        buttonStart.setAlignmentX(JButton.CENTER_ALIGNMENT);
        backgroundPanel.add(buttonStart);

        backgroundPanel.add(Box.createVerticalStrut(10));

        JButton buttonFinish = new JButton("Finish");
        buttonFinish.setAlignmentX(JButton.CENTER_ALIGNMENT);
        backgroundPanel.add(buttonFinish);

        backgroundPanel.add(Box.createVerticalGlue());

        displayStart.setContentPane(backgroundPanel);





        displayStart.add(panelStart);


        displayStart.setSize(400,400);
        displayStart.setDefaultCloseOperation(EXIT_ON_CLOSE);
        displayStart.setLocation(600,200);
        displayZoneFrame = new JFrame("Java Labs");
        displayZoneFrame.setSize(1600,900);
        displayZoneFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);


        DynamicSprite hero = new DynamicSprite(200,300,
                ImageIO.read(new File("./img/heroTileSheetLowRes.png")),48,51);


        renderEngine2 = new RenderEngine(displayStart);
        renderEngine = new RenderEngine(displayZoneFrame);

        physicEngine = new PhysicEngine();
        gameEngine = new GameEngine(hero);

        //Timer.........
        Timer renderTimer = new Timer(50,(time)-> renderEngine.update());
        Timer gameTimer = new Timer(50,(time)-> gameEngine.update());
        Timer physicTimer = new Timer(50,(time)-> physicEngine.update());
        //...........
        renderTimer.start();
        gameTimer.start();
        physicTimer.start();


        displayStart.setVisible(true);


        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //displayStart.setVisible(false);
                displayZoneFrame.getContentPane().add(renderEngine);
                displayZoneFrame.setVisible(true);

                Playground level = new Playground("./data/level2.txt");



                ArrayList<Sprite> environmentSprites = new ArrayList<>();
                for (Displayable displayable : level.getSpriteList()) {
                    if (displayable instanceof Sprite) {
                        environmentSprites.add((Sprite) displayable);
                    }
                }


                physicEngine.setEnvironement(environmentSprites);
                physicEngine.setEnvironement(level.getSolidSpriteList());
                physicEngine.addToMovingSpriteList(hero);
                renderEngine.addToRenderList(level.getSpriteList());
                renderEngine.addToRenderList(hero);

                displayZoneFrame.addKeyListener(gameEngine);
            }
        });

        buttonFinish.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

    }

    public static void main (String[] args) throws Exception {
        // write your code here
        Main main = new Main();
    }
}