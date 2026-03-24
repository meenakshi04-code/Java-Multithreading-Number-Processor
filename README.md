# Multithreaded Number Processing in Java

This Java application demonstrates multithreading using the Producer-Consumer concept.

## Features
- Generates random numbers using one thread
- Uses separate threads to:
  - Calculate square (for even numbers)
  - Calculate cube (for odd numbers)
- Uses synchronization:
  - synchronized methods
  - wait() and notifyAll()

## Technologies Used
- Java
- Multithreading
- Synchronization

## How it Works
1. Producer thread generates numbers.
2. Consumer threads process:
   - Even → Square
   - Odd → Cube
3. Threads coordinate using shared data.

## How to Run

1. Compile:
   javac MultiThreadedApp.java

2. Run:
   java MultiThreadedApp

## Author
Meenakshi
