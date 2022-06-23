package yayang.setiyawan.skin_diase.model

import java.io.Serializable

class Produk : Serializable {
    var id = 0
    var name: String? = null
    var deskripsi: String? = null
    var url:String? = null
    var category_id = 0
    var image: String? = null
    var created_at: String? = null
    var updated_at: String? = null
}