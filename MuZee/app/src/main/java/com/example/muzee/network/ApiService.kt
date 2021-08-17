
import com.example.muzee.network.AddToCartProduct
import com.example.muzee.network.CartProduct
import com.example.muzee.network.NewProduct
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

private const val BASE_URL = "http://192.168.31.133:3000"

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

    // Get cart
    @GET("api/cart")
    suspend fun getCartProducts(): List<CartProduct>

    // Post Add to cart
    @POST("api/product/add_to_cart")
    suspend fun addToCart(@Body cartProduct: AddToCartProduct): Void
}

object Api {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}