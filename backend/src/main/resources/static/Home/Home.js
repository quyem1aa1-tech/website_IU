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

