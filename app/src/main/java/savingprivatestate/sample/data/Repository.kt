package savingprivatestate.sample.data

import kotlinx.coroutines.delay

object Repository {
    suspend fun getProductDetail(productId: Int): ProductDetailEntity {
        delay(1200)
        return ProductDetailEntity(
            name = "Google Phone 10",
            attributes = listOf(
                "Color=Black",
                "Camera=16MP",
                "Display:7\"",
                "Type=Tablet"
            ),
            inStock = 16
        )
    }
}