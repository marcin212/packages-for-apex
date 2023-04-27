package com.bymarcin.packages4apex;

import com.intellij.ide.plugins.PluginManager;
import com.intellij.openapi.vfs.VirtualFile;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FileParser {
    private static final List<String> keywords = Arrays.asList("private", "protected", "public", "global");
    private static final String packageKeyword = "//package";
    private static final String vpackageKeyword = "//vpackage";

    public static PackageString analyze(VirtualFile file) {
        if (file == null) return null;
        if ("cls".equals(file.getExtension())) {
            try (Scanner scr = new Scanner(file.getInputStream())) {
                while (scr.hasNextLine()) {
                    String line = scr.nextLine().trim();
                    if (line.startsWith(packageKeyword)) {
                        return PackageString.fromString(line.substring(packageKeyword.length()));
                    }
                    if(line.startsWith(vpackageKeyword)) {
                        return PackageString.fromString(line.substring(vpackageKeyword.length()));
                    }
                    for (String keyword : keywords) {
                        if (line.startsWith(keyword)) {
                            return null;
                        }
                    }
                }
            } catch (Throwable e) {
                Packages4Apex.logger.error(e);
            }
        }
        return null;
    }
}
