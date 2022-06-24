package com.mercadolibre.items.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.mercadolibre.items.R
import com.mercadolibre.items.core.exception.Failure
import com.mercadolibre.items.core.extensions.failure
import com.mercadolibre.items.core.extensions.observe
import com.mercadolibre.items.data.ProductFailure
import com.mercadolibre.items.databinding.FragmentDetailProductBinding
import com.mercadolibre.items.ui.adapters.ProductListAdapter
import com.mercadolibre.items.ui.adapters.ViewPagerAdapter
import com.mercadolibre.items.ui.viewmodels.ProductDetailViewModel
import com.mercadolibre.items.ui.viewmodels.ProductListViewModel
import com.mercadolibre.items.ui.viewmodels.States
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProductDetailFragment : Fragment() {

    private var _binding: FragmentDetailProductBinding? = null

    private val binding get() = _binding!!

    private val detailProductViewModel: ProductDetailViewModel by viewModels()

    private val args: ProductDetailFragmentArgs by navArgs()

    @Inject
    lateinit var viewPager: ViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(detailProductViewModel) {
            observe(states, ::validateStates)
            failure(failure, ::handleFailure)
        }
    }

    private fun validateStates(event: States<ProductDetailViewModel.StatesProductListViewModel>?) {
        event?.getContentIfNotHandled()?.let { navigation ->
            when (navigation) {
                is ProductDetailViewModel.StatesProductListViewModel.LoadDetailProduct -> navigation.run {
                    binding.product = product
                    viewPager.collection = product.pictures
                }
            }
        }
    }

    private fun viewPagerPageChanged() {
        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                binding.countImages = "${position.plus(1)} /${viewPager.collection.size}"
            }
        })
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is Failure.NetworkConnection -> renderFailure(R.string.failure_network_connection)
            is Failure.ServerError -> renderFailure(R.string.failure_server_error)
            is ProductFailure.ListNotAvailable -> renderFailure(R.string.failure_products_list_unavailable)
            else -> renderFailure(R.string.failure_server_error)
        }
    }

    private fun renderFailure(@StringRes message: Int) {
        Log.d("err", "joder ${requireContext().resources.getText(message)}")
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager = ViewPagerAdapter()
        binding.viewPager2.adapter = viewPager
        detailProductViewModel.postEvent(
            ProductDetailViewModel.EventsProductListViewModel.GetDetailProduct(
                args.idProduct
            )
        )
        viewPagerPageChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}