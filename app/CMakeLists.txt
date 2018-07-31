# Path definitions
set(pathToProject /Users/jonathanreynolds/Documents/Projects/AndroidOpenCVCamera)
set(pathToOpenCv /Users/jonathanreynolds/Documents/Projects/OpenCV-android-sdk)

cmake_minimum_required(VERSION 3.4.1)

# Make sure we use flags for OpenGL in cpp
set(CMAKE_VERBOSE_MAKEFILE on)
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=gnu++11")

# OpenCV includes
include_directories(${pathToOpenCv}/sdk/native/jni/include)

#Added by IDE on project create
set(CPP_ROOT src/main/cpp)
set(SRC_FILES ${CPP_ROOT}/common.hpp ${CPP_ROOT}/native-lib.cpp)
add_library( native-lib SHARED ${SRC_FILES} )

# OpenCV library
add_library( lib_opencv SHARED IMPORTED )
set_target_properties(lib_opencv PROPERTIES IMPORTED_LOCATION ${pathToProject}/app/src/main/jniLibs/${ANDROID_ABI}/libopencv_java3.so)

# Link libraries
target_link_libraries( native-lib lib_opencv android log )