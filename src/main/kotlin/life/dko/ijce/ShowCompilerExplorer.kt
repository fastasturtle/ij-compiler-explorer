package life.dko.ijce

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.editor.ex.EditorEx
import com.intellij.openapi.fileEditor.OpenFileDescriptor
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx
import com.intellij.openapi.fileEditor.impl.EditorWindow
import com.intellij.openapi.fileTypes.PlainTextFileType
import com.intellij.openapi.project.DumbAwareAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.testFramework.LightVirtualFile
import javax.swing.SwingConstants

val LOG = Logger.getInstance("life.dko.ijce.ShowCompilerExplorer")

class ShowCompilerExplorer : DumbAwareAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        openAssembly(getAssemblyText(), project)
    }

    private fun openAssembly(assemblyText: String, project: Project) {
        val virtualFile = LightVirtualFile("CE assembly", PlainTextFileType.INSTANCE, assemblyText)
        val fileEditorManager = FileEditorManagerEx.getInstanceEx(project)
        fileEditorManager.openTextEditor(OpenFileDescriptor(project, virtualFile), true)
        val assemblyEditor = moveRight(fileEditorManager, virtualFile, project)
        if (assemblyEditor == null) {
            LOG.error("Can't create the assembly editor")
            return
        }
        assemblyEditor.isViewer = true
    }

    private fun getAssemblyText() = "mov eax, 1"

    private fun moveRight(fileEditorManager: FileEditorManagerEx,
                          virtualFile: VirtualFile,
                          project: Project): EditorEx? {
        val window = findEditorWindow(fileEditorManager, virtualFile)
        if (window == null) {
            LOG.error("Can't find editor window for the editor we've just created")
            return null
        }

        fileEditorManager.createSplitter(SwingConstants.VERTICAL, window)
        window.closeFile(virtualFile, false, false)
        return fileEditorManager.openTextEditor(OpenFileDescriptor(project, virtualFile), true) as? EditorEx
    }

    private fun findEditorWindow(fileEditorManager: FileEditorManagerEx, virtualFile: VirtualFile): EditorWindow? {
        for (window in fileEditorManager.windows) {
            for (file in window.files) {
                if (file == virtualFile)
                    return window
            }
        }
        return null
    }
}