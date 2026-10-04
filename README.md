# Modified Flappy Bird

A Java Swing recreation of Flappy Bird with additional gameplay features including power-ups, projectiles, collision immunity, scoring, and randomized obstacles.

## Features

- Flappy Bird-style movement with gravity and jumping
- Randomly generated pipes
- Score tracking
- Collision detection
- FIRE power-up that allows the player to shoot projectiles
- STAR power-up that temporarily prevents pipe collisions from ending the game
- Timed power-up effects
- Randomized power-up spawning
- Game-over and restart functionality

## Technologies

- Java
- Java Swing
- Object-Oriented Design

## How to Run

### Requirements

Install a Java Development Kit (JDK).

You can verify that Java is installed by running:

```bash
java -version
javac -version
```

### 1. Clone the Repository

```bash
git clone https://github.com/IsabellaPoon/birdie.git
cd birdie
```

### 2. Compile the Java Files

```bash
javac *.java
```

### 3. Run the Game

```bash
java Main
```

The game window should open automatically.

## Controls

- **Spacebar** — flap / move the bird upward
- **E** — shoot a projectile while the FIRE power-up is active
- **Spacebar after game over** — restart the game
