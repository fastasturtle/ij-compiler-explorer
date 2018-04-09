package life.dko.ijce

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.editor.ex.EditorEx
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileEditor.OpenFileDescriptor
import com.intellij.openapi.fileTypes.PlainTextFileType
import com.intellij.openapi.project.DumbAwareAction
import com.intellij.testFramework.LightVirtualFile

val LOG = Logger.getInstance("life.dko.ijce.ShowCompilerExplorer")

class ShowCompilerExplorer : DumbAwareAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return

        val virtualFile = LightVirtualFile("CE assembly", PlainTextFileType.INSTANCE, "mov eax, 1")
        val fileEditorManager = FileEditorManager.getInstance(project)

        val openedEditor = fileEditorManager.openTextEditor(OpenFileDescriptor(project, virtualFile), true)

        if (openedEditor !is EditorEx) {
            LOG.error("Opened editor is not EditorEx")
            return
        }

        openedEditor.isViewer = true

    }
}