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
import savingprivatestate.sample.databinding.FragmentAttributesBinding
import savingprivatestate.sample.util.toFormattedString

class AttributesFragment : Fragment() {
    private var _binding: FragmentAttributesBinding? = null
    private val binding: FragmentAttributesBinding get() = _binding!!

    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModelFactory = DetailViewModel.Factory(this, arguments)
        val viewModelStore = findNavController().getBackStackEntry(R.id.detailFragment)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory).get()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        FragmentAttributesBinding.inflate(inflater, container, false).also { _binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.tvArgs.text = arguments.toString()
        binding.tvState.text = savedInstanceState.toFormattedString()

        viewModel.detailLiveData.observe(viewLifecycleOwner) {
            binding.tvDetail.text = it.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}