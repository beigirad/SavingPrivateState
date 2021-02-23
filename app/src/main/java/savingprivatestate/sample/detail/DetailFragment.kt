package savingprivatestate.sample.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import savingprivatestate.sample.R
import savingprivatestate.sample.databinding.FragmentDetailBinding
import savingprivatestate.sample.util.toFormattedString

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding: FragmentDetailBinding get() = _binding!!

    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModelFactory = DetailViewModel.Factory(this, arguments)
        val viewModelStore = findNavController().getBackStackEntry(R.id.detailFragment)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory).get()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        FragmentDetailBinding.inflate(inflater, container, false).also { _binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.tvArgs.text = arguments.toString()
        binding.tvState.text = savedInstanceState.toFormattedString()
        binding.btnInc.setOnClickListener { viewModel.incrementCount() }
        binding.btnDec.setOnClickListener { viewModel.decrementCount() }
        binding.btnAttributes.setOnClickListener {
            val direction = DetailFragmentDirections.actionDetailFragmentToAttributesFragment()
            findNavController().navigate(direction)
        }

        viewModel.cartLiveData.observe(viewLifecycleOwner) {
            binding.tvCart.text = it.toString()
        }

        viewModel.detailLiveData.observe(viewLifecycleOwner) {
            binding.tvDetail.text = it.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}