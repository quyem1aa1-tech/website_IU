package com.app.util;

import java.security.SecureRandom;

/**
 * Lớp tiện ích chứa các hàm bổ trợ về mật khẩu.
 * Sử dụng static để có thể gọi ở bất cứ đâu mà không cần khởi tạo đối tượng.
 */
public class PasswordUtils {

    // Định nghĩa các bộ ký tự để mật khẩu có độ phức tạp cao
    private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz"; // Chữ thường
    private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();    // Chữ hoa
    private static final String NUMBER = "0123456789";                   // Chữ số
    private static final String OTHER_CHAR = "!@#$%&*";                  // Ký tự đặc biệt

    // Gộp tất cả các bộ ký tự trên thành một "kho" ký tự cho phép
    private static final String PASSWORD_ALLOW_BASE = CHAR_LOWER + CHAR_UPPER + NUMBER + OTHER_CHAR;

    /**
     * SecureRandom: Bộ sinh số ngẫu nhiên an toàn hơn Random thông thường.
     * Random thường dựa trên thời gian hệ thống nên hacker có thể dự đoán được.
     * SecureRandom sử dụng các nguồn nhiễu từ hệ điều hành nên cực kỳ khó bẻ khóa.
     */
    private static final SecureRandom random = new SecureRandom();

    /**
     * Hàm chính để tạo chuỗi mật khẩu ngẫu nhiên.
     * @param length: Độ dài mong muốn (ví dụ: 8, 10, 12 ký tự).
     * @return: Một chuỗi mật khẩu thô (Plain Text).
     */
    public static String generateRandomPassword(int length) {
        // Kiểm tra đầu vào: độ dài không được nhỏ hơn 1
        if (length < 1) throw new IllegalArgumentException("Độ dài mật khẩu phải lớn hơn 0");

        // StringBuilder giúp nối chuỗi hiệu năng cao hơn so với dùng String cộng dồn (+)
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            // Bước 1: Lấy ra một vị trí index ngẫu nhiên trong kho PASSWORD_ALLOW_BASE
            // random.nextInt(n) sẽ trả về số từ 0 đến n-1
            int rndCharAt = random.nextInt(PASSWORD_ALLOW_BASE.length());

            // Bước 2: Lấy ký tự tại vị trí đó
            char rndChar = PASSWORD_ALLOW_BASE.charAt(rndCharAt);

            // Bước 3: Đưa ký tự đó vào chuỗi đang xây dựng
            sb.append(rndChar);
        }

        // Trả về kết quả cuối cùng dưới dạng String
        return sb.toString();
    }
}