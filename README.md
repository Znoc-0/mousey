# Mouse Control App

## Description

The **Mouse Control App** is an Android application that transforms your mobile device into a virtual trackpad to control your laptop's mouse pointer. This app enables users to perform various mouse-related tasks such as moving the pointer, clicking, double-clicking, dragging, and scrolling—all from their mobile device.

The app sends touch data through a WebSocket connection to your laptop, where it is processed and used to control the mouse pointer. Whether you are giving a presentation, playing a game, or just looking for a more intuitive way to interact with your laptop, this app provides a seamless, wireless solution.

## Features

- **Mouse Movement**: Control the mouse pointer on your laptop by moving your finger on the screen of your Android device.
- **Single Tap**: Simulate a single left-click by tapping anywhere on the screen.
- **Double Tap**: Simulate a double-click by tapping twice.
- **Drag**: Hold and drag to interact with elements on your laptop (e.g., moving windows or selecting text).
- **Scroll**: Swipe to scroll through content on your laptop, mimicking the behavior of a trackpad.
  
## How It Works

- The app uses **WebSocket** to send real-time touch data from your mobile device to your laptop.
- The laptop should have a WebSocket server running that processes the touch data to simulate mouse movements and interactions.
- The app tracks touch gestures such as taps, drags, and scrolls and sends the corresponding data to the laptop.

## Requirements

- **Android device**: The app requires an Android device running version 5.0 (Lollipop) or higher.
- **Laptop setup**: The laptop must have a WebSocket server running to accept touch data from the app and control the mouse.


