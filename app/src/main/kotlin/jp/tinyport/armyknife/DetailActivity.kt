package jp.tinyport.armyknife

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.navigation.navArgs
import jp.tinyport.armyknife.feature.fragmentlifecycle.FragmentLifecycleFragment
import jp.tinyport.armyknife.feature.keystore.KeyStoreFragment

class DetailActivity : FragmentActivity() {
    private val args by navArgs<DetailActivityArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            val fragment = when (ItemCommand.fromId(args.id)!!) {
                ItemCommand.KeyStore -> KeyStoreFragment()
                ItemCommand.FragmentLifecycle -> FragmentLifecycleFragment()
            }

            supportFragmentManager.beginTransaction()
                    .replace(android.R.id.content, fragment)
                    .commit()
        }
    }
}
