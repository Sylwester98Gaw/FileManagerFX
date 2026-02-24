package script.exec;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/*
Process process = runtime.exec(cmd);
jest przestarzały należy to zmienić
https://www.reddit.com/r/learnjava/comments/1cut6i2/how_do_i_execute_command_prompt_commands_in_java/
 */
public class ExecuteCommand {
    private Runtime runtime = Runtime.getRuntime();
    static ArrayList<String> list = new ArrayList<>();

    public ArrayList getList(){
        return list;
    }

    public void commands(String cmd) {
        try {
            Process process = runtime.exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void commands(String cmd, String path){
        try {
            Process pr = runtime.exec(cmd,null, new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    // zmienić nazwę metody bo wykonuje jednak konkretne działanie
    public void commandsWithResult(String cmd){
        try {
            Process process = runtime.exec(cmd);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            try {
                process.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String line;
            while ((line = reader.readLine()) !=null){
                list.add(line);
            }
            process.destroy();
            fixList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fixList() {
        ArrayList <String> fix = new ArrayList<>();
        fix.add("MOUNTPOINTS");
        fix.add("[SWAP]");
        fix.add("/boot/efi");
        fix.add("/boot");
        fix.add("/");
        fix.add("/home");
        list.removeIf(String::isBlank);
        for (int i = 0; i < list.size(); i++) {
            for (String s : fix) {
                if (list.get(i).equals(s)) {
                    list.remove(i);
                }
            }
        }
    }

    public void getSizes(){
// df -h /home/sylwester --output=avail and --output=used
    }
}