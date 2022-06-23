package yayang.setiyawan.skin_diase.app

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class UnitApiConfig {
    companion object{
        private var retrofit: Retrofit? = null
        private var okHttpClient = OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS).build()

        fun APIService(): UnitApiServices = getClient().create(UnitApiServices::class.java)
        private fun getClient(): Retrofit {
            return if (retrofit == null) {
                retrofit = Retrofit.Builder().baseUrl(Config.baseUrl).client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create()).build()
                retrofit!!
            } else {
                retrofit!!
            }
        }
    }
}