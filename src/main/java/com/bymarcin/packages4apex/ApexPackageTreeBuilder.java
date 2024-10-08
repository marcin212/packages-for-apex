package com.bymarcin.packages4apex;

import com.intellij.ide.projectView.ViewSettings;
import com.intellij.ide.projectView.impl.nodes.PsiFileNode;
import com.intellij.ide.util.treeView.AbstractTreeNode;
import com.intellij.openapi.project.Project;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class ApexPackageTreeBuilder {
    private final Package rootDir = new Package("root");

    public void addFile(PackageString pkg, PsiFileNode fileNode) {
        Package last = rootDir;
        for (String pkgPart : pkg.getPackageParts()) {
            Package p = last.findPackage(pkgPart);
            if (p == null) {
                p = new Package(pkgPart);
                last.dirs.add(p);
            }
            last = p;
        }
        last.files.add(fileNode);
    }

    public Collection<? extends AbstractTreeNode> build(Project project, ViewSettings settings) {
        ApexPackageNode node = rootDir.toAbstractTreeNode(project, settings);
        return node.getChildren();
    }


    private static class Package {
        List<Package> dirs = new LinkedList<>();
        List<PsiFileNode> files = new LinkedList<>();
        String name;

        Package(String name) {
            this.name = name;
        }

        Package findPackage(String name) {
            for (Package p : dirs) {
                if (p.name.equals(name)) {
                    return p;
                }
            }
            return null;
        }

        ApexPackageNode toAbstractTreeNode(Project project, ViewSettings settings) {
            ApexPackageNode myNode = new ApexPackageNode(project, name, settings);
            for (Package dir : dirs) {
                myNode.addChild(dir.toAbstractTreeNode(project, settings));
            }
            for (PsiFileNode file : files) {
                myNode.addChild(file);
            }
            return myNode;
        }
    }
}
