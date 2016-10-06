package net.eightytwenty.rtd.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Handy utilities for working with JAR resources
 */
public class ResourceUtil {
    public static FileInputStream getRtdResource(String fileName) throws FileNotFoundException {
        ClassLoader classLoader = ResourceUtil.class.getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        return new FileInputStream(file);
    }

}
