package com.example.cryptocourse.coinListFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
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
        val viewModel = ViewModelProvider(this)[CoinListViewModel::class.java]
        recyclerView = binding.listcoin
        adapter = CoinListAdapter()
        recyclerView.adapter = adapter
        viewModel.getCoinsListUSD()
        viewModel.coinsList.observe(viewLifecycleOwner) { list ->
            list.body()?.let { adapter.update(it) }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}