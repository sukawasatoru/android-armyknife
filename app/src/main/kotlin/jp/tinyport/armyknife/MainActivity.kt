package jp.tinyport.armyknife

import android.app.Activity
import android.os.Bundle
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import jp.tinyport.armyknife.core.log
import java.security.AlgorithmParameterGenerator
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.IvParameterSpec

private const val ANDROID_KEYSTORE = "AndroidKeyStore"

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        log.info("onCreate")

        super.onCreate(savedInstanceState)

        val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE)
        keyStore.load(null)
        log.info("aliases: ${keyStore.aliases().toList().joinToString(", ") { return@joinToString it.toString() }}")
        log.info("keyStore.containsAlias: ${keyStore.containsAlias("my-alias")}")
        keyStore.deleteEntry("my-alias")
        log.info("keyStore.containsAlias2: ${keyStore.containsAlias("my-alias")}")

        val algorithm = KeyProperties.KEY_ALGORITHM_AES
        val blockMode = KeyProperties.BLOCK_MODE_GCM
        val padding = KeyProperties.ENCRYPTION_PADDING_NONE

        val keyGenerator = KeyGenerator.getInstance(algorithm, ANDROID_KEYSTORE)
        keyGenerator.init(
            KeyGenParameterSpec
                .Builder("my-alias", KeyProperties.PURPOSE_DECRYPT or KeyProperties.PURPOSE_ENCRYPT)
                .setKeySize(256)
                .setBlockModes(blockMode)
                .setEncryptionPaddings(padding)
                .build()
        )
        val secretKey = keyGenerator.generateKey()

        // https://developer.android.com/training/articles/keystore#SupportedCiphers
        val cipher = Cipher.getInstance("$algorithm/$blockMode/$padding")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val iv = cipher.iv

        log.info("iv: ${iv.joinToString("") { "%02X".format(it) }}")
        val ret = cipher.doFinal("Hello!!".toByteArray())

        val restore = Cipher.getInstance("$algorithm/$blockMode/$padding")
        restore.init(Cipher.DECRYPT_MODE, secretKey, GCMParameterSpec(128, iv))
        val restoreValue = restore.doFinal(ret)
        log.info("restoreValue: ${String(restoreValue)}")
    }
}
