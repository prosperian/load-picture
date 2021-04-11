import com.sun.prism.*;
import com.sun.prism.Graphics;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Danial_Iranpour on 9/7/2016.
 */
public class Graphic extends JFrame implements ActionListener {

    private Button addButton;
    private JPanel picture;
    private BufferedImage image = null;

    static final int HW=30;

    public Graphic(){

        setSize(new Dimension(500,500));

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        addButton = new Button("Add");
        addButton.addActionListener(this);
        addButton.setActionCommand("add");
        addButton.setBackground(Color.WHITE);
        getContentPane().add(addButton,BorderLayout.NORTH);

        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getActionCommand().equals("add")){

            if(picture!=null) getContentPane().remove(picture);

            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files","jpg","png","jpeg");/////////////////////////
            fileChooser.setFileFilter(filter);
            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            fileChooser.showOpenDialog(this);

            try {
                image = ImageIO.read(fileChooser.getSelectedFile().getAbsoluteFile());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            setSize(new Dimension(image.getWidth()+16,image.getHeight()+62));

            ArrayList<BufferedImage> images = new ArrayList<>();
            ArrayList<Integer> xs = new ArrayList<>();
            ArrayList<Integer> ys = new ArrayList<>();
            Map<Integer,Integer> map = new LinkedHashMap<>();

            picture = new JPanel(){
                @Override
                protected void paintComponent(java.awt.Graphics g) {
                    super.paintComponent(g);

                    for (int i = 0; i <images.size() ; i++) {
                        g.drawImage(images.get(i),xs.get(i),ys.get(i),HW,HW,null);
                    }

                }
            };

            getContentPane().add(picture, BorderLayout.CENTER);

            Thread worker = new Thread(new Runnable() {

                @Override
                public void run() {

                    Random rnd = new Random();

                    int x,y;

                    while(true) {

                        x = rnd.nextInt(image.getWidth());
                        y = rnd.nextInt(image.getHeight());

                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }

                        if( x+HW <= image.getWidth() && y+HW <= image.getHeight()) {

                            xs.add(x);
                            ys.add(y);
                            map.put(x,y);
                            images.add(image.getSubimage(x, y, HW, HW));

                        }

                        picture.repaint();

                    }
                }
            });

            worker.start();

        }
    }
}
