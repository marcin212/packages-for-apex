package com.bymarcin.packages4apex;

import com.intellij.ide.projectView.PresentationData;
import com.intellij.ide.projectView.ProjectViewNode;
import com.intellij.ide.projectView.ViewSettings;
import com.intellij.ide.projectView.impl.nodes.PsiFileNode;
import com.intellij.ide.util.treeView.AbstractTreeNode;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.PlatformIcons;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.LinkedList;

public class ApexPackageNode extends ProjectViewNode<String> {
    private Collection<AbstractTreeNode<?>> children = new LinkedList<>();

    private Collection<ProjectViewNode> childrenContainers = new LinkedList<>();
    private Collection<VirtualFile> childrenVirtualFiles = new LinkedList<>();

    private String displayName;

    public ApexPackageNode(Project project, @NotNull String s, ViewSettings viewSettings) {
        super(project, s, viewSettings);
        displayName = s;
    }

    @Override
    public boolean contains(@NotNull VirtualFile virtualFile) {
        if (childrenVirtualFiles.contains(virtualFile)) {
            return true;
        } else {
            for (ProjectViewNode node : childrenContainers) {
                if (node.contains(virtualFile)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void addChild(AbstractTreeNode child) {
        if (child instanceof PsiFileNode) {
            childrenVirtualFiles.add(((PsiFileNode) child).getVirtualFile());
        }
        if (child instanceof ProjectViewNode) {
            childrenContainers.add((ProjectViewNode) child);
        }
        children.add(child);
    }

    @NotNull
    @Override
    public Collection<? extends AbstractTreeNode<?>> getChildren() {
        return children;
    }

    @Override
    protected void update(@NotNull PresentationData presentationData) {
        presentationData.setPresentableText(displayName);
        presentationData.setIcon(PlatformIcons.PACKAGE_ICON);
    }
}
