package com.example.inventory.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.inventory.InventoryApplication
import com.example.inventory.R
import com.example.inventory.adapter.ItemListAdapter
import com.example.inventory.database.entity.Item
import com.example.inventory.databinding.ItemListFragmentBinding
import com.example.inventory.viewmodel.InventoryViewModel
import com.example.inventory.viewmodel.InventoryViewModelFactory

/**
 * Main fragment displaying details for all items in the database.
 */
class ItemListFragment : Fragment() {

    private var _binding: ItemListFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: InventoryViewModel by activityViewModels {
        InventoryViewModelFactory((activity?.application as InventoryApplication).database.itemDao())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View {
        _binding = ItemListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.floatingActionButton.setOnClickListener { onFabClick() }

        val itemListAdapter = ItemListAdapter { item -> onItemClick(item) }
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = itemListAdapter
        }

        viewModel.allItems.observe(viewLifecycleOwner, { items ->
            items?.let { itemListAdapter.submitList(it) }
        })
    }

    private fun onFabClick() {
        val addItemAction = ItemListFragmentDirections.actionItemListFragmentToAddItemFragment(
            getString(R.string.add_fragment_title)
        )
        this.findNavController().navigate(addItemAction)
    }

    private fun onItemClick(item: Item) {
        val action = ItemListFragmentDirections.actionItemListFragmentToItemDetailFragment(item.id)
        this.findNavController().navigate(action)
    }
}
