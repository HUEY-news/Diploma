package ru.practicum.android.diploma.filter.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.practicum.android.diploma.R

class IndustryFragment : Fragment() {
    /*   override fun onCreate(savedInstanceState: Bundle?) {
           super.onCreate(savedInstanceState)
       }*/
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_industry, container, false)
    }

}
