package yayang.setiyawan.skin_diase.presenter

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import yayang.setiyawan.skin_diase.app.UnitApiConfig
import yayang.setiyawan.skin_diase.contract.ProductContract
import yayang.setiyawan.skin_diase.model.ListResponse
import yayang.setiyawan.skin_diase.model.Produk

class ProductPresenter(v:ProductContract.View?):ProductContract.Presenter {
    private var view:ProductContract.View?=v
    override fun getAllProduct() {
        val webServices = UnitApiConfig.APIService()
        val request = webServices.getProduct()
        request.enqueue(object : Callback<ListResponse<Produk>>{
            override fun onResponse(
                call: Call<ListResponse<Produk>>,
                response: Response<ListResponse<Produk>>
            ) {
                if (response.isSuccessful){
                    val  body = response.body()
                    if (body != null){
                        if (body.data.isNotEmpty()){
                            view?.attachToRecycler(body.data)
                            view?.emptydata(false)
                        }else{
                            view?.emptydata(true)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ListResponse<Produk>>, t: Throwable) {
                view?.toast("terjadi kesalahan server")
            }

        })
    }

    override fun destroy() {
        view = null
    }
}