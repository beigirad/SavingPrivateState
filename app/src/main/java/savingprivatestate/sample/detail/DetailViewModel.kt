package savingprivatestate.sample.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import savingprivatestate.sample.data.ProductDetailEntity
import savingprivatestate.sample.data.Repository

class DetailViewModel(
    private val repository: Repository,
    private val productId: Int
) : ViewModel() {

    class Factory(private val productId: Int) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return DetailViewModel(repository = Repository, productId = productId) as T
        }
    }

    private val _detailLiveData = MutableLiveData<ProductDetailEntity>()
    val detailLiveData: LiveData<ProductDetailEntity> = _detailLiveData

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