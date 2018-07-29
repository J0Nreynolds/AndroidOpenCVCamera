#include <jni.h>

#include <opencv2/core.hpp>
#include <opencv2/imgproc.hpp>

#include <GLES2/gl2.h>

#include "common.hpp"

using namespace cv;

extern "C" JNIEXPORT void

JNICALL
Java_net_jonreynolds_androidopencvcamera_MyGLSurfaceView_processFrame(JNIEnv *env, jobject /* this */,
                                                              jint texIn, jint texOut,
                                                              jint w, jint h, jboolean frontFacing) {
    LOGD("Processing on CPU");
    int64_t t;

    // let's modify pixels in FBO texture in C++ code (on CPU)
    static cv::Mat m;
    m.create(h, w, CV_8UC4);

    // read
    t = getTimeMs();
    // expecting FBO to be bound
    glReadPixels(0, 0, w, h, GL_RGBA, GL_UNSIGNED_BYTE, m.data);
    LOGD("glReadPixels() costs %d ms", getTimeInterval(t));

    // modify
    t = getTimeMs();
    cv::Laplacian(m, m, CV_8U);
    m *= 10;
    LOGD("Laplacian() costs %d ms", getTimeInterval(t));

    // Check if we should flip image due to frontFacing
    // I don't think this should be required, but I can't find
    // a way to get tne OpenCV Android SDK to do this properly
    if(frontFacing){
        cv::flip(m, m, 1);
    }

    // write back
    glActiveTexture(GL_TEXTURE0);
    glBindTexture(GL_TEXTURE_2D, texOut);
    t = getTimeMs();
    glTexSubImage2D(GL_TEXTURE_2D, 0, 0, 0, w, h, GL_RGBA, GL_UNSIGNED_BYTE, m.data);
    LOGD("glTexSubImage2D() costs %d ms", getTimeInterval(t));
}