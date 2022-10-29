package com.example.cryptocourse.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.cryptocourse.R
import com.example.cryptocourse.databinding.FragmentDescriptionBinding
import kotlinx.coroutines.launch


class DescriptionFragment : Fragment() {

    private var _binding: FragmentDescriptionBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: CoinViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        _binding = FragmentDescriptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val idCoin: String? = arguments?.getString("idCoin")
        binding.linerlayout.visibility = View.GONE
        binding.progressBar2.visibility = View.VISIBLE
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        viewModel.getDescription(idCoin)

        lifecycleScope.launch {
            viewModel.description
                .flowWithLifecycle(lifecycle,Lifecycle.State.STARTED)
                .collect { description ->
                    description.body()?.let {
                        Glide.with(this@DescriptionFragment).load(it.image.large).into(binding.imageView)
                        binding.textDescription.text = it.description.en
                        binding.textCategories.text = it.categories.joinToString()
                        (activity as MainActivity).supportActionBar?.title = it.name
                        binding.progressBar2.visibility = View.GONE
                        binding.linerlayout.visibility = View.VISIBLE
                    }
                }
        }

        viewModel.exception.observe(viewLifecycleOwner){ ex ->
            if (ex == "exception") {
                val bundle = Bundle()
                bundle.putString("idCoin",idCoin)
                Navigation.findNavController(view)
                    .navigate(R.id.action_DescriptionFragment_to_retryFragment,bundle)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}