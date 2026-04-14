// 1. Lấy thông tin từ localStorage
const username = localStorage.getItem("username");
const userId = localStorage.getItem("userId");

// 2. Kiểm tra bảo mật: Nếu không có username (chưa login), đá về trang login
if (!username) {
    window.location.href = "../login.html"; // Điều chỉnh đường dẫn cho đúng cấu trúc thư mục của bạn
} else {
    // Hiển thị lời chào
    const welcomeElement = document.getElementById("welcomeText");
    if (welcomeElement) {
        welcomeElement.innerText = "Xin chào, " + username;
    }
}


// 3. Xử lý logic nút Log Out (nếu bạn muốn làm sạch dữ liệu khi thoát)
const logoutBtn = document.querySelector('a[href="index.html"]');
if (logoutBtn) {
    logoutBtn.addEventListener("click", function() {
        localStorage.clear(); // Xóa sạch ID và Username khi thoát
    });
}