package com.bymarcin.packages4apex.config;

import com.google.gson.Gson;
import com.intellij.openapi.project.Project;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;

public class CustomConfig {
    private Gson gson = new Gson();

    public PackageConfig loadConfig(Project project) {
        File file = configFile(project);
        if (file!=null) {
            try {
                return gson.fromJson(new InputStreamReader(new FileInputStream(file)), PackageConfig.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public File configFile(Project project) {
        File file = configPath(project);
        if (file.exists() && file.isFile()) {
            return file;
        } else {
            return null;
        }
    }

    public boolean configFileExists(Project project) {
        return configFile(project) != null;
    }

    public File configPath(Project project) {
        String baseDir = project.getBasePath();
        if (baseDir == null) return null;
        return Paths.get(project.getBasePath()).resolve(configFileName()).toFile();
    }

    public String configFileName(){
        return "apex-packages.json";
    }

    public String defaultContent(){
        return "{\n" +
                "  \"packageAssignments\" : [\n" +
                "    {\n" +
                "      \"packageName\": \"example\",\n" +
                "      \"classes\" : [ \"Example\" ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";
    }

}
