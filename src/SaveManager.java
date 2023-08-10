import java.io.*;

public class SaveManager implements Serializable{
    public static boolean trySave(Save save){
        try{
            FileOutputStream fos = new FileOutputStream(save.getOutputLocation());
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(save);
            fos.close();
            oos.close();
            return true;
       }catch(IOException e){System.out.println(e.getMessage());}
        return false;
    }
    public static Save load(String filePath){
        try{
            FileInputStream fis = new FileInputStream(filePath);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Save save = (Save) ois.readObject();
            fis.close();
            ois.close();
            return save;
        }catch(Exception e){}
        return new Save();
    }
}