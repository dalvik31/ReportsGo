# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Firebase

# Add this global rule
-keepattributes Signature



# This rule will properly ProGuard all the model classes in
# the package com.epacheco.reports.
# Modify this rule to fit the structure of your app.
-keepclassmembers class com.epacheco.reports.compose_reformat.** {
  *;
}
-keep class com.google.firebase.database.GenericTypeIndicator{*;}
-keep class * extends com.google.firebase.database.GenericTypeIndicator { *; }
-keepclassmembers class com.google.firebase.database.GenericTypeIndicator{*;}
-keep,allowobfuscation,allowshrinking class com.google.firebase.database.GenericTypeIndicator

# Google Play Services Auth, Identity & Credential Manager rules
-keep class com.google.android.gms.auth.** { *; }
-keep class com.google.android.gms.auth.api.** { *; }
-keep class com.google.android.gms.auth.api.identity.** { *; }
-keep class com.google.android.libraries.identity.googleid.** { *; }
-keep class androidx.credentials.** { *; }

# Keep SafeParcelable classes and Parcelable Creators for Google Play Services
-keep class com.google.android.gms.common.internal.safeparcel.SafeParcelable {
    public static final *** NULL;
}
-keep class * implements com.google.android.gms.common.internal.safeparcel.SafeParcelable {
    public static final *** CREATOR;
}
-keepnames class * implements android.os.Parcelable
-keepclassmembers class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}
-keepclassmembers class * implements com.google.android.gms.common.internal.safeparcel.SafeParcelable {
    public static final *** CREATOR;
}


