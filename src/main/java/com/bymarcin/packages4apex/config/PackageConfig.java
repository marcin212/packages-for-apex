package com.bymarcin.packages4apex.config;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class PackageConfig {
    List<ClassConfig> packageAssignments = new LinkedList<>();

    public ClassConfig findPackageForClass(String className){
        Optional<ClassConfig> result =  packageAssignments.stream().filter(it -> it.classes.contains(className)).findAny();
        return result.orElse(null);
    }
}
