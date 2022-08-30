package com.yyw.framework.util;

import java.util.Base64;
import java.util.UUID;

/**
 * 22 位UUID 生成器
 * 性能：i7-8750h 100w次生成耗时：
 * 原生36位（0.9s）
 * 去掉'-'32位（1s）
 * Base64压缩22位（2.7s）
 * 字母大小写19位（4.7s）
 *
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/30 16:34
 */
public class UUIDUtil {

    /**
     * 完整 UUID 共128位 (16 byte)
     */
    private static final int UUID_LENGTH = 128 >> 3;

    /**
     * UUID 可以分为两个 long 部分，long 类型长度 64 位 (8 byte)
     */
    private static final int HALF_LENGTH = 64 >> 3;

    /**
     * byte 类型有效位数为 8
     */
    private static final int BYTE_MASK = (1 << 8) - 1;

    private static final Base64.Encoder BASE_64_ENCODER = Base64.getEncoder().withoutPadding();


    /**
     * 生成 22 位压缩版 UUID： 用 base64 压缩 JDK 的 UUID
     *
     * @return 压缩版的 22位 UUID
     */
    public String nextId() {
        UUID uuid = UUID.randomUUID();
        long high = uuid.getMostSignificantBits();
        long low = uuid.getLeastSignificantBits();

        byte[] byteUuid = new byte[UUID_LENGTH];
        for (int i = 0; i < HALF_LENGTH; i++) {
            byteUuid[i] = (byte) (high >>> (HALF_LENGTH * (7 - i)) & BYTE_MASK);
            byteUuid[i + HALF_LENGTH] = (byte) (low >>> (HALF_LENGTH * (7 - i)) & BYTE_MASK);
        }

        return BASE_64_ENCODER.encodeToString(byteUuid);
    }

    /**
     * 压缩
     *
     * @param src uuid字符串，可带有{@code -}
     * @return base64字符串，length=22
     */
    public static String compress(String src) {
        UUID uuid = UUID.fromString(src);
        long msb = uuid.getMostSignificantBits();
        long lsb = uuid.getLeastSignificantBits();

        byte[] b = new byte[16];
        for (int i = 0; i < 8; i++) {
            b[i] = (byte) (msb >>> (8 * (7 - i)) & 0xff);
            b[i + 8] = (byte) (lsb >>> (8 * (7 - i)) & 0xff);
        }

        return Base64.getEncoder().withoutPadding().encodeToString(b);
    }

    /**
     * 解压
     *
     * @param src base64字符串，length=22
     * @return uuid字符串，{@code -}分割
     */
    public static String decompress(String src) {
        byte[] b = Base64.getDecoder().decode(src);

        long msb = 0;
        long lsb = 0;
        for (int i = 0; i < 8; i++) {
            msb = (msb << 8) | (b[i] & 0xff);
            lsb = (lsb << 8) | (b[i + 8] & 0xff);
        }

        return new UUID(msb, lsb).toString();
    }

}