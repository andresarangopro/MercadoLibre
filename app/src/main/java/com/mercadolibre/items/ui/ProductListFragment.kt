package com.mercadolibre.items.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.text.HtmlCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mercadolibre.items.R
import com.mercadolibre.items.core.exception.Failure
import com.mercadolibre.items.core.exception.Failure.NetworkConnection
import com.mercadolibre.items.core.exception.Failure.ServerError
import com.mercadolibre.items.core.extensions.failure
import com.mercadolibre.items.core.extensions.observe
import com.mercadolibre.items.core.extensions.onQueryTextChanged
import com.mercadolibre.items.core.platform.BaseFragment
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
class ProductListFragment : BaseFragment() {

    private var _binding: FragmentProductListBinding? = null

    @Inject
    lateinit var productAdapter: ProductListAdapter

    private val binding get() = _binding!!

    private val productListViewModel: ProductListViewModel by viewModels()

    private fun firstTimeCreated(savedInstanceState: Bundle?) = savedInstanceState == null

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
            showProgress()
            productListViewModel.postEvent(
                ProductListViewModel.EventsProductListViewModel.GetListBySearch(
                    it, 10
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
                    hideProgress()
                    productAdapter.collection = listProducts
                }
                is StatesProductListViewModel.ShowBottomLoader -> {
                    binding.progressBottom.visibility = View.VISIBLE
                }
                is StatesProductListViewModel.HideBottomLoader -> {
                    binding.progressBottom.visibility = View.GONE
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
        hideProgress()
        binding.progressBottom.visibility = View.GONE
        notifyWithAction(message, R.string.action_refresh) {}
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (firstTimeCreated(savedInstanceState = savedInstanceState) == false) {
            productListViewModel.postEvent(ProductListViewModel.EventsProductListViewModel.ReloadAdapterIfListIsDifferentOfNull)
        }
        initList()
    }

    private fun initList() {

        val gridLayoutManager = GridLayoutManager(context, 2)

        with(binding.rvPlaceList) {
            if (!productAdapter.hasObservers()) {
                productAdapter.setHasStableIds(true)
            }
            binding.rvPlaceList.adapter = productAdapter
            binding.rvPlaceList.layoutManager = gridLayoutManager

            productAdapter.clickListener = { product ->
                product.id?.let {
                    findNavController().navigate(
                        ProductListFragmentDirections.actionsOpenDetailProduct(it)
                    )
                }
            }
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val lastVisibleItem = gridLayoutManager.findLastVisibleItemPosition()
                    val totalItemCount = gridLayoutManager.itemCount
                    productListViewModel.listScrolled(lastVisibleItem, totalItemCount)
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}