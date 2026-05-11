KIỂM TRA LIÊN KẾT HỆ THỐNG WEB:
KHI CHẠY WEB BẰNG LOCAL HOST HÃY CHÚ Ý NẾU WEB LỖI HÃY NHẤN CHUỘT PHẢI VÀO TRANG WEB CHỌN INSPECT->CHỌN NETWORKING ĐỂ CHECK XEM FILE NÀO BỊ LỖI ĐỒNG THỜI MỞ CỬA SỐ CONSOL ĐỂ XEM LỖI CHI TIẾT LÀ GÌ VÀ SỮA Ở FILE ĐÓ

SCHEDULE FOR FRONTEND
Member for frontend:
Vũ Ngọc Khánh Vy-ITITIU25048
Nguyễn Chí Tiến-ITITIU25036
Chung Chí Tâm-ITITIU25032
Võ Lê Hà Vy-ITITIU25049
I.XỬ LÍ KHUNG HTML(Khánh Vy)
1. TẠO KHUNG VÀ CÁC THÀNH PHẦN CHO TRANG LOGIN
2. TẠO KHUNG VÀ CÁC THÀNH PHẦN CHO HOME
3. TẠO KHUNG VÀ CÁC THÀNH PHẦN CHO TRANG COURSE
4. TẠO KHUNG VÀ CÁC THÀNH PHẦN CHO TRANG ACCOUNT
5. TẠO KHUNG VÀ CÁC THÀNH PHẦN CHO TRANG FORGOTPASSWORD
   TẠO KHUNG VÀ CÁC THÀNH PHẦN CHO TRANG CHANGEPASSWORD

II.XỬ LÍ MÀU CSS(Chí Tiến)
1. LOGO HCMIU TRANG LOGIN CÓ HÌNH TRƯỜNG LÀM NỀN CÀNG TỐT
2. LẤY HÌNH TRƯỜNG LÀM NỀN CHO TRANG HOME
3. THIẾT KẾ KHUNG TRANG COURSE
4. THIẾT KẾ KHUNG TRANG ACCOUNT
5. THIẾT KẾ VÀ TRANG TRÍ CHO CÁC THÀNH PHẦN TRANG FORGOTPASSWORD
6. THIẾT KẾ VÀ TRANG TRÍ CHO CÁC THÀNH PHẦN TRANG CAHNGEPASSWORD

III. Liên kết với Backend
1. Chúc năng login thêm trang đổi mật khẩu và quên mật khẩu-Tâm-9/4/2026
2. Khi đăng nhập sẽ chuyển thẳng đến trang home-Tâm-9/4/2026
3. duy trì trạng thái đăng nhập nhấn vào course sẽ hiển thị danh sách môn-Tâm-9/4/2026
   3.1 đăng kí môn-Tâm-10/4/2026
   3.2 các môn đã đăng kí-Tâm-10/4/2026
   3.3 thay đổi các môn đã đăng kí-Tâm-11/4/2026
   3.4 tìm kiếm môn-Tâm-11/4/2026
   4.Account hiển thị thông tin học sinh-Tâm-13/4/2026
   5.Nút logout thoát xóa sạch toàn bộ thông tin đăng nhập user và id được lưu trên web-Hà Vy-14/4/2026
4. Change password-Hà Vy-14/4/2026
5. Forgot password-Hà Vy-14/4/2026
   8.Xủ lí ngăn chặn user quay lại các trang cũ khi đã logout-Hà Vy-16/4/2026
   Deadline:
   Thứ 4 15/4 kết thúc HTML CSS CHO TOÀN BỘ WEB:
   -YÊU CẦU:
   +COMMENT CODE CHI TIẾT RÕ RÀNG NHỮNG NƠI HIỂN THỊ THÔNG TIN NỘI DUNG
   +Ghi rõ nhiệm vụ ai làm phần nào
   Thứ 7 18/4 KẾT THÚC LIÊN KẾT GIAO THỨC WEB GIỮA BROWSER VÀ
   Tâm:từ login->account
   Hà Vy: từ Changepassword->logout( trang account được truy cập từ trang home hay trang course đều phải xuất hiện tức là phải duy trì trạng thái đăng nhập cho đến khi đăng xuất mới xóa toàn bộ dữ liệu
   Forgot password and change password( tính năng này chỉ thấy khi đăng nhập thành công và nó nằm trong home)
   Frontend
   ↓
   JSON Request
   ↓
   DTO (LoginRequest)
   ↓
   Controller
   ↓
   Service
   ↓
   Repository
   ↓
   Database
   ↓
   Entity (User)
   ↓
   Service xử lý logic
   ↓
   DTO (AuthResponse)
   ↓
   Controller
   ↓
   JSON Response
   ↓
   Frontend
------------------------------------------------------------------------------------------------------------------------------------------------------------
GIT HUB CHỈ ĐƯỢC TẠO THÊM NHÁNH KHÔNG ĐƯỢC CODE TRỰC TIẾP LÊN NHÁNH MAIN
CÁC LỆNH CẦN NHỚ: git clone, git pull, git checkout...
git clone...: down repository
git pull: update repository
git checkout (nhánh): chuyển đổi nhánh
Push and commit lên trên git hub
Toàn bộ sẽ được đẩy lên nhánh main vào ngày họp lần cuối của buổi fix bug