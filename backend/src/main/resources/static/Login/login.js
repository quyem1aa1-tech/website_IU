    // lấy form từ HTML
    var form = document.getElementById("loginForm");

    // khi người dùng bấm submit
    form.addEventListener("submit", function(event) {

    // chặn reload trang
    event.preventDefault();

    // lấy dữ liệu từ input
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;

    // tạo object chứa dữ liệu
    // json-object
    var userData = {
        username: username,
        password: password
    };
    // gửi request đến server
    fetch("/api/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(userData)
    })
    .then(function(response) {
        // Kiểm tra nếu response trả về là JSON
        return response.json();
    })
    .then(function(data) {
        // data bây giờ là một Object, bạn lấy message ra để hiển thị
        console.log("Server tra ve object:", data);

        // Nếu login thành công (HTTP 200), Spring trả về AuthResponse
        if (data.message) {
            document.getElementById("message").innerText = data.message;

            if (data.message === "Welcome to International University!") {
            localStorage.setItem("username", data.username);
                console.log("Next page...");
                window.location.href = "/Home/Home.html";
            }
        }
    })
    .catch(function(error) {
        // Xử lý lỗi khi Server trả về 401, 404, 500
        // Lưu ý: fetch không nhảy vào catch khi nhận mã 401/404, bạn cần check response.ok ở trên
        console.error("Loi ket noi hoac loi server:", error);
        document.getElementById("message").innerText = "Sai tên đăng nhập hoặc mật khẩu!";
    });
});