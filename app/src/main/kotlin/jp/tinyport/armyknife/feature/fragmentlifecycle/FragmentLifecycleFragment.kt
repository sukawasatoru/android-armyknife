package jp.tinyport.armyknife.feature.fragmentlifecycle

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.leanback.app.DetailsSupportFragment
import androidx.leanback.widget.AbstractDetailsDescriptionPresenter
import androidx.leanback.widget.Action
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.DetailsOverviewRow
import androidx.leanback.widget.FullWidthDetailsOverviewRowPresenter
import jp.tinyport.armyknife.core.log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

private data class DetailsItem(val title: String, val body: String?, val subtitle: String?)

private sealed class DetailsAction(id: Long, label: CharSequence) : Action(id, label) {
    object Replace : DetailsAction(0, "Replace")
}

class FragmentLifecycleFragment : DetailsSupportFragment() {
    private lateinit var detailsRow: DetailsOverviewRow

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
            setOnActionClickedListener {
                val action = it as DetailsAction
                when (action) {
                    DetailsAction.Replace -> GlobalScope.launch {
                        // TODO:
                    }
                }.let { }
            }
        }

        detailsRow = DetailsOverviewRow(DetailsItem(
                title = "FragmentLifecycleFragment",
                body = null,
                subtitle = null)).apply {
            actionsAdapter = ArrayObjectAdapter().apply {
                setItems(listOf(DetailsAction.Replace), null)
            }
        }

        adapter = ArrayObjectAdapter(detailsPresenter).apply {
            setItems(listOf(detailsRow), null)
        }

        addLine("onAttach")
    }

    override fun onDetach() {
        addLine("onDetach")

        super.onDetach()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        addLine("onCreate")

        super.onCreate(savedInstanceState)

        prepareEntranceTransition()
        startEntranceTransition()
    }

    override fun onDestroy() {
        addLine("onDestroy")

        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        addLine("onSaveInstanceState")

        super.onSaveInstanceState(outState)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        addLine("onCreateView")

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onDestroyView() {
        addLine("onDestroyView")

        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        addLine("onViewCreated")

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        addLine("onActivityCreated")

        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        addLine("onViewStateRestored")

        super.onViewStateRestored(savedInstanceState)
    }

    override fun onStart() {
        addLine("onStart")

        super.onStart()
    }

    override fun onStop() {
        addLine("onStop")

        super.onStop()
    }

    override fun onResume() {
        addLine("onResume")

        super.onResume()
    }

    override fun onPause() {
        addLine("onPause")

        super.onPause()
    }

    private fun addLine(msg: CharSequence) {
        log.info("[FragmentLifecycleFragment] $msg")

        val item = detailsRow.item as DetailsItem
        detailsRow.item = item.copy(body = if (item.body == null) {
            "$msg\n"
        } else {
            "${item.body}$msg\n"
        })
    }
}
