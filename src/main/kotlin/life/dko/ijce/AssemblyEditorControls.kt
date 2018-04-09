package life.dko.ijce

import com.intellij.openapi.editor.impl.EditorHeaderComponent
import javax.swing.JLabel

class AssemblyEditorControls : EditorHeaderComponent()

fun createEditorControls() : AssemblyEditorControls {
    val controls = AssemblyEditorControls()
    val label = JLabel("Some text")
    controls.add(label)
    return controls
}