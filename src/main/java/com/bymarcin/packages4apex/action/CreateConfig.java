package com.bymarcin.packages4apex.action;

import com.bymarcin.packages4apex.config.CustomConfig;
import com.intellij.ide.SaveAndSyncHandler;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.VirtualFileManager;
import org.jetbrains.annotations.NotNull;


public class CreateConfig extends AnAction {
    private final CustomConfig customConfig = new CustomConfig();

    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        Project project = anActionEvent.getProject();
        if(project == null) {
            Notifications.Bus.notify(new Notification("Packages4Apex", "Can not create config for Null project.", NotificationType.ERROR).setTitle("Packages for Apex"));
            return;
        }
        if (customConfig.configFile(anActionEvent.getProject()) == null) {
            try {
                FileUtil.writeToFile(customConfig.configPath(project), customConfig.defaultContent());
                FileDocumentManager.getInstance().saveAllDocuments();
                SaveAndSyncHandler.getInstance().refreshOpenFiles();
                VirtualFileManager.getInstance().refreshWithoutFileWatcher(true);

                Notifications.Bus.notify(new Notification("Packages4Apex", "Config file created.", NotificationType.INFORMATION).setTitle("Packages for Apex"));
            } catch (Exception e) {
                e.printStackTrace();
                Notifications.Bus.notify(new Notification("Packages4Apex", "Error creating config file: " + e.getMessage(), NotificationType.ERROR).setTitle("Packages for Apex"));
            }
        }
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        super.update(e);
        Presentation presentation = e.getPresentation();
        Project project = e.getProject();
        boolean enabled = project != null && !customConfig.configFileExists(project);
        presentation.setEnabled(enabled);
        presentation.setVisible(true);
    }

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.BGT;
    }
}
