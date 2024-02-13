/**
 * Buzzer.java
 *
 * Buzzer
 */

//package ;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.DefaultKeyboardFocusManager;
import java.awt.Dimension; 
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.KeyEventDispatcher;
import java.awt.Toolkit; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.*;
import javafx.scene.input.KeyCode; 

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.ImageIcon; 
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 * @author 
 *
 */
public class Buzzer extends JFrame implements KeyListener {
    
    private static final long serialVersionUID = 7492374642744742658L;
    private static final String BUZZ_A = "BuzzA.wav";
    private static final String BUZZ_B = "BuzzB.wav";
    private static final String TIME_UP = "timeup.wav"; 
    private Clip buzz = null;
    private boolean listening = true;
    private final JTextField display1 = new JTextField(5);
    private final JTextField display2 = new JTextField(5);
    private final JButton reset = new JButton("Reset");
    ImageIcon imageA = new ImageIcon("picA.jpg");
    ImageIcon imageB = new ImageIcon("picB.jpg");
    ImageIcon Abuzz = new ImageIcon("picAbuzz.jpg"); 
    ImageIcon Bbuzz = new ImageIcon("picBbuzz.jpg"); 
    ImageIcon cdA = new ImageIcon("timerA.gif"); 
    ImageIcon cdStatA = new ImageIcon("timerAstatic.gif");
    ImageIcon cdB = new ImageIcon("timerB.gif"); 
    ImageIcon cdStatB = new ImageIcon("timerBstatic.gif");
    JLabel label = new JLabel("", imageA, JLabel.CENTER);
    JLabel label2 = new JLabel("", imageB, JLabel.CENTER);
    // JLabel labelAbuzz = new JLabel("", Abuzz, JLabel.CENTER);
    // JLabel labelBbuzz = new JLabel("", Bbuzz, JLabel.CENTER);
    JLabel labelCD1 = new JLabel("", cdStatA, JLabel.CENTER); 
    JLabel labelCD2 = new JLabel("", cdStatB, JLabel.CENTER); 
    
    {
        
        DefaultKeyboardFocusManager.getCurrentKeyboardFocusManager()
            .addKeyEventDispatcher(new KeyEventDispatcher() {
                public boolean dispatchKeyEvent(KeyEvent e) {
                    keyTyped(e);
                    keyPressed(e);
                    return false;
                }
            });
        setTitle("Buzzer");
        setLayout(new BorderLayout());
        final JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 1));
        panel.setBackground(Color.BLACK);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight(); 
        setSize((int) width, (int) height);
        setDefaultCloseOperation(EXIT_ON_CLOSE);



        final JPanel r1 = new JPanel(); 
        r1.setLayout(new GridLayout(3, 2)); 
        r1.setBackground(Color.BLACK);

        display1.setFont(new Font("Helvetica", Font.BOLD, 60));
        display1.setForeground(Color.BLACK);
        display1.setBackground(Color.GRAY);
        display1.setText("GALS");
        display1.setHorizontalAlignment(JTextField.CENTER);
        display1.setVisible(true);
        display1.setEditable(false);
        display1.setHorizontalAlignment(JTextField.CENTER);
        display2.setFont(new Font("Helvetica", Font.BOLD, 60));
        display2.setForeground(Color.BLACK);
        display2.setBackground(Color.GRAY);
        display2.setText("GUYS");
        display2.setVisible(true);
        display2.setEditable(false);
        display2.setHorizontalAlignment(JTextField.CENTER);
        
        r1.add(new JLabel()); 
        r1.add(new JLabel()); 
        r1.add( label);
        r1.add(label2);
        r1.add(labelCD1); 
        r1.add(labelCD2); 

        //panel.add(new JLabel());
        panel.add(r1); 
        //panel.add(new JLabel()); 
        //panel.add(display1);
        //panel.add(display2);
        // reset.addActionListener(new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent e) {
        //         //display1.setBackground(Color.GRAY);
        //         //display1.setText("GALS");
        //         //display2.setBackground(Color.GRAY);
        //         //display2.setText("GUYS");
        //         label.setIcon(imageA);
        //         label2.setIcon(imageB); 
        //         labelCD.setIcon(cdStat);
        //         listening = true;
        //         reset.setEnabled(false);
        //     }
        // });
        // reset.setEnabled(false);
        // add(reset, "South");
        add(panel);
        try {
            buzz = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), "FATAL ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static final void main(String args[]) {
        Buzzer b = new Buzzer();
        b.setVisible(true);
    }

    public void keyPressed(KeyEvent e) {
        /* if(e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            display1.setBackground(Color.GRAY);
            display1.setText("GALS");
            display2.setBackground(Color.GRAY);
            display2.setText("GUYS");

            label.setIcon(imageA);
            label2.setIcon(imageB); 
            labelCD.setIcon(cdStat);
            listening = true;
            reset.setEnabled(false);
        }  */
    }

    public void keyReleased(KeyEvent e) {
        // Do nothing        
    }

    public void keyTyped(KeyEvent e) {
        final char c = e.getKeyChar();
        if (listening && Character.isLetterOrDigit(c)) {
            buzz.close();
            if (c == 'a') {

                cdA.getImage().flush();
                listening = false;
                //display1.setBackground(Color.RED);
                //display1.setText("GALS");
                label.setIcon(Abuzz);
                try {
                    buzz.open(AudioSystem.getAudioInputStream(getClass().getResource(BUZZ_A)));
                    buzz.start();
                    SwingUtilities.invokeLater(() -> {
                        labelCD1.setIcon(cdA);
                        Timer timer = new Timer(8000, f -> {
                            try {
                                buzz.close(); 
                                buzz.open(AudioSystem.getAudioInputStream(getClass().getResource(TIME_UP)));
                                buzz.start(); 
                            }
                            catch (Exception ex) {
                                JOptionPane.showMessageDialog(this, ex.getLocalizedMessage(), "FATAL ERROR", JOptionPane.ERROR_MESSAGE);
                                System.exit(1);
                            }
                            reset.setEnabled(true);
                            Timer timer2 = new Timer(2000, g -> {
                                reset.setEnabled(true);
                                label.setIcon(imageA);
                                label2.setIcon(imageB); 
                                labelCD1.setIcon(cdStatA);
                                listening = true;
                                reset.setEnabled(false);
                            });
                            timer2.setRepeats(false); 
                            timer2.start(); 
                        });
                        timer.setRepeats(false);
                        timer.start();
                    });

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getLocalizedMessage(), "FATAL ERROR", JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                }
            } else if (c == 'l') {
                cdB.getImage().flush();
                listening = false;
                //display2.setBackground(Color.BLUE);
                //display2.setText("GUYS");
                label2.setIcon(Bbuzz); 
                try {
                    buzz.open(AudioSystem.getAudioInputStream(getClass().getResource(BUZZ_B)));
                    buzz.start();
                    SwingUtilities.invokeLater(() -> {
                        labelCD2.setIcon(cdB);
                        Timer timer = new Timer(8000, f -> {
                            try {
                                buzz.close(); 
                                buzz.open(AudioSystem.getAudioInputStream(getClass().getResource(TIME_UP)));
                                buzz.start(); 
                            }
                            catch (Exception ex) {
                                JOptionPane.showMessageDialog(this, ex.getLocalizedMessage(), "FATAL ERROR", JOptionPane.ERROR_MESSAGE);
                                System.exit(1);
                            }

                            Timer timer2 = new Timer(2000, g -> {
                                reset.setEnabled(true);
                                label.setIcon(imageA);
                                label2.setIcon(imageB); 
                                labelCD2.setIcon(cdStatB);
                                listening = true;
                                reset.setEnabled(false);
                            });
                            timer2.setRepeats(false); 
                            timer2.start(); 
                        });
                        timer.setRepeats(false);
                        timer.start();
                    });
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getLocalizedMessage(), "FATAL ERROR", JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                }
            }
            else {
                //do nothing
            }
        }
        
    }
    
}