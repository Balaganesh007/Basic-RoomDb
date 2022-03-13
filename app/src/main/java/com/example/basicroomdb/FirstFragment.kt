package com.example.basicroomdb

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.basicroomdb.database.DB
import com.example.basicroomdb.databinding.FirstFragmentBinding

class FirstFragment : Fragment() {

    private lateinit var binding: FirstFragmentBinding
    private lateinit var viewModel: FirstViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.first_fragment,container,false)

        val application = requireNotNull(this.activity).application

        val dataSource = DB.getInstance(application).dataDao

        val viewModelFactory = FirstViewModelFactory(dataSource, application)

        viewModel = ViewModelProvider(this,viewModelFactory).get(FirstViewModel::class.java)
        binding.lifecycleOwner = this

        viewModel.allname?.observe(viewLifecycleOwner, Observer {
            binding.textView.setText(it.toString())
        })

        binding.SaveButton.setOnClickListener {
            val word = binding.editTextTextPersonName.text.toString()
            Log.v("bala", word)
            viewModel.onSave(word)
        }
        binding.deleteButton.setOnClickListener {
            viewModel.onClear()
        }

        return binding.root
    }

}