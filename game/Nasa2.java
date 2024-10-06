package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Random;

public class Nasa2 {
    public static void main(String[] args) {
        // Create the game frame
        SpaceWeatherGame game = new SpaceWeatherGame();
        game.setVisible(true);
    }
}

class SpaceWeatherGame extends JFrame implements ActionListener {
    // Components
    private JLabel questionLabel;
    private JButton option1, option2, option3, option4;
    private JLabel feedbackLabel;
    private int score = 0;
    private int questionIndex = 0;
    private int totalQuestions;

    // Questions and answers related to the provided content
    String[][] questions = {
        // Space Weather Basics (1-10)
        {"What keeps the planets in orbit around the Sun?", "Gravity", "Solar winds", "Magnetic field", "Coronal mass ejections"},
        {"What major event occurred in May 2024?", "A historic geomagnetic storm", "Solar eclipse", "Astronauts landed on Mars", "Jupiter aligned with Mars"},
        {"Which phenomenon can interfere with communication systems?", "Solar flares", "Asteroids", "Moon phases", "Meteor showers"},
        {"What is the approximate duration of the solar cycle?", "11 years", "5 years", "20 years", "2 years"},
        {"Which organization tracks solar activity?", "NASA", "FBI", "Google", "WTO"},
        {"What is a solar flare?", "An intense burst of radiation from the Sun", "A meteor falling to Earth", "A moon passing in front of the Sun", "A comet flying past Earth"},
        {"What can a solar storm potentially disrupt on Earth?", "Satellites and power grids", "Earth's gravity", "Rainfall patterns", "Earthquakes"},
        {"Which instrument helps monitor solar flares and CMEs?", "Solar Dynamics Observatory (SDO)", "Hubble Space Telescope", "James Webb Space Telescope", "Kepler Observatory"},
        {"What is a geomagnetic storm?", "A disturbance in Earth's magnetic field caused by solar activity", "A thunderstorm in the upper atmosphere", "A black hole’s magnetic influence", "A meteor storm"},
        {"Which celestial body exerts the strongest gravitational influence in the solar system?", "The Sun", "Earth", "Jupiter", "Saturn"},
        
        // May 2024 Solar Storm (11-20)
        {"What event occurred between May 3rd and May 9th, 2024?", "A series of coronal mass ejections (CMEs)", "A meteor shower", "A lunar eclipse", "A solar eclipse"},
        {"What was the classification of the geomagnetic storm in May 2024?", "G5", "G3", "G1", "G2"},
        {"Which organization issued a rare storm warning during the May 2024 solar storm?", "NOAA", "FBI", "NASA", "ISRO"},
        {"What phenomenon was visible far beyond the polar regions during the May 2024 geomagnetic storm?", "Auroras", "Comets", "Sunspots", "Meteors"},
        {"Which project collects public reports of auroras and helps map auroral activity?", "Aurorasaurus", "Solar Flare Tracker", "Geostorm Watch", "Solar Shield"},
        {"How many significant solar flares were recorded during the May 2024 solar storm by NASA’s Solar Dynamics Observatory?", "82", "50", "25", "100"},
        {"What is the main cause of a geomagnetic storm?", "Coronal mass ejections (CMEs)", "Asteroid collisions", "Moon phases", "Planetary alignment"},
        {"Which part of Earth’s atmosphere is affected by solar storms?", "Ionosphere", "Stratosphere", "Troposphere", "Mesosphere"},
        {"What increased atmospheric drag during the May 2024 solar storm?", "Increased solar activity and density", "Asteroids", "Polar vortex", "Rising sea levels"},
        {"Which observatory played a crucial role in observing the May 2024 solar storm?", "Solar Dynamics Observatory (SDO)", "Arecibo Observatory", "Hubble Space Telescope", "Atacama Large Millimeter Array"},

        // Solar Flares, CMEs, and Space Weather Impact (21-30)
        {"What phenomenon can cause stunning auroras during a solar storm?", "Charged particles interacting with Earth's magnetic field", "Asteroids burning up in the atmosphere", "Moonlight reflecting on clouds", "Earth's gravitational pull on the Sun"},
        {"What solar event is responsible for coronal mass ejections?", "The Sun's magnetic field", "Solar eclipses", "Lunar gravitational pull", "Meteor showers"},
        {"Which layer of the Sun do coronal mass ejections originate from?", "The corona", "The photosphere", "The chromosphere", "The core"},
        {"What role do sunspots play in solar storms?", "They are regions of intense magnetic activity that can lead to solar flares", "They cool down Earth’s atmosphere", "They control the lunar cycle", "They protect Earth from meteors"},
        {"What are auroras caused by?", "Solar wind interacting with Earth’s magnetic field", "Moon phases", "Meteor showers", "Volcanic eruptions"},
        {"Which of the following is a consequence of solar storms?", "Power grid failures", "Hurricanes", "Earthquakes", "Tornadoes"},
        {"How can solar storms affect astronauts in space?", "Exposure to harmful radiation", "Freezing temperatures", "Increased gravity", "Loss of communication with Earth"},
        {"Which of the following is NOT an effect of solar storms?", "Tsunamis", "Satellite disruption", "Power grid damage", "Auroras"},
        {"What tool helps scientists predict solar activity?", "Solar telescopes and solar cycle data", "Weather balloons", "Ocean buoys", "Earthquake detectors"},
        {"What major technological infrastructure is vulnerable to solar storms?", "Satellites and communication systems", "Nuclear power plants", "Solar panels", "Hydroelectric dams"},

        // Space Exploration and International Cooperation (31-40)
        {"Which spacecraft is studying the solar wind’s effects on Earth's magnetic field?", "NASA’s Parker Solar Probe", "Voyager 1", "Apollo 11", "Curiosity Rover"},
        {"How does the European Space Agency contribute to monitoring solar activity?", "Through the Solar Orbiter mission", "Through the Mars Rover mission", "By tracking asteroids", "By sending astronauts to the ISS"},
        {"Which space mission is crucial for studying solar activity and the solar wind?", "Parker Solar Probe", "James Webb Space Telescope", "New Horizons", "Rosetta"},
        {"How long does it take for the light from the Sun to reach Earth?", "8 minutes", "1 hour", "5 seconds", "12 minutes"},
        {"Which organization is responsible for predicting space weather?", "NOAA", "FBI", "Google", "WHO"},
        {"What is a CME (Coronal Mass Ejection)?", "A massive burst of solar wind and magnetic fields released from the Sun’s corona", "A meteor burning up in the atmosphere", "A type of asteroid impact", "A volcanic eruption on the Moon"},
        {"What happens to Earth’s magnetic field during a geomagnetic storm?", "It gets disturbed and can cause disruptions in communication systems", "It strengthens, protecting the planet from all solar radiation", "It disappears completely", "It reverses"},
        {"What is the purpose of NASA’s Solar Dynamics Observatory (SDO)?", "To observe solar flares and CMEs and study their effects on space weather", "To monitor weather patterns on Mars", "To search for exoplanets", "To study black holes"},
        {"What major power outage occurred due to a geomagnetic storm?", "Quebec, 1989", "California, 1994", "Texas, 2011", "London, 2015"},
        {"What international organization helps exchange real-time space weather data?", "ISES", "UN", "ESA", "FBI"}
    };

    String[] correctAnswers = {
        // Space Weather Basics (1-10)
        "Gravity", "A historic geomagnetic storm", "Solar flares", "11 years", "NASA", 
        "An intense burst of radiation from the Sun", "Satellites and power grids", "Solar Dynamics Observatory (SDO)", "A disturbance in Earth's magnetic field caused by solar activity", "The Sun",
        
        // May 2024 Solar Storm (11-20)
        "A series of coronal mass ejections (CMEs)", "G5", "NOAA", "Auroras", "Aurorasaurus", 
        "82", "Coronal mass ejections (CMEs)", "Ionosphere", "Increased solar activity and density", "Solar Dynamics Observatory (SDO)",
        
        // Solar Flares, CMEs, and Space Weather Impact (21-30)
        "Charged particles interacting with Earth's magnetic field", "The Sun's magnetic field", "The corona", "They are regions of intense magnetic activity that can lead to solar flares", "Solar wind interacting with Earth’s magnetic field",
        "Power grid failures", "Exposure to harmful radiation", "Tsunamis", "Solar telescopes and solar cycle data", "Satellites and communication systems",
        
        // Space Exploration and International Cooperation (31-40)
        "NASA’s Parker Solar Probe", "Through the Solar Orbiter mission", "Parker Solar Probe", "8 minutes", "NOAA", 
        "A massive burst of solar wind and magnetic fields released from the Sun’s corona", "It gets disturbed and can cause disruptions in communication systems", "To observe solar flares and CMEs and study their effects on space weather", "Quebec, 1989", "ISES"
    };

    ArrayList<Integer> questionOrder = new ArrayList<>();
    Random random = new Random();

    public SpaceWeatherGame() {
        setTitle("Space Quiz"); // Updated title
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit the quiz?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0); // Exit game
                }
            }
        });
        
        ImageIcon icon = new ImageIcon("C:/Users/USER/OneDrive/Desktop/icon.png");
        setIconImage(icon.getImage());  // This sets the window icon
        // Setting a blue background
        getContentPane().setBackground(new Color(30, 144, 255));

        // Ask the user how many questions they want to answer
        String input = JOptionPane.showInputDialog(this, "How many questions would you like to answer?", "Space Quiz", JOptionPane.QUESTION_MESSAGE);
        if (input == null || input.equals("0")) {
            System.exit(0); // Exit the game
        }
        try {
            totalQuestions = Integer.parseInt(input);
            if (totalQuestions > questions.length) {
                totalQuestions = questions.length; // Limit the number of questions to available questions
            }
        } catch (NumberFormatException e) {
            totalQuestions = questions.length; // If input is invalid, ask all questions
        }

        // Create a list of question indices and shuffle them for randomness
        for (int i = 0; i < questions.length; i++) {
            questionOrder.add(i);
        }
        Collections.shuffle(questionOrder);

        // Initialize components
        questionLabel = new JLabel(questions[questionOrder.get(questionIndex)][0], JLabel.CENTER);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        questionLabel.setForeground(Color.WHITE);

        option1 = new JButton(questions[questionOrder.get(questionIndex)][1]);
        option2 = new JButton(questions[questionOrder.get(questionIndex)][2]);
        option3 = new JButton(questions[questionOrder.get(questionIndex)][3]);
        option4 = new JButton(questions[questionOrder.get(questionIndex)][4]);

        // Set colors for buttons
        JButton[] buttons = {option1, option2, option3, option4};
        for (JButton button : buttons) {
            button.setBackground(new Color(65, 105, 225));
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Arial", Font.PLAIN, 14));
        }

        feedbackLabel = new JLabel("Select an answer", JLabel.CENTER);
        feedbackLabel.setFont(new Font("Arial", Font.BOLD, 14));
        feedbackLabel.setForeground(new Color(255, 223, 0));  // Yellow color for feedback

        // Add action listeners
        option1.addActionListener(this);
        option2.addActionListener(this);
        option3.addActionListener(this);
        option4.addActionListener(this);

        // Set up the layout
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));
        panel.setBackground(new Color(30, 144, 255)); // Blue background
        panel.add(questionLabel);
        panel.add(feedbackLabel); // Feedback label under the question
        panel.add(option1);
        panel.add(option2);
        panel.add(option3);
        panel.add(option4);
       

        add(panel, BorderLayout.CENTER);

       // add(panel, BorderLayout.CENTER);
        //add(feedbackLabel, BorderLayout.SOUTH);
    }

    // Handle button clicks
    public void actionPerformed(ActionEvent e) {
        JButton selectedOption = (JButton) e.getSource();
        String selectedAnswer = selectedOption.getText();

        // Check if the answer is correct
        if (selectedAnswer.equals(correctAnswers[questionOrder.get(questionIndex)])) {
            feedbackLabel.setText("Correct!");
            feedbackLabel.setForeground(new Color(50, 205, 50)); // Green color for correct answer
            score++;
        } else {
            feedbackLabel.setText("Incorrect! The correct answer is: " + correctAnswers[questionOrder.get(questionIndex)]);
            feedbackLabel.setForeground(Color.RED); // Red color for incorrect answer
            // Play a sound for incorrect answer
            Toolkit.getDefaultToolkit().beep();
        }

        // Move to the next question or end the game
        questionIndex++;
        if (questionIndex < totalQuestions) {
            updateQuestion();
        } else {
            endGame();
        }
    }

    // Update question and options
    private void updateQuestion() {
        questionLabel.setText(questions[questionOrder.get(questionIndex)][0]);
        option1.setText(questions[questionOrder.get(questionIndex)][1]);
        option2.setText(questions[questionOrder.get(questionIndex)][2]);
        option3.setText(questions[questionOrder.get(questionIndex)][3]);
        option4.setText(questions[questionOrder.get(questionIndex)][4]);
    }

    // End the game and show the final score
    private void endGame() {
        JOptionPane.showMessageDialog(this, "Game Over! Your score is: " + score + "/" + totalQuestions);
        System.exit(0);
    }
}
