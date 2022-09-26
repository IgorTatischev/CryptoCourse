package com.example.cryptocourse.coinListFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptocourse.R
import com.example.cryptocourse.databinding.FragmentCoinlistBinding

class CoinListFragment : Fragment() {

    private var _binding: FragmentCoinlistBinding? = null
    private val binding get() = _binding!!

    lateinit var  recyclerView: RecyclerView
    lateinit var  adapter: CoinListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        _binding = FragmentCoinlistBinding.inflate(inflater, container, false)
                binding.chipgroup.setOnCheckedStateChangeListener { group, id ->
            when(group.checkedChipId){
                R.id.chip1 -> {
                    createUSD();
                }
                R.id.chip2 -> {
                    createEUR();
                }
            }

        }
        return binding.root
    }

    private fun createUSD(){
        val viewModel = ViewModelProvider(this)[CoinListViewModel::class.java]
        recyclerView = binding.listcoin
        adapter = CoinListAdapter()
        recyclerView.adapter = adapter
        viewModel.getCoinsListUSD()
        viewModel.coinsList.observe(viewLifecycleOwner) { list ->
            list.body()?.let { adapter.update(it) }
        }
    }

    private fun createEUR(){
        val viewModel = ViewModelProvider(this)[CoinListViewModel::class.java]
        recyclerView = binding.listcoin
        adapter = CoinListAdapter()
        recyclerView.adapter = adapter
        viewModel.getCoinsListEUR()
        viewModel.coinsList.observe(viewLifecycleOwner) { list ->
            list.body()?.let { adapter.update(it) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}