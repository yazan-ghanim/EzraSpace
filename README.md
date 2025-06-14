#  Ezra Space – Class Availability Management System

Ezra Space is a Java-based desktop application designed for educational institutions to easily manage and visualize classroom availability in real-time. The system helps students and lecturers find free classrooms, update room statuses, and keep logs — all through a clean JavaFX graphical interface.

## Features

-  **Live status update** (Occupied / Available) per classroom
-  **Table view** of all rooms with filter/sort by name or status
-  **Pie chart visualization** (JFreeChart) for occupied vs. free classrooms
-  **Quick search** for a specific classroom
-  **Auto save/load** classroom data from file (CSV / JSON)
-  **Log every change** with timestamp and room name
-  **GUI** built with JavaFX for intuitive interaction

##  Technologies Used

- **Java (OOP principles)**
- **JavaFX** – for GUI development
- **JFreeChart** – to display pie charts
- **IntelliJ IDEA** – development environment
- **File I/O** – for saving/loading classroom data
- **GitHub** – for version control and sharing

##  Project Structure

```bash
EzraSpace/
├── src/
│   ├── controller/
│   ├── model/
│   ├── view/
│   ├── Main.java
│   └── ...
├── resources/
│   ├── data.csv
│   └── style.css
├── README.md
└── ...
```

## 💻 Platforms Required

| Tool / Library     | Required Version     |
|--------------------|----------------------|
| Java               | 17+ or above         |
| IntelliJ IDEA      | Latest recommended   |
| JavaFX SDK         | 17+                  |
| JFreeChart         | 1.5.3 or compatible  |

##  How to Run

1. **Install Java & JavaFX SDK**  
   Ensure Java 17+ is installed and JavaFX is set up in your IDE.

2. **Clone the repository**  
   ```bash
   git clone https://github.com/yazan-ghanim/Final-project.git
   cd Final-project
   ```

3. **Open with IntelliJ IDEA**  
   - Import the project as a Java project  
   - Set JavaFX libraries in your project structure  
   - Add `JFreeChart` to your module dependencies

4. **Run `Main.java`**  
   Run the application via the `Main` class. The GUI should open and load the classroom status.

<img width="786" alt="Screenshot 2025-06-05 at 19 35 10" src="https://github.com/user-attachments/assets/9e7ea4a7-43c7-4275-9c71-35e1e9e4260e" />



![WhatsApp Image 2025-06-09 at 19 05 18](https://github.com/user-attachments/assets/40bdfb23-2197-468a-a2dc-855e11d1b8c9)


![WhatsApp Image 2025-06-09 at 19 19 13](https://github.com/user-attachments/assets/9620879d-7da8-40ac-96d2-eb1b2b7af4e4)

##  Educational Value

Ezra Space showcases how to:
- Build a complete modular JavaFX system using OOP
- Implement real-time UI updates
- Manage persistent data with File I/O
- Visualize statistics in a user-friendly way

##  Notes

- Developed as part of a final project for the **Computer Science & Engineering** program at **[Institution Name]**
- Instructor: *Mr. Hakam Naboulsi*
