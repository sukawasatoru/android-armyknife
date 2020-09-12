class Depends {
    static final androidx = new AndroidX()
    static final google = new Google()
    static final kotlin = new Kotlin()
    static final test = new Test()
    static final tinyport = new Tinyport()

    static class AndroidX {
        final annotation = 'androidx.annotation:annotation:1.1.0'
        final constraint = 'androidx.constraintlayout:constraintlayout:2.0.0'
        final datastore = 'androidx.datastore:datastore-core:1.0.0-alpha01'
        final leanback = 'androidx.leanback:leanback:1.0.0'
        final lifecycleVersion = '2.2.0'
        final lifecycleCompiler = "androidx.lifecycle:lifecycle-compiler:$lifecycleVersion"
        final lifecycleLiveData = "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion"
        final lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion"
        final lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"
        final lifecycleViewModelSavedState = "androidx.lifecycle:lifecycle-viewmodel-savedState:$lifecycleVersion"
        final roomVersion = '2.2.5'
        final roomCompiler = "androidx.room:room-compiler:$roomVersion"
        final roomKtx = "androidx.room:room-ktx:$roomVersion"
        final roomRuntime = "androidx.room:room-runtime:$roomVersion"
    }

    static class Google {
        final hiltVersion = '2.29-alpha'
        final hilt = "com.google.dagger:hilt-android:$hiltVersion"
        final hiltCompiler = "com.google.dagger:hilt-android-compiler:$hiltVersion"
        final protobufVersion = '3.13.0'
        final protoc = "com.google.protobuf:protoc:$protobufVersion"
        final protobufLite = "com.google.protobuf:protobuf-javalite:$protobufVersion"
    }

    static class Kotlin {
        final coroutinesVersion = '1.3.9'
        final coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion"
        final coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion"
        final stdlib = 'org.jetbrains.kotlin:kotlin-stdlib:1.4.10'
        final serialization = 'org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.20.0'
    }

    static class Test {
        final androidJunit4Ktx = 'androidx.test.ext:junit-ktx:1.1.2'
        final junit = 'junit:junit:4.12'
        final robolectric = 'org.robolectric:robolectric:4.3'
        final testCoreKtx = 'androidx.test:core-ktx:1.3.0'
        final testRunner = 'androidx.test:runner:1.3.0'
        final testRules = 'androidx.test:rules:1.3.0'
        final truth = 'com.google.truth:truth:1.0.1'
        final truthJava8 = 'com.google.truth.extensions:truth-java8-extension:1.0.1'
    }

    static class Tinyport {
        final logger = "jp.tinyport:logger:0.1.0"
    }
}
