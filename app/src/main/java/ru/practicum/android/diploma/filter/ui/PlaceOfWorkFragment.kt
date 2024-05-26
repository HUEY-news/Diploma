package ru.practicum.android.diploma.filter.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.practicum.android.diploma.databinding.FragmentFiltrationWorkplaceBinding

class PlaceOfWorkFragment : Fragment() {
    private var _binding: FragmentFiltrationWorkplaceBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFiltrationWorkplaceBinding.inflate(inflater, container, false)
        return binding.root
    }
}
