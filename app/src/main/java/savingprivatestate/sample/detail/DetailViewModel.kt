package savingprivatestate.sample.detail

import android.os.Bundle
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

    private val productId = requireNotNull(handle.get<Int>("productId")) { "handle must contain 'productId'. handle=$handle" }

    init {
        fetchProductDetail()
    }

    private fun fetchProductDetail() {
        viewModelScope.launch {
            val result = repository.getProductDetail(productId)
            _detailLiveData.value = result
        }
    }
}