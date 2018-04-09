package life.dko.ijce

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.project.DumbAwareAction

class ShowCompilerExplorer : DumbAwareAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val editor = e.getData(CommonDataKeys.EDITOR) ?: return
        val project = e.project ?: return

        WriteCommandAction.runWriteCommandAction(project, {
            editor.document.insertString(0, "Godbolted!")
        })
    }
}