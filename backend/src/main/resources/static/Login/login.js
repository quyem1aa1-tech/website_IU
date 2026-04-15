// Tệp: login.js

var form = document.getElementById("loginForm");

form.addEventListener("submit", function(event) {
    event.preventDefault();

    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;

    var userData = {
        username: username,
        password: password
    };

    fetch("/api/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(userData)
    })
    .then(function(response) {
        // Đọc dữ liệu dưới dạng text vì Backend trả về Enum String "SUCCESS"
        if (response.ok) {
            return response.text();
        }
        throw new Error("Mật khẩu hoặc tên đăng nhập không đúng");
    })
    .then(function(data) {
        // Xử lý chuỗi trả về từ Server (Lưu ý: data lúc này là "SUCCESS")
        console.log("Server trả về:", data);

        // SỬA TẠI ĐÂY: So sánh trực tiếp với chuỗi "SUCCESS"
        if (data === "SUCCESS" || data === "\"SUCCESS\"") {
            // Lưu thông tin để các trang sau sử dụng
            localStorage.setItem("username", username);

            // Tạm thời để ID là 1 vì Backend hiện tại chưa trả về ID người dùng cụ thể
            localStorage.setItem("userId", "1");

            document.getElementById("message").style.color = "green";
            document.getElementById("message").innerText = "Đăng nhập thành công! Đang chuyển hướng...";

            // Chuyển sang trang Home
            setTimeout(function() {
                window.location.href = "/Home/Home.html";
            }, 1000);
        } else {
            // Trường hợp Server trả về mã lỗi khác
            document.getElementById("message").style.color = "red";
            document.getElementById("message").innerText = "Đăng nhập thất bại: " + data;
        }
    })
    .catch(function(error) {
        console.error("Lỗi:", error);
        document.getElementById("message").style.color = "red";
        document.getElementById("message").innerText = "Sai tên đăng nhập hoặc mật khẩu!";
    });
});