## NƠI NÀY DÙNG ĐỂ GHI LẠI LỊCH SỬ CẬP NHẬT

-- Ngày 4/5/2026 - 10:52 AM 🕙

TẠO RA THÊM CÁI MỚI:
- website_IU\backend\src\main\java\com\app\entity\LoginStatus.java: nơi này dùng để check xem trạng thái người dùng đăng nhập (bị khoá tài khoản, sai mật khẩu...) 🌟🌟
- website_IU\backend\src\main\java\com\app\entity\User.java: nơi này là cái sườn để tạo ra một đối tượng người dùng 🌟🌟
- website_IU\backend\src\main\java\com\app\entity\UserRole.java: nơi này chia role giáo viên, học sinh, và admin 🌟🌟
- website_IU\backend\src\main\java\com\app\repository\UserRepository.java: một hàm để truy xuất bên trong Optional<User> (chưa biết có hay chưa) bằng findByUsername(...) 🌟🌟
  
- website_IU\backend\src\main\java\com\app\service\AuthService.java: File AuthService.java 🌟:
 ❗❗ **Thông tin về file** ❗❗
1. Nhiệm vụ: Đóng vai trò là Tầng nghiệp vụ (Service Layer).
2. Chức năng chính: > - Kết nối giữa Controller và Repository.
3. Thực hiện quy trình xác thực (Authentication): Kiểm tra tài khoản tồn tại -> So sánh mật khẩu -> Trả về thông báo định danh.

Lưu ý kỹ thuật: Hiện đang so sánh mật khẩu dạng văn bản thuần (Plain-text). Cần nâng cấp lên BCrypt ở nhánh dev2 để bảo mật hơn. ⚠️⚠️

- website_IU\backend\src\main\java\com\app\service\UserService.java: UserService.java 🌟🌟:
 ❗❗ **Thông tin về file** ❗❗:
1. Tích hợp Enum LoginStatus: Thay vì trả về đúng/sai đơn giản, giờ đây hệ thống đã có thể "nói" chính xác tại sao đăng nhập thất bại (Sai tên hay sai pass).

2. Sử dụng Optional<User>: Đảm bảo an toàn tuyệt đối khi tìm kiếm, không lo bị crash app nếu gõ nhầm tên user.

3. Quy trình @Service: Đã được đánh dấu để Spring Boot tự động quản lý vòng đời và "tiêm" (@Autowired) vào Controller.

**4. 👀👀 Luồng Login 👀👀:**

B1: Tìm user trong DB bằng userRepository.

B2: Nếu rỗng -> Trả về USER_NOT_FOUND.

B3: Nếu có -> So sánh mật khẩu bằng lệnh .equals().

B4: Nếu sai pass -> Trả về WRONG_PASSWORD.

B5: Nếu khớp -> Trả về SUCCESS.

- website_IU\backend\src\main\java\com\app\dto\AuthControllerse.java: AuthResponse.java 🌟🌟:
 **❗❗ Thông tin về file ❗❗**
Loại file: DTO (Data Transfer Object).
Nhiệm vụ: Chuẩn hóa dữ liệu trả về cho Client (Postman/Browser).

1. Bảo mật: Không làm lộ mật khẩu (password) ra ngoài API.

2. Chỉ trả về những thông tin cần thiết để hiển thị giao diện.

3. Tạo ra một cấu trúc JSON đồng nhất, giúp phía Frontend dễ dàng xử lý logic.

- website_IU\backend\src\main\java\com\app\controller\AuthController.java 🌟🌟:

**👀👀 Luồng Logic 👀👀:**
1. Nhận đầu vào: Lấy username và password từ @RequestParam (Form-encode).

2. Gọi: Nhờ userService.processLogin đưa ra phán quyết.

**Phân luồng phản hồi (The Switch-Case):**

SUCCESS: Trả về 200 OK. Lúc này, Controller gọi tiếp getUserByUsername để lấy thông tin chi tiết và đóng gói vào hộp AuthResponse (JSON).

USER_NOT_FOUND: Trả về 404 Not Found. Báo cho người dùng là "Số nhà này không tồn tại".

WRONG_PASSWORD: Trả về 401 Unauthorized. Báo là "Vào đúng nhà rồi nhưng sai chìa khóa".

DEFAULT: Trả về 500 Internal Server Error. Đây là cái "lưới bảo hiểm" nếu có lỗi gì đó mà mình chưa lường trước được.

 ❗❗ **Thông tin về file** ❗❗:
File: AuthController.java (thuộc package com.app.controller)
Nhiệm vụ: Tiếp nhận HTTP Request và điều phối luồng dữ liệu.
Các kỹ thuật sử dụng:

ResponseEntity<?>: Cho phép trả về linh hoạt nhiều loại dữ liệu và Mã lỗi HTTP khác nhau.

switch-case: Giúp code tường minh, dễ đọc hơn hẳn so với việc dùng if-else lồng nhau.

HTTP Status Codes: Sử dụng chuẩn công nghiệp (200, 401, 404) để các ứng dụng khác (Frontend/Mobile) dễ dàng bắt lỗi.


