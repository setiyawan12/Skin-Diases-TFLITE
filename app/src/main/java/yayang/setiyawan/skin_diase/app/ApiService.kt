package yayang.setiyawan.skin_diase.app

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import yayang.setiyawan.skin_diase.model.ResponModel

interface ApiService {
    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password")password: String,
    ):Call<ResponModel>
}