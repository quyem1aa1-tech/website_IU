/**
 * Account.js: Xử lý hiển thị thông tin và Đổi mật khẩu
 */

document.addEventListener("DOMContentLoaded", function () {
    const userId = localStorage.getItem('userId');
    const userEmail = localStorage.getItem('userEmail'); // Bạn nên lưu email lúc login để dùng cho reset password

    if (!userId) {
        window.location.href = "../LOGIN/login.html";
        return;
    }

    // --- PHẦN 1: HIỂN THỊ THÔNG TIN ---
    fetch(`/api/students/${userId}/profile`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' }
         })
    .then(response => response.json())
    .then(data => {
        displayProfile(data);
        // Lưu email vào localStorage nếu chưa có để dùng cho việc đổi pass
        if (!userEmail) localStorage.setItem('userEmail', data.email);
    })
    .catch(error => console.error("Lỗi lấy profile:", error));
    });

function displayProfile(profile) {
    document.getElementById('username').textContent = profile.username;
    document.getElementById('Fullname').textContent = profile.fullName;
    document.getElementById('Id').textContent = profile.studentId;
    document.getElementById('Email').textContent = profile.email;
}