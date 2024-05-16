package com.example.claculater.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.claculater.R
import com.example.claculater.data.App
import com.example.claculater.data.DataManger
import com.example.claculater.databinding.FragmentHomeBinding

/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment : Fragment(), AppInteractionListener{

    private lateinit var pageViewModel: PageViewModel
    private var _binding: FragmentHomeBinding? = null
    lateinit var adapter : AppAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProvider(this).get(PageViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
        adapter = AppAdapter(DataManger.apps, this)
        addItem()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root = binding.root

        val textView: TextView = binding.sectionLabel
        pageViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        DataManger.setAppsData()
        binding.recyclerApp.adapter = adapter
    }

    fun addItem(){
        val app = App("Telegram", true, "Picture")
        DataManger.addApp(app)
        adapter.setData(DataManger.apps)
    }
    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClickItem(app: App) {
        Toast.makeText(this.context, app.appName, Toast.LENGTH_SHORT).show()
    }

    override fun onSwitchLock() {
        TODO()
    }
}