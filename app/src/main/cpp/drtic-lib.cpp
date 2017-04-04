#include <jni.h>
#include <string>

#include "MrizkoDrtic.h"

extern "C"
JNIEXPORT jstring JNICALL
Java_cz_civilizacehra_sifrohaluzic_MrizkoDrticActivity_grindGrid(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = drtMrizku("abcd");
    return env->NewStringUTF(hello.c_str());
}