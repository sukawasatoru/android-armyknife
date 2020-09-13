package jp.tinyport.armyknife

import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import androidx.leanback.app.VerticalGridSupportFragment
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.ImageCardView
import androidx.leanback.widget.Presenter
import androidx.leanback.widget.VerticalGridPresenter
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import jp.tinyport.armyknife.core.log

sealed class ItemCommand {
    companion object {
        fun fromId(id: Int): ItemCommand? {
            return when (id) {
                0 -> ItemCommand.KeyStore
                1 -> ItemCommand.FragmentLifecycle
                else -> null
            }
        }
    }

    val id: Int
        get() = when (this) {
            KeyStore -> 0
            FragmentLifecycle -> 1
        }

    object KeyStore : ItemCommand()
    object FragmentLifecycle : ItemCommand()
}

private object MenuPresenter : Presenter() {
    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        return MenuPresenterViewHolder(ImageCardView(parent.context))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, item: Any) {
        val vh = viewHolder as MenuPresenterViewHolder
        val command = item as ItemCommand

        vh.cardView.setMainImageDimensions(640, 0)
        vh.cardView.titleText = when (command) {
            ItemCommand.KeyStore -> "KeyStore"
            ItemCommand.FragmentLifecycle -> "FragmentLifecycle"
        }
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder?) {
        // do nothing.
    }
}

private class MenuPresenterViewHolder(val cardView: ImageCardView) : Presenter.ViewHolder(cardView)

class MainFragment : VerticalGridSupportFragment() {
    private lateinit var nav: NavController
    override fun onAttach(context: Context) {
        log.debug("[MainFragment] onAttach")

        super.onAttach(context)

        title = "Armyknife"

        gridPresenter = VerticalGridPresenter().apply {
            numberOfColumns = 1
        }

        adapter = ArrayObjectAdapter(MenuPresenter).apply {
            setItems(listOf(
                    ItemCommand.KeyStore,
                    ItemCommand.FragmentLifecycle),
                    null)
        }

        setOnItemViewClickedListener { _, item, _, _ ->
            log.debug("[MainFragment] onItemViewClicked")

            val command = item as ItemCommand
            nav.navigate(MainFragmentDirections.actionMainFragmentToDetailActivity(command.id))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        log.debug("[MainFragment] onCreate")

        super.onCreate(savedInstanceState)

        nav = findNavController()

        prepareEntranceTransition()
        startEntranceTransition()
    }
}
