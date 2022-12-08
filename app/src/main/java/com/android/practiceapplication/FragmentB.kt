package com.android.practiceapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_b.*

class FragmentB : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_b, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sendBtn.setOnClickListener {
            val message = inputTextEditText.text.toString()
            if (message.isNotEmpty()) {
                val action = FragmentBDirections.actionFragmentBToFragmentC(message)
                Navigation.findNavController(view).navigate(action)
            }
        }
    }
}