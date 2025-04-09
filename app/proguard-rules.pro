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

#keep 类名和类成员都不会被移除和混淆
#-optimizationpasses 5
#-dontusemixedcaseclassnames
#-dontskipnonpubliclibraryclasses
#-dontpreverify
#-verbose
#-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
#
#-keep class * implements androidx.viewbinding.ViewBinding {
#    public static * inflate(android.view.LayoutInflater);
#    public static * inflate(android.view.LayoutInflater, android.view.ViewGroup, boolean);
#    public static * bind(android.view.View);
#}
#-keep class com.hihonor.adsdk.demo.databinding.ActivityHomeBinding {*;}
#-keep class com.hihonor.adsdk.demo.databinding.ActivityMockRequestDataBinding {*;}
#-keep class com.hihonor.adsdk.demo.databinding.ActivityNativeBinding {*;}
#-keep class com.hihonor.adsdk.demo.databinding.ActivityOldNativeBinding {*;}
#-keep class com.hihonor.adsdk.demo.databinding.ActivitySettingsBinding {*;}
#-keep class com.hihonor.adsdk.demo.databinding.ActivityTestAdBinding {*;}
#-keep class com.hihonor.adsdk.demo.databinding.ActivityVideoTestBinding {*;}
#-keep class com.hihonor.adsdk.demo.databinding.ActivityBannerBinding {*;}
#-keep public class * implements com.bumptech.glide.module.GlideModule
#-keep class com.bumptech.glide.load.resource.gif.GifDrawable$GifState{*;}
#-keep class com.bumptech.glide.load.resource.gif.GifFrameLoader {*;}