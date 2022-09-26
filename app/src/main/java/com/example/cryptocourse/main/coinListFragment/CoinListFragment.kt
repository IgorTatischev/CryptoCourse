package com.example.cryptocourse.main.coinListFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptocourse.R
import com.example.cryptocourse.databinding.FragmentCoinlistBinding
import com.example.cryptocourse.main.CoinViewModel

class CoinListFragment : Fragment() {

    private var _binding: FragmentCoinlistBinding? = null
    private val binding get() = _binding!!

    private lateinit var  recyclerView: RecyclerView
    lateinit var  adapter: CoinListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        _binding = FragmentCoinlistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progressBar.visibility = View.VISIBLE
        val viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        recyclerView = binding.listcoin
        adapter = CoinListAdapter()
        recyclerView.adapter = adapter

        binding.chipgroup.setOnCheckedStateChangeListener { group, id ->
            when(group.checkedChipId){
                R.id.chip1 -> {
                    viewModel.getCoinsListUSD()
                    viewModel.coinsList.observe(viewLifecycleOwner) { list ->
                        list.body()?.let { adapter.update(it) }
                        binding.progressBar.visibility = View.GONE
                    }
                }
                R.id.chip2 -> {
                    viewModel.getCoinsListEUR()
                    viewModel.coinsList.observe(viewLifecycleOwner) { list ->
                        list.body()?.let { adapter.update(it) }
                        binding.progressBar.visibility = View.GONE
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.chipgroup.check(binding.chip1.id)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}