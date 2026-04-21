
document.addEventListener("DOMContentLoaded", function () {
    const forgotForm = document.getElementById("forgotPasswordForm");
    const emailInput = document.getElementById("email");
    const messageBox = document.getElementById("responseMessage");

    // Thêm từ khóa 'async' trước function để cho phép dùng 'await' bên trong
    forgotForm.addEventListener("submit", async function (event) {
    // ngăn chặn reload trang
        event.preventDefault();

        const emailValue = emailInput.value.trim();

        // Hiển thị trạng thái chờ
        messageBox.style.display = "block";
        messageBox.innerHTML = "Đang xử lý...";
        messageBox.className = "message-box processing";

        // Sử dụng cấu trúc try...catch để bắt lỗi (thay cho .catch)
        try {
            // Gửi yêu cầu và đợi phản hồi trả về (await)
            const response = await fetch("/api/auth/forgot-password", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({ email: emailValue })
            });

            // Tạo 1 biến Lấy nội dung chữ từ phản hồi
            const resultText = await response.text();
            //Trường hợp nếu serviec thấy email tồn tại và trả về ok

            if (response.ok) {
                // Trường hợp thành công (Status 200)
                // lưu email vào hộp chứa trong suốt qua s trình quên mật khẩu và có thể sang trang change password
                localStorage.setItem("email", email);
                //hiển thị màu chữ của khung trả lời
                messageBox.style.color = "#155724";
                // hiển thị màu background của khung trả lời
                messageBox.style.backgroundColor = "#d4edda";
               // innerHTML CÓ TÁC DỤNG LẤY GIÁ TRỊ ĐƯỢC TRẢ VỀ BÊN PHẢI VÀ ĐẶT VÀO Ô BÊN TRÁI
               // Khác với innerText nó sẽ thực thi thêm các thẻ strong và br của html
                messageBox.innerHTML = `<strong>Thành công!</strong> <br> ${resultText}`;
                emailInput.value = "";
            } else {
                // Trường hợp lỗi từ Server (404, 500...)
                throw new Error(resultText);
            }

        } catch (error) {
            // Xử lý lỗi kết nối hoặc lỗi được throw ở trên
            console.error("Lỗi:", error);
            messageBox.style.color = "#721c24";
            messageBox.style.backgroundColor = "#f8d7da";
            // lấy dữ liệu
            messageBox.innerText = "Lỗi: " + error.message;
        }
    });
});