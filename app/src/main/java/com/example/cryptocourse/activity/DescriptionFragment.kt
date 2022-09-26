package com.example.cryptocourse.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.cryptocourse.databinding.FragmentDescriptionBinding


class DescriptionFragment : Fragment() {

    private var _binding: FragmentDescriptionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        _binding = FragmentDescriptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id : String? = arguments?.getString("idCoin")

        binding.scrollview.visibility = View.GONE
        binding.progressBar2.visibility = View.VISIBLE
        val viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        viewModel.getDescription(id)
        viewModel.description.observe(viewLifecycleOwner) { list ->
            list.body()?.let {
                Glide.with(this).load(it.image.large).into(binding.imageView)
                binding.textDescription.text = it.description.en
                binding.textCategories.text = it.categories.joinToString()
            }
            binding.progressBar2.visibility = View.GONE
            binding.scrollview.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}