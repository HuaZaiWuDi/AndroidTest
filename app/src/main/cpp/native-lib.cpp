#include <jni.h>
#include <string>
#include "Test.h"

extern "C" {
    JNIEXPORT jstring JNICALL
    Java_com_wesmarclothing_jniproject_MainActivity_stringFromJNI(
            JNIEnv *env,
            jobject /* this */) {
        std::string hello = "Hello from C++";

        return env->NewStringUTF(hello.c_str());
    }

    JNIEXPORT jstring JNICALL
    Java_com_wesmarclothing_jniproject_MainActivity_encode(
            JNIEnv *env,
            jobject job, /* this */
            jstring jstring1
    ) {
        char *target = (char *) (env->GetStringUTFChars(jstring1, 0));
        char *result = encode(target);
        return env->NewStringUTF(result);
    }

}


extern "C"
JNIEXPORT jstring JNICALL
Java_com_wesmarclothing_jniproject_JinTest_jnitest(JNIEnv *env, jclass type) {

    std::string myStr = "我是JNI";
    return env->NewStringUTF(myStr.c_str());
}