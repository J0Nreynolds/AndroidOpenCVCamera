# AndroidOpenCVCamera
A boilerplate camera app for processing camera preview frames in real-time using the OpenCV Android SDK and the native OpenCV library.

This is intended to give developers a simple way to prototype real-time image processing techniques on a smartphone. The application is set up to perform image processing using native code for best performance. 

As an example, this application uses OpenCV to get the Laplacian of each preview frame.

![AndroidOpenCVCamera screenshot](https://lh3.googleusercontent.com/Fh5i-M_W1JMAtKKXZdLwKPuodJF0_Lfyaz9niwyWbjywtWn6Be-VwpXsjOdq8Untfb-PMfC4dy62Gbh7wOnIrNBCx0x98xceUtVZDyxOSfRSQ1f3sYar_mFve3-S1S6YWXeeNShlNrZm-yf0YfgzBLafRbVWABaAx7HtDxXPNKrr5-ZgpxLdpmsM9xo_cChjjX6m1bzt4hMQQlr38GZ6SqOkt8VoFz3FiRfD1SP0E9vSLYTm3wD0HAbE8bQymazEBOuf-3Tavyn3t5xFInaIyAlA8gLjRhBbYPS3uvMQ1oJ_XRf-5Z9BTAoXUNM10q-aQeUIvkakyc3CBXaVf1z84gwKAzSTRHBWzabk-4F0HxEIhSo337g5qOAgl-rRDaVMlp45gyrf5pc2aN67PwGXWO7lbSDcL3qEZbVhn99DjgEapHZK1MHSTKf0-lArq4fK7CXBJsS1JxUuIGvfgNZPwnaEJGxsvXaoZBnMm0NTNFTDQSIs5sghROA1_dSElNbMGWNCRV-Hnecvgr-6jQq6PQphIzKCi-1om16hGcYS1enqqTol5yIMp8JfIOZsDgXuS8plYPge4EHaLiyCPOgbXCSLbYXO5XIBIQQnLJ8Pf6ZPgRK78xdMD2P8kzAOnb6A0EgBjQq7Lr2qRvWkPCb8Zr6Ebkn-3OAntA=w351-h624-no)

## Features
- Modifies preview frame from Camera2 API with OpenCV Android SDK
- Performs image processing with native C++
- Ability to swap between front and back cameras
- Shows FPS in application

## Setup
#### [Tested with OpenCV 3.4.2]
This project contains only the Android code necessary for performing native image processing. In order to import and use OpenCV, users must perform a few steps, which are outlined below. You can also reference [this guide](https://medium.com/@sukritipaul005/a-beginners-guide-to-installing-opencv-android-in-android-studio-ea46a7b4f2d3), but you must perform **Step 5** after doing so.

### Step 1 - Download the OpenCV Android SDK
You can obtain the latest version of the OpenCV Android SDK at https://github.com/opencv/opencv/releases, labelled *opencv-{VERSION}-android-sdk.zip* under Assets. After extracting the contents of the zip file, you should have a directory called *OpenCV-android-sdk*.

### Step 2 - Import the OpenCV Android SDK as a module in this project
Open the cloned version of this repo as a project in Android Studio. Then, click File -> New -> Import Module. Navigate to where you just extracted the OpenCV Android SDK files, and use *OpenCV-android-sdk/sdk/java* as the source directory for the import. Click Next, then click Finish, using the default import settings.

### Step 3 - Modify the OpenCV module's imported build.gradle
After the import completes, a new folder *openCVLibrary{VERSION_NUMBER}* is created in the root of the project. In this directory, there's a file called *build.gradle*, which you must modify in order to meet the SDK requirements of the application. Make the following changes:

#### In android
compileSdkVersion 14 -> compileSdkVersion27

#### In android.defaultConfig
minSdkVersion 8 -> minSdkVersion 23

targetSdkVersion 21 -> targetSdkVersion 27

### Step 4 - Copy native libs from OpenCV Android SDK
Create a new folder called *jniLibs* in the project's *app/src/main* folder. Then, copy everything in *OpenCV-android-sdk/sdk/native/libs* to the *jniLibs* directory you just created.

### Step 5 - Modify the CMakeLists.txt file
Lastly, you just need to modify two strings in the CMakeList file to ensure your native libraries are linked correctly.
Open the project's *app/CMakeLists.txt* file. The first three lines should look like this:
```
# Path definitions
set(pathToProject /Users/jonathanreynolds/Documents/Projects/AndroidOpenCVCamera)
set(pathToOpenCv /Users/jonathanreynolds/Documents/Projects/OpenCV-android-sdk)
```
Change */Users/jonathanreynolds/Documents/Projects/AndroidOpenCVCamera* to be the path to the project, and change */Users/jonathanreynolds/Documents/Projects/OpenCV-android-sdk* to be the path to your OpenCV Android SDK folder.

### Step 6 - Build and run
The app should build and run now. If you want to modify the behavior of the application, `MyGLSurfaceView.onCameraTexture` is the callback used in the Java layer, and it calls *`processFrame`* to do work in the native layer.

## Credits
I created this application using OpenCV's Android samples, namely [Tutorial 4 - OpenCL](https://github.com/opencv/opencv/tree/3.4/samples/android/tutorial-4-opencl). The OpenCL sample demonstrated how to use the OpenCV Android SDK's `CameraGLSurfaceView`, which provides a nice interface for intercepting and processing Android camera preview frames.
