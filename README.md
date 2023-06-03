This guide will help you set up the required environment to run the Android app and its accompanying PHP server locally on your Windows machine. Please follow the steps below:

1. Install Android Studio
Download and install Android Studio from the official website: Android Studio.
Follow the installation instructions provided on the website.
Ensure that Android Studio is successfully installed and running on your Windows machine.
2. Import the Android Project
Clone or download the Android project from the GitHub repository.
Launch Android Studio.
Click on "Open an existing Android Studio project" option.
Navigate to the location where you cloned/downloaded the project and select the project folder.
Android Studio will import the project and set up the necessary dependencies.
3. Build the Gradle
After the project is imported, Android Studio will start downloading the required dependencies.
Once the downloads are complete, you can proceed to build the Gradle files.
Click on the "Build" menu in the top toolbar of Android Studio.
Choose the "Make Project" option.
Android Studio will compile and build the Gradle files for the project.
4. Install XAMPP Server
Download XAMPP server from the official website: XAMPP.
Follow the installation instructions provided on the website.
During the installation process, ensure that Apache server is selected for installation.
After the installation is complete, launch XAMPP control panel.
5. Configure XAMPP Server
In the XAMPP control panel, start the Apache server by clicking the "Start" button.
By default, XAMPP sets the server's document root directory to C:\xampp\htdocs.
Open File Explorer and navigate to C:\xampp\htdocs.
Create a new folder for your project and name it accordingly.
6. Copy PHP Files
In the Android project folder, locate the PHP files (typically in a smd_project or similar folder).
Copy the entire smd_project folder or the relevant PHP files.
Navigate to C:\xampp\htdocs\your_project_folder (replace your_project_folder with the name of the folder you created in step 5).
Paste the copied PHP files into this folder.
7. Run the Server
In the XAMPP control panel, ensure that the Apache server is running.
Open your preferred web browser.
In the address bar, type http://localhost/your_project_folder (replace your_project_folder with the name of the folder you created in step 5).
If everything is set up correctly, you should see the project's web interface.
8. Run the Android App
Ensure that an Android device (physical or virtual) is connected to your computer.
In Android Studio, click on the "Run" menu in the top toolbar.
Select your connected device from the list of available devices.
Click on the "Run" button or press Shift + F10.
Android Studio will build the app and install it on the selected device.
Once the installation is complete, you can use the app to interact with the server running on your localhost.
That's it! You have successfully set up the Android app and the accompanying PHP server on your Windows machine. You can now use the app to communicate with the server locally. Enjoy!
