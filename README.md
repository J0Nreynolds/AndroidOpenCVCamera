# AndroidOpenCVCamera
A boilerplate camera app for processing camera preview frames in real-time using the OpenCV Android SDK and the native OpenCV library.

This is intended to give developers a simple way to prototype real-time image processing techniques on a smartphone. The application is set up to perform image processing using native code for best performance. As an example, this application uses OpenCV to apply a Laplacian convolution to each preview frame.

## Setup
This project contains only the Android code necessary for performing native image processing. In order to import and use OpenCV, users must perform a few steps, which are outlined below. You can also reference [this guide](https://medium.com/@sukritipaul005/a-beginners-guide-to-installing-opencv-android-in-android-studio-ea46a7b4f2d3), but you must perform **Step 5** after doing so.

### Step 1 - Download the OpenCV Android SDK
You can obtain the latest version of the OpenCV Android SDK at https://github.com/opencv/opencv/releases, labelled *opencv-{VERSION}-android-sdk.zip* under Assets. After extracting the contents of the zip file, you should have a directory called *OpenCV-android-sdk*.

### Step 2 - Import the OpenCV Android SDK as a module in this project
Open the cloned version of this repo as a project in Android Studio. Then, click File -> New -> Import Module. Navigate to where you just extracted the OpenCV Android SDK files, and use *OpenCV-android-sdk/sdk/java* as the source directory for the import. Click Next, then click Finish, using the default import settings.

### Step 3 - Modify the OpenCV module's imported build.gradle
A new directory called *openCVLibrary{VERSION_NUMBER}* was created in the root of the project after importing the OpenCV module (it may not be visible in Android Studio - reopening project may help). In this new directory, there's a file called *build.gradle*, which you must modify in order to meet the SDK requirements of the application. Make the following changes:

#### In android
compileSdkVersion 14 -> compileSdkVersion27

#### In android.defaultConfig
minSdkVersion 8 -> minSdkVersion 23

targetSdkVersion 21 -> targetSdkVersion 27

### Step 4 - Copy native libs from OpenCV Android SDK
Create a new folder called *jniLibs* in the project's *app/src/main* folder. Then, copy everything in *OpenCV-android-sdk/sdk/native/libs* to the *jniLibs* directory you just created.

### Step 5 - Modify the CMakeLists.txt file
Lastly, you just need to modify two strings in the CMakeList file to ensure your native libraries are linked correctly.
Open the project's *app/CMakeLists.txt* file. The first two lines should look like this:
```
set(pathToProject /Users/jonathanreynolds/Documents/Projects/AndroidOpenCVCamera)
set(pathToOpenCv /Users/jonathanreynolds/Documents/Projects/OpenCV-android-sdk)
```
Change */Users/jonathanreynolds/Documents/Projects/AndroidOpenCVCamera* to be the path to the project, and change */Users/jonathanreynolds/Documents/Projects/OpenCV-android-sdk* to be the path to your OpenCV Android SDK folder.

## Credits
I created this application referencing OpenCV's Android samples, namely [Tutorial 4 - OpenCL](https://github.com/opencv/opencv/tree/3.4/samples/android/tutorial-4-opencl). This sample used the OpenCV Android SDK's `CameraGLSurfaceView`, which provides a nice interface for intercepting and processing Android camera preview frames.
