/*
 * Copyright (c) 2024. This code is my creation, my passion, and my contribution to the community. It's open for all to use, learn, and grow from. I sign it with my name proudly, committed to supporting freedom and collaboration in the world of programming.
 */

package script.helpers;

import java.io.File;
import java.util.ArrayList;

public class AddToList {
    public static ArrayList<File> list = new ArrayList<>();

    public void addSelectedToList(String item){
        list.add(new File(item));
        System.out.println(list);
    }
    public void removeSelectedFromList(String item){
        list.remove(new File(item));
    }
    public void removeAll(){
        try {
            for (int i = 0; i < list.size(); i++) {
                list.remove(i);
            }
            if (!list.isEmpty()){
                removeAll();
            }
        }catch (Exception e){
            e.getStackTrace();
        }
    }
    public boolean checkItem (String item){
        return list.contains(new File(item));
    }
}