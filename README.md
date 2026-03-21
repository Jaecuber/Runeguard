# Runeguard

**Developers:** [Your Name] and [Partner's Name]
**Date:** [Month, Year]
**Course:** AP Computer Science A 

---

## 🎮 Game Description
[Write a 2-3 sentence pitch for your game. What is the goal? What is the core mechanic? E.g., "Space Defender is a 2D top-down shooter where the player must protect their home planet from endless waves of incoming asteroids. Survive as long as you can to get the high score!"]

## 🕹️ Controls
* **[W, A, S, D]** or **[Arrow Keys]**: Move the player.
* **[Spacebar]** or **[Left Mouse Click]**: Shoot / Jump / Interact.
* **[P]**: Pause the game.
* **[Escape]**: Quit.

---

## 🧠 AP CSA Developer Notes
*This section shows how we aligned to the AP CSA standards.*

* **Encapsulation & Architecture:** All classes use `private` instance variables with `public` getters and setters. We created a class hierarchy where `[Subclass 1]`, `[Subclass 2]`, and `[Subclass 3]` all extend our base `GameObject` class.
* **Polymorphism:** In our main `render()` loop, we call the `.move()` and `.draw()` methods on a list of `GameObject` references, but the specific overridden methods in our subclasses execute at runtime.
* **ArrayList Traversal:** We manage our game entities using an `ArrayList<GameObject>`. We successfully implemented a backwards traversal loop in the `[Insert Method Name]` method to safely remove objects without causing an `IndexOutOfBoundsException`.
* **Standard Arrays & Sorting:** We used a standard 1D array to manage `[Insert Mechanic, e.g., the top 5 high scores]`. We used `[Selection Sort / Insertion Sort]` to order these scores before displaying them on the Game Over screen.
* **File I/O & String Parsing:** Our game saves data persistently to `[filename.txt]`. When the game loads, it reads the file using a `Scanner` and uses String methods like `[substring() / indexOf()]` to parse the saved names and scores.

---

## 🛠️ How to Run Locally

If you want to pull this repository and play the game yourself, follow these steps:

1. **Clone the repository:** `git clone [Insert your GitHub Repo URL here]`
2. **Open the project** in your IDE (VS Code, IntelliJ IDEA, or Eclipse).
3. **Run the Desktop Launcher:** Navigate to `src/Launcher.java` and run the `main` method. 