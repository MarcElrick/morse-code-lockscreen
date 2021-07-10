# morse-code-lockscreen
An exciting, experimental method of unlocking your mobile device.

### [Demo](https://youtu.be/IFSO3dgt7O8)


## Installation
1. On your android device, go to settings -> Allow Apps from Unknown Sources
1. Download the apk.
1. Follow device instructions to install.
1. Run from app drawer.

## Running via Android Studio
1. Download and Install Android Studio: https://developer.android.com/studio
1. Start Android Studio and Open Project Folder.
1. On Device:
   - Enable developer options: Settings -> About -> Build Number -> Tap 5 times
   - Enable USB debugging: Settings -> About -> USB Debugging
1. Plug in device via USB.
1. Ensure device is selected in device menu(top bar)
1. Click the "Run" button at the top.


## Default Password
 - Default password is SHORT_LEFT, SHORT_RIGHT, LONG_LEFT, LONG_RIGHT. 
 - Password can be changed via an array named 'password' within ```MainActivity.java``` which is an enum type.
 - This enum has types ```SHORT_LEFT```,```SHORT_RIGHT```,```LONG_LEFT```,```LONG_RIGHT```.
