package com.mercadolibre.items.ui


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mercadolibre.items.R
import com.mercadolibre.items.core.exception.Failure
import com.mercadolibre.items.core.exception.Failure.NetworkConnection
import com.mercadolibre.items.core.exception.Failure.ServerError
import com.mercadolibre.items.core.extensions.failure
import com.mercadolibre.items.core.extensions.observe
import com.mercadolibre.items.core.extensions.onQueryTextChanged
import com.mercadolibre.items.data.ProductFailure.ListNotAvailable
import com.mercadolibre.items.databinding.FragmentProductListBinding
import com.mercadolibre.items.ui.adapters.ProductListAdapter
import com.mercadolibre.items.ui.viewmodels.ProductListViewModel
import com.mercadolibre.items.ui.viewmodels.ProductListViewModel.StatesProductListViewModel
import com.mercadolibre.items.ui.viewmodels.ProductListViewModel.StatesProductListViewModel.LoadAdapterListProducts
import com.mercadolibre.items.ui.viewmodels.States
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ProductListFragment : Fragment() {

    private var _binding: FragmentProductListBinding? = null

    @Inject
    lateinit var productAdapter: ProductListAdapter

    private val binding get() = _binding!!

    private val productListViewModel: ProductListViewModel by viewModels()

    internal fun firstTimeCreated(savedInstanceState: Bundle?) = savedInstanceState == null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProductListBinding.inflate(inflater, container, false)


        val toolbar: Toolbar = binding.toolbar
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)

        binding.searchProduct.queryHint = HtmlCompat.fromHtml(
            "<font color = #808080>" + resources.getString(R.string.hintSearchMess) + "</font>",
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )

        binding.searchProduct.onQueryTextChanged {
            productListViewModel.postEvent(
                ProductListViewModel.EventsProductListViewModel.WroteWord(
                    it
                )
            )
        }
        return binding.root

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(productListViewModel) {
            observe(states, ::validateStates)
            failure(failure, ::handleFailure)
        }

    }

    private fun validateStates(event: States<StatesProductListViewModel>?) {
        event?.getContentIfNotHandled()?.let { navigation ->
            when (navigation) {
                is LoadAdapterListProducts -> navigation.run {
                    productAdapter.collection = listProducts.orEmpty()
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
        Log.d("err", "joder ${requireContext().getResources().getText(message)}")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (firstTimeCreated(savedInstanceState) == false) {
            productListViewModel.postEvent(ProductListViewModel.EventsProductListViewModel.ReloadAdapterIfListIsDifferentOfNull)
        }

        binding.rvPlaceList.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.rvPlaceList.adapter = productAdapter
        productAdapter.clickListener = { product ->
            product.id?.let {
                findNavController().navigate(
                    ProductListFragmentDirections.actionsOpenDetailProduct(it)
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}