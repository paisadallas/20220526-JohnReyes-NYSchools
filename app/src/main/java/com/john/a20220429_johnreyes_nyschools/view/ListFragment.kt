package com.john.a20220429_johnreyes_nyschools.view

import android.net.NetworkRequest
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.john.a20220429_johnreyes_nyschools.R
import com.john.a20220429_johnreyes_nyschools.adapter.SchoolAdapter
import com.john.a20220429_johnreyes_nyschools.databinding.FragmentListBinding
import com.john.a20220429_johnreyes_nyschools.model.School
import com.john.a20220429_johnreyes_nyschools.model.SchoolItem
import com.john.a20220429_johnreyes_nyschools.res.API
import com.john.a20220429_johnreyes_nyschools.utils.SchoolState
import com.john.a20220429_johnreyes_nyschools.viewmodel.SchoolViewModel


class ListFragment : BaseFragment() {

    private val schoolAdapter by lazy {
        SchoolAdapter(){
            Log.d("CLICK","YOU DID CLICK"+it.school_name)
            var data = it.dbn

            val intention = ListFragmentDirections.actionListFragmentToSchoolFragment(data)

            findNavController().navigate(intention)
           // schoolViewModel.getScore()
        }
    }

    private val binding by lazy {
        FragmentListBinding.inflate(layoutInflater)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.rvSchool.apply {
            layoutManager =
                LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            adapter = schoolAdapter
        }

        schoolViewModel.getSchool()
        schoolViewModel.school.observe(viewLifecycleOwner){
            when(it){
                SchoolState.LOADING ->{

                }
                is SchoolState.SUCCESS<*>->{
                    var schools = it?.school as School
                    schools.let {
                        schoolAdapter.update(it)
                    }
                   // Log.d("RECEIVING",schoolItem[0].school_name)

                }
            }
        }

        return binding.root
    }

}