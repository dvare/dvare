package org.dvare.annotations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author Muhammad Hammad
 * @since 2016-06-30
 */

public class ClassFinder {
    private static final char PKG_SEPARATOR = '.';
    private static final char DIR_SEPARATOR = '/';
    private static final String CLASS_FILE_SUFFIX = ".class";
    private static final String JAR_FILE_SUFFIX = ".jar";
    private static final String BAD_PACKAGE_ERROR = "Unable to get resources from path '%s'. Are you sure the package '%s' exists?";
    private static final Logger logger = LoggerFactory.getLogger(ClassFinder.class);

    public static List<Class<?>> find(String scannedPackage) {
        return findAnnotated(scannedPackage, null);
    }

    public static List<Class<?>> findAnnotated(String scannedPackage, Class<? extends Annotation> annotation) {
        String scannedPath = scannedPackage.replace(PKG_SEPARATOR, DIR_SEPARATOR);
        URL scannedUrl = Thread.currentThread().getContextClassLoader().getResource(scannedPath);

        if (scannedUrl == null) {
            throw new IllegalArgumentException(String.format(BAD_PACKAGE_ERROR, scannedPath, scannedPackage));
        }
        File scannedDir = new File(scannedUrl.getFile());
        List<Class<?>> classes = new ArrayList<>();
        File[] files = scannedDir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file != null) {
                    classes.addAll(find(file, scannedPackage, annotation));
                }
            }
        } else {
            String pathToJar;
            if (scannedUrl.getProtocol().equals("file") || scannedUrl.getPath().contains("file:")) {
                pathToJar = scannedUrl.getPath().substring(5, scannedUrl.getPath().indexOf(JAR_FILE_SUFFIX) + JAR_FILE_SUFFIX.length());
            } else {
                pathToJar = scannedUrl.getPath().substring(0, scannedUrl.getPath().indexOf(JAR_FILE_SUFFIX) + JAR_FILE_SUFFIX.length());
            }
            classes.addAll(findJar(pathToJar, scannedPath, annotation));
        }

        return classes;
    }


    private static List<Class<?>> findJar(String pathToJar, String scannedPath, Class<? extends Annotation> annotation) {
        List<Class<?>> classes = new ArrayList<>();

        JarFile jarFile;
        try {
            jarFile = new JarFile(pathToJar);

            Enumeration<JarEntry> e = jarFile.entries();
            while (e.hasMoreElements()) {
                JarEntry je = e.nextElement();
                if (je.isDirectory() || !je.getName().endsWith(CLASS_FILE_SUFFIX)) {
                    continue;
                }
                String resource = je.getName();
                int endIndex = resource.length() - CLASS_FILE_SUFFIX.length();
                String path = resource.substring(0, endIndex);
                if (path.contains(scannedPath)) {
                    String className = path.replace(DIR_SEPARATOR, PKG_SEPARATOR);
                    Class<?> aClass = validClass(className, annotation);
                    if (aClass != null) {
                        classes.add(aClass);
                    }
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return classes;
    }

    private static List<Class<?>> find(File file, String scannedPackage, Class<? extends Annotation> annotation) {
        List<Class<?>> classes = new ArrayList<>();
        String resource = scannedPackage + PKG_SEPARATOR + file.getName();
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File child : files) {
                    classes.addAll(find(child, resource, annotation));
                }
            }
        } else if (resource.endsWith(CLASS_FILE_SUFFIX)) {
            int endIndex = resource.length() - CLASS_FILE_SUFFIX.length();
            String className = resource.substring(0, endIndex);
            Class<?> aClass = validClass(className, annotation);
            if (aClass != null) {
                classes.add(aClass);
            }

        }
        return classes;
    }

    private static Class<?> validClass(String className, Class<? extends Annotation> annotation) {
        try {
            Class<?> aClass = Class.forName(className);
            if (annotation != null) {
                if (aClass.isAnnotationPresent(annotation)) {
                    return aClass;
                }
            } else {
                return aClass;
            }
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

}