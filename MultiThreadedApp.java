import java.util.Random;

class SharedData {
    private int number;
    private boolean isNewNumber = false;

    // Set number (Producer)
    public synchronized void setNumber(int number) {
        while (isNewNumber) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        this.number = number;
        isNewNumber = true;
        notifyAll();
    }

    // Get number (Consumer)
    public synchronized int getNumber() {
        while (!isNewNumber) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        isNewNumber = false;
        notifyAll();
        return number;
    }
}

class FirstThread extends Thread {
    private SharedData sharedData;
    private Random random = new Random();

    public FirstThread(SharedData sharedData) {
        this.sharedData = sharedData;
    }

    public void run() {
        while (true) {
            int num = random.nextInt(100) + 1;
            sharedData.setNumber(num);

            System.out.println("Generated Number: " + num);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class SecondThread extends Thread {
    private SharedData sharedData;

    public SecondThread(SharedData sharedData) {
        this.sharedData = sharedData;
    }

    public void run() {
        while (true) {
            int num = sharedData.getNumber();

            if (num % 2 == 0) {
                System.out.println("Square of " + num + " = " + (num * num));
            } else {
                // If not for this thread, give chance to other thread
                sharedData.setNumber(num);
            }
        }
    }
}

class ThirdThread extends Thread {
    private SharedData sharedData;

    public ThirdThread(SharedData sharedData) {
        this.sharedData = sharedData;
    }

    public void run() {
        while (true) {
            int num = sharedData.getNumber();

            if (num % 2 != 0) {
                System.out.println("Cube of " + num + " = " + (num * num * num));
            } else {
                // Pass back if not used
                sharedData.setNumber(num);
            }
        }
    }
}

public class MultiThreadedApp {
    public static void main(String[] args) {
        SharedData sharedData = new SharedData();

        FirstThread t1 = new FirstThread(sharedData);
        SecondThread t2 = new SecondThread(sharedData);
        ThirdThread t3 = new ThirdThread(sharedData);

        t1.start();
        t2.start();
        t3.start();
    }
}