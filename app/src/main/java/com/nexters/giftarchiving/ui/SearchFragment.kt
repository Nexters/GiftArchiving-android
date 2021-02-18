package com.nexters.giftarchiving.ui

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.view.inputmethod.EditorInfo
import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.base.BaseFragment
import com.nexters.giftarchiving.data.room.LatestSearchDB
import com.nexters.giftarchiving.data.room.LatestSearchDao
import com.nexters.giftarchiving.databinding.FragmentSearchBinding
import com.nexters.giftarchiving.extension.observe
import com.nexters.giftarchiving.extension.toast
import com.nexters.giftarchiving.viewmodel.SearchViewModel
import kotlinx.coroutines.*
import org.koin.android.viewmodel.ext.android.viewModel

internal class SearchFragment : BaseFragment<SearchViewModel, FragmentSearchBinding>() {
    override val layoutId = R.layout.fragment_search
    override val viewModel: SearchViewModel by viewModel()
    private val latestSearchDao : LatestSearchDao by lazy { LatestSearchDB.getInstance(requireContext()).latestSearchDao() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        GlobalScope.launch(Dispatchers.Main) {
            delay(100L)
            withContext(Dispatchers.Main){
                observe(viewModel.fragmentType){
                    changeFragment(it)
                }
                binding.searchAutoCompleteTextView.setOnEditorActionListener { v, actionId, event ->
                    if(actionId==EditorInfo.IME_ACTION_DONE){
                        if(binding.searchAutoCompleteTextView.text.toString()==""){
                            toast("검색어를 입력해주세요")
                        } else{
                            viewModel.setCurrentSearchText(binding.searchAutoCompleteTextView.text.toString())
                            insertKeyword(binding.searchAutoCompleteTextView.text.toString())
                        }
                    } else{
                        return@setOnEditorActionListener false
                    }
                    return@setOnEditorActionListener true
                }
                observe(viewModel.currentSearchText){
                    binding.searchAutoCompleteTextView.text = Editable.Factory.getInstance().newEditable(it)
                }
            }
        }
    }


    fun changeFragment(it : Int){
        val cFM = childFragmentManager.beginTransaction()
        when(it){
            0->cFM.replace(R.id.search_fragment, SearchFilterFragment()).commit()
            1->cFM.replace(R.id.search_fragment, SearchResultFragment()).commit()
            2->cFM.replace(R.id.search_fragment, SearchNoneFragment()).commit()
            3->cFM.replace(R.id.search_fragment, SearchErrorFragment()).commit()
        }
    }

    private fun insertKeyword(keyword : String) {
        runBlocking(Dispatchers.IO) {
            latestSearchDao.insert(keyword)
        }
    }
}