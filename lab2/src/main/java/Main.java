import app.WarehouseCli;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        WarehouseCli.createDemoCli(new Scanner(System.in)).run();
    }
}
