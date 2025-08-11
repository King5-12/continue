package com.github.continuedev.continueintellijextension.auth

import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBPasswordField
import com.intellij.ui.components.JBTextField
import java.awt.BorderLayout
import javax.swing.BoxLayout
import javax.swing.JComponent
import javax.swing.JPanel

class ContinueAuthDialog(
        private val useOnboarding: Boolean,
        private val onTokenEntered: (String, String) -> Unit,
) : DialogWrapper(true) {
    private val tokenFieldUser = JBTextField()
    private val tokenFieldPassword = JBPasswordField()

    init {
        init()
        title = "Continue authentication"
    }

    override fun createCenterPanel(): JComponent {
        println("createCenterPanel")
        val panel = JPanel(BorderLayout())
        val topPanel = JPanel()
        topPanel.layout = BoxLayout(topPanel, BoxLayout.Y_AXIS)

        val message =
                if (useOnboarding)
                        "After onboarding you will be shown an authentication token. Please enter it here:"
                else "请输入邮箱和密码:"
        topPanel.add(JBLabel(message))
        topPanel.add(javax.swing.Box.createVerticalStrut(10))

        // if (authUrl != null) {
        //     val linkLabel = HyperlinkLabel("Open authentication page")
        //     linkLabel.setHyperlinkTarget(authUrl)
        //     topPanel.add(linkLabel)
        //     topPanel.add(javax.swing.Box.createVerticalStrut(10))
        // }

        // 创建输入字段面板
        val inputPanel = JPanel()
        inputPanel.layout = BoxLayout(inputPanel, BoxLayout.Y_AXIS)

        // 用户名标签和输入框
        val userLabel = JBLabel("Email:")
        userLabel.alignmentX = JComponent.LEFT_ALIGNMENT
        inputPanel.add(userLabel)
        inputPanel.add(javax.swing.Box.createVerticalStrut(3))
        tokenFieldUser.alignmentX = JComponent.LEFT_ALIGNMENT
        inputPanel.add(tokenFieldUser)
        inputPanel.add(javax.swing.Box.createVerticalStrut(15))

        // 密码标签和输入框
        val passwordLabel = JBLabel("Password:")
        passwordLabel.alignmentX = JComponent.LEFT_ALIGNMENT
        inputPanel.add(passwordLabel)
        
        inputPanel.add(javax.swing.Box.createVerticalStrut(3))
        tokenFieldPassword.alignmentX = JComponent.LEFT_ALIGNMENT
        inputPanel.add(tokenFieldPassword)
        

        panel.add(topPanel, BorderLayout.NORTH)
        panel.add(javax.swing.Box.createVerticalStrut(10), BorderLayout.CENTER)
        panel.add(inputPanel, BorderLayout.SOUTH)
        return panel
    }
    


    override fun doOKAction() {
        val user = tokenFieldUser.text
        val password = String(tokenFieldPassword.password)
        if (user.isNotBlank() && password.isNotEmpty()) {
            onTokenEntered(user, password)
            super.doOKAction()
        } else {
            setErrorText("请输入邮箱和密码")
        }
    }
}
