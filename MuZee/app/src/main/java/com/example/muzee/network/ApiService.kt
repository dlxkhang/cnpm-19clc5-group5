
import com.example.muzee.network.*
import com.example.muzee.network.signup.SignUp_response
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

private const val BASE_URL = "http://192.168.11.109:3000"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ApiService {
    // Get product overview
    @GET("api/product")
    suspend fun getNewProducts(): List<NewProduct>

    // Search new product
    @GET("api/search/product")
    suspend fun searchNewProducts(@Query("key") key: String?): List<NewProduct>

    // Search new product by category
    @GET("api/search/product/category")
    suspend fun searchNewProductsByCategory(@Query("key") key: String?): List<NewProduct>

    // Get cart
    @GET("api/cart")
    suspend fun getCartProducts(@Query ("NID") NID: String?): List<CartProduct>

    // Post Add to cart
    @POST("api/product/add_to_cart")
    suspend fun addToCart(@Body cartProduct: AddToCartProduct): Void

    // Delete product from cart
    @POST("api/cart/delete")
    suspend fun deleteFromCart(@Body deleteProduct: DeleteFromCartProduct): Response<SignUp_response>



    // Old product
    // Get old product overview
    @GET("api/old_product/user_product")
    suspend fun getOldProducts(@Query ("NID") NID: String?): List<OldProduct>

    // Add old product
    @POST("api/old_product/add")
    suspend fun addOldProduct(@Body oldProduct: AddOldProduct): Response<SignUp_response>

    // Edit old product
    @POST("api/old_product/edit")
    suspend fun editOldProduct(@Body oldProduct: AddOldProduct): Response<SignUp_response>

    // Delete old product
    @POST("api/old_product/delete")
    suspend fun deleteOldProduct(@Body oldProductID: OldProductID): Response<SignUp_response>

    // Normal User Order
    // Get normal user order
    @GET("api/order")
    suspend fun getUserOders(@Query ("NID") NID: String?): List<UserOrder>

    // Place order
    @POST("api/order/place_order")
    suspend fun placeOrder(@Body placeOrder: PlaceOrder): Response<SignUp_response>

    // Cancel order
    @POST("api/order/cancel")
    suspend fun cancelOrder(@Body cancelOrder: CancelOrder): Response<SignUp_response>
}

object Api {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}