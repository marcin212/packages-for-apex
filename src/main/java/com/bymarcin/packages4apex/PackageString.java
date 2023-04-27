package com.bymarcin.packages4apex;

import java.util.regex.Pattern;

public class PackageString {
    private String pkg;
    private static final Pattern pkgPattern = Pattern.compile("^[a-zA-Z][a-zA-Z0-9_]*(\\.[a-zA-Z0-9_]+)*[0-9A-Za-z_]$");

    private PackageString(String pkg) {
        this.pkg = pkg;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof PackageString && pkg.equals(((PackageString) obj).pkg);
    }

    @Override
    public int hashCode() {
        return pkg.hashCode();
    }

    public String[] getPackageParts() {
        return pkg.split("\\.");
    }

    public static PackageString fromString(String pkgString) {
        String trimPkgString = pkgString.trim();
        //PluginManager.getLogger().warn("PKG STRING: [" + trimPkgString +"] -> " + pkgPattern.matcher(trimPkgString).matches() + " -> " + pkgPattern.matcher(trimPkgString).find());
        if (pkgPattern.matcher(trimPkgString).matches()) {
            return new PackageString(trimPkgString);
        }
        return null;
    }
}
