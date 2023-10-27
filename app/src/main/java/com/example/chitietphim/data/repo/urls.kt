package com.example.chitietphim.data.repo

enum class urls(val stringURL: String) {
    urlPhimByMaPhim("http://192.168.1.209:80/service/dulieuctphim.php"),
    urlBinhLuanByMaPhim("http://192.168.1.209:80/service/dulieubinhluan.php"),
    urlThemBL("http://192.168.1.209:80/service/thembinhluan.php"),
    urlThemLS("http://192.168.1.209:80/service/themls.php"),
    urlThemDG("http://192.168.1.209:80/service/themdg.php"),
    urlLichSuByMaKH("http://192.168.1.209:80/service/datalichsu.php"),
    urlLogin("http://192.168.1.209:80/service/login.php"),
    urlPhim("http://192.168.1.209:80/service/datamovie.php"),
    urlPhimByTheLoai("http://192.168.1.209:80/service/datamovietheloai.php"),
    urlKhachHangByEmail("http://192.168.1.209:80/service/datakhachhangbyemail.php"),
    urlPhimByYear("http://192.168.1.209:80/service/datamovieyear.php"),
    urlKhachHangByMaKH("http://192.168.1.209:80/service/datakhachhang.php"),
    urlRegister("http://192.168.1.209:80/Demoservice/register.php"),
}