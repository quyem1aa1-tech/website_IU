# NƠI NÀY DÙNG ĐỂ GHI LẠI LỊCH SỬ CẬP NHẬT

## Ngày 4/7/2026: 9:00 AM nằm trong branch feature/student-course-management - Trương Thế Vinh 🕙

### III. Chi tiết về từng file

#### [TẠO FILE MỚI]

**1. website_IU\backend\src\main\java\com\app\entity\Course.java 🌟🌟**
 - Dùng để tạo ra một cái bảng course
 - Mối quan hệ (Many-to-Many): mối quan hệ từ một bảng này có thể liên kết nhiều dữ liệu khác nhau từ bảng kia và ngược lại
** Ví dụ về mối quan hệ Many-to-Many: một sinh viên thì có thể đăng ký nhiều môn, và một môn thì có thế chứa nhiều sinh viên khác nhau **

**2. website_IU\backend\src\main\java\com\app\controller\StudentController.java 🌟🌟**
 - Cửa ngõ xử lý API cho Sinh viên: Đăng ký môn học và xem danh sách môn học cá nhân.
 - Quản lý phản hồi HTTP (200 OK / 400 Bad Request) dựa trên kết quả từ Service.

**3. website_IU\backend\src\main\java\com\app\controller\CourseController.java 🌟🌟**
 - Vai trò: Cung cấp các Endpoint công khai liên quan đến thực thể Course
 - Chức năng: Trả về danh sách toàn bộ các khóa học đang có trong hệ thống IU thông qua phương thức GET

 - Tính kỹ thuật: + Sử dụng @RestController để trả dữ liệu trực tiếp dưới dạng JSON cho Frontend:
    - Kết nối với StudentService để truy xuất dữ liệu từ CourseRepository.
    - Phân tách rõ ràng luồng "Xem tất cả môn học" ra khỏi luồng "Quản lý cá nhân" của Sinh viên để dễ dàng bảo trì.

**4. website_IU\backend\src\main\java\com\app\repository\CourseRepository.java 🌟🌟**
 - Database (lưu trong phần ổ cứng)
 - Interface để thao tác với dữ liệu save(), delete(), ... như User bên kia
 - Có thể tìm kiếm, lọc, xóa, đếm... cực nhanh.
 - Phải hỏi Database mới biết bên trong có gì

**5. website_IU\backend\src\main\java\com\app\service\StudentService.java 🌟🌟**
 + Bước 1: Tìm kiếm thông tin Sinh viên và Khóa học từ Database dựa trên ID. Nếu không thấy sẽ thông báo lỗi ngay lập tức.
 + Bước 2 (Kiểm tra nghiệp vụ): Sử dụng hàm .contains() để kiểm tra xem sinh viên đã đăng ký môn này chưa, tránh việc trùng lặp dữ liệu.
 + Bước 3 (Thiết lập quan hệ): Thực hiện "bắt tay" 2 chiều. Khi thêm sinh viên vào môn học, hệ thống cũng tự động cập nhật môn học đó vào hồ sơ của sinh viên.
 + Bước 4 (Lưu trữ): Chỉ cần gọi lệnh .save() ở phía Khóa học (bên giữ chìa khóa), JPA sẽ tự động cập nhật vào bảng trung gian course_student.
 + Bước 5 (Phản hồi): Trả về thông báo thành công kèm theo tên môn học cụ thể để người dùng dễ nhận biết.

 - @Transactional: Đảm bảo tính nhất quán. Nếu quá trình lưu gặp lỗi (ví dụ: mất điện giữa chừng), toàn bộ thao tác sẽ được hủy bỏ (Rollback), không để lại dữ liệu rác.
 - Many-to-Many Mapping: Xử lý mối quan hệ phức tạp nhất trong cơ sở dữ liệu một cách mượt mà thông qua bảng trung gian tự động.
 - Bidirectional Sync: Logic đồng bộ hóa 2 chiều giúp dữ liệu trong bộ nhớ RAM luôn khớp hoàn toàn với dữ liệu dưới Database.

---

#### [UPDATE FILE]

**6. website_IU\backend\src\main\java\com\app\controller\AuthController.java 🌟🌟**
 - Cập nhật: cải thiện logic API đăng ký người dùng (/signup).
 - Triển khai cấu trúc try-catch để bắt và xử lý các ngoại lệ (Exception), đảm bảo Server không bị "sập" khi có dữ liệu không hợp lệ.
 - Kết nối chặt chẽ với AuthService thông qua cơ chế Dependency Injection (không dùng từ khóa new).
 - Phản hồi thông tin lỗi chi tiết về phía Client để hỗ trợ quá trình sửa lỗi nhanh chóng.

**7. website_IU\backend\src\main\java\com\app\dto\AuthResponse.java 🌟🌟**
 - Thêm biến fullname
 - Thêm khai báo fullname trong constructor
 - Thêm một hàm getFullName(): String
 - Thêm một hàm setFullName(String fullame): void

**8. website_IU\backend\src\main\java\com\app\dto\SignupRequest.java 🌟🌟**
 - Thêm một số @NotBlank cho biến: kiểm tra null, kiểm tra chuỗi "", và kiểm tra khoảng trắng " " chỉ dàng String
 - Thêm một số @NotNull: có kiểm tra null, dùng với mọi Object

**9. website_IU\backend\src\main\java\com\app\entity\User.java 🌟🌟**
 - Có cập nhật thêm mối quan hệ Many-To-Many lấy từ liststudents trong Course ra
 - Có cập nhật thêm getCouse(): Set ~Course~
 - Có cập nhật thêm setCouse(Set ~Course~ courses): void

**10. website_IU\backend\src\main\java\com\app\service\AuthService.java 🌟🌟**
 - Trong hàm registerUser(SignupRequest request) Chỉnh sửa thêm tại ghi chú 1.5 FIX, xem fullname có null không và Role có null và để trống không

**11. website_IU\backend\src\main\java\com\app\DemoApplication.java 🌟🌟**
 - Khởi tạo một Course trong hàm main


## -- Ngày 4/5/2026 - 6:24 PM nằm trong branch: feature/user-auth-logic -- Trấn Vinh🕙

## TẠO FILE MỚI:
- ### **website_IU\backend\src\main\resources\static\index.html: File HOME mẫu để test tính năng login 🌟🌟**
- ### **website_IU\backend\src\main\resources\static\hello.html: File mẫu để test cách redirect trang 🌟🌟**
- ### **website_IU\backend\src\main\java\com\app\entity\LoginData.java: Lưu thông tin đăng nhập từ JS 🌟🌟**

## CHỈNH SỬA FILE:
- ### **website_IU\backend\src\main\resources\application.properties 🌟🌟**
- ### **website_IU\backend\src\main\java\com\app\controller\AuthService 🌟🌟**
- ### **website_IU\database chuyển vào: website_IU\backend\ 🌟🌟**
- ### **CHANGELOG.md chuyển vào: website_IU\ 🌟🌟**

- ### **website_IU\backend\src\main\resources\application.properties 🌟🌟**
**Thông tin về file** ❗❗

1. Xóa dòng: server.servlet.context-path=/api
* Lý do: Gây khó khăn truy cập web / test file .html

- ### **website_IU\backend\src\main\java\com\app\controller\AuthService 🌟🌟**
**Thông tin về file** ❗❗

1. Trong hàm login():
* Đổi parameters (@RequestParam username, @RequestParam password) thành (@RequestBody LoginData data)

In application.properties: deleted line: server.servlet.context-path=/api // Reason: no need for url
## -- Ngày 4/5/2026 - 10:52 AM nằm trong branch: feature/user-auth-logic🕙

TẠO RA THÊM CÁI MỚI:
- ### **website_IU\backend\src\main\java\com\app\entity\LoginStatus.java: nơi này dùng để check xem trạng thái người dùng đăng nhập (bị khoá tài khoản, sai mật khẩu...) 🌟🌟**


## -- Ngày 4/5/2026 - 10:52 AM nằm trong branch: feature/user-auth-logic🕙

TẠO RA THÊM CÁI MỚI:
- ### **website_IU\backend\src\main\java\com\app\entity\LoginStatus.java: nơi này dùng để check xem trạng thái người dùng đăng nhập (bị khoá tài khoản, sai mật khẩu...) 🌟🌟**
- ### **website_IU\backend\src\main\java\com\app\entity\User.java: nơi này là cái sườn để tạo ra một đối tượng người dùng 🌟🌟**
- ### **website_IU\backend\src\main\java\com\app\entity\UserRole.java: nơi này chia role giáo viên, học sinh, và admin 🌟🌟**
- ### **website_IU\backend\src\main\java\com\app\repository\UserRepository.java: một hàm để truy xuất bên trong Optional<User> (chưa biết có hay chưa) bằng findByUsername(...) 🌟🌟**
  
- ### **website_IU\backend\src\main\java\com\app\service\AuthService.java: File AuthService.java 🌟🌟:**
[CHANGELOG.md](../../../../../../../website_IU/backend/src/main/java/com/app/repository/CHANGELOG.md)
**Thông tin về file** ❗❗

1. Nhiệm vụ: Đóng vai trò là Tầng nghiệp vụ (Service Layer).
2. Chức năng chính: > - Kết nối giữa Controller và Repository.
3. Thực hiện quy trình xác thực (Authentication): Kiểm tra tài khoản tồn tại -> So sánh mật khẩu -> Trả về thông báo định danh.

Lưu ý kỹ thuật: Hiện đang so sánh mật khẩu dạng văn bản thuần (Plain-text). Cần nâng cấp lên BCrypt ở nhánh dev2 để bảo mật hơn. ⚠️⚠️

- ### **website_IU\backend\src\main\java\com\app\service\UserService.java: UserService.java 🌟🌟:**

**Thông tin về file** ❗❗
1. Tích hợp Enum LoginStatus: Thay vì trả về đúng/sai đơn giản, giờ đây hệ thống đã có thể "nói" chính xác tại sao đăng nhập thất bại (Sai tên hay sai pass).

2. Sử dụng Optional<User>: Đảm bảo an toàn tuyệt đối khi tìm kiếm, không lo bị crash app nếu gõ nhầm tên user.

3. Quy trình @Service: Đã được đánh dấu để Spring Boot tự động quản lý vòng đời và "tiêm" (@Autowired) vào Controller.

**4. 👀👀 Luồng Login 👀👀:**

B1: Tìm user trong DB bằng userRepository.

B2: Nếu rỗng -> Trả về USER_NOT_FOUND.

B3: Nếu có -> So sánh mật khẩu bằng lệnh .equals().

B4: Nếu sai pass -> Trả về WRONG_PASSWORD.

B5: Nếu khớp -> Trả về SUCCESS.

- ### **website_IU\backend\src\main\java\com\app\dto\AuthControllerse.java: AuthResponse.java 🌟🌟:**

 **Thông tin về file ❗❗**
  
Loại file: DTO (Data Transfer Object).
Nhiệm vụ: Chuẩn hóa dữ liệu trả về cho Client (Postman/Browser).

1. Bảo mật: Không làm lộ mật khẩu (password) ra ngoài API.

2. Chỉ trả về những thông tin cần thiết để hiển thị giao diện.

3. Tạo ra một cấu trúc JSON đồng nhất, giúp phía Frontend dễ dàng xử lý logic.

- ### **website_IU\backend\src\main\java\com\app\controller\AuthController.java 🌟🌟:**

**👀👀 Luồng Logic 👀👀:**
1. Nhận đầu vào: Lấy username và password từ @RequestParam (Form-encode).

2. Gọi: Nhờ userService.processLogin đưa ra phán quyết.

**Phân luồng phản hồi (The Switch-Case):**

SUCCESS: Trả về 200 OK. Lúc này, Controller gọi tiếp getUserByUsername để lấy thông tin chi tiết và đóng gói vào hộp AuthResponse (JSON).

USER_NOT_FOUND: Trả về 404 Not Found. Báo cho người dùng là "Số nhà này không tồn tại".

WRONG_PASSWORD: Trả về 401 Unauthorized. Báo là "Vào đúng nhà rồi nhưng sai chìa khóa".

DEFAULT: Trả về 500 Internal Server Error. Đây là cái "lưới bảo hiểm" nếu có lỗi gì đó mà mình chưa lường trước được.

 **Thông tin về file** ❗❗:
 
File: AuthController.java (thuộc package com.app.controller)
Nhiệm vụ: Tiếp nhận HTTP Request và điều phối luồng dữ liệu.
Các kỹ thuật sử dụng:

ResponseEntity<?>: Cho phép trả về linh hoạt nhiều loại dữ liệu và Mã lỗi HTTP khác nhau.

switch-case: Giúp code tường minh, dễ đọc hơn hẳn so với việc dùng if-else lồng nhau.

HTTP Status Codes: Sử dụng chuẩn công nghiệp (200, 401, 404) để các ứng dụng khác (Frontend/Mobile) dễ dàng bắt lỗi.

