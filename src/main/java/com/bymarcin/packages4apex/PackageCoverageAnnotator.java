package com.bymarcin.packages4apex;

import com.intellij.coverage.BaseCoverageAnnotator;
import com.intellij.coverage.CoverageDataManager;
import com.intellij.coverage.CoverageSuitesBundle;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PackageCoverageAnnotator extends BaseCoverageAnnotator {
    public PackageCoverageAnnotator(Project project) {
        super(project);
    }

    @Nullable
    @Override
    protected Runnable createRenewRequest(@NotNull CoverageSuitesBundle coverageSuitesBundle, @NotNull CoverageDataManager coverageDataManager) {
        return null;
    }

    @Nullable
    @Override
    public String getDirCoverageInformationString(@NotNull PsiDirectory psiDirectory, @NotNull CoverageSuitesBundle coverageSuitesBundle, @NotNull CoverageDataManager coverageDataManager) {
        return null;
    }

    @Nullable
    @Override
    public String getFileCoverageInformationString(@NotNull PsiFile psiFile, @NotNull CoverageSuitesBundle coverageSuitesBundle, @NotNull CoverageDataManager coverageDataManager) {
        return null;
    }
}
