document.addEventListener('DOMContentLoaded', function() {
    const changePasswordForm = document.getElementById('changePasswordForm');
    const responseMessage = document.getElementById('responseMessage');

    changePasswordForm.addEventListener('submit', async function(event) {
        // Ngăn chặn hành vi reload trang mặc định của form
        event.preventDefault();

        // 1. Lấy dữ liệu từ các thẻ input
        const email = document.getElementById('email').value.trim();
        const oldPassword = document.getElementById('oldPassword').value;
        const newPassword = document.getElementById('newPassword').value;
        const confirmPassword = document.getElementById('confirmPassword').value;

        // Reset lại trạng thái của khung thông báo
        responseMessage.className = 'message-box';
        responseMessage.style.display = 'block';
        responseMessage.textContent = 'Đang xử lý...';

        // Validate cơ bản ở Front-end: Kiểm tra mật khẩu xác nhận
        if (newPassword !== confirmPassword) {
            responseMessage.classList.add('msg-error');
            responseMessage.textContent = 'Mật khẩu xác nhận không khớp!';
            return;
        }

        // 2. Tạo Payload JSON để gửi xuống Backend (Khớp với Map<String, String> trong Controller)
        const payload = {
            email: email,
            oldPassword: oldPassword,
            newPassword: newPassword,
            confirmPassword: confirmPassword
        };

        try {
            // 3. Gọi API bằng Fetch
            // Lưu ý: Đảm bảo Spring Boot đang chạy ở port 8080
            const response = await fetch('http://localhost:8080/api/auth/reset-password', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(payload)
            });

            // Lấy nội dung text backend trả về (ví dụ: "[200] SUCCESS: Password Reset")
            const resultText = await response.text();

            // 4. Xử lý hiển thị dựa trên HTTP Status Code từ AuthController
            if (response.ok) {
                // Status 200 (Thành công)
                responseMessage.classList.add('msg-success');
                // Lọc bỏ chuỗi "[200] " từ backend để hiện thông báo đẹp hơn
                responseMessage.textContent = 'Thành công: ' + resultText.replace('[200] ', '');

                // Xóa trắng form sau khi đổi thành công
                changePasswordForm.reset();
            } else {
                // Xử lý các mã lỗi nghiệp vụ
                responseMessage.classList.add('msg-error');

                if (response.status === 401) {
                    responseMessage.textContent = 'Mật khẩu hiện tại không đúng!';
                } else if (response.status === 403) {
                    responseMessage.textContent = 'Mật khẩu mới không đúng định dạng quy định!';
                } else if (response.status === 405) {
                    responseMessage.textContent = 'Xác nhận mật khẩu thất bại!';
                } else if (response.status === 404) {
                    responseMessage.textContent = 'Không tìm thấy tài khoản với Email này!';
                } else {
                    // Lỗi 500 hoặc lỗi khác
                    responseMessage.textContent = 'Lỗi hệ thống: ' + resultText;
                }
            }

        } catch (error) {
            // Lỗi khi không thể kết nối tới server (server tắt, sai URL, CORS...)
            console.error('Fetch error:', error);
            responseMessage.classList.add('msg-error');
            responseMessage.textContent = 'Không thể kết nối đến máy chủ. Vui lòng kiểm tra lại Backend!';
        }
    });
});