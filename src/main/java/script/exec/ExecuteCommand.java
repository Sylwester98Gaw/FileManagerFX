package script.exec;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class ExecuteCommand {
    private Runtime runtime = Runtime.getRuntime();

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
}