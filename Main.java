import javax.imageio.ImageIO;

import org.bridj.cpp.std.list;
import org.bridj.relocated.org.objectweb.asm.Type;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.github.sarxos.webcam.WebcamPanel.DrawMode;

import java.awt.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.text.JTextComponent;

import java.awt.Color;
import java.awt.Dimension;
import java.io.FileWriter;
import java.io.IOError;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.ResolutionSyntax;
import javax.print.attribute.Size2DSyntax;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.PrinterResolution;

import java.awt.event.*;

public class Main {
    public static void captureWindow(JFrame window, Dimension webcamViewSize) throws IOException {
        // Webcam setup
        Webcam webcam = Webcam.getDefault();
        webcam.setViewSize(webcamViewSize);

        // Webcam panel setup
        WebcamPanel webcamPanel = new WebcamPanel(webcam);
        webcamPanel.setFPSDisplayed(false);
        webcamPanel.setDisplayDebugInfo(false);
        webcamPanel.setImageSizeDisplayed(false);
        webcamPanel.setMirrored(true);
        webcamPanel.setDrawMode(DrawMode.FILL);

        // Webcam captor setup
        JPanel webcamCaptor = new JPanel();
        webcamCaptor.setSize(320, 240);
        webcamCaptor.setLayout(new BorderLayout());
        webcamCaptor.add(webcamPanel);

        // Card image is found and scaled
        ImageIcon cardSource = new ImageIcon("resources/card.png");
        JLabel cardSwing = new JLabel(
                new ImageIcon(cardSource.getImage().getScaledInstance(506, 318, Image.SCALE_SMOOTH)));

        // Webcam Captor Captor is made (meant to set size of webcam viewport)
        JPanel webcamCaptorCaptor = new JPanel();
        webcamCaptorCaptor.setBounds(38, 81, 141, 175);
        // x = 13, y = 56
        // x = 38, y= 81
        webcamCaptor.setLocation(-100, 0);
        webcamCaptorCaptor.setLayout(null);
        webcamCaptorCaptor.add(webcamCaptor);

        // Card Captor is made to display the card
        JPanel cardCaptor = new JPanel();
        cardCaptor.setBounds(25, 25, 506, 318);
        cardCaptor.setLayout(new BorderLayout());
        cardCaptor.add(cardSwing);
        cardCaptor.setBorder(BorderFactory.createLineBorder(Color.RED, 2));

        // Capture button is made to capture webcam
        JButton captureButton = new JButton("capture");
        captureButton.setFont(new Font("SansSerif", 0, 22));
        captureButton.setForeground(Color.BLACK);
        captureButton.setBounds(210, 400, 150, 75);

        // Name text
        JTextArea nameLabel = new JTextArea("Name:");
        nameLabel.setFont(new Font("SansSerif", 0, 20));
        // nameLabel.setForeground(Color.RED);
        nameLabel.setBounds(540, 30, 55, 50);
        nameLabel.setBackground(window.getContentPane().getBackground());
        nameLabel.setEditable(false);

        // Name input box
        JTextArea nameInputBox = new JTextArea();
        nameInputBox.setFont(new Font("SansSerif", 0, 18));
        nameInputBox.setBounds(605, 30, 175, 30);
        nameInputBox.setBackground(window.getContentPane().getBackground());
        nameInputBox.setBorder(new LineBorder(Color.BLACK, 1));

        // name display
        JTextField nameDisplay = new JTextField();
        nameDisplay.setFont(new Font("SansSerif", 0, 18));
        nameDisplay.setForeground(Color.BLACK);
        nameDisplay.setText("???");
        nameDisplay.setBounds(250, 145, 175, 35);
        nameDisplay.setBorder(null);

        // set name button
        JButton setNameButton = new JButton("set");
        setNameButton.setFont(new Font("SansSerif", 0, 16));
        setNameButton.setForeground(Color.BLACK);
        setNameButton.setBounds(618, 80, 75, 35);
        setNameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent hello) {
                String name = nameInputBox.getText();
                nameDisplay.setText(name);
                window.revalidate();
                // window.repaint(); // (breaks the date for some reason)
            }
        });

        // date display
        LocalDate date = LocalDate.now();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();
        int year = date.getYear();
        String fullDate = month + "/" + day + "/" + year;
        JTextField dateDisplay = new JTextField();
        dateDisplay.setFont(new Font("SansSerif", 0, 14));
        dateDisplay.setForeground(Color.BLACK);
        dateDisplay.setText(fullDate);
        dateDisplay.setBounds(323, 195, 90, 20);
        dateDisplay.setBorder(null);

        JPanel captureContainer = new JPanel();
        captureContainer.setLayout(null);
        captureContainer.setSize(window.getSize());

        // capture button action listener
        captureButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent hello) {
                BufferedImage capture = webcam.getImage();
                System.out.println("scaling");
                Image ImageCapture = capture.getScaledInstance(320, 240, Image.SCALE_SMOOTH);
                System.out.println("creating new capture");
                capture = new BufferedImage(320, 240, BufferedImage.TYPE_INT_RGB);
                System.out.println("casting");
                capture.getGraphics().drawImage(ImageCapture, 0, 0, null);
                System.out.println("cropping");
                capture = capture.getSubimage(79, 0, 141, 175);
                System.out.println("converting to image");
                ImageCapture = capture;
                System.out.println("Flipping");
                capture.getGraphics().drawImage(ImageCapture, 0 + ImageCapture.getWidth(null), 0,
                        -ImageCapture.getWidth(null), ImageCapture.getHeight(null), null);
                System.out.println("Removing");
                window.remove(captureContainer);
                window.revalidate();
                window.repaint();
                System.out.println("Removed");
                webcam.close();
                reviewWindow(window, capture, nameDisplay.getText(), webcamViewSize);
            }
        });

        // adds the components
        captureContainer.add(cardCaptor);
        captureContainer.add(webcamCaptorCaptor);
        captureContainer.add(captureButton);
        captureContainer.add(nameLabel);
        captureContainer.add(nameInputBox);
        captureContainer.add(setNameButton);
        // window.add(setWebCamButton);
        // window.add(webCamLabel);
        // window.add(webCamInputBox);
        captureContainer.add(nameDisplay);
        captureContainer.add(dateDisplay);
        window.add(captureContainer);
        window.revalidate();
        window.repaint();

    }

    public static void reviewWindow(JFrame window, BufferedImage capture, String name, Dimension webcamViewSize) {
        System.out.println("review window");
        window.setTitle("Review Window");
        window.setLayout(null);

        // Date stuff
        LocalDate date = LocalDate.now();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();
        int year = date.getYear();
        String fullDate = month + "/" + day + "/" + year;

        // capture setup
        Image captureScaler = capture.getScaledInstance(282, 350, Image.SCALE_SMOOTH);
        BufferedImage finishedCapture = new BufferedImage(282, 350, BufferedImage.TYPE_INT_ARGB);
        Graphics2D capture2D = finishedCapture.createGraphics();
        capture2D.drawImage(captureScaler, 0, 0, null);
        capture2D.dispose();

        // card setup
        BufferedImage imageCard = null;
        try {
            imageCard = ImageIO.read(new File("C:/repos/cr80 maker/resources/card.png"));
        } catch (IOException e) {
            System.out.println("I don't wanna");
        }

        // combination
        BufferedImage combinedImage = new BufferedImage(imageCard.getWidth(), imageCard.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D combiner = combinedImage.createGraphics();
        combiner.drawImage(imageCard, 0, 0, null);
        combiner.drawImage(finishedCapture, 27, 113, null);
        combiner.setFont(new Font("SansSerif", 0, 36));
        combiner.setColor(Color.BLACK);
        combiner.drawString(name, 445, 285);
        combiner.setFont(new Font("SansSerif", 0, 28));
        combiner.drawString(fullDate, 598, 372);
        combiner.dispose();

        // Card
        ImageIcon cardSource = new ImageIcon("card.png");
        JLabel cardSwing = new JLabel(
                new ImageIcon(cardSource.getImage().getScaledInstance(506, 318, Image.SCALE_SMOOTH)));

        // Card Captor is made to display the card
        JPanel cardCaptor = new JPanel();
        cardCaptor.setBounds(25, 25, 506, 318);
        cardCaptor.setLayout(new BorderLayout());
        cardCaptor.add(cardSwing);
        cardCaptor.setBorder(BorderFactory.createLineBorder(Color.RED, 2));

        // Webcam image
        ImageIcon webcamSource = new ImageIcon(capture);
        JLabel webcamSwing = new JLabel(
                new ImageIcon(webcamSource.getImage()));

        // Webcam Image Captor is made to display the card
        JPanel webcamImageCaptor = new JPanel();
        webcamImageCaptor.setBounds(38, 81, 141, 175);
        // webcamImageCaptor.setLocation(600, 350);
        webcamImageCaptor.setLayout(new BorderLayout());
        webcamImageCaptor.add(webcamSwing);

        // name display
        JTextField nameDisplay = new JTextField();
        nameDisplay.setFont(new Font("SansSerif", 0, 18));
        nameDisplay.setForeground(Color.BLACK);
        nameDisplay.setText(name);
        nameDisplay.setBounds(250, 145, 175, 35);
        nameDisplay.setBorder(null);

        // date display
        JTextField dateDisplay = new JTextField();
        dateDisplay.setFont(new Font("SansSerif", 0, 14));
        dateDisplay.setForeground(Color.BLACK);
        dateDisplay.setText(fullDate);
        dateDisplay.setBounds(323, 195, 90, 20);
        dateDisplay.setBorder(null);

        // Retake button is made to capture webcam
        JButton captureButton = new JButton("retake");
        captureButton.setFont(new Font("SansSerif", 0, 22));
        captureButton.setForeground(Color.BLACK);
        captureButton.setBounds(210, 400, 150, 75);

        // File text
        JTextArea fileNameLabel = new JTextArea("File name:");
        fileNameLabel.setFont(new Font("SansSerif", 0, 20));
        // nameLabel.setForeground(Color.RED);
        fileNameLabel.setBounds(600, 30, 90, 30);
        fileNameLabel.setBackground(window.getContentPane().getBackground());
        fileNameLabel.setEditable(false);

        // File name input box
        JTextArea fileNameInputBox = new JTextArea();
        fileNameInputBox.setFont(new Font("SansSerif", 0, 18));
        fileNameInputBox.setBounds(537, 70, 240, 30);
        fileNameInputBox.setBackground(window.getContentPane().getBackground());
        fileNameInputBox.setBorder(new LineBorder(Color.BLACK, 1));

        // save button
        JButton setFileNameButton = new JButton("save");
        setFileNameButton.setFont(new Font("SansSerif", 0, 16));
        setFileNameButton.setForeground(Color.BLACK);
        setFileNameButton.setBounds(618, 110, 75, 35);
        setFileNameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent hello) {
                if (!(fileNameInputBox.getText().equals("card"))) {
                    try {
                        ImageIO.write(combinedImage, "png",
                                new File("C:/repos/cr80 maker/" + fileNameInputBox.getText() + ".png"));
                    } catch (IOException e) {
                        System.out.println("I don't wanna");
                    }
                }
            }
        });

        // print button
        JButton setPrinterNameButton = new JButton("print");
        setPrinterNameButton.setFont(new Font("SansSerif", 0, 16));
        setPrinterNameButton.setForeground(Color.BLACK);
        setPrinterNameButton.setBounds(618, 280, 75, 35);
        setPrinterNameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent hello) {
                try {
                    ImageIO.write(combinedImage, "png", new File("C:/repos/cr80 maker/printedCard.png"));
                } catch (IOException e) {
                    System.out.println("I don't wanna");
                }
                try {
                    // Find a PrintService
                    DocFlavor flavor = DocFlavor.INPUT_STREAM.PNG;
                    PrintService[] printServices = PrintServiceLookup.lookupPrintServices(flavor, null);
                    System.out.println(PrintServiceLookup.lookupDefaultPrintService().getName());
                    System.out.println(printServices[1].getName());
                    PrintService selectedService = printServices[1];

                    // Create a PrintRequestAttributeSet
                    PrintRequestAttributeSet attributes = new HashPrintRequestAttributeSet();
                    attributes.add(new Copies(1));
                    attributes.add(new MediaPrintableArea(0, 0, 172, 108, MediaSize.MM));
                    attributes.add(new PrinterResolution(300, 300, PrinterResolution.DPI));

                    // Create a Doc
                    File file = new File("printedCard.png");
                    FileInputStream fis = new FileInputStream(file);
                    Doc doc = new SimpleDoc(fis, flavor, null);

                    // Create a PrintJob
                    DocPrintJob printJob = selectedService.createPrintJob();
                    printJob.print(doc, attributes);

                    fis.close();
                } catch (PrintException | IOException e) {
                    e.printStackTrace();
                    System.out.println("I don't wanna print");
                }
                if (new File("C:/repos/cr80 maker/printedCard.png").delete()) {
                    System.out.println("Deleted image successfully");
                } else {

                    System.out.println("Failed to delete image!!!");
                }

            }
        });

        // review container is made
        JPanel reviewContainer = new JPanel();
        reviewContainer.setLayout(null);
        reviewContainer.setSize(window.getSize());

        // Text area is made
        JTextArea webcamViewAreaContainter = new JTextArea(
                Integer.toString(webcamViewSize.width) + "/" + Integer.toString(webcamViewSize.height));
        System.out.println("container: " + webcamViewAreaContainter.getText());
        captureButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent hello) {
                window.remove(reviewContainer);
                window.revalidate();
                window.repaint();
                String insideText = webcamViewAreaContainter.getText();
                int dimWidth = Integer.parseInt(insideText.substring(0, insideText.indexOf("/")));
                System.out.println("Width: " + dimWidth);
                int dimHeight = Integer.parseInt(insideText.substring(insideText.indexOf("/") + 1));
                System.out.println("Height: " + dimHeight);
                Dimension webcamDimenson = new Dimension(dimWidth, dimHeight);
                try {
                    captureWindow(window, webcamDimenson);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        // Parts are added and rendered
        reviewContainer.add(webcamImageCaptor);
        reviewContainer.add(cardCaptor);
        reviewContainer.add(dateDisplay);
        reviewContainer.add(nameDisplay);
        reviewContainer.add(captureButton);
        reviewContainer.add(setPrinterNameButton);
        reviewContainer.add(fileNameLabel);
        reviewContainer.add(fileNameInputBox);
        reviewContainer.add(setFileNameButton);
        window.add(reviewContainer);
        window.revalidate();
        window.repaint();

    }

    public static JFrame setup() {
        // JFrame is made to display the first part
        JFrame window = new JFrame("webcam panel");
        window.setSize(800, 600);
        window.setLayout(null);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // window.getContentPane().setBackground(Color.WHITE);
        // window.pack();
        window.setVisible(true);
        window.revalidate();
        return window;
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        Dimension webCamDimension = WebcamResolution.VGA.getSize();
        JFrame window = setup();
        captureWindow(window, webCamDimension);

    }
}