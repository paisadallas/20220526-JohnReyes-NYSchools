package com.john.a20220526_johnreyes_nyschools.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.john.a20220526_johnreyes_nyschools.R
import com.john.a20220526_johnreyes_nyschools.databinding.FragmentSchoolBinding
import com.john.a20220526_johnreyes_nyschools.model.School
import com.john.a20220526_johnreyes_nyschools.model.SchoolItem
import com.john.a20220526_johnreyes_nyschools.utils.SchoolState

class SchoolFragment : BaseFragment() {

    val args: SchoolFragmentArgs by navArgs()
    private val binding by lazy {
        FragmentSchoolBinding.inflate(layoutInflater)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

            schoolViewModel.scoreItem.observe(viewLifecycleOwner){
                when(it){
                    SchoolState.LOADING ->{
                        Toast.makeText(activity, "Loading...", Toast.LENGTH_SHORT)
                            .show()
                    }
                    is SchoolState.SUCCESS<*>->{

                        var data = false
                        val schools = it.school as School

                        schools.let { schoolsItem ->

                            schoolsItem.forEach { school ->
                                if(args.dbn == school.dbn){
                                    bind(school)
                                    data = true
                                }
                            }
                            if (!data){
                                binding.tvSchoolName.text = "School not found!"
                            }
                        }
                    }

                    is SchoolState.ERROR ->{
                        Toast.makeText(requireContext(), it.error.localizedMessage, Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }

        return binding.root
    }

    private fun bind(schoolItem: SchoolItem) {
        binding.tvSchoolName.text = schoolItem.school_name
        binding.tvTakers.text = context?.getString(R.string.school_taker, schoolItem.sat_takers)
        binding.tvCritical.text = context?.getString(R.string.school_critical,schoolItem.sat_critical)
        binding.tvMath.text = context?.getString(R.string.school_math,schoolItem.sat_math)
        binding.tvWriting.text = context?.getString(R.string.school_writing,schoolItem.sat_writing)
    }

}