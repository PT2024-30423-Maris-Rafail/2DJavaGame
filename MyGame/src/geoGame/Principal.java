//AIzaSyB4Xh0kkM0YDRuJ-zncqRhjCUUBEa4TIks
package geoGame;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Random;
import java.util.Scanner;
import Display.Panel.*;

public class Principal {
    public static void main(Display.Panel panel) {

        // Path to the folder containing the pictures
        String folderPath = "D:\\UTCN\\An II-sem I\\OOP\\PROJECT\\GEO"; // Replace with the actual folder path

        File folder = new File(folderPath);
        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".jpg") || name.toLowerCase().endsWith(".png"));

        if (files == null || files.length == 0) {
            System.out.println("No image files found in the specified folder.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int score = 0;
        int tries = 0;
        while (score < 3 && tries <5) {
            // Randomly select an image
            File selectedFile = files[random.nextInt(files.length)];

            // Display the image
            displayImage(selectedFile);

            // Prompt the user to guess
            System.out.print("Guess the name of the picture (or type 'exit' to quit): ");
            String userGuess = scanner.nextLine();

            if (userGuess.equalsIgnoreCase("exit")) {
                break;
            }

            // Check the guess (ignoring file extension)
            String correctName = selectedFile.getName().substring(0, selectedFile.getName().lastIndexOf('.'));
            if (userGuess.equalsIgnoreCase(correctName)) {
                System.out.println("Correct! You gain a point.");
                score++;
            } else {
                System.out.println("Wrong! The correct name was: " + correctName);
            }

            System.out.println("Your current score: " + score);
        }
        tries++;
        System.out.println("Game over! Your final score: " + score);
        panel.playsGeo = false;
    }

    // Method to display an image using a JFrame
    private static void displayImage(File imageFile) {
        JFrame frame = new JFrame("Guess the Picture");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(800, 600));
        try {

            ImageIcon imageIcon = new ImageIcon(imageFile.getAbsolutePath());
            Image originalImage = imageIcon.getImage();
            int width = 800;
            int height = 600;
            originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            ImageIcon scaledImage = new ImageIcon(originalImage);
            JLabel imageLabel = new JLabel(scaledImage);
            frame.add(imageLabel);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            // Close the frame automatically after a few seconds (optional)

        } catch (Exception e) {
            System.out.println("Error displaying the image: " + e.getMessage());
        }
    }


}
