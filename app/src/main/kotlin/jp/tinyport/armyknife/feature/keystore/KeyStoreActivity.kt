package jp.tinyport.armyknife.feature.keystore

import android.os.Bundle
import androidx.fragment.app.FragmentActivity

class KeyStoreActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(android.R.id.content, KeyStoreFragment())
                    .commit()
        }
    }
}
