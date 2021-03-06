package io.protostuff.jetbrains.plugin.reference.file;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import io.protostuff.jetbrains.plugin.settings.ProtobufSettings;
import java.util.ArrayList;
import java.util.List;

/**
 * Returns source roots for custom include paths.
 *
 * @author Kostiantyn Shchepanovskyi
 */
class CustomIncludePathRootsProvider implements FilePathReferenceProvider.SourceRootsProvider {
    @Override
    public VirtualFile[] getSourceRoots(Module module) {
        List<VirtualFile> result = new ArrayList<>();
        Project project = module.getProject();
        ProtobufSettings settings = project.getComponent(ProtobufSettings.class);
        List<String> includePaths = settings.getIncludePaths();
        for (String includePath : includePaths) {
            VirtualFile path = LocalFileSystem.getInstance().findFileByPath(includePath);
            if (path != null && path.isDirectory()) {
                result.add(path);
            }
        }
        return result.toArray(new VirtualFile[0]);
    }
}
