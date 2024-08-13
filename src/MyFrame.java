import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

public class MyFrame extends JFrame {

    // Date and time formatting
    SimpleDateFormat timeFormat;
    SimpleDateFormat dayFormat;
    SimpleDateFormat dateFormat;
    
    // Labels to display clock information
    JLabel timeLabel;
    JLabel dayLabel;
    JLabel dateLabel;
    JLabel stopwatchLabel;
    
    // Strings to hold formatted time, day, and date
    String time;
    String day;
    String date;

    // Stopwatch variables
    Timer stopwatchTimer;  // Timer for updating stopwatch
    Timer clockTimer;      // Timer for updating clock
    long startTime = 0;    // Start time of the stopwatch
    long elapsedTime = 0;  // Elapsed time of the stopwatch
    boolean isRunning = false;  // Flag to check if stopwatch is running

    // Buttons for stopwatch control
    JButton startStopButton;
    JButton resetButton;

    MyFrame() {
        // Set up the JFrame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Clock Program with Stopwatch");
        this.setLayout(new FlowLayout());
        this.setSize(400, 250);
        this.setResizable(false);

        // Initialize date formatters
        timeFormat = new SimpleDateFormat("hh:mm:ss a");
        dayFormat = new SimpleDateFormat("EEEE");
        dateFormat = new SimpleDateFormat("MMMMM dd, yyyy");

        // Set up time label
        timeLabel = new JLabel();
        timeLabel.setFont(new Font("Verdana", Font.PLAIN, 50));
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setBackground(new Color(173, 216, 230));
        timeLabel.setOpaque(true);

        // Set up day label
        dayLabel = new JLabel();
        dayLabel.setFont(new Font("Ink Free", Font.PLAIN, 35));

        // Set up date label
        dateLabel = new JLabel();
        dateLabel.setFont(new Font("Ink Free", Font.PLAIN, 25));

        // Set up stopwatch label
        stopwatchLabel = new JLabel("00:00:00");
        stopwatchLabel.setFont(new Font("Verdana", Font.PLAIN, 30));

        // Set up stopwatch control buttons
        startStopButton = new JButton("Start");
        resetButton = new JButton("Reset");

        // Add action listener to start/stop button
        startStopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (isRunning) {
                    stopStopwatch();
                } else {
                    startStopwatch();
                }
            }
        });

        // Add action listener to reset button
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetStopwatch();
            }
        });

        // Add components to the frame
        this.add(timeLabel);
        this.add(dayLabel);
        this.add(dateLabel);
        this.add(stopwatchLabel);
        this.add(startStopButton);
        this.add(resetButton);

        // Initialize both clock and stopwatch
        initializeClockAndStopwatch();

        this.setVisible(true);
    }

    // Method to initialize both clock and stopwatch
    private void initializeClockAndStopwatch() {
        // Initialize clock
        clockTimer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateClock();
            }
        });
        clockTimer.start();

        // Initialize stopwatch (but don't start it yet)
        stopwatchTimer = new Timer(10, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                elapsedTime = System.currentTimeMillis() - startTime;
                updateStopwatchLabel();
            }
        });

        // Initial update for both clock and stopwatch
        updateClock();
        updateStopwatchLabel();
    }

    // Method to update the clock
    private void updateClock() {
        time = timeFormat.format(Calendar.getInstance().getTime());
        timeLabel.setText(time);

        day = dayFormat.format(Calendar.getInstance().getTime());
        dayLabel.setText(day);

        date = dateFormat.format(Calendar.getInstance().getTime());
        dateLabel.setText(date);
    }

    // Method to start the stopwatch
    private void startStopwatch() {
        startTime = System.currentTimeMillis() - elapsedTime;
        stopwatchTimer.start();
        isRunning = true;
        startStopButton.setText("Stop");
    }

    // Method to stop the stopwatch
    private void stopStopwatch() {
        stopwatchTimer.stop();
        isRunning = false;
        startStopButton.setText("Start");
    }

    // Method to reset the stopwatch
    private void resetStopwatch() {
        stopStopwatch();  // Stop the stopwatch if it's running
        elapsedTime = 0;  // Reset elapsed time
        updateStopwatchLabel();  // Update the label to show 00:00:00
    }

    // Method to update the stopwatch label
    private void updateStopwatchLabel() {
        // Calculate hours, minutes, and seconds
        long hours = elapsedTime / 3600000;
        long minutes = (elapsedTime % 3600000) / 60000;
        long seconds = (elapsedTime % 60000) / 1000;
        
        // Update the stopwatch label with formatted time
        stopwatchLabel.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
    }
}