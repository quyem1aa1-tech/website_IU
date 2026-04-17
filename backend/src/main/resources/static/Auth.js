function handleLogout() {
    // Xóa sạch dữ liệu trong localStorage
    localStorage.clear();

    // Chuyển hướng về trang Login (đường dẫn từ file Auth.js vào thư mục Login)
    window.location.replace("../Login/Login.html");
}

// Kiểm tra quyền truy cập ngay lập tức
// ở các trang sẽ phải kiểm tra quyền truy cập nó sẽ ko duy trì storage cho các trang khi logout
(function checkAuth() {
    const userId = localStorage.getItem('userId');
    // Nếu không thấy userId, coi như chưa đăng nhập hoặc đã logout
    if (!userId) {
        window.location.replace("../Login/Login.html");
    }
})();