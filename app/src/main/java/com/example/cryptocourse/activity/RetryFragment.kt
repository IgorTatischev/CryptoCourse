package com.example.cryptocourse.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.example.cryptocourse.R
import com.example.cryptocourse.databinding.FragmentRetryBinding


class RetryFragment : Fragment() {

    private var _binding: FragmentRetryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        _binding = FragmentRetryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val actionBar = (activity as MainActivity).supportActionBar
        val name : String? = arguments?.getString("nameFragment")
        val id : String? = arguments?.getString("idCoin")
        when(name)
        {
            "list" ->   actionBar?.setDisplayHomeAsUpEnabled(false);
            "description" -> actionBar?.setDisplayHomeAsUpEnabled(true);
        }
        binding.buttonRetry.setOnClickListener {
            when(name)
            {
                "list" ->  Navigation.findNavController(it).navigate(R.id.action_retryFragment_to_ListFragment)
                "description" -> {
                    val bundle = Bundle()
                    bundle.putString("idCoin",id)
                    Navigation.findNavController(it)
                        .navigate(R.id.action_retryFragment_to_DescriptionFragment,bundle)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}