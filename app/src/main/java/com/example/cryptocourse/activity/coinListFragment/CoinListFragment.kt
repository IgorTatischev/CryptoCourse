package com.example.cryptocourse.activity.coinListFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptocourse.R
import com.example.cryptocourse.activity.CoinViewModel
import com.example.cryptocourse.databinding.FragmentCoinlistBinding

class CoinListFragment : Fragment() {

    private var _binding: FragmentCoinlistBinding? = null
    private val binding get() = _binding!!

    private lateinit var  recyclerView: RecyclerView
    lateinit var  adapter: CoinListAdapter
    private val viewModel: CoinViewModel by viewModels()
    var isUsd = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setCoinsListUSD("chips")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        _binding = FragmentCoinlistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.chipgroup.setOnCheckedStateChangeListener { group, id ->
            when(group.checkedChipId){
                R.id.chip1 -> {
                    viewModel.setCoinsListUSD("chips")
                    isUsd = true
                    progressLoad()
                }
                R.id.chip2 -> {
                    progressLoad()
                    viewModel.setCoinsListEUR("chips")
                    isUsd = false
                    progressLoad()
                }
            }
        }
        recyclerView = binding.listcoin
        adapter = CoinListAdapter()
        recyclerView.adapter = adapter
        progressLoad()
        viewModel.coinsList.observe(viewLifecycleOwner) { list ->
            list.body()?.let { adapter.update(it,isUsd) }
            binding.progressBar.visibility = View.GONE
            binding.listcoin.visibility = View.VISIBLE
        }
        viewModel.exception.observe(viewLifecycleOwner) { ex ->
            if (ex == "create"){
                Navigation.findNavController(view)
                    .navigate(R.id.action_ListFragment_to_retryFragment)
            }
            else if(ex == "refresh"){
                //snackbar
            }
        }
    }

    private fun progressLoad(){
        binding.progressBar.visibility = View.VISIBLE
        binding.listcoin.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}