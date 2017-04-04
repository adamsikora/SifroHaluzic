set( CMAKE_CXX_STANDARD 14 )

# Specifies a library name, specifies whether the library is STATIC or
# SHARED, and provides relative paths to the source code. You can
# define multiple libraries by adding multiple add.library() commands,
# and CMake builds them for you. When you build your app, Gradle
# automatically packages shared libraries with your APK.

add_library( # Specifies the name of the library.
             MrizkoDrtic

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
             src/main/cpp/drtic-lib.cpp
             src/main/cpp/MrizkoDrtic.cpp
             src/main/cpp/Trie.cpp )