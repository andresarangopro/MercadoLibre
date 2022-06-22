package com.mercadolibre.items.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.viewModels
import com.mercadolibre.items.R
import com.mercadolibre.items.core.exception.Failure
import com.mercadolibre.items.core.exception.Failure.NetworkConnection
import com.mercadolibre.items.core.exception.Failure.ServerError
import com.mercadolibre.items.core.extensions.failure
import com.mercadolibre.items.core.extensions.observe
import com.mercadolibre.items.data.ProductFailure.ListNotAvailable
import com.mercadolibre.items.databinding.FragmentProductListBinding
import com.mercadolibre.items.ui.viewmodels.ProductListViewModel
import com.mercadolibre.items.ui.viewmodels.ProductListViewModel.EventsProductListViewModel.SearchedWord
import com.mercadolibre.items.ui.viewmodels.ProductListViewModel.StatesProductListViewModel.LoadAdapterListProducts
import com.mercadolibre.items.ui.viewmodels.ProductListViewModel.StatesProductListViewModel
import com.mercadolibre.items.ui.viewmodels.States
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductListFragment : Fragment() {

    private var _binding: FragmentProductListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val productListViewModel: ProductListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(productListViewModel) {
            observe(states, ::validateEvents)
            failure(failure, ::handleFailure)
        }

    }

    private fun validateEvents(event: States<StatesProductListViewModel>?) {
        event?.getContentIfNotHandled()?.let { navigation ->
            when (navigation) {
                is LoadAdapterListProducts -> navigation.run{
                    listProducts.forEach {
                        Log.d("COÑO", "${it.id} -- ${it.title} -- ${it.price}")
                    }
                }
            }
        }
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is NetworkConnection -> renderFailure(R.string.failure_network_connection)
            is ServerError -> renderFailure(R.string.failure_server_error)
            is ListNotAvailable -> renderFailure(R.string.failure_products_list_unavailable)
            else -> renderFailure(R.string.failure_server_error)
        }
    }

    private fun renderFailure(@StringRes message: Int) {
        Log.d("err","joder ${requireContext().getResources().getText(message)}")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productListViewModel.postEvent(SearchedWord("Motorola"))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}