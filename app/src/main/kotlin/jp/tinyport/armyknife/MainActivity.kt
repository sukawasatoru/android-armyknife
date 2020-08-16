package jp.tinyport.armyknife

import android.app.Activity
import android.os.Bundle
import jp.tinyport.armyknife.core.log

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        log.info("onCreate")

        super.onCreate(savedInstanceState)
    }
}
