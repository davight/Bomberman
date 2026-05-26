# Bomberman

A 2-player Bomberman-style game built with **Java 21** and the **ShapesGE 2.1.0** framework. 
Developed as a semestral project for FRI UNIZA - Informatika 2 (`6BI0012`).

**Gameplay:** Navigate a procedurally generated tile map, place bombs to destroy barrel blocks and trigger chain explosions, collect power-up items, and eliminate all enemies to advance to the next level. Two players can team up in duo mode, each with their own controls and a shared objective.

---

## Game Modes

### Singleplayer
- You as a Blue player need to eliminate all enemies across levels.

### Mutliplayer
- As the name suggest, you and your friend, Red and Blue players need to cooperate to clear enemies together, while trying to not kill each other by accident.
- The game ends when both players die.

---

## Controls

| Action     | Blue Player     | Red Player |
|------------|-----------------|------------|
| Move       | `W` `A` `S` `D` | Arrow keys |
| Place bomb | `Space`         | `Enter`    |

---

## Enemies

| Enemy          | Speed  | Behaviour                        |
|----------------|--------|----------------------------------|
| DeadlySlime    | Slow   | Chases player on same row/column |
| ExplosiveSlime | Medium | Patrol-style movement            |
| SpeedSlime     | Fast   | Aggressive pursuit               |

---

## Items

| Item   | Effect                          |
|--------|---------------------------------|
| Heart  | Restores 1 HP                   |
| Freeze | Freezes all enemies temporarily |
| Orbit  | Orbit projectile effect         |

Items drop from destroyed barrel blocks.

---

## Requirements

- Java 21+
- `shapesGE-2.1.0.jar` placed in `libs/`


ShapesGE 2.1.0 can be installed at https://github.com/infjava/shapesge/releases/tag/2.1.0

---

## Running

```
./gradlew run
```

---

## Build

**Full JAR** (includes all dependencies):
```
./gradlew jar
```

Output: `build/libs/Bomberman-1.0.jar`
