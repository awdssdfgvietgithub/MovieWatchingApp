package com.example.chitietphim.data.repo

const val IP_STRING = "192.168.1.92"

enum class urls(val stringURL: String) {
    urlPhimByMaPhim("http://$IP_STRING:80/service/dulieuctphim.php"),
    urlBinhLuanByMaPhim("http://$IP_STRING:80/service/dulieubinhluan.php"),
    urlThemBL("http://$IP_STRING:80/service/thembinhluan.php"),
    urlThemLS("http://$IP_STRING:80/service/themls.php"),
    urlThemDG("http://$IP_STRING:80/service/themdg.php"),
    urlLichSuByMaKH("http://$IP_STRING:80/service/datalichsu.php"),
    urlLogin("http://$IP_STRING:80/service/login.php"),
    urlPhim("http://$IP_STRING:80/service/datamovie.php"),
    urlPhimByTheLoai("http://$IP_STRING:80/service/datamovietheloai.php"),
    urlKhachHangByEmail("http://$IP_STRING:80/service/datakhachhangbyemail.php"),
    urlPhimByYear("http://$IP_STRING:80/service/datamovieyear.php"),
    urlKhachHangByMaKH("http://$IP_STRING:80/service/datakhachhang.php"),
    urlRegister("http://$IP_STRING:80/service/register.php"),
}