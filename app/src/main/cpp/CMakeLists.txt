
#设置构建本机库所需的CMake的最小版本。
cmake_minimum_required(VERSION 3.4.1)


#是制定需要生成的.so库的名字
add_library( # Sets the name of the library.
        native-lib
        #将库设置为共享库。
        # Sets the library as a shared library.
        SHARED
        #提供到源文件的相对路径。
        # Provides a relative path to your source file(s).
        native-lib.cpp
        Test.cpp
        )

find_library( # Sets the name of the path variable.
        log-lib

        # Specifies the name of the NDK library that
        # you want CMake to locate.
        log)

target_link_libraries( # Specifies the target library.
        native-lib

        # Links the target library to the log library
        # included in the NDK.
        ${log-lib})