package ru.tradernet.presentation.ui.mainList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.main_list_fragment.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import org.koin.androidx.viewmodel.ext.android.viewModel

import ru.tradernet.test.R
import ru.tradernet.presentation.Event
import ru.tradernet.presentation.EventObserver

class MainListFragment : Fragment() {

    private val viewModel by viewModel<MainListViewModel>()
    private lateinit var adapter: TikersAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupToolbar()

        adapter = TikersAdapter()
        list.adapter = adapter

        viewModel.tikers.observe(
            viewLifecycleOwner,
            Observer {
                adapter.submitList(it)
            }
        )

        viewModel.showProblemMessage.observe(
            viewLifecycleOwner,
            EventObserver {
                Snackbar.make(requireView(), R.string.error_text, Snackbar.LENGTH_SHORT).show()
            }
        )
    }

    private fun setupToolbar() {
        toolbar.apply {
            setTitle(R.string.main_list_title)
            inflateMenu(R.menu.main_list_menu)
            setOnMenuItemClickListener {
                viewModel.navigateToFavorites.postValue(Event(Unit))
                true
            }
        }
    }
}
