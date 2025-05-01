package com.example.valora

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.valora.databinding.BottomSheetInputBinding
import com.example.valora.databinding.FragmentHomeBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [fragment_home.newInstance] factory method to
 * create an instance of this fragment.
 */
class fragment_home : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: MyAdapter
    private val itemList = mutableListOf<CategoryItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MyAdapter(itemList)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        binding.fabAdd.setOnClickListener {
            showBottomSheet()
        }
    }

    private fun showBottomSheet() {
        val dialog = BottomSheetDialog(requireContext())
        val sheetBinding = BottomSheetInputBinding.inflate(layoutInflater)
        dialog.setContentView(sheetBinding.root)

        sheetBinding.buttonSubmit.setOnClickListener {
            val title = sheetBinding.editTextTitle.text.toString()
            val description = sheetBinding.editTextDescription.text.toString()

            if (title.isNotEmpty() && description.isNotEmpty()) {
                itemList.add(CategoryItem(title, description))
                adapter.notifyItemInserted(itemList.size - 1)
                dialog.dismiss()
            } else {
                if (title.isEmpty()) sheetBinding.editTextTitle.error = "Required"
                if (description.isEmpty()) sheetBinding.editTextDescription.error = "Required"
            }
        }

        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Avoid memory leaks
            }
    }
