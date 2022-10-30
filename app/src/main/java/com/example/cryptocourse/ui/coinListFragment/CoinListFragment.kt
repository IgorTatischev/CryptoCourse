package com.example.cryptocourse.ui.coinListFragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptocourse.R
import com.example.cryptocourse.ui.CoinViewModel
import com.example.cryptocourse.databinding.FragmentCoinlistBinding
import com.example.cryptocourse.model.coins.CoinItem
import com.google.android.material.snackbar.Snackbar

class CoinListFragment : Fragment() {

    private var _binding: FragmentCoinlistBinding? = null
    private val binding get() = _binding!!

    private lateinit var  recyclerView: RecyclerView
    private  lateinit var  coinListAdapter: CoinListAdapter
    private val viewModel: CoinViewModel by viewModels()
    private  var isUsd = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getCoinsListUSD("chips")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        _binding = FragmentCoinlistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.chipgroup.setOnCheckedStateChangeListener { group, _ ->
            when(group.checkedChipId){
                R.id.chip1 -> {
                    progressLoad()
                    viewModel.getCoinsListUSD("chips")
                    isUsd = true
                }
                R.id.chip2 -> {
                    progressLoad()
                    viewModel.getCoinsListEUR("chips")
                    isUsd = false
                }
            }
        }
        val swipeRefreshLayout = binding.swipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener {
            if (isUsd) viewModel.getCoinsListUSD("swipe")
            else viewModel.getCoinsListEUR("swipe")
        }

        recyclerView = binding.listcoin
        coinListAdapter = CoinListAdapter(object : CoinListAdapter.Listener {
            override fun onItemClick(coin: CoinItem) {
                val bundle = Bundle()
                bundle.putString("idCoin", coin.id)
                Navigation.findNavController(view).navigate(R.id.action_LirstFragment_to_DescriptionFragment,bundle)
            }
        })

        (recyclerView.itemAnimator as? DefaultItemAnimator)?.supportsChangeAnimations = false
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = coinListAdapter
        progressLoad()

        viewModel.coinsList.observe(viewLifecycleOwner) { list ->
            list.body()?.let {
                coinListAdapter.update(isUsd)
                coinListAdapter.submitList(it)
            }
            binding.progressBar.visibility = View.GONE
            binding.listcoin.visibility = View.VISIBLE
            binding.swipeRefreshLayout.isRefreshing = false
        }

        viewModel.exception.observe(viewLifecycleOwner) { ex ->
            if (ex == "create"){
                Navigation.findNavController(view)
                    .navigate(R.id.action_ListFragment_to_retryFragment)
            }
            else if(ex == "refresh"){
                val snackbar = Snackbar.make(view, resources.getString(R.string.errorSnackBar) ,Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.parseColor("#FF5252"))
                    .setTextColor(Color.parseColor("#FFFFFF"))
                snackbar.show()
                binding.swipeRefreshLayout.isRefreshing = false
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