package com.example.chitietphim.data.repo

enum class urls(val stringURL: String) {
    urlPhimByMaPhim("http://172.16.82.219:80/service/dulieuctphim.php"),
    urlBinhLuanByMaPhim("http://172.16.82.219:80/service/dulieubinhluan.php"),
    urlThemBL("http://172.16.82.219:80/service/thembinhluan.php"),
    urlThemLS("http://172.16.82.219:80/service/themls.php"),
    urlThemDG("http://172.16.82.219:80/service/themdg.php"),
    urlLichSuByMaKH("http://172.16.82.219:80/service/datalichsu.php"),
    urlLogin("http://172.16.82.219:80/service/login.php"),
    urlPhim("http://172.16.82.219:80/service/datamovie.php"),
    urlPhimByTheLoai("http://172.16.82.219:80/service/datamovietheloai.php"),
    urlKhachHangByEmail("http://172.16.82.219:80/service/datakhachhangbyemail.php"),
    urlPhimByYear("http://172.16.82.219:80/service/datamovieyear.php"),
    urlKhachHangByMaKH("http://172.16.82.219:80/service/datakhachhang.php"),
    urlRegister("http://172.16.82.219:80/Demoservice/register.php"),
}