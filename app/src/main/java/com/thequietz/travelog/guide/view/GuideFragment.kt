package com.thequietz.travelog.guide.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.thequietz.travelog.R
import com.thequietz.travelog.databinding.FragmentGuideBinding
import com.thequietz.travelog.guide.AllPlaceAdapter
import com.thequietz.travelog.guide.GuideViewModel
import com.thequietz.travelog.guide.RecommendPlaceAdapter
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "AREA_LIST"

@AndroidEntryPoint
class GuideFragment : Fragment() {
    private var _binding: FragmentGuideBinding? = null
    private val binding get() = _binding!!
    private val guideViewModel by viewModels<GuideViewModel>()
    lateinit var allPlaceAdapter: AllPlaceAdapter
    lateinit var recommendPlaceAdapter: RecommendPlaceAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_guide, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        allPlaceAdapter = AllPlaceAdapter()
        recommendPlaceAdapter = RecommendPlaceAdapter()
        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            viewModel = guideViewModel
            rvPlaceAll.adapter = allPlaceAdapter
            rvRecommendPlace.adapter = recommendPlaceAdapter
        }
        guideViewModel.setAllPlaceData()
        guideViewModel.setRecommendPlaceData()
        guideViewModel.allPlaceList.observe(viewLifecycleOwner, { it ->
            it?.let { allPlaceAdapter.submitList(it) }
        })
        guideViewModel.allRecommendPlaceList.observe(viewLifecycleOwner, { it ->
            it?.let { recommendPlaceAdapter.submitList(it) }
        })
    }
}
