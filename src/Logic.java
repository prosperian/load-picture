import javax.swing.*;

/**
 * Created by Danial_Iranpour on 9/7/2016.
 */
public class Logic {

    public static void main(String [] args){

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Graphic();
            }
        });


    }



}
