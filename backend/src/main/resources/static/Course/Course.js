// 1. KHAI BÁO "BỘ NHỚ" CỦA TRANG WEB
const SINH_VIEN_ID = localStorage.getItem("userId");
const CUA_HANG_API = "http://localhost:8080/api";

let tatCaMonHoc = [];      // Kho tổng (chứa mọi thứ)
let monDaDangKy = [];      // Giỏ hàng cá nhân (chứa đồ đã mua)

// 2. KHI MỞ TRANG WEB LÊN: Đi lấy hàng ngay
document.addEventListener("DOMContentLoaded", function() {
    batDauTaiDuLieu();
    ganSuKienTimKiem();
});

// Hàm điều phối việc tải dữ liệu
async function batDauTaiDuLieu() {
    // Đi tới kho lấy danh sách tổng
    const response1 = await fetch(CUA_HANG_API + "/courses");
    tatCaMonHoc = await response1.json();

    // Đi hỏi xem sinh viên này đã đăng ký gì chưa
    const response2 = await fetch(CUA_HANG_API + "/students/" + SINH_VIEN_ID + "/courses");
    monDaDangKy = await response2.json();

    // Sau khi có 2 danh sách rồi, bắt đầu dán lên bảng cho người dùng xem
    veGiaoDien();
}

// 3. HÀM VẼ GIAO DIỆN (Nơi quan trọng nhất)
function veGiaoDien() {
    // Lấy 2 cái bảng từ HTML ra để chuẩn bị đổ chữ vào
    const bangTren = document.getElementById("danh_sach_mon_hoc");
    const bangDuoi = document.getElementById("danhsachdadangki");

    // Xóa sạch nội dung cũ trước khi vẽ mới
    bangTren.innerHTML = "";
    bangDuoi.innerHTML = "";

    // --- BƯỚC A: VẼ BẢNG TRÊN (Môn để chọn) ---
    for (let i = 0; i < tatCaMonHoc.length; i++) {
        let monNay = tatCaMonHoc[i];

        // KIỂM TRA: Nếu môn này đã nằm trong "giỏ hàng" rồi thì BỎ QUA, không vẽ lên bảng trên
        let daMuaRoi = false;
        for (let j = 0; j < monDaDangKy.length; j++) {
            if (monDaDangKy[j].courseId === monNay.courseId) {
                daMuaRoi = true;
                break;
            }
        }

        if (daMuaRoi === false) {
            // Nếu chưa mua thì mới tạo cái thẻ HTML dán vào bảng trên
            bangTren.innerHTML += `
                <div class="course-item">
                    <input type="checkbox" class="course-checkbox" value="${monNay.courseId}">
                    <label>${monNay.courseName}</label>
                </div>`;
        }
    }

    // --- BƯỚC B: VẼ BẢNG DƯỚI (Môn đã đăng ký) ---
    if (monDaDangKy.length === 0) {
        bangDuoi.innerHTML = "<tr><td colspan='3'>Bạn chưa đăng ký môn nào</td></tr>";
    } else {
        for (let i = 0; i < monDaDangKy.length; i++) {
            let monDaMua = monDaDangKy[i];
            bangDuoi.innerHTML += `
                <tr>
                    <td><input type="checkbox" class="enrolled-checkbox" value="${monDaMua.courseId}" checked></td>
                    <td>${monDaMua.courseName}</td>
                    <td>3</td>
                </tr>`;
        }
    }
}

// 4. NÚT XÁC NHẬN (CONFIRM) - Đưa hàng từ trên xuống dưới
document.getElementById("dangki").addEventListener("click", async function() {
    // Tìm xem người dùng tích vào những ô nào ở bảng trên
    const cacOChon = document.querySelectorAll(".course-checkbox:checked");

    for (let i = 0; i < cacOChon.length; i++) {
        const maMon = cacOChon[i].value;
        // Gửi thư cho Server bảo: "Đăng ký môn này cho tôi"
        await fetch(CUA_HANG_API + "/students/" + SINH_VIEN_ID + "/enroll/" + maMon, { method: 'POST' });
    }

    alert("Đã đăng ký xong!");
    // Tải lại dữ liệu mới nhất từ Server để cập nhật bảng
    batDauTaiDuLieu();
});

// 5. NÚT THAY ĐỔI (CHANGE) - Trả hàng từ dưới lên trên
document.getElementById("change").addEventListener("click", async function() {
    // Tìm các ô ở bảng dưới mà người dùng BỎ TÍCH (không chọn nữa)
    const cacOTrongBangDuoi = document.querySelectorAll(".enrolled-checkbox");

    for (let i = 0; i < cacOTrongBangDuoi.length; i++) {
        if (cacOTrongBangDuoi[i].checked === false) {
            const maMonCanXoa = cacOTrongBangDuoi[i].value;
            // Gửi thư cho Server bảo: "Xóa môn này đi"
            await fetch(CUA_HANG_API + "/students/" + SINH_VIEN_ID + "/drop/" + maMonCanXoa, { method: 'DELETE' });
        }
    }

    alert("Đã cập nhật thay đổi!");
    batDauTaiDuLieu();
});