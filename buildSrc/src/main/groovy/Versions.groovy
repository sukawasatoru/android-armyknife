class Versions {
    static final gradle = new Gradle()
    static final kotlin = '1.3.72'
    static final manifest = new Manifest()

    static class Gradle {
        final android = '4.0.1'
        final androidCacheFix = '1.0.8'
    }

    static class Manifest {
        final compileSdk = 29
        final buildTools = '30.0.1'
        final minSdk = 24
        final targetSdk = 29
    }
}
