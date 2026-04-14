/**
 * Tệp: course.js
 * Chức năng: Quản lý đăng ký môn học (Enroll) và hủy môn (Drop) đồng bộ với Server.
 */

// --- 1. KHỞI TẠO CẤU HÌNH BAN ĐẦU ---
// Lấy ID người dùng từ bộ nhớ trình duyệt để định danh sinh viên
const userId = localStorage.getItem("userId") || 1;
const username = localStorage.getItem("username");

// --- 2. CÁC HÀM HỖ TRỢ LOGIC ---


/**
 * Hàm lấy danh sách ID các môn học mà sinh viên hiện đang có trên Database.
 * Trả về một mảng chứa ID dưới dạng chuỗi (String).
 */
async function getCurrentServerCourseIds() {
    try {
        const response = await fetch(`http://localhost:8080/api/students/${userId}/courses`);
        const enrolledCourses = await response.json();
        // Chuyển đổi ID về String để so sánh chính xác với value từ checkbox
        return enrolledCourses.map(c => c.id.toString());
    } catch (error) {
        console.error("Lỗi lấy danh sách môn học hiện tại từ server:", error);
        return [];
    }
}

// --- 3. CÁC HÀM XỬ LÝ DỮ LIỆU (API) ---

/**
 * Tải toàn bộ môn học và hiển thị kèm trạng thái đã tích chọn hay chưa.
 */
async function loadAllCourses() {
    try {
        // Lấy tất cả môn học có trong hệ thống
        const resAll = await fetch('http://localhost:8080/api/courses');
        const allCourses = await resAll.json();

        // Lấy danh sách ID các môn học sinh viên đã đăng ký
        const serverIds = await getCurrentServerCourseIds();

        // Hiển thị danh sách lên giao diện
        displayCourses(allCourses, serverIds);
    } catch (error) {
        console.error("Lỗi khi tải dữ liệu:", error);
    }
}

/**
 * Hiển thị danh sách môn học lên màn hình.
 * @param {Array} allCourses - Tất cả môn học từ DB.
 * @param {Array} serverIds - Danh sách ID các môn học sinh viên đã đăng ký.
 */
function displayCourses(allCourses, serverIds = []) {
    const list = document.getElementById("danh_sach_mon_hoc");
    list.innerHTML = "";

    allCourses.forEach(c => {
        // Nếu ID của môn học nằm trong danh sách đã đăng ký trên server, đánh dấu là 'checked'
        const isChecked = serverIds.includes(c.id.toString()) ? "checked" : "";

        list.innerHTML += `
            <div class="course-item">
                <input type="checkbox" value="${c.id}" class="reg-check" ${isChecked}>
                <label>${c.courseName}</label>
            </div>
        `;
    });
}

/**
 * Tải và hiển thị danh sách các môn học (dạng chữ) đã đăng ký thành công.
 */
async function loadEnrolledCourses() {
    try {
        const response = await fetch(`http://localhost:8080/api/students/${userId}/courses`);
        const data = await response.json();
        const box = document.getElementById("danhsachdadangki");
        box.innerHTML = data.map(c => `<div class="enrolled-item">✓ ${c.courseName}</div>`).join("");
    } catch (error) {
        console.error("Lỗi khi tải danh sách đã đăng ký:", error);
    }
}

// --- 4. XỬ LÝ SỰ KIỆN (EVENT LISTENERS) ---

/**
 * Xử lý Confirm: Đồng bộ hóa trạng thái tích chọn với Database.
 */
document.getElementById("dangki").addEventListener("click", async () => {
    // A. Lấy danh sách ID đang được tích chọn trên giao diện hiện tại
    const selectedCheckboxes = document.querySelectorAll(".reg-check:checked");
    const currentSelectedIds = Array.from(selectedCheckboxes).map(cb => cb.value);

    // B. Lấy danh sách ID thực tế đang lưu trên Database
    const serverIds = await getCurrentServerCourseIds();

    // C. Tìm các môn CẦN ĐĂNG KÝ (Có tích chọn nhưng Server chưa có)
    const toEnroll = currentSelectedIds.filter(id => !serverIds.includes(id));

    // D. Tìm các môn CẦN HỦY (Server đang có nhưng giao diện đã bỏ tích chọn)
    const toDrop = serverIds.filter(id => !currentSelectedIds.includes(id));

    try {
        // Thực hiện đăng ký các môn mới
        for (let id of toEnroll) {
            await fetch(`http://localhost:8080/api/students/${userId}/enroll/${id}`, { method: 'POST' });
        }

        // Thực hiện hủy các môn bị bỏ tích
        for (let id of toDrop) {
            await fetch(`http://localhost:8080/api/students/${userId}/drop/${id}`, { method: 'DELETE' });
        }

        alert("Cập nhật danh sách khóa học thành công!");
        loadEnrolledCourses(); // Làm mới danh sách hiển thị
    } catch (error) {
        alert("Lỗi khi cập nhật dữ liệu!");
        console.error(error);
    }
});

/**
 * Tìm kiếm môn học.
 */
document.getElementById("timkiemmonhoc").addEventListener("keyup", async (e) => {
    const val = e.target.value.toLowerCase();
    const response = await fetch('http://localhost:8080/api/courses');
    const allCourses = await response.json();

    // Khi tìm kiếm, vẫn cần lấy serverIds để duy trì trạng thái tích chọn
    const serverIds = await getCurrentServerCourseIds();

    const filtered = allCourses.filter(c => c.courseName.toLowerCase().includes(val));
    displayCourses(filtered, serverIds);
});

// --- 5. TỰ ĐỘNG CHẠY ---
loadAllCourses();
loadEnrolledCourses();