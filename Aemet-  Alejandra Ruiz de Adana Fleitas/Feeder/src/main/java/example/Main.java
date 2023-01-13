package example;


import java.io.File;

public class Main {
    public static void main(String[] args){
        String datalakeRoot = args[0];
        new Controller(new AemetLoader(), new FileSystemDatalake(new File(datalakeRoot))).start();
    }
}