package ezy.id.artipoint

import java.io.Serializable

data class Articles(var judul: String, var deskripsi: String, var pathGambar: String): Serializable{
    constructor(): this("","", ""){}
}
