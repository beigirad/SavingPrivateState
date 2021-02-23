package savingprivatestate.sample.detail

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.savedstate.SavedStateRegistryOwner
import kotlinx.coroutines.launch
import savingprivatestate.sample.data.ProductDetailEntity
import savingprivatestate.sample.data.Repository

class DetailViewModel(
    private val repository: Repository,
    private val handle: SavedStateHandle
) : ViewModel() {

    class Factory(owner: SavedStateRegistryOwner, args: Bundle?) : AbstractSavedStateViewModelFactory(owner, args) {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(key: String, modelClass: Class<T>, handle: SavedStateHandle): T {
            return DetailViewModel(repository = Repository, handle) as T
        }
    }

    private val _detailLiveData = MutableLiveData<ProductDetailEntity>()
    val detailLiveData: LiveData<ProductDetailEntity> = _detailLiveData

    private val _cartLiveData = MutableLiveData(0)
    val cartLiveData: LiveData<Int> = _cartLiveData

    private val productId = requireNotNull(handle.get<Int>("productId")) { "handle must contain 'productId'. handle=$handle" }

    init {
        handle.get<Bundle>("cart")?.getInt("cartCount")?.let {
            _cartLiveData.value = it
        }
        handle.setSavedStateProvider("cart") {
            bundleOf("cartCount" to _cartLiveData.value)
        }
        fetchProductDetail()
    }

    fun incrementCount() {
        _cartLiveData.value = _cartLiveData.value?.inc()
    }

    fun decrementCount() {
        _cartLiveData.value = _cartLiveData.value?.dec()
    }

    private fun fetchProductDetail() {
        viewModelScope.launch {
            val result = repository.getProductDetail(productId)
            _detailLiveData.value = result
        }
    }
}