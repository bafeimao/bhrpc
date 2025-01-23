package io.binghe.rpc.common.scanner;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author You Chuande
 */
public class ClassScanner {
    /**
     * 文件
     */
    private static final String PROTOCOL_FILE = "file";

    /**
     * jar 包
     */
    private static final String PROTOCOL_JAR = "jar";

    /**
     * class文件的后缀
     */
    private static final String CLASS_FILE_SUFFIX = ".class";

    /**
     * 扫描当前工程中指定包下的所有类信息
     *
     * @param packageName   包名
     * @param packagePath   包路径
     * @param recursive     是否递归
     * @param classNameList 类名列表
     */
    private static void findAndAddClassesInPackageByFile(String packageName, String packagePath, final boolean recursive, List<String> classNameList) {
        File dir = new File(packagePath);
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        File[] dirfiles = dir.listFiles(file -> (recursive && file.isDirectory()) || (file.getName().endsWith(CLASS_FILE_SUFFIX)));
        for (File file : dirfiles) {
            if (file.isDirectory()) {
                findAndAddClassesInPackageByFile(packageName + "." + file.getName(),
                        file.getAbsolutePath(),
                        recursive,
                        classNameList);
            } else {
                String className = file.getName().substring(0, file.getName().length() - 6);
                classNameList.add(packageName + "." + className);
            }
        }
    }

    /**
     * 扫描Jar文件中指定包下的所有类信息
     *
     * @param packageName    包名
     * @param classNameList  类名列表
     * @param recursive      是否递归
     * @param packageDirName 包路径
     * @param url            包的url地址
     * @return 处理后的包名，供下次调用使用
     * @throws IOException
     */
    private static String findAndAddClassesInPackageByJar(String packageName,
                                                          List<String> classNameList,
                                                          boolean recursive, String packageDirName, URL url) throws IOException {
        JarFile jar = ((JarURLConnection) url.openConnection()).getJarFile();
        Enumeration<JarEntry> entries = jar.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            String name = entry.getName();
            if (name.charAt(0) == '/') {
                name = name.substring(1);
            }
            if (name.startsWith(packageDirName)) {
                int idx = name.lastIndexOf('/');
                if (idx != -1) {
                    packageName = name.substring(0, idx).replace('/', '.');
                }
                if ((idx != -1) || recursive) {
                    if (name.endsWith(CLASS_FILE_SUFFIX) && !entry.isDirectory()) {
                        String className = name.substring(packageName.length() + 1, name.length() - 6);
                        classNameList.add(packageName + '.' + className);
                    }
                }
            }
        }
        return packageName;
    }

    public static List<String> getClassNameList(String packageName,boolean recursive) throws Exception {
        List<String> classNameList = new ArrayList<>();
        String packageDirName = packageName.replace('.', '/');
        Enumeration<URL> dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
        while (dirs.hasMoreElements()) {
            URL url = dirs.nextElement();
            String protocol = url.getProtocol();
            if (PROTOCOL_FILE.equals(protocol)) {
                String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                findAndAddClassesInPackageByFile(packageName, filePath, recursive, classNameList);
            } else if (PROTOCOL_JAR.equals(protocol)) {
                packageName = findAndAddClassesInPackageByJar(packageName, classNameList, recursive, packageDirName, url);
            }
        }
        return classNameList;
    }

}
