package dmpinyar.scan_thread;

import java.util.Scanner;

public class ScanThread extends Thread {
    private Scanner scanner;
    private volatile String scanLine;
    
    public ScanThread(Scanner scanner){
        this.scanner = scanner;
    }

    public String getLine(){
        return scanLine;
    }

    public void run() {
        while(true) {
            scanLine = scanner.nextLine();
        }
    }
}