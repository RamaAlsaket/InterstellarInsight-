package nasa;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ChatBotInterface {
	private static JTextArea chatArea;
	private static JTextField userInput;
	private static Map<String, String> learningMap = new HashMap<>(); // Stores learned questions and answers
	private static final String LEARNING_FILE = "chatbot_memory.txt"; // File to save learned conversations

	public static void main(String[] args) {
		// Load previous learning data
		loadLearningData();
		// Create the frame
		JFrame frame = new JFrame("Space Weather Chatbot");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 500);
		frame.setLocationRelativeTo(null); // Center the frame
		// Create a custom panel with a background image
		BackgroundPanel panel = new BackgroundPanel();
		panel.setLayout(new BorderLayout());
		// Chat area (non-editable)
		chatArea = new JTextArea();
		chatArea.setEditable(false);
		chatArea.setLineWrap(true);
		chatArea.setWrapStyleWord(true);
		chatArea.setBackground(new Color(0, 0, 0, 150)); // Semi-transparent background for chat area
		chatArea.setForeground(Color.WHITE); // Set text color to white
		chatArea.setFont(new Font("Monospaced", Font.PLAIN, 14)); // Use a monospaced font
		// Scrollable chat area
		JScrollPane scrollPane = new JScrollPane(chatArea);
		scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding
		panel.add(scrollPane, BorderLayout.CENTER);
		// User input area
		userInput = new JTextField();
		userInput.setBackground(new Color(255, 255, 255, 200)); // Slightly transparent background
		userInput.setForeground(Color.BLACK); // Set text color for input area
		userInput.setFont(new Font("Monospaced", Font.PLAIN, 14)); // Same font as chat area
		userInput.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY, 1),
				"Type your message here:")); // Add a titled border
		userInput.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				respondToUser();
			}
		});
		panel.add(userInput, BorderLayout.SOUTH);
		// Initial greeting
		chatArea.append(
				"SpaceBot: Hello! I'm here to talk about space weather, solar storms, and what happened in May 2024. How can I assist you?\n");
		// Set focus on user input field
		userInput.requestFocusInWindow();
		// Add panel to frame
		frame.add(panel);
		frame.setVisible(true);
	}

	private static void respondToUser() {
		String userMessage = userInput.getText().toLowerCase();
		if (userMessage.isEmpty()) {
			return;
		}
		chatArea.append("You: " + userMessage + "\n");
		userInput.setText("");
		// Check if we have learned something similar
		for (String key : learningMap.keySet()) {
			if (userMessage.contains(key)) {
				chatArea.append("SpaceBot: " + learningMap.get(key) + "\n");
				return;
			}
		}
		// Enhanced response logic
		String response = generateResponse(userMessage);
		chatArea.append("SpaceBot: " + response + "\n");
		// Save the new learning if the user provided new information
		if (!learningMap.containsKey(userMessage)) {
			saveLearning(userMessage, response);
		}
	}

	private static String generateResponse(String userMessage) {
		if (userMessage.contains("space weather")) {
			return "Space weather refers to environmental conditions in space influenced by the Sun's activity, such as solar wind and magnetic fields. These conditions can impact satellites, power grids, and communication systems on Earth.";
		} else if (userMessage.contains("solar storm")) {
			return "A solar storm is caused by solar activity like solar flares and coronal mass ejections (CMEs). These storms can release high-energy particles, affecting Earth's magnetosphere and potentially disrupting technology.";
		} else if (userMessage.contains("may 2024")) {
			return "In May 2024, a historic G5 geomagnetic storm occurred due to a series of CMEs between May 3rd and May 9th. It resulted in stunning auroras visible as far south as India.";
		} else if (userMessage.contains("planets")) {
			return "Our solar system consists of eight planets: Mercury, Venus, Earth, Mars, Jupiter, Saturn, Uranus, and Neptune.";
		} else if (userMessage.contains("sun")) {
			return "The Sun is the closest star to Earth and the driving force behind all solar activity. It influences everything in the solar system, affecting seasons, weather patterns, and space weather events.";
		} else if (userMessage.contains("auroras")) {
			return "Auroras are caused by solar storms and can be stunning to witness. During the May 2024 storm, they were visible in regions where they are rarely seen.";
		} else if (userMessage.contains("preparedness")) {
			return "To prepare for solar storms, it's essential to enhance prediction models and improve satellite designs.";
		} else if (userMessage.contains("satellite protection")) {
			return "Satellites are equipped with protective measures against solar radiation, and operators follow protocols to reposition or protect them during geomagnetic storms.";
		} else if (userMessage.contains("space weather prediction")) {
			return "Predicting space weather involves monitoring solar activity like sunspots, flares, and CMEs, using data from observatories and space missions.";
		} else if (userMessage.contains("sunspots")) {
			return "Sunspots are dark areas on the Sun’s surface caused by magnetic field disturbances. Their number increases during periods of high solar activity, known as the solar maximum.";
		} else if (userMessage.contains("solar radiation")) {
			return "Solar radiation is the energy emitted by the Sun. During solar flares and coronal mass ejections, this radiation increases and can impact space exploration and technology on Earth.";
		} else if (userMessage.contains("geomagnetic induced currents")) {
			return "Geomagnetic induced currents (GICs) are electric currents generated in power grids and pipelines during geomagnetic storms, potentially damaging infrastructure.";
		} else if (userMessage.contains("power grids")) {
			return "Geomagnetic storms can induce currents in power grids, causing outages or damage. Strengthening infrastructure is essential to protect against these effects.";
		} else if (userMessage.contains("coronal holes")) {
			return "Coronal holes are regions of the Sun’s atmosphere where solar wind escapes at high speeds, contributing to space weather and geomagnetic storms.";
		} else if (userMessage.contains("solar observatories")) {
			return "Solar observatories monitor the Sun’s activity, including flares, CMEs, and sunspots, providing real-time data crucial for space weather forecasting.";
		} else if (userMessage.contains("magnetosphere")) {
			return "Earth's magnetosphere is the region of space dominated by its magnetic field. It shields us from solar wind, but during solar storms, it can be disturbed, causing geomagnetic storms.";
		} else if (userMessage.contains("mars atmosphere")) {
			return "Mars lacks a strong magnetic field, making its atmosphere more vulnerable to solar radiation and space weather, which could affect future human missions.";
		} else if (userMessage.contains("earth's magnetic field")) {
			return "Earth’s magnetic field protects us from harmful solar radiation. When solar storms interact with the magnetic field, they can cause geomagnetic storms and auroras.";
		} else if (userMessage.contains("spacecraft")) {
			return "Spacecraft are designed to withstand solar radiation, but during intense solar storms, astronauts and electronics are at greater risk of exposure and malfunction.";
		} else if (userMessage.contains("technology disruptions")) {
			return "Technology, especially satellites, GPS, and communication systems, can be disrupted by geomagnetic storms caused by solar flares or CMEs.";
		} else if (userMessage.contains("solar wind streams")) {
			return "Solar wind streams, made of charged particles from the Sun, interact with Earth's magnetic field and can cause minor geomagnetic storms.";
		} else if (userMessage.contains("geomagnetic storm effects")) {
			return "The effects of geomagnetic storms include beautiful auroras, but they can also damage satellites, disrupt communications, and impact power grids.";
		} else if (userMessage.contains("public awareness campaigns")) {
			return "Public awareness campaigns help communities understand the risks of space weather and prepare for events like geomagnetic storms that can impact technology and infrastructure.";
		} else if (userMessage.contains("g5 storm")) {
			return "A G5 geomagnetic storm is the most severe, capable of causing widespread power outages, satellite damage, and stunning auroras visible far from the poles.";
		} else if (userMessage.contains("international space environment service")) {
			return "The International Space Environment Service (ISES) coordinates global efforts to monitor space weather and provides real-time data to help predict solar storms.";
		} else if (userMessage.contains("aurora effects")) {
			return "Auroras are stunning visual displays caused by geomagnetic storms when charged solar particles collide with Earth’s atmosphere, usually seen near the poles.";
		} else if (userMessage.contains("solar cycle 25")) {
			return "Solar cycle 25 began in 2019 and is expected to reach its peak in the mid-2020s, marked by increased solar activity such as flares and geomagnetic storms.";
		} else if (userMessage.contains("electromagnetic radiation")) {
			return "Solar flares emit electromagnetic radiation, which can impact communication systems on Earth and interfere with satellite operations.";
		}

		else if (userMessage.contains("bye") || userMessage.contains("exit")) {
			return "Goodbye! Stay curious about the universe!";
		} else {
			return "I’m not sure I understand. Could you ask something else about space weather or our solar system?";
		}
	}

	private static void saveLearning(String question, String answer) {
		learningMap.put(question, answer);
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(LEARNING_FILE, true))) {
			writer.write(question + ":" + answer + "\n");
		} catch (IOException e) {
			chatArea.append("Error saving learning data.\n");
		}
	}

	private static void loadLearningData() {
		File file = new File(LEARNING_FILE);
		if (file.exists()) {
			try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
				String line;
				while ((line = reader.readLine()) != null) {
					String[] parts = line.split(":");
					if (parts.length == 2) {
						learningMap.put(parts[0], parts[1]);
					}
				}
			} catch (IOException e) {
				System.out.println("Error loading learning data.");
			}
		}
	}

	// Custom panel to paint background image
	static class BackgroundPanel extends JPanel {
		private Image backgroundImage;

		public BackgroundPanel() {
			// Load the image
			backgroundImage = new ImageIcon("path_to_your_image.jpg").getImage(); // Set your image path here
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this); // Draw the image
		}
	}

}


