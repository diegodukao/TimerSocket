TimerSocket - Control your electrical socket using Android and Arduino
======================================================================

Introduction
------------

This project was presented by [Álvaro Justen](https://github.com/turicas) and
[Diego Dukão](https://github.com/diegodukao) on 2012-07-27 at FISL13,
in Porto Alegre/RS, Brazil.


Architecture
------------

    Android <-[HTTP]-> Computer <-[Serial]-> Arduino <-[electrical]-> Socket

- Your Android phone talks with a computer (using Wi-Fi, 3G or whatever you
    want)
    - Android makes HTTP requests that are answered by a python script
      (`http_server.py`)
- The computer talks with Arduino using USB (we use `python-serial` library for
  this)
- The Arduino controls a relay that switches a socket on and off.

If you have an Ethernet, Bluetooth or Wi-Fi shield, you can completly remove
the computer and your Android will talk directly to Arduino.

Usage
-----

Just follow these steps:


### Arduino

- Make the circuit to control the relay
- Connect the relay module to Arduino digital pin 7
- Upload the software (`arduino/TimerSocket/TimerSocket.ino`)


### Computer

- Connect Arduino to an USB port
- Install the library `python-serial` (`apt-get install python-serial`)
- Run `python computer/http_server.py`


### Android

- Connect in the same network of your computer
- Build the application APK
- Install on your phone
- Open it
- Configure the computer's IP address in the application
- Done!

