package com.example.cryptocourse.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.bumptech.glide.Glide
import com.example.cryptocourse.R
import com.example.cryptocourse.databinding.FragmentDescriptionBinding


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
        viewModel.setDescription(idCoin)
        viewModel.description.observe(viewLifecycleOwner) { description ->
            description.body()?.let {
                Glide.with(this).load(it.image.large).into(binding.imageView)
                binding.textDescription.text = it.description.en
                binding.textCategories.text = it.categories.joinToString()
                (activity as MainActivity).supportActionBar?.title = it.name
                binding.progressBar2.visibility = View.GONE
                binding.linerlayout.visibility = View.VISIBLE
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