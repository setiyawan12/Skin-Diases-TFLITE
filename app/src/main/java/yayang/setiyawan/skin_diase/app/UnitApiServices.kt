package yayang.setiyawan.skin_diase.app

import retrofit2.Call
import retrofit2.http.GET
import yayang.setiyawan.skin_diase.model.ListResponse
import yayang.setiyawan.skin_diase.model.Produk

interface UnitApiServices {
    @GET("api/product")
    fun getProduct():Call<ListResponse<Produk>>
}