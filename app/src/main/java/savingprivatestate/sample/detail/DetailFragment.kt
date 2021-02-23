package savingprivatestate.sample.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import savingprivatestate.sample.databinding.FragmentDetailBinding
import savingprivatestate.sample.util.toFormattedString

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding: FragmentDetailBinding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        FragmentDetailBinding.inflate(inflater, container, false).also { _binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.tvArgs.text = arguments.toString()
        binding.tvState.text = savedInstanceState.toFormattedString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}