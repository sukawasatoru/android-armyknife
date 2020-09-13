package jp.tinyport.armyknife.feature.keystore

import android.content.Context
import android.os.Bundle
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.leanback.app.DetailsSupportFragment
import androidx.leanback.widget.AbstractDetailsDescriptionPresenter
import androidx.leanback.widget.Action
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.DetailsOverviewRow
import androidx.leanback.widget.FullWidthDetailsOverviewRowPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.GCMParameterSpec

private const val ANDROID_KEYSTORE = "AndroidKeyStore"

private data class DetailsItem(val title: String, val body: String?, val subtitle: String?)

private sealed class DetailsAction(id: Long, label: CharSequence) : Action(id, label) {
    object Calc : DetailsAction(0, "Calc")
}

class KeyStoreFragment : DetailsSupportFragment() {
    private lateinit var detailsRow: DetailsOverviewRow
    private lateinit var keyStore: KeyStore

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val detailsPresenter = FullWidthDetailsOverviewRowPresenter(object : AbstractDetailsDescriptionPresenter() {
            override fun onBindDescription(vh: ViewHolder, item: Any) {
                val i = item as DetailsItem
                vh.title.text = i.title
                vh.body.text = i.body
                vh.subtitle.text = i.subtitle
            }
        }).apply {
            initialState = FullWidthDetailsOverviewRowPresenter.STATE_HALF
            setOnActionClickedListener {
                val action = it as DetailsAction
                when (action) {
                    DetailsAction.Calc -> GlobalScope.launch {
                        val ret = calc()
                        withContext(Dispatchers.Main) {
                            detailsRow.item = (detailsRow.item as DetailsItem).copy(body = ret)
                        }
                    }
                }.let { }
            }
        }

        detailsRow = DetailsOverviewRow(DetailsItem(
                title = "KeyStore",
                body = null,
                subtitle = null)).apply {
            actionsAdapter = ArrayObjectAdapter().apply {
                setItems(listOf(DetailsAction.Calc), null)
            }
        }

        adapter = ArrayObjectAdapter(detailsPresenter).apply {
            setItems(listOf(detailsRow), null)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        prepareEntranceTransition()

        keyStore = KeyStore.getInstance(ANDROID_KEYSTORE)
        keyStore.load(null)

        startEntranceTransition()
    }

    private fun calc(): String {
        val builder = StringBuilder()

        builder.appendLine("aliases: ${keyStore.aliases().toList().joinToString(", ") { return@joinToString it.toString() }}")
                .appendLine("keyStore.containsAlias: ${keyStore.containsAlias("my-alias")}")
        keyStore.deleteEntry("my-alias")
        builder.appendLine("keyStore.containsAlias2: ${keyStore.containsAlias("my-alias")}")

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

        builder.appendLine("iv: ${iv.joinToString("") { "%02X".format(it) }}")
        val ret = cipher.doFinal("Hello!!".toByteArray())

        val restore = Cipher.getInstance("$algorithm/$blockMode/$padding")
        restore.init(Cipher.DECRYPT_MODE, secretKey, GCMParameterSpec(128, iv))
        val restoreValue = restore.doFinal(ret)
        builder.appendLine("restoreValue: ${String(restoreValue)}")

        return builder.toString()
    }
}
