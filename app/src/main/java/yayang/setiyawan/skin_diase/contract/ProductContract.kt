package yayang.setiyawan.skin_diase.contract

import yayang.setiyawan.skin_diase.model.Produk

class ProductContract {
    interface View{
        fun attachToRecycler(listProduk : List<Produk>)
        fun emptydata(status:Boolean)
        fun toast(message:String)
    }
    interface Presenter{
        fun getAllProduct()
        fun destroy()
    }
}