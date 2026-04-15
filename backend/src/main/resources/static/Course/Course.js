/**
 * Tệp: Course.js
 * Chức năng: Đồng bộ hóa đăng ký/hủy môn học giữa Giao diện và Database.
 */

// 1. LẤY THÔNG TIN CẤU HÌNH
const userId = localStorage.getItem("userId") || 1;

/**
 * Lấy danh sách ID (Primary Key) các môn User đang học để hiển thị dấu tích (Check).
 */
async function getEnrolledServerIds() {
    try {
        const res = await fetch(`http://localhost:8080/api/students/${userId}/courses`);
        const data = await res.json();
        return data.map(c => c.id.toString()); // Dùng ID để so sánh với checkbox value
    } catch (e) { return []; }
}

/**
 * Hiển thị danh sách môn học từ Database.
 */
async function loadAllCourses() {
    const list = document.getElementById("danh_sach_mon_hoc");
    try {
        const [allRes, serverIds] = await Promise.all([
            fetch('http://localhost:8080/api/courses'),
            getEnrolledServerIds()
        ]);
        const allCourses = await allRes.json();
        list.innerHTML = "";

        allCourses.forEach(c => {
            const isChecked = serverIds.includes(c.id.toString()) ? "checked" : "";
            list.innerHTML += `
                <div class="course-item">
                    <input type="checkbox" value="${c.id}" data-code="${c.courseId}" ${isChecked} class="reg-check">
                    <label><strong>${c.courseId}</strong> - ${c.courseName}</label>
                </div>
            `;
        });
        updateSummaryUI(allCourses, serverIds);
    } catch (e) { console.error("Lỗi tải môn:", e); }
}

/**
 * Xử lý khi nhấn Submit - Logic So Sánh 2 Chiều.
 */
document.getElementById("dangki").addEventListener("click", async () => {
    // A. Lấy tất cả Checkbox
    const allChecks = document.querySelectorAll(".reg-check");

    // B. Lấy danh sách ID đã học trên Server để so sánh
    const serverIds = await getEnrolledServerIds();

    try {
        for (let cb of allChecks) {
            const id = cb.value;
            const courseCode = cb.getAttribute("data-code"); // Mã môn (VD: MATH01)
            const isChecked = cb.checked;
            const wasEnrolled = serverIds.includes(id);

            // CƠ CHẾ:
            // 1. Nếu tích chọn MÀ trước đó chưa học -> ENROLL
            if (isChecked && !wasEnrolled) {
                await fetch(`http://localhost:8080/api/students/${userId}/enroll/${courseCode}`, { method: 'POST' });
            }
            // 2. Nếu bỏ tích MÀ trước đó có học -> DROP
            else if (!isChecked && wasEnrolled) {
                await fetch(`http://localhost:8080/api/students/${userId}/drop/${courseCode}`, { method: 'DELETE' });
            }
        }

        alert("Cập nhật dữ liệu thành công!");
        loadAllCourses(); // Làm mới lại toàn bộ giao diện

    } catch (error) {
        alert("Có lỗi xảy ra!");
        console.error(error);
    }
});

function updateSummaryUI(all, enrolled) {
    const box = document.getElementById("danhsachdadangki");
    box.innerHTML = all.filter(c => enrolled.includes(c.id.toString()))
                       .map(c => `<div class="enrolled-item">✓ ${c.courseName}</div>`).join("");
}

document.addEventListener("DOMContentLoaded", loadAllCourses);