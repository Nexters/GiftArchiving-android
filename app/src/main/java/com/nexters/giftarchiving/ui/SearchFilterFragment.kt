package com.nexters.giftarchiving.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.base.BaseFragment
import com.nexters.giftarchiving.data.room.LatestSearch
import com.nexters.giftarchiving.data.room.LatestSearchDB
import com.nexters.giftarchiving.data.room.LatestSearchDao
import com.nexters.giftarchiving.databinding.FragmentSearchFilterBinding
import com.nexters.giftarchiving.extension.observe
import com.nexters.giftarchiving.viewmodel.SearchViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

internal class SearchFilterFragment : BaseFragment<SearchViewModel, FragmentSearchFilterBinding>() {
    override val layoutId = R.layout.fragment_search_filter
    override val viewModel: SearchViewModel by viewModels({requireParentFragment()})
    private val latestSearchDao : LatestSearchDao by lazy { LatestSearchDB.getInstance(requireContext()).latestSearchDao() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val allLatestSearch = getAllLatestSearch()
        observe(allLatestSearch){
            if(it.isEmpty()){
                binding.searchLatestLayout.visibility = View.GONE
            } else{
                binding.searchLatestLayout.visibility = View.VISIBLE
            }
        }
        val latestRecyclerView = binding.searchLatestRecyclerView
        latestRecyclerView.apply {
            observe(allLatestSearch){
                adapter = LatestListAdapter(it){ keyword ->
                    viewModel.setCurrentSearchText(keyword)
                }
            }
            layoutManager = LinearLayoutManager(requireContext()).also { it.orientation = LinearLayoutManager.HORIZONTAL }
        }

        val personRecyclerView = binding.searchPersonRecyclerView
        personRecyclerView.apply {
            observe(viewModel.getAllName){
                adapter = PersonListAdapter(it) { name ->
                    viewModel.setCurrentSearchText(name)
                }
            }
            layoutManager = LinearLayoutManager(requireContext()).also { it.orientation = LinearLayoutManager.VERTICAL }
        }

        binding.searchLatestDeleteButton.setOnClickListener {
            deleteLatestSearch()
        }
    }

    private fun getAllLatestSearch() : LiveData<List<LatestSearch>> {
        return latestSearchDao.getAll()
    }

    private fun deleteLatestSearch() {
        runBlocking (Dispatchers.IO) {
            latestSearchDao.deleteSearch()
        }
    }

    inner class LatestListAdapter(private val keywords: List<LatestSearch>, val itemClick : (String) -> Unit) : RecyclerView.Adapter<LatestListAdapter.PagerViewHolder>() {

        inner class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            private val nameTextView: TextView = itemView.findViewById(R.id.recyclerView_latest_keyword)

            fun bind(keyword: LatestSearch) {
                nameTextView.text = keyword.keyword
                itemView.setOnClickListener { itemClick(keyword.keyword) }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.search_latest_recyclerview_item, parent, false)
            return PagerViewHolder(view)
        }
        override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
            holder.bind(keywords[position])

        }

        override fun getItemCount(): Int = keywords.size

    }

    inner class PersonListAdapter(private val names: ArrayList<String>, val itemClick : (String) -> Unit) : RecyclerView.Adapter<PersonListAdapter.PagerViewHolder>() {

        inner class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            private val nameTextView: TextView = itemView.findViewById(R.id.recyclerView_search_person_name)

            fun bind(name: String) {
                nameTextView.text = name
                itemView.setOnClickListener { itemClick(name) }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.search_person_recyclerview_item, parent, false)
            return PagerViewHolder(view)
        }
        override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
            holder.bind(names[position])

        }

        override fun getItemCount(): Int = names.size

    }
}