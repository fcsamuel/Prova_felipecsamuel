package prova_felipecsamuel;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Felipe
 */
public class Prova_Felipecsamuel {

    public static void main(String[] args) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy"); //você pode usar outras máscaras 
        Date y = new Date();
        System.out.println(sdf1.format(y));
    }
    
}
