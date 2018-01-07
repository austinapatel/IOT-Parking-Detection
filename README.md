# In Brief
Uses a Raspberry Pi to upload pictures through AWS IoT that a Java program then uses to detect open parking spots.
Submitted for AWS IoT Challenge 2017 and won Honorable Mention prize.
Devpost submission & Pictures/Video: https://devpost.com/software/iot-parking-detection

# Inspiration
At the high school we go to, the parking situation is a mess.  Constantly we have to leave school early and sometimes don't even end up with a parking spot.  We thought that if we made a way that people could see how many spaces are open in a parking lot, they would be able to determine their likelihood of getting a parking spot.

# What it does
This project incorporates AWS IoT services to upload pictures from a Raspberry Pi Zero with a camera attached to it.  These pictures are saved in S3 storage service and then a Java application on another computer downloads those images.  The Java program shows the user the image and allows them to specify the parking spaces in the parking lot.  We created an algorithm that detects whether cars are present in the specified spots and shows the user how many spots are open and filled.  While the Raspberry Pi is running it takes new pictures every 15 seconds and the Java program pulls those images every 15 seconds.  Now our parking problem can be solved!

# How we built it
We used a Raspberry Pi Zero and attached a camera, solar panel and portable battery to it and 3d printed a case for it to fit into.  This allows the Pi to be set up anywhere and run sustainably and easily.  We used AWS Python IoT SDK and created a program that periodically took pictures and published them using MQTT to AWS IoT.  We created an IoT action that sent the uploaded image data to S3.

We then created a Java program to take the uploaded images from S3 and display them to the user with the help of the AWS Java library after setting up AWS CLI.  We created an interface that allows the user to draw rectangles over the image to specify parking spot locations.  After applying a threshold to the image, we wrote an algorithm that detects if a car is present in each parking spot.  Spots where there is a car are highlighted red and spots without cars are labelled green.

# Challenges we ran into
The AWS IoT python program kept throwing errors when we uploaded images.  After many days of trying to solve the issue, we finally realized that the image we were trying to send through IoT was too large: it was over the 128kb limit.  We fixed this by sending parts of the image at a time.  We were very, very happy when we finally solved it!

# Accomplishments that we're proud of
We learned just how numerous and powerful the AWS services were.  We expected them to be complex and too hard to understand, but we were surprised that they were relatively easy to setup and use.

We were also proud of how we were able to get the Raspberry Pi, AWS and the Java program to work seamlessly together to build a project that comprised of not only multiple programming languages, but also both hardware and software.

# What we learned
Complexity does not equal a lot of code.  Creating such a nuanced and large project with many different parts working together did not mean that we needed a tremendous amount of code.  Although we had to write a lot of code for the Java interface, the algorithm for car detection and the python script to take pictures, we were able to make them communicate well with the help of AWS abstracting away low level work.

# What's next for Solar Powered RasPi Camera Parking Detection
We are are interested in creating an Alexa skill that would connect to this parking program and tell the user how many spots are open.

For example:
Query: "Alexa, how many spots are open in front of my house?"
Response: "Currently, there are 5 open spots and 1 filled spot."

We have experience with writing Alexa skills, so we could reasonably incorporate that into our project to make it so that the user won't even have to open the Java app.

# Testing Instructions
## Hardware Setup
1. Connect camera module to Raspberry Pi Zero W
2. Enclose in 3D printed case
3. Connect to external battery attached to solar panel
4. Follow official instrcutions for installing OS on Pi
5. Connect Pi to internet
6. Put Pi in a high position pointing down on a parking lot/driveway/street


## Software Setup
6. Clone this repo onto Pi
7. Set up IoT thing and S3
  - Add action to upload from my/topic to S3 bucket iot-parking with file named image
  - Add action to upload from my/topic1 to S3 bucket iot-parking with file named image2
8. Associate a policy and certificate to IoT Thing through the cloud console

9. In the folder "Raspberry Pi/MQTT Certificates/" in the cloned repo, put MQTT Certificates from AWS IoT Thing
10. Customize the file paths in picture.py located in the "Raspberry Pi" to set up with your Pi if necessary
11. Clone this repo onto a different device (that has Java and a screen like PC)
12. Open "PC" folder in Intellij, another Java IDE, or use a command prompt to run "IOT-Parking-Detection\PC\src\main\java\com\amazonaws\samples\SpotMakerInterface.java"
13. On the Raspberry Pi, navigate to "IOT-Parking-Detection\Raspberry Pi\" in the terminal.
14. Configure "run picture.py" file in this folder to have the correct file paths for your certificates
15. Run the command "bash run\ picture.sh" to start the python script to upload images through IoT
16. The Java program on the other computer will now update every 5 seconds with a new image taken from the Pi

17. On the Java program draw rectangles around each parking spot and the program will detect if there is a car in the place
  - the rectangle will be red if the spot is full
  - the rectangle will be green if the spot is empty
18. Manage spots by using the delete button
19. Current the program will upload spots to S3 (currently working on pulling those spots back down when the program restarts to save spots)
20. Terminate the python script to stop uploading pictures 
