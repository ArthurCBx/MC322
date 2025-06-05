package lab05;

import java.io.*;

public class Teste {
    public static void main(String[] args) {
        String file = "semanais/src/lab05/log.txt";
        String message = "Hello, World!";

        try {
            DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
            out.writeBytes(message);
            out.writeBytes("\n");
            out.writeBytes("\n");
            out.writeBytes("This is a test message.");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            String inLine;
            while ((inLine = in.readLine()) != null) {
                System.out.println(inLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}