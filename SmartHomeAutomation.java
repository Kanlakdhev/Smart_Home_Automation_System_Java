import java.util.*;

// Interface
interface SmartDevice {
    // Method to turn the device on
    void turnOn();
    // Method to turn off the device
    void turnOff();
    // Method to display the current status of the device
    void showStatus();
}

// Abstract Class
abstract class Appliance implements SmartDevice {
    private String name;
    private boolean isOn;

    // Constructor for Appliance class
    public Appliance(String name) {
        // Setting the name of the Appliance
        this.name = name;
        // Initializing the appliance as OFF
        this.isOn = false;
    }

    // Overriding
    @Override
    public void turnOn() {
        // Checking if the device is On
        if (!isOn) {
            // Setting the device to on
            isOn = true;
            System.out.println(name + " is now ON.");
        } else {
            System.out.println(name + " is already ON.");
        }
    }

    // Overriding the turnoff method
    @Override
    // Checking the device is off
    public void turnOff() {
        //Setting the device to off
        if (isOn) {
            isOn = false;
            System.out.println(name + " is now OFF.");
        } else {
            System.out.println(name + " is already OFF.");
        }
    }

    // Getter method to retrieve the name of the device
    public String getName() {
        return name;
    }

    // Getter method to check the device is currently On or Off
    public boolean isOn() {
        return isOn;
    }
}

// Light Class
class Light extends Appliance {
    private int brightness;

    // Constructor for Light Class
    public Light(String name) {
        super(name);
        this.brightness = 5;
    }

    // Adjusting the brightness level
    public void adjustBrightness(int level) {
        if (level >= 1 && level <= 10) {
            brightness = level;
            System.out.println(getName() + " brightness set to " + brightness + ".");
        } else {
            System.out.println("Invalid brightness level.");
        }
    }

    // Overriding the turn ON method to add the brightness
    @Override
    public void turnOn() {
        super.turnOn();
        if (isOn()) brightness = 5;
    }

    // Override the showstatus method
    @Override
    public void showStatus() {
        System.out.println(getName() + ": " + (isOn() ? "ON" : "OFF"));
        if (isOn()) {
            System.out.println("[" + "█".repeat(brightness) + "-".repeat(10 - brightness) + "] " + brightness + "/10");
        }
    }
}

// Fan Class
class Fan extends Appliance {
    private int speed;

    // Constructor for Fan Class
    public Fan(String name) {
        // Calling the Constructor
        super(name);
        // Initializing the fan speed
        this.speed = 1;
    }

    // Setting speed
    public void setSpeed(int level) {
        if (level >= 1 && level <= 5) {
            speed = level;
            System.out.println(getName() + " speed set to " + speed + ".");
        } else {
            System.out.println("Invalid speed level.");
        }
    }

    // Overriding the turn on method
    @Override
    public void turnOn() {
        // Calling the turn on method
        super.turnOn();
        // Fan is ON
        if (isOn()) speed = 1;
    }

    // Showing Status
    @Override
    public void showStatus() {
        System.out.println(getName() + ": " + (isOn() ? "ON" : "OFF"));
        if (isOn()) {
            System.out.println("[" + "█".repeat(speed) + "-".repeat(5 - speed) + "] " + speed + "/5");
        }
    }
}

// Smart Home System Class (Encapsulates all logic)
class SmartHomeSystem {
    private Map<String, SmartDevice> devices = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    private Scanner scanner = new Scanner(System.in);

    // Using switch statement printing all the menu
    public void start() {
        while (true) {
            printMenu();
            String input = scanner.nextLine();
            switch (input) {
                case "1": showAllDevices(); break;
                case "2": turnOnDevice(); break;
                case "3": turnOffDevice(); break;
                case "4": adjustDevice(); break;
                case "5": addDevice(); break;
                case "6": removeDevice(); break;
                case "7": System.out.println("Goodbye!"); return;
                default: System.out.println("Invalid input.");
            }
        }
    }

    // Printing the main menu
    private void printMenu() {
        // Choosing an Option
        System.out.println("\nChoose an option:");
        // Showing all devices
        System.out.println("1. Show All Devices");
        // Turning on device
        System.out.println("2. Turn ON Device");
        // Turning off devices
        System.out.println("3. Turn OFF Device");
        // Adjusting Devices
        System.out.println("4. Adjust Device");
        // Adding a devices
        System.out.println("5. Add a Device");
        // Removing a Device
        System.out.println("6. Remove a Device");
        // Exiting the Menu
        System.out.println("7. Exit");
        System.out.print("Please select: ");
    }

    // Showing all the devices
    private void showAllDevices() {
        System.out.println("\n*** Showing All Devices ***");
        if (devices.isEmpty()) {
            System.out.println("No devices available.");
        } else {
            for (SmartDevice device : devices.values()) {
                device.showStatus();
            }
        }
    }

    // turning on the device
    private void turnOnDevice() {
        SmartDevice device = promptDevice("turn ON");
        if (device != null) device.turnOn();
    }

    // turning off the device
    private void turnOffDevice() {
        SmartDevice device = promptDevice("turn OFF");
        if (device != null) device.turnOff();
    }

    // Adjusting a device
    private void adjustDevice() {
        SmartDevice device = promptDevice("adjust");
        if (device == null) return;

        Appliance appliance = (Appliance) device;
        if (!appliance.isOn()) {
            System.out.print(appliance.getName() + " is OFF. Turn it ON? (y/n): ");
            if (scanner.nextLine().equalsIgnoreCase("y")) {
                appliance.turnOn();
            } else {
                return;
            }
        }

        try {
            if (device instanceof Light light) {
                System.out.print("Enter brightness level [1–10]: ");
                light.adjustBrightness(Integer.parseInt(scanner.nextLine()));
            } else if (device instanceof Fan fan) {
                System.out.print("Enter speed level [1–5]: ");
                fan.setSpeed(Integer.parseInt(scanner.nextLine()));
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }

    // Adding a device
    private void addDevice() {
        System.out.print("Enter device type (Light/Fan): ");
        String type = scanner.nextLine();
        System.out.print("Enter device name: ");
        String name = scanner.nextLine();

        if (devices.containsKey(name)) {
            System.out.println("Device already exists.");
            return;
        }

        switch (type.toLowerCase()) {
            case "light" -> devices.put(name, new Light(name));
            case "fan" -> devices.put(name, new Fan(name));
            default -> System.out.println("Invalid device type.");
        }
    }

    // removing
    private void removeDevice() {
        System.out.print("Enter the name of the device to remove: ");
        String name = scanner.nextLine();
        // Removing device
        if (devices.remove(name) != null) {
            System.out.println(name + " has been removed.");
        } else {
            System.out.println("Device not found.");
        }
    }

    private SmartDevice promptDevice(String action) {
        System.out.print("Which device do you want to " + action + ": ");
        // Reading the device
        String name = scanner.nextLine();
        SmartDevice device = devices.get(name);
        if (device == null) {
            System.out.println("Device not found.");
        }
        // returning the found device
        return device;
    }
}

// Launcher Class of all class
public class SmartHomeAutomation {
    public static void main(String[] args) {
        SmartHomeSystem system = new SmartHomeSystem();
        system.start();
    }
}
