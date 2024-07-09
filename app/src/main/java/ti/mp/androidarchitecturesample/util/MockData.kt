package ti.mp.androidarchitecturesample.util

import ti.mp.androidarchitecturesample.productstore.domain.model.Product
import ti.mp.androidarchitecturesample.productstore.domain.model.Rating

object MockData {
    fun getStoreData() = arrayListOf(
        Product(
            id = 1,
            title = "Sample Product 1",
            description = "This is a sample description for product 1.",
            category = "Electronics",
            image = "https://example.com/product1.jpg",
            rating = Rating(
                range = 4.5,
                count = 100
            )
        ),
        Product(
            id = 2,
            title = "Sample Product 2",
            description = "This is a sample description for product 2.",
            category = "Books",
            image = "https://example.com/product2.jpg",
            rating = Rating(
                range = 4.0,
                count = 50
            )
        ),
        Product(
            id = 3,
            title = "Sample Product 3",
            description = "This is a sample description for product 3.",
            category = "Clothing",
            image = "https://example.com/product3.jpg",
            rating = Rating(
                range = 3.5,
                count = 75
            )
        ),
        Product(
            id = 4,
            title = "Sample Product 4",
            description = "This is a sample description for product 4.",
            category = "Toys",
            image = "https://example.com/product4.jpg",
            rating = Rating(
                range = 4.8,
                count = 200
            )
        ),
        Product(
            id = 5,
            title = "Sample Product 5",
            description = "This is a sample description for product 5.",
            category = "Home & Kitchen",
            image = "https://example.com/product5.jpg",
            rating = Rating(
                range = 4.2,
                count = 150
            )
        )
    )

    fun getRemoteData() = arrayListOf(
        Product(
            id = 1,
            title = "Sample Product 1",
            description = "This is a sample description for product 1.",
            category = "Electronics",
            image = "https://example.com/product1.jpg",
            rating = Rating(
                range = 4.5,
                count = 100
            )
        ),
        Product(
            id = 2,
            title = "Sample Product 2",
            description = "This is a sample description for product 2.",
            category = "Books",
            image = "https://example.com/product2.jpg",
            rating = Rating(
                range = 4.0,
                count = 50
            )
        ),
        Product(
            id = 3,
            title = "Sample Product 3",
            description = "This is a sample description for product 3.",
            category = "Clothing",
            image = "https://example.com/product3.jpg",
            rating = Rating(
                range = 3.5,
                count = 75
            )
        ),
        Product(
            id = 4,
            title = "Sample Product 4",
            description = "This is a sample description for product 4.",
            category = "Toys",
            image = "https://example.com/product4.jpg",
            rating = Rating(
                range = 4.8,
                count = 200
            )
        ),
        Product(
            id = 5,
            title = "Sample Product 5",
            description = "This is a sample description for product 5.",
            category = "Home & Kitchen",
            image = "https://example.com/product5.jpg",
            rating = Rating(
                range = 4.2,
                count = 150
            )
        )
    )
}