package com.bymarcin.packages4apex;

import com.bymarcin.packages4apex.config.ClassConfig;
import com.bymarcin.packages4apex.config.CustomConfig;
import com.bymarcin.packages4apex.config.PackageConfig;
import com.intellij.ide.projectView.TreeStructureProvider;
import com.intellij.ide.projectView.ViewSettings;
import com.intellij.ide.projectView.impl.nodes.PsiFileNode;
import com.intellij.ide.util.treeView.AbstractTreeNode;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.LinkedList;

public class ApexPackagesStructureTreeProvider implements TreeStructureProvider {
    private final Project myProject;
    private final CustomConfig cc = new CustomConfig();

    ApexPackagesStructureTreeProvider(Project project) {
        myProject = project;
    }


    @NotNull
    @Override
    public Collection<AbstractTreeNode<?>> modify(@NotNull AbstractTreeNode<?> abstractTreeNode, @NotNull Collection<AbstractTreeNode<?>> collection, ViewSettings viewSettings) {
        PackageConfig classesLib = cc.loadConfig(myProject);
        if (isClassesDir(abstractTreeNode)) {
            Collection<AbstractTreeNode<?>> result = new LinkedList<>();
            ApexPackageTreeBuilder treeBuilder = new ApexPackageTreeBuilder();
            for (AbstractTreeNode<?> n : collection) {
                if (n instanceof PsiFileNode) {
                    PackageString packageString = FileParser.analyze(((PsiFileNode) n).getVirtualFile());
                    if (packageString != null) {
                        treeBuilder.addFile(packageString, (PsiFileNode) n);
                    } else {
                        if(classesLib != null){
                            ClassConfig classConfig = classesLib.findPackageForClass(((PsiFileNode) n).getVirtualFile().getNameWithoutExtension());
                            if(classConfig !=null){
                                PackageString pkgString = PackageString.fromString(classConfig.packageName);
                                if(pkgString !=null) {
                                    treeBuilder.addFile(pkgString, (PsiFileNode) n);
                                } else {
                                    result.add(n);
                                }
                            } else {
                                result.add(n);
                            }
                        } else {
                            result.add(n);
                        }
                    }
                } else {
                    result.add(n);
                }
            }
            result.addAll((Collection<? extends AbstractTreeNode<?>>) treeBuilder.build(myProject, viewSettings));
            return result;
        }
        return collection;
    }

    private boolean isClassesDir(AbstractTreeNode abstractTreeNode) {
        if ("classes".equals(abstractTreeNode.getName())) {
            return true;
//            AbstractTreeNode parent = abstractTreeNode.getParent();
//            if (parent != null) {
//                AbstractTreeNode masterParent = parent.getParent();
//                if (masterParent instanceof PsiDirectoryNode) {
//                    VirtualFile file = ((PsiDirectoryNode) masterParent).getVirtualFile();
//                    if (file != null) {
//                        String path = file.getCanonicalPath();
//                        return path != null && path.equals(myProject.getBasePath());
//                    }
//                }
//            }
        }
        return false;
    }
}
