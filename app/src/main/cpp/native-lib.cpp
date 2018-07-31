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
                                                                      jint w, jint h,
                                                                      jboolean frontFacing) {
    static UMat m;

    LOGD("Processing on CPU");
    int64_t t;
    m.create(h, w, CV_8UC4);

    // read
    t = getTimeMs();
    // expecting FBO to be bound, read pixels to mat
    glReadPixels(0, 0, m.cols, m.rows, GL_RGBA, GL_UNSIGNED_BYTE, m.getMat(ACCESS_WRITE).data);
    LOGD("glReadPixels() costs %d ms", getTimeInterval(t));

    t = getTimeMs();
    // Check if we should flip image due to frontFacing
    // I don't think this should be required, but I can't find
    // a way to get the OpenCV Android SDK to do this properly
    // (also, time taken to flip image is negligible)
    if(frontFacing){
        flip(m, m, 1);
    }
    LOGD("flip() costs %d ms", getTimeInterval(t));

    // modify
    t = getTimeMs();
    cvtColor(m, m, CV_BGRA2GRAY);
    Laplacian(m, m, CV_8U);
    multiply(m, 10, m);
    cvtColor(m, m, CV_GRAY2BGRA);
    LOGD("Laplacian() costs %d ms", getTimeInterval(t));

    // write back
    glActiveTexture(GL_TEXTURE0);
    glBindTexture(GL_TEXTURE_2D, texOut);
    t = getTimeMs();
    glTexSubImage2D(GL_TEXTURE_2D, 0, 0, 0, m.cols, m.rows, GL_RGBA, GL_UNSIGNED_BYTE, m.getMat(ACCESS_READ).data);
    LOGD("glTexSubImage2D() costs %d ms", getTimeInterval(t));
}